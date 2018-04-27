package lxs.app.service.app;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import lxs.app.dao.app.CarouselDAO;
import lxs.app.dao.line.LxsLineDAO;
import lxs.app.dao.mp.ScenicSpotDAO;
import lxs.domain.model.app.Carousel;
import lxs.domain.model.line.Line;
import lxs.pojo.command.app.AddCarouselCommand;
import lxs.pojo.command.app.UpdateCarouselCommand;
import lxs.pojo.qo.app.CarouselQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
/**
 * 
 * @类功能说明：轮播图service
 * @类修改者：
 * @修改日期：2015-10-26 上午11:18:27
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015-10-26 上午11:18:27
 */
public class CarouselService extends
		BaseServiceImpl<Carousel, CarouselQO, CarouselDAO> {

	@Autowired
	private CarouselDAO carouselDao;
	@Autowired
	private LxsLineDAO lineDAO;
	@Autowired
	private ScenicSpotDAO scenicSpotDAO;
	
	@Override
	protected CarouselDAO getDao() {
		return carouselDao;
	}

	/**
	 * 
	 * @方法功能说明：当线路 下架 或删除时 更新轮播图
	 * @修改者名字：cangs
	 * @修改时间：2015年10月16日下午2:14:45
	 * @修改内容：
	 * @参数：@param lineID
	 * @return:void
	 * @throws
	 */
	public void refresh() {
		List<Carousel> carousels = carouselDao.queryList(new CarouselQO());
		for (Carousel carousel : carousels) {
			if(carousel.getCarouselType()==Carousel.LINE){
				if(lineDAO.get(carousel.getCarouselAction())==null ||lineDAO.get(carousel.getCarouselAction()).getForSale()==Line.NOT_SALE||lineDAO.get(carousel.getCarouselAction()).getForSale()==2){
					carouselDao.delete(carousel);
				}
			}
			if(carousel.getCarouselType()==Carousel.MENPIAO){
				if(scenicSpotDAO.get(carousel.getCarouselAction())==null ){
					carouselDao.delete(carousel);
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * @方法功能说明：添加轮播图
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:21:32
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void addCarouse(AddCarouselCommand addCarouselCommand) {
		HgLogger.getInstance().info("jinyy", "【CarouselService】【addCarouse】【AddCarouselCommand】"+JSON.toJSONString(addCarouselCommand));

		Carousel carousel = new Carousel();
		carousel.setId(UUIDGenerator.getUUID());
		carousel.setNote(addCarouselCommand.getNote());
		carousel.setStatus(Carousel.ON);
		carousel.setImageURL(addCarouselCommand.getImagePath());
		carousel.setCreateDate(new Date());
		carousel.setCarouselType(addCarouselCommand.getLinetype());
		/**
		 * 2016年3月4日 17:20:44，修改
		 */
		carousel.setCarouselLevel(addCarouselCommand.getCarouselLevel());

		if (StringUtils.isNotBlank(addCarouselCommand.getCarouselAction())) {
			carousel.setCarouselAction(addCarouselCommand.getCarouselAction());
		} else {
			carousel.setCarouselAction(addCarouselCommand
					.getCarouselActionCheck());
		}

		carousel.setSort(carouselDao.maxProperty("sort", new CarouselQO()) + 1);
		save(carousel);
	}

	/**
	 * 
	 * 
	 * @方法功能说明：修改(启用/禁用)状态
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:21:50
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyCarouselStatus(UpdateCarouselCommand updateCarouselCommand) {
		HgLogger.getInstance().info("jinyy", "【CarouselService】【modifyCarouselStatus】【UpdateCarouselCommand】"+JSON.toJSONString(updateCarouselCommand));
		Carousel carousel = carouselDao.get(updateCarouselCommand.getId());
		carousel.setStatus(updateCarouselCommand.getStatus());
		getDao().update(carousel);
	}

	/**
	 * 
	 * 
	 * @方法功能说明：修改轮播图
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:22:50
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void editCarouse(UpdateCarouselCommand updateCarouselCommand) {
		HgLogger.getInstance().info("jinyy", "【CarouselService】【editCarouse】【UpdateCarouselCommand】"+JSON.toJSONString(updateCarouselCommand));

		Carousel carousel = get(updateCarouselCommand.getId());
		carousel.setNote(updateCarouselCommand.getNote());

		if (StringUtils.isNotBlank(updateCarouselCommand.getImagePath())) {
			carousel.setImageURL(updateCarouselCommand.getImagePath());
		}
		carousel.setCarouselType(updateCarouselCommand.getLinetype());

		if (StringUtils.isNotBlank(updateCarouselCommand.getCarouselAction())
				&& updateCarouselCommand.getLinetype() == 6) {
			carousel.setCarouselAction(updateCarouselCommand
					.getCarouselAction());
		} else {
			carousel.setCarouselAction(updateCarouselCommand
					.getCarouselActionCheck());
		}

		carousel.setCreateDate(new Date());
		update(carousel);
	}

}
