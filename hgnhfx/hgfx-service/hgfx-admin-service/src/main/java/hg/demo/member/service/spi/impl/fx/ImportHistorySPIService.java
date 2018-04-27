package hg.demo.member.service.spi.impl.fx;

import hg.demo.member.service.dao.hibernate.fx.DistributorDAO;
import hg.demo.member.service.dao.hibernate.fx.DistributorUserDAO;
import hg.demo.member.service.dao.hibernate.fx.ImportHistoryDAO;
import hg.demo.member.service.domain.manager.fx.ImportHistoryManager;
import hg.demo.member.service.qo.hibernate.fx.ImportHistoryQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.importHistory.CreateImportHistoryCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.ImportHistory;
import hg.fx.spi.ImportHistorySPI;
import hg.fx.spi.qo.ImportHistorySQO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午11:57:27
 * @版本： V1.0
 */
@Transactional
@Service("importHistorySPIService")
public class ImportHistorySPIService extends BaseService implements ImportHistorySPI {
	
	@Autowired
	private ImportHistoryDAO importHistoryDAO;
	@Autowired
	private DistributorDAO distributorDAO;
	@Autowired
	private DistributorUserDAO distributorUserDAO;

	@Override
	public ImportHistory create(CreateImportHistoryCommand command) {
		ImportHistory importHistory = new ImportHistory();
		Distributor distributor = null;
		DistributorUser dstributorUser = null;
		if(StringUtils.isNotBlank(command.getDstributorUserId())){
			dstributorUser = distributorUserDAO.get(command.getDstributorUserId());
			distributor = dstributorUser.getDistributor();
		}
		return importHistoryDAO.save(new ImportHistoryManager(importHistory).create(command, distributor, dstributorUser).get());
	}
	
	@Override
	public Pagination<ImportHistory> queryPagination(ImportHistorySQO sqo) {
		return importHistoryDAO.queryPagination(ImportHistoryQO.build(sqo));
	}

	@Override
	public Map<String, String> queryDstributorUser(ImportHistorySQO sqo) {
		ImportHistoryQO importHistoryQO = ImportHistoryQO.build(sqo);
		importHistoryQO.setQueryDistributorUser(true);
		List<ImportHistory> importHistories= importHistoryDAO.queryList(importHistoryQO);
		HashMap<String,String> hashMap = new HashMap<>();
		for(int i=0;i<importHistories.size();i++){
			String distributorUserID = JSON.toJSONString(importHistories.get(i)).replaceAll("\"", "");
			DistributorUser distributorUser = distributorUserDAO.get(distributorUserID);
			if(distributorUser!=null&&StringUtils.isNotBlank(distributorUser.getName())){
				hashMap.put(distributorUserID, distributorUser.getName());
			}
		}
		return hashMap;
	}

	@Override
	public List<ImportHistory> queryList(ImportHistorySQO sqo) {
		return importHistoryDAO.queryList(ImportHistoryQO.build(sqo));
	}

}
