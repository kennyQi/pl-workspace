package slfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.xl.app.dao.DayRouteDAO;
import slfx.xl.app.dao.LineDAO;
import slfx.xl.app.dao.LineImageDAO;
import slfx.xl.app.dao.LinePictureDAO;
import slfx.xl.app.dao.LinePictureFolderDAO;
import slfx.xl.app.dao.LineSnapshotDAO;
import slfx.xl.domain.model.line.DateSalePrice;
import slfx.xl.domain.model.line.DayRoute;
import slfx.xl.domain.model.line.Line;
import slfx.xl.domain.model.line.LineImage;
import slfx.xl.domain.model.line.LineSnapshot;
import slfx.xl.pojo.command.line.AuditLineCommand;
import slfx.xl.pojo.command.line.CopyLineCommand;
import slfx.xl.pojo.command.line.CreateLineCommand;
import slfx.xl.pojo.command.line.GroundingLineCommand;
import slfx.xl.pojo.command.line.ModifyLineCommand;
import slfx.xl.pojo.command.line.ModifyLineMinPriceCommand;
import slfx.xl.pojo.command.line.UnderCarriageLineCommand;
import slfx.xl.pojo.command.route.ModifyLineRouteInfoCommand;
import slfx.xl.pojo.qo.DayRouteQO;
import slfx.xl.pojo.qo.LineQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：LOCAL线路SERVICE(操作数据库)实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月02日下午4:42:43
 * @版本：V1.0
 * 
 */
@Service
@Transactional
public class LineLocalService extends BaseServiceImpl<Line, LineQO, LineDAO> {

	@Autowired
	private LineDAO lineDAO;
	@Autowired
	private DomainEventRepository domainEventRepository;
	@Autowired
	private LinePictureFolderDAO linePictureFolderDAO;
	@Autowired
	private LineSnapshotDAO lineSnapshotDAO;
	@Autowired
	private DayRouteDAO dayRouteDAO;
	@Autowired
	private LinePictureDAO linePictureDAO;
	@Autowired
	private LineImageDAO lineImageDAO;

	@Override
	protected LineDAO getDao() {
		return lineDAO;
	}

	/**
	 * 
	 * @方法功能说明：新增线路
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月15日下午3:12:45
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean createLine(CreateLineCommand command) {

		try {

			Line line = new Line();
			line.createLine(command);
			lineDAO.save(line);

			// 创建线路基本信息的同时添加线路图片主文件夹
//			CreateLinePictureFolderCommand createLinePictureFolderCommand = new CreateLinePictureFolderCommand();
//			createLinePictureFolderCommand.setName("主文件夹");
//			createLinePictureFolderCommand.setMatter(true);
//			LinePictureFolder linePictureFolder = new LinePictureFolder();
//			linePictureFolder.create(createLinePictureFolderCommand, line);
//			linePictureFolderDAO.save(linePictureFolder);

			// List<LinePicture> pictureList = new ArrayList<LinePicture>();
			Hibernate.initialize(line.getDateSalePriceList());
			Hibernate.initialize(line.getLineImageList());
			// List<LinePictureFolder> folder = line.getFolderList();
			// for (LinePictureFolder pictureFolder : folder) {
			// Hibernate.initialize(linePictureFolder.getPictureList());
			// pictureList.addAll(pictureFolder.getPictureList());
			// }
			// line.setPictureList(pictureList);
			// 创建线路基本信息的同时添加线路快照
			LineSnapshot lineSnapshot = new LineSnapshot();
			lineSnapshot.create(line);
			lineSnapshotDAO.save(lineSnapshot);

			DomainEvent event = new DomainEvent(
					"slfx.xl.domain.model.line.Line", "createLine",
					JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;

		} catch (Exception e) {
			HgLogger.getInstance().error(
					"luoyun",
					"LineLocalService->createLine->exception:"
							+ HgLogger.getStackTrace(e));
			return false;
		}

	}

	/**
	 * 
	 * @方法功能说明：修改线路行程信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月16日下午2:24:42
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean modifyLineRouteInfo(ModifyLineRouteInfoCommand command) {

		try {

			Line line = lineDAO.get(command.getLineID());
			line.modifyLineRouteInfo(command);
			lineDAO.update(line);

			DomainEvent event = new DomainEvent(
					"slfx.xl.domain.model.line.Line", "modifyLineRouteInfo",
					JSON.toJSONString(command));
			domainEventRepository.save(event);

			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error(
					"luoyun",
					"LineLocalService->modifyLineRouteInfo->exception:"
							+ HgLogger.getStackTrace(e));
			return false;
		}

	}

	/**
	 * @方法功能说明：线路基本信息修改
	 * @修改者名字：liusong
	 * @修改时间：2014年12月16日下午4:52:01
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean modifyLine(ModifyLineCommand command) {

		try {

			Line line = lineDAO.get(command.getLineID());
			line.modifyLine(command);
			lineDAO.update(line);

			Hibernate.initialize(line.getDateSalePriceList());
			Hibernate.initialize(line.getLineImageList());
			// List<LinePicture> pictureList = new ArrayList<LinePicture>();
			// List<LinePictureFolder> folder = line.getFolderList();
			// for (LinePictureFolder linePictureFolder : folder) {
			// Hibernate.initialize(linePictureFolder.getPictureList());
			// pictureList.addAll(linePictureFolder.getPictureList());
			// }
			// line.setPictureList(pictureList);
			// 修改线路基本信息的同时添加线路快照
			LineSnapshot lineSnapshot = new LineSnapshot();
			lineSnapshot.create(line);
			lineSnapshotDAO.save(lineSnapshot);

			DomainEvent event = new DomainEvent(
					"slfx.xl.domain.model.line.Line", "modifyLine",
					JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;

		} catch (Exception e) {
			HgLogger.getInstance().error(
					"liusong",
					"LineLocalService->modifyLine->exception:"
							+ HgLogger.getStackTrace(e));
			return false;
		}
	}

	/**
	 * @方法功能说明：线路基本信息审核
	 * @修改者名字：liusong
	 * @修改时间：2014年12月17日上午11:13:34
	 * @修改内容：
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean auditLine(AuditLineCommand command) {
		try {
			Line line = lineDAO.get(command.getId());
			line.auditLine(command);
			lineDAO.update(line);

			DomainEvent event = new DomainEvent(
					"slfx.xl.domain.model.line.Line", "auditLine",
					JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error(
					"liusong",
					"LineLocalService->auditLine->exception:"
							+ HgLogger.getStackTrace(e));
			return false;
		}
	}

	/**
	 * @方法功能说明：上架线路基本信息
	 * @修改者名字：liusong
	 * @修改时间：2014年12月17日下午2:15:37
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean groundingLine(GroundingLineCommand command) {
		try {
			Line line = lineDAO.get(command.getLineID());
			line.groundingLine(command);
			lineDAO.update(line);

			DomainEvent event = new DomainEvent(
					"slfx.xl.domain.model.line.Line", "groundingLine",
					JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error(
					"liusong",
					"LineLocalService->groundingLine->exception:"
							+ HgLogger.getStackTrace(e));
			return false;
		}
	}

	/**
	 * @方法功能说明：下架线路基本信息
	 * @修改者名字：liusong
	 * @修改时间：2014年12月17日下午2:17:32
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean underCarriageLine(UnderCarriageLineCommand command) {
		try {
			Line line = lineDAO.get(command.getLineID());
			line.underCarriageLine(command);
			lineDAO.update(line);

			DomainEvent event = new DomainEvent(
					"slfx.xl.domain.model.line.Line", "underCarriageLine",
					JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error(
					"liusong",
					"LineLocalService->underCarriageLine->exception:"
							+ HgLogger.getStackTrace(e));
			return false;
		}
	}

	/**
	 * 
	 * @方法功能说明：线路复制信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月8日上午9:05:44
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean copyLine(CopyLineCommand command) {
		try {

			// 查询复制对象
			LineQO lineQO = new LineQO();
			lineQO.setId(command.getLineID());
			Line copyLine = lineDAO.queryUnique(lineQO);
			// 复制线路基本信息
			Line line = new Line();
			line.copyLine(command, copyLine);
			lineDAO.save(line);
			
			//复制首图列表
			List<LineImage> headImageList = copyLine.getLineImageList();
			for(LineImage lineImage : headImageList){
				LineImage copyHeadImage = new LineImage();
				copyHeadImage.copy(lineImage);
				copyHeadImage.setLine(line);
				lineImageDAO.save(copyHeadImage);
			}

			// 复制线路行程信息
			List<DayRoute> dayRouteList = new ArrayList<DayRoute>();
			List<DayRoute> dayRouteCopyList = new ArrayList<DayRoute>();
			DayRouteQO dayRouteQO = new DayRouteQO();
			dayRouteQO.setLineID(command.getLineID());
			dayRouteList = dayRouteDAO.queryList(dayRouteQO);
			for (DayRoute dayRoute : dayRouteList) {
				DayRoute dayRouteCopy = new DayRoute();
				dayRouteCopy.copy(dayRoute, line);
				dayRouteCopyList.add(dayRouteCopy);
			}
			dayRouteDAO.saveList(dayRouteCopyList);
			line.getRouteInfo().setDayRouteList(dayRouteCopyList);
			
			//复制行程图列表
			List<DayRoute> dayRoutes = copyLine.getRouteInfo().getDayRouteList();
			for(int i = 0; i < dayRouteCopyList.size(); i++){
				for(int j = 0; j < dayRoutes.size(); j++){
					//找到对应的行程
					if(dayRouteCopyList.get(i).getDays() == dayRoutes.get(j).getDays()){
						List<LineImage> dayRouteImageList = dayRoutes.get(j).getLineImageList();
						//把图片的dayRoute改掉
						for(LineImage dayRouteImage : dayRouteImageList){
							LineImage copyLineImage = new LineImage();
							copyLineImage.copy(dayRouteImage);
							copyLineImage.setDayRoute(dayRouteCopyList.get(i));
							lineImageDAO.save(copyLineImage);
						}
					}
				}
			}

			Hibernate.initialize(line.getDateSalePriceList());
			Hibernate.initialize(line.getLineImageList());

			// 保存线路快照
			LineSnapshot lineSnapshot = new LineSnapshot();
			lineSnapshot.create(line);
			lineSnapshotDAO.save(lineSnapshot);

			//复制线路图片信息
			
			
			
			// 复制线路图片文件夹信息
//			LinePictureFolderQO linePictureFolderQO = new LinePictureFolderQO();
//			LineQO lineQO = new LineQO();
//			lineQO.setId(command.getLineID());
//			linePictureFolderQO.setLineQO(lineQO);
//			List<LinePictureFolder> linePictureFolderList = new ArrayList<LinePictureFolder>();
//			List<LinePictureFolder> linePictureFolderCopyList = new ArrayList<LinePictureFolder>();
//			linePictureFolderList = linePictureFolderDAO
//					.queryList(linePictureFolderQO);
//			List<LinePicture> linePictureCopyList = new ArrayList<LinePicture>();
//			for (LinePictureFolder linePictureFolder : linePictureFolderList) {
//				LinePictureFolder linePictureFolderCopy = new LinePictureFolder();
//				linePictureFolderCopy.copy(linePictureFolder, line);
//				linePictureFolderCopyList.add(linePictureFolderCopy);
//				// 复制线路图片文件夹下的图片
//				List<LinePicture> linePictureList = linePictureFolder
//						.getPictureList();
//				if (linePictureList != null && linePictureList.size() != 0) {
//					for (LinePicture linePicture : linePictureList) {
//						LinePicture linePictureCopy = new LinePicture();
//						linePictureCopy
//								.copy(linePicture, linePictureFolderCopy);
//						linePictureCopyList.add(linePictureCopy);
//					}
//				}
//			}
//			linePictureFolderDAO.saveList(linePictureFolderCopyList);
//			linePictureDAO.saveList(linePictureCopyList);
			//保存首图
//			LineImageQO lineImageQO_head = new LineImageQO();
//			lineImageQO_head.setLineId(command.getLineID());
//			List<LineImage> lineImageList_head = lineImageDAO.queryList(lineImageQO_head);
//			if(null != lineImageList_head){
//				List<LineImage> newLineImageList = new ArrayList<LineImage>();
//				for(LineImage lineImage : lineImageList_head){
//					LineImage newLineImage = new LineImage();
//					newLineImage.copy(lineImage);
//					Line newLine = new Line();
//					newLine.setId(line.getId());
//					newLineImage.setLine(newLine);
//					newLineImageList.add(newLineImage);
//				}
//				lineImageDAO.saveList(newLineImageList);
//			}
//			//保存行程图片
//			LineImageQO lineImageQO_dayRoute = new LineImageQO();
//			lineImageQO_dayRoute.setLineId(command.getLineID());
//			List<LineImage> newLineImageLists = new ArrayList<LineImage>();
//			for(int j = 0; j < dayRouteList.size(); j++){
//				DayRoute dayRoute = new DayRoute();
//				dayRoute = dayRouteList.get(j);
//				List<LineImage> lineImageList_dayRoute = new ArrayList<LineImage>();
//				lineImageQO_dayRoute.setDayRouteId(dayRoute.getId());
//				lineImageList_dayRoute = lineImageDAO.queryList(lineImageQO_dayRoute);
//				if(null != lineImageList_dayRoute && lineImageList_dayRoute.size() > 0){
//					for(int i = 0; i < lineImageList_dayRoute.size(); i++){
//						LineImage lineImage_dayRoute = new LineImage();
//						lineImage_dayRoute = lineImageList_dayRoute.get(i);
//						LineImage newLineImage_dayRoute = new LineImage();
//						newLineImage_dayRoute.copy(lineImage_dayRoute);
//						Line newLine = new Line();
//						DayRoute newDayRoute = new DayRoute();
//						newLine.setId(line.getId());
//						newDayRoute.setId(dayRouteCopyList.get(j).getId());
//						newLineImage_dayRoute.setLine(newLine);
//						newLineImage_dayRoute.setDayRoute(newDayRoute);
//						newLineImageLists.add(newLineImage_dayRoute);
//					}
//				}
//			}
//			lineImageDAO.saveList(newLineImageLists);
			
			DomainEvent event = new DomainEvent(
					"slfx.xl.domain.model.line.Line", "copyLine",
					JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;
		} catch (Exception e) {
			HgLogger.getInstance().error(
					"liusong",
					"LineLocalService->copyLine->exception:"
							+ HgLogger.getStackTrace(e));
			return false;
		}
	}

	/**
	 * 
	 * @方法功能说明：：查询价格政策的适用的线路列表
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月5日上午10:09:35
	 * @修改内容：
	 * @参数：@param salePolicyID
	 * @参数：@return
	 * @return:List<Line>
	 * @throws
	 */
	public List<Line> findLineBySalePolicyID(String salePolicyID) {
		return lineDAO.findLineBySalePolicyID(salePolicyID);
	}

	public List<Line> shopQueryList(LineQO qo) {
		List<Line> lines = lineDAO.queryList(qo);
		for (Line line : lines) {
			if (line != null && line.getLineImageList() != null) {
				Hibernate.initialize(line.getLineImageList());
			}
			if(null != line && null != line.getRouteInfo() && null != line.getRouteInfo().getDayRouteList()){
				List<DayRoute> dayRouteList = line.getRouteInfo().getDayRouteList();
				for(DayRoute dayRoute : dayRouteList){
					if(null != dayRoute.getLineImageList() && !dayRoute.getLineImageList().isEmpty()){
						Hibernate.initialize(dayRoute.getLineImageList());
					}
				}
			}
//			Hibernate.initialize(line.getRouteInfo().getDayRouteList());
//			Hibernate.initialize(line.getDateSalePriceList());
//			Hibernate.initialize(line.getLineImageList());
//			for(LinePictureFolder folder:line.getLineImageList()){
//				Hibernate.initialize(folder.getPictureList());
//			}
			removelastDateSalePrice(line);
		}
		return lines;
	}

	private void removelastDateSalePrice(Line line) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH,-1);
		Date lastDay = cal.getTime();
		List<DateSalePrice> prices = line.getDateSalePriceList();
		List<DateSalePrice> removeList = new ArrayList<DateSalePrice>();
		for (DateSalePrice dateSalePrice : prices) {
			if (dateSalePrice.getSaleDate().before(lastDay)) {
				removeList.add(dateSalePrice);
			}
		}
		prices.removeAll(removeList);
		line.setDateSalePriceList(prices);
	}

	public void modifyLineMinPrice(ModifyLineMinPriceCommand modifyLineMinPriceCommand) {
		Line line = lineDAO.get(modifyLineMinPriceCommand.getLineId());
		line.setMinPrice(modifyLineMinPriceCommand.getMinPrice());
		lineDAO.update(line);
	}

}
