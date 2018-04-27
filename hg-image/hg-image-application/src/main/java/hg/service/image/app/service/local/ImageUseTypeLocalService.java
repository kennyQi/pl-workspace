package hg.service.image.app.service.local;

import java.util.List;
import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.service.image.app.common.Assert;
import hg.service.image.app.dao.ImageUseTypeDao;
import hg.service.image.command.image.usetype.CreateImageUseTypeCommand;
import hg.service.image.command.image.usetype.DeleteImageUseTypeCommand;
import hg.service.image.command.image.usetype.ModifyImageUseTypeCommand;
import hg.service.image.domain.model.ImageUseType;
import hg.service.image.domain.qo.ImageUseTypeLocalQo;
import hg.service.image.pojo.exception.ImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：用途本地Service
 * @修改者名字：chenys
 * @修改时间：2014年12月15日 下午6:22:47
 * @修改内容：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月3日 下午10:58:26
 */
@Service
@Transactional
public class ImageUseTypeLocalService extends BaseServiceImpl<ImageUseType,ImageUseTypeLocalQo,ImageUseTypeDao> {
	@Autowired
	private ImageUseTypeDao dao;
	@Autowired
	private DomainEventRepository domainEvent;
	
	@Override
	protected ImageUseTypeDao getDao() {
		return dao;
	}
	
	/**
	 * @方法功能说明：创建用途
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午10:04:14
	 * @修改内容：
	 * @param command
	 * @throws ImageException 
	 */
	public void createImageSpecKeyUseType(CreateImageUseTypeCommand command) throws ImageException {
		Assert.notCommand(command,ImageException.NEED_USETYPE_WITHOUTPARAM,"创建用途指令");
		Assert.notPorjectandEnv(command.getFromProjectId(),command.getFromProjectEnvName());
		Assert.notEmpty(command.getTitle(),ImageException.NEED_USETYPE_WITHOUTPARAM,"用途标题");
//		Assert.notList(command.getImageSpecKeys(),ImageException.NEED_USETYPE_WITHOUTPARAM,"图片规格KEY");
		
		//保存图片用途KEY
		ImageUseType useKeys = new ImageUseType();
		useKeys.create(command);
		dao.save(useKeys);
		
		//领域日志
		DomainEvent event = new DomainEvent("hg.service.image.domain.model.ImageUseType","createImageSpecKeyUseType",JSON.toJSONString(command));
		domainEvent.save(event);
	}

	/**
	 * @方法功能说明：删除用途
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午10:04:14
	 * @修改内容：
	 * @param command
	 * @throws ImageException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteImageSpecKeyUseType(DeleteImageUseTypeCommand command) throws ImageException {
		Assert.notCommand(command,ImageException.NEED_USETYPE_WITHOUTPARAM,"删除用途指令");
		Assert.notList(command.getUseTypeIds(),ImageException.NEED_USETYPE_WITHOUTPARAM,"用途Id");
		dao.deleteByIds((List)command.getUseTypeIds());
		
		//领域日志
		DomainEvent event = new DomainEvent("hg.service.image.domain.model.ImageUseType","deleteImageSpecKeyUseType",JSON.toJSONString(command));
		domainEvent.save(event);
	}

	/**
	 * @方法功能说明：修改用途
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午10:04:14
	 * @修改内容：
	 * @param command
	 * @throws ImageException 
	 */
	public void modifyImageSpecKeyUseType(ModifyImageUseTypeCommand command) throws ImageException {
		Assert.notCommand(command,ImageException.NEED_USETYPE_WITHOUTPARAM,"修改用途指令");
		Assert.notEmpty(command.getUseTypeId(),ImageException.NEED_USETYPE_WITHOUTPARAM,"用途id");
		
		ImageUseType useType = dao.get(command.getUseTypeId());
		Assert.notNull(useType,ImageException.RESULT_USETYPE_NOTFOUND,"用途");
		//更新用途
		useType.modify(command);
		dao.update(useType);
		
		//领域日志
		DomainEvent event = new DomainEvent("hg.service.image.domain.model.ImageUseType","modifyImageSpecKeyUseType",JSON.toJSONString(command));
		domainEvent.save(event);
	}
}