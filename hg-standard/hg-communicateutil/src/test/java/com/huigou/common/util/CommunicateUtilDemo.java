package com.huigou.common.util;

import java.io.UnsupportedEncodingException;

import hg.common.util.AESUtil;
import hg.common.util.Base64Utils;
import hg.common.util.CommunicateData;
import hg.common.util.CommunicateUtil;
import hg.common.util.EncodedCommunicateData;
import hg.common.util.KeyPairEntity;
import hg.common.util.RSAUtil;

import org.junit.Test;

/**
 * 传输过程模拟
 * @author admin
 *
 */
public class CommunicateUtilDemo {

	@Test
	public void testCase(){
		//模拟RSA密钥对的生成(一般由合作方生成,并将公钥告知方)
		KeyPairEntity keyPairs = RSAUtil.generateKeyPair();
		//模拟请求放生成请求数据
		CommunicateData data = new CommunicateData();
		data.aesKey="12345678";
		data.appId="hjf";
		data.time = System.currentTimeMillis()+"";
		data.data="{'hjfUser':'13012345678','jf':'100','phone':'18212345678','user':'18212345678'}";
		
		
		System.out.println("加密请求===================");
		//加密请求===================
		EncodedCommunicateData enCode = CommunicateUtil.encryptRequest(data, Base64Utils.Base64Encode(keyPairs.getPublicKey()));
		System.out.println("模拟传输:"+enCode);
		System.out.println("合作方接到请求，使用工具类解密请求===================");
		//合作放校验签名
		String deContent="";
		if(CommunicateUtil.checkSign(enCode, Base64Utils.Base64Encode(keyPairs.getPublicKey()))){
			//校验成功 解密
			CommunicateData deCode = CommunicateUtil.decyrptRequest(enCode, Base64Utils.Base64Encode(keyPairs.getPrivateKey()));
			System.out.println("解密后的数据"+deCode);
			String content = "{'status':'true','text':'处理成功'}";
			System.out.println("处理业务，得到返回数据"+content);
			//加密响应数据
			deContent = CommunicateUtil.encryptResponse(deCode.aesKey, content);
			System.out.println("模拟传输"+deContent);
		}else{
			System.out.println("校验签名失败");
		}
		//接受到响应数据，解密
		try {
			String enContent = new String(AESUtil.decryptAES(Base64Utils.Base64Decode(deContent), data.aesKey.getBytes("utf-8")));
			System.out.println("得到响应:"+enContent);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
