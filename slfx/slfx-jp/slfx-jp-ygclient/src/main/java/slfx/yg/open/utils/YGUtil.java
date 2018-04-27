package slfx.yg.open.utils;

//import hg.log.util.HgLogger;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

/**
 * 
 * @类功能说明：易购发访问配置工具类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:20:22
 * @版本：V1.0
 *
 */
@Component
public class YGUtil {
	
//	@Autowired
//	private HgLogger hgLogger;
	
	/**
	 * 运价云访问地址
	 */
	private String yjUrl;
	
	/**
	 * 运价云用户名
	 */
	private String yjUserName;
	
	/**
	 * 运价云密码
	 */
	private String yjPwd;

	/**
	 * 采购云用户名
	 */
	private String cgUserName;
	
	/**
	 * 采购云密码
	 */
	private String cgPwd;
	
	/**
	 * 采购云访问地址
	 */
	private String cgUrl;
	
	private String office;

	public String getYjUrl() {
		return yjUrl;
	}

	public void setYjUrl(String yjUrl) {
		this.yjUrl = yjUrl;
	}

	public String getYjUserName() {
		return yjUserName;
	}

	public void setYjUserName(String yjUserName) {
		this.yjUserName = yjUserName;
	}

	public String getYjPwd() {
		return yjPwd;
	}

	public void setYjPwd(String yjPwd) {
		this.yjPwd = yjPwd;
	}

	public String getCgUserName() {
		return cgUserName;
	}

	public void setCgUserName(String cgUserName) {
		this.cgUserName = cgUserName;
	}

	public String getCgPwd() {
		return cgPwd;
	}

	public void setCgPwd(String cgPwd) {
		this.cgPwd = cgPwd;
	}

	public String getCgUrl() {
		return cgUrl;
	}

	public void setCgUrl(String cgUrl) {
		this.cgUrl = cgUrl;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
	
	
	/**
	 * 获取 YJ 运价接口部分HTTP post方法对象  设置身份认证参数
	 * @return
	 */
	public PostMethod getYJPostMethod(){
		PostMethod post = new PostMethod(this.yjUrl);
		post.setParameter("identity", "<?xml version=\"1.0\" encoding=\"utf-8\"?><Identity_1_0><Operator>"+this.yjUserName+"</Operator><Pwd>"+this.yjPwd+"</Pwd><Terminal></Terminal><UserType>Platform</UserType></Identity_1_0>");
		//post.setParameter("identity", "<?xml version=\"1.0\" encoding=\"utf-8\"?><Identity_1_0><Operator>zjhgkj</Operator><Pwd>zjhgkj123</Pwd><Terminal></Terminal><UserType>Platform</UserType></Identity_1_0>");
		return post;
	}
	
	/**
	 * 获取 CG采购接口部分HTTP post方法对象  设置身份认证参数
	 * @return
	 */
	public PostMethod getCGPostMethod(){
		PostMethod post = new PostMethod(this.cgUrl);
		post.setParameter("identity", "<?xml version=\"1.0\" encoding=\"utf-8\"?><Identity_1_0><Operator>"+this.cgUserName+"</Operator><Pwd>"+this.cgPwd+"</Pwd></Identity_1_0>");
		
		System.out.println("identity========="+"<?xml version=\"1.0\" encoding=\"utf-8\"?><Identity_1_0><Operator>"+this.cgUserName+"</Operator><Pwd>"+this.cgPwd+"</Pwd></Identity_1_0>");
		return post;
	}
	
	
	public String sendPost(PostMethod post){
		
		HttpClient client = new HttpClient();
		client.getParams().setHttpElementCharset("UTF-8");
		client.getParams().setContentCharset("UTF-8");
		try {
			client.executeMethod(post);
			String result = post.getResponseBodyAsString();
			//System.out.println("result========="+result);
			return result;
		} catch (HttpException e) {
			//hgLogger.error("yangkang", e.getMessage());
			return null;
		} catch (IOException e) {
			//hgLogger.error("yangkang", e.getMessage());
			return null;
		} finally {
			post.releaseConnection();//关闭连接
		}
	}
	
}
