package dzpw.test;

import java.util.List;

import hg.dzpw.app.pojo.qo.ScenicSpotPicQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.ScenicSpotPicLocalService;
import hg.dzpw.domain.model.scenicspot.ScenicSpotPic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class TestScenicService {
	@Autowired
	private ScenicSpotLocalService scenicService;
	@Autowired
	private DealerLocalService dealerLocalService;
	@Autowired
	private ScenicSpotPicLocalService picLocalService;
	
	@Test
	public void getNextCode(){
		String code=dealerLocalService.getNextKey();
		System.out.println(code);
	}
	
	@Test
	public void getPic(){
		String scenicSpotId="1f33c580bc604eef8a1d757074e74097";
		ScenicSpotPicQo qo=new ScenicSpotPicQo();
		ScenicSpotQo scenicSpotQo=new ScenicSpotQo();
		scenicSpotQo.setId(scenicSpotId);
		qo.setScenicSpotQo(scenicSpotQo);
		List<ScenicSpotPic> pics=picLocalService.queryList(qo);
		for (ScenicSpotPic scenicSpotPic : pics) {
			if (scenicSpotPic.getScenicSpot()!=null) {
				System.out.println(scenicSpotPic.getScenicSpot().getBaseInfo().getName());
			}
		}
	}
}
