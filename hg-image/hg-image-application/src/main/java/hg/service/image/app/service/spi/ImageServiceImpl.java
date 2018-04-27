package hg.service.image.app.service.spi;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.EntityConvertUtils;
import hg.log.util.HgLogger;
import hg.service.image.app.service.local.ImageLocalService;
import hg.service.image.app.service.local.ImageSpecLocalService;
import hg.service.image.command.image.BatchCreateImageCommand;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.command.image.CreateTempImageCommand;
import hg.service.image.command.image.DeleteImageCommand;
import hg.service.image.command.image.ModifyImageCommand;
import hg.service.image.command.image.spec.CreateImageSpecCommand;
import hg.service.image.command.image.spec.DeleteImageSpecCommand;
import hg.service.image.command.image.spec.ModifyImageSpecCommand;
import hg.service.image.domain.model.Image;
import hg.service.image.domain.model.ImageSpec;
import hg.service.image.domain.model.ImageUseType;
import hg.service.image.domain.qo.ImageLocalQo;
import hg.service.image.domain.qo.ImageSpecLocalQo;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.dto.ImageSpecDTO;
import hg.service.image.pojo.dto.ImageUseTypeDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.pojo.qo.ImageSpecQo;
import hg.service.image.spi.inter.ImageService_1;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：图片服务Service接口实现类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月4日 上午9:11:49
 */
@Service("spiImageServiceImpl_1")
public class ImageServiceImpl implements ImageService_1 {
	@Autowired
	private ImageLocalService imgSer;
	@Autowired
	private ImageSpecLocalService specSer;


	@Override
	public String createTempImage_1(CreateTempImageCommand command) throws ImageException {
		return imgSer.createTempImage(command).getId();
	}
	
	/**
	 * 创建图片
	 * @throws ImageException
	 * @throws IOException
	 */
	@Override
	public String createImage_1(CreateImageCommand command) throws ImageException, IOException{
		return imgSer.createImage(command).getId();
	}
	
	/**
	 * @方法功能说明：批量创建图片
	 * @修改者名字：chenys
	 * @修改时间：2014年11月6日 上午3:47:52
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws IOException
	 */
	public void batchCreateImage_1(BatchCreateImageCommand command) throws ImageException,IOException{
		imgSer.batchCreateImage(command);
	}

	/**
	 * 删除图片
	 * @throws ImageException
	 */
	@Override
	public void deleteImage_1(DeleteImageCommand command) {
		imgSer.deleteImage(command);
	}

	/**
	 * 修改图片
	 * @throws ImageException
	 * @throws IOException 
	 */
	@Override
	public void modifyImage_1(ModifyImageCommand command) throws ImageException,IOException{
		imgSer.modifyImage(command);
	}
	
	/**
	 * 查询数量 
	 * @param qo
	 * @return
	 */
	@Override
	public Integer queryCountImage_1(ImageQo qo){
		ImageLocalQo localQo = new ImageLocalQo();
		if (null != qo)
			localQo = BeanMapperUtils.map(qo, ImageLocalQo.class);
		return imgSer.queryCount(localQo);
	}
	
	/**
	 * 查询图片
	 */
	@Override
	public ImageDTO queryUniqueImage_1(ImageQo qo) {
		ImageLocalQo localQo = new ImageLocalQo();
		if (null != qo) {
			localQo = BeanMapperUtils.map(qo, ImageLocalQo.class);
		}
		localQo.setFetchUseType(true);
		HgLogger.getInstance().info("yuqizhuang", "查询图片开始");
		Image img = imgSer.queryUnique(localQo);
		if (null != img) {
			HgLogger.getInstance().info("yuqizhuang", "查询图片返回->img=" + JSON.toJSONString(img));
			ImageUseType useType = img.getUseType();
			HgLogger.getInstance().info("yuqizhuang", "查询图片->useType=" + JSON.toJSONString(useType));
			ImageUseTypeDTO useTypeDTO = EntityConvertUtils.convertEntityToDto(useType,
					ImageUseTypeDTO.class);
			HgLogger.getInstance().info("yuqizhuang", "查询图片->useTypeDTO=" + JSON.toJSONString(useTypeDTO));
			ImageDTO imageDTO = EntityConvertUtils.convertEntityToDto(img, ImageDTO.class);
			HgLogger.getInstance().info("yuqizhuang", "查询图片->处理前imageDTO=" + JSON.toJSONString(imageDTO));
			Map<String, ImageSpecDTO> specImageMap = new HashMap<String, ImageSpecDTO>();
			for (Entry<String, ImageSpec> entry : img.getImageSpecMap().entrySet()) {
				ImageSpecDTO imageSpecDTO = EntityConvertUtils.convertEntityToDto(entry.getValue(), ImageSpecDTO.class);
				specImageMap.put(entry.getKey(), imageSpecDTO);
			}
			imageDTO.setSpecImageMap(specImageMap);
			imageDTO.setUseType(useTypeDTO);
			HgLogger.getInstance().info("yuqizhuang", "查询图片->处理后imageDTO=" + JSON.toJSONString(imageDTO));
			return imageDTO;
		}
		return null;
	}
	
	/**
	 * 查询图片列表，分页
	 */
	@Override
	public Pagination queryPaginationImage_1(Pagination pagination) {
		Object obj = pagination.getCondition();
		if (obj instanceof ImageQo) {
			// Qo转换，并重新设置查询条件
			ImageLocalQo localQo = BeanMapperUtils.map((ImageQo)obj, ImageLocalQo.class);
			pagination.setCondition(localQo);
		}
		pagination = imgSer.queryPagination(pagination);
		
		// Model至DTO转换
		List<?> list = pagination.getList();
		if (null == list || list.size() < 1)
			list = null;
		else
			list = EntityConvertUtils.convertEntityToDtoList(list,
					ImageDTO.class);
		pagination.setList(list);
		pagination.setCondition(obj);
		// 返回
		return pagination;
	}
	
	/**
	 * 查询图片列表，分页
	 */
	@Override
	public Pagination queryPaginationImage_1(ImageQo qo) {
		Pagination pagination = new Pagination();
		pagination.setCondition(BeanMapperUtils.map(qo,ImageLocalQo.class));//Qo转换，并重新设置查询条件
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		pagination = imgSer.queryPagination(pagination);
		
		// Model至DTO转换
		List<?> list = pagination.getList();
		if (null == list || list.size() < 1)
			list = null;
		else
			list = EntityConvertUtils.convertEntityToDtoList(list,
					ImageDTO.class);
		pagination.setList(list);
		pagination.setCondition(qo);
		// 返回
		return pagination;
	}

	//------------ 图片规格 ------------------//
	/**
	 * 创建图片规格
	 * @throws ImageException
	 * @throws IOException
	 */
	@Override
	public void createImageSpec_1(CreateImageSpecCommand command)	throws ImageException, IOException {
		specSer.createImageSpec(command);
	}

	public void modifyImageSpec_1(ModifyImageSpecCommand command) throws ImageException, IOException {
		specSer.modifyImageSpec(command);
	}
	
	/**
	 * 删除图片规格
	 * @throws ImageException
	 */
	@Override
	public void deleteImageSpec_1(DeleteImageSpecCommand command) throws ImageException {
		specSer.deleteImageSpec(command);
	}

	/**
	 * 查询数量 
	 * @param qo
	 * @return
	 */
	@Override
	public Integer queryCountImageSpec_1(ImageSpecQo qo){
		ImageSpecLocalQo localQo = new ImageSpecLocalQo();
		if (null != qo)
			localQo = BeanMapperUtils.map(qo, ImageSpecLocalQo.class);
		return specSer.queryCount(localQo);
	}
	
	/**
	 * 查询图片规格
	 */
	@Override
	public ImageSpecDTO queryUniqueImageSpec_1(ImageSpecQo qo) {
		ImageSpecLocalQo localQo = new ImageSpecLocalQo();
		if (null != qo)
			localQo = BeanMapperUtils.map(qo, ImageSpecLocalQo.class);
		ImageSpec spec = specSer.queryUnique(localQo);
		
		if (null != spec)
			return EntityConvertUtils.convertEntityToDto(spec,
					ImageSpecDTO.class);
		return null;
	}

	/**
	 * 查询图片规格列表，分页
	 */
	@Override
	public Pagination queryImageSpecPagination_1(ImageSpecQo qo) {
		// Qo转换，并重新设置查询条件
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		pagination.setCondition(BeanMapperUtils.map(qo,ImageSpecLocalQo.class));
		pagination = specSer.queryPagination(pagination);
		
		// Model至DTO转换
		List<?> list = pagination.getList();
		if (null == list || list.size() < 1)
			list = null;
		else
			list = EntityConvertUtils.convertEntityToDtoList(list,
					ImageSpecDTO.class);
		pagination.setList(list);
		pagination.setCondition(qo);
		// 返回
		return pagination;
	}

}