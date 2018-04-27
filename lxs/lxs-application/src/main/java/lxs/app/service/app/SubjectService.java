package lxs.app.service.app;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import lxs.app.dao.app.SubjectDAO;
import lxs.domain.model.app.Subject;
import lxs.pojo.command.app.AddSubjectCommand;
import lxs.pojo.command.app.ModifySubjectCommand;
import lxs.pojo.qo.app.SubjectQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：主题service
 * @类修改者：
 * @修改日期：2015年9月18日上午10:52:37
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午10:52:37
 */

@Service
@Transactional
public class SubjectService extends BaseServiceImpl<Subject, SubjectQO, SubjectDAO>{

	@Autowired
	private SubjectDAO subjectDAO;
	
	/**
	 * 
	 * @方法功能说明：更新主题数量
	 * @修改者名字：cangs
	 * @修改时间：2015年10月16日下午2:00:21
	 * @修改内容：
	 * @参数：@param subjectIDs
	 * @return:void
	 * @throws
	 */
	public void refresh(List<String> subjectIDs){
		for (String string : subjectIDs) {
			String subjectID = string.split(":")[0];
			String sum = string.split(":")[1];
			Subject subject = subjectDAO.get(subjectID);
			subject.setProductSUM(sum);
			subjectDAO.update(subject);
		}
	}
	
	/**
	 * @Title: saveSubject 
	 * @author guok
	 * @Description: 添加主题
	 * @Time 2015年9月18日下午2:42:39
	 * @param command void 设定文件
	 * @throws
	 */
	public void saveSubject(AddSubjectCommand command) {
		HgLogger.getInstance().info("gk", "【SubjectService】【saveSubject】【AddSubjectCommand】"+JSON.toJSONString(command));
		Subject subject = new Subject();
		subject.setId(UUIDGenerator.getUUID());
		subject.setCreateDate(new Date());
		subject.setSubjectName(command.getSubjectName());
		subject.setSubjectType(command.getSubjectType());
		subject.setProductSUM("0");
		subject.setSort(subjectDAO.maxProperty("sort", new SubjectQO())+1);
		subjectDAO.save(subject);
	}
	
	/**
	 * @throws Exception 
	 * @Title: modifySubject 
	 * @author guok
	 * @Description: 修改主题
	 * @Time 2015年9月18日下午2:42:52
	 * @param command void 设定文件
	 * @throws
	 */
	public void modifySubject(ModifySubjectCommand command) throws Exception {
		HgLogger.getInstance().info("gk", "【SubjectService】【modifySubject】【ModifySubjectCommand】"+JSON.toJSONString(command));
		Subject subject = subjectDAO.get(command.getSubjectID());
		if (subject == null) {
			throw new Exception("主题不存在");
		}
		HgLogger.getInstance().info("gk", "【SubjectService】【modifySubject】【Subject】"+JSON.toJSONString(subject));
		subject.setSubjectName(command.getSubjectName());
		subject.setSubjectType(command.getSubjectType());
		subjectDAO.update(subject);
	}
	
	/**
	 * @Title: addProduct 
	 * @author guok
	 * @Description: 添加产品数量
	 * @Time 2015年9月24日上午10:51:26
	 * @param id void 设定文件
	 * @throws
	 */
	public void addProduct(String id,Integer sum) {
		HgLogger.getInstance().info("gk", "【SubjectService】【addProduct】【id】:"+id);
		Subject subject = subjectDAO.get(id);
		subject.setProductSUM(sum+"");
		subjectDAO.update(subject);
	}
	
	/**
	 * 
	 * @方法功能说明：保存主题列表
	 * @修改者名字：cangs
	 * @修改时间：2016年3月7日下午3:46:18
	 * @修改内容：
	 * @参数：@param subjects
	 * @return:void
	 * @throws
	 */
	public void saveSubjectList(List<Subject> subjects){
		SubjectQO subjectQO = new SubjectQO();
		subjectQO.setSubjectType(Subject.SUNGECT_TYPE_MENPIAO);
		for(Subject subject:subjectDAO.queryList(subjectQO)){
			subjectDAO.delete(subject);
		}
		for (Subject subject : subjects) {
			subject.setSort(subjectDAO.maxProperty("sort", new SubjectQO())+1);
			subject.setType(Subject.SYNC);
			subjectDAO.save(subject);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：更新主题
	 * @修改者名字：cangs
	 * @修改时间：2016年3月8日下午3:40:22
	 * @修改内容：
	 * @参数：@param subjects
	 * @return:void
	 * @throws
	 */
	public void updateSubjectList(String[] subjects,int type){
		for (String string : subjects) {
			SubjectQO subjectQO = new SubjectQO();
			subjectQO.setSubjectName(string);
			Subject subject = subjectDAO.queryUnique(subjectQO);
			if(type==1){
				if(subject!=null){
					//更新 减法
					if(StringUtils.equals(subject.getProductSUM(),"1")){
						subjectDAO.delete(subject);
					}else{
						subject.setProductSUM(String.valueOf(Integer.valueOf(subject.getProductSUM())-1));
						subjectDAO.update(subject);
					}
				}
			}else if(type==2){
				if(subject==null){
					Subject newSubject = new Subject();
					newSubject.setId(UUIDGenerator.getUUID());
					newSubject.setProductSUM("1");
					newSubject.setSubjectName(string);
					newSubject.setSubjectType(Subject.SUNGECT_TYPE_MENPIAO);
					newSubject.setCreateDate(new Date());
					newSubject.setSort(subjectDAO.maxProperty("sort", new SubjectQO())+1);
					newSubject.setType(Subject.SYNC);
					subjectDAO.save(newSubject);
				}else{
					subject.setProductSUM(String.valueOf(Integer.valueOf(subject.getProductSUM())+1));
					subjectDAO.update(subject);
				}
			}
		}
	}
	
	@Override
	protected SubjectDAO getDao() {
		return subjectDAO;
	}

	public int getMaxSort(){
		return subjectDAO.maxProperty("sort", new SubjectQO());
	}
}
