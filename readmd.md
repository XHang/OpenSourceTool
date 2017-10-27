# 工具类库的学习案例
包含一下内容
## XML的学习和解析

### XStream的基本使用
XStream主要是xml和javaBean之间的互转。
你需要注意的
1. 除非要转的Bean类和XStream在同一个包内，否则需要为设置别名  
	eg：`xStream.alias("userlist", List.class);`  
	如果不设置，会报ClassNoneFoundException  
 
### 你知道吗
1. Element和Node的区别：Element是继承自Node的。  
	Node有三种：  
	Element，Text，Comment(注释)  

