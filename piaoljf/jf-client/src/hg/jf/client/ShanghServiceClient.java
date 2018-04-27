/**
 * @JfSeviceClient.java Create on 2015年1月27日下午4:12:19
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf.client;

import hg.common.page.Pagination;
import hgtech.jfaccount.JfAccount;
import hgtech.jfcal.model.CalResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSON;

import hg.jf.client.ResultDto;
/**
 * @类功能说明：提供给商户访问积分web service的客户端(委托型商户使用）
 * @类修改者：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @version：
 */
public class ShanghServiceClient extends ServiceClient {

    private static final String JF_COMMIT_SERVICE = "/jfCommitService";
	private String urlContext = "http://localhost:8082/hg-hjf-jf";
    private String appId; 
	private String accountType ="hjf__consume";
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
	String url = urlContext + JF_COMMIT_SERVICE + "/queryjf?pageNum=" + 1 + "&numPerPage=" + 20 + "&code=" + userId+"&appId="+appId;
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
     * 
     * @return may null
     * @throws Exception
     */
    public JfAccount queryAppAccount() throws Exception{
    	final ResultDto<List<JfAccount>> queryJf = queryJf(appId);
    	if(queryJf.isOk()){
    		for(JfAccount a: queryJf.data){
    			return a;
    		}
    		return null;
    	}else
    		throw new RuntimeException(queryJf.getText());
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
	String url = urlContext + JF_COMMIT_SERVICE + "/queryjf?pageNum=" + 1 + "&numPerPage=" + Integer.MAX_VALUE + "&code="
		+ userIds+"&appId="+appId;
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

    public ResultDto<String> chargeJf(String userId, int jf, String remark)
			throws Exception {
		String inout = JF_COMMIT_SERVICE + "/charge?"+"appId="+appId;
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
    
    public ResultDto<String> chargeJfBatch(List<JfOrder> orders) throws Exception {
	String inout =  JF_COMMIT_SERVICE + "/charge?"+"appId="+appId ;
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
     * @param urlContext
     *            the urlContext to set
     */
    public void setUrlContext(String urlContext) {
	this.urlContext = urlContext;
    }

    public static void main(String[] args) throws Exception {
	ShanghServiceClient client = new ShanghServiceClient();
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
	 * 仅调用规则引擎计算
	 * @param t
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public Collection<CalResult> calJf(PiaolTrade t) throws MalformedURLException, UnsupportedEncodingException, IOException {
		String data = JSON.toJSONString(t);
		String url = urlContext + JF_COMMIT_SERVICE + "/caljf?"+"appId="+appId;
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
		String url = urlContext + JF_COMMIT_SERVICE + "/caljfNoSave?"+"appId="+appId;
		String s = callWebServicePost(url, data);
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
		String url = urlContext + JF_COMMIT_SERVICE + "/calcommitjf?"+"appId="+appId;
		String s = callWebServicePost(url, data);
		String okKey="计算入账成功";
		ResultDto<String> ret = new ResultDto<>();
		ret.ok = s.contains(okKey);
		ret.text=s;
		return ret;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
