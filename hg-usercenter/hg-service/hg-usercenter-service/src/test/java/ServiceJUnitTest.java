import com.alibaba.fastjson.JSON;
import hg.demo.member.common.domain.vo.DepartmentVO;
import hg.demo.member.common.spi.DepartmentSPI;
import hg.demo.member.service.spi.impl.DepartmentSPIService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author zhurz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ServiceJUnitTest {

	@Autowired
	DepartmentSPIService service;

	/**
	 * 测试Mybatis查询
	 */
	@Test
	public void testMybatisQuery() {
		System.out.println("查询>>部门列表");
		List<DepartmentVO> departmentVOs = service.queryListTop20();
		System.out.println(JSON.toJSONString(departmentVOs, true));
	}

}
