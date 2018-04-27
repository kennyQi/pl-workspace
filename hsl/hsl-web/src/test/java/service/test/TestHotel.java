package service.test;

import hsl.pojo.command.HotelGeoCommand;
import hsl.pojo.dto.hotel.util.CommericalLocationDTO;
import hsl.pojo.dto.hotel.util.DistrictDTO;
import hsl.pojo.dto.hotel.util.HotelGeoDTO;
import hsl.pojo.qo.hotel.HotelGeoQO;
import hsl.spi.inter.hotel.HotelGeoService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import oracle.sql.DATE;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestHotel {
	@Resource
	private HotelGeoService hotelGeoService;
	@Autowired
	private JedisPool jedisPool;

	/*
	 * @Test public void test1() { try { HotelGeoQO hotelGeoQO=new HotelGeoQO();
	 * hotelGeoQO.setCityName("上海"); HotelGeoDTO cc=
	 * hotelGeoService.queryUnique(hotelGeoQO);
	 * System.out.println(JSON.toJSONString(cc)); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * }
	 */
	 @Test
	public void test() {
		// 获取商业区域
		try {
			SAXReader reader = new SAXReader();
			URL url1 = new URL("http://api.elong.com/xml/v2.0/hotel/geo_cn.xml");
			Document document = reader.read(url1);
			Element root = document.getRootElement();
			Element element = root.element("HotelGeoList");
			List<Element> geos = element.elements();
			System.out.println(geos.size());
			// List<HotelGeo> hotelGeos=new ArrayList<HotelGeo>();
			for (Element e : geos) {
				HotelGeoCommand hotelGeo = new HotelGeoCommand();
				List<Attribute> attributes = e.attributes();
				for (Attribute attr : attributes) {
					if (attr.getName().equals("Country")) {
						hotelGeo.setCountry(attr.getValue());
					} else if (attr.getName().equals("ProvinceName")) {
						hotelGeo.setProvinceName(attr.getValue());
					} else if (attr.getName().equals("ProvinceId")) {
						hotelGeo.setProvinceId(attr.getValue());
					} else if (attr.getName().equals("CityName")) {
						hotelGeo.setCityName(attr.getValue());
					} else if (attr.getName().equals("CityCode")) {
						hotelGeo.setCityCode(attr.getValue());
					}
				}
				Element districts = e.element("Districts");
				if (districts != null) {
					List<Element> locations = districts.elements();
					List<DistrictDTO> dList = new ArrayList<DistrictDTO>();
					for (Element e1 : locations) {
						DistrictDTO district = new DistrictDTO();
						List<Attribute> as = e1.attributes();
						for (Attribute attr : as) {
							if (attr.getName().equals("Id")) {
								district.setDistrictId(attr.getValue());
							} else if (attr.getName().equals("Name")) {
								district.setName(attr.getValue());
							}
						}
						dList.add(district);
					}
					hotelGeo.setDistricts(dList);
				}
				Element cElement = e.element("CommericalLocations");
				if (cElement != null) {
					List<Element> locations1 = cElement.elements();
					List<CommericalLocationDTO> cList = new ArrayList<CommericalLocationDTO>();
					for (Element e1 : locations1) {
						CommericalLocationDTO commericalLocation = new CommericalLocationDTO();
						List<Attribute> as = e1.attributes();
						for (Attribute attr : as) {
							if (attr.getName().equals("Id")) {
								commericalLocation.setCommericalLocationId(attr
										.getValue());
							} else if (attr.getName().equals("Name")) {
								commericalLocation.setName(attr.getValue());
							}
						}
						cList.add(commericalLocation);
					}
					hotelGeo.setCommericalLocations(cList);
				}
				System.out.println(JSON.toJSONString(hotelGeo));
				this.hotelGeoService.create(hotelGeo);
				System.out.println(JSON.toJSONString(hotelGeo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * @SuppressWarnings("unchecked") public static void test1(){ try {
	 * SAXReader reader = new SAXReader(); URL url1 = new
	 * URL("http://api.elong.com/xml/v2.0/hotel/brand_cn.xml"); Document
	 * document = reader.read(url1); Element root = document.getRootElement();
	 * // Element element = root.elements("HotelBrand "); List<Element>
	 * geos=root.elements(); System.out.println(geos.size()); List<HotelBrand>
	 * hotelBrands=new ArrayList<HotelBrand>(); for(Element e:geos){ HotelBrand
	 * hotelGeo=new HotelBrand(); List<Attribute> attributes = e.attributes();
	 * for (Attribute attr : attributes) { if(attr.getName().equals("BrandId")){
	 * hotelGeo.setBrandId(attr.getValue()); }else
	 * if(attr.getName().equals("GroupId")){
	 * hotelGeo.setGroupId(attr.getValue()); }else
	 * if(attr.getName().equals("ShortName")){
	 * hotelGeo.setShortName(attr.getValue()); }else
	 * if(attr.getName().equals("Name")){ hotelGeo.setName(attr.getValue());
	 * }else if(attr.getName().equals("Letters")){
	 * hotelGeo.setLetters(attr.getValue()); } } hotelBrands.add(hotelGeo);
	 * System.out.println(JSON.toJSONString(hotelGeo)); } } catch (Exception e)
	 * { e.printStackTrace(); } }
	 */
	/*
	 * public void test2(){ Jedis jedis = null; jedis = jedisPool.getResource();
	 * String cityGroup = jedis.get("HotelGeoList");
	 * 
	 * @SuppressWarnings("unchecked") List<HotelGeo>
	 * hotelGeoList=JSON.parseObject(cityGroup, List.class);
	 * if(hotelGeoList!=null&&hotelGeoList.size()>0){ //将jedis连接返回给jedisPool中
	 * jedisPool.returnResource(jedis); }else{
	 * jedis.set("HotelGeoList",JSON.toJSONString("")); //将jedis连接返回给jedisPool中
	 * jedisPool.returnResource(jedis); } }
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test3() {
		// 获取商业区域
		try {
			SAXReader reader = new SAXReader();
			long strtime=System.currentTimeMillis();
			URL url1 = new URL("http://api.elong.com/xml/v2.0/hotel/hotellist.xml");
			Document document = reader.read(url1);
			Element root = document.getRootElement();
			Element element = root.element("Hotels");
			List<Element> hList = element.elements();
			long ed=System.currentTimeMillis();
			System.out.println("耗时>>>:"+(ed-strtime)+"ms");
			System.out.println("耗时>>>:"+(ed-strtime)/3600+"min");
			System.out.println(">>>>>>>>>>>>"+hList.size());
			// List<HotelGeo> hotelGeos=new ArrayList<HotelGeo>();
			for (Element e : hList) {
				List<Attribute> attributes = e.attributes();
				for (Attribute attr : attributes) {
					if (attr.getName().equals("HotelId")) {
						System.out.println("1111>>>>>"+attr.getValue());
					} else if (attr.getName().equals("UpdatedTime")) {
						System.out.println("2222>>>>>"+attr.getValue());
					} else if (attr.getName().equals("Modification")) {
						System.out.println("3333>>>>>"+attr.getValue());
					} else if (attr.getName().equals("Products")) {
						System.out.println("4444>>>>>"+attr.getValue());
					} else if (attr.getName().equals("Status")) {
						System.out.println("5555>>>>>"+attr.getValue());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
