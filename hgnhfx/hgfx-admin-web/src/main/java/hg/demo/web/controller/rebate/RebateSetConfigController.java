package hg.demo.web.controller.rebate;

import hg.demo.web.controller.BaseController;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.command.rebate.CreateRebateSetCommand;
import hg.fx.command.rebate.ModifyRebateSetCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.Product;
import hg.fx.domain.rebate.RebateInterval;
import hg.fx.domain.rebate.RebateSet;
import hg.fx.domain.rebate.RebateSetDto;
import hg.fx.domain.rebate.RenateQuJian;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.RebateIntervalSPI;
import hg.fx.spi.RebateSetSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.DistributorUserSQO;
import hg.fx.spi.qo.ProductSQO;
import hg.fx.spi.qo.RebateIntervalSQO;
import hg.fx.spi.qo.RebateSetSQO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/rebateSetConfig")
public class RebateSetConfigController extends BaseController {

	@Autowired
	private RebateSetSPI rebateSetService;
	@Autowired
	private ProductSPI productService;
	@Autowired
	private DistributorSPI distributorService;
	@Autowired
	private DistributorUserSPI distributorUserService;

	@Autowired
	private RebateIntervalSPI rebateIntervalSPIService;

	@RequestMapping("/index")
	public String getList(
			@ModelAttribute RebateSetSQO sqo,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "20") Integer pageSize,
			Model model) {
		// 获取商品
		List<Product> proList = productService
				.queryProductList(new ProductSQO());
		if (StringUtils.isBlank(sqo.getProductId())) {
			// 如果没有选择商品 默认选择第一个
			sqo.setProductId(proList.get(0).getId());
		}
		// 获取商户
		DistributorSQO distributorSQO = new DistributorSQO();
		distributorSQO.setStatus(1);
		distributorSQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_REBATE);
		List<Distributor> distriList = distributorService
				.queryList(distributorSQO);
		// 获取次月返利设置
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setPageNo(pageNum);
		limitQuery.setPageSize(pageSize);
		sqo.setLimit(limitQuery);
		Pagination<RebateSet> rebateList = rebateSetService
				.queryNextMonthSet(sqo);
		List<RebateSetDto> dtoList = new ArrayList<RebateSetDto>();
		// 将RebateSet->RebateSetDto
		for (RebateSet item : rebateList.getList()) {
			dtoList.add(new RebateSetDto(item));
		}
		// 查找默认返利区间
		RebateIntervalSQO rebateIntervalsqo = new RebateIntervalSQO();
		sqo.setIsImplement(true);
		sqo.setProductId(sqo.getProductId());
		RebateInterval rebateInterval = rebateIntervalSPIService
				.queryUnique(rebateIntervalsqo);
		model.addAttribute("qujian",
				RebateSetDto.getIntervalList(rebateInterval.getIntervalStr()));
		model.addAttribute("proList", proList);
		model.addAttribute("rebateSetSQO", sqo);
		model.addAttribute("distriList", distriList);
		model.addAttribute("pagination", rebateList);
		model.addAttribute("dtoList", dtoList);
		return "/rebate/nextMonthSetList.html";
	}

	@RequestMapping("/currMonthSetList")
	public String currMonthSetList(
			@ModelAttribute RebateSetSQO sqo,
			Model model,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "20") Integer pageSize) {
		// 获取商品
		List<Product> proList = productService
				.queryProductList(new ProductSQO());
		if (StringUtils.isBlank(sqo.getProductId())) {
			// 如果没有选择商品 默认选择第一个
			sqo.setProductId(proList.get(0).getId());
		}
		// 获取商户
		DistributorSQO distributorSQO = new DistributorSQO();
		distributorSQO.setStatus(1);
		distributorSQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_REBATE);
		List<Distributor> distriList = distributorService
				.queryList(distributorSQO);
		// 获取次月返利设置
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setPageNo(pageNum);
		limitQuery.setPageSize(pageSize);
		sqo.setLimit(limitQuery);
		Pagination<RebateSet> rebateList = rebateSetService
				.queryCurrMonthSet(sqo);
		List<RebateSetDto> dtoList = new ArrayList<RebateSetDto>();
		// 将RebateSet->RebateSetDto
		for (RebateSet item : rebateList.getList()) {
			dtoList.add(new RebateSetDto(item));
		}
		// 查找默认返利区间
		RebateIntervalSQO rebateIntervalsqo = new RebateIntervalSQO();
		sqo.setIsImplement(true);
		sqo.setProductId(sqo.getProductId());
		RebateInterval rebateInterval = rebateIntervalSPIService
				.queryUnique(rebateIntervalsqo);
		model.addAttribute("qujian",
				RebateSetDto.getIntervalList(rebateInterval.getIntervalStr()));
		model.addAttribute("proList", proList);
		model.addAttribute("rebateSetSQO", sqo);
		model.addAttribute("distriList", distriList);
		model.addAttribute("pagination", rebateList);
		model.addAttribute("dtoList", dtoList);
		return "/rebate/currMonthSetList.html";
	}

	@RequestMapping("/modifyMonthSet")
	public String modifyMonthSet(Model model,
			@RequestParam(value = "id", required = false) String id) {
		RebateSetSQO sqo = new RebateSetSQO();
		sqo.setId(id);
		RebateSet rebate = rebateSetService.queryUnique(sqo);
		RebateSetDto dto = new RebateSetDto(rebate);
		model.addAttribute("dto", dto);
		return "/rebate/modifyNextMonthSet.html";
	}

	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(Model model,
			@RequestParam(value = "id", required = false) String id,
			HttpServletRequest request) {
		// 获取原对象
		RebateSetSQO sqo = new RebateSetSQO();
		sqo.setId(id);
		RebateSet rebate = rebateSetService.queryUnique(sqo);
		RebateSetDto dto = new RebateSetDto(rebate);
		// 组装请求对象
		List<RenateQuJian> intervalList = new ArrayList<RenateQuJian>();
		for (RenateQuJian item : dto.getIntervalList()) {
			RenateQuJian qujian = new RenateQuJian();
			qujian.setQj(item.getQj());
			qujian.setZk((String) request.getParameter(item.getQj()));
			intervalList.add(qujian);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 检查合法性
		try {
			// 第一个区间折扣不能小于0
			double zk1 = Double.parseDouble(intervalList.get(0).getZk());
			if (zk1 < 0) {
				throw new RuntimeException("返利比例不合法");
			}
			// 之后的参数不能小于前一个折扣，不大于后一个参数
			for (int i = 1; i < intervalList.size() - 1; i++) {
				double zkBefore = Double.parseDouble(intervalList.get(i - 1)
						.getZk());
				double zk = Double.parseDouble(intervalList.get(i).getZk());
				double zkLatter = Double.parseDouble(intervalList.get(i + 1)
						.getZk());
				if (zk < zkBefore || zk > zkLatter) {
					throw new RuntimeException("返利比例不合法");
				}
			}
			double zkLast = Double.parseDouble(intervalList.get(
					intervalList.size() - 1).getZk());
			// 最后一个区间折扣不能大于1
			if (zkLast >= 1.0) {
				throw new RuntimeException("返利比例不合法");
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("statusCode", 300);
			map.put("callbackType", "closeCurrent");
			map.put("message", "折扣不合法");
			return map;
		}
		// 设置区间参数
		Map<String, String> intervalMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < intervalList.size(); i++) {
			intervalMap.put(intervalList.get(i).getQj(), intervalList.get(i)
					.getZk());
		}
		if (rebate.getIntervalStr()
				.equals(JSONObject.toJSONString(intervalMap))) {
			map.put("statusCode", 200);
			map.put("callbackType", "closeCurrent");
			map.put("message", "操作成功");
			return map;
		}
		// 创建命令
		CreateRebateSetCommand cmd = new CreateRebateSetCommand();
		cmd.setProductId(rebate.getProduct().getId());
		cmd.setDistributorId(rebate.getDistributor().getId());
		// 获取商户主帐号
		DistributorUserSQO disUserSqo = new DistributorUserSQO();
		disUserSqo.setDistributorId(rebate.getDistributor().getId());
		disUserSqo.setType(1);
		DistributorUser disUser = distributorUserService
				.queryUnique(disUserSqo);
		cmd.setDistributorUserId(disUser.getId());

		cmd.setCheckStatus(RebateSet.CHECK_STATUS_WAITTING);
		// 设置被修改的配置id
		cmd.setRunningSetId(rebate.getId());
		cmd.setApplyDate(new Date());
		// 设置申请人
		cmd.setApplyUser(getAuthUser(request.getSession()));
		cmd.setApplyUserName(getAuthUser(request.getSession()).getDisplayName());
		cmd.setIntervalStr(JSONObject.toJSONString(intervalMap));

		rebateSetService.createApplyRebateSet(cmd);
		map.put("statusCode", 200);
		map.put("callbackType", "closeCurrent");
		map.put("message", "保存成功");
		return map;
	}

	/**
	 * 修改当前生效规则
	 * 
	 * @author admin
	 * @since hgfx-admin-web
	 * @date 2016-7-26 下午7:02:01
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/modifyCurrMonthSet")
	public String modifyCurrMonthSet(Model model,
			@RequestParam(value = "id", required = false) String id) {
		RebateSetSQO sqo = new RebateSetSQO();
		sqo.setId(id);
		RebateSet rebate = rebateSetService.queryUnique(sqo);
		RebateSetDto dto = new RebateSetDto(rebate);
		model.addAttribute("dto", dto);
		return "/rebate/modifyCurrMonthSet.html";
	}

	/**
	 * 修改当前配置保存
	 * 
	 * @author admin
	 * @since hgfx-admin-web
	 * @date 2016-7-26 下午7:04:10
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveCurrSet")
	public Map<String, Object> saveCurrSet(Model model,
			@RequestParam(value = "id", required = false) String id,
			HttpServletRequest request) {
		// 获取原对象
		RebateSetSQO sqo = new RebateSetSQO();
		sqo.setId(id);
		RebateSet rebate = rebateSetService.queryUnique(sqo);
		RebateSetDto dto = new RebateSetDto(rebate);
		if (dto.getIsCanEdit() == null || dto.getIsCanEdit() == false) {
			throw new RuntimeException("超过可修改期限");
		}
		// 组装请求对象
		List<RenateQuJian> intervalList = new ArrayList<RenateQuJian>();
		for (RenateQuJian item : dto.getIntervalList()) {
			RenateQuJian qujian = new RenateQuJian();
			qujian.setQj(item.getQj());
			qujian.setZk((String) request.getParameter(item.getQj()));
			intervalList.add(qujian);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 检查合法性
		try {
			// 第一个区间折扣不能小于0
			double zk1 = Double.parseDouble(intervalList.get(0).getZk());
			if (zk1 < 0) {
				throw new RuntimeException("返利比例不合法");
			}
			// 之后的参数不能小于前一个折扣，不大于后一个参数
			for (int i = 1; i < intervalList.size() - 1; i++) {
				double zkBefore = Double.parseDouble(intervalList.get(i - 1)
						.getZk());
				double zk = Double.parseDouble(intervalList.get(i).getZk());
				double zkLatter = Double.parseDouble(intervalList.get(i + 1)
						.getZk());
				if (zk < zkBefore || zk > zkLatter) {
					throw new RuntimeException("返利比例不合法");
				}
			}
			double zkLast = Double.parseDouble(intervalList.get(
					intervalList.size() - 1).getZk());
			// 最后一个区间折扣不能大于1
			if (zkLast >= 1.0) {
				throw new RuntimeException("返利比例不合法");
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("statusCode", 300);
			map.put("callbackType", "closeCurrent");
			map.put("message", e.getMessage());
			return map;
		}
		// 设置区间参数
		Map<String, String> intervalMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < intervalList.size(); i++) {
			intervalMap.put(intervalList.get(i).getQj(), intervalList.get(i)
					.getZk());
		}
		if (rebate.getIntervalStr()
				.equals(JSONObject.toJSONString(intervalMap))) {
			map.put("statusCode", 200);
			map.put("callbackType", "closeCurrent");
			map.put("message", "操作成功");
			return map;
		}
		// 创建命令
		ModifyRebateSetCommand cmd = new ModifyRebateSetCommand();
		cmd.setIntervalStr(JSONObject.toJSONString(intervalMap));
		cmd.setIsDefault(false);
		rebateSetService.modifyDefaultRebateSet(cmd, rebate.getId());
		map.put("statusCode", 200);
		map.put("callbackType", "closeCurrent");
		map.put("message", "保存成功");
		return map;
	}
}
