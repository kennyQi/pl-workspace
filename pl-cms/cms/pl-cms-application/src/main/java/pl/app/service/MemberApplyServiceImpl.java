package pl.app.service;
import hg.common.component.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.dao.MemberApplyDao;
import pl.cms.domain.entity.apply.MemberApply;
import pl.cms.pojo.command.apply.CreateMemberApplyCommand;
import pl.cms.pojo.qo.MemberApplyQO;
@Service
@Transactional
public class MemberApplyServiceImpl extends BaseServiceImpl<MemberApply, MemberApplyQO, MemberApplyDao> {
	
	@Autowired
	private MemberApplyDao memberApplyDao;
	
	public void create(CreateMemberApplyCommand command) {
		MemberApply memberApply = new MemberApply();
		memberApply.create(command);
		getDao().save(memberApply);
	}
	
 	@Override
	protected MemberApplyDao getDao() {
		return memberApplyDao;
	}
	
}
