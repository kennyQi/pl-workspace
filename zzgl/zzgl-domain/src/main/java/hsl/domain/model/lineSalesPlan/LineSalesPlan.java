package hsl.domain.model.lineSalesPlan;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.xl.Line;
import hsl.pojo.command.lineSalesPlan.CreateLineSalesPlanCommand;
import hsl.pojo.command.lineSalesPlan.ModifyLineSalesPlanCommand;
import hsl.pojo.command.lineSalesPlan.UpdateLineSalesPlanStatusCommand;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @类功能说明：线路销售方案
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/11/28 11:06
 */
@Entity
@Table(name = M.TABLE_PREFIX_LSP + "LINE")
public class LineSalesPlan extends BaseModel{
	/**
	 * 方案基本信息
	 */
	@Embedded
	private  LineSalesPlanBaseInfo baseInfo;
	/**
	 * 关联的线路（直接关联线路实体，分销修改后可以直接关联到活动中）
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LINE_ID")
	private Line line;
	/**
	 * 方案的销售信息
	 */
	@Embedded
	private LineSalesPlanSalesInfo lineSalesPlanSalesInfo;
	/**
	 * 方案的状态
	 */
	@Embedded
	private LineSalesPlanStatus lineSalesPlanStatus;
	/**
	 * @方法功能说明: 创建线路销售方案
	 * @修改者名字：chenxy
	 * @修改时间 2015-12-01 13:55:34
	 * @修改内容：
	 * @param createLineSalesPlanCommand
	 * @return 
	 * @throws 
	 */
	public void createLineSalesPlan(CreateLineSalesPlanCommand createLineSalesPlanCommand,Line line){
		this.setId(UUIDGenerator.getUUID());
		//基础信息设置
		baseInfo=new LineSalesPlanBaseInfo();
		baseInfo.setPlanName(createLineSalesPlanCommand.getPlanName());
		baseInfo.setPlanType(createLineSalesPlanCommand.getPlanType());
		baseInfo.setImageUri(createLineSalesPlanCommand.getImageUri());
		baseInfo.setCreateDate(new Date());
		baseInfo.setLastUpdateDate(new Date());
		baseInfo.setPlanRule(createLineSalesPlanCommand.getPlanRule());
		this.setBaseInfo(baseInfo);
		//销售信息设置
		lineSalesPlanSalesInfo=new LineSalesPlanSalesInfo();
		lineSalesPlanSalesInfo.setOriginalPrice(createLineSalesPlanCommand.getOriginalPrice());
		lineSalesPlanSalesInfo.setBeginDate(createLineSalesPlanCommand.getBeginDate());
		lineSalesPlanSalesInfo.setEndDate(createLineSalesPlanCommand.getEndDate());
		lineSalesPlanSalesInfo.setPlanPrice(createLineSalesPlanCommand.getPlanPrice());
		lineSalesPlanSalesInfo.setProvideNum(createLineSalesPlanCommand.getProvideNum());
		lineSalesPlanSalesInfo.setSoldNum(0);
		lineSalesPlanSalesInfo.setTravelDate(createLineSalesPlanCommand.getTravelDate());
		lineSalesPlanSalesInfo.setPriority(createLineSalesPlanCommand.getPriority());
		this.setLineSalesPlanSalesInfo(lineSalesPlanSalesInfo);
		//状态信息设置
		lineSalesPlanStatus=new LineSalesPlanStatus();
		lineSalesPlanStatus.setShowStatus(createLineSalesPlanCommand.getShowStatus());
		lineSalesPlanStatus.setStatus(LineSalesPlanConstant.LSP_STATUS_NOCHECK);
		this.setLineSalesPlanStatus(lineSalesPlanStatus);
		//设置关联的线路
		this.setLine(line);
	}
	/**
	 * @方法功能说明:修改线路销售方案状态
	 * @修改者名字：chenxy
	 * @修改时间 2015-12-01 16:07:39
	 * @修改内容：
	 * @param updateLineSalesPlanStatusCommand
	 * @return
	 * @throws
	 */
	public void updateStatus(UpdateLineSalesPlanStatusCommand updateLineSalesPlanStatusCommand){
		lineSalesPlanStatus=this.getLineSalesPlanStatus();
		if(updateLineSalesPlanStatusCommand.getShowStatus()!=null){
			lineSalesPlanStatus.setShowStatus(updateLineSalesPlanStatusCommand.getShowStatus());
		}
		if(updateLineSalesPlanStatusCommand.getStatus()!=null){
			lineSalesPlanStatus.setStatus(updateLineSalesPlanStatusCommand.getStatus());
		}
	}

	/**
	 * 修改线路销售方案信息
	 * @param modifyLineSalesPlanCommand
	 */
	public void modifyLineSalesPlan(ModifyLineSalesPlanCommand modifyLineSalesPlanCommand,Line line){
		baseInfo=this.getBaseInfo();
		//修改基本信息
		if(StringUtils.isNotBlank(modifyLineSalesPlanCommand.getPlanName())){
			baseInfo.setPlanName(modifyLineSalesPlanCommand.getPlanName());
		}
		if(modifyLineSalesPlanCommand.getPlanType()!=null){
			baseInfo.setPlanType(modifyLineSalesPlanCommand.getPlanType());
		}
		if(StringUtils.isNotBlank(modifyLineSalesPlanCommand.getImageUri())){
			baseInfo.setImageUri(modifyLineSalesPlanCommand.getImageUri());
		}
		baseInfo.setLastUpdateDate(new Date());
		if(StringUtils.isNotBlank(modifyLineSalesPlanCommand.getPlanRule())){
			baseInfo.setPlanRule(modifyLineSalesPlanCommand.getPlanRule());
		}
		this.setBaseInfo(baseInfo);
		//修改销售信息
		lineSalesPlanSalesInfo=this.getLineSalesPlanSalesInfo();

		lineSalesPlanSalesInfo.setOriginalPrice(modifyLineSalesPlanCommand.getOriginalPrice());

		if(modifyLineSalesPlanCommand.getBeginDate()!=null){
			lineSalesPlanSalesInfo.setBeginDate(modifyLineSalesPlanCommand.getBeginDate());
		}
		if(modifyLineSalesPlanCommand.getEndDate()!=null){
			lineSalesPlanSalesInfo.setEndDate(modifyLineSalesPlanCommand.getEndDate());
		}
		if(modifyLineSalesPlanCommand.getPlanPrice()!=null&&modifyLineSalesPlanCommand.getPlanPrice()>0){
			lineSalesPlanSalesInfo.setPlanPrice(modifyLineSalesPlanCommand.getPlanPrice());
		}
		if(modifyLineSalesPlanCommand.getPriority()!=null){
			lineSalesPlanSalesInfo.setPriority(modifyLineSalesPlanCommand.getPriority());
		}
		if(modifyLineSalesPlanCommand.getTravelDate()!=null){
			lineSalesPlanSalesInfo.setTravelDate(modifyLineSalesPlanCommand.getTravelDate());
		}
		if(modifyLineSalesPlanCommand.getProvideNum()!=null){
			lineSalesPlanSalesInfo.setProvideNum(modifyLineSalesPlanCommand.getProvideNum());
		}
		this.setLineSalesPlanSalesInfo(lineSalesPlanSalesInfo);
		//修改方案的状态
		lineSalesPlanStatus=this.getLineSalesPlanStatus();
		lineSalesPlanStatus.setShowStatus(modifyLineSalesPlanCommand.getShowStatus());
		this.setLineSalesPlanStatus(lineSalesPlanStatus);
		if(line!=null){
			this.setLine(line);
		}
	}
	public LineSalesPlanBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(LineSalesPlanBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public LineSalesPlanSalesInfo getLineSalesPlanSalesInfo() {
		return lineSalesPlanSalesInfo;
	}

	public void setLineSalesPlanSalesInfo(LineSalesPlanSalesInfo lineSalesPlanSalesInfo) {
		this.lineSalesPlanSalesInfo = lineSalesPlanSalesInfo;
	}

	public void setLineSalesPlanStatus(LineSalesPlanStatus lineSalesPlanStatus) {
		this.lineSalesPlanStatus = lineSalesPlanStatus;
	}

	public LineSalesPlanStatus getLineSalesPlanStatus() {
		return lineSalesPlanStatus;
	}
}
