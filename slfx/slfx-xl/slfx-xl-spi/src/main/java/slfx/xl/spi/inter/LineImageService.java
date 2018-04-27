package slfx.xl.spi.inter;

import java.util.List;

import slfx.xl.pojo.command.line.CreateLineImageCommand;
import slfx.xl.pojo.dto.line.LineImageDTO;
import slfx.xl.pojo.qo.LineImageQO;

/**
 * 
 * @类功能说明：线路图片接口
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月22日下午3:18:10
 * @版本：V1.0
 *
 */
public interface LineImageService extends BaseXlSpiService<LineImageDTO, LineImageQO>{

	/**
	 * 
	 * @方法功能说明：查询线路图片
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月22日下午3:39:27
	 * @修改内容：
	 * @参数：@param lineImageQO
	 * @参数：@return
	 * @return:List<LineImage>
	 * @throws
	 */
	public List<LineImageDTO> getLineImages(LineImageQO lineImageQO);

	/**
	 * 
	 * @方法功能说明：修改线路图片
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月24日上午10:18:41
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyLineImage(CreateLineImageCommand command);
	
	/**
	 * 
	 * @方法功能说明：查询线路所有图片
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月6日下午4:13:17
	 * @修改内容：
	 * @参数：@param lineImageQO
	 * @参数：@return
	 * @return:List<LineImageDTO>
	 * @throws
	 */
	public List<LineImageDTO> queryAllImage(LineImageQO lineImageQO);

}
