package hsl.app.service.spi.clickRecord;
import java.util.Date;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.UUIDGenerator;
import hg.log.clickrecord.ClickRecordQo;
import hg.log.po.clickrecord.ClickRecord;
import hsl.app.dao.log.PLZXClickRecordDao;
import hsl.app.dao.log.PLZXClickRecordRepository;
import hsl.pojo.command.clickRecord.PLZXClickRecordCommand;
import hsl.pojo.log.PLZXClickRecord;
import hsl.pojo.qo.log.PLZXClickRecordQo;
import hsl.spi.inter.clickRecord.PLZXClickRecordService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @类功能说明：票量直销点击记录
 * @类修改者：
 * @修改日期：2015年7月7日上午9:42:21
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年7月7日上午9:42:21
 */
@Service
public class PLZXClickRecordServiceImpl implements PLZXClickRecordService{
	@Autowired
	private PLZXClickRecordRepository plzxClickRecordRepository;
	@Autowired
	private PLZXClickRecordDao plzxClickRecordDao;
	/**
	 * 新建用户浏览记录
	 * @param command
	 */
	public void createPLZXClickRecord(PLZXClickRecordCommand command){
		PLZXClickRecordQo clickRecordQo = new PLZXClickRecordQo();
		clickRecordQo.setObjectId(command.getObjectId());
		PLZXClickRecord clickRecord = plzxClickRecordDao.queryUnique(clickRecordQo);
		//新建用户浏览记录
		if(clickRecord==null){
			clickRecord = new PLZXClickRecord();
			clickRecord.setId(UUIDGenerator.getUUID());
		}
		clickRecord.setFromIP(command.getFromIP());
		clickRecord.setCreateDate(new Date());
		clickRecord.setObjectId(command.getObjectId());
		clickRecord.setObjectType(command.getObjectType());
		clickRecord.setUrl(command.getUrl());
		if(StringUtils.isNotBlank(command.getUserId())){
			clickRecord.setUserId(command.getUserId());
		}
		plzxClickRecordRepository.save(clickRecord);
		
	}

	/**
	 * 查询用户浏览记录
	 * @param userClickRecordQO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PLZXClickRecord> queryPLZXClickRecord(PLZXClickRecordQo plzxClickRecordQo){
		//分页查询用户点击记录
		Pagination pagination=new Pagination();
		plzxClickRecordQo.setCreateDateDesc(true);
		pagination.setCondition(plzxClickRecordQo);
		pagination.setPageNo(1);
		pagination.setPageSize(plzxClickRecordQo.getPageSize());
		pagination=plzxClickRecordDao.queryPagination(pagination);
		List<PLZXClickRecord> list=(List<PLZXClickRecord>)pagination.getList();
		return list;
	}
}
