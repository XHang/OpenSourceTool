package com.cxh.converter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.String.StringUitl.StringUitl;
import com.cxh.annotation.OnlySerialization;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 在Bena序列化成XML时，只序列化指定字段。指定字段必须加@OnlySerialization注解
 * 支持内部实体类的别名注解（同样使用OnlySerialization）
 * @author Mr-hang
 *
 */
public class OnlySerializationFieldConverter implements Converter{

	@Override
	public boolean canConvert(Class type) {
		//除List之外的都由该转换器进行序列化，list由XStream自带的转换器序列化，这样可以使别名xStream.alias生效
		return !type.equals(ArrayList.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		if(source == null){
			return ;
		}
		Class<?> clazz = source.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field:fields){
			OnlySerialization annotation = field.getAnnotation(OnlySerialization.class);
			if(annotation == null){
				continue;
			}
			String nodeName = "";
			if(StringUitl.isNoneEmpty(annotation.value())){
				nodeName = annotation.value();
			}else{
				 nodeName = field.getName();
			}
			Class<?> fieldType = field.getType();
			if(fieldType.equals(List.class)){
				writer.startNode(nodeName);
				field.setAccessible(true);
				try {
					processList((List<?>)field.get(source),writer);
				}catch (IllegalAccessException e) {
					throw new RuntimeException("序列化"+nodeName+"节点失败",e);
				}
				writer.endNode();
				continue;
			}
			field.setAccessible(true);
			writer.startNode(nodeName);
			try {
				if(isBaseType(fieldType)){
					writer.setValue(field.get(source).toString());
				}else{
					marshal(field.get(source),writer,context);
				}
				
			} catch (Exception e) {
				throw new RuntimeException("序列化"+nodeName+"节点失败",e);
			}
			writer.endNode();
		}
	}
	
	private void processList(List<?> list,HierarchicalStreamWriter writer){
		if(list == null){
			return ;
		}
		for(Object obj:list){
			String nodeName = "";
			OnlySerialization alias = obj.getClass().getAnnotation(OnlySerialization.class);
			if(alias !=null && StringUitl.isNoneEmpty(alias.value())){
				nodeName = alias.value();
			}else{
				nodeName =obj.getClass().getSimpleName();
			}
			writer.startNode(nodeName);
			marshal(obj, writer, null);
			writer.endNode();
		}
	}
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return null;
	}
	
	/**
	 * 判断传进来的类型是否是基本数据类型
	 * @param type
	 * @return
	 */
	  private boolean isBaseType(Class<?> type)
	    {
	        if (type.equals(Integer.class)
	            || type.equals(Double.class)
	            || type.equals(String.class)    
	            || type.equals(Boolean.class)
	            || type.equals(Long.class)
	            ||type.equals(Short.class)
	            ||type.equals(Byte.class)
	            ||type.equals(Float.class)
	        	||type.equals(BigDecimal.class))
	        {
	            return true;
	        }
	        return false;
	    }
}
