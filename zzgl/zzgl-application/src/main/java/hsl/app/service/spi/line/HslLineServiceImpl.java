package hsl.app.service.spi.line;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.EntityConvertUtils;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.line.LineLocalService;
import hsl.domain.model.xl.Line;
import hsl.pojo.command.line.InitLineCommand;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.dto.line.LineImageDTO;
import hsl.pojo.exception.LineException;
import hsl.pojo.log.PLZXClickRecord;
import hsl.pojo.qo.line.HslLineQO;
import hsl.pojo.qo.log.PLZXClickRecordQo;
import hsl.pojo.util.LogFormat;
import hsl.spi.inter.clickRecord.PLZXClickRecordService;
import hsl.spi.inter.line.HslLineService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.api.client.api.v1.xl.request.qo.XLLineApiQO;
import plfx.api.client.api.v1.xl.response.XLQueryLineResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.SlfxApiClient;
import plfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import plfx.xl.pojo.system.LineMessageConstants;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：线路本地服务
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhuxy
 * @创建时间：2015年2月26日
 */
@Service
public class HslLineServiceImpl extends BaseSpiServiceImpl<LineDTO, HslLineQO,LineLocalService> implements HslLineService{
	
	@Autowired
	private LineLocalService lineLocalService;
	@Autowired
	private PLZXClickRecordService plzxClickRecordService;
	/**
	 * 商旅分销客户端
	 */
	@Autowired
	private SlfxApiClient slfxApiClient;

	@Override
	protected LineLocalService getService() {
		return lineLocalService;
	}

	@Override
	protected Class<LineDTO> getDTOClass() {
		return LineDTO.class;
	}

	@Override
	public void updateLineData(XLUpdateLineMessageApiCommand command) throws Exception {
		HgLogger.getInstance().info("renfeng", LogFormat.log("开始更新直销线路数据。。。。。", JSON.toJSONString(command)));
		if(command==null||StringUtils.isBlank(command.getLineId())||StringUtils.isBlank(command.getStatus())){
			throw new LineException(LineException.COMMAND_ERROR,JSON.toJSONString(command));
		}
		
		//删除
		if(command.getStatus().equals(LineMessageConstants.AUDIT_DOWN)){
			HgLogger.getInstance().info("renfeng", LogFormat.log("更新线路", "新线路下架"));
			try{
				lineLocalService.notForSale(command.getLineId());
			}catch(RuntimeException e){
				e.printStackTrace();
				HgLogger.getInstance().error("renfeng", LogFormat.log("删除线路出错", HgLogger.getStackTrace(e)));
			}
		}else{//更新或者添加
			if(command.getStatus().equals(LineMessageConstants.AUDIT_UP)){
				HgLogger.getInstance().info("renfeng", LogFormat.log("更新线路", "新线路上架"));
				try{
					lineLocalService.forSale(command.getLineId());
					//此处不能结束，上架还可能有线路信息的更新，要接着做下面的更新操作
				}catch(RuntimeException e){
					e.printStackTrace();
					HgLogger.getInstance().error("renfeng", LogFormat.log("线路上架出错", HgLogger.getStackTrace(e)));
				}
			}else if(command.getStatus().equals(LineMessageConstants.UPDATE_DATE_SALE_PRICE)){
				HgLogger.getInstance().info("renfeng", LogFormat.log("更新线路", "更新价格日历"));
			}else if(command.getStatus().equals(LineMessageConstants.UPDATE_PICTURE)){
				HgLogger.getInstance().info("renfeng", LogFormat.log("更新线路", "更新图片"));
			}
			try{
				HgLogger.getInstance().info("renfeng", LogFormat.log("开始更新线路",""));
				XLLineApiQO qo = new XLLineApiQO();
				qo.setPageNo(1);
				qo.setPageSize(1);
				qo.setId(command.getLineId());
				ApiRequest apiRequest=new ApiRequest("XLQueryLine",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), qo);
				XLQueryLineResponse response = slfxApiClient.send(apiRequest, XLQueryLineResponse.class);
				if(response.getLineList()==null||response.getLineList().size()==0){
					throw new LineException(LineException.NODATA, "找不到线路数据");
				}
				HgLogger.getInstance().info("renfeng", LogFormat.log("开始更新线路",JSON.toJSONString(response.getLineList())));
				plfx.xl.pojo.dto.line.LineDTO lineDTO = response.getLineList().get(0);
				
				//清空本地线路级联信息
				boolean res=lineLocalService.delLineCascadeEntity(lineDTO);
				
				//保存或更新
				if(res){
					lineLocalService.SaveOrUpdateLine(lineDTO);
				}				
				
				HgLogger.getInstance().info("renfeng", LogFormat.log("更新线路完成",""));
				
			}catch(RuntimeException e){
				e.printStackTrace();
				HgLogger.getInstance().error("renfeng", LogFormat.log("更新线路出错", HgLogger.getStackTrace(e)));
			}
		}
	}

	@Override
	public void initLineData(InitLineCommand command) throws Exception {
		lineLocalService.initLineData(command);
	}

	@Override
	public double getProgress() {
		return lineLocalService.getProgress();
	}

	@Override
	public void stopInit() {
		lineLocalService.stopInit();
	}

	
	@Override
	public LineDTO queryLine(HslLineQO lineQO) {
		Line line=this.lineLocalService.queryUnique(lineQO);
		return BeanMapperUtils.map(line, LineDTO.class);
	}
	
	@Override
	public Pagination queryPagination(Pagination pagination) {
		pagination = super.queryPagination(pagination);
		
		List<LineDTO> lineDTOList= EntityConvertUtils.convertEntityToDtoList(pagination.getList(), LineDTO.class);
		for(LineDTO line:lineDTOList){
			List<LineImageDTO> lineImageList=line.getLineImageList();
			for(LineImageDTO lineImage:lineImageList){
				lineImage.setUrlMaps(lineImage.getUrlMaps());
			}
		}
		pagination.setList(lineDTOList);
		
		return pagination;
	}

	@Override
	public List<LineDTO> queryUserClickRecord(PLZXClickRecordQo plzxClickRecordQo) {
		plzxClickRecordQo.setPageSize(2);
		List<PLZXClickRecord> plzxClickRecords=plzxClickRecordService.queryPLZXClickRecord(plzxClickRecordQo);
		List<LineDTO> lineDTOs=new ArrayList<LineDTO>();
		for(PLZXClickRecord plzxClickRecord:plzxClickRecords){
			HslLineQO lineQO=new HslLineQO();
			lineQO.setId(plzxClickRecord.getObjectId());
			Line line=lineLocalService.queryUnique(lineQO);
			if(line!=null){
				LineDTO lineDTO=BeanMapperUtils.map(line, LineDTO.class);
				lineDTOs.add(lineDTO);
			}
			
		}
		return lineDTOs;
	}
}
