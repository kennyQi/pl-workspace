package hsl.domain.model.mp.scenic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.CreateHotScenicSpotCommand;
import hsl.pojo.command.ModifyHotScenicSpotCommand;
/**
 * 
 * @类功能说明：热门景区
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月4日上午11:02:38
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_HSL_MP+"HOT_SCENICSPOT")
public class MPHotScenicSpot extends BaseModel {

	/**
	 * 关联的景区
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SCENICSPOT_ID")
	private MPScenicSpot scenicSpot;

	/**
	 * 上架时间
	 */
	@Column(name = "OPEN_DATE", columnDefinition = M.DATE_COLUM)
	private Date openDate;

	/**
	 * 加入热门景区时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 是否上架中
	 */
	@Type(type = "yes_no")
	@Column(name = "OPEN")
	private Boolean open;

	public MPScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(MPScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	
	public void removeCurrentHotScenicSpot(){
		setOpen(false);
	}
	
	/**
	 * 新建热门景点
	 * @param command
	 */
	public void createHotScenicSpot(CreateHotScenicSpotCommand command){
		setId(UUIDGenerator.getUUID());
		setCreateDate(command.getCreateDate());
		setOpen(command.getOpen());
		setOpenDate(command.getOpenDate());
		scenicSpot=new MPScenicSpot();
		scenicSpot.setId(command.getScenicSpotId());
		MPScenicSpotsBaseInfo baseInfo=new MPScenicSpotsBaseInfo();
		MPScenicSpotsGeographyInfo geographyInfo=new MPScenicSpotsGeographyInfo();
		baseInfo.setAlias(command.getAlias());
		baseInfo.setGrade(command.getGrade());
		baseInfo.setImage(command.getImage());
		baseInfo.setIntro(command.getIntro());
		baseInfo.setName(command.getName());
		scenicSpot.setScenicSpotBaseInfo(baseInfo);
		geographyInfo.setAddress(command.getAddress());
		geographyInfo.setCityCode(command.getCity());
		geographyInfo.setProvinceCode(command.getProvince());
		geographyInfo.setTraffic(command.getTraffic());
		scenicSpot.setScenicSpotGeographyInfo(geographyInfo);
	}
	
	public void modifyCurrentHotScenicSpot(ModifyHotScenicSpotCommand command){
		setOpen(true);
		setOpenDate(command.getOpenDate());
	}
}
