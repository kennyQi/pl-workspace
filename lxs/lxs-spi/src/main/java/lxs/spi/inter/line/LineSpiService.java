package lxs.spi.inter.line;

import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.List;

import lxs.app.service.app.CarouselService;
import lxs.app.service.app.RecommendService;
import lxs.app.service.app.SubjectService;
import lxs.app.service.line.LineSelectiveService;
import lxs.app.service.line.LineService;
import lxs.app.service.line.LineSubjectService;
import lxs.app.util.line.ClientKeyUtil;
import lxs.pojo.exception.line.LineException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.qo.xl.XLLineApiQO;
import slfx.api.v1.response.xl.XLQueryLineResponse;
import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.system.LineMessageConstants;

import com.alibaba.fastjson.JSON;

@Service
public class LineSpiService {


	@Autowired
	private LineService lineService;
	@Autowired
	private LineSubjectService lineSubjectService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private CarouselService carouselService;
	@Autowired
	private LineSelectiveService lineSelectiveService;
	/**
	 * 商旅分销客户端
	 */
	@Autowired
	private SlfxApiClient slfxApiClient;
	
	public void updateLineData(XLUpdateLineMessageApiCommand command) throws Exception {
		HgLogger.getInstance().info("lxs_dev","【updateLineData】"+ "开始更新直销线路数据。。。。。"+JSON.toJSONString(command));
		if(command==null||StringUtils.isBlank(command.getLineId())||StringUtils.isBlank(command.getStatus())){
			throw new LineException(LineException.COMMAND_ERROR,JSON.toJSONString(command));
		}
		
		//删除
		if(command.getStatus().equals(LineMessageConstants.AUDIT_DOWN)){
			HgLogger.getInstance().info("lxs_dev", "【updateLineData】"+"更新线路"+"新线路下架");
			try{
				lineService.notForSale(command.getLineId());
				//更新主题
				List<String> subjectIDs = lineSubjectService.refresh(command.getLineId());
				subjectService.refresh(subjectIDs);
				//更新推荐
				recommendService.refresh();
				//更新轮播
				carouselService.refresh();
				//更新精选
				lineSelectiveService.delSelectiveByNullScenicSpot();
			}catch(RuntimeException e){
				e.printStackTrace();
				HgLogger.getInstance().info("lxs_dev", "【updateLineData】"+"删除线路出错"+e);
			}
		}else{//更新或者添加
			if(command.getStatus().equals(LineMessageConstants.AUDIT_UP)){
				HgLogger.getInstance().info("lxs_dev", "【updateLineData】"+"更新线路"+ "新线路上架");
				try{
					lineService.forSale(command.getLineId());
					//此处不能结束，上架还可能有线路信息的更新，要接着做下面的更新操作
				}catch(RuntimeException e){
					e.printStackTrace();
					HgLogger.getInstance().error("lxs_dev","【updateLineData】"+"删除线路出错"+e);
				}
			}
			try{
				HgLogger.getInstance().info("lxs_dev", "【updateLineData】"+"开始更新线路");
				XLLineApiQO qo = new XLLineApiQO();
				qo.setPageNo(1);
				qo.setPageSize(1);
				qo.setId(command.getLineId());
				ApiRequest apiRequest=new ApiRequest("XLQueryLine",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), qo);
				XLQueryLineResponse response = slfxApiClient.send(apiRequest, XLQueryLineResponse.class);
				if(response.getLineList()==null||response.getLineList().size()==0){
					throw new LineException(LineException.NODATA, "找不到线路数据");
				}
				HgLogger.getInstance().info("lxs_dev", "【updateLineData】"+"开始更新线路"+JSON.toJSONString(response.getLineList()));
				slfx.xl.pojo.dto.line.LineDTO lineDTO = response.getLineList().get(0);
				
				//清空本地线路级联信息
				boolean res=lineService.delLineCascadeEntity(lineDTO);
				
				//保存或更新
				if(res){
					lineService.SaveOrUpdateLine(lineDTO);
				}				
				
				HgLogger.getInstance().info("lxs_dev", "【updateLineData】"+"更新线路完成");
				
			}catch(RuntimeException e){
				e.printStackTrace();
				HgLogger.getInstance().error("lxs_dev", "【updateLineData】"+"更新线路出错"+e);
			}
		}
	}

}
