package com.kedacom.expenses.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import com.kedacom.expenses.model.expenses.ExpPaymentB;
import com.kedacom.expenses.model.expenses.ExpUpdatePaymentHis;

public class ExpensesUtils {

	/**
	 * 把子表记录转成历史记录
	 * @param expB
	 * @param recordType 1.表示修改前 2.表示修改后
	 * @return
	 */
	public static ExpUpdatePaymentHis convortBean(ExpPaymentB expB, int recordType, Date time) {
		ExpUpdatePaymentHis his = new ExpUpdatePaymentHis();
		his.setPaymentBId(expB.getId());
		his.setCostSubjectC(expB.getCostSubjectC());
		his.setCostSubjectP(expB.getCostSubjectP());
		his.setRealCostSubjectC(expB.getRealCostSubjectC());
		his.setRealCostSubjectP(expB.getRealCostSubjectP());
		his.setRecordType(recordType);
		his.setCreatetime(time);
		return his;
	}

	/**
	 * 设置创建人跟创建时间
	 * @param obj
	 * @param userId
	 */
	public static synchronized void setCreator(Object obj, Long userId) {
		Method[] declaredMethods = obj.getClass().getMethods();
		Date date = new Date();
		try {
			for (Method method : declaredMethods) {
				if ("setCreateby".equals(method.getName())) {
					method.invoke(obj, userId);
				} else if ("setCreatetime".equals(method.getName())) {
					method.invoke(obj, date);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置创建人跟创建时间
	 * @param obj
	 * @param userId
	 */
	public static synchronized void setCreatorByList(List list, Long userId) {
		for (Object obj : list) {
			setCreator(obj, userId);
		}
	}

	/**
	 * 设置更新时间跟更新人
	 * @param obj
	 * @param userId
	 */
	public static synchronized void setUpdateUser(Object obj, Long userId) {
		Method[] declaredMethods = obj.getClass().getMethods();
		Date date = new Date();
		try {
			for (Method method : declaredMethods) {
				if ("setUpdateby".equals(method.getName())) {
					method.invoke(obj, userId);
				} else if ("setUpdatetime".equals(method.getName())) {
					method.invoke(obj, date);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置更新时间跟更新人
	 * @param obj
	 * @param userId
	 */
	public static synchronized void setUpdateUserByList(List list, Long userId) {
		for (Object obj : list) {
			setUpdateUser(obj, userId);
		}
	}

}
