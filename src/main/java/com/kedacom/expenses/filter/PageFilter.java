/**
 * @(#)PageFilter.java 2013年11月20日 Copyright 2013 it.kedacom.com, Inc. All
 *                     rights reserved.
 */
package com.kedacom.expenses.filter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kedacom.core.page.PageBean;
import com.kedacom.core.page.PageUtils;
import com.kedacom.core.page.QueryFilter;
import com.kedacom.core.utils.RequestUtil;
import com.kedacom.expenses.common.Constants;

/**
 * 分页查询配置对象.
 * @author zhujun
 * @version 2013-11-20
 */
public class PageFilter extends QueryFilter {

	private Logger logger = LoggerFactory.getLogger(PageFilter.class);

	public PageFilter() {
		super(RequestUtil.getHttpServletRequest());
		init(RequestUtil.getHttpServletRequest());
	}

	/**
	 * @param request
	 */
	public PageFilter(HttpServletRequest request) {
		super(request);
		init(request);
	}

	private void init(HttpServletRequest request) {
		try {
			Map<String, Object> map = getQueryMap(request);
			super.setFilters(map);
			// 分页 默认一页 20条记录
			JSONObject fromObject = JSONObject.fromObject(request.getParameter("pagevo"));
			int page = 1;
			if (fromObject.has(Constants.CURRENT_PAGE) && StringUtils.isNotEmpty(fromObject.getString(Constants.CURRENT_PAGE))) {
				page = fromObject.getInt(Constants.CURRENT_PAGE);
			}

			int pageSize = PageBean.DEFAULT_PAGE_SIZE.intValue();
			if (fromObject.has(Constants.PAGE_SIZE) && StringUtils.isNotEmpty(fromObject.getString(Constants.PAGE_SIZE))) {
				pageSize = fromObject.getInt(Constants.PAGE_SIZE);
			}

			/**
			 * 目前没有使用oldPage,故这段代码不会走。
			 */
			int oldPageSize = PageBean.DEFAULT_PAGE_SIZE.intValue();
			if (fromObject.has(Constants.OLD_PAGE_SIZE) && StringUtils.isNotEmpty(fromObject.getString(Constants.OLD_PAGE_SIZE))) {
				oldPageSize = fromObject.getInt(Constants.OLD_PAGE_SIZE);

				if (pageSize != oldPageSize) {
					int first = PageUtils.getFirstNumber(page, oldPageSize);
					page = first / pageSize + 1;
				}
			}
			super.getPageBean().setPagesize(pageSize);
			super.getPageBean().setCurrentPage(page);
		} catch (Exception ex) {
			this.logger.error(ex.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Object> getQueryMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			String val = values[0].trim();
			if (StringUtils.isNotEmpty(val)) {
				map.put(key, values[0].trim());
			}
		}

		return map;
	}
}
