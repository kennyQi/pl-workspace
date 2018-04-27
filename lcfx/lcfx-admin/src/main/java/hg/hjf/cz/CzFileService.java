package hg.hjf.cz;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.SMSUtils;
import hg.common.util.UUIDGenerator;
import hg.hjf.nh.NHFile;
import hg.hjf.nh.NHFileFormat;
import hg.hjf.nh.NHFileFormat.ACRow;
import hg.jxc.admin.task.CZReturnTask;
import hg.pojo.exception.LcfxException;
import hg.pojo.qo.CZFileQo;
import hg.pojo.qo.MileOrderQo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxc.app.dao.cz.CZFileDao;
import jxc.app.dto.ResultDto;
import jxc.app.service.distributor.MileOrderService;
import jxc.domain.model.cz.CZFile;
import jxc.domain.model.distributor.MileOrder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hazelcast.nio.serialization.Data;

/**
 * @类功能说明：南航文件处理（生成和处理反馈文件
 * @类修改者：
 * @修改日期：2015年6月8日上午10:54:46
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年6月8日上午10:54:46
 * @version：
 */
@Transactional
@Service
public class CzFileService extends BaseServiceImpl<CZFile, CZFileQo, CZFileDao> {

	@Autowired
	CZFileDao czFileDao;

	@Autowired
	MileOrderService mileOrderService;
	
	//处理成功
	public static final int CZ_FILE_SUCCEED = 100;

	//文件异常
	public static final int CZ_FILE_ERROR = 101;
	
	private static Log logger = LogFactory.getLog(CzFileService.class);



	// public static String okSms = UserService.SMSSIGN + "您的南航里程已到账，到账%s里程。 ",
	// failSms = UserService.SMSSIGN + "您的南航里程未到账，所扣汇积分已退回。里程未到账原因：%s";

	/**
	 * 生成文件 日结完毕后，生成上日的
	 * 调用方式：/czfile/gen?domain=xx&path=/main/apps/jfPath/cz/&date
	 * =<2015-06-07,date 为交易日期，可选项>
	 * 
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年6月11日上午9:54:43
	 * @version：
	 * @修改内容：
	 * @参数：@param path
	 * @参数：@param domain
	 * @参数：@param date 交易日期, ru 2015-06-07。可选。
	 * @参数：@return
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	public List<MileOrder> genFile(String path, String domain, String date) throws IOException {
		logger.info("开始生成南航文件");

		List<String> idList = new ArrayList<String>();
		System.out.println("生成里程文件:\n" + path);
		// TransferInStatQo dto = new TransferInStatQo();
		// dto.setDomain(domain);
		// dto.setTransferIn(false);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//
		// if (date != null) {
		// dto.setFromDate(date);
		// dto.setToDate(date);
		// }

		// //dto.setStatus(JfFlow.NOR);// 已发货状态
		// dto.setSendStatus(JfFlow.TOSEND);
		// Pagination paging = dto.getPagination();
		// paging.setCondition(dto);
		// List<JfFlow> list =
		// flowService.transferOutMemo(paging.getCondition());

		MileOrderQo qo = new MileOrderQo();
		qo.setStatus(MileOrder.STATUS_CHECK_PASS);
		List<MileOrder> list = mileOrderService.queryList(qo);
		logger.info("待生产里程订单：" + list.size());
		NHFile nhfile = new NHFile();
		for (MileOrder order : list) {
			ACRow acRow = new ACRow();
			// id 为32位，南航文件只有20位。在 空格 字段中存放整的id
			acRow.AC记录流水号.value = order.getFlowCode().substring(0, acRow.AC记录流水号.length);
			acRow.空格.value = order.getId();
			acRow.会员卡号.value = order.getCsairCard();
			acRow.姓.value = order.getCsairName();
			acRow.名.value = order.getCsairName();
			acRow.兑换日期.value = sdf.format(order.getCheckDate());
			acRow.基本里程数.value = 100 * order.getNum();
			nhfile.acList.add(acRow);
			// flow.setMerchandiseStatus(JfFlow.SEND);
			// flow.setSendTime(new Date());
			// flowService.update(flow);
		}
		ResultDto rd = new ResultDto();
		String mess;
		if (nhfile.acList.size() > 0) {
			Date date2 = new Date();
			String file = nhfile.toFile(sdf.format(date2), new File(path));
			mess = "增加里程条数" + nhfile.acList.size() + ".南航文件生成完毕 " + path + file;
			System.out.println(mess);
			// CZTask.logger.info(mess);
			rd.setStatus(true);
			rd.setText(path + file);
			File f = new File(rd.getText());
			CZFile cz = new CZFile();
			cz.setId(UUIDGenerator.getUUID());
			cz.setFileName(f.getName());
			cz.setTimestamp(new Date());
			cz.setStatus(CZFile.TOSEND);
			cz.setType(CZFile.SENDFILE);
			rd.setText2(cz.getId());
			saveCzFile(cz);
		} else {
			mess = "无南航里程交易:日期 " + date;
			System.out.println(mess);
			rd.setStatus(false);
			rd.setText(mess);
		}
		return list;

	}

	/**
	 * 处理反馈文件，如失败的积分要回滚
	 * 
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年6月11日上午9:56:44
	 * @version：
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@return
	 * @参数：@throws IOException
	 * @参数：@throws Exception
	 * @return:String
	 * @throws
	 */
	@Transactional(rollbackFor = Exception.class)
	public int handleFile(String file) throws IOException, Exception {
		NHFile nhfile = new NHFile();
		nhfile.parseFile(file);

		// 原发送文件名
		String sendFile = file.replace("_CZ", "");
		// 反馈文件又重新将空格自动清空了，所有需要读原发送文件，获取里程的记录号和 数据库id对照
		Map<String, String> fileIdMap = getIdMap(sendFile);

		StringWriter w = new StringWriter();
		// row
		List<ACRow> acList = nhfile.acList;
		for (ACRow acRow : acList) {
			if (acRow == null || StringUtils.isBlank(acRow.南航反馈代码.value.toString())) {
				throw new LcfxException(null, "南航反馈文件格式不正确");
			}
		}
		for (ACRow r : acList) {
			String id = fileIdMap.get(r.AC记录流水号.value.toString());
			MileOrder order = mileOrderService.get(id);
			if (order == null) {
				logger.debug("没找到里程订单：" + id);
				logger.info("文件="+file);
				logger.info("idMap="+fileIdMap);
				logger.info("流水号="+r.AC记录流水号.value.toString());
				continue;
				
			}
			order.setCsairReturnDate(new Date());
			if (r.南航反馈代码.value.equals(NHFile.RETCODE_OK)) {
				order.setStatus(MileOrder.STATUS_CSAIR_SUCCEED);
				mileOrderService.update(order);
			} else {
				order.setStatus(MileOrder.STATUS_CSAIR_ERROR);
				order.setCsairInfo(NHFile.resultCodeMap.get(r.南航反馈代码.value));
				mileOrderService.update(order);
				
			}
			logger.debug("处理里程订单：" + id + "，状态：" + r.南航反馈代码.value);
			
		}

		return CZ_FILE_SUCCEED;
	}

	/**
	 * 
	 * @方法功能说明：jfflow记录发送状态
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-7-14下午3:40:35
	 * @version：
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@throws IOException
	 * @参数：@throws Exception
	 * @return:void
	 * @throws
	 */
	/*
	 * public void NHFileToSendList(String file) throws IOException, Exception {
	 * NHFile nhfile = new NHFile(); nhfile.parseFile(file); String sendFile =
	 * file.replace("_CZ", ""); Map<String, String> fileIdMap =
	 * getIdMap(sendFile); for (ACRow r : nhfile.acList) { String id =
	 * fileIdMap.get(r.AC记录流水号.value.toString()); JfFlow flow =
	 * flowService.get(id); // flow.setMerchandiseStatus(JfFlow.SEND);
	 * flow.setSendTime(new Date()); flow.setSendStatus(JfFlow.SEND);
	 * flowService.update(flow); }
	 * 
	 * }
	 * 
	 * /**
	 * 
	 * @方法功能说明：保存文件信息
	 * 
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * 
	 * @修改时间：2015-6-12下午2:25:12
	 * 
	 * @version：
	 * 
	 * @修改内容：
	 * 
	 * @参数：@param cz
	 * 
	 * @return:void
	 * 
	 * @throws
	 */

	public void saveCzFile(CZFile cz) {
		save(cz);
	}

	/**
	 * 
	 * @方法功能说明：获取已发送的文件
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-6-12下午2:25:39
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:List<CZFile>
	 * @throws
	 */
	public List<CZFile> getSendCzFile() {
		CZFileQo qo = new CZFileQo();
		qo.setStatus(CZFile.TOSEND);
		List<CZFile> list = czFileDao.queryList(qo);
		return list;
	}

	/**
	 * 
	 * @方法功能说明：获取待发送的文件
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-7-7下午4:27:06
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:List<CZFile>
	 * @throws
	 */
	public List<CZFile> getToSendCzFile() {
		CZFileQo qo = new CZFileQo();
		qo.setStatus(CZFile.TOSEND);
		List<CZFile> list = czFileDao.queryList(qo);
		return list;
	}

	/**
	 * 
	 * @方法功能说明：获取所有南航文件信息
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-6-17下午2:45:02
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:List<CZFile>
	 * @throws
	 */
	/*
	 * public List<CZFile> getCzFile(CzFileDto dto) { CZFileQo qo; if (dto ==
	 * null) { qo = new CZFileQo(); } else { qo = daoToQo(dto); } List<CZFile>
	 * list = czFileDao.queryList(qo); return list; }
	 * 
	 * private CZFileQo daoToQo(CzFileDto dto) { CZFileQo qo = new CZFileQo();
	 * qo.setId(dto.getId()); qo.setStatus(dto.getStatus());
	 * qo.setFileName(dto.getFileName()); qo.setStartDate(dto.getStartDate());
	 * qo.setEndDate(dto.getEndDate()); qo.setType(dto.getType());
	 * qo.setTimestamp(dto.getTimestamp());
	 * qo.setFeedbacktime(dto.getFeedbacktime()); return qo;
	 * 
	 * }
	 * 
	 * /**
	 * 
	 * @方法功能说明：更新南航文件信息
	 * 
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * 
	 * @修改时间：2015-6-12下午2:26:04
	 * 
	 * @version：
	 * 
	 * @修改内容：
	 * 
	 * @参数：@param cz
	 * 
	 * @return:void
	 * 
	 * @throws
	 */
	public void updateCzFile(CZFile cz) {
		update(cz);
	}

	/**
	 * @throws IOException
	 * @方法功能说明：获取里程的记录号和 数据库id对照
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年6月11日下午2:42:50
	 * @version：
	 * @修改内容：
	 * @参数：@param sendFile
	 * @参数：@return
	 * @return:Map<String,String>
	 * @throws
	 */
	private Map<String, String> getIdMap(String sendFile) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		NHFile file = new NHFile();
		file.parseFile(sendFile);
		for (ACRow r : file.acList)
			map.put(r.AC记录流水号.value.toString(), r.空格.value.toString());

		return map;
	}

	@Override
	protected CZFileDao getDao() {
		return czFileDao;
	}

	/*
	 * public static void download(HttpServletRequest request,
	 * HttpServletResponse response, String fileName) throws
	 * UnsupportedEncodingException, IOException {
	 * response.setContentType("text/html;charset=utf-8");
	 * request.setCharacterEncoding("UTF-8"); java.io.BufferedInputStream bis =
	 * null; java.io.BufferedOutputStream bos = null; String downLoadPath =
	 * CZTask.CZPATH + fileName; try { File file = new File(downLoadPath); long
	 * fileLength = file.length();
	 * response.setContentType("application/octet-stream;");
	 * response.setHeader("Content-disposition", "attachment; filename=" + new
	 * String(fileName.getBytes("utf-8"), "ISO8859-1"));
	 * response.setHeader("Content-Length", String.valueOf(fileLength)); bis =
	 * new BufferedInputStream(new FileInputStream(downLoadPath)); bos = new
	 * BufferedOutputStream(response.getOutputStream()); byte[] buff = new
	 * byte[2048]; int bytesRead; while (-1 != (bytesRead = bis.read(buff, 0,
	 * buff.length))) { bos.write(buff, 0, bytesRead); } } catch (Exception e) {
	 * e.printStackTrace(); throw new RuntimeException(e.getCause()); } finally
	 * { if (bis != null) bis.close(); if (bos != null) bos.close();
	 * response.flushBuffer(); }
	 * 
	 * }
	 */
}
