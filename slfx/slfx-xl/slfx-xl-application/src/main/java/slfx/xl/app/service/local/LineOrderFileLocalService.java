package slfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import slfx.xl.app.dao.LineOrderFileDAO;
import slfx.xl.domain.model.order.LineOrderFile;
import slfx.xl.pojo.command.order.UploadLineOrderFileCommand;
import slfx.xl.pojo.qo.LineOrderFileQO;

/**
 * 
 * @类功能说明：线路订单文件LocalService实现类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月24日上午9:29:09
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class LineOrderFileLocalService extends BaseServiceImpl<LineOrderFile, LineOrderFileQO, LineOrderFileDAO>{

	@Autowired
	private LineOrderFileDAO lineOrderFileDAO;
	@Autowired
	private DomainEventRepository domainEventRepository;
	
	@Override
	protected LineOrderFileDAO getDao() {
		return lineOrderFileDAO;
	}
	
	/**
	 * 
	 * @方法功能说明：保存线路订单文件上传记录
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月24日下午1:52:50
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public Boolean uploadLineOrderFile(UploadLineOrderFileCommand command){
		
		try{
			
			LineOrderFile lineOrderFile = new LineOrderFile();
			lineOrderFile.uploadLineOrderFile(command);
			lineOrderFileDAO.save(lineOrderFile);
			
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.LineOrderFile","uploadLineOrderFile",JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "LineOrderFileLocalService->uploadLineOrderFile->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		
		
		
	}

}
