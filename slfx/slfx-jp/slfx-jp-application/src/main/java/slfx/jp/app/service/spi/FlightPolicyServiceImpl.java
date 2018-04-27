package slfx.jp.app.service.spi;

import hg.common.util.DateUtil;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.component.FlightCacheManager;
import slfx.jp.app.service.local.FlightPolicyLocalService;
import slfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import slfx.jp.pojo.dto.flight.SlfxFlightPolicyDTO;
import slfx.jp.pojo.dto.supplier.SupplierDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
import slfx.jp.pojo.exception.JPException;
import slfx.jp.qo.admin.supplier.SupplierQO;
import slfx.jp.qo.api.JPPolicySpiQO;
import slfx.jp.qo.client.PolicyByPnrQo;
import slfx.jp.spi.inter.FlightPolicyService;
import slfx.jp.spi.inter.policy.PolicyService;
import slfx.jp.spi.inter.supplier.SupplierService;
import slfx.yg.open.inter.YGFlightService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：平台航班政策SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:45:40
 * @版本：V1.0
 *
 */
@Service("flightPolicyService")
public class FlightPolicyServiceImpl implements FlightPolicyService{
	
	/**
	 * 易购的机票服务类
	 */
	@Autowired
	private YGFlightService ygFlightService;

	/**
	 * 平台的价格政策服务类
	 */
	@Autowired
	private PolicyService policyService;
	
	/**
	 * 航班缓存服务类
	 */
	@Autowired
	private FlightCacheManager flightCacheManager;

	@Autowired
	private FlightPolicyLocalService flightPolicyLocalService;
	
	/**
	 * 供应商管理service
	 */
	@Autowired
	private SupplierService supplierService;
	
	@Override
	public SlfxFlightPolicyDTO queryPlatformPolicy(JPPolicySpiQO qo) throws JPException {
		HgLogger.getInstance().info("tandeng","FlightPolicyServiceImpl->queryPlatformPolicy->JPPolicyQO[平台价格政策查询开始]:"+ JSON.toJSONString(qo));
		if(qo == null){
			throw new JPException(JPException.FLIGHT_POLICY_QUERY_QO_NULL,"查询条件为空");
		}
		
		//根据航班key，从缓存中获取航班信息
		String flightNo=qo.getFlightNo();
		String departDate=qo.getDepartDate();
		String classCode = qo.getClassCode();
		String dealerCode = qo.getDealerCode();
		
		YGFlightDTO ygFlightDTO = flightCacheManager.getFlightDTO(
				flightNo,
				departDate);
		HgLogger.getInstance().info("yuqz", "FlightPolicyServiceImpl->queryPlatformPolicy->ygFlightDTO" + JSON.toJSONString(ygFlightDTO));
		if(ygFlightDTO == null ){
			HgLogger.getInstance().info("tandeng","slfx-jp-application从缓存查询航班信息失败！");
			throw new JPException(JPException.FLIGHT_NOT_IN_CACHE,"从缓存查询航班信息失败");
		}
		//航班起飞日期
		Date departureDate = null;
		if(ygFlightDTO.getStartDate().indexOf("-") != -1){
			departureDate = DateUtil.parseDate5(ygFlightDTO.getStartDate()+" "+ygFlightDTO.getStartTime());			
		}
		if(ygFlightDTO.getStartDate().indexOf("/") != -1){
			departureDate = DateUtil.parseDate6(ygFlightDTO.getStartDate()+" "+ygFlightDTO.getStartTime());			
		}
		//航班到达日期
		Date arrivalDate = null;
		if(ygFlightDTO.getEndDate().indexOf("-") != -1){
			arrivalDate = DateUtil.parseDate5(ygFlightDTO.getEndDate()+" "+ygFlightDTO.getEndTime());
		}
		if(ygFlightDTO.getEndDate().indexOf("/") != -1){
			arrivalDate = DateUtil.parseDate6(ygFlightDTO.getEndDate()+" "+ygFlightDTO.getEndTime());
		}
		
		String boardPoint = ygFlightDTO.getStartPort(); 
		String offPoint = ygFlightDTO.getEndPort();
		
		String carrier = qo.getFlightNo().substring(0, 2);
		
		//Date tempDepartureDate = new Date();
		//Date tempArrivalDate = new Date(tempDepartureDate.getTime()+1000*60*5);
		HgLogger.getInstance().info("yuqz", "FlightPolicyServiceImpl->queryPlatformPolicy:flightNo="+flightNo+
				",classCode="+classCode+",departureDate="+departureDate+",arrivalDate="+arrivalDate
				+",boardPoint="+boardPoint+",offPoint="+offPoint+",carrier="+carrier);
		PolicyByPnrQo policyByPnrQo = new PolicyByPnrQo(
				flightNo,
				classCode,
				departureDate,
				arrivalDate,
				boardPoint,
				offPoint,
				carrier);
		
		//如果还要设置其他信息请在qo添加(易购接口19选6)
		HgLogger.getInstance().info("yuqz", "FlightPolicyServiceImpl->queryPlatformPolicy->policyByPnrQo" + JSON.toJSONString(policyByPnrQo));
		List<SlfxFlightPolicyDTO> policys = ygFlightService.queryPolicyByPNR(policyByPnrQo);
		HgLogger.getInstance().info("yuqz", "FlightPolicyServiceImpl->queryPlatformPolicy->policys" + JSON.toJSONString(policys));
		//平台政策筛选规则（6选1）
		SlfxFlightPolicyDTO policyDTO = this.getPlatformPolicyByCondition(policys,
				flightNo,
				classCode,
				ygFlightDTO,
				dealerCode,
				departureDate,
				arrivalDate);
		HgLogger.getInstance().info("tandeng","FlightPolicyServiceImpl->queryPlatformPolicy->policyDTO[平台价格政策查询结束]:"+ JSON.toJSONString(policyDTO));
		return policyDTO;
	}
	
	/**
	 * 
	 * @方法功能说明：平台按照筛选规则筛选一条政策信息
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月12日下午4:07:12
	 * @修改内容：
	 * @参数：@param policys
	 * @参数：@param fligthNo
	 * @参数：@param classCode
	 * @参数：@param ygFlightDTO
	 * @参数：@param dealerCode
	 * @参数：@param departureDate
	 * @参数：@param arrivalDate
	 * @return:FlightPolicyDTO
	 * @throws
	 */
	
	private SlfxFlightPolicyDTO getPlatformPolicyByCondition(
			List<SlfxFlightPolicyDTO> policys,
			String flightNo,
			String classCode,
			YGFlightDTO ygFlightDTO,
			String dealerCode,
			Date departureDate,
			Date arrivalDate){
		
		SlfxFlightPolicyDTO slfxFlightPolicyDTO = null;
		
		if(policys != null && policys.size() > 0){
			/*1.只选择签约供应商（出票平台）
			 	a.排除8家供应商之外的讯息
				b.排除一切包含特殊政策的机票价格
				c.按照用户当前查询时间，排除不在工作时间内的供应商
				d.用户周末当前查询时间，排除不在周末工作时间内的供应商  
			String qianYuePingTai = SysProperties.getInstance().get("qian_yue_ping_tai");
			String[] qianYuePingTaiArray = qianYuePingTai.split(";");
			String[] qianYuePingTaiArray = new String[]{
					//"BT","001",
					"B5","002",
					//"PM","003",
					"C5","004",
					"YX","007",
					"Y8","008",
					//"CD","016",
					//"ST","033"
				};
			Set<String> qianYuePingTaiSet = new HashSet<String>();
			for (String tempStr : qianYuePingTaiArray) {
				qianYuePingTaiSet.add(tempStr);
			}
			 */
			SupplierQO qo = new SupplierQO();
			qo.setStatus("1");
			List<SupplierDTO> list = supplierService.getSupplierList(qo);
			Set<String> qianYuePingTaiSet = new HashSet<String>();
			
			for (SupplierDTO supplierDTO : list) {
				qianYuePingTaiSet.add(supplierDTO.getCode());
				qianYuePingTaiSet.add(supplierDTO.getNumber());
			}
			
			int i = policys.size()-1;
			for (; i >= 0; i--) {
				SlfxFlightPolicyDTO tempDTO = policys.get(i);
				//a.排除8家供应商之外的讯息
				if(!qianYuePingTaiSet.contains(tempDTO.getPlatCode())){
					policys.remove(i);
					continue;
				}
				//b.排除一切包含特殊政策的机票价格
				if (tempDTO.getIsSpecial()!=null&& tempDTO.getIsSpecial().equals("true")) {
					policys.remove(i);
					continue;
				}
				//c.按照用户当前查询时间，排除不在工作时间内的供应商
				String[] worktimes = tempDTO.getTktWorktime().split("-");; //格式为：08:00-23:59
				SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
				String current = sdf_date.format(new Date());
				Date date01 = null;
				Date date02 = null;
				try {
					SimpleDateFormat sdf_all = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					date01 = sdf_all.parse(current+" " + worktimes[0]);
					date02 = sdf_all.parse(current+" " + worktimes[1]);
					//如果不在工作时间内
					if (!(date01.before(new Date()) && date02.after(new Date()))) {
						policys.remove(i);
						continue;
					}
				} catch (Exception e) {
					HgLogger.getInstance().error("tandeng", "FlightPolicyServiceImpl->getPlatformPolicyByCondition->exception[平台筛选政策]:" + HgLogger.getStackTrace(e));
				}
				

			}
			
			/*2. 政策筛选规则优先级：
			a.排除不满足15分钟出票要求的信息，剩余信息取返点最多，有返点重复数据再次比较取出票最快
      		b.当前信息全部不满足15分钟出票要求，直接取出票时间最快*/
			if(policys.size() > 0){
				sortProduct(policys);//进行排序
				Integer efficiency = policys.get(0).getEfficiency();
				if (efficiency < 15) { //15分钟
					Double commAmount = 0.0;
					int index = 0;
					int j = 0;
					for (SlfxFlightPolicyDTO dto : policys) {
						if (dto.getCommAmount() > commAmount) {
							commAmount = dto.getCommAmount();
							index = j;
						}
						j ++;
					}
					slfxFlightPolicyDTO = policys.get(index);
				} else {
					slfxFlightPolicyDTO = policys.get(0);	
				}
				
				//3 save JPPlatformPolicySnapshot
				JPPlatformPolicySnapshot entity = new JPPlatformPolicySnapshot();
				//机场建设费和燃油附加费从航班信息中获取
				slfxFlightPolicyDTO.setAirportTax(ygFlightDTO.getAirportTax());
				slfxFlightPolicyDTO.setFuelSurTax(ygFlightDTO.getFuelSurTax());
				
				slfxFlightPolicyDTO.setDealerCode(dealerCode);
				slfxFlightPolicyDTO.setFltStart(departureDate);
				slfxFlightPolicyDTO.setFltEnd(arrivalDate);
				slfxFlightPolicyDTO.setFlightNo(flightNo);
				slfxFlightPolicyDTO.setClassCode(classCode);
				
				BeanUtils.copyProperties(slfxFlightPolicyDTO, entity);
				String uuid = UUIDGenerator.getUUID();
				entity.setId(uuid);
				entity.setCreateDate(new Date());
				flightPolicyLocalService.save(entity);
				slfxFlightPolicyDTO.setPolicyId(uuid);
				
			}
			
		}
		return slfxFlightPolicyDTO;
	}
	
	/**
	 * @param productList
	 *            排序对象
	 */
	private void sortProduct(List<SlfxFlightPolicyDTO> productList) {
		short sort = 1;
		sortProduct(productList, sort);
	}

	/**
	 * 
	 * @param productList
	 *            排序对象
	 * @param sortType
	 *            1 逆序 2 顺序
	 */
	private void sortProduct(List<SlfxFlightPolicyDTO> productList, short sortType) {
		int size = productList.size();
		SlfxFlightPolicyDTO[] arrs = new SlfxFlightPolicyDTO[size];
		productList.toArray(arrs);

		SlfxFlightPolicyDTO temp;
		for (int i = 0; i < arrs.length; i++) {// 趟数
			for (int j = 0; j < arrs.length - i - 1; j++) {// 比较次数
				if (sortType(arrs[j], arrs[j + 1], sortType)) {

					temp = arrs[j];
					arrs[j] = arrs[j + 1];
					arrs[j + 1] = temp;
				}
			}

		}
		productList.clear();
		for (SlfxFlightPolicyDTO product : arrs) {
			if (product.getEfficiency() <= 15) {
				productList.add(product);
			}
		}
		if (productList.size() == 0) {
			productList.add(arrs[0]);
		}
	}

	// 判断
	private boolean sortType(SlfxFlightPolicyDTO pro1, SlfxFlightPolicyDTO pro2,
			short sortType) {
		int inte1 = pro1.getEfficiency();
		int inte2 = pro2.getEfficiency();
		if (sortType == 1) { // 1 逆序
			if (inte1 > inte2) {
				return false;
			} else {
				return true;
			}

		} else if (sortType == 2) {// 2 顺序
			if (inte1 < inte2) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		String qianYuePingTai = SysProperties.getInstance().get("qian_yue_ping_tai");
		String[] qianYuePingTaiArray = qianYuePingTai.split(";");
		Set<String> qianYuePingTaiSet = new HashSet<String>();
		for (String tempStr : qianYuePingTaiArray) {
			qianYuePingTaiSet.add(tempStr.split("-")[0]);
		}
		
		System.out.println("qianYuePingTai="+qianYuePingTaiSet.toString());
		System.out.println(qianYuePingTaiSet.contains("YX"));
		System.out.println(qianYuePingTaiSet.contains("YX  "));
	}
	
	
}
