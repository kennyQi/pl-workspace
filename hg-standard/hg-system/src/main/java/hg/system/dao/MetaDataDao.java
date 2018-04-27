package hg.system.dao;

import hg.common.component.hibernate.Finder;
import hg.common.component.hibernate.HibernateSimpleDao;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class MetaDataDao extends HibernateSimpleDao {

	@SuppressWarnings("unchecked")
	public List<Province> queryProvinceList(ProvinceQo qo) {
		Finder finder = Finder.create("from Province p");
		// 按名称模糊查询
		if (StringUtils.isNotBlank(qo.getName())) {
			finder.append(" where");
			if (qo.getNameFuzzy()) {
				finder.append(" p.name like :name");
			} else {
				finder.append(" p.name = :name");
			}
			finder.setParam("name", qo.getName());
		}

		List<Province> list = find(finder);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<City> queryCityList(CityQo qo) {
		Finder finder = Finder.create("from City c");
		
		if (StringUtils.isNotBlank(qo.getProvinceCode())) {
			finder.append(" where c.parentCode = :provinceCode").setParam("provinceCode",
					qo.getProvinceCode());
		}
		
		// 按名称模糊查询
		if (StringUtils.isNotBlank(qo.getName())) {

			if (qo.getNameFuzzy()) {
				finder.append(" and c.name like :name");
			} else {
				finder.append(" and c.name = :name");
			}
			finder.setParam("name", qo.getName());
		}

		List<City> list = find(finder);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Area> queryAreaList(AreaQo qo) {
		Finder finder = Finder.create("from Area a");

		if (StringUtils.isNotBlank(qo.getCityCode())) {
			finder.append(" where a.parentCode = :cityCode").setParam("cityCode",
					qo.getCityCode());
		}
		// 按名称模糊查询
		if (StringUtils.isNotBlank(qo.getName())) {

			if (qo.getNameFuzzy()) {
				finder.append(" and a.name like :name");
			} else {
				finder.append(" and a.name = :name");
			}
			finder.setParam("name", qo.getName());
		}

		List<Area> list = find(finder);
		return list;
	}
}
