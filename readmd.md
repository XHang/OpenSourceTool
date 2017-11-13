# 工具类库的学习案例
包含一下内容
## Java读取制造word，Excel
### 制造，读取word
#### 介绍
这次我们准备使用POI来读取时创建Word文档。  
POi与之相关的组件是HWPF和XWPF。  
#### HWPF
HWPF 支持Microsoft Word 97 读写。它还提供了对较早的Word 6和Word 95文件格式的有限的只读支持。  
代码的入口类主要是：HWPFDocument，在更高的版本中，它可能被分为两个不同的接口：WordFile和WordDocument  
#### XWPF 
XWPF 支持Word 2007 的.docx格式读和写
代码的主要入口类是：XWPFDocument，从这个类，你可得到段落，图片，表格，部分，标题等
特性：  
1. 用该类创建一个表格时，默认会生成一一行，一cell的表格，可以直接获取到并为其加文本信息  
2. 创建表格的一行后，会默认有一列cell,可以通过指针获取到。


迁移
1. Dom4j使用Xpat要加入一个jaxen依赖，该依赖不会自动添加，需要手动添加

2. 没有遍历某个标签下所有标签的Junit测试

