package hg.dzpw.pojo.api.appv1.request;

import hg.dzpw.pojo.api.appv1.base.ApiRequestBody;

/**
 * @类功能说明： 身份证件验票（通过证件信息验票，如果未开启人工核查证件，则直接核销唯一可用门票，否则仅返回门票信息。）
 * @类修改者：
 * @修改日期：2014-11-18下午3:18:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午3:18:57
 */
public class ValiTicketByCerRequestBody extends ApiRequestBody {
	private static final long serialVersionUID = 1L;

	/** 验票方式 1、 身份证扫描 */
	public final static String CHECK_WAY_SCAN = "1";
	/** 验票方式 2、 手工输入证件号 */
	public final static String CHECK_WAY_MANUAL = "2";

	/** 证件类型 1、 身份证 */
	public final static String CER_TYPE_IDENTITY = "1";
	/** 证件类型 2、 军官证 */
	public final static String CER_TYPE_OFFICER = "2";
	/** 验票方式 3、 驾驶证 */
	public final static String CER_TYPE_DRIVING = "3";
	/** 证件类型 4、 护照 */
	public final static String CER_TYPE_PASSPORT = "4";
	
	/** 性别：男  */
	public final static String GENDER_MAN = "男";
	/** 性别：女  */
	public final static String GENDER_WOMAN = "女";
	
	/**
	 * 验票方式
	 */
	private String checkWay;
	
	/**
	 * 证件类型
	 */
	private String cerType;
	
	/**
	 * 证件号
	 */
	private String idNo;
	
	/**
	 * 游客姓名
	 */
	private String name;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 名族
	 */
	private String nation;
	
	/**
	 * 户籍地址
	 */
	private String address;
	
	/**
	 * 出生年月日
	 */
	private String birthday;
	
	/**
	 * 身份证图片BASE64编码串
	 */
	@Deprecated
	private String imageBase64Str;
	
	/**
	 * 景区ID
	 */
	private String scenicSpotId;
	
	public boolean isCheckWay(){
		if(CHECK_WAY_SCAN.equals(this.checkWay) || CHECK_WAY_MANUAL.equals(this.checkWay))
			return true;
		return false;
	}
	public boolean isCerType(){
		if(CER_TYPE_IDENTITY.equals(this.cerType) || CER_TYPE_OFFICER.equals(this.cerType))
			return true;
		if(CER_TYPE_DRIVING.equals(this.cerType) || CER_TYPE_PASSPORT.equals(this.cerType))
			return true;
		return false;
	}
	public boolean isGender(){
		if(GENDER_MAN.equals(this.gender) || GENDER_WOMAN.equals(this.gender))
			return true;
		return false;
	}
	
	public String getCheckWay() {
		return checkWay;
	}
	public void setCheckWay(String checkWay) {
		this.checkWay = checkWay == null ? null : checkWay.trim();
	}
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		this.cerType = cerType == null ? null : cerType.trim();
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo == null ? null : idNo.trim();
	}
	public String getImageBase64Str() {
		return imageBase64Str;
	}
	public void setImageBase64Str(String imageBase64Str) {
		this.imageBase64Str = imageBase64Str == null ? null : imageBase64Str.trim();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender == null ? null : gender.trim();
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation == null ? null : nation.trim();
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday == null ? null : birthday.trim();
	}
	public String getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
}