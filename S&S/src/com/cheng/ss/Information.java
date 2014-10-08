package com.cheng.ss;
/**
 * 简单的信息存储类
 * @author Cheng
 *
 */
public class Information {
	public int id;
	public String name;
	public String phoneNum;

	public Information() {
	}

	public Information(String name, String phoneNum) {
		this.name = name;
		this.phoneNum = phoneNum;
	}
	public Information(int id, String name,String phoneNum){
		this.id=id;
		this.name=name;
		this.phoneNum=phoneNum;
	}
}
