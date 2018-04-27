//
//
//import java.io.IOException;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//
//public class TestXmlRtPnr {
//	
//	public static void main(String[] args) {
//		
////		HttpClient client = new HttpClient();
////		// 防止乱码
////		client.getParams().setHttpElementCharset("UTF-8");
////		client.getParams().setContentCharset("UTF-8");
////		
////		PostMethod post = new PostMethod("http://123.119.189.29:8009/AOIS/ysta.asmx/XmlSubmit");
////		post.setParameter("identity", "<?xml version=\"1.0\" encoding=\"utf-8\"?><Identity_1_0><Operator>zjhgkj</Operator><Pwd>zjhgkj123</Pwd><Terminal></Terminal><UserType>Platform</UserType></Identity_1_0>");
////		post.setParameter("request","<XmlRtPnr_1_1><Pnr>JXPD6E</Pnr><Office></Office><HostResult>Y</HostResult></XmlRtPnr_1_1>");
////		post.setParameter("filter", "");
////	
////		try {
////			client.executeMethod(post);
////			String result = post.getResponseBodyAsString();
////			System.out.println(result);
////			try {
////				Document document = DocumentHelper.parseText(result);
////				
////				Element root = document.getRootElement();
////				System.out.println(root.getText());
////				
////			} catch (DocumentException e) {
////			} 
////		} catch (HttpException e) {
////		} catch (IOException e) {
////		}
//		
//		HttpClient client = new HttpClient();
//		// 防止乱码
//		client.getParams().setHttpElementCharset("UTF-8");
//		client.getParams().setContentCharset("UTF-8");
//		
//		PostMethod post = new PostMethod("http://221.122.126.167:8080/aois/ysta.asmx/XmlSubmit");
//		post.setParameter("identity", "<?xml version=\"1.0\" encoding=\"utf-8\"?><Identity_1_0><Operator>zjhgkj</Operator><Pwd>zjhgkj123</Pwd><Terminal></Terminal><UserType>Platform</UserType></Identity_1_0>");
//		post.setParameter("request","<?xml version=\"1.0\"?><DeletePnr_1_0><PNR>JX3FL6</PNR><Office>BJS280</Office><CheckDZ>N</CheckDZ><OrderNo></OrderNo><SPID></SPID></DeletePnr_1_0>");
//		post.setParameter("filter", "");
//	
//		try {
//			client.executeMethod(post);
//			String result = post.getResponseBodyAsString();
//			System.out.println("result========"+result);
//
//		} catch (HttpException e) {
//		} catch (IOException e) {
//		}
//	
//	}
//	
//}
