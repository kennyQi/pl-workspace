package hg.demo.member.common.spi;

import hg.demo.member.common.spi.command.member.ModifyMemberCommand;
import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.demo.member.common.spi.command.member.CreateMemberCommand;
import hg.demo.member.common.spi.command.member.DeleteMemberCommand;
import hg.demo.member.common.domain.model.Member;
import hg.demo.member.common.spi.qo.MemberSQO;

import java.util.List;

/**
 * @author zhurz
 */
public interface MemberSPI extends BaseServiceProviderInterface {

	Member create(CreateMemberCommand command);

	Member modify(ModifyMemberCommand command);

	void delete(DeleteMemberCommand command);

	Pagination<Member> queryMemberPagination(MemberSQO sqo);

	List<Member> queryMemberList(MemberSQO sqo);

	Member queryMember(MemberSQO sqo);

}
