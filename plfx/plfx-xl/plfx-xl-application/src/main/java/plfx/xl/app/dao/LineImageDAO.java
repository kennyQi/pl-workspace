package plfx.xl.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.line.LineImage;
import plfx.xl.pojo.qo.LineImageQO;

/**
 * 
 * @类功能说明：线路图片DAO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月22日下午4:21:07
 * @版本：V1.0
 *
 */
@Repository
public class LineImageDAO extends BaseDao<LineImage,LineImageQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineImageQO qo) {
		if(null != qo){
			if(StringUtils.isNotBlank(qo.getLineId())){
				criteria.add(Restrictions.eq("line.id", qo.getLineId()));
			}
			if(StringUtils.isNotBlank(qo.getDayRouteId())){
				criteria.add(Restrictions.eq("dayRoute.id", qo.getDayRouteId()));
			}
			
			if(StringUtils.isNotBlank(qo.getImageId())){
				criteria.add(Restrictions.eq("imageId", qo.getImageId()));
			}
			
			if(qo.getOrderByCreateDate()){
				criteria.addOrder(qo.getOrderByCreateDate()?Order.desc("createDate"):Order.asc("createDate"));
			}
		}
		return criteria;
	}

	@Override
	protected Class<LineImage> getEntityClass() {
		return LineImage.class;
	}
	
	@Override
	public LineImage queryUnique(LineImageQO qo) {
		LineImage lineImage = super.queryUnique(qo);
		if(null != lineImage){
			if(null != lineImage.getLine()){
				lineImage.getLine().setDateSalePriceList(null);
				lineImage.getLine().setLineImageList(null);
				lineImage.getLine().setRouteInfo(null);
			}
			lineImage.setDayRoute(null);
		}
		return lineImage;
	}
	

}
