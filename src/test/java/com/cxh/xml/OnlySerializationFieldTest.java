package com.cxh.xml;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.cxh.bean.Game;
import com.cxh.bean.User;
import com.cxh.converter.OnlySerializationFieldConverter;
import com.thoughtworks.xstream.XStream;

public class OnlySerializationFieldTest {
	
	/**
	 * 序列化字段，仅加了字段注解才序列化
	 * 注意：只有name和age有加注解，实际运行应该只序列化这两个字段
	 */
	@Test
	public void serializationField(){
		Game game = new Game ();
		game.setAge(23);
		game.setLead("ME");
		game.setLimitage(18+"");
		game.setMaker("任天堂");
		game.setName("路易火舞");
		XStream xstream = new XStream();
		xstream.alias("GG", Game.class);
		xstream.registerConverter(new OnlySerializationFieldConverter());
		String xml = xstream.toXML(game);
		System.out.println("您测试的xml结果是"+xml);
	}
	
	/**
	 * 演示如何序列化List对象
	 */
	@Test
	public void serializationFieldByList(){
		List<Game> games = new ArrayList<Game> ();
		for(int i=0;i<5;i++){
			Game game = new Game ();
			game.setAge(i+10);
			game.setLead("张"+i);
			game.setLimitage(20+i+"");
			game.setMaker("maker"+i);
			game.setName("tutut"+i);
			games.add(game);
		}
		XStream xStream = new XStream();
		xStream.alias("Gmae", List.class);
		xStream.alias("gamedfg", Game.class);
		xStream.registerConverter(new OnlySerializationFieldConverter());
		String xml = xStream.toXML(games);
		System.out.println("您测试的代码"+xml);
	}
	
	/**
	 * 演示序列化对象中的嵌套对象
	 */
	@Test
	public void serializationObject(){
		Game game = new Game();
		game.setName("super Man");
		game.setAge(23);
		game.setLimitage("4");
		game.setMaker("unknown");
		User user = new User();
		user.setName("林可");
		user.setNationality("Japan");
		game.setProtagonist(user);
		XStream xStream = new XStream();
		xStream.alias("Game", Game.class);
		xStream.registerConverter(new OnlySerializationFieldConverter());
		String xml = xStream.toXML(game);
		System.out.println("演示结果如下");
		System.out.println(xml);
	}
	
	/**
	 * 演示对象里面含有List类型的字段下，应该怎么序列化
	 */
	@Test 
	public void serializationObjectOfList(){
		Game game = new Game();
		game.setName("super Man");
		game.setAge(23);
		game.setLimitage("4");
		game.setMaker("unknown");
		List<User> supportingRole = new ArrayList<>();
		for(int i=0;i<3;i++){
			User user = new User();
			user.setName("配角"+i);
			user.setCareer("魔法师");
			supportingRole.add(user);
		}
		game.setSupportingRole(supportingRole);
		XStream xStream = new XStream();
		xStream.alias("Game", Game.class);
		xStream.registerConverter(new OnlySerializationFieldConverter());
		String xml = xStream.toXML(game);
		System.out.println("当对象里面含有List集合的字段类型时，序列化的结果是");
		System.out.println(xml);
	}
	
	
}
