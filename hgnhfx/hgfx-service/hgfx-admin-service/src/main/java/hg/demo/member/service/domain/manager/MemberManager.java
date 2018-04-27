package hg.demo.member.service.domain.manager;

import hg.framework.common.base.BaseDomainManager;
import hg.demo.member.common.domain.model.Member;

/**
 * @author zhurz
 */
public class MemberManager extends BaseDomainManager<Member> {

	public MemberManager(Member entity) {
		super(entity);
	}

}
