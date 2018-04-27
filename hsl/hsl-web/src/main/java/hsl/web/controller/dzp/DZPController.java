package hsl.web.controller.dzp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import hg.common.component.CommonDao;
import hg.common.page.Pagination;
import hsl.app.service.local.dzp.order.DZPTicketOrderLocalService;
import hsl.app.service.local.dzp.policy.DZPTicketPolicyLocalService;
import hsl.app.service.local.dzp.scenicspot.DZPScenicSpotLocalService;
import hsl.app.service.local.user.TravelerLocalService;
import hsl.domain.model.dzp.meta.DZPCity;
import hsl.domain.model.dzp.meta.DZPProvince;
import hsl.domain.model.dzp.policy.DZPTicketPolicy;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpot;
import hsl.domain.model.user.traveler.Traveler;
import hsl.pojo.command.dzp.order.CreateDZPTicketOrderCommand;
import hsl.pojo.qo.dzp.policy.DZPTicketPolicyQO;
import hsl.pojo.qo.dzp.region.DZPCityQO;
import hsl.pojo.qo.dzp.region.DZPProvinceQO;
import hsl.pojo.qo.dzp.scenicspot.DZPScenicSpotQO;
import hsl.pojo.qo.dzp.scenicspot.DZPScenicSpotRQO;
import hsl.pojo.qo.user.TravelerQO;
import hsl.web.controller.BaseController;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 电子票
 *
 * @author zhurz
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/dzp")
public class DZPController extends BaseController {

	@Autowired
	private CommonDao commonDao;
	@Autowired
	private DZPScenicSpotLocalService dzpScenicSpotService;
	@Autowired
	private DZPTicketPolicyLocalService dzpTicketPolicyService;
	@Autowired
	private TravelerLocalService travelerService;
	@Autowired
	private DZPTicketOrderLocalService dzpOrderService;

	/**
	 * 电子票首页
	 *
	 * @param model 模型
	 * @param rqo   页面请求
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model, @ModelAttribute("rqo") DZPScenicSpotRQO rqo) {

		// 查询所有省
		List<DZPProvince> provinces = commonDao.forEntity(DZPProvince.class).autoQueryList(new DZPProvinceQO());
		// 关联的市
		List<DZPCity> cities = Collections.emptyList();

		// 查询关联的市
		if (StringUtils.isNotBlank(rqo.getProvinceId())) {
			DZPCityQO cityQO = new DZPCityQO();
			cityQO.setProviceId(rqo.getProvinceId());
			cities = commonDao.forEntity(DZPCity.class).autoQueryList(cityQO);
		}

		// 查询符合筛选条件的景区
		Pagination pagination = dzpScenicSpotService.queryPagination(rqo.buildQueryPagination());

		model.addAttribute("provinces", provinces);
		model.addAttribute("cities", cities);
		model.addAttribute("pagination", pagination);

		return "/dzp/index.html";
	}

	/**
	 * 景区详情
	 *
	 * @param model        模型
	 * @param scenicSpotId 景区ID
	 * @return
	 */
	@RequestMapping("/scenic-spot-detail/{id}")
	public String scenicSpotDetail(Model model, @PathVariable("id") String scenicSpotId) {
		DZPScenicSpotQO qo = new DZPScenicSpotQO();
		qo.setId(scenicSpotId);
		qo.setQueryTicketPolicy(true);
		qo.setPicsFetch(true);
		DZPScenicSpot scenicSpot = dzpScenicSpotService.queryUnique(qo);
		// 景区图片JSON
		String picsJson = JSON.toJSONString(scenicSpot.getPics(), new ValueFilter() {
			private String[] fields = {"name", "url"};

			@Override
			public Object process(Object object, String name, Object value) {
				if (ArrayUtils.contains(fields, name)) return value;
				return null;
			}
		});
		model.addAttribute("scenicSpot", scenicSpot);
		model.addAttribute("picsJson", picsJson);

		return "/dzp/scenic-spot-detail.html";
	}

	/**
	 * 订单编辑页面
	 *
	 * @param model          模型
	 * @param ticketPolicyId 门票政策ID
	 * @return
	 */
	@RequestMapping("/order-edit/{id}")
	public String orderEdit(Model model, @PathVariable("id") String ticketPolicyId,
							HttpServletRequest request) {

		// 查询门票政策
		DZPTicketPolicyQO qo = new DZPTicketPolicyQO();
		qo.setId(ticketPolicyId);
		qo.setClosed(false);
		qo.setFinished(false);
		qo.setPriceFetch(true);
		qo.setType(DZPTicketPolicy.TICKET_POLICY_TYPE_GROUP, DZPTicketPolicy.TICKET_POLICY_TYPE_SINGLE);
		DZPTicketPolicy policy = dzpTicketPolicyService.queryUnique(qo);

		// 价格日历
		String priceJson = JSON.toJSONString(policy.getPrice().values(), new ValueFilter() {
			private String[] fields = {"date", "price"};

			@Override
			public Object process(Object object, String name, Object value) {
				if (ArrayUtils.contains(fields, name)) return value;
				return null;
			}
		});

		// 常用联系人
		TravelerQO travelerQO = new TravelerQO();
		travelerQO.setFromUserId(super.getUserBySession(request).getId());
		List<Traveler> travelers = travelerService.queryList(travelerQO);

		model.addAttribute("policy", policy);
		model.addAttribute("priceJson", priceJson);
		model.addAttribute("travelers", travelers);

		return "/dzp/dzp-order-edit.html";
	}

	/**
	 * 订单创建并支付
	 *
	 * @param command 下单命令
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/order-create")
	public String createOrder(@RequestBody CreateDZPTicketOrderCommand command) {

		try {
			System.out.println("---------->>");
			return dzpOrderService.createOrder(command);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}


	/**
	 * 订单支付成功页面
	 *
	 * @return
	 */
	@RequestMapping("/order-pay-success")
	public String paySuccess(Model model,@RequestParam("orderNo") String orderNo) {





		return "/dzp/dzp-order-pay-success.html";
	}


}
