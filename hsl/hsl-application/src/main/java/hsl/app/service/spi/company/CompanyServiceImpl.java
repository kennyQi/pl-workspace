package hsl.app.service.spi.company;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hg.log.util.HgLogger;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.company.CompanyLocalService;
import hsl.app.service.local.jp.JPOrderLocalService;
import hsl.app.service.local.mp.MPOrderLocalService;
import hsl.app.service.local.mp.ScenicSpotLocalService;
import hsl.domain.model.jp.JPOrder;
import hsl.domain.model.jp.JPPassanger;
import hsl.pojo.command.CreateCompanyCommand;
import hsl.pojo.command.CreateDepartmentCommand;
import hsl.pojo.command.CreateMemberCommand;
import hsl.pojo.command.DeleteMemberCommand;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.DepartmentDTO;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.dto.company.TravelDTO;
import hsl.pojo.dto.jp.FlightDTO;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.company.HslDepartmentQO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.pojo.qo.company.HslTravelQO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.spi.inter.company.CompanyService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class CompanyServiceImpl extends BaseSpiServiceImpl<TravelDTO, BaseQo, CompanyLocalService> implements CompanyService {
	@Autowired
	private CompanyLocalService companyLocalService;
	
	@Autowired
	private MPOrderLocalService mpOrderLocalService;
	
	@Autowired
	private JPOrderLocalService jpOrderlocalservice;
	
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	@Autowired
	private HgLogger hgLogger;
	private SimpleDateFormat dateformater1=new SimpleDateFormat("yyyy-MM-dd hh:mm");
	private SimpleDateFormat dateformater2=new SimpleDateFormat("yyyy/MM/dd hh:mm");
	@Override
	public List<CompanyDTO> getCompanys(HslCompanyQO qo) {
		List<CompanyDTO> dtolist=companyLocalService.getCompanys(qo);
		return dtolist;
	}


	@Override
	public List<MemberDTO> getMembers(HslMemberQO qo) {
		List<MemberDTO> list=companyLocalService.getMembers(qo);
		return list;
	}

	@Override
	public CompanyDTO addCompany(CreateCompanyCommand cmd) {
		CompanyDTO dto=companyLocalService.addCompany(cmd);
		return dto;
	}

	@Override
	public DepartmentDTO addDepartment(CreateDepartmentCommand cmd) {
		DepartmentDTO dto=companyLocalService.addDepart(cmd);
		return dto;
	}

	@Override
	public MemberDTO addMember(CreateMemberCommand cmd) {
		MemberDTO dto=companyLocalService.addMember(cmd);
		return dto;
	}

	@Override
	public MemberDTO delMember(DeleteMemberCommand cmd) {
		MemberDTO dto=companyLocalService.delMember(cmd);
		return dto;
	}

	@Override
	public List<TravelDTO> getTravelInfo(HslTravelQO qo) {
		List<TravelDTO> list=new ArrayList<TravelDTO>();
		//项目类型(0:全部;1:机票;2:门票;)
		//MPOrderUser,下单人,下单时间
		if(StringUtils.isNotBlank(qo.getMemberId())){//选中某个人
			list=getByIds(qo.getProjectType(),qo.getStartTime(),qo.getEndTime(),qo.getMemberId());
		}else if(StringUtils.isNotBlank(qo.getDepartmentId())){//某个部门
			String[] ids=getDepartMemberIds(qo.getDepartmentId());
			list=getByIds(qo.getProjectType(),qo.getStartTime(),qo.getEndTime(),ids);
		}else if(StringUtils.isNotBlank(qo.getCompanyId())){//一个公司
			String[] ids=getDepartid(qo.getCompanyId());
			for(String id:ids){
				String[] memberids=getDepartMemberIds(id);
				list.addAll(getByIds(qo.getProjectType(),qo.getStartTime(), qo.getEndTime(), memberids));
			}
		}
		return list;
	}
	/**
	 * 获得一个公司下属的部门id数组
	 * @param companyid
	 * @return
	 */
	private String[] getDepartid(String companyid){
		HslDepartmentQO qo=new HslDepartmentQO();
		HslCompanyQO companyQO = new HslCompanyQO();
		companyQO.setId(companyid);
		qo.setCompanyQO(companyQO);
		
		List<DepartmentDTO> list=companyLocalService.getDepartments(qo);
		String[] ids=new String[list.size()];
		for(int i=0;i<list.size();i++){
			DepartmentDTO department=list.get(i);
			ids[i]=department.getId();
		}
		return ids;
	}
	/**
	 * 获得一个部门下的所有成员id数组
	 * @param departid
	 * @return
	 */
	private String[] getDepartMemberIds(String departid){
		
		HslMemberQO memberqo=new HslMemberQO();
		memberqo.setDepartmentId(departid);
//		memberqo.setIsDel(false);
		List<MemberDTO> members=companyLocalService.getMembers(memberqo);
		String[] memberids=new String[members.size()];
		for(int i=0;i<members.size();i++){
			MemberDTO member=members.get(i);
			memberids[i]=member.getId();
		}
		return memberids;
	}
	/**
	 * 根据成员id，差旅类型，开始/结束时间来查询结果
	 * @param type
	 * @param beginTime
	 * @param endtime
	 * @param ids
	 * @return
	 */
	private List<TravelDTO> getByIds(int type,String beginTime,String endtime,String... ids ){
		ArrayList<TravelDTO> list=new ArrayList<TravelDTO>();
		try {
			if(type==2){
				list.addAll(getJPInfoByIds(beginTime,endtime, ids));
			}else if(type==1){
				list.addAll(getMPInfoByIds(beginTime,endtime, ids));
			}else if(type==0){
				list.addAll(getJPInfoByIds(beginTime,endtime, ids));
				list.addAll(getMPInfoByIds(beginTime,endtime, ids));
			}
		} catch (ParseException e) {
			HgLogger.getInstance().error("zhangka", "CompanyServiceImpl->getByIds->exception:" + HgLogger.getStackTrace(e));
		}
		return list;
	}
	//机票
	private List<TravelDTO> getJPInfoByIds(String beginTime,String endtime,String... ids) throws ParseException{
		ArrayList<TravelDTO> list=new ArrayList<TravelDTO>();
//		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
		for(String id:ids){
//			JPOrderQO jpqo=new JPOrderQO();
			HslJPOrderQO hslJPOrderQO=new HslJPOrderQO();
			hslJPOrderQO.setMemeberId(id);
			hslJPOrderQO.setFlightStartTime(beginTime);
			hslJPOrderQO.setFlightEndTime(endtime);
//			List<JPOrderDTO> dtos=jpServiceImpl.queryOrder(jpqo);
			//机票订单查询
			List<JPOrder> jpOrders=jpOrderlocalservice.queryOrder(hslJPOrderQO);
			//passanger
			for(JPOrder jpOrder:jpOrders){
				Set<JPPassanger> jpPassangers=jpOrder.getPassangers();
				FlightDTO flightDTO=JSON.parseObject(jpOrder.getJpOrderSnapshot(), FlightDTO.class);
				for(JPPassanger jpPassanger:jpPassangers){
					TravelDTO travelDTO=new TravelDTO();
					travelDTO.setCompanyName(jpPassanger.getCompanyName());
					travelDTO.setDeptName(jpPassanger.getDepartmentName());
					travelDTO.setDestination(flightDTO.getEndPortName());//目的地
					HslMemberQO qo=new HslMemberQO();
					qo.setId(jpPassanger.getMemeberId());
					MemberDTO member=companyLocalService.getMember(qo);
					travelDTO.setJob(member.getJob());//职务
					travelDTO.setMemberName(jpPassanger.getName());
					travelDTO.setOrderNum(jpOrder.getDealerOrderCode());
					travelDTO.setPrice(jpOrder.getTicketPrice());
					travelDTO.setProjectType(1);
					String timestr=flightDTO.getStartDate()+" "+flightDTO.getStartTime();
					Date time=null;
					if(timestr.indexOf("-")>0){
						time=dateformater1.parse(timestr);
					}else if(timestr.indexOf("/")>0){
						time=dateformater2.parse(timestr);
					}else{
						hgLogger.error("wuyg", "unknown parttern time:"+timestr);
						time=new Date();
					}
					travelDTO.setTarvelDate(time);
					list.add(travelDTO);
				}
			}
		}
		return list;
	}
	
	//门票
	private List<TravelDTO> getMPInfoByIds(String beginTime,String endtime,String... ids ){
		ArrayList<TravelDTO> list=new ArrayList<TravelDTO>();
		try {
			for(String id:ids){
				HslMPOrderQO mpOrderQO=new HslMPOrderQO();
				mpOrderQO.setMemberId(id);
				if(beginTime!=null){
					mpOrderQO.setStartTravelTime(beginTime);
				}
				if(endtime!=null){
					mpOrderQO.setEndTravelTime(endtime);
				}
				List<MPOrderDTO> mpdtolist=scenicSpotLocalService.getMPOrderList(mpOrderQO);
//				List<MPOrder> orderlist=(List<MPOrder>) map.get("list");
//				List<MPOrderDTO> mpdtolist = EntityConvertUtils.convertEntityToDtoList(orderlist, MPOrderDTO.class);
				for(MPOrderDTO mpOrder:mpdtolist){
					UserDTO mpOrderUser=mpOrder.getTakeTicketUser();
					TravelDTO travelDTO=new TravelDTO();
					travelDTO.setOrderNum(mpOrder.getDealerOrderCode());//经销商id
					SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
					travelDTO.setTarvelDate(formater.parse(mpOrder.getTravelDate()));
					travelDTO.setProjectType(2);
					travelDTO.setPrice(mpOrder.getPrice());
					travelDTO.setCompanyName(mpOrderUser.getCompanyName());
					travelDTO.setDeptName(mpOrderUser.getDepartmentName());
					HslMemberQO qo=new HslMemberQO();
					qo.setId(id);
					MemberDTO member=companyLocalService.getMember(qo);
					travelDTO.setJob(member.getJob());
					travelDTO.setDestination(mpOrder.getScenicSpot().getScenicSpotGeographyInfo().getAddress());
					travelDTO.setMemberName(mpOrderUser.getName());
					list.add(travelDTO);
				}
//				int count=(Integer) map.get("count");
			}
		} catch (MPException e) {
			e.printStackTrace();
			hgLogger.error("wuyg",e.getCode()+ e.getMessage());
			HgLogger.getInstance().error("zhangka", "CompanyServiceImpl->getMPInfoByIds->exception:" + HgLogger.getStackTrace(e));
		} 
		catch (ParseException e) {
			e.printStackTrace();
			hgLogger.error("wuyg", "ParseException:"+e.getMessage());
			HgLogger.getInstance().error("zhangka", "CompanyServiceImpl->getMPInfoByIds->exception:" + HgLogger.getStackTrace(e));
		}
		return list;
	}
	@Override
	protected CompanyLocalService getService() {
		return companyLocalService;
	}

	@Override
	protected Class<TravelDTO> getDTOClass() {
		return TravelDTO.class;
	}

	@Override
	public List<DepartmentDTO> getDepartments(HslDepartmentQO qo) {
		List<DepartmentDTO> list=companyLocalService.getDepartments(qo);
		return list;
	}
	@Override
	public void importMembers(List<CreateMemberCommand> cmdlist) throws Exception{
		companyLocalService.addMemberList(cmdlist);
	}

	@Override
	public Pagination getTravelInfoPage(HslTravelQO qo) {
		Pagination pagination=new Pagination();
		int page=qo.getPage()==0?1:qo.getPage();
		pagination.setPageNo(page);
		int size=qo.getPageSize()==0?10:qo.getPageSize();
		int deviationNo = qo.getPageNo();
		pagination.setPageSize(size);
		int start=(page-1)*size-deviationNo;
		int end=page*size-deviationNo;
		List<TravelDTO> list=new ArrayList<TravelDTO>();
		int sum=0;
		//项目类型(0:全部;2:机票;1:门票;)
		//MPOrderUser,下单人,下单时间
		if(StringUtils.isNotBlank(qo.getMemberId())){//选中某个人
			if(sum<=start){
				int count=getCountById(qo.getProjectType(), qo.getStartTime(),qo.getEndTime(),qo.getMemberId());
				if(sum+count<=start){
					sum+=count;
					pagination.setTotalCount(sum);
				}else{
					List<TravelDTO> dtos=getByIds(qo.getProjectType(), qo.getStartTime(),qo.getEndTime(),qo.getMemberId());
					int si=start-sum;
					while(si>=10){
						si-=10;
					}
					list.addAll(dtos.subList(start==0?start:si,(start==0?start:si)+(size-list.size())>dtos.size()?dtos.size():(size-list.size())));
					pagination.setList(list);
					pagination.setTotalCount(sum+count);
				}
			}
		}else if(StringUtils.isNotBlank(qo.getDepartmentId())){//某个部门
			String[] ids=getDepartMemberIds(qo.getDepartmentId());
			for(String id:ids){
				int count=getCountById(qo.getProjectType(), qo.getStartTime(),qo.getEndTime(),id);
				sum+=count;
				if(sum<=start){
					continue;
				}else{
					if(list.size()<size){
						List<TravelDTO> dtos=getByIds(qo.getProjectType(), qo.getStartTime(),qo.getEndTime(),id);
						int si=start-sum;
						while(si>=10){
							si-=10;
						}
						list.addAll(dtos.subList(start==0?start:si,(start==0?start:si)+(size-list.size())>dtos.size()?dtos.size():(size-list.size())));
					}
				}
			}
			pagination.setList(list);
			pagination.setTotalCount(sum);
		}else if(StringUtils.isNotBlank(qo.getCompanyId())){//一个公司
			String[] ids=getDepartid(qo.getCompanyId());
			for(String departid:ids){
				String[] memberids=getDepartMemberIds(departid);
				for(String id:memberids){
int count=getCountById(qo.getProjectType(), qo.getStartTime(),qo.getEndTime(),id);
					
					if(sum+count<=start||count==0){
						//还没到start
						sum+=count;
						continue;
					}else{
						if(list.size()<size){
							List<TravelDTO> dtos=getByIds(qo.getProjectType(), qo.getStartTime(),qo.getEndTime(),id);
							int si=start-sum;
							while(si>=10){
								si-=10;
							}
							//合并list，已有数据的list中加入dtos的子列表，
							int begin=0;
							int len =0;
							int emptysize=size-list.size();
							if(list.isEmpty()){
								begin=start-sum;
								len = emptysize>dtos.size()?dtos.size():emptysize; 
							}else{
								begin=0;
								len= emptysize>dtos.size()?dtos.size():emptysize;
							}
							System.out.println("begin:"+begin);
							System.out.println("len:"+len);
							list.addAll(dtos.subList(begin,begin+len));
						}
						sum+=count;
					}
				}
			}
			pagination.setList(list);
			pagination.setTotalCount(sum);
		}else if(StringUtils.isNotBlank(qo.getId())){//userid
			pagination=getByUserId(qo, pagination, start, end);
		}
		return pagination;
	}
	/**
	 * 根据用户id获得订单分页
	 * @param qo
	 * @param pagination
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Pagination getByUserId(HslTravelQO qo,Pagination pagination,int start,int end){
		List<TravelDTO> list=new ArrayList<TravelDTO>();
		int type=qo.getProjectType();
		HslMPOrderQO mpqo=new HslMPOrderQO();
		mpqo.setShowMember(true);
		mpqo.setUserId(qo.getId());//设置用户id
		mpqo.setStartTravelTime(qo.getStartTime());
		mpqo.setEndTravelTime(qo.getEndTime());
		mpqo.setShowMember(true);
		HslJPOrderQO jpqo=new HslJPOrderQO();
		jpqo.setShowMember(true);
		jpqo.setUserId(qo.getId());//设置用户id
		jpqo.setFlightStartTime(qo.getStartTime());//设为起飞时间
		jpqo.setFlightEndTime(qo.getEndTime());
		if(type==0){
			int mpcount=mpOrderLocalService.queryCount(mpqo);
			int jpcount=jpOrderlocalservice.queryCount(jpqo);
//			int size=qo.getPagesize()==0?10:qo.getPagesize();
			pagination.setTotalCount(mpcount+jpcount);
			pagination.setCondition(mpqo);
			if(mpcount>=end){
				//只在门票里查询
				Pagination p1=mpOrderLocalService.queryTravelDtoPagination(pagination);
				List<TravelDTO> dtolist=fillTravelDTO((List<TravelDTO>) p1.getList());//补全信息
				list.addAll(dtolist);
				pagination.setList(list);
			}else if(mpcount>=start&&mpcount<=end){
				//结果同时同时包含门票和机票
				Pagination p1=mpOrderLocalService.queryTravelDtoPagination(pagination);
				Pagination p2=new Pagination();
				p2.setCondition(jpqo);
				p2.setPageNo(1);
				p2.setPageSize(end-mpcount);
				p2=jpOrderlocalservice.queryTravelDtoPagination(p2);
				List<TravelDTO> mplist=fillTravelDTO((List<TravelDTO>) p1.getList());//补全信息
				List<TravelDTO> jplist=fillTravelDTO((List<TravelDTO>) p2.getList());
				list.addAll(jplist);
				list.addAll(mplist);
				pagination.setList(list);
			}else{
				//查询机票
				Pagination p2=new Pagination();
				p2.setCondition(jpqo);
				p2.setPageNo(1);
				p2.setPageSize(end-mpcount);
				p2=jpOrderlocalservice.queryTravelDtoPagination(p2);
				List<TravelDTO> orderlist=fillTravelDTO((List<TravelDTO>) p2.getList());
				int fromi=start-mpcount;
				int toi=(end-mpcount)>orderlist.size()?orderlist.size():(end-mpcount);
				if(fromi>=orderlist.size()||fromi>toi){
					return pagination;
				}
				List<TravelDTO> jplist=orderlist;
				pagination.setList(jplist.subList(start-mpcount, (end-mpcount)>jplist.size()?jplist.size():(end-mpcount)));
			}
		}else if(type==1){
			pagination.setCondition(mpqo);
			pagination=mpOrderLocalService.queryTravelDtoPagination(pagination);
			List<TravelDTO> dtolist=fillTravelDTO((List<TravelDTO>) pagination.getList());
			pagination.setList(dtolist);
		}else if(type==2){
			pagination.setCondition(jpqo);
			pagination=jpOrderlocalservice.queryTravelDtoPagination(pagination);
			List<TravelDTO> dtolist=fillTravelDTO((List<TravelDTO>) pagination.getList());
			pagination.setList(dtolist);
		}
		return pagination;
	}
	
	
	private int getCountById(int type,String beginTime,String endtime,String id ){
		//项目类型(0:全部;2:机票;1:门票;)
		if(type==0){
			return getCountById(1,beginTime,endtime,id)+getCountById(2,beginTime,endtime,id);
		}else if(type==2){
			HslJPOrderQO hslJPOrderQO=new HslJPOrderQO();
			hslJPOrderQO.setMemeberId(id);
			hslJPOrderQO.setFlightStartTime(beginTime);
			hslJPOrderQO.setFlightEndTime(endtime);
			return jpOrderlocalservice.queryCount(hslJPOrderQO);
		}else if(type==1){
			HslMPOrderQO mpOrderQO=new HslMPOrderQO();
			mpOrderQO.setMemberId(id);
			mpOrderQO.setStartTravelTime(beginTime);
			mpOrderQO.setEndTravelTime(endtime);
			return mpOrderLocalService.queryCount(mpOrderQO);
		}else 
			return 0;
	}
	
	/**
	 * 补全信息
	 * @param list
	 * @return
	 */
	public List<TravelDTO> fillTravelDTO(List<TravelDTO> list){
		if(list==null||list.isEmpty())
			return list;
		for(int i=0;i<list.size();i++){
			TravelDTO travelDTO=list.get(i);
			HslMemberQO qo=new HslMemberQO();
			qo.setId(travelDTO.getId());
			qo.setIsDel(null);//查找所有
			MemberDTO member=companyLocalService.getMember(qo);
			if(member==null){
				continue;
			}
			travelDTO.setJob(member.getJob());
			travelDTO.setMemberName(member.getName());
			if(StringUtils.isBlank(travelDTO.getMemberName()))
				travelDTO.setMemberName(member.getName());
		}
		return list;
	}


	@Override
	public Pagination getMemberPagination(Pagination p) {
		return companyLocalService.getMemberPagination(p);
	}
}
