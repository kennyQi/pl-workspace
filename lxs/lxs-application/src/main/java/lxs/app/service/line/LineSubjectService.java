package lxs.app.service.line;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lxs.app.dao.line.LxsLineSubjectDAO;
import lxs.domain.model.line.LineSubject;
import lxs.pojo.command.line.CreateLineSubjectCommand;
import lxs.pojo.qo.line.LineSubjectQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class LineSubjectService extends
		BaseServiceImpl<LineSubject, LineSubjectQO, LxsLineSubjectDAO> {

	@Autowired
	private LxsLineSubjectDAO lineSubjectDAO;
	
	/**
	 * 
	 * @方法功能说明：更新线路 主题中间表 返回受影响的主题ID
	 * @修改者名字：cangs
	 * @修改时间：2015年10月16日下午1:45:38
	 * @修改内容：
	 * @参数：@param lineID
	 * @参数：@return
	 * @return:List<String>
	 * @throws
	 */
	public List<String> refresh(String lineID){
		LineSubjectQO lineSubjectQO = new LineSubjectQO();
		lineSubjectQO.setLineID(lineID);
		List<LineSubject> lineSubjects = lineSubjectDAO.queryList(lineSubjectQO);
		List<String> subjectIDs = new ArrayList<String>();
		for (LineSubject lineSubject : lineSubjects) {
			lineSubjectDAO.delete(lineSubject);
			for(String subjectId:subjectIDs){
				int i = 0;
				if(StringUtils.equals(subjectId, lineSubject.getSubjectID())){
					subjectIDs.remove(i);
				}
				i++;
			}
			lineSubjectQO = new LineSubjectQO();
			lineSubjectQO.setSubjectID(lineSubject.getSubjectID());
			int sum = lineSubjectDAO.queryCount(lineSubjectQO); 
			subjectIDs.add(lineSubject.getSubjectID()+":"+String.valueOf(sum));
		}
		return subjectIDs;
	}
	
	/**
	 * @throws Exception 
	 * @Title: create 
	 * @author guok
	 * @Description: 线路主题关联创建
	 * @Time 2015年9月23日下午5:24:33
	 * @param command void 设定文件
	 * @throws
	 */
	public void create(CreateLineSubjectCommand command) throws Exception {
		HgLogger.getInstance().info("gk", "【LineSubjectService】【create】【CreateLineSubjectCommand】"+JSON.toJSONString(command));
		if (command.getLineID() == null || StringUtils.isBlank(command.getLineID())) {
			throw new Exception("线路为空");
		}
		
		for (String subjectID : command.getSubjectID()) {
			if (!StringUtils.equals(subjectID, "0")) {
				LineSubject lineSubject = new LineSubject();
				lineSubject.setId(UUIDGenerator.getUUID());
				lineSubject.setCreateDate(new Date());
				lineSubject.setLineID(command.getLineID());
				lineSubject.setSubjectID(subjectID);
				lineSubjectDAO.save(lineSubject);
			}
		}
	}
	
	/**
	 * @Title: detele 
	 * @author guok
	 * @Description: 删除
	 * @Time 2015年9月23日下午5:36:19
	 * @param lineID void 设定文件
	 * @throws
	 */
	public void detele(String lineID) {
		LineSubjectQO lineSubjectQO = new LineSubjectQO();
		lineSubjectQO.setLineID(lineID);
		List<LineSubject> lineSubjects = lineSubjectDAO.queryList(lineSubjectQO);
		if (lineSubjects.size()>0) {
			for (LineSubject lineSubject : lineSubjects) {
				lineSubjectDAO.delete(lineSubject);
			}
		}
		
	}
	
	@Override
	protected LxsLineSubjectDAO getDao() {
		return lineSubjectDAO;
	}

}
