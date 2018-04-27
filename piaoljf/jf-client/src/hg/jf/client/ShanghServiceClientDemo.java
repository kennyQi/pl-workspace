/**
 * @JfServiceClientDemo.java Create on 2015年1月28日上午9:19:27
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf.client;

import hgtech.jfaccount.JfAccount;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.springframework.util.Log4jConfigurer;

/**
 * @类功能说明：积分服务web service 的客户端代码 测试
 * @类修改者：
 * @修改日期：2015年1月28日上午9:19:27
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月28日上午9:19:27
 * @version：
 */
public class ShanghServiceClientDemo {
    
    public static void main(String[] args) throws Exception {
	
	testApiClient();
    }
    
    //为福利网提供的4个接口客户端测试
    public static void testApiClient() throws Exception{
	ShanghServiceClient client = new ShanghServiceClient();
	
	//设置应用id
	client.setAppId("9a9");
	//设置应用密码
	client.setMd5Key("99a");
	//设置 web service 服务器地址
	client.setUrlContext("http://localhost:8080/hg-hjf-jf");

	//接口1：查询单个用户积分
	List<JfAccount> data ;
/*	 final ResultDto<List<JfAccount>> queryJf = client.queryJf( "15305153869");
	 if(queryJf.ok){
		 data = queryJf.getData();
		 for(JfAccount a:data)
		     System.out.println(String.format("用户%s 积分%s 在途积分%s",a.getUser() ,a.getJf(),a.getJfTODO()));
	 }else
		 System.out.println(queryJf.text);
*/	 
//	 接口2：查询多个用户积分
	 List<String> userLst=new ArrayList<>();
	 userLst.add("15305153869");
	 userLst.add("15305153860");
/*	 data = client.queryJf( userLst).getData();
	 for(JfAccount a:data)
	     System.out.println(String.format("用户%s 积分%s 在途积分%s",a.getUser() ,a.getJf(),a.getJfTODO()));
*/
//	 接口4：查询预付费余额积分
	System.out.println("查询预付费余额积分:"+client.queryAppAccount().jf);
	 
//	 接口3：发放积分
	System.out.println(client.chargeJf("15305153869", 100, "通过商户端sdk测试充值"));

//	 接口4：查询预付费余额积分
	System.out.println("查询预付费余额积分:"+client.queryAppAccount().jf);
	
    }
   
}
