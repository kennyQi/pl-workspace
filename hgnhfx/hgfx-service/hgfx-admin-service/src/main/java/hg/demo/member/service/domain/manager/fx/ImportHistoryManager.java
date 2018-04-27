package hg.demo.member.service.domain.manager.fx;

import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.importHistory.CreateImportHistoryCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.ImportHistory;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午11:56:25
 * @版本： V1.0
 */
public class ImportHistoryManager extends BaseDomainManager<ImportHistory> {

	public ImportHistoryManager(ImportHistory entity) {
		super(entity);
	}

	public ImportHistoryManager create(CreateImportHistoryCommand command,Distributor distributor,DistributorUser dstributorUser){
		entity.setId(UUIDGenerator.getUUID());
		entity.setDistributor(distributor);
		entity.setDstributorUser(dstributorUser);
		entity.setImportDate(command.getImportDate());
		entity.setFileName(command.getFileName());
		entity.setFilePath(command.getFilePath());
		entity.setNormalNum(command.getNormalNum());
		entity.setErrorNUM(command.getErrorNUM());
		entity.setNormalMileages(command.getNormalMileages());
		entity.setErrorMileages(command.getErrorMileages());
		entity.setContentMD5(command.getContentMD5());
		return this;
	}
	
}
