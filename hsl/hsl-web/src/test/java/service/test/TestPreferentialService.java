package service.test;

import hg.common.page.Pagination;
import hsl.app.service.spi.preferential.PreferentialServiceImpl;
import hsl.domain.model.mp.ad.SpecialOfferMp;
import hsl.pojo.dto.ad.HslAdConstant;
import hsl.pojo.dto.preferential.PreferentialDTO;
import hsl.pojo.qo.preferential.HslPreferentialQO;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestPreferentialService {
	
	@Resource
	private PreferentialServiceImpl preferentialServiceImpl;
	@Test
	public void queryPagination(){
		Pagination pagination=new Pagination();
		HslPreferentialQO hslPreferentialQO=new HslPreferentialQO();
		try {
			//hslPreferentialQO.setPositionId(HslAdConstant.THZQ);
			hslPreferentialQO.setIsShow(true);
			Integer pageNo=null;
			Integer pageSize=null;
			pageNo=pageNo==null?new Integer(1):pageNo;
			pageSize=pageSize==null?new Integer(20):pageSize;
			pagination.setCondition(hslPreferentialQO);
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			Pagination paginations=preferentialServiceImpl.queryPagination(pagination);
			System.out.println(paginations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void queryUnique(){
		try {
			
			HslPreferentialQO hslPreferentialQO=new HslPreferentialQO();
			hslPreferentialQO.setId("84566b3811fa42e5901d0db1995ccaf7");//本地Preferential的   Id
			PreferentialDTO preferentialDTO=preferentialServiceImpl.queryUnique(hslPreferentialQO);
			System.out.println(preferentialDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
