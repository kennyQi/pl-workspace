package jxc.app.dao.cz;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import jxc.domain.model.cz.CZFile;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import hg.common.util.DateUtil;
import hg.pojo.qo.CZFileQo;

@Repository
public class CZFileDao extends BaseDao<CZFile, CZFileQo> {

	@Override
	protected Class<CZFile> getEntityClass() {
		return CZFile.class;
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, CZFileQo qo) {
		if (qo != null) {

			if (StringUtils.isNotBlank(qo.getId())) {
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if (StringUtils.isNotBlank(qo.getStatus())) {
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
			if (StringUtils.isNotBlank(qo.getFileName())) {
				criteria.add(Restrictions.like("fileName", "%"+qo.getFileName()+"%"));
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				if (StringUtils.isNotBlank(qo.getStartDate())) {
					try {
						String sDate=qo.getStartDate()+" 00:00:00";
						criteria.add(Restrictions.ge("timestamp", df.parse(sDate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			
				if (StringUtils.isNotBlank(qo.getEndDate())) {
					try {
						String eDate=qo.getEndDate()+" 23:59:59";
						criteria.add(Restrictions.le("timestamp", df.parse(eDate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			if (StringUtils.isNotBlank(qo.getType())) {
				criteria.add(Restrictions.eq("type", qo.getType()));
			}
		}
		criteria.addOrder(Order.desc("timestamp"));
		return criteria;
	}

}
