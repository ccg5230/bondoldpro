说明：此项目现在最新版本1.0,用于token验证，仅仅在innodealing公司内部使用.
1.使用方法：
将
<dependency>
	<groupId>com.innodealing</groupId>
	<artifactId>dm-security-client</artifactId>
	<version>1.0</version>
</dependency>
添加到使用到springboot项目的pom.xml文件依赖中，然后在项目的总入口class使用@EnableTokenFilter启用即可(默认不加@EnableTokenFilter过滤器是不生效的。)。
example：

@EnableTokenFilter
public class Application{


}

2.notice：
在使用的过程中在请求头中加上参数token(token登录时候在安全服务获取)。使用本项目需要在web项目配置innodealing的数据源。