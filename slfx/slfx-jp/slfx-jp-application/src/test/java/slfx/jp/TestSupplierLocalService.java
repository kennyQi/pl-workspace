//
//package slfx.jp;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.pojo.dto.supplier.SupplierDTO;
//import slfx.jp.qo.admin.supplier.SupplierQO;
//import slfx.jp.spi.inter.supplier.SupplierService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp-test.xml" })
//public class TestSupplierLocalService {
//
//	
//	@Autowired
//	private SupplierService supplierService;
//	
//	@Test
//	public void test(){
//		SupplierQO qo = new SupplierQO();
//		qo.setStatus("0");
//		
//		List<SupplierDTO> list = supplierService.getSupplierList(qo);
//		Set<String> qianYuePingTaiSet = new HashSet<String>();
//		for (SupplierDTO supplierDTO : list) {
//			qianYuePingTaiSet.add(supplierDTO.getCode());
//			qianYuePingTaiSet.add(supplierDTO.getNumber());
//		}
//
//		System.out.println(qianYuePingTaiSet);
//	}
//	
//	public static void main(String[] args) {
//		double d=987.12;
//		
//		System.out.println(d%10);
//	}
// }
//	
//
////package slfx.jp;
////
////import java.util.HashSet;
////import java.util.List;
////import java.util.Set;
////
////import org.apache.commons.lang.StringUtils;
////import org.junit.Test;
////import org.junit.runner.RunWith;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.test.context.ContextConfiguration;
////import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
////
////import slfx.jp.pojo.dto.supplier.SupplierDTO;
////import slfx.jp.spi.inter.supplier.SupplierService;
////import slfx.jp.spi.qo.admin.supplier.SupplierQO;
////
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp-test.xml" })
////public class TestSupplierLocalService {
////
////	
////	@Autowired
////	private SupplierService supplierService;
////	
////	@Test
////	public void test(){
////		SupplierQO qo = new SupplierQO();
////		qo.setStatus("0");
////		
////		List<SupplierDTO> list = supplierService.getSupplierList(qo);
////		Set<String> qianYuePingTaiSet = new HashSet<String>();
////		for (SupplierDTO supplierDTO : list) {
////			qianYuePingTaiSet.add(supplierDTO.getCode());
////			qianYuePingTaiSet.add(supplierDTO.getNumber());
////		}
////
////		System.out.println(qianYuePingTaiSet);
////	}
////	
////	public static void main(String[] args) {
//////		double d=987.12;
//////		
//////		System.out.println(d%10);
////		
////		String resultDetails ="2014112112427121^369.00^SUCCESS$ply365@ply365.com^2088611359544680^1.48^SUCCESS#2014112011771421^339.00^SUCCESS$ply365@ply365.com^2088611359544680^1.36^SUCCESS";
////		String[] details = resultDetails.split("#");//解析每一条数据集
////			
////			for (String detail : details) {
////				detail.indexOf("$");
////				int index = detail.indexOf("$");
////				if (index != -1)  {
////					detail = detail.substring(0, index);
////				}
////				
////				String[] datas = detail.split("^"); //解析每一条数据集中的字段，第一项为支付宝单号，第二项为退款金额，第三项为退款状态
////				String payTradeNo = datas[0];
////				//String status = datas[2];
////				String payTradeNo2 =detail.substring(0,16);
////				boolean status2 = detail.endsWith("SUCCESS");
////				System.out.println(status2);
////			}
////	}
////	
////}
//
