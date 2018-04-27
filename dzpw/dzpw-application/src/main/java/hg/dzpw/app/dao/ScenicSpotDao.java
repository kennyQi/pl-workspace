package hg.dzpw.app.dao;

import java.util.List;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.system.dao.AreaDao;
import hg.system.dao.CityDao;
import hg.system.dao.ProvinceDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：景区DAO
 * @类修改者：
 * @修改日期：2014-11-12下午3:18:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-12下午3:18:22
 */
@Repository
public class ScenicSpotDao extends BaseDao<ScenicSpot, ScenicSpotQo> {
	
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private ClientDeviceDao deviceDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, ScenicSpotQo qo) {
		if(null == qo)
			return criteria;
		
		// 排序
		if (qo.getCrateDateSort() != 0) {
			criteria.addOrder(qo.getCrateDateSort() > 0 ? Order.asc("baseInfo.createDate") : Order.desc("baseInfo.createDate"));
		}
		// 景区id
				if (StringUtils.isNotBlank(qo.getId())) {
						criteria.add(Restrictions.eq("id", qo.getId()));
				}
		// 景区名称
		if (StringUtils.isNotBlank(qo.getName())) {
			if (qo.isNameLike())
				criteria.add(Restrictions.like("baseInfo.name", qo.getName(), MatchMode.ANYWHERE));
			else
				criteria.add(Restrictions.eq("baseInfo.name", qo.getName()));
		}
		// 景区简称
		if (StringUtils.isNotBlank(qo.getShortName())) {
			if (qo.isShortNameLike())
				criteria.add(Restrictions.like("baseInfo.shortName", qo.getShortName(), MatchMode.ANYWHERE));
			else
				criteria.add(Restrictions.eq("baseInfo.shortName", qo.getShortName()));
		}
		// 景区代码
		if (StringUtils.isNotBlank(qo.getCode())) {
			criteria.add(Restrictions.eq("baseInfo.code", qo.getCode()));
		}
		// 景区级别
		if (qo.getLevel() != null && qo.getLevel().length > 0) {
			criteria.add(Restrictions.or(eachEqProperty(qo.getLevel(), "baseInfo.level")));
		}
		// 所在省
		if (qo.getProvinceQo() != null) {
			Criteria provinceCriteria = criteria.createCriteria("baseInfo.province",qo.getProvinceQo().getAlias(),JoinType.LEFT_OUTER_JOIN);
			provinceDao.buildCriteriaOut(provinceCriteria, qo.getProvinceQo());
		}
		// 所在市
		if (qo.getCityQo() != null) {
			Criteria cityCriteria = criteria.createCriteria("baseInfo.city",qo.getCityQo().getAlias(),JoinType.LEFT_OUTER_JOIN);
			cityDao.buildCriteriaOut(cityCriteria, qo.getCityQo());
		}
		//所在地区
		if(qo.getAreaQo() != null){
			Criteria areaCriteria = criteria.createCriteria("baseInfo.area",qo.getAreaQo().getAlias(),JoinType.LEFT_OUTER_JOIN);
			areaDao.buildCriteriaOut(areaCriteria, qo.getAreaQo());
		}
		// 创建时间
		if (qo.getCreateDateBegin() != null) {
			criteria.add(Restrictions.ge("baseInfo.createDate", qo.getCreateDateBegin()));
		}
		if (qo.getCreateDateEnd() != null) {
			criteria.add(Restrictions.le("baseInfo.createDate", qo.getCreateDateEnd()));
		}
		// 创建的管理者id
		if (StringUtils.isNotBlank(qo.getCreateAdminId())) {
			criteria.add(Restrictions.eq("baseInfo.createAdminId", qo.getCreateAdminId()));
		}
		// 联票的默认有效天
		if (qo.getTicketDefaultValidDaysMin() != null) {
			criteria.add(Restrictions.ge("baseInfo.ticketDefaultValidDays", qo.getTicketDefaultValidDaysMin()));
		}
		if (qo.getTicketDefaultValidDaysMax() != null) {
			criteria.add(Restrictions.le("baseInfo.ticketDefaultValidDays", qo.getTicketDefaultValidDaysMax()));
		}
		// 联系人
		if (StringUtils.isNotBlank(qo.getLinkMan())) {
			if (qo.isLinkManLike())
				criteria.add(Restrictions.like("contactInfo.linkMan", qo.getLinkMan(), MatchMode.ANYWHERE));
			else
				criteria.add(Restrictions.eq("contactInfo.linkMan", qo.getLinkMan()));
		}
		// 联系电话
		if (StringUtils.isNotBlank(qo.getTelephone())) {
			if (qo.isTelephoneLike())
				criteria.add(Restrictions.like("contactInfo.telephone", qo.getTelephone(), MatchMode.ANYWHERE));
			else
				criteria.add(Restrictions.eq("contactInfo.telephone", qo.getTelephone()));
		}
		// 邮箱
		if (StringUtils.isNotBlank(qo.getEmail())) {
			if (qo.isEmailLike())
				criteria.add(Restrictions.like("contactInfo.email",
						qo.getEmail(), MatchMode.ANYWHERE));
			else
				criteria.add(Restrictions.eq("contactInfo.email", qo.getEmail()));
		}
		// 联系QQ
		if (StringUtils.isNotBlank(qo.getQq())) {
			if (qo.isQqLike())
				criteria.add(Restrictions.like("contactInfo.qq", qo.getQq(), MatchMode.ANYWHERE));
			else
				criteria.add(Restrictions.eq("contactInfo.qq", qo.getQq()));
		}
		// 联系地址(模糊匹配)
		if (StringUtils.isNotBlank(qo.getAddress())) {
			criteria.add(Restrictions.like("contactInfo.address", qo.getAddress(), MatchMode.ANYWHERE));
		}
		// 登录帐号
		if (StringUtils.isNotBlank(qo.getAdminLoginName())) {
			if (qo.isAdminLoginNameLike())
				criteria.add(Restrictions.like("superAdmin.adminLoginName", qo.getAdminLoginName(), MatchMode.ANYWHERE));
			else
				criteria.add(Restrictions.eq("superAdmin.adminLoginName", qo.getAdminLoginName()));
		}
		// 是否启用
		if (qo.getActivated() != null) {
			criteria.add(Restrictions.eq("status.activated", qo.getActivated()));
		}
		// 是否被逻辑删除
		if (qo.getRemoved() != null) {
			criteria.add(Restrictions.eq("status.removed", qo.getRemoved()));
		}
		// 套票数（联票数）
		if (qo.getGroupTicketNumberMin() != null) {
			criteria.add(Restrictions.ge("groupTicketNumber", qo.getGroupTicketNumberMin()));
		}
		if (qo.getGroupTicketNumberMax() != null) {
			criteria.add(Restrictions.le("groupTicketNumber", qo.getGroupTicketNumberMax()));
		}
		// 修改时间
		if (qo.getModifyDateBegin() != null) {
			criteria.add(Restrictions.ge("baseInfo.modifyDate", qo.getModifyDateBegin()));
		}
		if (qo.getModifyDateEnd() != null) {
			criteria.add(Restrictions.le("baseInfo.modifyDate", qo.getModifyDateEnd()));
		}
		
		//secretKey查询景区
		if(qo.getSecretKey() !=null){
			criteria.add(Restrictions.eq("superAdmin.secretKey", qo.getSecretKey()));
		}
		//支付类型
		if (qo.getAccountType()!=null) {
			criteria.add(Restrictions.eq("certificateInfo.accountTupe", qo.getAccountType()));
		}
		
		return criteria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination resultPagination = super.queryPagination(pagination);
		ScenicSpotQo qo = (ScenicSpotQo) pagination.getCondition();
		if(null == qo)
			return resultPagination;
		
		List<ScenicSpot> list = (List<ScenicSpot>) resultPagination.getList();
		for (ScenicSpot o : list) {
			if(qo.isProvinceFetchAble())
				Hibernate.initialize(o.getBaseInfo().getProvince());
			if(qo.isCityFetchAble())
				Hibernate.initialize(o.getBaseInfo().getCity());
			if(qo.isFetchAllDevices())
				Hibernate.initialize(o.getDevices());
		}
		return resultPagination;
	}
	
	@Override
	public ScenicSpot queryUnique(ScenicSpotQo qo) {
		ScenicSpot spot = super.queryUnique(qo);
		if(null == qo)
			return spot;
		
		if(qo.isProvinceFetchAble())
			Hibernate.initialize(spot.getBaseInfo().getProvince());
		if(qo.isCityFetchAble())
			Hibernate.initialize(spot.getBaseInfo().getCity());
		if(qo.isFetchAllDevices())
			Hibernate.initialize(spot.getDevices());
		if (qo.isFetchAllPic()) {
			Hibernate.initialize(spot.getPics());
		}
		return spot;
	}

	@Override
	protected Class<ScenicSpot> getEntityClass() {
		return ScenicSpot.class;
	}
}