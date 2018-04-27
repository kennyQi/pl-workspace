package hg.jf.client;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class FileUploadClientDemo {
	
	
	public void submitPostUpload(String url,String appId,String filePath){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			FileBody uploadFile = new FileBody( new File(filePath));
			StringBody paramAppId = new StringBody(appId);
			Charset charset = Charset.forName("UTF-8"); 
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,
	                null, Charset.forName(HTTP.UTF_8));
			//设置传递的参数
			reqEntity.addPart("file",uploadFile);
			reqEntity.addPart("appId",paramAppId);
			httpPost.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(httpPost);
			
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == HttpStatus.SC_OK){
				System.out.println("服务器正常响应...");
				HttpEntity resEntity = response.getEntity(); 
				byte[] buss = EntityUtils.toByteArray(resEntity);
				//System.out.println(EntityUtils.toString(resEntity));//httpclient自带的工具类读取返回数据  
				System.out.println(new String(buss,"utf-8"));
                System.out.println(resEntity.getContent());     
                EntityUtils.consume(resEntity);  
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 httpClient.getConnectionManager().shutdown();   
		}
	}
	
	
	public static void main(String[] args) {
		FileUploadClientDemo demo = new FileUploadClientDemo();
		demo.submitPostUpload("http://localhost:8888/hg-hjf-admin/fileUpload","123456","E:\\HG2.0\\中信乐益通积分核心与总线系统项目--总体设计说明书v1.0.docx");
		
	}

}
