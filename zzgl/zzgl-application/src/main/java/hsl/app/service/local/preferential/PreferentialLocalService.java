package hsl.app.service.local.preferential;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hg.service.ad.pojo.dto.ad.AdDTO;
import hg.service.ad.pojo.qo.ad.AdQO;
import hg.service.ad.spi.inter.AdService;
import hsl.app.dao.PreferentialDao;
import hsl.domain.model.preferential.Preferential;
import hsl.pojo.command.CreatePreferentialCommand;
import hsl.pojo.command.UpdatePreferentialCommand;
import hsl.pojo.dto.ad.HslAdConstant;
import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.preferential.PreferentialDTO;
import hsl.pojo.qo.preferential.HslPreferentialQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @类功能说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015年4月27日上午11:27:29
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */
@Service
@Transactional
public class PreferentialLocalService extends
BaseServiceImpl<Preferential, HslPreferentialQO, PreferentialDao>{
	@Autowired
	private PreferentialDao PreferentialDao;
	@Autowired
	private AdService adService;
	@Override
	protected PreferentialDao getDao() {
		return PreferentialDao;
	}
	/**
	 * @throws ParseException 
	 * 
	 * @方法功能说明：创建特惠专区
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月27日下午1:41:49
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void CreatePreferential(CreatePreferentialCommand command) throws ParseException{
		Preferential entity = new Preferential();
		entity.create(command);
		PreferentialDao.save(entity);
	}
	/**
	 * 
	 * @方法功能说明：删除特惠专区
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月27日下午3:57:21
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void deletePreferential(UpdatePreferentialCommand command){
		PreferentialDao.deleteById(command.getId());
	}
	/**
	 * @throws ParseException 
	 * 
	 * @方法功能说明 修改特惠专区
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月27日下午4:09:13
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void updatePreferential(UpdatePreferentialCommand command) throws ParseException{
		Preferential entity = PreferentialDao.get(command.getId());
		entity.update(command);
		PreferentialDao.update(entity);
	}
	/**
	 * 
	 * @方法功能说明：根据id得到特惠专区信息
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月27日下午4:27:21
	 * @参数：@param id
	 * @return:PreferentialDTO
	 * @throws
	 */
	public PreferentialDTO getPreferential(String id) {
		Preferential Preferential = PreferentialDao.get(id);
		return BeanMapperUtils.map(Preferential, PreferentialDTO.class);
	}
	/**
	 * 
	 * @方法功能说明：获取特惠专区列表
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月28日上午8:17:45
	 * @参数：@param pagination
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Pagination getPreferentialList(Pagination pagination) {
		List<AdDTO> adlist = (List<AdDTO>) pagination.getList();
		List<PreferentialDTO> dtos = new ArrayList<PreferentialDTO>();
		if(adlist!=null&&adlist.size()>0){
			HslPreferentialQO hslPreferentialQO = (HslPreferentialQO)pagination.getCondition();
			for(AdDTO adDTO : adlist){
				hslPreferentialQO.setAdId(adDTO.getId());
				List<Preferential> list = PreferentialDao.queryList(hslPreferentialQO);
				if(list!=null&&list.size()>0){
					PreferentialDTO dto = BeanMapperUtils.map(list.get(0), PreferentialDTO.class);
					HslAdDTO addto = BeanMapperUtils.map(adDTO, HslAdDTO.class);
					dto.setAdBaseInfo(addto.getAdBaseInfo());
					dto.setAdStatus(addto.getAdStatus());
					dto.setPosition(addto.getPosition());
					dtos.add(dto);
				}else{
					try {
						/*DeleteAdCommand adCommand = new DeleteAdCommand();
						adCommand.setAdId(adDTO.getId());
						adService.deletAd(adCommand);*/
					} catch (Exception e) {
						HgLogger.getInstance().error("zhaows", "getPreferentialList-->特惠专区分页查询失败"+HgLogger.getStackTrace(e));
						e.printStackTrace();
					}
				}
			}
			
			
		}
		pagination.setList(dtos);
		return pagination;
	}
	/**
	 * 
	 * @方法功能说明：特惠专区查询返回list
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月29日上午8:41:00
	 * @参数：@param QO
	 * @参数：@return
	 * @return:List<PreferentialDTO>
	 * @throws
	 */
	public List<PreferentialDTO> queryPreferentialList(HslPreferentialQO QO){
		List<PreferentialDTO> list=new ArrayList<PreferentialDTO>();
		AdQO adQO=new AdQO();
		adQO.setIsShow(true);
		adQO.setPositionId(HslAdConstant.THZQ);
		List<AdDTO> adlist=(List<AdDTO>) adService.queryList(adQO);
		if(adlist!=null&&adlist.size()>0){
			for(AdDTO adDTO : adlist){
				QO.setAdId(adDTO.getId());
				List<Preferential> lists = PreferentialDao.queryList(QO);
				if(lists.size()>0){
				PreferentialDTO dto = BeanMapperUtils.map(lists.get(0), PreferentialDTO.class);
				HslAdDTO addto = BeanMapperUtils.map(adDTO, HslAdDTO.class);
				dto.setAdBaseInfo(addto.getAdBaseInfo());
				dto.setAdStatus(addto.getAdStatus());
				dto.setPosition(addto.getPosition());
				list.add(dto);
				}
			}
		}else{
			try {
				/*DeleteAdCommand adCommand = new DeleteAdCommand();
				adCommand.setAdId(adDTO.getId());
				adService.deletAd(adCommand);*/
			} catch (Exception e) {
				HgLogger.getInstance().error("zhaows", "queryPreferentialList-->list列表查询失败"+HgLogger.getStackTrace(e));
				e.printStackTrace();
			}
		}
		return list;
	}
}
