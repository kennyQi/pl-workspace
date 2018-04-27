package plfx.yeexing.constant;

import hg.common.util.SysProperties;

/****
 * 
 * @类功能说明：机票用户名和密钥常量类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午4:24:41
 * @版本：V1.0
 * 
 */
public class JPConstant {

	/**
	 * 用户名
	 */
	private String USERNAME = "";

	/***
	 * 密钥
	 */
	private String KEY = "";

	public JPConstant() {
		USERNAME=SysProperties.getInstance().get("yeeXing_userName");
		KEY=SysProperties.getInstance().get("yeeXing_key");
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getKEY() {
		return KEY;
	}

	public void setKEY(String kEY) {
		KEY = kEY;
	}
	
	
}
