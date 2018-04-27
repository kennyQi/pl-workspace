/**
 * @文件名称：RuleService.java
 * @类路径：hgtech.jfadmin.service
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月22日上午9:27:32
 */
package hgtech.jfadmin.service.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.page.Pagination;
import hgtech.jfadmin.dao.CalFlowDao;
import hgtech.jfadmin.dto.CalLogDto;
import hgtech.jfadmin.hibernate.CalFlowHiberEntity;
import hgtech.jfadmin.hibernate.RuleHiberEntity;
import hgtech.jfadmin.service.CalFlowService;
import hgtech.jfadmin.service.RuleService;
import hgtech.jfcal.model.Rule;

/**
 * @类功能说明：对外计算服务接口
 * @类修改者：
 * @修改日期：2014年10月31日 
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xy
 * @创建时间：2014年10月31日 
 *
 */
@Transactional
@Service("calFlowService")
public   class CalFlowServiceImp  implements CalFlowService{

	@Resource
	CalFlowDao  calFlowDao;
	
	@Autowired
	RuleService ruleService;
	
	@Override
	public Pagination findPagination(Pagination pagination) {
		return calFlowDao.findPagination(pagination);
	}

	@Override
	public Pagination findTradeFlowPagination(Pagination pagination) {
		return calFlowDao.findTradeFlowPagination( pagination);
	}

	@Override
	public Pagination findPaginationActivityStat(Pagination pagination) {
		
		
		return calFlowDao.findPaginationActivityStat(pagination);
	}
	/**
	 * 刷新Rule
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年9月8日下午3:57:35
	 * @version：
	 * @修改内容：
	 * @参数：@param lst
	 * @return:void
	 * @throws
	 */
	public static void refreshRule(List<CalFlowHiberEntity> lst,RuleService ruleService){
		for (Object en : lst) {
		    	CalFlowHiberEntity entity=(CalFlowHiberEntity) en;
		    	 
			if (!StringUtils.isBlank(entity.getIn_rulecode())) {
				RuleHiberEntity rule = ruleService.queryByCode(entity.getIn_rulecode());
				
				if (rule!=null) {
					Rule r=new Rule();
					r.setCode(rule.getCode());
					r.setName(rule.getName());
					entity.setIn_rule(r) ;
				}
			}
		}
	}

	@Override
	public HSSFWorkbook export(CalLogDto dto) {
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("1");
		String[] headers = "账户，发放汇积分，备注，活动代码，活动名称，发放时间，收货人，收货人手机，收货地址".split("，");

		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
		}

		List<CalFlowHiberEntity> list = calFlowDao.findList(dto);
		for (int i = 0; i < list.size(); i++) {
			CalFlowHiberEntity entity = list.get(i);
			
			HSSFRow line = sheet.createRow(i + 1);

			line.createCell(0).setCellValue(entity.getIn_userCode());
			line.createCell(1).setCellValue(entity.getOut_jf());
			line.createCell(2).setCellValue(entity.getOut_resultText());
			line.createCell(3).setCellValue(entity.getIn_rulecode());
			RuleHiberEntity rule = ruleService.queryByCode(entity.getIn_rulecode());
			if (rule!=null) {
				String activityName =rule.getName();
				line.createCell(4).setCellValue(activityName);
				
			}
			else {
				line.createCell(4).setCellValue("该活动已删除");
				
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			line.createCell(5).setCellValue(sdf.format(entity.getCalTime()));

			line.createCell(6).setCellValue(entity.receiverName);
			line.createCell(7).setCellValue(entity.receiverMobile);
			line.createCell(8).setCellValue(entity.receiverAddress);
		}
		return workbook;
		
	}

	public static  long getCalFlowTotal(List<CalFlowHiberEntity> lst) {
		long sum = 0;
		for (CalFlowHiberEntity f : lst) {
			sum += f.out_jf;
		}
		return sum;
	}
}
