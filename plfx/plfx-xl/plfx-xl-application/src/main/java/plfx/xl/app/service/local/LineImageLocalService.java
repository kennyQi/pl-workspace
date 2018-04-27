package plfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.repository.DomainEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.xl.app.dao.LineImageDAO;
import plfx.xl.domain.model.line.LineImage;
import plfx.xl.pojo.qo.LineImageQO;

/**
 * 
 * @类功能说明：线路图片LocalService
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月22日下午4:22:49
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class LineImageLocalService extends BaseServiceImpl<LineImage, LineImageQO,LineImageDAO> {
	@Autowired
	private LineImageDAO dao;
	@Autowired
	private LineLocalService lineSer;
	@Autowired
	private DomainEventRepository domainEvent;
	@Override
	protected LineImageDAO getDao() {
		return dao;
	}
}