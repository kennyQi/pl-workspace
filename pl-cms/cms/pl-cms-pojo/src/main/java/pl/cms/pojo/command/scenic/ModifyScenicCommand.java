package pl.cms.pojo.command.scenic;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import hg.common.component.BaseCommand;
import hg.common.util.file.FdfsFileInfo;

/**
 * 
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
public class ModifyScenicCommand extends BaseCommand {

	private String scenicId;

	private String name;

	private String provinceId;

	private String cityId;
	
	private String address;
	
	private String grade;

	private String linkMan;

	private String tel;

	private String email;
	
	private String fdfsFileInfoJSON;
	private FdfsFileInfo titleImageFileInfo;

	private String intro;

	private String traffic;

	public String getScenicId() {
		return scenicId;
	}

	public void setScenicId(String scenicId) {
		this.scenicId = scenicId;
	}

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
		// 自动将JSON转化为FdfsFileInfo
		if (titleImageFileInfo == null
				&& StringUtils.isNotBlank(fdfsFileInfoJSON)) {
			setTitleImageFileInfo(JSON.parseObject(fdfsFileInfoJSON,
					FdfsFileInfo.class));
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFdfsFileInfoJSON() {
		return fdfsFileInfoJSON;
	}

	public void setFdfsFileInfoJSON(String fdfsFileInfoJSON) {
		this.fdfsFileInfoJSON = fdfsFileInfoJSON;
	}

}
