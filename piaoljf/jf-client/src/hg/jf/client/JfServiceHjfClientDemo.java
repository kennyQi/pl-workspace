/**
 * @JfServiceClientDemo.java Create on 2015年1月28日上午9:19:27
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf.client;

import hg.common.page.Pagination;
import hg.common.util.FileUtils;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfFlow;
import hgtech.jfcal.model.CalResult;
import hgtech.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSON;

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
public class JfServiceHjfClientDemo {
    
    public static void main(String[] args) throws Exception {
	
 		
	testApiClient();
//    	testCalClient();
    }
    
    //测试积分引擎
    public static void testCalClient() throws Exception{
    	JfServiceClient client = new JfServiceClient();

    	//设置引擎服务地址
    	client.setUrlAdmin("http://localhost:8081/hjf-admin");
    	//设置积分帐务地址
    	client.setUrlContext("http://localhost:8081/hjf-admin");
//    	client.setUrlAdmin("http://192.168.2.226:8890/hg-hjf-admin");

    	//    	签名用的md5密码
    	client.setMd5Key("abc");
    	
    	//    	传递交易数据
    	PiaolTrade t=new PiaolTrade();
    	t.user="tom";
    	t.date="20150108";
    	t.tradeName=t.TRADE_QUERYDISCOUNTCODEVALID;// t.TRADE_CHOUJIAGN;
    	
    	t.discountCode="adfsd3434";

    	//    	仅仅根据引擎计算
//		final Collection<CalResult> ret = client.calJfNoSave(t);
//		System.out.println(ret);
		
    	//		调用规则引擎计算,并将结果积分入账
//		System.out.println( client.calAndCommitJf(t));
		
		//接口：查询单个用户积分
		 String userId_a = "15305153869";
		ResultDto<List<JfAccount>> queryJf = client.queryJf( userId_a);
		List<JfAccount> data= new ArrayList<>();
		//是否正确执行
		if(!queryJf.ok){
//		    错误信息
		    System.out.println(queryJf.text);
		}else{
	        	  data = queryJf.getData();
	        	 if(data.size()==0)
	        	     System.out.println("not jf");
	        	 else
	        	 for(JfAccount a:data)
	        	     System.out.println(String.format("用户%s 积分%s 在途积分%s", a.getUser()+a.acct_type , a.getJf(),a.getJfTODO()));
		}

		//接口：查询用户积分flow
		ResultDto<List<JfFlow>> queryFlow = client.queryJfFlow( userId_a,"2016-02-05","");
		//是否正确执行
		if(!queryFlow.ok){
//		    错误信息
		    System.out.println(queryFlow.text);
		}else{
	        	  List<JfFlow> flow = queryFlow.getData();
	        	 if(data.size()==0)
	        	     System.out.println("no jf flow");
	        	 else
	        	 for(JfFlow a:flow)
	        	     System.out.println(String.format("用户%s 积分%s 交易%s time%s", a.getUser(),a.jf,a.getTradeType().getName(), a.insertTime));
		}		
		
    }
    
    //为福利网提供的4个接口客户端测试
    public static void testApiClient() throws Exception{
	JfServiceClient client = new JfServiceClient();
	
	client.setMd5Key("abc343");
	
	//设置积分类型
	client.setAccountType( "hjf__consume");
	//设置 web service 服务器地址
	client.setUrlContext("http://localhost:8080/hg-hjf-jf");
//	client.setUrlContext("http://192.168.41.108:8083/hg-hjf-jf");

	//接口1：查询单个用户积分
	 String userId_a = "15305153869";
	ResultDto<List<JfAccount>> queryJf = client.queryJf( userId_a);
	
	List<JfAccount> data= new ArrayList<>();
	
	//是否正确执行
	if(!queryJf.ok){
//	    错误信息
	    System.out.println(queryJf.text);
	}else{
        	  data = queryJf.getData();
        	 if(data.size()==0)
        	     System.out.println("not jf");
        	 else
        	 for(JfAccount a:data)
        	     System.out.println(String.format("用户%s 积分%s 在途积分%s", a.getUser()+a.acct_type , a.getJf(),a.getJfTODO()));
	}
//	 接口2：查询多个用户积分
	 List<String> userLst=new ArrayList<>();
	 userLst.add(userId_a);
	 userLst.add("xinglj");
	 //data = client.queryJf( userLst).getData();
	 for(JfAccount a:data)
	     System.out.println(String.format("用户%s 积分%s 在途积分%s",a.getUser()+a.acct_type ,a.getJf(),a.getJfTODO()));
	 
//	 接口3：发放积分
//	System.out.println(client.inJf("a", 100, "发放福利"));
	// System.out.println(client.chargeJf("15305153869", 100, "发放福利"));
	 
	List<JfOrder> orders=new ArrayList<>();
	orders.add(new JfOrder("aa", 100, "福利"));
	orders.add(new JfOrder("ab", 100, "福利"));
//	System.out.println(client.inJfBatch(orders));
	 
//	 接口4：支付积分
	//ResultDto<String> outJf = client.outJf(userId_a, -1, "支付福利");
//	System.out.println(outJf);

//	接口5：撤销积分
//	System.out.println(client.cancelJf(userId_a, "0116b6978157426781f4e2a085aad67d", "撤销"));
//	接口6：用户消费
//	final ResultDto<String> costService = client.costService(userId_a, -200, "积分消费");
//	System.out.println(costService);
	
	//接口6：带积分电子券的用户消费
	final ResultDto<String> costService = client.costService("20160307a",userId_a, -200, "积分消费",new String[]{"hjf__grow"},new int[]{-8});
	System.out.println(costService);
	//批量撤销，按batchNo
	System.out.println(client.cancelBatch(userId_a, "20160307a", "batch撤销"));
    }
   
}
