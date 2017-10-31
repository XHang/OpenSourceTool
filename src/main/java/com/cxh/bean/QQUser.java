package com.cxh.bean;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

@SuppressWarnings("unused")
public class QQUser {
	private String name;
	//该注解可以隐藏生成的集合标签，并把集合内元素的标签名改为friend
	@XStreamImplicit(itemFieldName="friend")
	private String[] friends;
	public QQUser(String name, String ... friends) {
		super();
		this.name = name;
		this.friends = friends;
	}
	
	
}
