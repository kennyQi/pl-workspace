//package hg.dzpw.app.common.util;
//
//
//import hg.dzpw.pojo.common.HjbPayServiceConfig;
//
//import java.io.FileInputStream;
//import java.security.PrivateKey;
//import java.security.cert.CertificateEncodingException;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.ContextLoader;
//
//import cfca.sadk.algorithm.common.Mechanism;
//import cfca.sadk.lib.crypto.JCrypto;
//import cfca.sadk.lib.crypto.Session;
//import cfca.sadk.util.CertUtil;
//import cfca.sadk.util.EnvelopeUtil;
//import cfca.sadk.util.KeyUtil;
//import cfca.sadk.util.Signature;
//import cfca.sadk.x509.certificate.X509Cert;
//
//@Component
//public class SignatureUtil {
//	private static Session session = null;
//	private static JCrypto jcrypto = null;
//	@Autowired
//	private HjbPayServiceConfig hjbPayServiceConfig;
//	public static SignatureUtil util;
//	
//	public void setHjbServiceConfig(HjbPayServiceConfig config){
//		this.hjbPayServiceConfig=config;
//		
//	}
//	
//	@PostConstruct
//	public void init(){
//		util=this;
//		util.hjbPayServiceConfig=this.hjbPayServiceConfig;
//	}
//	static {
//		System.out.println("*********************** 加载证书 ***********************************");
//		jcrypto = JCrypto.getInstance();
//		try {
//			jcrypto.initialize(JCrypto.JSOFT_LIB, null);
//			session = jcrypto.openSession(JCrypto.JSOFT_LIB);
//			System.out.println("*********************** 加载证书成功 ***********************************");
//		} catch (Exception e) {
//			System.out
//					.println("***********************证书加载失败******************************");
//		}
//	}
//
//	/**
//	 * 6.5 sm2消息签名(含原文)
//	 * 
//	 * @throws Exception
//	 * @throws CertificateEncodingException
//	 */
//	public static String sm2SignAttach(String content) throws Exception {
//		String sm2pfxPath;
//		try {
//			sm2pfxPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(util.hjbPayServiceConfig.getSignPath());
//		} catch (Exception e) {
//			sm2pfxPath="d:\\cert\\hjbCFCA004.sm2";
//		}
//		String pwd = PropertiesUtil.getSignPassword();
//		PrivateKey priKey = KeyUtil.getPrivateKeyFromSM2(sm2pfxPath, pwd);
//		X509Cert cert = CertUtil.getCertFromSM2(sm2pfxPath);
//		// 对加密数据进行签名
//		byte[] signature = new Signature().p7SignMessageAttach(
//				Mechanism.SM3_SM2, content.getBytes("UTF-8"), priKey, cert,
//				session);
//		StringBuffer strb = new StringBuffer(content);
//		strb.append("<signatureInfo>");
//		strb.append(signature.toString());
//		strb.append("</signatureInfo>");
//		// 对数据进行加密
//		byte[] srcData = sm2EnvelopMessage(strb.toString());
//		return new String(srcData);
//	}
//
//	/**
//	 * 6.5.1 sm2消息验签(含原文)
//	 * 
//	 * @throws Exception
//	 */
//	public static boolean sm2VerifyAttach(Map<String, String> signData)
//			throws Exception {
//		Signature signature = new Signature();
//		boolean result = false;
//		if (signature.p7VerifyMessageAttach(
//				signData.get("signData").getBytes(), session)) {
//			signData.put("signData",
//					openSM2EnvelopMessage(signature.getSourceData()));
//			result = true;
//		}
//		return result;
//	}
//
//	/**
//	 * 6.6 sm2消息签名(不含原文)
//	 * 
//	 * @throws Exception
//	 */
//	public static String sm2SignDettach(String srcData) throws Exception {
//		String sm2pfxPath;
//		try {
//			sm2pfxPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(util.hjbPayServiceConfig.getSignPath());
//		} catch (Exception e) {
//			sm2pfxPath="d:\\cert\\hjbCFCA004.sm2";
//		}
//		System.out.println(sm2pfxPath);
//		String pwd = util.hjbPayServiceConfig.getSignPassword();//"hjb3651";
//		PrivateKey priKey = KeyUtil.getPrivateKeyFromSM2(sm2pfxPath, pwd);
//		X509Cert cert = CertUtil.getCertFromSM2(sm2pfxPath);
//
//		// 对加密数据进行签名
//		byte[] signature = new Signature().p7SignMessageAttach(
//				Mechanism.SM3_SM2, srcData.getBytes("UTF-8"), priKey, cert,
//				session);
//		StringBuffer strb = new StringBuffer(srcData);
//		strb.append("<signatureInfo>");
//		strb.append(new String(signature));
//		strb.append("</signatureInfo>");
//		// 对数据进行加密
//		byte[] srcDataPwd = sm2EnvelopMessage(strb.toString());
//		return new String(srcDataPwd);
//	}
//
//	/**
//	 * 6.6.1 sm2消息验签(不含原文)
//	 * 
//	 * @throws Exception
//	 */
//	public static boolean sm2VerifyDettach(String signResult) throws Exception {
//		//对密文进行解密
//		//String srcData = openSM2EnvelopMessage(signResult.getBytes("UTF-8"));
//		//获取签名值
//		String signData = XmlUtil.getNodeVal(signResult, "signatureInfo");
//		Signature signature = new Signature();
//		if (signature.p7VerifyMessageDetach(XmlUtil.removeNode(signResult, "signatureInfo").getBytes("UTF-8"),
//				signData.getBytes(), session)) {
//			return true;
//		}
//		return false;
//
//	}
//
//	/*
//	 * public static boolean sm2VerifyDettach(String signData, String srcData)
//	 * throws Exception{ Signature signature = new Signature();
//	 * if(signature.p7VerifyMessageDetach(srcData.getBytes("UTF-8"),
//	 * signData.getBytes(), session)){ return true; } return false;
//	 * 
//	 * }
//	 */
//
//	/**
//	 * 15.1 SM2消息加密(数字信封)
//	 * 
//	 * @throws Exception
//	 */
//	private static byte[] sm2EnvelopMessage(String content) throws Exception {
//		// System.out.println("输入接收者证书路径，多张证书用；分开");
//		String cerPath;
//		try {
//			cerPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(util.hjbPayServiceConfig.getCerPath());
//		} catch (Exception e) {
//			cerPath="d:\\cert\\hjbcfca005.cer";
//		}
//		X509Cert cert = new X509Cert(new FileInputStream(cerPath));
//		X509Cert[] certs = { cert };
//		String symmetricAlgorithm = Mechanism.SM4_CBC;
//		byte[] encryptedData = EnvelopeUtil.envelopeMessage(
//				content.getBytes("UTF-8"), symmetricAlgorithm, certs);
//		System.out.println("加密:" + new String(encryptedData));
//		return encryptedData;
//	}
//
//	/**
//	 * 15.1.1 SM2消息解密(数字信封)
//	 * 
//	 * @throws Exception
//	 */
//	public static String openSM2EnvelopMessage(byte[] encryptedData)
//			throws Exception {
//		// System.out.println("输入SM2证书路径:");
//		String sm2Path;
//		try {
//			sm2Path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(util.hjbPayServiceConfig.getSignPath());
//		} catch (Exception e) {
//			sm2Path="d:/cert/hjbCFCA004.sm2";
//		}
//		// System.out.println("输入SM2证书密码:");
//		String pwd = util.hjbPayServiceConfig.getSignPassword();//"hjb3651";
//		PrivateKey priKey = (PrivateKey) KeyUtil.getPrivateKeyFromSM2(sm2Path,
//				pwd);
//		X509Cert cert = CertUtil.getCertFromSM2(sm2Path);
//		byte[] sourceData = EnvelopeUtil.openEvelopedMessage(encryptedData,
//				priKey, cert, session);
//		System.out.println("解密:" + new String(sourceData));
//		return new String(sourceData, "UTF-8");
//	}
//}
