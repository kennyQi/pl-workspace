package hsl.h5.base.utils;
import hsl.h5.base.weixin.TokenAndExpiresOfWx;
import hsl.h5.base.weixin.WxUserInfo;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
/**
 * @类功能说明：微信工具类
 * @类修改者：
 * @修改日期：2014年11月11日下午3:11:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年11月11日下午3:11:33
 *
 */
public class WxUtil {
	
	private WxUtil() {}
	/**
	 * @方法功能说明：添加自定义菜单（微信）
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月11日下午3:10:02
	 * @修改内容：
	 * @参数：@param accessToken
	 * @参数：@param menu
	 * @参数：@throws ClientProtocolException
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	public static void addCustomMenusToWx(String accessToken, String menu) throws ClientProtocolException, IOException {
		String url = String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s", accessToken);
		HttpClient client = new DefaultHttpClient();
		client = HttpClientWrap.wrapClient(client);
        HttpPost httpost = new HttpPost(url);
        httpost.setEntity(new StringEntity(menu,"utf-8"));
        HttpResponse response = client.execute(httpost);
        String responseContent = EntityUtils.toString(response.getEntity());
        if(!responseContent.equals("{\"errcode\":0,\"errmsg\":\"ok\"}")){
        	throw new RuntimeException(responseContent);
        }
	}
	/**
	 * @方法功能说明：删除自定义菜单（微信）
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月11日下午3:10:22
	 * @修改内容：
	 * @参数：@param accessToken
	 * @参数：@throws ClientProtocolException
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	public static void delCustomMenusToWx(String accessToken) throws ClientProtocolException, IOException{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s", accessToken);
		HttpClient client = new DefaultHttpClient();
		client = HttpClientWrap.wrapClient(client);
        HttpGet httpost = new HttpGet(url);
        HttpResponse response = client.execute(httpost);
        String responseContent = EntityUtils.toString(response.getEntity());
        if(!responseContent.equals("{\"errcode\":0,\"errmsg\":\"ok\"}")){
        	throw new RuntimeException(responseContent);
        }
	}
	/**
	 * @方法功能说明：获取微信access_token  access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。正常情况下access_token有效期为7200秒，重复获取将导致上次获取的access_token失效。
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月11日下午3:22:37
	 * @修改内容：
	 * @参数：@param appid
	 * @参数：@param secret
	 * @参数：@return
	 * @return:TokenAndExpiresOfWx
	 * @throws
	 */
	public static TokenAndExpiresOfWx getWxAccessTokenFromWx(String appid, String secret) {
		try {
			String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appid, secret);
			HttpClient client = new DefaultHttpClient();
			client = HttpClientWrap.wrapClient(client);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			String responseContent = EntityUtils.toString(response.getEntity());	
			TokenAndExpiresOfWx tokenAndExpires =JSON.parse(responseContent, TokenAndExpiresOfWx.class);
			return tokenAndExpires;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * @方法功能说明：获取用户的基本信息
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月11日下午3:22:23
	 * @修改内容：
	 * @参数：@param accessToken
	 * @参数：@param openid
	 * @参数：@return
	 * @参数：@throws ClientProtocolException
	 * @参数：@throws IOException
	 * @return:WxUserInfo
	 * @throws
	 */
	public static WxUserInfo getWxUserInfo(String accessToken, String openid) throws ClientProtocolException, IOException {
		String url = String.format("https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN", accessToken, openid);
		HttpClient client = new DefaultHttpClient();
		client = HttpClientWrap.wrapClient(client);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		String responseContent = EntityUtils.toString(response.getEntity(), "UTF-8");	
		try {
			return JSON.parse(responseContent, WxUserInfo.class);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
