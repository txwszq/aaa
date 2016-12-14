package com.kedacom.expenses.model;

public class ReturnVO {

	// 源数据对象主键
	private String tableid;
	// 成功标记 S:d成功，E：失败
	private String resultFlag;
	// 错误 信息：E的写入e.getMessage(),注意在各自系统记录错误详细信息
	private String resultMsg;

	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}
