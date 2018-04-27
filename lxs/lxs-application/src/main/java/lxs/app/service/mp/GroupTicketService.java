package lxs.app.service.mp;

import hg.common.component.BaseServiceImpl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import lxs.app.dao.mp.GroupTicketDAO;
import lxs.app.dao.mp.ScenicSpotDAO;
import lxs.domain.model.mp.GroupTicket;
import lxs.domain.model.mp.ScenicSpot;
import lxs.pojo.qo.mp.GroupTicketQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class GroupTicketService extends BaseServiceImpl<GroupTicket, GroupTicketQO, GroupTicketDAO>{

	@Autowired
	private GroupTicketDAO groupTicketDAO;
	@Autowired
	private ScenicSpotDAO scenicSpotDAO;
	
	/**
	 * 
	 * @方法功能说明：保存联票
	 * @修改者名字：cangs
	 * @修改时间：2016年4月7日上午9:38:59
	 * @修改内容：
	 * @参数：@param groupTicketMap
	 * @return:void
	 * @throws
	 */
	public void saveGroupTicketMap(Map<String,GroupTicket> groupTicketMap){
		Iterator<Entry<String, GroupTicket>> iter = groupTicketMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String,GroupTicket> entry = iter.next();
			if(groupTicketDAO.get(entry.getValue().getId())==null){
				if(StringUtils.isNotBlank(entry.getValue().getScenicSpotID())){
					ScenicSpot scenicSpot = scenicSpotDAO.get(entry.getValue().getScenicSpotID());
					if(scenicSpot!=null&&scenicSpot.getBaseInfo()!=null){
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getProvinceId())){
							entry.getValue().setProvinceId(scenicSpot.getBaseInfo().getProvinceId());
						}
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getCityId())){
							entry.getValue().setCityId(scenicSpot.getBaseInfo().getCityId());						
						}
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getAreaId())){
							entry.getValue().setAreaId(scenicSpot.getBaseInfo().getAreaId());
						}
					}
				}
				groupTicketDAO.save(entry.getValue());
			}else{
				GroupTicket theOldGroupTicket = groupTicketDAO.get(entry.getValue().getId());
				if(StringUtils.isNotBlank(entry.getValue().getScenicSpotID())){
					theOldGroupTicket.setScenicSpotID(theOldGroupTicket.getScenicSpotID()+";"+entry.getValue().getScenicSpotID());
					ScenicSpot scenicSpot = scenicSpotDAO.get(entry.getValue().getScenicSpotID());
					if(scenicSpot!=null&&scenicSpot.getBaseInfo()!=null){
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getProvinceId())){
							if(StringUtils.isNotBlank(theOldGroupTicket.getProvinceId())){
								entry.getValue().setProvinceId(theOldGroupTicket.getProvinceId()+";"+scenicSpot.getBaseInfo().getProvinceId());
							}else{
								entry.getValue().setProvinceId(scenicSpot.getBaseInfo().getProvinceId());
							}
						}
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getCityId())){
							if(StringUtils.isNotBlank(theOldGroupTicket.getProvinceId())){
								entry.getValue().setCityId(theOldGroupTicket.getCityId()+";"+scenicSpot.getBaseInfo().getCityId());						
							}else{
								entry.getValue().setCityId(scenicSpot.getBaseInfo().getCityId());
							}
						}
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getAreaId())){
							if(StringUtils.isNotBlank(theOldGroupTicket.getAreaId())){
								entry.getValue().setAreaId(theOldGroupTicket.getAreaId()+";"+scenicSpot.getBaseInfo().getAreaId());
							}else{
								entry.getValue().setAreaId(scenicSpot.getBaseInfo().getAreaId());
							}
						}
					}
				}
				groupTicketDAO.update(theOldGroupTicket);
			}
		}
	}
	
	public void deleteOldGroupTicket(int versionNO){
		groupTicketDAO.executeHql("delete GroupTicket gt where gt. versionNO < "+versionNO);
	}
	
	public void deleteScenciSoptGroupTicket(String scenicSpotID){
		groupTicketDAO.executeHql("delete GroupTicket gt where gt. SCENIC_SPOT_ID like '%"+scenicSpotID+"%'");
	}
	
	@Override
	protected GroupTicketDAO getDao() {
		return groupTicketDAO;
	}

}
