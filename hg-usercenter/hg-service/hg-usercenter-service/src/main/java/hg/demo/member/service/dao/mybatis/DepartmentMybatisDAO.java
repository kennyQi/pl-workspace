package hg.demo.member.service.dao.mybatis;

import hg.demo.member.common.domain.vo.DepartmentVO;

import java.util.List;

/**
 * @author zhurz
 */
public interface DepartmentMybatisDAO {

	/**
	 * 查询前20条部门信息
	 *
	 * @return
	 */
	List<DepartmentVO> queryListTop20();

}
