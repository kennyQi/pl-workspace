package hg.service.ad.domain.model.ad;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.service.ad.command.ad.ChangeAdImageCommand;
import hg.service.ad.command.ad.CreateAdCommand;
import hg.service.ad.command.ad.ModifyAdCommand;
import hg.service.ad.domain.model.image.Image;
import hg.service.ad.domain.model.position.AdPosition;

/**
 * @类功能说明：广告类
 * @类修改者：
 * @修改日期：2014年12月11日下午4:10:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月11日下午4:10:36
 * 
 */
@Entity
@Table(name = "AD_AD")
public class Ad extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 广告位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POSITION_ID")
	private AdPosition position;

	/**
	 * 广告基本信息
	 */
	@Embedded
	private AdBaseInfo baseInfo;

	/**
	 * 广告状态
	 */
	@Embedded
	private AdStatus status;

	/**
	 * 广告图
	 */
	private Image image;

	public void create(CreateAdCommand command, AdPosition position) {

		setId(UUIDGenerator.getUUID());

		setPosition(position);

		setBaseInfo(new AdBaseInfo());
		baseInfo.setRemark(command.getRemark());
		baseInfo.setTitle(command.getTitle());
		baseInfo.setPriority(command.getPriority());
		baseInfo.setUrl(command.getUrl());
		baseInfo.setCreateTime(new Date());

		setStatus(new AdStatus());
		status.setClickNo(0);
		status.setShow(false);

		// FdfsFileInfo fileInfo = JSON.parseObject(command.getImageInfo(),
		// FdfsFileInfo.class);
		setImage(new Image());
		image.setImageId(command.getImageId());
		image.setUrl(command.getImageUrl());

	}

	public void modify(ModifyAdCommand command) {

		baseInfo.setRemark(command.getRemark());
		baseInfo.setTitle(command.getTitle());
		baseInfo.setPriority(command.getPriority());
		baseInfo.setUrl(command.getUrl());

		status.setShow(false);
		
		image.setImageId(command.getImageId());
		image.setUrl(command.getImageUrl());
	}
	
	public void show() {
		status.setShow(true);
	}
	
	public void hide() {
		status.setShow(false);
	}
	
	public void modifyPriority(Integer priority) {
		getBaseInfo().setPriority(priority);
	}
	
	public void changeImage(ChangeAdImageCommand command) {
		
		getImage().setImageId(command.getImageId());
		getImage().setUrl(command.getImageUrl());
		
	}
	
	public AdPosition getPosition() {
		return position;
	}

	public void setPosition(AdPosition position) {
		this.position = position;
	}

	public AdBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(AdBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public AdStatus getStatus() {
		return status;
	}

	public void setStatus(AdStatus status) {
		this.status = status;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}