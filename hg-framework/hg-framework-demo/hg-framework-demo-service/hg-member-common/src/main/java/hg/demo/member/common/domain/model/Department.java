package hg.demo.member.common.domain.model;

import hg.demo.member.common.domain.model.def.M;
import hg.framework.common.base.BaseStringIdModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * 部门
 *
 * @author zhurz
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX + "DEPARTMENT")
public class Department extends BaseStringIdModel {

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 部门经理
	 */
	@OneToOne
	@JoinColumn(name = "MANAGER_MEMBER_ID")
	private Member manager;

	/**
	 * 操作员
	 */
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "department")
	private List<Member> operators;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Member getManager() {
		return manager;
	}

	public void setManager(Member manager) {
		this.manager = manager;
	}

	public List<Member> getOperators() {
		return operators;
	}

	public void setOperators(List<Member> operators) {
		this.operators = operators;
	}
}
