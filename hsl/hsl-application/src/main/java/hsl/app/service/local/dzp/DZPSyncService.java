package hsl.app.service.local.dzp;

import hg.common.component.CommonDao;
import hg.common.util.SpringContextUtil;
import hg.dzpw.dealer.client.api.v1.request.RegionQO;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.RegionResponse;
import hg.dzpw.dealer.client.api.v1.response.ScenicSpotResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.common.util.DealerApiClient;
import hg.dzpw.dealer.client.dto.meta.RegionDTO;
import hg.log.util.HgLogger;
import hsl.app.service.local.dzp.region.RegionLocalService;
import hsl.app.service.local.dzp.scenicspot.DZPScenicSpotLocalService;
import hsl.app.service.local.dzp.policy.DZPTicketPolicyLocalService;
import hsl.domain.model.dzp.meta.DZPArea;
import hsl.domain.model.dzp.meta.DZPCity;
import hsl.domain.model.dzp.meta.DZPProvince;
import hsl.pojo.exception.DZPTicketPolicyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 电子票同步服务
 *
 * @author zhurz
 * @since 1.8
 */
@Service
public class DZPSyncService {

	private String    devName     = "hgg";
	//每次分页抓取数据的大小
	private final Integer                                fetchSize = 10000;

	@Autowired
	private DZPTicketPolicyLocalService ticketPoliceLocalService;
	@Autowired
	private DZPScenicSpotLocalService                    dZPScenicSpotLocalService;
	@Autowired
	private RegionLocalService 							  regionLocalService;
	@Autowired
	private DealerApiClient 							  dzpwDealerApiClient;

	@Transactional
	public void testUpdateRegion() {

		long begin = System.currentTimeMillis();
		DZPWService service = SpringContextUtil.getApplicationContext().getBean(DZPWService.class);
		CommonDao dao = SpringContextUtil.getApplicationContext().getBean(CommonDao.class);

		RegionQO qo = new RegionQO();
		qo.setType(1);
		RegionResponse res1 = service.queryRegion(qo);
		qo.setType(2);
		RegionResponse res2 = service.queryRegion(qo);
		qo.setType(3);
		RegionResponse res3 = service.queryRegion(qo);

		Map<String, DZPProvince> provinceMap = new HashMap<String, DZPProvince>();
		for (RegionDTO regionDTO : res1.getRegions()) {
			DZPProvince p = new DZPProvince();
			p.setId(regionDTO.getId());
			p.setName(regionDTO.getName());
			dao.saveOrUpdate(p);
			provinceMap.put(p.getId(), p);
		}

		Map<String, DZPCity> cityMap = new HashMap<String, DZPCity>();
		for (RegionDTO regionDTO : res2.getRegions()) {
			DZPProvince p = provinceMap.get(regionDTO.getParentCode());
			if(p==null) continue;
			DZPCity c = new DZPCity();
			c.setId(regionDTO.getId());
			c.setName(regionDTO.getName());
			c.setFromProvince(p);
			dao.saveOrUpdate(c);
			cityMap.put(c.getId(), c);
		}

		for (RegionDTO regionDTO : res3.getRegions()) {
			DZPCity c = cityMap.get(regionDTO.getParentCode());
			if(c==null)continue;
			DZPArea a = new DZPArea();
			a.setId(regionDTO.getId());
			a.setName(regionDTO.getName());
			a.setFromCity(c);
			dao.saveOrUpdate(c);
		}

		long end = System.currentTimeMillis();

		System.out.println("耗时：" + (end - begin));

	}

	// 同步行政区，省市区到本地数据库
	public void syncRegion() {

		//每次请求抓取到的数据
		Integer count = 0;
		//第几页
		Integer pager = 0;

		Long time1 = System.currentTimeMillis();

		//处理省份数据
		regionLocalService.pullProvince();
		//处理城市数据
		regionLocalService.pullCity();
		//同步区数据
		regionLocalService.pullArea();


		Long time2 = System.currentTimeMillis();

		Long time = time2 - time1;

		HgLogger.getInstance().info(devName,"本次从电子票务同步省市区用时："+time);
		System.out.println("本次从电子票务同步省市区用时："+time);
	}

	// 同步景区到本地数据库
	public void syncScenicSpot() {

		//每次请求抓取到的数据
		Integer count = 0;
		//第几页
		Integer pager = 1;

		Long time1 = System.currentTimeMillis();

		ScenicSpotQO qo = new ScenicSpotQO();
		ScenicSpotResponse response = dzpwDealerApiClient.send(qo, ScenicSpotResponse.class);
		Integer totalCount = response.getTotalCount();

		while (count < totalCount){

			qo.setPageSize(fetchSize);
			qo.setPageNo(pager);
			//同步景区
			Integer size = dZPScenicSpotLocalService.syncScenicSpot(qo);
			if(size == -1){//当前页码已经没有数据
				break;
			}
			pager++;
			count = count + size;
		}


		Long time2 = System.currentTimeMillis();

		Long time = time2 - time1;

		HgLogger.getInstance().info(devName,"本次从电子票务同步门票政策用时："+time);
	}

	// 同步门票政策到本地数据库
	public void syncTicketPolicy() {

		//每次请求抓取到的数据
		Integer count = 0;
		//第几页
		Integer pager = 1;

		Long time1 = System.currentTimeMillis();

		try {

			TicketPolicyQO qo=new TicketPolicyQO();
			qo.setSingleTicketPoliciesFetch(true);
			qo.setModifyDateEnd(new Date());

			//【1】调用电子票务借口抓取门票政策数据
			TicketPolicyResponse response = dzpwDealerApiClient.send(qo, TicketPolicyResponse.class);
			Integer totalCount = response.getTotalCount();

			while (count < totalCount){
				qo.setPageNo(pager);
				qo.setPageSize(fetchSize);
				qo.setCalendarFetch(true);
				//同步门票政策
				Integer size = ticketPoliceLocalService.syncTicketPolicy(qo);
				if(size == -1){//当前页码已经没有数据
					break;
				}
				count = count + size;
				pager++;
				try {
					// 每次分页数据处理睡眠1秒
					Thread.sleep(1 * 1000);

				} catch (InterruptedException e) {

					HgLogger.getInstance().error(devName,"睡眠异常！！");

				}
			}

		}catch (DZPTicketPolicyException e){
			HgLogger.getInstance().error(devName,"本次从电子票务同步门票政策发生异常："+e.getMessage());
		}

		Long time2 = System.currentTimeMillis();

		Long time = time2 - time1;

		HgLogger.getInstance().info(devName,"本次从电子票务同步门票政策用时："+time);
	}

}
