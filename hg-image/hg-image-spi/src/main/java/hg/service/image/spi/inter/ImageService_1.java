package hg.service.image.spi.inter;

import hg.common.page.Pagination;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.command.image.BatchCreateImageCommand;
import hg.service.image.command.image.CreateTempImageCommand;
import hg.service.image.command.image.DeleteImageCommand;
import hg.service.image.command.image.ModifyImageCommand;
import hg.service.image.command.image.spec.CreateImageSpecCommand;
import hg.service.image.command.image.spec.DeleteImageSpecCommand;
import hg.service.image.command.image.spec.ModifyImageSpecCommand;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.dto.ImageSpecDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.pojo.qo.ImageSpecQo;

import java.io.IOException;

/**
 * @类功能说明：图片服务Service接口
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午5:02:17
 */
public interface ImageService_1 {
	
	public String createTempImage_1(CreateTempImageCommand command) throws ImageException;
	
	/**
	 * @方法功能说明：创建图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:47:52
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String createImage_1(CreateImageCommand command) throws ImageException,IOException;
	
	/**
	 * @方法功能说明：批量创建图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:47:52
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void batchCreateImage_1(BatchCreateImageCommand command) throws ImageException,IOException;
	
	/**
	 * @方法功能说明：删除图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:48:00
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 */
	public void deleteImage_1(DeleteImageCommand command);
	
	/**
	 * @方法功能说明：修改图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:48:08
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public void modifyImage_1(ModifyImageCommand command) throws ImageException,IOException;
	
	/**
	 * @方法功能说明: 查询数量 
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:24:53
	 * @修改内容：
	 * @param qo
	 * @return
	 */
	Integer queryCountImage_1(ImageQo qo);
	
	/**
	 * @方法功能说明：查询图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:48:15
	 * @修改内容：
	 * @param qo
	 * @return
	 */
	public ImageDTO queryUniqueImage_1(ImageQo qo);
	
	/**
	 * @方法功能说明：查询图片列表，分页
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:48:15
	 * @修改内容：
	 * @param pag
	 * @return
	 */
	public Pagination queryPaginationImage_1(Pagination pagination);
	public Pagination queryPaginationImage_1(ImageQo qo);

	//------------ 图片规格 ------------------//
	/**
	 * @方法功能说明：创建图片规格
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:48:34
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void createImageSpec_1(CreateImageSpecCommand command) throws ImageException,IOException;
	
	/**
	 * @方法功能说明：修改规格图
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月6日 上午3:48:34
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void modifyImageSpec_1(ModifyImageSpecCommand command) throws ImageException, IOException;
	
	/**
	 * @方法功能说明：删除图片规格
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:48:42
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 */
	public void deleteImageSpec_1(DeleteImageSpecCommand command) throws ImageException;
	
	/**
	 * @方法功能说明: 查询数量 
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午2:24:53
	 * @修改内容：
	 * @param qo
	 * @return
	 */
	Integer queryCountImageSpec_1(ImageSpecQo qo);
	
	/**
	 * @方法功能说明：查询图片规格
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:48:50
	 * @修改内容：
	 * @param qo
	 * @return
	 */
	public ImageSpecDTO queryUniqueImageSpec_1(ImageSpecQo qo);
	
	/**
	 * @方法功能说明：查询图片规格列表，分页
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:48:15
	 * @修改内容：
	 * @param pag
	 * @return
	 */
	public Pagination queryImageSpecPagination_1(ImageSpecQo qo);
}