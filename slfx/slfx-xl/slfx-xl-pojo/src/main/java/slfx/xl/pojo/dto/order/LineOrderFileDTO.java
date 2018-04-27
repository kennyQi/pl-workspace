package slfx.xl.pojo.dto.order;

import slfx.xl.pojo.dto.BaseXlDTO;

/**
 * 
 * @类功能说明：线路订单文件DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月23日下午5:48:59
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LineOrderFileDTO extends BaseXlDTO{

	/**
	 * 文件所属订单
	 */
	private LineOrderDTO lineOrder;
	
	/**
	 * 文件名称 
	 * 名称加后缀名
	 */
	private String fileName;
	
	/**
	 * 文件保存路径
	 */
	private String filePath;

	public LineOrderDTO getLineOrder() {
		return lineOrder;
	}

	public void setLineOrder(LineOrderDTO lineOrder) {
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
	
	
	
	
}
