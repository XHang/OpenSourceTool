package com.cxh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//指定该注解只能用于字段上
@Target(value = {ElementType.FIELD,ElementType.TYPE})
//指定该注解在运行时可检测到
@Retention(RetentionPolicy.RUNTIME)
/**
 * 此注解只能在字段中使用，可以指定那些字段应该被注解
 * @author Mr-hang
 *
 */
public @interface OnlySerialization {
	/**
	 * 指定序列化时此字段对应的标签名，默认是字段名
	 * @return
	 */
	public String value() default "" ;
}
