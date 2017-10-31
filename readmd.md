# 工具类库的学习案例
包含一下内容
## XML的学习和解析

### XStream的基本使用
XStream主要是xml和javaBean之间的互转。
你需要注意的
1. 除非要转的Bean类和XStream在同一个包内，否则需要为设置别名  
	eg：`xStream.alias("userlist", List.class);`  
	如果不设置，会报ClassNoneFoundException
	
2：对象转xml的时候，如果对象含有一个集合字段，那么最后生成的xml会不好看。
	蛤？怎么不好看
	大概是这样子的
	
	<QQ>
	  <name>陈厦航</name>
	  <friends>
	    <string>曽根美雪</string>
	    <string>林可</string>
	    <string>成步堂龙一</string>
	  </friends>
	</QQ>

   这时候就需要我们用注解来把它美容一下。比如friends标签去掉，string标签改名
   注意：XStream的注解并不是加在实体类就OK了，你要生效的话，需要在程序上加上`xstream.processAnnotations(RendezvousMessage.class);`
   参数是加了注解的实体类class
	
### XStream的问题
1. 对于xml转对象的，如果xml里面有一个空标签  ，而这个空标签正好对着实体类的一个数值字段，那么进行转换会报错，报数值转换异常。
	解决办法,注册一个转换器。。请看本项目的示例
 
### 你知道吗
1. Element和Node的区别：Element是继承自Node的。  
	Node有三种：  
	Element，Text，Comment(注释)  

