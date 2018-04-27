package hg.dzpw.app.service.api.app;

import hg.common.component.RedisLock;
import hg.common.util.DateUtil;
import hg.common.util.JsonUtil;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.app.pojo.qo.UseRecordQo;
import hg.dzpw.app.service.local.AnnualMettingSmsService;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.SingleTicketLocalService;
import hg.dzpw.app.service.local.TouristLocalService;
import hg.dzpw.app.service.local.UseRecordLocalService;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.GroupTicketStatus;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.dzpw.domain.model.ticket.Ticket;
import hg.dzpw.domain.model.ticket.UseRecord;
import hg.dzpw.domain.model.tourist.Tourist;
import hg.dzpw.pojo.annotation.AppApiAction;
import hg.dzpw.pojo.api.appv1.base.ApiRequest;
import hg.dzpw.pojo.api.appv1.base.ApiResponse;
import hg.dzpw.pojo.api.appv1.dto.TicketDto;
import hg.dzpw.pojo.api.appv1.exception.DZPWAppApiException;
import hg.dzpw.pojo.api.appv1.request.LoginRequestBody;
import hg.dzpw.pojo.api.appv1.request.UseTicketRequestBody;
import hg.dzpw.pojo.api.appv1.request.ValiTicketByCerRequestBody;
import hg.dzpw.pojo.api.appv1.request.ValiTicketByTicketNoRequestBody;
import hg.dzpw.pojo.api.appv1.response.LoginResponse;
import hg.dzpw.pojo.api.appv1.response.UseTicketResponse;
import hg.dzpw.pojo.api.appv1.response.ValiTicketByCerResponse;
import hg.dzpw.pojo.api.appv1.response.ValiTicketByTicketNoResponse;
import hg.dzpw.pojo.command.platform.scenicspot.PlatformAdminLoginCommand;
import hg.dzpw.pojo.command.platform.tourist.PlatformModifyTouristCommand;
import hg.dzpw.pojo.command.platform.useticket.PlatformUseTicketCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.util.HgLogger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：APP API 服务
 * @类修改者：
 * @修改日期：2014-11-26下午3:06:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-26下午3:06:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppApiService {

	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	@Autowired
	private SingleTicketLocalService singleTicketLocalService;
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	@Autowired
	private UseRecordLocalService useRecordLocalService;
	@Autowired
	private TouristLocalService touristSer;
	//年会短信发送
	@Autowired
	private AnnualMettingSmsService annualMettingSmsService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

	/**
	 * @方法功能说明：根据景区密码查询景区
	 * @修改者名字：yangkang
	 * @修改时间：2015-7-8下午12:05:10
	 * @参数：@param secretKey
	 * @参数：@return
	 * @return:ScenicSpot
	 */
	public ScenicSpot queryScenicSpotBySecretKey(String secretKey) {
		if (StringUtils.isBlank(secretKey))
			return null;

		ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
		scenicSpotQo.setSecretKey(secretKey);
		ScenicSpot s = scenicSpotLocalService.queryUnique(scenicSpotQo);

		if (s != null)
			return s;

		return null;
	}

	/**
	 * @方法功能说明: 手动核销
	 * @param checkWay
	 * @param deviceId
	 * @param ticketNo
	 * @throws DZPWAppApiException
	 */
	public void manualCheckCertificate(String checkWay, String deviceId,
			String ticketNo) throws DZPWAppApiException {
		PlatformUseTicketCommand command = new PlatformUseTicketCommand();
		command.setCheckWay(checkWay);
		command.setDeviceId(deviceId);
		command.setTicketNo(ticketNo);
		// useRecordLocalService.useTicket(command);
	}

	/**
	 * @方法功能说明: 更新游客信息
	 * @param tourQo
	 * @param body
	 * @throws DZPWAppApiException
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws ImageException
	 */
	public void updateTourist(TouristQo tourQo, ValiTicketByCerRequestBody body)
			throws DZPWAppApiException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, IOException,
			URISyntaxException {
		if (StringUtils.isBlank(body.getName())
				&& StringUtils.isBlank(body.getGender())
				&& StringUtils.isBlank(body.getNation())
				&& StringUtils.isBlank(body.getAddress())
				&& StringUtils.isBlank(body.getBirthday())
				&& StringUtils.isBlank(body.getImageBase64Str()))
			return;

		Tourist tour = touristSer.queryUnique(tourQo);
		if (null == tour)
			throw new DZPWAppApiException(ValiTicketByCerResponse.RESULT_ERROR,
					"游客不存在或已删除！", null);

		// 更新游客信息
		PlatformModifyTouristCommand command = new PlatformModifyTouristCommand();
		command.setBuyAmountTotal(0.0);// 避免上次游客基本信息时，购买金额这边报空指针
		command.setTouristId(tour.getId());
		command.setName(body.getName());
		command.setGender(ValiTicketByCerRequestBody.GENDER_MAN.equals(body
				.getGender()) ? 0 : 1);
		command.setNation(body.getNation());
		command.setAddress(body.getAddress());
		if (null == tour.getBirthday())
			command.setBirthday(DateUtil.parseDate3(body.getBirthday()));
		// if(StringUtils.isBlank(tour.getImageUrl()))
		// command.setImageUrl(this.getImageURL(body.getImageBase64Str(),tour.getId(),tour.getName()));
		touristSer.modifyTourist(command);
	}

	/**
	 * @方法功能说明: 拼接景区名称
	 * @param group
	 * @return
	 */
	public String pingScenicName(GroupTicket group) {
		StringBuilder sb = new StringBuilder(3);
		for (SingleTicket ticket : group.getSingleTickets()) {
			sb.append("、" + ticket.getScenicSpot().getBaseInfo().getName());
		}

		// 联票下至少包含2个单票，所以不需要空字符判断
		if (sb.length() < 1)
			return sb.toString();
		return sb.substring(1);
	}

	/**
	 * @方法功能说明: 门票Dto的 factory方法
	 * @param groupTicket
	 * @return
	 */
	public TicketDto factoryTicketDto(GroupTicket groupTicket, String scenicId) {
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
		GroupTicketQo groupTicketQo = new GroupTicketQo();
		groupTicketQo.setTicketNo(groupTicket.getTicketNo());
		scenicSpotQo.setId(scenicId);
		// 根据景区和票号查单票
		singleTicketQo.setScenicSpotQo(scenicSpotQo);
		singleTicketQo.setGroupTicketQo(groupTicketQo);
		SingleTicket singleTicket = singleTicketLocalService
				.queryUnique(singleTicketQo);
		Tourist tourist = singleTicket.getTourist();// group.getTourist();
		if (tourist == null) {
			return null;
		}
		return new TicketDto(
				groupTicket.getTicketNo(),
				// 一般游客姓名不会发生前后不对应的情况
				tourist.getName(), tourist.getIdType() + "", tourist.getIdNo(),
				groupTicket.getTicketPolicySnapshot().getBaseInfo()
						.getName(),
						groupTicket.getTicketPolicySnapshot().getBaseInfo().getScenicSpotNameStr()
						, groupTicket.getType(),
				String.format("有效期：%s - %s",
						sdf.format(groupTicket.getUseDateStart()),
						sdf.format(groupTicket.getUseDateEnd())));
	}

	/**
	 * @方法功能说明：票号验票
	 * @修改者名字：guotx
	 * @修改时间：2014-11-26下午3:07:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:ValiTicketByTicketNoResponse
	 * @throws
	 */
	@AppApiAction("ValiTicketByTicketNo")
	public ValiTicketByTicketNoResponse valiTicketByTicketNo(
					ApiRequest<ValiTicketByTicketNoRequestBody> request) {

		// 条件判断：缺少票号
		if (request == null || request.getBody() == null
				|| request.getBody().getTicketNo() == null) {
			HgLogger.getInstance().debug("guotx", "景区验票-根据票号验票【缺少票号】");
			return ValiTicketByTicketNoResponse.error("缺少票号参数");
		}

		if (StringUtils.isBlank(request.getBody().getScenicSpotId())) {
			HgLogger.getInstance().debug("guotx", "景区验票-根据票号验票【缺少景区id】");
			return ValiTicketByTicketNoResponse.error("缺少景区ID参数");
		}

		// 校验票号
		if (!this.checkTicketNo(request.getBody().getTicketNo())) {
			HgLogger.getInstance().debug("guotx", String.format("景区验票-根据票号验票【非法票号%s】",request.getBody().getTicketNo()));
			return new ValiTicketByTicketNoResponse(
					ValiTicketByTicketNoResponse.RESULT_TICKET_NO_FOMART_ERROR,
					"非法票号");
		}

		ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
		scenicSpotQo.setId(request.getBody().getScenicSpotId());
		// 全部景区访问查询 防止卖票后 景区被禁用或逻辑删除
		scenicSpotQo.setRemoved(null);
		ScenicSpot scenicSpot = scenicSpotLocalService
				.queryUnique(scenicSpotQo);
		// 条件判断：景区不存在
		if (scenicSpot == null) {
			HgLogger.getInstance().debug("guotx", "景区验票-根据票号验票【景区不存在】");
			return ValiTicketByTicketNoResponse.error("景区不存在");
		}

		String ticketNo = request.getBody().getTicketNo();

		GroupTicketQo groupTicketQo = new GroupTicketQo();
		groupTicketQo.setTicketNo(ticketNo);
		groupTicketQo.setTicketNoLike(false);
		groupTicketQo.setStatus(GroupTicketStatus.GROUP_TICKET_CURRENT_OUT_SUCC);

		// 查询门票
		GroupTicket groupTicket = groupTicketLocalService
				.queryUnique(groupTicketQo);

		if (groupTicket == null){
			HgLogger.getInstance().debug("guotx", String.format("景区验票-根据票号验票【查询不到GroupTicket，票号%s】",ticketNo));
			return ValiTicketByTicketNoResponse.notFound();
		}
		if (groupTicket.getStatus().getCurrent() != GroupTicketStatus.GROUP_TICKET_CURRENT_OUT_SUCC){
			HgLogger.getInstance().debug("guotx", String.format("景区验票-根据票号验票【该票不可用GId=%s】",groupTicket.getId()));
			return ValiTicketByTicketNoResponse.notUseable();
		}
		// 查询门票下查询当前景区的SingleTicket
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		singleTicketQo.setGroupTicketQo(new GroupTicketQo());
		singleTicketQo.setScenicSpotQo(scenicSpotQo);
		singleTicketQo.getGroupTicketQo().setId(groupTicket.getId());
		singleTicketQo.setTourQo(new TouristQo());

		SingleTicket singleTicket = singleTicketLocalService
				.queryUnique(singleTicketQo);

		if (singleTicket == null){
			HgLogger.getInstance().debug("guotx", String.format("景区验票-根据票号验票【根据景区和票号查询不到单票记录】scenicId=[%s],groupTicketId=[%s]",
					scenicSpotQo.getId(),groupTicketQo.getId()));
			return ValiTicketByTicketNoResponse.notFound();
		}
		// singleTicket状态不在：待游玩、已游玩时
		if (!(singleTicket.getStatus().getCurrent() == SingleTicketStatus.SINGLE_TICKET_CURRENT_UNUSE
				|| singleTicket.getStatus().getCurrent() == SingleTicketStatus.SINGLE_TICKET_CURRENT_USED)){
			HgLogger.getInstance().debug("guotx", String.format("景区验票-根据票号验票【当前单票状态值为%s，不是待游玩和已游玩】",singleTicket.getStatus().getCurrent()));
			return ValiTicketByTicketNoResponse.notUseable();
		}
		if (singleTicket.getTourist() == null){
			HgLogger.getInstance().debug("guotx", String.format("景区验票-根据票号验票【单票尚未绑定游玩人,singleTicketId=%s】",singleTicket.getId()));
			return ValiTicketByTicketNoResponse.notUseable();
		}
		ValiTicketByTicketNoResponse response = new ValiTicketByTicketNoResponse();

		// 已游玩即非首次入园
		if (singleTicket.getStatus().getCurrent() == SingleTicketStatus.SINGLE_TICKET_CURRENT_USED) {
			// 非首次入园时候只判断singleTicket时间范围即可
			// 不在有效期
			if (!isTicketInExpDate(singleTicket)){
				HgLogger.getInstance().debug("guotx", String.format("景区验票-根据票号验票【非初次入园，且单票已过有效期，票号=%s】",singleTicket.getId()));
				return ValiTicketByTicketNoResponse.overDue(String.format(
						"有效期：%s - %s",
						sdf.format(singleTicket.getUseDateStart()),
						sdf.format(singleTicket.getUseDateEnd())));
			}
		} else if(singleTicket.getStatus().getCurrent() == SingleTicketStatus.SINGLE_TICKET_CURRENT_UNUSE){
			// 首次入园
			if (!isTicketInExpDate(groupTicket)){
				HgLogger.getInstance().debug("guotx", String.format("景区验票-根据票号验票【首次入园，但联票已过有效期，票号=%s】",groupTicket.getId()));
				return ValiTicketByTicketNoResponse.overDue(String.format(
						"有效期：%s - %s",
						sdf.format(groupTicket.getUseDateStart()),
						sdf.format(groupTicket.getUseDateEnd())));
			}
		}

		// 查询入园记录
		UseRecordQo urQo = new UseRecordQo();
		urQo.setGroupTicketId(groupTicket.getId());
		urQo.setSingleTicketId(singleTicket.getId());
		Date now = new Date();
		urQo.setUseDateStart(DateUtil.dateStr2BeginDate(DateUtil
				.formatDate(now)));
		urQo.setUseDateEnd(DateUtil.dateStr2EndDate(DateUtil.formatDate(now)));

		List<UseRecord> ul = useRecordLocalService.queryList(urQo);
		// 超过当天游玩次数限制
		if (ul != null
				&& singleTicket.getTicketPolicySnapshot().getUseInfo()
						.getValidTimesPerDay() <= ul.size()){
			HgLogger.getInstance().debug("guotx", String.format("景区验票-根据票号验票【超过当日可游玩次数已游玩%d次，可游玩%d次】",ul.size(),singleTicket.getTicketPolicySnapshot().getUseInfo().getValidTimesPerDay()));
			return ValiTicketByTicketNoResponse.checkInTimes();
		}
		TicketDto ticketDetail = new TicketDto();

		ticketDetail.setCerType(singleTicket.getTourist().getIdType()
				.toString());
		ticketDetail.setTouristName(singleTicket.getTourist().getName());
		ticketDetail.setIdNo(singleTicket.getTourist().getIdNo());
		// 包含景区
		ticketDetail.setRemark(groupTicket.getTicketPolicySnapshot()
				.getBaseInfo().getScenicSpotNameStr());
		ticketDetail.setTicketNo(ticketNo);
		ticketDetail.setTicketPolicyName(groupTicket.getTicketPolicySnapshot()
				.getBaseInfo().getName());

		// 门票类型 1单票 2联票
		ticketDetail
				.setType(groupTicket.getTicketPolicySnapshot() != null ? groupTicket
						.getTicketPolicySnapshot().getType() : groupTicket
						.getTicketPolicySnapshot().getType());

		ticketDetail.setResultMsg("["
				+ scenicSpot.getBaseInfo().getShortName() + "]游玩");

		ticketDetail.setExpDate(String.format("有效期：%s - %s",
				sdf.format(groupTicket.getUseDateStart()),
				sdf.format(groupTicket.getUseDateEnd())));

		response.setResult(ValiTicketByTicketNoResponse.RESULT_SUCCESS);
		response.setMessage("查询成功");
		response.setTicket(ticketDetail);
		HgLogger.getInstance().debug("guotx", String.format("景区验票-根据票号验票【验票成功】，返回结果【%s】",JsonUtil.parseObject(response, false)));
		return response;
	}

	/**
	 * 
	 * @描述： 根据校验位校验票号是否正确
	 * @author: guotx
	 * @version: 2015-12-17 下午5:27:14
	 */
	private boolean checkTicketNo(String ticketNo) {
		int ticketLength = ticketNo.length();
		if (ticketLength != 12) {
			return false;
		}
		char[] tickets = ticketNo.toCharArray();
		// 偶数和
		int evenSum = 0;
		// 奇数和
		int oddSum = 0;
		for (int i = 0; i < ticketLength - 1; i++) {
			if (i % 2 == 0) {
				oddSum += tickets[i] - 48;
			} else {
				evenSum += tickets[i] - 48;
			}
		}
		int verifyNum = (oddSum * 2 + evenSum) % 11;
		char verifyCode;
		if (verifyNum == 10)
			verifyCode = 'X';
		else
			verifyCode = (char) (verifyNum + 48);
		if (verifyCode == tickets[11]) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * @方法功能说明: 证件验票Body校验
	 * @param body
	 * @return
	 * @throws DZPWAppApiException
	 */
	public boolean ticketByCerValidate(ValiTicketByCerRequestBody body)
			throws DZPWAppApiException {
		// 必需参数
		if (!body.isCheckWay())
			throw new DZPWAppApiException("验票方式参数无效！");
		if (!body.isCerType())
			throw new DZPWAppApiException("证件类型参数无效！");
		if (StringUtils.isBlank(body.getIdNo()))
			throw new DZPWAppApiException("证件号参数无效！");
		// 非必需
		if (StringUtils.isNotBlank(body.getGender()) && !body.isGender())
			throw new DZPWAppApiException("性别参数无效！");
		if (StringUtils.isNotBlank(body.getBirthday())
				&& !DateUtil.checkDate(body.getBirthday(), null))
			throw new DZPWAppApiException("出生日期参数无效！");
		return false;
	}

	/**
	 * @方法功能说明：证件号验票
	 * @修改者名字：guotx
	 * @修改时间：2015-12-16 16:33:43
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:ValiTicketByCerResponse
	 * @throws
	 */
	@AppApiAction("ValiTicketByCer")
	public ValiTicketByCerResponse valiTicketByCer2(
					ApiRequest<ValiTicketByCerRequestBody> request) {

		ValiTicketByCerRequestBody body = request.getBody();
		ValiTicketByCerResponse response = new ValiTicketByCerResponse();

		try {
			this.ticketByCerValidate(body);

			String cerType = body.getCerType(); // 证件类型(1、身份证；2、军官证；3、驾驶证；4、护照)
			String idNo = body.getIdNo();

			if (StringUtils.isBlank(request.getBody().getScenicSpotId())) {
				throw new DZPWAppApiException("缺少景区ID");
			}

			ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
			scenicSpotQo.setId(request.getBody().getScenicSpotId());
			scenicSpotQo.setRemoved(null);
			ScenicSpot scenicSpot = scenicSpotLocalService
					.queryUnique(scenicSpotQo);
			// 条件判断：景区不存在
			if (scenicSpot == null) {
				HgLogger.getInstance().debug("guotx", "景区身份证验票【景区不存在】");
				throw new DZPWAppApiException("景区不存在");
			}

			// 游客Qo
			TouristQo tourQo = new TouristQo();
			tourQo.setIdNo(idNo);
			tourQo.setIdNoLike(false);
			tourQo.setIdType(Integer.valueOf(cerType));

			SingleTicketQo singleTicketQo = new SingleTicketQo();
			singleTicketQo.setTourQo(tourQo);
			singleTicketQo.setScenicSpotQo(scenicSpotQo);
			// 根据景区和游客查询套票
			GroupTicketQo groupTicketQo = new GroupTicketQo();
			groupTicketQo.setSingleTicketQo(singleTicketQo);
			groupTicketQo
					.setStatus(GroupTicketStatus.GROUP_TICKET_CURRENT_OUT_SUCC);
			List<GroupTicket> groupTickets = groupTicketLocalService
					.queryList(groupTicketQo);

			if (groupTickets.size() < 1) {
				response.setResult(ValiTicketByCerResponse.RESULT_NOT_FOUND);
				response.setMessage("查询不到购票记录");
				HgLogger.getInstance().debug("guotx", "身份证验票-查询不到购票记录-GroupTicket个数为0");
				return response;
			}

			List<TicketDto> dtoList = new ArrayList<TicketDto>();

			GroupTicketQo gtQo = new GroupTicketQo();

			/* 校验每张门票有效期 */
			for (GroupTicket groupTicket : groupTickets) {
				/* 单票、联票未到使用期 或 过期 */
//				if (!isTicketInExpDate(groupTicket)) {
//					continue;
//				}

				/* 查询联票下对应景区门票 */
				gtQo.setTicketNo(groupTicket.getTicketNo());
				singleTicketQo.setScenicSpotQo(scenicSpotQo);
				singleTicketQo.setGroupTicketQo(gtQo);
				singleTicketQo.setTourQo(tourQo);
				SingleTicket singleTicket = singleTicketLocalService
						.queryUnique(singleTicketQo);

				if (singleTicket == null) {
					continue;
				}
				TicketDto ticketDto = null;
				//单票是否可用
				boolean singleVailable=false;

				/*------未使用过且在有效期内即可-------*/
				if (singleTicket.getStatus().getCurrent()==SingleTicketStatus.SINGLE_TICKET_CURRENT_USED && isTicketInExpDate(singleTicket)) {
					UseRecordQo recordQo=new UseRecordQo();
					recordQo.setSingleTicketId(singleTicket.getId());
					Date now=new Date();
					Date currentDayBegin=DateUtil.dateStr2BeginDate(DateUtil.formatDate(now));
					Date currentDayEnd=DateUtil.dateStr2EndDate(DateUtil.formatDate(now));
					recordQo.setUseDateEnd(currentDayEnd);
					recordQo.setUseDateStart(currentDayBegin);
					List<UseRecord> recoredList=useRecordLocalService.queryList(recordQo);
					//超出当日可游玩次数
					if (recoredList != null
							&& singleTicket.getTicketPolicySnapshot().getUseInfo()
							.getValidTimesPerDay() <= recoredList.size() ) {
						if (groupTickets.size()==1) {
							HgLogger.getInstance().debug("guotx", String.format("超出当日可游玩次数【SingleTicketId=%s】",singleTicket.getId()));
							response.setResult(ValiTicketByCerResponse.RESULT_SCENIC_SPOT_CHECK_IN_TIMES);
							response.setMessage("超过景区当天入园次数限制");
							return response;
						}
						//仍然加到可选票列表，在核销时提示当日超限
						singleVailable=true;
					}else {
						singleVailable=true;
					}
					
				} else if(singleTicket.getStatus().getCurrent()==SingleTicketStatus.SINGLE_TICKET_CURRENT_UNUSE && isTicketInExpDate(groupTicket)){
					singleVailable=true;
				}
				if (singleVailable) {
					ticketDto = this.factoryTicketDto(groupTicket,
							scenicSpot.getId());
					ticketDto.setResultMsg("["
							+ scenicSpot.getBaseInfo().getShortName() + "]游玩");
					dtoList.add(ticketDto);
				}
			}

			response.setTickets(dtoList);
			response.setResult(ApiResponse.RESULT_SUCCESS);
			HgLogger.getInstance().debug("guotx", String.format("身份证验票返回结果【%s】",JsonUtil.parseObject(dtoList, true)));
			try {
				// 更新游客信息
				this.updateTourist(tourQo, body);
			} catch (Exception e) {
				e.printStackTrace();
				return response;
			}
			return response;
		} catch (DZPWAppApiException e) {
			e.printStackTrace();
			response.setResult(ApiResponse.RESULT_ERROR);
			return response;
		}
	}

	/**
	 * @方法功能说明：确认核销门票
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-27下午3:49:44
	 * @参数：@param req
	 * @参数：@return
	 * @return:UseTicketResponse
	 * @throws
	 */
	@AppApiAction("UseTicket")
	public UseTicketResponse useTicket(ApiRequest<UseTicketRequestBody> req) {

		UseTicketResponse resp = new UseTicketResponse();
		PlatformUseTicketCommand command = new PlatformUseTicketCommand();
		command.setCheckWay(req.getBody().getCheckWay());
		command.setScenicSpotId(req.getBody().getScenicSpotId());
		command.setTicketNo(req.getBody().getTicketNo());

		RedisLock lock = new RedisLock(command.getTicketNo());

		try {

			if (lock.tryLock()) {
				this.useRecordLocalService.useTicket(command);
				GroupTicketQo groupTicketQo=new GroupTicketQo();
				groupTicketQo.setTicketNo(req.getBody().getTicketNo());
				GroupTicket groupTicket=groupTicketLocalService.queryUnique(groupTicketQo);
				TicketPolicy policy=groupTicket.getTicketPolicy();
				if (policy.getBaseInfo().getName().equals("汇购科技2016年年会入场券")) {
					ScenicSpotQo scenicSpotQo=new ScenicSpotQo();
					scenicSpotQo.setId(policy.getScenicSpot().getId());
					SingleTicketQo singleTicketQo=new SingleTicketQo();
					singleTicketQo.setScenicSpotQo(scenicSpotQo);
					singleTicketQo.setGroupTicketQo(groupTicketQo);
					SingleTicket singleTicket=singleTicketLocalService.queryUnique(singleTicketQo);
					Tourist tourist=singleTicket.getTourist();
					//预订人手机
					String bookTele=tourist.getTelephone();
					String lottory=tourist.getNation();
					String tourisName=tourist.getName();
					HgLogger.getInstance().debug("guotx", String.format("核销后发送短息[手机号：%s][入园者：%s][抽奖码：%s]", bookTele,tourisName,lottory));
					annualMettingSmsService.sendSmsAfterUse(bookTele, tourisName, lottory);
				}
				lock.unlock();
				resp.setMessage("核销成功");
				resp.setResult("1");
			} else {
				resp.setMessage("正在处理...");
				resp.setResult("0");
			}
			return resp;
		} catch (DZPWAppApiException e) {
			e.printStackTrace();
			resp.setMessage(e.getMessage());
			resp.setResult(e.getCode());
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			resp.setMessage("系统异常");
			resp.setResult("0");
			return resp;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @方法功能说明：管理员登录
	 * @修改者名字：Caihuan
	 * @修改时间：2014-11-26下午3:07:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:LoginResponse
	 * @throws
	 */
	@AppApiAction("Login")
	public LoginResponse login(ApiRequest<LoginRequestBody> request) {

		LoginResponse response = new LoginResponse();
		PlatformAdminLoginCommand command = new PlatformAdminLoginCommand();

		command.setLoginName(request.getBody().getLoginName());
		command.setPassword(request.getBody().getPassword());


		// 景区简称
		String scenicSpotShortName = "";
		// 景区秘钥
		String secretKey = "";
		ScenicSpot scenicSpot2 = null;
		try {
			scenicSpot2 = scenicSpotLocalService.apiAdminLogin(command);
		} catch (DZPWException e) {
			response.setScenicSpotShortName("");
			response.setSecretKey("");
			response.setResult(e.getCode() + "");
			response.setMessage(e.getMessage());
			return response;
		}

		if (scenicSpot2 == null) {
			response.setScenicSpotShortName("");
			response.setSecretKey("");
			response.setResult(LoginResponse.RESULT_LOGIN_NAME_OR_PASSWORD_FAIL);
			response.setMessage("用户名密码有误");
			return response;
		}

		scenicSpotShortName = scenicSpot2.getBaseInfo().getShortName();
		secretKey = scenicSpot2.getSuperAdmin().getSecretKey();
		response.setScenicSpotId(scenicSpot2.getId());
		response.setScenicSpotShortName(scenicSpotShortName);
		response.setSecretKey(secretKey);
		response.setResult(ApiResponse.RESULT_SUCCESS);
		response.setMessage("登录成功");
		return response;
	}


	/**
	 * @方法功能说明：判断票是否在有效期
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-24上午11:14:08
	 * @参数：@param GroupTicket 或 SingleTicket
	 * @return:Boolean
	 */
	private Boolean isTicketInExpDate(Ticket ticket) {

		if (ticket == null)
			return null;

		GroupTicket groupTicket = null;
		SingleTicket singleTicket = null;
		Date now = new Date();

		if (ticket instanceof GroupTicket) {
			groupTicket = (GroupTicket) ticket;
		} else {
			singleTicket = (SingleTicket) ticket;
		}

		if (groupTicket != null) {
			// 未到有效期、已过期
			if (now.getTime() < groupTicket.getUseDateStart().getTime()
					|| now.getTime() > groupTicket.getUseDateEnd().getTime())
				return false;
		}

		if (singleTicket != null) {
			// 未到有效期、已过期
			if (now.getTime() < singleTicket.getUseDateStart().getTime()
					|| now.getTime() > singleTicket.getUseDateEnd().getTime())
				return false;
		}

		return true;
	}

}