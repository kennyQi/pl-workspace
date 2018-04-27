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
public class JfServiceClientDemo {
    
    public static void main(String[] args) throws Exception {
	
	testApiClient();
    }
    
    //为福利网提供的4个接口客户端测试
    public static void testApiClient() throws Exception{
	JfServiceClient client = new JfServiceClient();
	
	//设置积分类型
	client.setAccountType( "piaol__consume");
	//设置 web service 服务器地址
	client.setUrlContext("http://192.168.2.226:9119/entjf-admin");

	//接口1：查询单个用户积分
	 List<JfAccount> data = client.queryJf( "a").getData();
	 for(JfAccount a:data)
	     System.out.println(String.format("用户%s 积分%s 在途积分%s",a.getUser() ,a.getJf(),a.getJfTODO()));
	
//	 接口2：查询多个用户积分
	 List<String> userLst=new ArrayList<>();
	 userLst.add("a");
	 userLst.add("xinglj");
	 data = client.queryJf( userLst).getData();
	 for(JfAccount a:data)
	     System.out.println(String.format("用户%s 积分%s 在途积分%s",a.getUser() ,a.getJf(),a.getJfTODO()));
	 
//	 接口3：发放积分
//	System.out.println(client.inJf("a", 100, "发放福利"));
	
	List<JfOrder> orders=new ArrayList<>();
	orders.add(new JfOrder("aa", 100, "福利"));
	orders.add(new JfOrder("ab", 100, "福利"));
//	System.out.println(client.inJfBatch(orders));
	 
//	 接口4：支付积分
//	System.out.println(client.outJf("a", -100, "支付福利"));
    }
   
}
