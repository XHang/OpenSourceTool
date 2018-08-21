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
### 制造，读取Excel
该工程主要是学习用java生成Excel文档。用POI实现吧
使用的POI版本是：3.17
以上，没有了

#### 初步认识
POI库里面对于EXCEL的处理主要分为两类
一类是：HSSF ，它支持Microsoft Excel 97（-2003）文件格式的读写
一类是：XSSF ，它支持Microsoft Excel XML（2007+）文件格式（OOXML）的读写
然后还有一个包：SS 它提供通用的API为两种文件提供通用支持

POI库里面对于Word的处理主要分为两类
一类是：HWPF ，它支持Microsoft Excel 97（-2003）文件格式的读写
一类是：XWPF ，它支持Microsoft Excel XML（2007+）文件格式（OOXML）的读写

#### 疑难杂症
如果单元格的宽度不够，有些日期可能会显示####，这并不是格式问题，你试试把单元格宽度调宽点
