package com.cxh.bean;

import java.util.List;
import com.cxh.annotation.OnlySerialization;

public class Game {
	
	@OnlySerialization("年龄")
	private String name;
	@OnlySerialization
	private Integer age;
	
	private String limitage;
	
	private String maker;
	
	private String lead;
	
	@OnlySerialization("主角")
	private User protagonist;
	
	
	
	public List<User> getSupportingRole() {
		return supportingRole;
	}
	public void setSupportingRole(List<User> supportingRole) {
		this.supportingRole = supportingRole;
	}
	
	@OnlySerialization("配角们")
	private List<User> supportingRole;
	
	public User getProtagonist() {
		return protagonist;
	}
	public void setProtagonist(User protagonist) {
		this.protagonist = protagonist;
	}
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
