package pl.cms.pojo.command.scenic;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import hg.common.component.BaseCommand;
import hg.common.util.file.FdfsFileInfo;

/**
 * @类功能说明：新建景区
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月10日下午2:47:40
 * 
 */
@SuppressWarnings("serial")
public class CreateScenicCommand extends BaseCommand {
	/**
	 * 景区名称
	 */
	private String name;
	/**
	 * 省份ID
	 */
	private String provinceId;
	/**
	 * 城市ID
	 */
	private String cityId;
	private String address;
	/**
	 * 景区等级
	 */
	private String grade;
	/**
	 * 联系人
	 */
	private String linkMan;
	/**
	 * 联系电话
	 */
	private String tel;
	/**
	 * 联系邮箱
	 */
	private String email;
	private String fdfsFileInfoJSON;
	private FdfsFileInfo titleImageFileInfo;
	/**
	 * 景区简介
	 */
	private String intro;
	/**
	 * 交通信息
	 */
	private String traffic;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public FdfsFileInfo getTitleImageFileInfo() {
		//自动将JSON转化为FdfsFileInfo
		if (titleImageFileInfo == null&& StringUtils.isNotBlank(fdfsFileInfoJSON)) {
			setTitleImageFileInfo(JSON.parseObject(fdfsFileInfoJSON,FdfsFileInfo.class));
		}
		return titleImageFileInfo;
	}

	public void setTitleImageFileInfo(FdfsFileInfo titleImageFileInfo) {
		this.titleImageFileInfo = titleImageFileInfo;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getFdfsFileInfoJSON() {
		return fdfsFileInfoJSON;
	}

	public void setFdfsFileInfoJSON(String fdfsFileInfoJSON) {
		this.fdfsFileInfoJSON = fdfsFileInfoJSON;
	}

}
