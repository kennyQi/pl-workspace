package hg.common.util;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/** 
 * RSA算法，实现数据的加密解密。 
 * @author ShaoJiang 
 * 
 */  
public class RSAUtil {  
      
    private static Cipher cipher;  
      
    static{  
        try {  
            cipher = Cipher.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 生成密钥对 
     * @return 
     */  
    public static KeyPairEntity generateKeyPair(/*String filePath*/){  
        try {  
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
            // 密钥位数  
            keyPairGen.initialize(1024);  
            // 密钥对  
            KeyPair keyPair = keyPairGen.generateKeyPair();  
            // 公钥  
            PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
            // 私钥  
            PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
            //将生成的密钥对返回  
            KeyPairEntity keyPartStr = new KeyPairEntity(publicKey, privateKey);
            return keyPartStr;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /** 
     * 得到公钥 
     *  
     * @param key 
     *            密钥字符串（经过base64编码） 
     * @throws Exception 
     */  
    private static PublicKey getPublicKey(byte[] publicKeyBytes) throws Exception {  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        PublicKey publicKey = keyFactory.generatePublic(keySpec);  
        return publicKey;  
    }  
      
    /** 
     * 得到私钥 
     *  
     * @param key 
     *            密钥字符串（经过base64编码） 
     * @throws Exception 
     */  
    private static PrivateKey getPrivateKey(byte[] privateKeyBytes) throws Exception {  
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  
        return privateKey;  
    }  
  
    /** 
     * 使用公钥对明文进行加密
     * @param byte[] publicKey 
     * @param byte[] contentBytes 
     * @return 
     */  
    public static byte[] encrypt(byte[] publicKey,byte[] contentBytes){  
        try {             
        	
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));  
            byte[] enBytes = cipher.doFinal(contentBytes);            
            return enBytes;  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
      
    /** 
     * 使用私钥对明文密文进行解密 
     * @param  byte[] privateKey 
     * @param byte[] enBytes 
     * @return 
     */  
    public static byte[] decrypt(byte[] privateKey,byte[] enBytes){  
        try {  
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));  
            byte[] deBytes = cipher.doFinal(enBytes);  
            return deBytes;  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
}  