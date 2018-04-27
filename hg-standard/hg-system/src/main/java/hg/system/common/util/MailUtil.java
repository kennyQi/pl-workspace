package hg.system.common.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import hg.common.util.SpringContextUtil;
import hg.log.po.normal.HgMail;
import hg.log.repository.MailRepository;
import hg.log.util.HgLogger;
import hg.system.model.mail.MailTemplate;
import hg.system.qo.MailTemplateQo;
import hg.system.service.MailTemplateService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @类功能说明： Mail邮件工具类(包含MongoDB操作)
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月23日 下午6:36:28
 */
public class MailUtil {
	/**默认的邮件协议，SMTP(简单邮件传输协议)*/
	public final static String MAIL_PROTOCOL_DEFAULT = "smtp";
	/**邮件记录Repository*/
	private MailRepository repos;
	/**邮件模板dao*/
	private MailTemplateService temSer;
	/**邮件工具类静态实例*/
	private static MailUtil mailUtil;
	
	private MailUtil(MailRepository repos,MailTemplateService temSer){
		this.repos = repos;
		this.temSer = temSer;
	}
	public MailRepository getRepos() {
		return repos;
	}
	public void setRepos(MailRepository repos) {
		this.repos = repos;
	}
	public MailTemplateService getTemSer() {
		return temSer;
	}
	public void setTemSer(MailTemplateService temSer) {
		this.temSer = temSer;
	}
	
	/**
	 * @方法功能说明：获取邮件工具类静态实例
	 * @修改者名字：chenys
	 * @修改时间：2014年10月26日 下午1:51:02
	 * @修改内容：
	 * @return
	 */
	public static MailUtil getInstance(){
		if(null == mailUtil){
			//从Spring容器中取出邮件记录Repository、邮件模板dao
			Object rObj = SpringContextUtil.getBean("mailRepository");
			Object mObj = SpringContextUtil.getBean("mailTemplateServiceImpl");
			if(null != rObj && null != mObj)
				mailUtil = new MailUtil((MailRepository)rObj,(MailTemplateService)mObj);
		}
		return mailUtil;
	}
	
	/**
	 * @方法功能说明：发送邮件
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param host          邮箱服务器地址
	 * @param userName      用户名
	 * @param password      密码
	 * @param title         标题
	 * @param content       内容
	 * @param sender        发送人
	 * @param accept        接收人
	 * @return true：成功，false：失败
	 * @throws Exception 
	 */
	public boolean sendMail1(
			String host,
			String userName,
			String password,
			String title,
			String content,
			String sender,
			List<String> accept
		) throws Exception{
		return this.sendMail3(host,null,userName,password,false,title,content,sender,accept,null,null,false);
	}
	
	/**
	 * @方法功能说明：发送邮件
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param host          邮箱服务器地址
	 * @param userName      用户名
	 * @param password      密码
	 * @param title         标题
	 * @param content       内容
	 * @param sender        发送人
	 * @param accept        接收人列表
	 * @param annex         附件文件列表   (内容可以是File、URL、InputStream、byte数组或是表示byte数组的字符串) <br>
	 * 注意：如果附件内容是URL的话，URL不能由域名+路径表示的文件或者带有查询参数，因为本身通过URL获取文件时，这种URL获得的文件是没有后缀名或者后缀名错误。所以请注意这种情况！
	 * @return true：成功，false：失败
	 * @throws Exception 
	 */
	public boolean sendMail2(
			String host,
			String userName,
			String password,
			String title,
			String content,
			String sender,
			List<String> accept,
			List<Object> annex
		) throws Exception{
		return this.sendMail3(host,null,userName,password,false,title,content,sender,accept,null,annex,false);
	}
	
	/**
	 * @方法功能说明：发送邮件
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param host          邮箱服务器地址
	 * @param protocol      邮件协议
	 * @param userName      用户名
	 * @param password      密码
	 * @param isSSL         安全连接 SSL
	 * @param title         标题
	 * @param content       内容
	 * @param sender        发送人
	 * @param accept        接收人列表
	 * @param notice        抄送地址列表
	 * @param annex         附件文件列表   (内容可以是File、URL、InputStream、byte数组或是表示byte数组的字符串) <br>
	 * 注意：如果附件内容是URL的话，URL不能由域名+路径表示的文件或者带有查询参数，因为本身通过URL获取文件时，这种URL获得的文件是没有后缀名或者后缀名错误。所以请注意这种情况！
	 * @param debug         mail消息等级
	 * @return true：成功，false：失败
	 * @throws Exception 
	 */
	public boolean sendMail3(
			String host,
			String protocol,
			String userName,
			String password,
			boolean isSSL,
			String title,
			String content,
			String sender,
			List<String> accept,
			List<String> notice,
			List<Object> annex,
			boolean debug
		) throws Exception{
		//封装数据，转换成邮件信息对象
		HgMail mailBean = new HgMail();
		mailBean.setHost(host);
		mailBean.setProtocol(protocol);
		mailBean.setUserName(userName);
		mailBean.setPassword(password);
		mailBean.setSSL(isSSL);
		mailBean.setTitle(title);
		mailBean.setContent(content);
		mailBean.setSender(sender);
		mailBean.setAccept(accept);
		mailBean.setNotice(notice);
		mailBean.setAnnex(annex);
		return this.sendMail(mailBean, debug);
	}
	
	/**
	 * @方法功能说明：发送模板邮件
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param host          邮箱服务器地址
	 * @param userName      用户名
	 * @param password      密码
	 * @param title         标题
	 * @param templateId	模板id
	 * @param map			数据模型 Map
	 * @param sender        发送人
	 * @param accept        接收人
	 * @return true：成功，false：失败
	 * @throws Exception
	 */
	public boolean sendTemplateMail1(
			String host,
			String userName,
			String password,
			String title,
			String templateId,
			Map<String,Object> map,
			String sender,
			List<String> accept
		) throws Exception{
		return this.sendTemplateMail3(host,null,userName,password,false,title,templateId,map,sender,accept,null,null,false);
	}
	
	/**
	 * @方法功能说明：发送模板邮件
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param host          邮箱服务器地址
	 * @param userName      用户名
	 * @param password      密码
	 * @param title         标题
	 * @param templateId	模板id
	 * @param map			数据模型 Map
	 * @param sender        发送人
	 * @param accept        接收人列表
	 * @param annex         附件文件列表   (内容可以是File、URL、InputStream、byte数组或是表示byte数组的字符串) <br>
	 * 注意：如果附件内容是URL的话，URL不能由域名+路径表示的文件或者带有查询参数，因为本身通过URL获取文件时，这种URL获得的文件是没有后缀名或者后缀名错误。所以请注意这种情况！
	 * @return true：成功，false：失败
	 * @throws Exception 
	 */
	public boolean sendTemplateMail2(
			String host,
			String userName,
			String password,
			String title,
			String templateId,
			Map<String,Object> map,
			String sender,
			List<String> accept,
			List<Object> annex
		) throws Exception{
		return this.sendTemplateMail3(host,null,userName,password,false,title,templateId,map,sender,accept,null,annex,false);
	}
	
	/**
	 * @方法功能说明：发送模板邮件
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param host          邮箱服务器地址
	 * @param protocol      邮件协议
	 * @param userName      用户名
	 * @param password      密码
	 * @param isSSL         安全连接 SSL
	 * @param title         标题
	 * @param templateId	模板id
	 * @param map			数据模型 Map
	 * @param sender        发送人
	 * @param accept        接收人列表
	 * @param notice        抄送地址列表
	 * @param annex         附件文件列表   (内容可以是File、URL、InputStream、byte数组或是表示byte数组的字符串) <br>
	 * 注意：如果附件内容是URL的话，URL不能由域名+路径表示的文件或者带有查询参数，因为本身通过URL获取文件时，这种URL获得的文件是没有后缀名或者后缀名错误。所以请注意这种情况！
	 * @param debug         mail消息等级
	 * @return true：成功，false：失败
	 * @throws Exception 
	 */
	public boolean sendTemplateMail3(
			String host,
			String protocol,
			String userName,
			String password,
			boolean isSSL,
			String title,
			String templateId,
			Map<String,Object> map,
			String sender,
			List<String> accept,
			List<String> notice,
			List<Object> annex,
			boolean debug
		) throws Exception{
		//得到模板数据装配之后的字符结果
		String content = this.processTemplateFreeMarker(templateId, map);
		return this.sendMail3(host, protocol, userName, password, isSSL, title, content, sender, accept, notice, annex, debug);
	}
	
	/**
	 * @方法功能说明：发送邮件
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param mailBean 邮件信息
	 * @param debug    mail消息等级
	 * @return true：成功，false：失败
	 * @throws Exception 
	 */
	public boolean sendMail(HgMail mail,boolean debug) throws Exception{
		boolean bo = false;
		try {
			//校验
			if(null == mail)
				throw new ParametersException("邮件信息参数不能为空！");
			if(StringUtils.isBlank(mail.getUserName()))
				throw new ParametersException("邮箱用户名参数不能为空！");
			if(StringUtils.isBlank(mail.getPassword()))
				throw new ParametersException("邮箱密码参数不能为空！");
			if(StringUtils.isBlank(mail.getTitle()))
				throw new ParametersException("邮件标题参数不能为空！");
			/**
			 * 就是说不能发空邮件。
			 * javax.mail是允许发空邮件的，但考虑邮件信息的完整性，所以加这个校验
			 */
			if(StringUtils.isBlank(mail.getContent()))
				throw new ParametersException("邮件内容参数不能为空，请确认！");
			if(StringUtils.isBlank(mail.getSender()))
				throw new ParametersException("邮件发件人参数不能为空！");
			if(null == mail.getAccept() || mail.getAccept().size() < 1)
				throw new ParametersException("邮件接收人参数不能为空！");
			if(null != mail.getNotice() && mail.getNotice().size() < 1)
				throw new ParametersException("邮件抄送地址参数不能为空！");
			//如果发送时间为空，默认为当前时间
			Date time = mail.getCreateDate();
			mail.setCreateDate(null == time?new Date():time);
			//如果邮件协议为空，默认为SMTP协议
			String protocol = mail.getProtocol();
			mail.setProtocol(StringUtils.isBlank(protocol)?MAIL_PROTOCOL_DEFAULT:protocol);
			
			//发送邮件
			bo = this.send(mail, debug);
			//记录执行日志
			HgLogger.getInstance().debug(MailUtil.class,"hg-system",JSON.toJSONString(mail,SerializerFeature.DisableCircularReferenceDetect),bo?"发送邮件成功":"发送邮件失败");
			//返回结果 
			return bo;
		} catch (Exception e) {
			bo = false;
			//错误信息
			e.printStackTrace();
			
			//避免因mail为空而报空指针
			if(null == mail) mail = new HgMail();
			mail.setErrorStack(this.getStackTrace(e));
			//记录执行日志
			HgLogger.getInstance().error(MailUtil.class,"hg-system",JSON.toJSONString(mail,SerializerFeature.DisableCircularReferenceDetect),e,"发送邮件出错");
			//抛出异常，便于上一层捕获
			throw e;
		} finally {
			mail.setEndTime(new Date());//结束时间
			mail.setStatus(bo?0:1);//状态
			//记录发送记录
			this.repos.save(mail);
		}
	}
	
	/**
	 * @方法功能说明：发送邮件列表
	 * 注意：此方法无法做到，邮件列表中一个邮件发送错误而不退出方法，因为要抛异常，通知上层进行异常捕获 <br>
	 * 原因：如果通过线程池或线程队列异步发送邮件，执行完后统一收集异常，是能做到不影响下次邮件发送，但问题在于异常怎么抛出！
	 *     即怎么在异步转同步的情况下逐个抛出异常，这不可能做到！除非是异步转异步的情况，而现在显然不是这样。
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param mailBean 邮件信息列表
	 * @param debug    mail消息等级
	 * @return true：成功，false：失败
	 * @throws RuntimeException
	 */
	public boolean sendMailList(List<HgMail> mailList,boolean debug) throws Exception {
		if(null == mailList || mailList.size() < 1)
			return false;
		
		boolean bo = true;//结果标识
		for (HgMail mail : mailList) {
			//只要有一次邮件发送出错，执行结果就为失败
			if(!this.sendMail(mail, debug))
				bo = false;
		}
		return bo;
	}
	
	/**
	 * @方法功能说明：邮件发送
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param mailBean 邮件详细
	 * @param debug    mail消息等级
	 * @return true：成功，false：失败
	 * @throws ParametersException 
	 */
	private boolean send(HgMail mail,boolean debug) throws ParametersException{
		//获取mail会话
		Session sess = this.getSession(mail.getHost(),mail.getProtocol(),mail.getUserName(),mail.getPassword(),mail.isSSL(),debug);
		Message mess = null;
		
		try {
	    	//获取mail消息
	    	mess = this.getMessage(sess,mail.getTitle(),mail.getCreateDate(),mail.getSender(),mail.getAccept(),mail.getNotice());
			//设置mail主体内容
		    Multipart multi = this.addContent(new MimeMultipart(),mail.getContent());
		    multi = this.addAnnex(multi, mail.getAnnex());
			mess.setContent(multi);
		} catch (AddressException e) {
			throw new ParametersException("初始化邮件地址出错！",e);
		} catch (UnsupportedEncodingException e) {
			throw new ParametersException("初始化邮件主体内容出错！可能是附件文件名的编码不符合RFC822规范，",e);
		} catch (IOException e) {
			throw new ParametersException("初始化邮件主体内容出错！输入流错误，",e);
		} catch (MessagingException e) {
			throw new ParametersException("初始化邮件消息出错！",e);
		}
	    
		//邮件发送配置
	    try {
	    	this.getTransport(sess, mess,mail.getHost(),mail.getUserName(),mail.getPassword(),mail.isSSL());
	    } catch (AuthenticationFailedException e){
	    	throw new ParametersException("身份验证出错！可能是用户名密码错误或者邮箱地址错误,",e);
		} catch (MessagingException e) {
			throw new ParametersException("邮件发送出错！可能是邮箱设置不支持或者邮箱地址错误，",e);
		}
		return true;
	}
    
    /**
     * @方法功能说明：mail会话设置
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
     * @param protocol      邮件协议
     * @param userName      用户名
     * @param password      密码
     * @param isSSL         安全连接 SSL
     * @param debug         mail消息等级
     * @return mail会话
     */
	private Session getSession(String host,String protocol,final String userName,final String password,boolean isSSL,boolean debug) {
		Session sess = null;
		Properties proper = System.getProperties();
		proper.put("mail.transport.protocol",protocol);//邮件协议
		proper.put("mail.smtp.auth", "true");//授权验证
		//如果是SSL认证邮件，则进行SSL设定
		if (isSSL) {
			proper.put("mail.smtp.host",host);
			proper.put("mail.smtp.starttls.enable","true");
			proper.put("mail.smtp.socketFactory.fallback","false");
//			proper.put("mail.smtp.socketFactory.port",port);//不用设置，有默认端口号
			/**
			 * 匿名内部类：邮箱身份验证器
			 */
			sess = Session.getInstance(proper,new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(userName, password);//真正的发件人及密码
				}
			});
		}else{
			sess = Session.getInstance(proper);
		}
	    sess.setDebug(debug);//mail命令消息级
	    
	    //返回mail会话
	    return sess;
    }

	/**
     * @方法功能说明：mail消息设置
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param sess
	 * @param title
	 * @param time
	 * @param sender
	 * @param accept
	 * @param notice
	 * @return
	 * @throws MessagingException
	 * @throws ParametersException 
	 */
	private Message getMessage(Session sess,String title,Date time,String sender,List<String> accept,List<String> notice)
			throws MessagingException, ParametersException{
    	Message mess = new MimeMessage(sess);
		mess.setSubject(title);
		mess.setSentDate(time);
		mess.addFrom(this.getStringToAddress(sender));
		/**
		 * TO表示主要接收人，CC表示抄送人，BCC表示秘密抄送人。这里不做秘密抄送人
		 * 抄送，在已有的邮件接收者上再发用一个副本给其它的邮件接收者
		 */
		mess.setRecipients(Message.RecipientType.TO,this.getStringListToAddress(accept));
		if(null != notice && notice.size() > 0)
			mess.addRecipients(Message.RecipientType.CC,this.getStringListToAddress(notice));
		
		//返回mail消息
		return mess;
	}
	
	/**
     * @方法功能说明：mail发送设置
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param sess
	 * @param mess
	 * @param host
	 * @param name
	 * @param password
	 * @param isSSL
	 * @throws MessagingException
	 */
	private void getTransport(Session sess,Message mess,String host,String name,String password,boolean isSSL) 
			throws MessagingException{
    	if (isSSL) {
    		Transport.send(mess,mess.getAllRecipients());
    	}else{
    		Transport trans = sess.getTransport();
    		trans.connect(host,name,password);
    		trans.sendMessage(mess, mess.getAllRecipients());
    		trans.close();//关闭连接
    	}
	}
	
	/**
     * @方法功能说明：添加邮件内容
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param multi
	 * @param content
	 * @return
	 * @throws MessagingException
	 */
	private Multipart addContent(Multipart multi,String content) throws MessagingException {
    	BodyPart mdp = new MimeBodyPart();
    	mdp.setContent(content,"text/html;charset=UTf-8");//设置邮件内容的编码方式
    	multi.addBodyPart(mdp);
		return multi;
    }
 
	/**
     * @方法功能说明：添加邮件附件
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param multi
	 * @param list
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
    private Multipart addAnnex(Multipart multi,List<Object> list) throws MessagingException,IOException {
    	if(null == list || list.size() < 1)
    		return multi;
    	
		//遍历列表，添加邮件附件
		for (Object obj : list) {
			if(null == obj)
				continue;
			
			BodyPart mdp = new MimeBodyPart();
			DataSource data = this.getDataSource(obj);
			//如果数据源为空，则忽略
			if(null == data)
				continue;
			//设置邮件附件和显示名称
			mdp.setFileName(MimeUtility.encodeText(data.getName()));
			mdp.setDataHandler(new DataHandler(data));
			multi.addBodyPart(mdp);
		}
		return multi;
    }
    
    /**
     * @方法功能说明：邮箱字符校验
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param email
	 * @return true：正确，false：错误
	 */
	private boolean isEmail(String email){
		String regex = "[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+";
		Pattern pat = Pattern.compile(regex);
		return pat.matcher(email).find();
	}
	
	/**
	 * @方法功能说明：对邮件模板进行FreeMarker数据装配
	 * @修改者名字：chenys
	 * @修改时间：2014年10月28日 上午10:18:31
	 * @修改内容：
	 * @param templateId	模板id
	 * @param map			数据模型 Map
	 * @return
	 * @throws Exception
	 */
	private String processTemplateFreeMarker(String templateId,Map<String,Object> map) throws IOException, TemplateException {
		MailTemplateQo qo = new MailTemplateQo();
		//如果模板id为空，则查询默认模板
		if(StringUtils.isBlank(templateId))
			qo.setDefAble(true);
		else
			qo.setId(templateId);
		MailTemplate tem = temSer.queryUnique(qo);
		//如果模板不存在，则返回空字符
		if(null == tem)
			return "";
		
		//FreeMarker配置实例
		Configuration cfg = new Configuration();
		//指定默认的检索数据模型
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		String ss = tem.getContent();
		//得到FreeMarker模板
		Template template = new Template(null,new StringReader(ss),cfg,"UTF-8");
		//通过FreeMarker模板工具类对数据装配并返回字符结果
		return FreeMarkerTemplateUtils.processTemplateIntoString(template,map);
	}
	
	/**
     * @方法功能说明：将邮件地址字符串转换成邮件地址
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param str
	 * @return
	 * @throws AddressException
	 * @throws ParametersException 
	 */
	private Address[] getStringToAddress(String str) throws AddressException, ParametersException{
		//校验
		if(!this.isEmail(str))
			throw new ParametersException("邮箱地址错误，请检查邮箱地址是否正确！");
		
		List<Address> addres = new ArrayList<Address>(1);
		addres.add(new InternetAddress(str));
		return addres.toArray(new Address[]{});
	}
	
	/**
     * @方法功能说明：将邮件地址字符串列表转换成邮件地址列表
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param strList
	 * @return
	 * @throws AddressException
	 * @throws ParametersException 
	 */
	private Address[] getStringListToAddress(List<String> strList) throws AddressException, ParametersException{
		List<Address> addres = new ArrayList<Address>(3);
		for (String acc : strList) {
			//如果邮件地址字符为空，则抛出参数错误
			if(!this.isEmail(acc))
				throw new ParametersException("邮箱地址错误，请检查邮箱地址是否正确！");
			addres.add(new InternetAddress(acc));
		}
		return addres.toArray(new Address[]{});
	}
    
    /**
     * @方法功能说明：获得文件数据源
	 * @修改者名字：chenys
	 * @修改时间：2014年10月24日 下午11:38:54
	 * @修改内容：
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	private DataSource getDataSource(Object obj) throws IOException{
		DataSource data = null;
		//判断对象类型，获得对应的数据源
		if(obj instanceof File){
			data = new FileDataSource((File)obj);
		}else if(obj instanceof URL){
			data = new URLDataSource((URL)obj);
		}else if(obj instanceof InputStream){
			data = new BinDataSource((InputStream)obj);
		}else if(obj instanceof byte[]){
			data = new BinDataSource((byte[])obj);
		}else if(obj instanceof String){
			data = new BinDataSource(obj+"");
		}
		return data;
	}
	
	/**
	 * @方法功能说明：获得异常堆栈信息
	 * @修改者名字：chenys
	 * @修改时间：2014年10月26日 下午6:40:51
	 * @修改内容：
	 * @param thr
	 * @return
	 */
	public String getStackTrace(Throwable thr) {
		Writer wri = new StringWriter();
		//通过打印流包装字符流，将错误基类Throwable的堆栈跟踪信息输出到字符中
		thr.printStackTrace(new PrintWriter(wri));
		return wri.toString();
	}
	
	/**
	 * @类功能说明：内部类：自定义异常，参数错误级异常
	 * @公司名称：浙江汇购科技有限公司
	 * @部门：技术部
	 * @作者：chenys
	 * @创建时间：2014年10月24日 下午11:42:52
	 */
	class ParametersException extends Exception{
		private static final long serialVersionUID = 1L;
		
		public ParametersException(){
	        super();
	    }
	    public ParametersException(String msg,Throwable cause){
	        super(msg, cause);
	    }
	    public ParametersException(String msg){
	        super(msg);
	    }
	    public ParametersException(Throwable cause){
	        super(cause);
	    }
	}
	
	/**
	 * @类功能说明：内部类：自定义数据源，为方便添加邮件附件时绑定数据
	 *  解决不了的问题，没办法通过分析二进制数据得到文件后缀，因为可能不是图片或者没有文件后缀，搞不定
	 * @公司名称：浙江汇购科技有限公司
	 * @部门：技术部
	 * @作者：chenys
	 * @创建时间：2014年10月24日 下午11:43:11
	 */
    class BinDataSource implements DataSource {
        private byte[] bytes;//字节数组
 
        public BinDataSource(InputStream in) throws IOException {
        	//依赖org.apache.commons的IO包
        	bytes = IOUtils.toByteArray(in);
        }
        public BinDataSource(byte[] data) {
        	bytes = data;
        }
        public BinDataSource(String str){
        	bytes = str.getBytes();
        }
 
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(bytes);
        }
        public OutputStream getOutputStream() throws IOException {
            return null;//不提供输出流的实现
        }
        public String getContentType() {
            return "application/octet-stream";
        }
        public String getName() {
            return "file";//文件名默认为‘file’
        }
    }
}