package pl.app.service;
import hg.common.component.BaseServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.app.dao.ContributionDao;
import pl.app.dao.ContributionRecordDao;
import pl.cms.domain.entity.article.ArticleStatus;
import pl.cms.domain.entity.contribution.Contribution;
import pl.cms.domain.entity.contribution.ContributionRecord;
import pl.cms.pojo.command.contribution.CheckContributionCommand;
import pl.cms.pojo.command.contribution.CreateContributionCommand;
import pl.cms.pojo.command.scenic.DeleteScenicCommand;
import pl.cms.pojo.command.scenic.ModifyScenicCommand;
import pl.cms.pojo.qo.ContributionQO;
@Service
@Transactional
public class ContributionServiceImpl extends BaseServiceImpl<Contribution, ContributionQO, ContributionDao> {
    @Autowired
    private ContributionDao contributionDao;
    @Autowired
    private ContributionRecordDao contributionRecordDao;
	public void createContribution(CreateContributionCommand command) {
		Contribution contribution=new Contribution();
		contribution.create(command);
		contributionDao.save(contribution);
	}
	public List<ContributionRecord> queryContributionRecordById(ContributionQO qo){
		return contributionRecordDao.queryList(qo);
	}
	public void modifyContribution(ModifyScenicCommand command){
	}
	public void checkContribution(CheckContributionCommand  command){
		ContributionQO qo=new ContributionQO();
		qo.setId(command.getContributionId());
		Contribution contribution=contributionDao.queryUnique(qo);
		contribution.setCheckStatus(command.getCheckStatus()?Contribution.CONTRIBUTION_APPROVE:Contribution.CONTRIBUTION_DISAPPROVE);
		contributionDao.update(contribution);
		ContributionRecord contributionRecord=new ContributionRecord();
		contributionRecord.createContributionRecord(command);
		contributionRecord.setContribution(contribution);
		contributionRecordDao.save(contributionRecord);
	}
	public void support(String id){
		ContributionQO qo=new ContributionQO();
		qo.setId(id);
		Contribution contribution=contributionDao.queryUnique(qo);
		ArticleStatus articleStatus=contribution.getStatus();
		articleStatus.setSupportCount(articleStatus.getSupportCount()+1);
		contribution.setStatus(articleStatus);
		contributionDao.update(contribution);
	}
	public void cancelSupport(String id){
		ContributionQO qo=new ContributionQO();
		qo.setId(id);
		Contribution contribution=contributionDao.queryUnique(qo);
		ArticleStatus articleStatus=contribution.getStatus();
		articleStatus.setSupportCount(articleStatus.getSupportCount()-1);
		contribution.setStatus(articleStatus);
		contributionDao.update(contribution);
	}
	public void deleteContribution(DeleteScenicCommand command) throws Exception {
	}
	@Override
	protected ContributionDao getDao() {
		return contributionDao;
	}
	/*public Scenic queryUnique(ScenicQO qo) {
		Scenic scenic=getDao().queryUnique(qo);
		Hibernate.initialize(scenic.getScenicSpotGeographyInfo().getProvince());
		Hibernate.initialize(scenic.getScenicSpotGeographyInfo().getCity());
		return scenic;
	}*/
}
