package zzpl.h5.service;

import hg.log.util.HgLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.jp.GNCabinListDTO;
import zzpl.api.client.dto.jp.GNFlightListDTO;
import zzpl.api.client.request.jp.GNBookCommand;
import zzpl.api.client.request.jp.GNCabinQO;
import zzpl.api.client.request.jp.GNFlightQO;
import zzpl.api.client.request.jp.GNTeamBookCommand;
import zzpl.api.client.request.user.CostCenterQO;
import zzpl.api.client.request.user.QueryFrequentFlyerQO;
import zzpl.api.client.request.workflow.ChooseExecutorCommand;
import zzpl.api.client.request.workflow.ChooseStepCommand;
import zzpl.api.client.request.workflow.WorkflowQO;
import zzpl.api.client.response.jp.GNBookResponse;
import zzpl.api.client.response.jp.GNCabinResponse;
import zzpl.api.client.response.jp.GNFlightResponse;
import zzpl.api.client.response.user.QueryCostCenterResponse;
import zzpl.api.client.response.user.QueryFrequentFlyerResponse;
import zzpl.api.client.response.workflow.ChooseExecutorResponse;
import zzpl.api.client.response.workflow.ChooseStepResponse;
import zzpl.api.client.response.workflow.WorkflowResponse;

import com.alibaba.fastjson.JSON;

@Service
public class FlightService {
	
	@Autowired
	private ApiClient apiClient;
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * @Title: getFlightList 
	 * @author guok
	 * @Description: 查询航班列表
	 * @Time 2015年11月26日上午9:10:40
	 * @param gnFlightQO
	 * @param sessionID
	 * @return List<GNFlightListDTO> 设定文件
	 * @throws
	 */
	public List<GNFlightListDTO> getFlightList(GNFlightQO gnFlightQO,String sessionID) {
		HgLogger.getInstance().info("gk", "【FlightService】【getFlightList】"+"gnFlightQO:"+JSON.toJSONString(gnFlightQO));
		ApiRequest request = new ApiRequest("QueryGNFlight", sessionID, gnFlightQO, "1.0");
		GNFlightResponse response = apiClient.send(request, GNFlightResponse.class);
		HgLogger.getInstance().info("gk", "【FlightService】【getFlightList】"+"response:"+JSON.toJSONString(response.getMessage()));
		if (response.getResult() == ApiResponse.RESULT_CODE_OK) {
			List<GNFlightListDTO>flightListDTOs =  response.getFlightGNListDTOs();
			if(gnFlightQO.getStartTime()==null){
				return response.getFlightGNListDTOs();
			}else{
				Date biginTime,endTime;
				String date = gnFlightQO.getStartDate();
				SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm");
				String time = sDateFormat.format(gnFlightQO.getStartTime());
				String timeBegin = date+" "+time;
				SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					endTime= sFormat.parse(timeBegin);
				} catch (ParseException e1) {
					return response.getFlightGNListDTOs();
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endTime);
				calendar.add(Calendar.HOUR, 6);
				endTime=calendar.getTime();
				try {
					biginTime = sFormat.parse(timeBegin);
				} catch (ParseException e) {
					return response.getFlightGNListDTOs();
				}
				List<GNFlightListDTO> resultFlightList = new ArrayList<GNFlightListDTO>();
				for (GNFlightListDTO gnFlightListDTO : flightListDTOs) {
					if(gnFlightListDTO.getStartTime().after(biginTime)&&gnFlightListDTO.getStartTime().before(endTime)){
						resultFlightList.add(gnFlightListDTO);
					}
				}
				return resultFlightList;
			}
		}else {
			List<GNFlightListDTO> dtos = new ArrayList<GNFlightListDTO>();
			return dtos;
		}
		
	}
	
	/**
	 * @Title: getCabinList 
	 * @author guok
	 * @Description: 查询舱位列表
	 * @Time 2015年11月26日上午9:10:52
	 * @param gnCabinQO
	 * @param sessionID
	 * @return List<GNCabinListDTO> 设定文件
	 * @throws
	 */
	public List<GNCabinListDTO> getCabinList(GNCabinQO gnCabinQO,String sessionID) {
		ApiRequest request = new ApiRequest("QueryGNCabin", sessionID, gnCabinQO, "1.0");
		GNCabinResponse gnCabinResponse = apiClient.send(request, GNCabinResponse.class);
		HgLogger.getInstance().info("gk", "【FlightService】【getFlightList】"+"gnCabinResponse:"+JSON.toJSONString(gnCabinResponse.getMessage()));
		if (gnCabinResponse.getResult() == ApiResponse.RESULT_CODE_OK) {
			return gnCabinResponse.getGnCabinListDTOs();
		}else {
			List<GNCabinListDTO> dtos = new ArrayList<GNCabinListDTO>();
			return dtos;
		}
	}

	/**
	 * @Title: order 
	 * @author guok
	 * @Description: 提交订单
	 * @Time 2015年11月27日下午2:45:03
	 * @param command
	 * @param sessionID
	 * @return GNBookCommand 设定文件
	 * @throws
	 */
	public GNBookCommand order(GNBookCommand command,String sessionID) {
		HgLogger.getInstance().info("gk", "【FlightService】【order】"+"GNBookCommand:"+JSON.toJSONString(command));
		return command;
	}
	
	/**
	 * @Title: QueryFrequentFlyer 
	 * @author guok
	 * @Description: 查询常用联系人列表
	 * @Time 2015年11月27日下午2:47:33
	 * @param queryFrequentFlyerQO
	 * @param sessionID
	 * @return List<FrequentFlyerDTO> 设定文件
	 * @throws
	 */
	public QueryFrequentFlyerResponse queryFrequentFlyer(QueryFrequentFlyerQO queryFrequentFlyerQO,String sessionID) {
		HgLogger.getInstance().info("gk", "【FlightService】【queryFrequentFlyer】"+"queryFrequentFlyerQO:"+JSON.toJSONString(queryFrequentFlyerQO));
		ApiRequest request = new ApiRequest("QueryFrequentFlyer", sessionID, queryFrequentFlyerQO, "1.0");
		QueryFrequentFlyerResponse queryFrequentFlyerResponse = apiClient.send(request, QueryFrequentFlyerResponse.class);
		return queryFrequentFlyerResponse;
	}
	
	/**
	 * @Title: queryCostCenter 
	 * @author guok
	 * @Description: 查询成本中心
	 * @Time 2015年11月27日下午4:50:05
	 * @param costCenterQO
	 * @return List<CostCenterDTO> 设定文件
	 * @throws
	 */
	public QueryCostCenterResponse queryCostCenter(CostCenterQO costCenterQO,String sessionID) {
		HgLogger.getInstance().info("gk", "【FlightService】【queryCostCenter】"+"costCenterQO:"+JSON.toJSONString(costCenterQO));
		ApiRequest request = new ApiRequest("QueryCostCenter", sessionID, costCenterQO, "1.0");
		QueryCostCenterResponse response = apiClient.send(request,QueryCostCenterResponse.class);
		return response;
	}
	
	/**
	 * @Title: queryWorkflow 
	 * @author guok
	 * @Description: 查询流程列表
	 * @Time 2015年11月27日下午4:53:30
	 * @param workflowQO
	 * @param sessionID
	 * @return List<WorkflowDTO> 设定文件
	 * @throws
	 */
	public WorkflowResponse queryWorkflow(WorkflowQO workflowQO,String sessionID) {
		HgLogger.getInstance().info("gk", "【FlightService】【queryWorkflow】"+"workflowQO:"+JSON.toJSONString(workflowQO));
		ApiRequest request = new ApiRequest("QueryWorkflow", sessionID, workflowQO, "1.0");		
		WorkflowResponse response = apiClient.send(request,WorkflowResponse.class);
		return response;
	}
	
	/**
	 * @Title: queryStep 
	 * @author guok
	 * @Description: 根据流程查询环节
	 * @Time 2015年11月27日下午4:57:27
	 * @param chooseStepCommand
	 * @param sessionID
	 * @return List<StepDTO> 设定文件
	 * @throws
	 */
	public ChooseStepResponse queryStep(ChooseStepCommand chooseStepCommand,String sessionID) {
		HgLogger.getInstance().info("gk", "【FlightService】【queryStep】"+"chooseStepCommand:"+JSON.toJSONString(chooseStepCommand));
		ApiRequest request = new ApiRequest("ChooseStep", "118d2b0769e54a948801733f551261e3", chooseStepCommand, "1.0");
		ChooseStepResponse response = apiClient.send(request,ChooseStepResponse.class);
		return response;
	}
	
	/**
	 * @Title: queryExecutors 
	 * @author guok
	 * @Description: 选择下一步执行人
	 * @Time 2015年11月27日下午5:07:05
	 * @param chooseExecutorCommand
	 * @param sessionID
	 * @return ChooseExecutorResponse 设定文件
	 * @throws
	 */
	public ChooseExecutorResponse queryExecutors(ChooseExecutorCommand chooseExecutorCommand,String sessionID) {
		HgLogger.getInstance().info("gk", "【FlightService】【queryExecutors】"+"chooseExecutorCommand:"+JSON.toJSONString(chooseExecutorCommand));
		ApiRequest request = new ApiRequest("ChooseExecutor", sessionID, chooseExecutorCommand, "1.0");
		ChooseExecutorResponse response = apiClient.send(request,ChooseExecutorResponse.class);
		return response;
	}

	/**
	 * @Title: bookOrder 
	 * @author guok
	 * @Description: 提交订单
	 * @Time 2015年11月30日下午3:25:19
	 * @param gnBookCommand
	 * @param sessionID
	 * @return GNBookResponse 设定文件
	 * @throws
	 */
	public GNBookResponse bookOrder(GNTeamBookCommand command,String sessionID) {
		ApiRequest request = new ApiRequest("BookGNFlight", sessionID, command, "1.0");		
		GNBookResponse response = apiClient.send(request,GNBookResponse.class);
		return response;
	}
	
}
