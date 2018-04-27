/*package hsl.pojo.util;

import hsl.pojo.dto.hotel.util.CommericalLocation;
import hsl.pojo.dto.hotel.util.District;
import hsl.pojo.dto.hotel.util.HotelBrand;
import hsl.pojo.dto.hotel.util.HotelGeo;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

*//**
 * @类功能说明：酒店工具类
 * @类修改者：
 * @修改日期：2015年6月30日上午10:36:42
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年6月30日上午10:36:42
 *//*
public class HotelUtil {

	private static HotelUtil hotelUtil = null;

	private HotelUtil() {

	}

	public static HotelUtil getInstance() {
		if (hotelUtil == null) {
			hotelUtil = new HotelUtil();
		}
		return hotelUtil;
	}

	*//**
	 * @方法功能说明：获取酒店商圈LIST
	 * @修改者名字：chenxy
	 * @修改时间：2015年6月30日上午10:37:07
	 * @修改内容：
	 * @参数：@return
	 * @return:List<HotelGeo>
	 * @throws
	 *//*
	@SuppressWarnings("unchecked")
	public List<HotelGeo> getHotelGeo() {
		try {
			SAXReader reader = new SAXReader();
			URL url1 = new URL("http://api.elong.com/xml/v2.0/hotel/geo_cn.xml");
			Document document = reader.read(url1);
			Element root = document.getRootElement();
			Element element = root.element("HotelGeoList");
			List<Element> geos = element.elements();
			System.out.println(geos.size());
			List<HotelGeo> hotelGeos = new ArrayList<HotelGeo>();
			for (Element e : geos) {
				HotelGeo hotelGeo = new HotelGeo();
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
					List<District> dList = new ArrayList<District>();
					for (Element e1 : locations) {
						District district = new District();
						List<Attribute> as = e1.attributes();
						for (Attribute attr : as) {
							if (attr.getName().equals("Id")) {
								district.setId(attr.getValue());
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
					List<CommericalLocation> cList = new ArrayList<CommericalLocation>();
					for (Element e1 : locations1) {
						CommericalLocation commericalLocation = new CommericalLocation();
						List<Attribute> as = e1.attributes();
						for (Attribute attr : as) {
							if (attr.getName().equals("Id")) {
								commericalLocation.setId(attr.getValue());
							} else if (attr.getName().equals("Name")) {
								commericalLocation.setName(attr.getValue());
							}
						}
						cList.add(commericalLocation);
					}
					hotelGeo.setCommericalLocations(cList);
				}
				hotelGeos.add(hotelGeo);
			}
			return hotelGeos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	*//**
	 * @方法功能说明：获取酒店品牌LIST
	 * @修改者名字：chenxy
	 * @修改时间：2015年6月30日上午10:37:32
	 * @修改内容：
	 * @参数：@return
	 * @return:List<HotelBrand>
	 * @throws
	 *//*
	@SuppressWarnings("unchecked")
	public List<HotelBrand> getHotelBrands() {
		try {
			SAXReader reader = new SAXReader();
			URL url1 = new URL("http://api.elong.com/xml/v2.0/hotel/brand_cn.xml");
			Document document = reader.read(url1);
			Element root = document.getRootElement();
			List<Element> geos = root.elements();
			System.out.println(geos.size());
			List<HotelBrand> hotelBrands = new ArrayList<HotelBrand>();
			for (Element e : geos) {
				HotelBrand hotelGeo = new HotelBrand();
				List<Attribute> attributes = e.attributes();
				for (Attribute attr : attributes) {
					if (attr.getName().equals("BrandId")) {
						hotelGeo.setBrandId(attr.getValue());
					} else if (attr.getName().equals("GroupId")) {
						hotelGeo.setGroupId(attr.getValue());
					} else if (attr.getName().equals("ShortName")) {
						hotelGeo.setShortName(attr.getValue());
					} else if (attr.getName().equals("Name")) {
						hotelGeo.setName(attr.getValue());
					} else if (attr.getName().equals("Letters")) {
						hotelGeo.setLetters(attr.getValue());
					}
				}
				hotelBrands.add(hotelGeo);
			}
			return hotelBrands;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
*/