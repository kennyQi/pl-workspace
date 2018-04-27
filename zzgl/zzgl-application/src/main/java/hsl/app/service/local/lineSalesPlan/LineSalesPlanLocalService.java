package hsl.app.service.local.lineSalesPlan;

import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hsl.app.common.util.EntityConvertUtils;
import hsl.app.dao.line.LineDAO;
import hsl.app.dao.lineSalesPlan.LineSalesPlanDao;
import hsl.domain.model.lineSalesPlan.LineSalesPlan;
import hsl.domain.model.lineSalesPlan.LineSalesPlanBaseInfo;
import hsl.domain.model.xl.Line;
import hsl.pojo.command.lineSalesPlan.CreateLineSalesPlanCommand;
import hsl.pojo.command.lineSalesPlan.ModifyLineSalesPlanCommand;
import hsl.pojo.command.lineSalesPlan.UpdateLSPSalesNumCommand;
import hsl.pojo.command.lineSalesPlan.UpdateLineSalesPlanStatusCommand;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanDTO;
import hsl.pojo.exception.LSPException;
import hsl.pojo.qo.line.HslLineQO;
import hsl.pojo.qo.lineSalesPlan.LineSalesPlanQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @类功能说明： 线路销售方案Service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 9:46
 */
@Service
@Transactional
public class LineSalesPlanLocalService extends BaseServiceImpl<LineSalesPlan,LineSalesPlanQO,LineSalesPlanDao> {
	@Autowired
	private LineSalesPlanDao lineSalesPlanDao;
	@Autowired
	private LineDAO lineDAO;
	@Override
	protected LineSalesPlanDao getDao() {
		return lineSalesPlanDao;
	}

	/**
	 * 添加线路销售方案
	 * @param createLineSalesPlanCommand
	 * @return
	 * @throws LSPException
	 */
	public LineSalesPlanDTO addLineSalesPlan(CreateLineSalesPlanCommand createLineSalesPlanCommand)throws LSPException {
		HslLineQO lineQO=new HslLineQO();
		lineQO.setId(createLineSalesPlanCommand.getLineId());
		Line line=lineDAO.queryUnique(lineQO);
		LineSalesPlan lineSalesPlan=new LineSalesPlan();
		lineSalesPlan.createLineSalesPlan(createLineSalesPlanCommand, line);
		lineSalesPlanDao.save(lineSalesPlan);
		LineSalesPlanDTO lineSalesPlanDTO= BeanMapperUtils.getMapper().map(lineSalesPlan, LineSalesPlanDTO.class);
		return lineSalesPlanDTO;
	}

	/**
	 * 修改线路销售方案的状态
	 * @param updateLineSalesPlanStatusCommand
	 * @throws LSPException
	 */
	public void updateLineSalesPlanStatus(UpdateLineSalesPlanStatusCommand updateLineSalesPlanStatusCommand) throws LSPException{
		LineSalesPlanQO lineSalesPlanQO=new LineSalesPlanQO();
		lineSalesPlanQO.setId(updateLineSalesPlanStatusCommand.getPlanId());
		LineSalesPlan lineSalesPlan=lineSalesPlanDao.queryUnique(lineSalesPlanQO);

		/**
		 * 审核的时候。修改为未开始状态，判断当前时间是否到开始时间，如果到了则修改为活动中
		 */
		if(updateLineSalesPlanStatusCommand.getStatus()!=null&&updateLineSalesPlanStatusCommand.getStatus().equals(LineSalesPlanConstant.LSP_STATUS_NOBEGIN)) {
			Date nowDate = new Date();
			//如果修改成为开始状态是发现时间已经到活动开始时间，就修改状态为进行中
			if (nowDate.after(lineSalesPlan.getLineSalesPlanSalesInfo().getBeginDate())) {
				updateLineSalesPlanStatusCommand.setStatus(LineSalesPlanConstant.LSP_STATUS_SALESING);
			}
		}
		lineSalesPlan.updateStatus(updateLineSalesPlanStatusCommand);
		lineSalesPlanDao.update(lineSalesPlan);
	}

	/**
	 * 修改线路销售方案
	 * @param modifyLineSalesPlanCommand
	 * @return
	 * @throws LSPException
	 */
	public LineSalesPlanDTO modifyLineSalesPlan(ModifyLineSalesPlanCommand modifyLineSalesPlanCommand)throws LSPException{
		HslLineQO lineQO=new HslLineQO();
		lineQO.setId(modifyLineSalesPlanCommand.getLineId());
		Line line=lineDAO.queryUnique(lineQO);
		if(line==null){
			throw new LSPException("添加线路查询不到");
		}
		LineSalesPlanQO lineSalesPlanQO=new LineSalesPlanQO();
		lineSalesPlanQO.setId(modifyLineSalesPlanCommand.getPlanId());
		LineSalesPlan lineSalesPlan=lineSalesPlanDao.queryUnique(lineSalesPlanQO);
		if(lineSalesPlan.getLineSalesPlanStatus().getStatus()!= LineSalesPlanConstant.LSP_STATUS_NOCHECK){
			throw new LSPException("修改活动不是未审核活动");
		}
		lineSalesPlan.modifyLineSalesPlan(modifyLineSalesPlanCommand, line);
		lineSalesPlanDao.update(lineSalesPlan);
		LineSalesPlanDTO lineSalesPlanDTO= BeanMapperUtils.getMapper().map(lineSalesPlan, LineSalesPlanDTO.class);
		return lineSalesPlanDTO;
	}

	/**
	 * 修改销售方案的已售数量
	 * @param updateLSPSalesNumCommand
	 * @throws LSPException
	 */
	public void updateLSPSalesNum(UpdateLSPSalesNumCommand updateLSPSalesNumCommand)throws LSPException{
		LineSalesPlanQO lineSalesPlanQO=new LineSalesPlanQO();
		lineSalesPlanQO.setId(updateLSPSalesNumCommand.getPlanId());
		LineSalesPlan lineSalesPlan=lineSalesPlanDao.queryUnique(lineSalesPlanQO);
		Integer pnum=lineSalesPlan.getLineSalesPlanSalesInfo().getProvideNum();
		Date nowDate=new Date();
		Date endDate=lineSalesPlan.getLineSalesPlanSalesInfo().getEndDate();
		/**
		 * 判断当前已售数量是否等于发放数量
		 */
		if((pnum-updateLSPSalesNumCommand.getSalesNum())>0){
			//判断是否到截止时间
			if(nowDate.after(endDate)){
				if(lineSalesPlan.getBaseInfo().getPlanType()== LineSalesPlanBaseInfo.PLANTYPE_GROUP){
					lineSalesPlan.getLineSalesPlanStatus().setStatus(LineSalesPlanConstant.LSP_STATUS_END_GROUP_FAIL);
				}else{
					lineSalesPlan.getLineSalesPlanStatus().setStatus(LineSalesPlanConstant.LSP_STATUS_END);
				}
			}else{
				lineSalesPlan.getLineSalesPlanStatus().setStatus(LineSalesPlanConstant.LSP_STATUS_SALESING);
			}
		}else{
			//只修改秒杀活动是否结束，团购活动是根据支付完成后，修改是否成团
			if(lineSalesPlan.getBaseInfo().getPlanType()== LineSalesPlanBaseInfo.PLANTYPE_SECKILL){
				lineSalesPlan.getLineSalesPlanStatus().setStatus(LineSalesPlanConstant.LSP_STATUS_END);
			}
		}
		lineSalesPlan.getLineSalesPlanSalesInfo().setSoldNum(updateLSPSalesNumCommand.getSalesNum());
		lineSalesPlanDao.update(lineSalesPlan);
	}
}
