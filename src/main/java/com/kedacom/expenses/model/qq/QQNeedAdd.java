package com.kedacom.expenses.model.qq;

import com.kedacom.core.model.BaseModel;


public class QQNeedAdd extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//qq_num
	private String qq_num;
	//1,男 2,女
	private Long sex;
	//age
	private String age;
	//0,没有被添加过 1,被添加过
	private Long state;
	/**
	*设置 id
	*/
	public Long getId(){
		return id;
	}
	
	/**
	*获取 id
	*/
	public void setId(Long id){
		this.id = id;
	}
	/**
	*设置 qq_num
	*/
	public String getQq_num(){
		return qq_num;
	}
	
	/**
	*获取 qq_num
	*/
	public void setQq_num(String qq_num){
		this.qq_num = qq_num;
	}
	/**
	*设置 1,男 2,女
	*/
	public Long getSex(){
		return sex;
	}
	
	/**
	*获取 1,男 2,女
	*/
	public void setSex(Long sex){
		this.sex = sex;
	}
	/**
	*设置 age
	*/
	public String getAge(){
		return age;
	}
	
	/**
	*获取 age
	*/
	public void setAge(String age){
		this.age = age;
	}
	/**
	*设置 0,没有被添加过 1,被添加过
	*/
	public Long getState(){
		return state;
	}
	
	/**
	*获取 0,没有被添加过 1,被添加过
	*/
	public void setState(Long state){
		this.state = state;
	}
}