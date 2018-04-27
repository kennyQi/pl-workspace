package lxs.app.service.app;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.Date;

import lxs.app.dao.app.LineCarouselDAO;
import lxs.domain.model.app.Carousel;
import lxs.pojo.command.app.AddCarouselCommand;
import lxs.pojo.command.app.UpdateCarouselCommand;
import lxs.pojo.qo.app.LineCarouselQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 
 * @类功能说明：线路轮播service
 * @类修改者：
 * @修改日期：2015-10-26 上午11:16:57
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015-10-26 上午11:16:57
 */
@Service
@Transactional
public class LineCarouselService extends
		BaseServiceImpl<Carousel, LineCarouselQO, LineCarouselDAO> {

	@Autowired
	private LineCarouselDAO carouselDao;

	@Override
	protected LineCarouselDAO getDao() {
		// TODO Auto-generated method stub
		return carouselDao;
	}

	/**
	 * 
	 * 
	 * @方法功能说明：添加
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:26:12
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void addCarouse(AddCarouselCommand addCarouselCommand) {
		HgLogger.getInstance().info("jinyy", "【LineCarouselService】【addCarouse】【AddCarouselCommand】"+JSON.toJSONString(addCarouselCommand));

		Carousel carousel = new Carousel();
		carousel.setId(UUIDGenerator.getUUID());
		carousel.setNote(addCarouselCommand.getNote());
		carousel.setStatus(Carousel.ON);
		carousel.setImageURL(addCarouselCommand.getImagePath());
		carousel.setCreateDate(new Date());
		carousel.setCarouselType(addCarouselCommand.getLinetype());
		carousel.setCarouselLevel(Carousel.LINE);

		if (StringUtils.isNotBlank(addCarouselCommand.getCarouselAction())) {
			carousel.setCarouselAction(addCarouselCommand.getCarouselAction());
		} else {
			carousel.setCarouselAction(addCarouselCommand
					.getCarouselActionCheck());
		}

		carousel.setSort(carouselDao.maxProperty("sort", new LineCarouselQO()) + 1);
		save(carousel);
	}

	/**
	 * 
	 * 
	 * @方法功能说明：启用禁用
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:26:22
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyCarouselStatus(UpdateCarouselCommand updateCarouselCommand) {
		HgLogger.getInstance().info("jinyy", "【LineCarouselService】【modifyCarouselStatus】【UpdateCarouselCommand】"+JSON.toJSONString(updateCarouselCommand));
		
		Carousel carousel = carouselDao.get(updateCarouselCommand.getId());
		carousel.setStatus(updateCarouselCommand.getStatus());
		getDao().update(carousel);
	}

	/**
	 * 
	 * 
	 * @方法功能说明：修改
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:26:36
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void editCarouse(UpdateCarouselCommand updateCarouselCommand) {
		HgLogger.getInstance().info("jinyy", "【LineCarouselService】【editCarouse】【UpdateCarouselCommand】"+JSON.toJSONString(updateCarouselCommand));
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
