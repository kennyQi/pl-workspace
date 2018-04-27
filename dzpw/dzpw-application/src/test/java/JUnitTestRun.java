import hg.common.util.JSONUtils;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.DealerScenicspotSettingQo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.domain.model.dealer.Dealer;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class JUnitTestRun {

	@Autowired
	private DealerLocalService service;

	@Test
	public void test() {
		DealerQo qo = new DealerQo();
		DealerScenicspotSettingQo settingQo = new DealerScenicspotSettingQo();
		qo.setScenicspotSettingQo(settingQo);
		settingQo.setScenicSpotId("1c1f21089d654214865a9b3c50b16d37");
		settingQo.setUseable(false);

		List<Dealer> list = service.queryList(qo);
		JSONArray json = (JSONArray) JSONObject.parse(JSONUtils.c(list));
		String fjson = JSONObject.toJSONString(json, true);
		System.out.println(fjson);
	}

	public static void main(String[] args) {

	}
}
