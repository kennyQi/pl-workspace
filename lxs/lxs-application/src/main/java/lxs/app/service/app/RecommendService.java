package lxs.app.service.app;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import lxs.app.dao.app.RecommendDAO;
import lxs.app.dao.line.LxsLineDAO;
import lxs.app.dao.mp.ScenicSpotDAO;
import lxs.domain.model.app.Recommend;
import lxs.domain.model.line.Line;
import lxs.pojo.command.app.AddRecommendCommand;
import lxs.pojo.command.app.DeleteRecommendCommand;
import lxs.pojo.command.app.ModifyRecommendCommand;
import lxs.pojo.command.app.ModifyRecommendSortCommand;
import lxs.pojo.exception.app.RecommendException;
import lxs.pojo.qo.app.RecommendQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class RecommendService extends BaseServiceImpl<Recommend, RecommendQO, RecommendDAO> {

	@Autowired
	private RecommendDAO recommendDAO;
	@Autowired
	private LxsLineDAO lineDAO;
	@Autowired
	private ScenicSpotDAO scenicSpotDAO;
	
	/**
	 * 
	 * @方法功能说明：当线路有 删除 下架操作时 更新推荐列表
	 * @修改者名字：cangs
	 * @修改时间：2015年10月16日下午2:11:31
	 * @修改内容：
	 * @参数：@param lineID
	 * @return:void
	 * @throws
	 */
	public void refresh(){
		List<Recommend> recommends = recommendDAO.queryList(new RecommendQO());
		for (Recommend recommend : recommends) {
			if(recommend.getRecommendType()==Recommend.LINE){
				if(lineDAO.get(recommend.getRecommendAction())==null || lineDAO.get(recommend.getRecommendAction()).getLocalStatus()==2||lineDAO.get(recommend.getRecommendAction()).getForSale()==Line.NOT_SALE){
					recommendDAO.delete(recommend);
				}
			}
			if(recommend.getRecommendType()==Recommend.MENPIAO){
				if(scenicSpotDAO.get(recommend.getRecommendAction())==null){
					recommendDAO.delete(recommend);
				}
			}
		}
	}
	
	/**
	 * @throws RecommendException 
	 * @Title: deleteRecommend 
	 * @author guok
	 * @Description: 删除推荐
	 * @Time 2015年9月14日上午10:43:03
	 * @param command void 设定文件
	 * @throws
	 */
	public void deleteRecommend(DeleteRecommendCommand command) throws RecommendException {
		HgLogger.getInstance().info("gk", "【RecommendService】【deleteRecommend】【DeleteRecommendCommand】"+JSON.toJSONString(command));
		
		RecommendQO recommendQO = new RecommendQO();
		recommendQO.setRecommendID(command.getRecommendID());
		Recommend recommend = recommendDAO.queryUnique(recommendQO);
		HgLogger.getInstance().info("gk", "【RecommendService】【deleteRecommend】【recommend】"+JSON.toJSONString(recommend));
		if (recommend == null) {
			throw new RecommendException(RecommendException.RECOMMEND_NULL);
		}
		recommendDAO.delete(recommend);
	}
	
	/**
	 * @throws RecommendException 
	 * @Title: addRecommend 
	 * @author guok
	 * @Description: 添加推荐
	 * @Time 2015年9月14日上午11:00:32
	 * @param command void 设定文件
	 * @throws
	 */
	public void addRecommend(AddRecommendCommand command) throws RecommendException {
		HgLogger.getInstance().info("guok", "【RecommendService】【addRecommend】【AddRecommendCommand】"+JSON.toJSONString(command));
		
		if (recommendDAO.queryCount(new RecommendQO())>=30) {
			throw new RecommendException(RecommendException.COUNT_UPPER_LIMIT);
		}
		
		Recommend recommend = new Recommend();
		recommend.setId(UUIDGenerator.getUUID());
		recommend.setRecommendName(command.getRecommendName());
		recommend.setImageURL(command.getImageURL());
		recommend.setNote(command.getNote());
		if (StringUtils.isNotBlank(command.getRecommendAction())) {
			recommend.setRecommendAction(command.getRecommendAction());
		}else {
			recommend.setRecommendAction(command.getRecommendActionCheck());
		}
		recommend.setRecommendType(command.getRecommendType());
		recommend.setSort(recommendDAO.maxProperty("sort", new RecommendQO(null,null))+1);
		recommend.setStatus(Recommend.ON);
		recommend.setCreateDate(new Date());
		HgLogger.getInstance().info("guok", "【RecommendService】【addRecommend】【Recommend】"+JSON.toJSONString(recommend));
		recommendDAO.save(recommend);
	}
	
	/**
	 * @throws RecommendException 
	 * @Title: modfiyRole 
	 * @author guok
	 * @Description: 修改
	 * @Time 2015年6月26日 10:15:26
	 * @param command void 设定文件
	 * @throws
	 */
	public void modfiyRecommend(ModifyRecommendCommand command) throws RecommendException {
		HgLogger.getInstance().info("guok", "【RecommendService】【modfiyRecommend】【ModifyRecommendCommand】"+JSON.toJSONString(command));
		RecommendQO recommendQO = new RecommendQO();
		recommendQO.setRecommendID(command.getRecommendID());
		Recommend recommend = recommendDAO.queryUnique(recommendQO);
		
		if (recommend == null) {
			throw new RecommendException(RecommendException.RECOMMEND_NULL);
		}
		recommend.setRecommendName(command.getRecommendName());
		if(command.getImageURL()!=null&&StringUtils.isNotBlank(command.getImageURL())){
			recommend.setImageURL(command.getImageURL());
		}
		recommend.setNote(command.getNote());
		
		if (StringUtils.isNotBlank(command.getRecommendAction())) {
			recommend.setRecommendAction(command.getRecommendAction());
		}else {
			recommend.setRecommendAction(command.getRecommendActionCheck());
		}
		recommend.setRecommendType(command.getRecommendType());
		recommend.setCreateDate(new Date());
		
		recommendDAO.update(recommend);
	}
	
	/**
	 * @Title: changeStatus 
	 * @author guok
	 * @Description: 修改状态
	 * @Time 2015年9月15日下午2:24:33
	 * @param recommendID
	 * @throws RecommendException void 设定文件
	 * @throws
	 */
	public void changeStatus(String recommendID) throws RecommendException {
		HgLogger.getInstance().info("guok", "【RecommendService】【changeStatus】【recommendID】"+JSON.toJSONString(recommendID));
		RecommendQO recommendQO = new RecommendQO();
		recommendQO.setRecommendID(recommendID);
		Recommend recommend = recommendDAO.queryUnique(recommendQO);
		
		if (recommend == null) {
			throw new RecommendException(RecommendException.RECOMMEND_NULL);
		}
		
		if (recommend.getStatus() == Recommend.OFF) {
			recommend.setStatus(Recommend.ON);
		}else {
			recommend.setStatus(Recommend.OFF);
		}
	}
	
	/**
	 * @throws RecommendException 
	 * @Title: changeSort 
	 * @author guok
	 * @Description: 移动顺序
	 * @Time 2015年9月16日下午6:06:29
	 * @param command void 设定文件
	 * @throws
	 */
	public void changeSort(ModifyRecommendSortCommand command) throws RecommendException {
		HgLogger.getInstance().info("guok", "【RecommendService】【changeStatus】【ModifyRecommendSortCommand】"+JSON.toJSONString(command));
		RecommendQO recommendQO = new RecommendQO();
		recommendQO.setRecommendID(command.getRecommendID());
		Recommend recommend = recommendDAO.queryUnique(recommendQO);
		if (recommend == null) {
			throw new RecommendException(RecommendException.RECOMMEND_NULL);
		}
		
		recommend.setSort(command.getSort());
		recommendDAO.update(recommend);
	}
	
	@Override
	protected RecommendDAO getDao() {
		return recommendDAO;
	}

	

}
