package hg.demo.test;

import hg.demo.member.common.domain.model.Department;
import hg.demo.member.common.spi.DepartmentSPI;
import hg.demo.member.common.spi.command.department.CreateDepartmentCommand;
import hg.demo.member.common.spi.qo.DepartmentSQO;
import hg.framework.common.model.Pagination;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhurz
 */
@Service
public class TestRemoteService {

	@Resource
	private DepartmentSPI departmentService;

	public void testCreate() {
		long a = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			CreateDepartmentCommand command = new CreateDepartmentCommand();
			command.setName("hello " + i);
			Department department = departmentService.create(command);
			System.out.println("创建成功");
			System.out.println(department.getId());
		}
		System.out.println("消耗时间：" + (System.currentTimeMillis() - a));
	}

	public void testQueryPagination() {
		DepartmentSQO sqo = new DepartmentSQO();
		sqo.setName("5");
		sqo.getLimit().setPageNo(2);
		sqo.getLimit().setPageSize(100);
		Pagination<Department> pagination = departmentService.queryDepartmentPagination(sqo);
		System.out.println(pagination.toFormatJSONString());
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestRemoteService ser = context.getBean(TestRemoteService.class);
		ser.testQueryPagination();
	}

}
