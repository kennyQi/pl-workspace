package slfx.xl.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import slfx.xl.domain.model.M;
import slfx.xl.pojo.command.order.UploadLineOrderFileCommand;

/**
 * 
 * @类功能说明：订单文件
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月23日下午5:38:04
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "LINE_ORDER_FILE")
public class LineOrderFile extends BaseModel{

	/**
	 * 文件所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LINE_ORDER_ID")
	private LineOrder lineOrder;
	
	/**
	 * 文件名称 
	 * 名称加后缀名
	 */
	@Column(name = "FILE_NAME")
	private String fileName;
	
	/**
	 * 文件保存路径
	 */
	@Column(name = "FILE_PATH")
	private String filePath;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	/**
	 * 
	 * @方法功能说明：上传文件
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月24日上午9:11:09
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void uploadLineOrderFile(UploadLineOrderFileCommand command){
		setId(UUIDGenerator.getUUID());
		LineOrder lineOrder = new LineOrder();
		lineOrder.setId(command.getLineOrderID());
		setLineOrder(lineOrder);
		setFileName(command.getFileName());
		setCreateDate(new Date());
		setFilePath(command.getFilePath());
	}

	public LineOrder getLineOrder() {
		return lineOrder;
	}

	public void setLineOrder(LineOrder lineOrder) {
		this.lineOrder = lineOrder;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
