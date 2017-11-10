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
