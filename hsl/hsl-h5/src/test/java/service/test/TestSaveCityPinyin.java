package service.test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.qo.CityQo;
import hg.system.service.CityService;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TestSaveCityPinyin {
	@Resource
	private CityService cityService;
	/**
	 * 测试发送邮件
	 * @throws MPException 
	 */
//	@Test
	public void testSaveCityPinyin(){
		
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		for (City city : cityList) {
			String name=city.getName();
			String pinyin="";
			String jian="";
			for (int i = 0; i <city.getName().length(); i++) {
				System.out.println(city.getName().charAt(i));
				HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
				defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
				defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
				String[] s;
					try {
						s = PinyinHelper.toHanyuPinyinStringArray(name.charAt(i),defaultFormat);
						pinyin+=s[0];
						jian+=s[0].substring(0, 1);
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
			}
			city.setCityQuanPin(pinyin);
			city.setCityJianPin(jian);
			cityService.update(city);
		}
	}
	@Test
	public void testqueryCityByGroup(){
			//查询所有的城市
			HashMap<String, List<City>> groupCity=new HashMap<String, List<City>>();
			CityQo cityQo = new CityQo();
			List<City> cityList = cityService.queryList(cityQo);
			String[] hotCitys={"北京","上海","广州","成都","重庆","西安","昆明","深圳","杭州","厦门","长沙","海口","武汉","乌鲁木齐","郑州","三亚","贵阳","南京","青岛","哈尔滨"};
			List<String> hc=Arrays.asList(hotCitys);
			//根据城市的首字母对城市分组
			for(City city:cityList){
				//得到城市的首字母的前缀
				String prefix=city.getCityJianPin().substring(0, 1);
				if(groupCity.size()>0){
					Set<String> keys=groupCity.keySet();
					//如果Map中含有当前的首字母的时候。直接获得该首字母的List，进行添加
					if(!hc.contains(city.getName().trim())){
						if(keys.contains(prefix)){
							List<City> cs=groupCity.get(prefix);
							cs.add(city);
						}else{
							List<City> cs =new ArrayList<City>();
							cs.add(city);
							groupCity.put(prefix,cs);
						}
					}
				}else{
					List<City> cs =new ArrayList<City>();
					cs.add(city);
					groupCity.put(prefix,cs);
				}
				//添加热门城市
				if(hc.contains(city.getName().trim())){
					List<City> hotC=groupCity.get("热门城市");
					if(hotC==null){
						List<City> h=new ArrayList<City>();
						h.add(city);
						groupCity.put("热门城市",h);
					}else{
						hotC.add(city);
					}
				}
			}
			System.out.println(JSON.toJSONString(groupCity,SerializerFeature.DisableCircularReferenceDetect));
//			HgLogger.getInstance().info("chenxy", "JPCtrl->init->queryCityAirCode[查询全部城市三字码成功！]:"+ JSON.toJSONString(groupCity));
			//将jedis连接返回给jedisPool中
			//设置城市列表，供页面使用
	}
}
