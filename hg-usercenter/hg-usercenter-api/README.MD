api使用方式：
1.maven工程里引入本api打成的jar；
如果是maven工程直接在pom.xml中添加依赖
<dependency>
	<groupId>hg.framework</groupId>
	<artifactId>hg-usercenter-api</artifactId>
	<version>0.1-SNAPSHOT</version>
</dependency>

2.调用UserApiController中对应的接口：
public static void main( String[] args )
{
   String sign = UserApiController.genSign("A", "2016-07-01 09:55:00");
   System.out.println(sign);
}

3.如果是普通Java工程
我们工程运行mvn assembly:assembly  ; 
打成带带依赖的jar包,直接扔给对方引用
hg-usercenter-api-0.1-SNAPSHOT-jar-with-dependencies.jar