package jxc.app.service.distributor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.mileOrder.CheckMileOrderCommand;
import hg.pojo.command.mileOrder.CreateMileOrderCommand;
import hg.pojo.command.mileOrder.ImportMileOrderCommand;
import hg.pojo.command.mileOrder.ModifyMileOrderCommand;
import hg.pojo.command.mileOrder.RemoveMileOrderCommand;
import hg.pojo.exception.LcfxException;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.DistributorQo;
import hg.pojo.qo.MileOrderQo;
import hg.pojo.qo.MileOrderQo;
import hg.system.model.auth.AuthUser;
import jxc.app.dao.system.MileOrderDao;
import jxc.app.dao.system.MileOrderDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.distributor.Distributor;
import jxc.domain.model.distributor.MileOrder;
import jxc.domain.util.CodeUtil;
import jxc.domain.util.Tools;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.BLACK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.nio.serialization.Data;

@Service
public class MileOrderService extends
		BaseServiceImpl<MileOrder, MileOrderQo, MileOrderDao> {
	@Autowired
	private MileOrderDao mileOrderDao;
	@Autowired
	private DistributorService distributorService;

	@Override
	protected MileOrderDao getDao() {
		return mileOrderDao;
	}

	@Autowired
	private JxcLogger logger;

	public boolean remove(RemoveMileOrderCommand command) {
		boolean allDelete = true;
		List<String> idList = command.getIds();
		for (String id : idList) {
			MileOrder mileOrder = get(id);
			if (mileOrder.getStatus().intValue() == MileOrder.STATUS_NO_CHECK) {
				deleteById(id);
			} else if (mileOrder.getStatus().intValue() == MileOrder.STATUS_CHECK_PASS) {
				mileOrder.setStatusRemoved(true);
				update(mileOrder);
			} else if (mileOrder.getStatus().intValue() == MileOrder.STATUS_TO_CSAIR
					|| mileOrder.getStatus().intValue() == MileOrder.STATUS_CSAIR_SUCCEED
					|| mileOrder.getStatus().intValue() == MileOrder.STATUS_CSAIR_ERROR) {
				allDelete = false;
			}
		}
		return allDelete;

	}

	public void modify(ModifyMileOrderCommand command) {
		MileOrder mileOrder = get(command.getId());
		mileOrder.modify(command);

		update(mileOrder);

	}

	public void importExcel(ImportMileOrderCommand importMileOrderCommand) {
		Distributor distributor = distributorService.get(importMileOrderCommand
				.getDistributorId());

		List<CreateMileOrderCommand> l = importMileOrderCommand.getList();
		for (CreateMileOrderCommand createMileOrderCommand : l) {
			MileOrder mileOrder = new MileOrder();
			mileOrder.setCount(createMileOrderCommand.getNum() * 100);
			mileOrder.create(createMileOrderCommand, importMileOrderCommand,
					distributor.getCode());
			save(mileOrder);
		}

	}

	public void check(CheckMileOrderCommand command) {
		List<String> idList = command.getIds();
		for (String id : idList) {
			MileOrder mileOrder = get(id);
			if (mileOrder.getStatus().intValue() == MileOrder.STATUS_NO_CHECK) {
				mileOrder.setStatus(MileOrder.STATUS_CHECK_PASS);
				AuthUser checkPerson = new AuthUser();
				checkPerson.setId(command.getCheckPersonId());
				mileOrder.setCheckPerson(checkPerson);
				mileOrder.setCheckDate(new Date());
				update(mileOrder);
			}
		}

	}

	public HSSFWorkbook export(MileOrderQo qo) {
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("1");
		String[] headers = "分销商，订单号，支付时间，系统流水号，里程面值（单位：里程），数量，合计（单位：里程），订单状态，导入订单时间，订单审核时间，提交南航时间，南航处理完成时间"
				.split("，");

		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
		}

		List<MileOrder> list = queryList(qo);
		for (int i = 0; i < list.size(); i++) {
			MileOrder order = list.get(i);

			HSSFRow line = sheet.createRow(i + 1);

			line.createCell(0).setCellValue(order.getDistributor().getName());
			line.createCell(1).setCellValue(order.getOrderCode());
			line.createCell(2).setCellValue(
					Tools.dateToStringForExport(order.getPayDate()));
			line.createCell(3).setCellValue(order.getFlowCode());
			line.createCell(4).setCellValue("100");
			line.createCell(5).setCellValue(order.getNum());
			line.createCell(6).setCellValue(100 * order.getNum());
			int status = order.getStatus();
			if (status == 200) {
				line.createCell(7).setCellValue("待审核");
			} else if (status == 201) {
				line.createCell(7).setCellValue("审核通过");
			} else if (status == 202) {
				line.createCell(7).setCellValue("审核不通过");
			} else if (status == 203) {
				line.createCell(7).setCellValue("提交南航");
			} else if (status == 204) {
				line.createCell(7).setCellValue("南航处理成功");
			} else if (status == 205) {
				line.createCell(7).setCellValue("南航处理失败");
			}

			line.createCell(8).setCellValue(
					Tools.dateToStringForExport(order.getImportDate()));
			line.createCell(9).setCellValue(
					Tools.dateToStringForExport(order.getCheckDate()));
			line.createCell(10).setCellValue(
					Tools.dateToStringForExport(order.getToCsairDate()));
			line.createCell(11).setCellValue(
					Tools.dateToStringForExport(order.getCsairReturnDate()));

		}
		return workbook;
	}

	public void testStatus(String ids, int s) {
		MileOrderQo qo = new MileOrderQo();
		qo.setId(ids);
		MileOrder o = mileOrderDao.queryUnique(qo);
		if (s == MileOrder.STATUS_TO_CSAIR) {
			o.setToCsairDate(new Date());

		}
		if (s == MileOrder.STATUS_CSAIR_ERROR) {
			o.setCsairInfo("xxxxxx");
		}
		o.setStatus(s);
		update(o);

	}

	public void sendOrder(List<MileOrder> sendMileOrderList) {
		if (sendMileOrderList == null) {
			return;
		}
		Date d = new Date();
		for (MileOrder mileOrder : sendMileOrderList) {
			mileOrder.setStatus(MileOrder.STATUS_TO_CSAIR);
			mileOrder.setToCsairDate(d);
			update(mileOrder);
		}

	}

	public void sendOrderByOrderCode(String id) {
		MileOrder mileOrder=get(id);
		if(mileOrder==null){
			return;
		}
		Date d = new Date();
		mileOrder.setStatus(MileOrder.STATUS_TO_CSAIR);
		mileOrder.setToCsairDate(d);
		update(mileOrder);

	}

	public String saveHjfMileOrder(MileOrder mileOrder) {
		DistributorQo qo = new DistributorQo();
		qo.setName("汇积分");
		qo.setNameLike(false);
		Distributor d = distributorService.queryUnique(qo);
		mileOrder.setFlowCode(CodeUtil.getMileOrderFlowCode(d.getCode()));
		mileOrder.setId(UUIDGenerator.getUUID());
		mileOrder.setStatusRemoved(false);
		mileOrder.setDistributor(d);
		mileOrder.setStatus(MileOrder.STATUS_TO_CSAIR);
		mileOrder.setImportDate(new Date());

		save(mileOrder);
		return "1";
	}

	/**
	 * 
	 * @方法功能说明：更新返回订单状态
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2016-2-18下午4:59:09
	 * @version：
	 * @修改内容：
	 * @参数：@param orderCode
	 * @参数：@param csairInfo
	 * @参数：@param Status
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String updateReturnMileOrder(String orderCode, String csairInfo,
			int Status) {
		MileOrderQo qo = new MileOrderQo();
		qo.setOrderCode(orderCode);
		MileOrder mileOrder = queryUnique(qo);
		mileOrder.setStatus(Status);
		mileOrder.setCsairInfo(csairInfo);
		mileOrder.setCsairReturnDate(new Date());
		update(mileOrder);
		return "1";
	}
}
