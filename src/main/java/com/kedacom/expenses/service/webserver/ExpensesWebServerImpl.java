/**
 * @(#)ExpensesWebServer.java 2013-12-20 Copyright 2013 it.kedacom.com, Inc. All
 *                            rights reserved.
 */
package com.kedacom.expenses.service.webserver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedacom.core.utils.BeanUtils;
import com.kedacom.expenses.dao.baseconfig.ConfActivityDao;
import com.kedacom.expenses.dao.baseconfig.ConfBusinessOpportunityDao;
import com.kedacom.expenses.dao.baseconfig.ConfClientDao;
import com.kedacom.expenses.dao.baseconfig.ConfCostSubjectTypeDao;
import com.kedacom.expenses.dao.baseconfig.ConfOtherTakerDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentBDao;
import com.kedacom.expenses.dao.expenses.ExpPaymentHDao;
import com.kedacom.expenses.model.ExpenseFee;
import com.kedacom.expenses.model.ReturnVO;
import com.kedacom.expenses.model.baseconfig.ConfActivity;
import com.kedacom.expenses.model.baseconfig.ConfBusinessOpportunity;
import com.kedacom.expenses.model.baseconfig.ConfClient;
import com.kedacom.expenses.model.baseconfig.ConfCostSubjectType;
import com.kedacom.expenses.model.baseconfig.ConfOtherTaker;
import com.kedacom.expenses.model.expenses.ExpPaymentB;
import com.kedacom.expenses.model.expenses.ExpPaymentH;
import com.kedacom.expenses.service.webserver.itf.ExpensesWebServerItf;
import com.kedacom.expenses.service.webserver.util.DateFormatUtil;
import com.kedacom.expenses.service.webserver.util.JsonUtil;
import com.kedacom.security.dao.SysUserDao;
import com.kedacom.security.model.SysUser;

/**
 * (提供webserver).
 * @author zhujun
 * @version 2013-12-20
 */
@WebService(targetNamespace = "http://itf.webserver.service.expenses.kedacom.com/")
@SuppressWarnings("static-access")
@Service
@Transactional
public class ExpensesWebServerImpl implements ExpensesWebServerItf {

	public static final int CLIENT_TYPE = 1;// 客户
	public static final int ACT_TYPE = 2;// 活动
	public static final int BUS_TYPE = 3;// 商机

	private Log logger = LogFactory.getLog(ExpensesWebServerImpl.class);
	// 客户对象
	public static Map<String, String> CLIENT_STATUS = new HashMap<String, String>();

	public ExpensesWebServerImpl() {
		CLIENT_STATUS.put("Prospect", "潜在");
		CLIENT_STATUS.put("Examine", "审核中");
		CLIENT_STATUS.put("Active", "有效");
		CLIENT_STATUS.put("Reject", "拒绝");
		CLIENT_STATUS.put("Inactive", "失效");
	}

	@Resource
	private ConfActivityDao confActDao;
	@Resource
	private ConfClientDao confClientDao;
	@Resource
	private ConfBusinessOpportunityDao confBusDao;
	@Resource
	private ConfOtherTakerDao takerDao;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private ExpPaymentBDao paymentBDao;
	@Resource
	private ExpPaymentHDao paymentHDao;
	@Resource
	private ConfCostSubjectTypeDao subDao;

	private static BeanUtils kdBeanUtil = new BeanUtils();

	/**
	 * (同步数据).
	 * @param type 类型 1是客户 2是活动 3是商机
	 * @param jsonInfo json数据
	 * @return
	 */
	@Override
	public String updateOrAdd(int type, String jsonInfo) {
		String msgStr = "";
		if (CLIENT_TYPE == type) {
			msgStr = updateOrAddClient(jsonInfo);
		} else if (ACT_TYPE == type) {
			msgStr = updateOrAddAct(jsonInfo);
		} else if (BUS_TYPE == type) {
			msgStr = updateOrAddBusiness(jsonInfo);
		}
		return msgStr;
	}

	@Override
	public String getExpPaymentData() {
		List<ExpenseFee> listexpFee = new ArrayList<ExpenseFee>();
		List<ExpPaymentB> listPayBs = getExpPayBs();
		if (kdBeanUtil.isNotEmpty(listPayBs)) {
			for (ExpPaymentB expPayB : listPayBs) {
				ExpenseFee fee = new ExpenseFee();
				try {
					fee.setFeeId(expPayB.getId() + "");
					fee.setCrmAccntId(expPayB.getCusId() == null ? "" : confClientDao.getById(expPayB.getCusId()).getClientCrmId());
					fee.setCrmActId(expPayB.getActId() == null ? "" : confActDao.getById(expPayB.getActId()).getOutActId());
					fee.setCrmOppId(expPayB.getBusId() == null ? "" : confBusDao.getById(expPayB.getBusId()).getBoCrmId());
					fee.setExpAmt(expPayB.getAmount());
					fee.setFeeDesc(expPayB.getDescipt());
					fee.setStartT(expPayB.getBeginDate() == null ? "" : DateFormatUtil.toTimeFormat2(expPayB.getBeginDate()));
					fee.setEndT(expPayB.getEndDate() == null ? "" : DateFormatUtil.toTimeFormat2(expPayB.getEndDate()));
					if (kdBeanUtil.isNotEmpty(expPayB.getRealCostSubjectP())) {
						ConfCostSubjectType parentType = subDao.getById(new Long(expPayB.getRealCostSubjectP()));
						if (kdBeanUtil.isNotEmpty(parentType)) {
							fee.setFeeType(parentType.getCost_name());
							fee.setFeeTypeCode(parentType.getCode());
						}
					}
					if (kdBeanUtil.isNotEmpty(expPayB.getRealCostSubjectC())) {
						ConfCostSubjectType childenType = subDao.getById(new Long(expPayB.getRealCostSubjectP()));
						if (kdBeanUtil.isNotEmpty(childenType)) {
							fee.setFeeSubType(childenType.getCost_name());
							fee.setFeeSubTypeCode(childenType.getCode());
						}
					}
					fee.setEmpLogin((String) paymentHDao.getOne("getUserAccount", expPayB.getExpensesHID()));
				} catch (Exception ex) {
					fee.setTransCd(-1);
					fee.setErrorMsg(ex.getMessage());
					continue;
				}
				listexpFee.add(fee);
			}
		}
		return JSONArray.fromObject(listexpFee).toString();
	}

	private List<ExpPaymentB> getExpPayBs() {
		// 获取前一天时间 yyyy-MM-dd
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String endDate = sf.format(date);
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_YEAR, -1);
		String beginDate = sf.format(ca.getTime());
		// 根据条件查询前一天的审批通过的数据
		Map<String, Object> parmars = new HashMap<String, Object>();
		parmars.put("begindate", beginDate);
		parmars.put("enddate", endDate);
		List<ExpPaymentB> listPayBs = paymentBDao.getBySqlKey("getExpPayPassDatas", parmars);
		return listPayBs;
	}

	/**
	 * (商机同步).
	 * @param jsonInfo
	 */
	private String updateOrAddBusiness(String jsonInfo) {

		List<ReturnVO> listReturnVO = new ArrayList<ReturnVO>();
		JSONArray jsonArr = JSONArray.fromObject(jsonInfo);
		if (kdBeanUtil.isNotEmpty(jsonArr)) {
			for (int n = 0; n < jsonArr.size(); n++) {
				ReturnVO returnVO = new ReturnVO();
				ConfBusinessOpportunity newBus = new ConfBusinessOpportunity();
				try {
					newBus = (ConfBusinessOpportunity) JsonUtil.toObject(jsonArr.get(n).toString(), ConfBusinessOpportunity.class);
					Map<String, Object> parmars = new HashMap<String, Object>();
					parmars.put("boCrmId", newBus.getBoCrmId());
					List<ConfBusinessOpportunity> listOldBus = confBusDao.getBySqlKey("getByCrmId", parmars);
					if (kdBeanUtil.isEmpty(listOldBus)) {
						confBusDao.add(newBus);
						saveOrUpdateTaker(ConfOtherTaker.BUS_TYPE, newBus.getId(), newBus.getTakers(), true);
					} else {
						for (ConfBusinessOpportunity oldBus : listOldBus) {
							org.springframework.beans.BeanUtils.copyProperties(newBus, oldBus, new String[]{"id"});
							confBusDao.update(oldBus);
							saveOrUpdateTaker(ConfOtherTaker.BUS_TYPE, oldBus.getId(), newBus.getTakers(), false);
						}
					}
					returnVO.setResultFlag("S");
					returnVO.setResultMsg("同步商机成功");
					returnVO.setTableid(newBus.getBoCrmId());
					listReturnVO.add(returnVO);
				} catch (Exception e) {
					returnVO.setResultFlag("E");
					returnVO.setResultMsg("同步商机失败：" + e.getMessage());
					returnVO.setTableid(newBus.getBoCrmId());
					listReturnVO.add(returnVO);
					logger.error(e.getMessage());
					continue;
				}
			}
		}
		return JSONArray.fromObject(listReturnVO).toString();

	}

	/**
	 * (活动同步).
	 * @param jsonInfo
	 */
	private String updateOrAddAct(String jsonInfo) {
		List<ReturnVO> listReturnVO = new ArrayList<ReturnVO>();
		JSONArray jsonArr = JSONArray.fromObject(jsonInfo);
		if (kdBeanUtil.isNotEmpty(jsonArr)) {
			for (int n = 0; n < jsonArr.size(); n++) {
				ReturnVO returnVO = new ReturnVO();
				ConfActivity actNew = new ConfActivity();
				try {
					actNew = (ConfActivity) JsonUtil.toObject(jsonArr.get(n).toString(), ConfActivity.class);
					Map<String, Object> parmars = new HashMap<String, Object>();
					parmars.put("outActId", actNew.getOutActId());
					List<ConfActivity> listOldAct = confActDao.getBySqlKey("getAll", parmars);
					if (kdBeanUtil.isEmpty(listOldAct)) {
						actNew = getBusOrClientForAct(actNew);
						confActDao.add(actNew);
						saveOrUpdateTaker(ConfOtherTaker.ACT_TYPE, actNew.getId(), actNew.getTakers(), true);
					} else {
						for (ConfActivity confActOld : listOldAct) {
							org.springframework.beans.BeanUtils.copyProperties(actNew, confActOld, new String[]{"id"});
							confActOld = getBusOrClientForAct(confActOld);
							confActDao.update(confActOld);
							saveOrUpdateTaker(ConfOtherTaker.ACT_TYPE, actNew.getId(), actNew.getTakers(), false);
						}
					}
					returnVO.setResultFlag("S");
					returnVO.setResultMsg("同步活动成功");
					returnVO.setTableid(actNew.getOutActId());
					listReturnVO.add(returnVO);
				} catch (Exception e) {
					returnVO.setResultFlag("E");
					returnVO.setResultMsg("同步活动失败：" + e.getMessage());
					returnVO.setTableid(actNew.getOutActId());
					listReturnVO.add(returnVO);
					logger.error(e.getMessage());
					continue;
				}
			}
		}
		return JSONArray.fromObject(listReturnVO).toString();
	}

	/**
	 * (客户同步).
	 * @param jsonInfo
	 */
	@Transactional
	private String updateOrAddClient(String jsonInfo) {
		List<ReturnVO> listReturnVO = new ArrayList<ReturnVO>();
		JSONArray jsonArr = JSONArray.fromObject(jsonInfo);
		if (kdBeanUtil.isNotEmpty(jsonArr)) {
			for (int n = 0; n < jsonArr.size(); n++) {
				ReturnVO returnVO = new ReturnVO();
				ConfClient confClientNew = new ConfClient();
				try {
					confClientNew = (ConfClient) JsonUtil.toObject(jsonArr.get(n).toString(), ConfClient.class);
					Map<String, Object> parmars = new HashMap<String, Object>();
					// 根据ID跟code查询
					parmars.put("clientCrmId", confClientNew.getClientCrmId());
					parmars.put("clientCode", confClientNew.getClientCode());
					List<ConfClient> listOldClients = confClientDao.getBySqlKey("getByOtherId", parmars);
					if (kdBeanUtil.isEmpty(listOldClients)) {
						confClientNew.setClientStatus(CLIENT_STATUS.get(confClientNew.getClientStatus()));
						confClientDao.add(confClientNew);
						saveOrUpdateTaker(ConfOtherTaker.CLIENT_TYPE, confClientNew.getId(), confClientNew.getTakers(), true);
					} else {
						for (ConfClient clientOld : listOldClients) {
							org.springframework.beans.BeanUtils.copyProperties(confClientNew, clientOld, new String[]{"id"});
							clientOld.setClientStatus(CLIENT_STATUS.get(clientOld.getClientStatus()));
							confClientDao.update(clientOld);
							saveOrUpdateTaker(ConfOtherTaker.CLIENT_TYPE, clientOld.getId(), confClientNew.getTakers(), false);
						}
					}
					returnVO.setResultFlag("S");
					returnVO.setResultMsg("同步客户成功");
					returnVO.setTableid(confClientNew.getClientCrmId());
					listReturnVO.add(returnVO);
				} catch (Exception e) {
					returnVO.setResultFlag("E");
					returnVO.setResultMsg("同步客户失败：" + e.getMessage());
					returnVO.setTableid(confClientNew.getClientCrmId());
					listReturnVO.add(returnVO);
					logger.error(e.getMessage());
					continue;
				}
			}
		}
		return JSONArray.fromObject(listReturnVO).toString();
	}

	/**
	 * (更新关联客户商机活动用户).
	 * @param type 类型
	 * @param billId 单据主键
	 * @param listTaker 配置人员
	 * @param isAdd true 添加 false 删除添加
	 */
	private void saveOrUpdateTaker(Long type, Long billId, List<ConfOtherTaker> listTaker, boolean isAdd) throws Exception {
		if (kdBeanUtil.isNotEmpty(listTaker)) {
			if (isAdd) {
				for (ConfOtherTaker taker : listTaker) {
					SysUser user = (SysUser) sysUserDao.getByAccount(taker.getUser_account());
					taker.setBusiness_id(billId);
					taker.setType(type);
					taker.setUser_id(user.getUserId());
					takerDao.add(taker);
				}
			} else {
				// 删除人员重新插入
				Map<String, Object> takerPar = new HashMap<String, Object>();
				takerPar.put("businessId", billId);
				takerDao.delBySqlKey("delByBusId", takerPar);
				for (ConfOtherTaker taker : listTaker) {
					SysUser user = (SysUser) sysUserDao.getByAccount(taker.getUser_account());
					if (null == user) {
						throw new Exception("客户账号在报销系统中不存在");
					}
					taker.setBusiness_id(billId);
					taker.setType(type);
					taker.setUser_id(user.getUserId());
					takerDao.add(taker);
				}
			}
		}

	}

	/**
	 * (把客户商机信息赋值给活动).
	 * @param confAct
	 * @return
	 */
	private ConfActivity getBusOrClientForAct(ConfActivity confAct) {
		Map<String, Object> clientPar = new HashMap<String, Object>();
		clientPar.put("clientCrmId", confAct.getCusId());
		// 客户
		List<ConfClient> listClient = confClientDao.getBySqlKey("getByOtherId", clientPar);
		if (kdBeanUtil.isNotEmpty(listClient)) {
			confAct.setCusId(listClient.get(0).getId() + "");
			confAct.setCusName(listClient.get(0).getClientName());
			confAct.setCusState(listClient.get(0).getClientStatus());
			confAct.setCusTakerCode(listClient.get(0).getClientPrincipalCode());
		}
		// 商机
		Map<String, Object> busPar = new HashMap<String, Object>();
		busPar.put("boCrmId", confAct.getBusId());
		List<ConfBusinessOpportunity> listBus = confBusDao.getBySqlKey("getByCrmId", busPar);
		if (kdBeanUtil.isNotEmpty(listBus)) {
			confAct.setBusBegin(listBus.get(0).getProjectApprovalTime());
			confAct.setBusEnd(listBus.get(0).getProjectApprovalEndtime());
			confAct.setBusId(listBus.get(0).getId() + "");
			confAct.setBusName(listBus.get(0).getBoName());
			confAct.setBusCode(listBus.get(0).getBoCode());
			confAct.setBusBudGet(listBus.get(0).getBoBudget() + "");
			confAct.setBusTakerCode(listBus.get(0).getBoPrincipalCode());
		}
		return confAct;
	}

}
