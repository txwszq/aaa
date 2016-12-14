package com.kedacom.expenses.model.qq;

import com.kedacom.core.model.BaseModel;


public class FriendCode extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//hash_code
	private String hash_code;
	//city_code
	private String city_code;
	//state
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
	*设置 hash_code
	*/
	public String getHash_code(){
		return hash_code;
	}
	
	/**
	*获取 hash_code
	*/
	public void setHash_code(String hash_code){
		this.hash_code = hash_code;
	}
	/**
	*设置 city_code
	*/
	public String getCity_code(){
		return city_code;
	}
	
	/**
	*获取 city_code
	*/
	public void setCity_code(String city_code){
		this.city_code = city_code;
	}
	/**
	*设置 state
	*/
	public Long getState(){
		return state;
	}
	
	/**
	*获取 state
	*/
	public void setState(Long state){
		this.state = state;
	}
}