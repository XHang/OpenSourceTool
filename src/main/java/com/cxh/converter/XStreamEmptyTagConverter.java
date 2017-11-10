package com.cxh.converter;

import java.math.BigDecimal;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 转换器
 * 在XML转实体类时，将XMl中为数值类型的空标签，序列成NULL值
 * 如若不然，会爆数值转换异常。
 * 注意，如果空标签对应的是基本类型的字段，则此转换器完全不可用，你得返回一个默认的值
 * @author Mr-hang
 *
 */
public class XStreamEmptyTagConverter implements Converter{

	@Override
	public boolean canConvert(Class type) {
		return type.equals(BigDecimal.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String value = reader.getValue();
		if(value ==null || value.trim().length() ==0){
			return null;
		}
		return value;
	}
}
