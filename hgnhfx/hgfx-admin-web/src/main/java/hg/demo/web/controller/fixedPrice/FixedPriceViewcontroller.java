package hg.demo.web.controller.fixedPrice;


import hg.demo.member.common.domain.model.system.DwzJsonResultUtil;
import hg.demo.web.common.UserInfo;
import hg.demo.web.component.cache.FixedPriceSetManager;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.domain.Distributor;
import hg.fx.domain.Product;
import hg.fx.domain.fixedprice.FixedPriceInterval;
import hg.fx.domain.fixedprice.FixedPriceSet;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.FixedPriceIntervalSPI;
import hg.fx.spi.FixedPriceSetSPI;
import hg.fx.spi.MileOrderSPI;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.FixedPriceIntervalSQO;
import hg.fx.spi.qo.FixedPriceSetSQO;
import hg.fx.spi.qo.MileOrderSQO;
import hg.fx.spi.qo.ProductSQO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/fixedPrice")
public class FixedPriceViewcontroller {

	@Autowired
	private ProductSPI productSPI;
	@Autowired
	private DistributorSPI distributorSPI;
	@Autowired
	private DistributorUserSPI distributorUserSPI;
	@Autowired
	private FixedPriceSetSPI fixedPriceSetSPI;
	@Autowired
	private FixedPriceIntervalSPI fixedPriceIntervalSPI;
	@Autowired
	private MileOrderSPI mileOrderSPI;
	@Autowired
	private FixedPriceSetManager fixedPriceSetManager;

	/**
	 * 
	 * @方法功能说明：跳转到列表页
	 * @修改者名字：cangs
	 * @修改时间：2016年7月26日上午11:17:05
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param pageNum
	 * @参数：@param pageSize
	 * @参数：@param distributorID
	 * @参数：@param datetype
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/view")
	public String fixedPriceView(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
			@RequestParam(value = "numPerPage",defaultValue = "20")int pageSize,
			@RequestParam(value = "distributorID",defaultValue = "")String distributorID,
			@RequestParam(value = "datetype",defaultValue = "next")String datetype){
		//查询商品
		List<Product> products = productSPI.queryProductList(new ProductSQO());
		model.addAttribute("products",products);
		model.addAttribute("productID",products.get(0).getId());
		//查询商户  启用、定价模式
		DistributorSQO distributorSQO = new DistributorSQO();
		distributorSQO.setStatus(1);
		distributorSQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
		List<Distributor> distributors = distributorSPI.queryList(distributorSQO);
		model.addAttribute("distributors",distributors);
		//查询定价规则
		FixedPriceSetSQO fixedPriceSetSQO = new FixedPriceSetSQO();
		//设置分页
		LimitQuery limit = new LimitQuery();
		limit.setPageNo(pageNum);
		limit.setByPageNo(true);
		limit.setPageSize(pageSize);
		fixedPriceSetSQO.setLimit(limit);
		//设置商品
		fixedPriceSetSQO.setProductID(products.get(0).getId());
		//设置时间
		if(StringUtils.equals(datetype, "next")){
			//next 设置下月
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, 1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String nextMonth = simpleDateFormat.format(calendar.getTime());
			fixedPriceSetSQO.setImplementDate(Integer.parseInt(nextMonth));
		}else{
			//now 设置本月
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String thisMonth = simpleDateFormat.format(new Date());
			fixedPriceSetSQO.setImplementDate(Integer.parseInt(thisMonth));
		}
		//设置商户
		fixedPriceSetSQO.setDistributorID(distributorID);
		Pagination<FixedPriceSet> fixedPriceSets = fixedPriceSetSPI.queryPage(fixedPriceSetSQO);
		model.addAttribute("distributorID",distributorID);
		model.addAttribute("fixedPriceSets",fixedPriceSets.getList());
		model.addAttribute("pagination",fixedPriceSets);
		if(StringUtils.equals(datetype, "next")){
			return "fixedPrice/fixPrice.html";
		}else{
			return "fixedPrice/fixPriceNow.html";
		}
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到编辑页
	 * @修改者名字：cangs
	 * @修改时间：2016年7月26日上午11:17:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param fpsid
	 * @参数：@param pid
	 * @参数：@param did
	 * @参数：@param loginname
	 * @参数：@param datetype
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "fpsid",defaultValue = "")String fpsid,
			@RequestParam(value = "pid",defaultValue = "")String pid,
			@RequestParam(value = "did" ,defaultValue = "")String did,
			@RequestParam(value = "loginname" ,defaultValue = "")String loginname,
			@RequestParam(value = "datetype" ,defaultValue = "")String datetype){
		//获取区间值
		FixedPriceIntervalSQO fixedPriceIntervalSQO = new FixedPriceIntervalSQO();
		fixedPriceIntervalSQO.setProductID(pid);
		if(StringUtils.equals(datetype, "next")){
			//next 获取下月
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, 1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String nextMonth = simpleDateFormat.format(calendar.getTime());
			fixedPriceIntervalSQO.setCreateDate(Integer.parseInt(nextMonth));
		}else{
			//now 获取本月
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String thisMonth = simpleDateFormat.format(new Date());
			fixedPriceIntervalSQO.setCreateDate(Integer.parseInt(thisMonth));
		}
		FixedPriceInterval fixedPriceInterval = fixedPriceIntervalSPI.queryFixedPriceInterval(fixedPriceIntervalSQO);
		Map<String, String> map =  new HashMap<>();
		map = JSON.parseObject(fixedPriceInterval.getIntervalStr(), Map.class);
		List<QuJian> qujians= new ArrayList<>();
		for (String key : map.keySet()) {
			QuJian qujian = new QuJian();
			qujian.setQj(key);
			qujian.setZk(map.get(key));
			qujians.add(qujian);
		}
		Collections.sort(qujians, new Comparator<QuJian>() {
			@Override
			public int compare(QuJian o1, QuJian o2) {
				return Integer.valueOf(o1.getQj()).compareTo(Integer.valueOf(o2.getQj()));
			}
		});
		//区间放入页面
		model.addAttribute("qujians",qujians);
		//区间ID放入页面
		model.addAttribute("fixedPriceIntervalID",fixedPriceInterval.getId());
		//商品Id放入页面
		model.addAttribute("pid",pid);
		//商户Id放入页面
		model.addAttribute("did",did);
		//设置时间放入页面
		model.addAttribute("datetype",datetype);
		//定价规则Id放入页面 有就放 没有就不放
		model.addAttribute("fpsid",fpsid);
		//查询商户名 放入页面
		DistributorSQO distributorSQO = new DistributorSQO();
		distributorSQO.setId(did);
		distributorSQO.setStatus(1);
		distributorSQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
		distributorSQO.setQueryReserveInfo(false);
		Distributor distributor = distributorSPI.queryUnique(distributorSQO);
		model.addAttribute("name",distributor.getName());
		//查询登录名 放入页面
		model.addAttribute("loginname",loginname);
		return "fixedPrice/edit.html";
	}
	
	/**
	 * 
	 * @方法功能说明：保存规则操作
	 * @修改者名字：cangs
	 * @修改时间：2016年7月26日上午11:17:29
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param fpsid
	 * @参数：@param pid
	 * @参数：@param did
	 * @参数：@param loginname
	 * @参数：@param qujian
	 * @参数：@param zhekou
	 * @参数：@param datetype
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/save")
	public String save(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "fpsid",defaultValue = "")String fpsid,
			@RequestParam(value = "pid",defaultValue = "")String pid,
	        @RequestParam(value = "did" ,defaultValue = "")String did,
		    @RequestParam(value = "loginname" ,defaultValue = "")String loginname,
		    @RequestParam(value = "qujian" ,defaultValue = "")String qujian,
		    @RequestParam(value = "zhekou" ,defaultValue = "")String zhekou,
	        @RequestParam(value = "datetype" ,defaultValue = "")String datetype,
	        @RequestParam(value = "fixedPriceIntervalID" ,defaultValue = "")String fixedPriceIntervalID){
		//核心校验
		//如果当前月该商品商户已经下单则不可修改
		if(StringUtils.equals(datetype, "now")){
			MileOrderSQO mileOrderSQO = new MileOrderSQO();
			mileOrderSQO.setDistributorId(did);
			mileOrderSQO.setProductId(pid);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
			String startdate = simpleDateFormat.format(new Date())+"-01";
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String enddate = simpleDateFormat.format(new Date());
			mileOrderSQO.setStrImportDate(startdate);
			mileOrderSQO.setEndImportDate(enddate);
			if(mileOrderSPI.queryList(mileOrderSQO).size()!=0){
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败，当前月份该商户已有订单生成",	"closeCurrent", "dingjia");
			}
		}
		FixedPriceSet fixedPriceSet = new FixedPriceSet();
		if(StringUtils.isNotBlank(fpsid)){
			//数据已经存在 修改操作
			FixedPriceSet oldFixedPriceSet = fixedPriceSetSPI.queryByID(fpsid);
			fixedPriceSet.setCurrentInterval(oldFixedPriceSet.getModifiedInterval());
			fixedPriceSet.setCurrentPercent(oldFixedPriceSet.getModifiedPercent());
		}
		//查询商品
		ProductSQO sqo = new ProductSQO();
		sqo.setProductID(pid);
		Product product = productSPI.queryProductByID(sqo);
		fixedPriceSet.setProduct(product);
		//查询商户
		DistributorSQO distributorSQO = new DistributorSQO();
		distributorSQO.setId(did);
		distributorSQO.setStatus(1);
		distributorSQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
		distributorSQO.setQueryReserveInfo(false);
		Distributor distributor = distributorSPI.queryUnique(distributorSQO);
		fixedPriceSet.setDistributor(distributor);
		//登录名
		fixedPriceSet.setLoginName(loginname);
		//区间ID
		fixedPriceSet.setFixedPriceIntervalID(fixedPriceIntervalID);
		//区间
		fixedPriceSet.setModifiedInterval(qujian);
		//折扣
		fixedPriceSet.setModifiedPercent(Double.parseDouble(zhekou));
		//设定生效时间
		if(StringUtils.equals(datetype, "next")){
			//next 为设置下月
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, 1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String nextMonth = simpleDateFormat.format(calendar.getTime());
			fixedPriceSet.setImplementYYYYMM(Integer.parseInt(nextMonth));
			simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
			nextMonth = nextMonth+"01 000000";
			try {
				fixedPriceSet.setImplementDate(simpleDateFormat.parse(nextMonth));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			//now 为设置本月
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String thisMonth = simpleDateFormat.format(new Date());
			fixedPriceSet.setImplementYYYYMM(Integer.parseInt(thisMonth));
			fixedPriceSet.setImplementDate(new Date());
		}
		//申请人 先 获取当前登录人
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("_SESSION_USER_");
		fixedPriceSetSPI.saveFixedPriceSet(fixedPriceSet,userInfo.getUserId(),datetype);
		if(StringUtils.equals(datetype, "now")){
			fixedPriceSetManager.updateFixedPriceSet(pid, did, zhekou);
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",	"closeCurrent", "dingjia");
	}
	
	/**
	 * 
	 * @方法功能说明：跳转展示区间
	 * @修改者名字：cangs
	 * @修改时间：2016年7月26日上午11:17:40
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param pid
	 * @参数：@param datetype
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/getFixedPriceInterval")
	public String getFixedPriceInterval(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "pid",defaultValue = "")String pid,
	        @RequestParam(value = "datetype" ,defaultValue = "")String datetype){
		FixedPriceIntervalSQO fixedPriceIntervalSQO = new FixedPriceIntervalSQO();
		fixedPriceIntervalSQO.setProductID(pid);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String thisMonth = simpleDateFormat.format(new Date());
		fixedPriceIntervalSQO.setCreateDate(Integer.parseInt(thisMonth));
		FixedPriceInterval fixedPriceInterval = fixedPriceIntervalSPI.queryFixedPriceInterval(fixedPriceIntervalSQO);
		Map<String, String> map =  new HashMap<>();
		map = JSON.parseObject(fixedPriceInterval.getIntervalStr(), Map.class);
		List<QuJian> qujians= new ArrayList<>();
		for (String key : map.keySet()) {
			QuJian qujian = new QuJian();
			qujian.setQj(key);
			qujian.setZk(map.get(key));
			qujians.add(qujian);
		}
		Collections.sort(qujians, new Comparator<QuJian>() {
			@Override
			public int compare(QuJian o1, QuJian o2) {
				return Integer.valueOf(o1.getQj()).compareTo(Integer.valueOf(o2.getQj()));
			}
		});
		//区间放入页面
		model.addAttribute("qujians",qujians);
		return "fixedPrice/getFixedPriceInterval.html";
	}
	
	@RequestMapping("/editFixedPriceInterval")
	public String editFixedPriceInterval(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "pid",defaultValue = "")String pid,
	        @RequestParam(value = "datetype" ,defaultValue = "")String datetype){
		FixedPriceIntervalSQO fixedPriceIntervalSQO = new FixedPriceIntervalSQO();
		fixedPriceIntervalSQO.setProductID(pid);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String thisMonth = simpleDateFormat.format(new Date());
		//next 为设置下月
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(simpleDateFormat.parse(thisMonth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, 1);
		String nextMonth = simpleDateFormat.format(calendar.getTime());
		fixedPriceIntervalSQO.setCreateDate(Integer.parseInt(nextMonth));
		FixedPriceInterval fixedPriceInterval = fixedPriceIntervalSPI.queryFixedPriceInterval(fixedPriceIntervalSQO);
		Map<String, String> map =  new HashMap<>();
		map = JSON.parseObject(fixedPriceInterval.getIntervalStr(), Map.class);
		List<QuJian> qujians= new ArrayList<>();
		for (String key : map.keySet()) {
			QuJian qujian = new QuJian();
			qujian.setQj(key);
			qujian.setZk(map.get(key));
			qujians.add(qujian);
		}
		Collections.sort(qujians, new Comparator<QuJian>() {
			@Override
			public int compare(QuJian o1, QuJian o2) {
				return Integer.valueOf(o1.getQj()).compareTo(Integer.valueOf(o2.getQj()));
			}
		});
		//区间放入页面
		model.addAttribute("qujians",qujians);
		//区间ID放入页面
		model.addAttribute("fixedPriceIntervalID",fixedPriceInterval.getId());
		return "fixedPrice/editFixedPriceInterval.html";
	}
	
	@ResponseBody
	@RequestMapping("/saveFixedPriceInterval")
	public String saveFixedPriceInterval(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "pid",defaultValue = "")String pid,
	        @RequestParam(value = "datetype" ,defaultValue = "")String datetype,
	        @RequestParam(value = "qj" ,defaultValue = "")String qj,
	        @RequestParam(value = "zk" ,defaultValue = "")String zk,
	        @RequestParam(value = "fixedPriceIntervalID" ,defaultValue = "")String fixedPriceIntervalID){
		String[] qjs = qj.split(",");
		String[] zks = zk.split(",");
		Map<String, String> map =  new HashMap<>();
		for(int i=0;i<qjs.length;i++){
			try{
				double d = Double.parseDouble(zks[i]);
				if(d<=0||d>1){
					throw new Exception();
				}else if(zks[i].split("\\.")[1].length()>2){
					throw new Exception();
				}
			
				map.put(qjs[i], zks[i]);
			}catch(Exception e){
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "请填写正确折扣",	"", "dingjia");
			}
		}
		FixedPriceInterval fixedPriceInterval =new FixedPriceInterval();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String nextMonth = simpleDateFormat.format(calendar.getTime());
		int theNextMonth = Integer.parseInt(simpleDateFormat.format(calendar.getTime()));
		fixedPriceInterval.setCreateDateYYYYMM(Integer.parseInt(nextMonth));
		simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
		nextMonth = nextMonth+"01 000000";
		try {
			fixedPriceInterval.setCreateDate(simpleDateFormat.parse(nextMonth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fixedPriceInterval.setIntervalStr(JSON.toJSONString(map));
		//查询商品
		ProductSQO sqo = new ProductSQO();
		sqo.setProductID(pid);
		Product product = productSPI.queryProductByID(sqo);
		fixedPriceInterval.setProduct(product);
		//申请人 先 获取当前登录人
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("_SESSION_USER_");
		fixedPriceIntervalSPI.createFixedPriceInterval(fixedPriceInterval,userInfo.getUserId());
		fixedPriceSetSPI.setFixedPriceIntervalID(fixedPriceIntervalID,theNextMonth);
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",	"closeCurrent", "dingjia");
	}
}