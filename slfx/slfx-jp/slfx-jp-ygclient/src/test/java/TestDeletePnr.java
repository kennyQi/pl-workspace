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
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//
//import slfx.jp.command.client.ABEDeletePnrCommand;
//import slfx.jp.pojo.dto.supplier.abe.ABEDeletePnrDTO;
//import slfx.yg.open.inter.YGFlightService;
//import slfx.yg.open.utils.YGXmlUtil;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:junit-test-spring-service.xml" })
//public class TestDeletePnr {
//	
//	@Autowired
//	private YGXmlUtil ygXmlUtil;
//	
//	@Autowired
//	private YGFlightService ygFlightService;
//	
//	@Test
//	public void testParseXML(){
//		//String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">&lt;?xml version=\"1.0\" encoding=\"gb2312\" ?&gt;&lt;ErrorInfo_1_0&gt;&lt;MethodName&gt;DeletePnr_1_0&lt;/MethodName&gt;&lt;Operator&gt;zjhgkj&lt;/Operator&gt;&lt;CallTime&gt;2014/10/17 10:37:36&lt;/CallTime&gt;&lt;Code&gt;10021&lt;/Code&gt;&lt;Content&gt; PNR 已经被删除。&lt;/Content&gt;&lt;/ErrorInfo_1_0&gt;</string>";
//		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">&lt;?xml version=\"1.0\" ?&gt;&lt;DeletePnr_1_0&gt;&lt;Status&gt;Y&lt;/Status&gt;&lt;Msg&gt;删除成功&lt;/Msg&gt;&lt;/DeletePnr_1_0&gt;</string>";
//		
//		result = result.replace("&lt;", "<");
//		result = result.replace("&gt;", ">");
//		result = result.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">", "");
//		result = result.replace("</string>", "");
//		result = result.replace("<?xml version=\"1.0\" encoding=\"gb2312\" ?>", "");
//		System.out.println("result======="+result);
//		Document document = null;
//		try {
//			document = DocumentHelper.parseText(result);
//			Element element = document.getRootElement();
//			String code = element.selectSingleNode("/ErrorInfo_1_0/Code").getText();
//			System.out.println("code=========="+code);
//			
//			String temp = element.selectSingleNode("/ErrorInfo_1_0").getText();
//			System.out.println("error========"+temp.indexOf("<ErrorInfo_1_0>"));
//			
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	@Test
//	public void testAdminDeletePnr(){
//		ABEDeletePnrCommand deleteCommand=new ABEDeletePnrCommand();
//		deleteCommand.setCommandId("123");
//		deleteCommand.setPnr("JZ4ZSG");
//		ABEDeletePnrDTO abeDeletePnrDTO = ygFlightService.deletePnr(deleteCommand);
//		System.out.println("==========="+JSON.toJSONString(abeDeletePnrDTO));
//	}
//	
//	@Test
//	public void testHttp() {
//		
//		HttpClient client = new HttpClient();
//		// 防止乱码
//		client.getParams().setHttpElementCharset("UTF-8");
//		client.getParams().setContentCharset("UTF-8");
//		
//		PostMethod post = new PostMethod("http://221.122.126.167:8080/aois/ysta.asmx/XmlSubmit");
//		post.setParameter("identity", "<?xml version=\"1.0\" encoding=\"utf-8\"?><Identity_1_0><Operator>zjhgkj</Operator><Pwd>zjhgkj123</Pwd><Terminal></Terminal><UserType>Platform</UserType></Identity_1_0>");
//		post.setParameter("request","<?xml version=\"1.0\"?><DeletePnr_1_0><PNR>HX78RG</PNR><Office>BJS280</Office><CheckDZ>N</CheckDZ><OrderNo></OrderNo><SPID></SPID></DeletePnr_1_0>");
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
//		
//		
//		
//	}
//	
//}
