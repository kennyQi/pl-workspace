package slfx.xl.app.service.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import slfx.xl.app.component.Assert;
import slfx.xl.app.dao.LinePictureFolderDAO;
import slfx.xl.domain.model.line.Line;
import slfx.xl.domain.model.line.LinePictureFolder;
import slfx.xl.pojo.command.line.CreateLinePictureFolderCommand;
import slfx.xl.pojo.dto.line.LinePictureFolderDTO;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.qo.LinePictureFolderQO;
import slfx.xl.pojo.qo.LineQO;
import hg.common.component.BaseServiceImpl;
import hg.common.util.EntityConvertUtils;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;

/**
 * @类功能说明：图片文件夹LocalService
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshuo
 * @创建时间：2014年12月18日上午9:40:29
 * @版本：V1.0
 */
@Service
@Transactional
public class LinePictureFolderLocalService extends BaseServiceImpl<LinePictureFolder, LinePictureFolderQO,LinePictureFolderDAO> {
	@Autowired
	private LinePictureFolderDAO dao;
	@Autowired
	private LineLocalService lineSer;
	@Autowired
	private DomainEventRepository domainEvent;
	
	@Override
	protected LinePictureFolderDAO getDao() {
		return dao;
	}
	
	/**
	 * @方法功能说明：创建图片文件夹
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-18下午2:38:07
	 * @param command
	 * @throws SlfxXlException
	 */
	public LinePictureFolderDTO createFolder(CreateLinePictureFolderCommand command) throws SlfxXlException{
		Assert.notCommand(command,SlfxXlException.FOLDER_WITHOUTPARAM_NOT_EXISTS,"创建线路图片文件夹指令");
		Assert.notEmpty(command.getLineId(),SlfxXlException.LINE_ID_IS_NULL,"线路Id");
		
		//线路
		LineQO lineQO = new LineQO(command.getLineId());
		Line line = lineSer.queryUnique(lineQO);
		Assert.notModel(line,SlfxXlException.RESULT_LINE_NOT_FOUND,"线路");
		
		//查询名称并设置
		LinePictureFolderQO qo = new LinePictureFolderQO();
		qo.setMatter(false);
		qo.setLineQO(lineQO);
		command.setName("行程"+(dao.queryCount(qo)+1));
		
		//创建线路图片文件夹
		LinePictureFolder folder = new LinePictureFolder();
		folder.create(command, line);
		dao.save(folder);
		
		//领域日志
		DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.LinePictureFolder","createFolder",JSON.toJSONString(command));
		domainEvent.save(event);
		
		//返回线路图片文件夹
		return EntityConvertUtils.convertEntityToDto(folder,LinePictureFolderDTO.class);
	}
}