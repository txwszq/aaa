/**
 * @(#)ExpensesWebServerItf.java 2013-12-20 Copyright 2013 it.kedacom.com, Inc.
 *                               All rights reserved.
 */
package com.kedacom.expenses.service.webserver.itf;

import javax.jws.WebService;

/**
 * (用一句话描述类的主要功能).
 * @author zhujun
 * @version 2013-12-20
 */
@WebService
public interface ExpensesWebServerItf {

	public String updateOrAdd(int type, String jsonInfo);

	public String getExpPaymentData();

}
