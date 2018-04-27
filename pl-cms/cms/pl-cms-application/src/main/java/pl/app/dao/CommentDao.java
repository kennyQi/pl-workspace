package pl.app.dao;
import javax.annotation.Resource;
import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import pl.cms.domain.entity.comment.Comment;
import pl.cms.pojo.qo.ArticleQO;
import pl.cms.pojo.qo.CommentQO;

@Repository
public class CommentDao extends BaseDao<Comment, CommentQO> {
	@Resource
	private ArticleDao articleDao;
	@Override
	protected Criteria buildCriteria(Criteria criteria, CommentQO qo) {
		
		if (qo != null) {
			if(StringUtils.isNotBlank(qo.getArticleId())){
				criteria.add(Restrictions.eq("article.id", qo.getArticleId()));
			}
			if (qo.getFetchArticle()) {
				criteria.setFetchMode("article", FetchMode.JOIN);
			}
			if(StringUtils.isNotBlank(qo.getTitle())&&StringUtils.isNotBlank(qo.getAuthor())){
				Criteria criteria2 = criteria.createCriteria("article","article_", JoinType.INNER_JOIN);
				ArticleQO articleQO=new ArticleQO();
				articleQO.setTitle(qo.getTitle());
				articleQO.setTitleLike(true);
				articleQO.setAuthor(qo.getAuthor());
				articleDao.buildCriteriaOut(criteria2,articleQO);
			}else if(StringUtils.isNotBlank(qo.getTitle())){
//				Criteria criteria2=criteria.setFetchMode("article", FetchMode.JOIN);
//				criteria2.add(Restrictions.like("baseInfo.title", qo.getTitle()));
				Criteria criteria2 = criteria.createCriteria("article","article_", JoinType.INNER_JOIN);
				ArticleQO articleQO=new ArticleQO();
				articleQO.setTitle(qo.getTitle());
				articleQO.setTitleLike(true);
				articleDao.buildCriteriaOut(criteria2,articleQO);
			}else if(StringUtils.isNotBlank(qo.getAuthor())){
				Criteria criteria2 = criteria.createCriteria("article","article_", JoinType.INNER_JOIN);
				ArticleQO articleQO=new ArticleQO();
				articleQO.setAuthor(qo.getAuthor());
				articleDao.buildCriteriaOut(criteria2,articleQO);
			}
			//如果有两个值则查询范围，否则查询当天订单
			if (StringUtils.isNotBlank(qo.getBeginTime()) && StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			} else if (StringUtils.isNotBlank(qo.getBeginTime())) {
				criteria.add(Restrictions.ge("createDate", DateUtil.dateStr2BeginDate(qo.getBeginTime())));
			} else if (StringUtils.isNotBlank(qo.getEndTime())) {
				criteria.add(Restrictions.le("createDate",DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
			if(qo.getIsChecked()!=null){
					criteria.add(Restrictions.eq("isCheck", qo.getIsChecked()));
			}
			criteria.addOrder(Order.desc("createDate"));
		}
		return criteria;
	}

	@Override
	protected Class<Comment> getEntityClass() {
		return Comment.class;
	}

}
