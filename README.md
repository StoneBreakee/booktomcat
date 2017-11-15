#booktomcat
本repository为在学习 深入剖析tomcat 一书中涉及的代码以及笔记
所有代码均在vim中编写，没有使用IDE，所有代码均已通过测试，可正常运行

现已完成第一章节和第二章节

java源代码编译指令
javac -classpath $TOMCAT/lib/servlet-api.jar:. -d classes/ @sourcefile

书本上的源代码可参考 https://github.com/Aresyi/HowTomcatWorks

从第三章开始
catalina-optional.jar中有CookieTools.class这个类
catalina.jar中有StringManager.class这个类
