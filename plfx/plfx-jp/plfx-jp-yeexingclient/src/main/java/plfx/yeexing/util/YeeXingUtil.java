package plfx.yeexing.util;

//import hg.log.util.HgLogger;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

/**
 * 
 * @类功能说明：易行天下发访问配置工具类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月29日下午4:50:29
 * @版本：V1.0
 *
 */
@Component
public class YeeXingUtil {
	
	/**
	 * 合作伙伴用户名
	 */
	private String userName;
	
	private String key;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
