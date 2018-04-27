package hg.common.util;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RSA密钥对
 * @author zqq
 *
 */
public class KeyPairEntity {

	/**
	 * 公钥字符串
	 */
	private PublicKey publicKey;
	/**
	 * 私钥字符串
	 */
	private PrivateKey privateKey;
	
	public KeyPairEntity() {
		// TODO Auto-generated constructor stub
	}
	public KeyPairEntity(PublicKey publicKey,PrivateKey privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	public byte[] getPublicKey() {
		return publicKey.getEncoded();
	}
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	public byte[] getPrivateKey() {
		return privateKey.getEncoded();
	}
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	
}
