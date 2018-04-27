package hg.common.util;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具类
 * @author zqq
 *
 */
public class AESUtil {

	/** 
	    * 加密 
	    *  
	    * @param content 需要加密的内容  128位
	    * @param keyBytes  加密密码 
	    * @return 
	     */   
	    public static byte[] encryptAES(byte[] content, byte[] keyBytes) {   
	    	try {       
	    		//生成AES密钥器
	    		KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	    		 SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
	             random.setSeed(keyBytes);
	            kgen.init(128, random);
	            //生成密钥
	            SecretKey secretKey = kgen.generateKey();   
	            byte[] enCodeFormat = secretKey.getEncoded();   
	            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");   
	            Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
	            byte[] byteContent = content;
	            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
	            byte[] result = cipher.doFinal(byteContent);   
	            return result; // 加密   
	            } catch (NoSuchAlgorithmException e) {   
	                    e.printStackTrace();   
	            } catch (NoSuchPaddingException e) {   
	                    e.printStackTrace();   
	            } catch (InvalidKeyException e) {   
	                    e.printStackTrace();   
	            } catch (IllegalBlockSizeException e) {   
	                    e.printStackTrace();   
	            } catch (BadPaddingException e) {   
	                    e.printStackTrace();   
	            }   
	            return null;   
	    } 
	    
	    /**解密 
	     * @param content  待解密内容 128位
	     * @param keyBytes 解密密钥 
	     * @return 
	      */   
	     public static byte[] decryptAES(byte[] content, byte[] keyBytes) {   
	         try {   
	        	//生成AES密钥器
	        	 KeyGenerator kgen = KeyGenerator.getInstance("AES"); 
	        	 SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
	             random.setSeed(keyBytes);
	        	 kgen.init(128, random);   
	        	 SecretKey secretKey = kgen.generateKey();   
	        	 byte[] enCodeFormat = secretKey.getEncoded();   
	        	 SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");               
	        	 Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
	        	 cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
	        	 byte[] result = cipher.doFinal(content);   
	        	 return result; // 加密   
	             } catch (NoSuchAlgorithmException e) {   
	                     e.printStackTrace();   
	             } catch (NoSuchPaddingException e) {   
	                     e.printStackTrace();   
	             } catch (InvalidKeyException e) {   
	                     e.printStackTrace();   
	             } catch (IllegalBlockSizeException e) {   
	                     e.printStackTrace();   
	             } catch (BadPaddingException e) {   
	                     e.printStackTrace();   
	             }   
	             return null;   
	     }  
}
