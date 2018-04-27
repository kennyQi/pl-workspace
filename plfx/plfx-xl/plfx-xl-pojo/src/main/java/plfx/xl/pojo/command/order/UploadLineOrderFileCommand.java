package plfx.xl.pojo.command.order;

import hg.common.component.BaseCommand;

/**
 * 
 * @param <CommonsMultipartFile>
 * @类功能说明：上传线路订单文件command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月23日下午5:53:45
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class UploadLineOrderFileCommand extends BaseCommand{

	/**
	 * 所属线路订单
	 */
	private String lineOrderID;
	
	/**
	 * 文件路径
	 */
	private String filePath;
	
	/**
	 * 文件名称
	 */
	private String fileName;

	public String getLineOrderID() {
		return lineOrderID;
	}

	public void setLineOrderID(String lineOrderID) {
		this.lineOrderID = lineOrderID;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	
	
	
	
}
