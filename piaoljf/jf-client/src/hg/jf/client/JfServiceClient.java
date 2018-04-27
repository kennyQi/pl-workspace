/**
 * @JfSeviceClient.java Create on 2015年1月27日下午4:12:19
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf.client;

import hg.common.page.Pagination;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfFlow;
import hgtech.jfcal.model.CalResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import hg.jf.client.ResultDto;
/**
 * @类功能说明：访问积分web service的客户端(内部使用）
 * @类修改者：
 * @修改日期：2015年1月27日下午4:12:19
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月27日下午4:12:19
 * @version：
 */
public class JfServiceClient extends ServiceClient {

    private String urlContext = "http://localhost:8082/hg-hjf-jf";
    private String urlAdmin = "http://localhost:8080/hjf-admin";
    public String getUrlAdmin() {
		return urlAdmin;
	}

	public void setUrlAdmin(String urlAdmin) {
		this.urlAdmin = urlAdmin;
	}

	private String accountType = "weal__jf";
    /**
     * 
     * @方法功能说明：查询积分
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午4:42:01
     * @version：
     * @修改内容：
     * @参数：@param pageNum
     * @参数：@param numPerPage
     * @参数：@param userId
     * @参数：@return
     * @参数：@throws Exception
     * @return:ResultDto<List<JfAccount>>
     * @throws
     */
    public ResultDto<List<JfAccount>> queryJf(String userId) throws Exception {
	String url = urlContext + "/service/queryjf?pageNum=" + 1 + "&numPerPage=" + 20 + "&code=" + userId;
	String s = callWebServiceGet(url, userId);
	ResultDto<List<JfAccount>> ret = new ResultDto<>();
	if (s.startsWith("{")) {
	    ret.setOk(true);
	    Pagination obj = JSON.parseObject(s, Pagination.class);
	    List<JfAccount> list = (List<JfAccount>) (obj.getList());
	    ret.setData(list);
	} else {
	    ret.setOk(false);
	    ret.setText(s);
	}
	return ret;
    }

    /**
     * 查询积分的流水
     * @param userId
     * @return
     * @throws Exception
     */
    public ResultDto<List<JfFlow>> queryJfFlow(String userId,String fromDate10,String toDate10) throws Exception {
		String url = String.format(urlContext + "/service/queryflow?pageNum=1&numPerPage="+100000 
				+"&code="+userId+"&startDate=%s&endDate=%s"	,fromDate10,toDate10);

		String s = callWebServiceGet(url, userId);
		ResultDto<List<JfFlow>> ret = new ResultDto<>();
		if (s.startsWith("{")) {
			ret.setOk(true);
			Pagination obj = JSON.parseObject(s,Pagination.class); 
			List<JfFlow> list = (List<JfFlow>) (obj.getList());
			ret.setData(list);
		} else {
			ret.setOk(false);
			ret.setText(s);
		}
		return ret;
    }
    
    /**
     * 
     * @方法功能说明：查询积分 多用户
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午4:49:00
     * @version：
     * @修改内容：
     * @参数：@param pageNum
     * @参数：@param numPerPage
     * @参数：@param userId
     * @参数：@return
     * @参数：@throws Exception
     * @return:ResultDto<List<JfAccount>>
     * @throws
     */
    public ResultDto<List<JfAccount>> queryJf(List<String> userId) throws Exception {
	String userIds = "";
	for (int i = 0; i < userId.size(); i++) {
	    userIds += userId.get(i);
	    if (i < userId.size() - 1)
		userIds += ",";
	}
	String url = urlContext + "/service/queryjf?pageNum=" + 1 + "&numPerPage=" + Integer.MAX_VALUE + "&code="
		+ userIds;
	String s = callWebServiceGet(url, userIds);
	ResultDto<List<JfAccount>> ret = new ResultDto<>();
	if (s.startsWith("{")) {
	    ret.setOk(true);
	    Pagination obj = JSON.parseObject(s, Pagination.class);
	    List<JfAccount> list = (List<JfAccount>) (obj.getList());
	    ret.setData(list);
	} else {
	    ret.setOk(false);
	    ret.setText(s);
	}
	return ret;
    }

    /**
     * 
     * @方法功能说明：转入积分
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午5:14:03
     * @version：
     * @修改内容：
     * @参数：@param userId
     * @参数：@param jf
     * @参数：@param remark
     * @参数：@return
     * @参数：@throws Exception
     * @return:ResultDto<String>
     * @throws
     */
    public ResultDto<String> inJf(String userId, int jf, String remark) throws Exception {
    	return chargeJf(userId, jf, remark);
//	boolean isIn = true;
//	return inoutJf(isIn, userId, jf, remark);
    }

	public ResultDto<String> chargeJf(String userId, int jf, String remark)
			throws Exception {
		String inout = "/service/charge";
		String data = "[{\"@type\":\"hgtech.jfaccount.AdjustBean\",\"accountTypeId\":\"%s\",\"jf\":%d,\"remark\":\"%s\",\"userCode\":\"%s\"}]";
		String urlf = urlContext + inout /* + "?data=" + data */;
		data = String.format(data, accountType, jf, remark, userId);
		String s = callWebServicePost(urlf, data);
		ResultDto<String> ret = new ResultDto<>();
		final String text = "已提交，请稍后查询。";
		if (s.equalsIgnoreCase(text)) {
			ret.setOk(true);
			ret.setText(text);
		} else {
			ret.setOk(false);
			ret.setText(s);
		}
		return ret;
	}
    
    /**
     * 
     * @方法功能说明：批量转入积分、
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午5:14:03
     * @version：
     * @修改内容：
     * @参数：@param userId
     * @参数：@param jf
     * @参数：@param remark
     * @参数：@return
     * @参数：@throws Exception
     * @return:ResultDto<String>
     * @throws
     */
    public ResultDto<String> inJfBatch(List<JfOrder> orders) throws Exception {
	boolean isIn = true;
	return inoutJfBatch(isIn, orders);
    }
    
    public ResultDto<String> chargeJfBatch(List<JfOrder> orders) throws Exception {
	String inout =  "/service/charge" ;
	String data = "[";
	for (JfOrder o : orders) {
	    if (data.length() > 0)
		data += ",";
	    data += String
		    .format("{\"@type\":\"hgtech.jfaccount.AdjustBean\",\"accountTypeId\":\"%s\",\"jf\":%d,\"remark\":\"%s\",\"userCode\":\"%s\"}",
			    accountType, o.jf, URLEncoder.encode(o.remark, charsetName), o.userId);
	}
	data += "]";
	String url = urlContext + inout /* + "?data="+data */;
	String s = callWebServicePost(url, data);
	ResultDto<String> ret = new ResultDto<>();
	if (s.equalsIgnoreCase("已提交，请稍后查询。")) {
	    ret.setOk(true);
	    ret.setText("已提交，请稍后查询。");
	} else {
	    ret.setOk(false);
	    ret.setText(s);
	}
	return ret;
    }
    
    /**
     * 
     * @方法功能说明：转出积分、扣减
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午5:14:08
     * @version：
     * @修改内容：
     * @参数：@param userId
     * @参数：@param jf
     * @参数：@param remark
     * @参数：@return
     * @参数：@throws Exception
     * @return:ResultDto<String>
     * @throws
     */
    public ResultDto<String> outJf(String userId, int jf, String remark) throws Exception {
	boolean isIn = false;
	return inoutJf(isIn, userId, jf, remark);
    }

    /**
     * 
     * @方法功能说明：批量转出积分、扣减
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午5:14:03
     * @version：
     * @修改内容：
     * @参数：@param userId
     * @参数：@param jf
     * @参数：@param remark
     * @参数：@return
     * @参数：@throws Exception
     * @return:ResultDto<String>
     * @throws
     */
    public ResultDto<String> outJfBatch(List<JfOrder> orders) throws Exception {
	boolean isIn = false;
	return inoutJfBatch(isIn, orders);
    }

    /**
     * @方法功能说明：单用户的积分发放、支付
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午5:12:24
     * @version：
     * @修改内容：
     * @参数：@param isIn
     * @参数：@param userId
     * @参数：@param jf
     * @参数：@param remark
     * @参数：@return
     * @参数：@throws MalformedURLException
     * @参数：@throws IOException
     * @参数：@throws UnsupportedEncodingException
     * @return:ResultDto<String>
     * @throws
     */
    ResultDto<String> inoutJf(boolean isIn, String userId, int jf, String remark) throws MalformedURLException,
	    IOException, UnsupportedEncodingException {
	String inout = isIn ? "/service/transferin" : "/service/transferout";
	String data = "[{\"@type\":\"hgtech.jfaccount.AdjustBean\",\"accountTypeId\":\"%s\",\"jf\":%d,\"remark\":\"%s\",\"userCode\":\"%s\"}]";
	String urlf = urlContext + inout /* + "?data=" + data */;
	data = String.format(data, accountType, jf, remark, userId);
	String s = callWebServicePost(urlf, data);
	ResultDto<String> ret = new ResultDto<>();
	if (s.equalsIgnoreCase("已提交，请稍后查询。")) {
	    ret.setOk(true);
	} else {
	    ret.setOk(false);
	    ret.setText(s);
	}
	return ret;
    }

    /**
     * @方法功能说明：批量的积分发放，支付
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午5:12:24
     * @version：
     * @修改内容：
     * @参数：@param isIn
     * @参数：@param userId
     * @参数：@param jf
     * @参数：@param remark
     * @参数：@return
     * @参数：@throws MalformedURLException
     * @参数：@throws IOException
     * @参数：@throws UnsupportedEncodingException
     * @return:ResultDto<String>
     * @throws
     */
    ResultDto<String> inoutJfBatch(boolean isIn, List<JfOrder> orders) throws MalformedURLException, IOException,
	    UnsupportedEncodingException {
	String inout = isIn ? "/service/transferin" : "/service/transferout";
	String data = "[";
	for (JfOrder o : orders) {
	    if (data.length() > 0)
		data += ",";
	    data += String
		    .format("{\"@type\":\"hgtech.jfaccount.AdjustBean\",\"accountTypeId\":\"%s\",\"jf\":%d,\"remark\":\"%s\",\"userCode\":\"%s\"}",
			    accountType, o.jf, URLEncoder.encode(o.remark, charsetName), o.userId);
	}
	data += "]";
	String url = urlContext + inout /* + "?data="+data */;
	String s = callWebServicePost(url, data);
	ResultDto<String> ret = new ResultDto<>();
	if (s.equalsIgnoreCase("已提交，请稍后查询。")) {
	    ret.setOk(true);
	} else {
	    ret.setOk(false);
	    ret.setText(s);
	}
	return ret;
    }

    /**
     * @return the urlContext
     */
    public String getUrlContext() {
	return urlContext;
    }

    /**
     * @param urlContext
     *            the urlContext to set
     */
    public void setUrlContext(String urlContext) {
	this.urlContext = urlContext;
    }

    public static void main(String[] args) throws Exception {
	JfServiceClient client = new JfServiceClient();
	client.accountType = "piaol__consume";
	client.setUrlContext("http://localhost:8080/pljf-admin");

	// System.out.println(client.inJf("b", 100, "发放福利"));

	// 查询积分
	List<JfAccount> data = client.queryJf("a,b").getData();
	for (JfAccount a : data)
	    System.out.println(String.format("用户%s 积分%s 在途积分%s", a.getUser(), a.getJf(), a.getJfTODO()));

	// System.out.println(client.inJf("a", 100, "发放福利"));
	// System.out.println(client.outJf("a", -100, "发放福利"));
    }

    /**
     * @return the accountType
     */
    public String getAccountType() {
	return accountType;
    }

    /**
     * @param accountType
     *            the accountType to set
     */
    public void setAccountType(String accountType) {
	this.accountType = accountType;
    }

    /**
     * 
     * @方法功能说明：积分撤销，增加
     * @修改者名字：zhaoqf
     * @修改时间：2015年7月28日下午11:30:01
     * @version：
     * @修改内容：
     * @参数：@param flowid
     * @参数：@return
     * @参数：@throws Exception
     * @return:ResultDto<String>
     * @throws
     */
	public ResultDto<String> cancelJf(String userId, String flowid, String remark) throws Exception {
		boolean isIn = true;
		return cancelInJf(isIn, userId, flowid, remark);
	}

    /**
     * @方法功能说明：用户的积分撤销
     * @修改者名字：zhaoqf
     * @修改时间：2015年7月28日下午5:12:24
     * @version：
     * @修改内容：
     * @参数：@param isIn
     * @参数：@param flowid
     * @参数：@return
     * @参数：@throws MalformedURLException
     * @参数：@throws IOException
     * @参数：@throws UnsupportedEncodingException
     * @return:ResultDto<String>
     * @throws
     */
	ResultDto<String> cancelInJf(boolean isIn,String userId, String flowid, String remark) throws MalformedURLException, UnsupportedEncodingException, IOException {
		String inout = "/service/cancelJf";
		String data = "[{\"@type\":\"hgtech.jfaccount.AdjustBean\",\"accountTypeId\":\"%s\",\"tradeFlowId\":\"%s\",\"remark\":\"%s\",\"userCode\":\"%s\"}]";
		String url = urlContext + inout;
		data = String.format(data, accountType, flowid, remark, userId);
		String s = callWebServicePost(url, data);
		ResultDto<String> ret = new ResultDto<>();
		if (s.equalsIgnoreCase("已提交，请稍后查询。")) {
		    ret.setOk(true);
		} else {
		    ret.setOk(false);
		    ret.setText(s);
		}
		return ret;
	}
	
	/**
	 * 按batchNo撤销积分
	 * @param userId
	 * @param batchNo
	 * @param remark
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	ResultDto<String> cancelBatch( String userId, String batchNo, String remark) throws MalformedURLException, UnsupportedEncodingException, IOException {
		String inout = "/service/cancelBatch";
		String data = "[{\"@type\":\"hgtech.jfaccount.AdjustBean\",\"batchNo\":\"%s\",\"remark\":\"%s\",\"userCode\":\"%s\"}]";
		String url = urlContext + inout;
		data = String.format(data,  batchNo, remark, userId);
		String s = callWebServicePost(url, data);
		ResultDto<String> ret = new ResultDto<>();
		if (s.equalsIgnoreCase("已提交，请稍后查询。")) {
		    ret.setOk(true);
		} else {
		    ret.setOk(false);
		    ret.setText(s);
		}
		return ret;
	}	
	
	/**
	 * 仅调用规则引擎计算
	 * @param t
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public Collection<CalResult> calJf(PiaolTrade t) throws MalformedURLException, UnsupportedEncodingException, IOException {
		String data = JSON.toJSONString(t);
		String url = urlAdmin + "/service/caljf";
		String s = callWebServicePost(url, data);
		Collection<CalResult> ret;
		ret = JSON.parseArray(s, CalResult.class);
		
		return ret;
	}
	/**
	 * 仅调用规则引擎计算,而且不记cal_flow
	 * @param t
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public Collection<CalResult> calJfNoSave(PiaolTrade t) throws MalformedURLException, UnsupportedEncodingException, IOException {
		String data = JSON.toJSONString(t);
		String url = urlAdmin + "/service/caljfNoSave";
		String s = callWebServicePost(url, data);
		if(s.contains("<!DOCTYPE"))
			//必须是jason返回，否则认为错误
			throw new RuntimeException(s);
		Collection<CalResult> ret;
		ret = JSON.parseArray(s, CalResult.class);
		
		return ret;
	}
	/**
	 * 调用规则引擎计算,并将结果积分入账
	 * @param t
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public ResultDto<String> calAndCommitJf(PiaolTrade t) throws MalformedURLException, UnsupportedEncodingException, IOException {
		String data = JSON.toJSONString(t);
		String url = urlAdmin + "/service/calcommitjf";
		String s = callWebServicePost(url, data);
		String okKey="计算入账成功";
		ResultDto<String> ret = new ResultDto<>();
		ret.ok = s.contains(okKey);
		ret.text=s;
		return ret;
	}
	
	/**
	 * 
	 * @方法功能说明：积分消费，扣除
	 * @修改者名字：zhaoqf
	 * @修改时间：2015年7月28日下午11:30:01
	 * @version：
	 * @修改内容：
	 * @参数：@param userId
	 * @参数：@param jf
	 * @参数：@param remark
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:ResultDto<String>
	 * @throws
	 */
	public ResultDto<String> costService(String userId, int jf, String remark) throws Exception {
		return cost(userId, jf, remark);
	}

	/**
	 * 和电子券一起使用的扣减
	 * @param userId
	 * @param jf
	 * @param remark
	 * @param otherAccountType 优惠券的账户类型和积分
	 * @param otherJf
	 * @param batchNo 
	 * @return
	 * @throws Exception
	 */
	public ResultDto<String> costService( String batchNo,String userId, int jf, String remark,String[] otherAccountType,int[] otherJf) throws Exception {
		return cost(batchNo,userId, jf, remark,otherAccountType,otherJf);
	}
	
	/**
	 * 
	 * @方法功能说明：用户的积分消费
	 * @修改者名字：zhaoqf
	 * @修改时间：2015年7月28日下午11:30:01
	 * @version：
	 * @修改内容：
	 * @参数：@param userId
	 * @参数：@param jf
	 * @参数：@param remark
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:ResultDto<String>
	 * @throws
	 */
	private ResultDto<String> cost(String userId, int jf, String remark) throws MalformedURLException, UnsupportedEncodingException, IOException {
		String inout = "/service/exchangejf";
		String data = "[{\"@type\":\"hgtech.jfaccount.AdjustBean\",\"accountTypeId\":\"%s\",\"jf\":%d,\"remark\":\"%s\",\"userCode\":\"%s\"}]";
		String url = urlContext + inout;
		data = String.format(data, accountType, jf, remark, userId);
		String s = callWebServicePost(url, data);
		ResultDto<String> ret = new ResultDto<>();
		if (s.contains("已提交，请稍后查询。")) {
			ret.setOk(true);
			if(s.contains("flowid:")){
				ret.setData(s.split(":")[1]);
			}
			
		} else {
			ret.setOk(false);
			ret.setText(s);
		}
		return ret;
	}
	
	/**
	 * 带 积分电子券的扣减
	 * @param userId
	 * @param jf
	 * @param remark
	 * @param otherAccountType 积分电子券的账户类型和积分数值
	 * @param otherJf
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private ResultDto<String> cost(String batchNo,String userId, int jf, String remark,String[] otherAccountType,int[] otherJf) throws MalformedURLException, UnsupportedEncodingException, IOException {
		String inout = "/service/exchangejf";
		String data = String.format("[" 
						+"{\"@type\":\"hgtech.jfaccount.AdjustBean\",\"accountTypeId\":\"%s\",\"jf\":%d,\"remark\":\"%s\",\"userCode\":\"%s\",\"batchNo\":\"%s\"}"
						, accountType, jf, remark, userId,batchNo);
				for(int i=0;i<otherAccountType.length;i++){
					data = data + String.format(",{\"@type\":\"hgtech.jfaccount.AdjustBean\",\"accountTypeId\":\"%s\",\"jf\":%d,\"remark\":\"%s\",\"userCode\":\"%s\",\"batchNo\":\"%s\"}"
							,otherAccountType[i],otherJf[i],remark,userId,batchNo);
				}
				data +="]";
		String url = urlContext + inout;
		String s = callWebServicePost(url, data);
		ResultDto<String> ret = new ResultDto<>();
		if (s.contains("已提交，请稍后查询。")) {
			ret.setOk(true);
			if(s.contains("flowid:")){
				ret.setData(s.split(":")[1]);
			}
			
		} else {
			ret.setOk(false);
			ret.setText(s);
		}
		return ret;
	}
	/**
	 * 计算消费奖励积分
	 * @param trade
	 * @return
	 * @throws Exception
	 */
	public ResultDto<String> jfRuleCost(PiaolTrade trade) throws Exception {
		return ruleCost(trade);
	}

	/**
	 * 计算消费奖励积分
	 * @param trade
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private ResultDto<String> ruleCost(PiaolTrade trade) throws MalformedURLException, UnsupportedEncodingException, IOException {
		String inout = "/service/calcommitjf";
		String data = JSON.toJSONString(trade);
		String url = urlAdmin + inout;
		data = String.format(data);
		String s = callWebServicePost(url, data);
		ResultDto<String> ret = new ResultDto<>();
		String[] split = s.split("。");
		if (split[0].equalsIgnoreCase("计算入账成功")) {
			ret.setOk(true);
		} else {
			ret.setOk(false);
			ret.setText(s);
		}
		return ret;
	}

	/**
	 * 计算签到奖励
	 * @param trade
	 * @return
	 * @throws Exception
	 */
	public ResultDto<String> jfRuleSign(PiaolTrade trade) throws Exception {
		return ruleCost(trade);
	}

}
