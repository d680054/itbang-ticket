# Introduction

基于spring-boot和selenium的自动购票程序,如果售票未开始,程序自动计算时间差,时间一到自动购票(仅限当天).

案例:抢票当天10点到公司后运行此程序,11点半出去吃饭,12点程序自动开始抢票.

如果大家发现问题可以修改后提交代码,我来merge.

# 使用环境
* Mac(10.13.6)+Firefox(61.0.1) 已测
* Win10+Firefox(61.0.2)        已测

# 使用方法
### 终端用户
下载dist目录下的zip文件,解压后修改/bin/eventbrite.properties, mac用户运行sh tickt.sh, windows用户双击ticket.bat

### 开发人员
下载源代码,导入到Intellij(需lombok插件)或Eclippse(未测过), 按照提示修改eventbrite.properties 
运行spring-boot

如需打包 运行 mvn clean package, 

# 框架描述
一个com.hj.selenium包, 该框架会自动扫描含有@Page注解,完成与webdriver驱动的绑定

一个com.hj.eventbrite包, page包下定义了所有页面的标签和事件, service包下的process方法定义业务逻辑

如果需要自动化其他项目,比如ebay 那么只需要修改eventbrite包下的page和重新定义process业务逻辑就行

# Any issue or questions
```
Email: davidzheng1022@gmail.com
```
