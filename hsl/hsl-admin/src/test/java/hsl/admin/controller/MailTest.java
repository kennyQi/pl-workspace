//package hsl.admin.controller;
//
//import freemarker.template.Configuration;
//import freemarker.template.DefaultObjectWrapper;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import hg.system.common.util.MailUtil;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.Reader;
//import java.io.StringReader;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
//public class MailTest {
//	
//	@Test
//	public void test() throws Exception{
//		// 创建根哈希表
//		Map<String,Object> root = new HashMap<String,Object>();
//		// 在根中放入字符串"user"
//		root.put("user", "Big Joe");
//		// 为"latestProduct"创建哈希表
//		Map<String,Object> latest = new HashMap<String,Object>();
//		// 将它添加到根哈希表中
//		root.put("latestProduct", latest);
//		//  在 latest 中放置"url"和"name" 
//		latest.put("url", "products/greenmouse.html");
//		latest.put("name", "green mouse");
//		latest.put("createTime",new Date());
//		
//		
//		MailUtil mm = MailUtil.getInstance();
//		String[] ll = {"1326921249@qq.com","1326000000@qq.com"};
//		
//		boolean bo =mm.sendTemplateMail1("smtp.qq.com","1940421482","BINPDAleftup","内网测试2",null,null, "1940421482@qq.com", Arrays.asList(ll));
////		boolean bo =mm.sendMail1("smtp.qq.com","1940421482","BINPDAleftup","内网测试2","123","1940421482@qq.com","1326921249@qq.com");
//		
////		
////		HgMail m1=new HgMail();
////		m1.setHost("smtp.qq.com");
////		m1.setUserName("1940421482");
////		m1.setPassword("BINPDAleftup");
////		m1.setTitle("内网测试001");
////		m1.setContent("123132");
////		m1.setSender("1940421482@qq.com");
////		m1.setAccept(Arrays.asList(ll));
////		
////		HgMail m2=new HgMail();
////		m2.setHost("smtp.qq.com");
////		m2.setUserName("1940421482");
////		m2.setPassword("BINPDAleftup");
////		m2.setTitle("内网测试3");
////		m2.setContent("123132");
////		m2.setSender("1940421482@qq.com");
////		m2.setAccept(Arrays.asList(ll));
////		
////		HgMail[] arr = {m1,m2};
////		
////		boolean bo = mm.sendMailList(Arrays.asList(arr), false);
////		
//		System.out.println("+++++++++"+bo);
//	}
//	
//	@Test
//	public void freeTest(){
//		// 创建根哈希表
//		Map<String,Object> root = new HashMap<String,Object>();
//		// 在根中放入字符串"user"
//		root.put("user", "Big Joe");
//		// 为"latestProduct"创建哈希表
//		Map<String,Object> latest = new HashMap<String,Object>();
//		// 将它添加到根哈希表中
//		root.put("latestProduct", latest);
//		//  在 latest 中放置"url"和"name" 
//		latest.put("url", "products/greenmouse.html");
//		latest.put("name", "green mouse");
//		latest.put("createTime",new Date());
//		
//		
//		
//		
//		InputStream in = this.getClass().getClassLoader().getResourceAsStream("freemarker.properties");
//		
//		try {
//			Configuration cfg = new Configuration();
//			cfg.setObjectWrapper(new DefaultObjectWrapper());//指定模板如何检索数据模型
//			cfg.setSettings(in);
//			
//			System.out.println("+++++++++++++++\n\n\n");
//			
//			
//			String ss = "<!DOCTYPE HTML><html><head><title>My JSP</title><meta http-equiv=\"pragma\" content=\"no-cache\"><meta http-equiv=\"cache-control\" content=\"no-cache\"></head><body>${user}<br/>${latestProduct.url}<br/>${latestProduct.name}<br/>${latestProduct.createTime?string('yyyy-MM-dd')}</body></html>";
//			
//			Reader read = new StringReader(ss);
//			Template template = new Template(null,read,cfg,"UTF-8");
//			
////			Writer out = new OutputStreamWriter(System.out);//简单，标准输出
////			template.process(root, out);
////			out.flush();
//			
//			String resu = FreeMarkerTemplateUtils.processTemplateIntoString(template,root);
//			System.out.println(resu);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (TemplateException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				in.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println("\n\n\n+++++++++++++++\n\n\n");
//	}
//}