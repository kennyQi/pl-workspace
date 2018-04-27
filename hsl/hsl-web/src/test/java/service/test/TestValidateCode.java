package service.test;

import hsl.app.component.manager.LoginUserMobileValidateCodeManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestValidateCode {
	@Resource
	private LoginUserMobileValidateCodeManager manager;

	@Test
	public void test() {
		manager.sendValidateCode("123", "5ad7895484414d759aa273fa9701f147");
	}

	@Test
	public void test2() {
		System.out.println(manager.checkValidateCode("123", "5ad7895484414d759aa273fa9701f147", "774409"));
	}
}
