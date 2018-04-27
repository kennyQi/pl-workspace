package com.huigou.common.util;

import java.io.UnsupportedEncodingException;

import hg.common.util.AESUtil;
import hg.common.util.Base64Utils;
import hg.common.util.DESUtil;
import hg.common.util.KeyPairEntity;
import hg.common.util.RSAUtil;

import org.junit.Test;

public class Test加密 {
	
	private String test="{'hjfUser':'13012345678','jf':'100','phone':'18212345678','user':'18212345678'}";
	
	@Test
	public void testDES(){
		System.out.println("DES加解密===============================================");
		//模拟json字串
		System.out.println("加密前："+test);
		String key="12345678";
		byte[] enBytes;
		//des加密
		try {
			//加密
			enBytes=DESUtil.encryptDES(test.getBytes("utf-8"), key.getBytes("utf-8"));
			String enStr = Base64Utils.Base64Encode(enBytes);
			System.out.println("加密后(base64编码字串)："+enStr);
			System.out.println("加密后(base64编码字串)2："+
					Base64Utils.Base64Encode(
							DESUtil.encryptDES(test, key)));
			//解密
			byte[] deBytes = Base64Utils.Base64Decode(enStr);
			String deStr = new String(DESUtil.decryptDES(deBytes, key.getBytes("utf8")),"utf-8");
			System.out.println("解密后"+deStr);
			System.out.println("解密后2"+DESUtil.decryptDES(deBytes, key));
			System.out.println("DES加解密===============================================");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testAES(){
		System.out.println("AES加解密===============================================");
		String key="123456789";
		System.out.println("加密前："+test);
		
		byte[] enBytes;
		//aes加密
		try {
			//加密
			enBytes=AESUtil.encryptAES(test.getBytes("utf-8"), key.getBytes("utf-8"));
			String enStr = Base64Utils.Base64Encode(enBytes);
			System.out.println("加密后(base64编码字串)2："+
					Base64Utils.Base64Encode(
							AESUtil.encryptAES(test, key)));
			System.out.println("加密后(base64编码字串)："+enStr);
			//解密
			byte[] deBytes = Base64Utils.Base64Decode(enStr);
			String deStr = new String(AESUtil.decryptAES(deBytes, key.getBytes("utf8")),"utf-8");
			System.out.println("解密后"+deStr);
			System.out.println("解密后2"+AESUtil.decryptAES(deBytes, key));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("AES加解密===============================================");
	}
	@Test
	public void testRSA() throws Exception{
		System.out.println("RSA加解密===============================================");
		//生成密钥对
		KeyPairEntity keyPairs = RSAUtil.generateKeyPair();
		System.out.println("私钥(base64编码String):"+Base64Utils.Base64Encode(keyPairs.getPrivateKey()));
		System.out.println("公钥(base64编码String):"+Base64Utils.Base64Encode(keyPairs.getPublicKey()));
		byte[] enBytes = RSAUtil.encrypt(keyPairs.getPublicKey(), test.getBytes("utf-8"));
		System.out.println("公钥加密"+Base64Utils.Base64Encode(enBytes));
		System.out.println("公钥加密1"+RSAUtil.encrypt(keyPairs.getPublicKey(), test));
		System.out.println("私钥解密"+new String(RSAUtil.decrypt(keyPairs.getPrivateKey(), enBytes),"utf-8"));
		System.out.println("私钥解密1"+RSAUtil.decrypt(keyPairs.getPrivateKey(), RSAUtil.encrypt(keyPairs.getPublicKey(), test)));
		System.out.println("RSA加解密===============================================");
	}
}


