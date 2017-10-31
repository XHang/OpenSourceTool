package com.cxh.bean;

public class Game {
	private String name;
	private Integer age;
	private String limitage;
	private String maker;
	private String lead;
	public String getLead() {
		return lead;
	}
	public void setLead(String lead) {
		this.lead = lead;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getLimitage() {
		return limitage;
	}
	public void setLimitage(String limitage) {
		this.limitage = limitage;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	@Override
	public String toString() {
		return "Game [name=" + name + ", age=" + age + ", limitage=" + limitage
				+ ", maker=" + maker + ", lead=" + lead + "]";
	}
	
}
