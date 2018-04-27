package plfx.xl.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.line.LinePicture;
import plfx.xl.pojo.qo.LinePictureQO;

/**
 * @类功能说明：线路图片DAO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日下午1:59:23
 * @版本：V1.0
 */
@Repository
public class LinePictureDAO extends BaseDao<LinePicture, LinePictureQO> {
	@Autowired
	private LinePictureFolderDAO folderDAO;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, LinePictureQO qo) {
		if(null == qo)
			return criteria;
		
		// 排序
		if (qo.getCreateDateSort() != 0)
			criteria.addOrder(qo.getCreateDateSort() > 0 ? Order.asc("createDate") : Order.desc("createDate"));
		// 名称
		if(StringUtils.isNotBlank(qo.getName()) && qo.isNameLike())
			criteria.add(Restrictions.like("name",qo.getName(),MatchMode.ANYWHERE));
		if(StringUtils.isNotBlank(qo.getName()) && !qo.isNameLike())
			criteria.add(Restrictions.eq("name",qo.getName()));
		//所属的文件夹
		if(null != qo.getFolderQO() && !qo.isFolderFetchAble())
			criteria.add(Restrictions.eq("folder.id",qo.getFolderQO().getId()));
		if(null != qo.getFolderQO() && qo.isFolderFetchAble()){
			Criteria criteria2 = criteria.createCriteria("folder",JoinType.LEFT_OUTER_JOIN);
			folderDAO.buildCriteriaOut(criteria2, qo.getFolderQO());
		}
		return criteria;
	}

	@Override
	protected Class<LinePicture> getEntityClass() {
		return LinePicture.class;
	}
}