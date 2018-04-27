package plfx.xl.app.dao;

import hg.common.component.BaseDao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.line.LinePictureFolder;
import plfx.xl.pojo.qo.LinePictureFolderQO;

/**
 * @类功能说明：线路图片文件夹DAO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日下午1:59:23
 * @版本：V1.0
 */
@Repository
public class LinePictureFolderDAO extends BaseDao<LinePictureFolder,LinePictureFolderQO> {
	@Autowired
	private LineDAO lineDAO;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, LinePictureFolderQO qo) {
		if(null == qo)
			return criteria;
		
		//主文件夹在前
		criteria.addOrder(Order.desc("matter"));
		// 排序
		if (qo.getCreateDateSort() != 0) 
			criteria.addOrder(qo.getCreateDateSort() > 0 ? Order.asc("createDate") : Order.desc("createDate"));
		// 名称
		if(StringUtils.isNotBlank(qo.getName()) && qo.isNameLike())
			criteria.add(Restrictions.like("name",qo.getName(),MatchMode.ANYWHERE));
		if(StringUtils.isNotBlank(qo.getName()) && !qo.isNameLike())
			criteria.add(Restrictions.eq("name",qo.getName()));
		//是否主文件夹
		if(null != qo.isMatter()){
			criteria.add(Restrictions.eq("matter",qo.isMatter()));
		}
		//所属的线路
		if(null != qo.getLineQO() && !qo.isLineFetchAble())
			criteria.add(Restrictions.eq("line.id",qo.getLineQO().getId()));
		if(null != qo.getLineQO() && qo.isLineFetchAble()){
			Criteria criteria2 = criteria.createCriteria("line",JoinType.LEFT_OUTER_JOIN);
			lineDAO.buildCriteriaOut(criteria2, qo.getLineQO());
		}
		return criteria;
	}

	@Override
	protected Class<LinePictureFolder> getEntityClass() {
		return LinePictureFolder.class;
	}
	
	@Override
	public List<LinePictureFolder> queryList(LinePictureFolderQO qo) {
		List<LinePictureFolder> folderList = super.queryList(qo);
		for(LinePictureFolder o : folderList){
			Hibernate.initialize(o.getPictureList());
		}
		return folderList;
	}
}