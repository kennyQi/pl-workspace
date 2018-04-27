package hg.service.image.spi.inter;

import hg.common.page.Pagination;
import hg.service.image.command.image.usetype.CreateImageUseTypeCommand;
import hg.service.image.command.image.usetype.DeleteImageUseTypeCommand;
import hg.service.image.command.image.usetype.ModifyImageUseTypeCommand;
import hg.service.image.pojo.dto.ImageUseTypeDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.ImageUseTypeQo;

/**
 * @类功能说明：图片用途KEY服务Service接口
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午5:02:17
 */
public interface ImageUseTypeService_1 {
	/**
	 * @方法功能说明：创建图片用途KEY
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:41:48
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 */
	public void createImageSpecKeyUseType_1(CreateImageUseTypeCommand command) throws ImageException;
	
	/**
	 * @方法功能说明：删除图片用途KEY
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:41:56
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 */
	public void deleteImageSpecKeyUseType_1(DeleteImageUseTypeCommand command) throws ImageException;
	
	/**
	 * @方法功能说明：修改图片用途KEY
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:42:03
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 */
	public void modifyImageSpecKeyUseType_1(ModifyImageUseTypeCommand command) throws ImageException;
	
	/**
	 * @方法功能说明: 查询数量 
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:24:53
	 * @修改内容：
	 * @param qo
	 * @return
	 */
	Integer queryCount_1(ImageUseTypeQo qo);
	
	/**
	 * @方法功能说明：查询图片用途KEY
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:42:10
	 * @修改内容：
	 * @param qo
	 * @return
	 */
	public ImageUseTypeDTO queryUniqueImageSpecKeyUseType_1(ImageUseTypeQo qo);
	
	/**
	 * @方法功能说明：查询图片用途KEY列表，分页
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:42:16
	 * @修改内容：
	 * @param pag
	 * @return
	 */
	// 查询明细规格用途
	public Pagination queryImageSpecKeyUseTypePagination_1(ImageUseTypeQo qo);
}