package com.cxh.bean;

public class User {
	private String name;
	
	private String sex;
	
	private String career;
	
	private String nationality;
	
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 得到职业
	 * @return
	 */
	public String getCareer() {
		return career;
	}
	/**
	 * 设置职业
	 * @param career
	 */
	public void setCareer(String career) {
		this.career = career;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
