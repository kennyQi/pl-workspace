package pl.app.service;
import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.app.dao.CommentDao;
import pl.cms.domain.entity.comment.Comment;
import pl.cms.pojo.command.comment.ApproveCommentCommand;
import pl.cms.pojo.command.comment.CreateCommentCommand;
import pl.cms.pojo.qo.CommentQO;

@Service
@Transactional
public class CommentServiceImpl extends BaseServiceImpl<Comment, CommentQO, CommentDao> {
	
	@Autowired
	private CommentDao commentDao;
	public void createComment(CreateCommentCommand command){
		Comment comment=new Comment();
		comment.createComment(command);
		commentDao.save(comment);
	}
	public void approveCommentDao(ApproveCommentCommand command){
		 CommentQO qo = new CommentQO();
	     qo.setId(command.getCommentId());
	     qo.setFetchArticle(false);
	     Comment comment = commentDao.queryUnique(qo);
	     comment.setIsCheck(true);
	     commentDao.update(comment);
	}
	@Override
	protected CommentDao getDao() {
		return commentDao;
	}
//	
//	@Override
//	public Pagination queryPagination(Pagination pagination) {
//		@SuppressWarnings("unchecked")
//		List<Comment> comments=(List<Comment>) getDao().queryPagination(pagination).getList();
//		for (Comment comment : comments) {
//			Hibernate.initialize(comment.getArticle());
//		}
//		return getDao().queryPagination(pagination);
//	}
}
