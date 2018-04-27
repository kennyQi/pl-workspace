package plfx.xl.spi.inter;

import plfx.xl.pojo.command.order.UploadLineOrderFileCommand;
import plfx.xl.pojo.dto.order.LineOrderFileDTO;
import plfx.xl.pojo.qo.LineOrderFileQO;

/**
 * 
 * @类功能说明：线路订单文件Service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月24日上午9:02:17
 * @版本：V1.0
 *
 */
public interface LineOrderFileService extends BaseXlSpiService<LineOrderFileDTO, LineOrderFileQO>{

	/**
	 * 
	 * @方法功能说明：线路订单文件上传
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月24日上午11:11:17
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean uploadLineOrderFile(UploadLineOrderFileCommand command);
	
}
