package lxs.app.service.mp;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;

import java.util.Date;
import java.util.List;

import lxs.app.dao.mp.ScenicSpotDAO;
import lxs.app.dao.mp.ScenicSpotSelectiveDAO;
import lxs.domain.model.mp.ScenicSpot;
import lxs.domain.model.mp.ScenicSpotSelective;
import lxs.pojo.command.mp.CreateScenicSpotSelectiveCommand;
import lxs.pojo.command.mp.DeleteScenicSpotSelectiveCommand;
import lxs.pojo.qo.mp.ScenicSpotSelectiveQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScenicSpotSelectiveService extends
		BaseServiceImpl<ScenicSpotSelective, ScenicSpotSelectiveQO, ScenicSpotSelectiveDAO> {

	@Autowired
	private ScenicSpotSelectiveDAO scenicSpotSelectiveDAO;
	@Autowired
	private ScenicSpotDAO scenicSpotDAO;

	/**
	 * @Title: createScenicSpotSelective 
	 * @author guok
	 * @Description: 添加景区精选
	 * @Time 2016年3月4日下午4:46:23
	 * @param command void 设定文件
	 * @throws
	 */
	public void createScenicSpotSelective(CreateScenicSpotSelectiveCommand command){
		ScenicSpotSelectiveQO scenicSpotSelectiveQO= new ScenicSpotSelectiveQO();
		scenicSpotSelectiveQO.setForSale(1);
		ScenicSpotSelective scenicSpotSelective = new ScenicSpotSelective();
		scenicSpotSelective.setId(UUIDGenerator.getUUID());
		scenicSpotSelective.setSort(scenicSpotSelectiveDAO.maxProperty("sort", scenicSpotSelectiveQO)+1);
		scenicSpotSelective.setCreateDate(new Date());
		scenicSpotSelective.setType(command.getType());
		scenicSpotSelective.setScenicSpotID(command.getScenicSpotId());
		scenicSpotSelective.setName(command.getName());
		scenicSpotSelectiveDAO.save(scenicSpotSelective);
	}
	
	/**
	 * @Title: deleteScenicSpotSelective 
	 * @author guok
	 * @Description: 删除景区精选
	 * @Time 2016年3月4日下午4:46:40
	 * @param command void 设定文件
	 * @throws
	 */
	public void deleteScenicSpotSelective(DeleteScenicSpotSelectiveCommand command){
			ScenicSpotSelective scenicSpottive=scenicSpotSelectiveDAO.get(command.getId());
			getDao().delete(scenicSpottive);
	}
	
	
	/**
	 * 
	 * @Title: getMaxSort 
	 * @author guok
	 * @Description: 获取最大数
	 * @Time 2016年3月7日下午4:32:40
	 * @return int 设定文件
	 * @throws
	 */
	public int getMaxSort(){
		ScenicSpotSelectiveQO scenicSpotSelectiveQO= new ScenicSpotSelectiveQO();
		scenicSpotSelectiveQO.setForSale(1);
		return scenicSpotSelectiveDAO.maxProperty("sort", scenicSpotSelectiveQO);
	}
	
	/**
	 * @Title: delSelective 
	 * @author guok
	 * @Description: 循环所有精选，查询景区，当景区已经删除是删除精选
	 * @Time 2016年3月7日下午4:35:53 void 设定文件
	 * @throws
	 */
	public void delSelectiveByNullScenicSpot() {
		List<ScenicSpotSelective> lists = scenicSpotSelectiveDAO.queryList(new ScenicSpotSelectiveQO());
		if (lists != null && lists.size() > 0) {
			for (ScenicSpotSelective scenicSpotSelective : lists) {
				ScenicSpot scenicSpot = scenicSpotDAO.get(scenicSpotSelective.getScenicSpotID());
				if (scenicSpot == null) {
					scenicSpotSelectiveDAO.delete(scenicSpotSelective);
				}
			}
		}
		
	}
	
	@Override
	protected ScenicSpotSelectiveDAO getDao() {
		return scenicSpotSelectiveDAO;
	}

}
