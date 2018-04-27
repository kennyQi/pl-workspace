package pl.cms.domain.entity.scenic;
import java.util.Date;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang.StringUtils;
import pl.cms.domain.entity.M;
import pl.cms.domain.entity.image.Image;
import pl.cms.pojo.command.scenic.CreateScenicCommand;
import pl.cms.pojo.command.scenic.ModifyScenicCommand;

/**
 * 旅游景区
 */
@Entity
@Table(name = M.TABLE_PREFIX + "SCENIC_SPOT")
@SuppressWarnings("serial")
public class Scenic extends BaseModel {

	/**
	 * 景区基本信息
	 */
	@Embedded
	private ScenicBaseInfo scenicSpotBaseInfo;

	/**
	 * 景区地理信息(省市区经纬度等)
	 */
	@Embedded
	private ScenicGeographyInfo scenicSpotGeographyInfo;

	/**
	 * 景区联系方式
	 */
	@Embedded
	private ScenicContactInfo contactInfo;
	
	
	public void createScenic(CreateScenicCommand command,Image titleImage){
		this.setId(UUIDGenerator.getUUID());
		scenicSpotBaseInfo=new ScenicBaseInfo();
		scenicSpotBaseInfo.setName(command.getName());
		scenicSpotBaseInfo.setGrade(command.getGrade());
		scenicSpotBaseInfo.setIntro(command.getIntro());
		scenicSpotBaseInfo.setCreateDate(new Date());
		
		scenicSpotBaseInfo.setTitleImage(titleImage);
		
//		if(StringUtils.isNotBlank(command.getFdfsFileInfoJSON())){
//			FdfsFileInfo fileInfo =JSON.parseObject(command.getFdfsFileInfoJSON(), FdfsFileInfo.class);
//			Image image = new Image();
//			image.setTitle(fileInfo.getMetaMap().get("title"));
////			image.setUrl(fileInfo.getUri());
//			scenicSpotBaseInfo.setTitleImage(image);
//		}
		scenicSpotGeographyInfo=new ScenicGeographyInfo();
		scenicSpotGeographyInfo.setAddress(command.getAddress());
		City city=new City();
		city.setId(command.getCityId());
		scenicSpotGeographyInfo.setCity(city);
		Province province=new Province();
		province.setId(command.getProvinceId());
		scenicSpotGeographyInfo.setProvince(province);
		scenicSpotGeographyInfo.setTraffic(command.getTraffic());
		
		contactInfo=new ScenicContactInfo();
		contactInfo.setEmail(command.getEmail());
		contactInfo.setTel(command.getTel());
		contactInfo.setLinkMan(command.getLinkMan());
	}
	public void modifyScenic(ModifyScenicCommand command,Image image){
		if(StringUtils.isNotBlank(command.getName())){
			scenicSpotBaseInfo.setName(command.getName());
		}
		scenicSpotBaseInfo.setGrade(command.getGrade());
		scenicSpotBaseInfo.setIntro(command.getIntro());
		scenicSpotBaseInfo.setTitleImage(image);
//		if(StringUtils.isNotBlank(command.getFdfsFileInfoJSON())){
//			FdfsFileInfo fileInfo =JSON.parseObject(command.getFdfsFileInfoJSON(), FdfsFileInfo.class);
//			Image image = new Image();
//			image.setTitle(fileInfo.getMetaMap().get("title"));
////			image.setUrl(fileInfo.getUri());
//			scenicSpotBaseInfo.setTitleImage(image);
//		}
		scenicSpotGeographyInfo.setAddress(command.getAddress());
		City city=new City();
		city.setId(command.getCityId());
		scenicSpotGeographyInfo.setCity(city);
		Province province=new Province();
		province.setId(command.getProvinceId());
		scenicSpotGeographyInfo.setProvince(province);
		scenicSpotGeographyInfo.setTraffic(command.getTraffic());
		
		contactInfo.setEmail(command.getEmail());
		contactInfo.setTel(command.getTel());
		contactInfo.setLinkMan(command.getLinkMan());
	}
	public ScenicBaseInfo getScenicSpotBaseInfo() {
		return scenicSpotBaseInfo;
	}

	public void setScenicSpotBaseInfo(ScenicBaseInfo scenicSpotBaseInfo) {
		this.scenicSpotBaseInfo = scenicSpotBaseInfo;
	}

	public ScenicGeographyInfo getScenicSpotGeographyInfo() {
		return scenicSpotGeographyInfo;
	}

	public void setScenicSpotGeographyInfo(
			ScenicGeographyInfo scenicSpotGeographyInfo) {
		this.scenicSpotGeographyInfo = scenicSpotGeographyInfo;
	}

	public ScenicContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ScenicContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

}