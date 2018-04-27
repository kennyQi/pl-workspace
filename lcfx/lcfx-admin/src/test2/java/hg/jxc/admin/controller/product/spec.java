package hg.jxc.admin.controller.product;
import static org.junit.Assert.*;

import java.util.List;

import hg.pojo.qo.SupplierQO;
import jxc.app.service.product.SpecValueService;
import jxc.app.service.supplier.SupplierService;
import jxc.domain.model.supplier.Supplier;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class spec {

	@Test
	public void test() {
        ApplicationContext apt = new ClassPathXmlApplicationContext("applicationContext.xml");
        SupplierService spec = (SupplierService)apt.getBean("supplierService");
        SupplierQO qo = new SupplierQO();
        
		List<Supplier> supplierList = spec.queryList(qo);
	}

}
