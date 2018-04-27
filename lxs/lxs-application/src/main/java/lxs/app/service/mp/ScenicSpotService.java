package lxs.app.service.mp;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lxs.app.dao.mp.ScenicSpotDAO;
import lxs.domain.model.mp.ScenicSpot;
import lxs.pojo.qo.mp.ScenicSpotQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class ScenicSpotService extends BaseServiceImpl<ScenicSpot, ScenicSpotQO, ScenicSpotDAO>{

	@Autowired
	private ScenicSpotDAO scenicSpotDAO;
	
	public void deleteOldScenicSpot(int versionNO){
		scenicSpotDAO.executeHql("delete ScenicSpot ss where ss. versionNO < "+versionNO);
	}
	
	/**
	 * @Title: editStatus 
	 * @author guok
	 * @Description: 修改状态
	 * @Time 2016年3月7日下午3:35:00
	 * @param scenicSpotID void 设定文件
	 * @throws
	 */
	public void editStatus(String scenicSpotID) {
		ScenicSpot scenicSpot = scenicSpotDAO.get(scenicSpotID);
		if (scenicSpot != null) {
			if (scenicSpot.getLocalStatus() == ScenicSpot.SHOW) {
				scenicSpot.setLocalStatus(ScenicSpot.HIDDEN);
			}else if (scenicSpot.getLocalStatus() == ScenicSpot.HIDDEN) {
				scenicSpot.setLocalStatus(ScenicSpot.SHOW);
			}
			scenicSpotDAO.update(scenicSpot);
		}
	}
	
	/**
	 * @Title: stickit 
	 * @author guok
	 * @Description: 置顶
	 * @Time 2016年3月7日下午3:45:17
	 * @param scenicSpotID void 设定文件
	 * @throws
	 */
	public void stickit(String scenicSpotID,String status) {
		ScenicSpot scenicSpot = scenicSpotDAO.get(scenicSpotID);
		if (scenicSpot != null) {
			if (StringUtils.equals(status, "set")) {
				int num = getMaxSort();
				scenicSpot.setSort(num+1);
			} else {
				scenicSpot.setSort(0);
			}
			
			scenicSpotDAO.update(scenicSpot);
		}
	}
	
	/**
	 * @Title: getMaxSort 
	 * @author guok
	 * @Description: 获取最大数
	 * @Time 2016年3月7日下午3:38:36
	 * @return int 设定文件
	 * @throws
	 */
	public int getMaxSort(){
		ScenicSpotQO scenicSpotQO = new ScenicSpotQO();
		return scenicSpotDAO.maxProperty("sort", scenicSpotQO);
	}
	
	@Override
	protected ScenicSpotDAO getDao() {
		return scenicSpotDAO;
	}

}
