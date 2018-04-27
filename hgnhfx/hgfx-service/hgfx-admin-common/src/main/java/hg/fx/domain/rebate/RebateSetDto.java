package hg.fx.domain.rebate;

import hg.framework.common.base.BaseStringIdModel;
import hg.fx.util.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;


public class RebateSetDto extends BaseStringIdModel{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 商品名称
	 */
	private String productName;
	
	/**
	 * 返利关联的商户
	 */
	private String distributorName;
	
	/**
	 * 商户用户名
	 */
	private String loginName;
	
	private Integer checkStatus;
	
	private List<RenateQuJian> intervalList;
	
	private Boolean isCanEdit;
	
	/**
	 * 区间建值串
	 * */
	private String intervalStr;
	//{"100":"0.95","1000":"0.9","10000":"0.85","100000":"0.8"}
	public RebateSetDto(RebateSet entity){
		this.id=entity.getId();
		this.productName=entity.getProductName();
		this.distributorName = entity.getDistributorName();
		this.loginName = entity.getLoginName();
		this.checkStatus = entity.getCheckStatus();
		Map<String, String> map ;
		if(StringUtils.isNotBlank(entity.getIntervalStr())){
			map = (Map<String, String>)JSONObject.parse(entity.getIntervalStr());
		}else{
			map = new LinkedHashMap<String, String>();
		}
		intervalList = new ArrayList<>();
		for (String key : map.keySet()) {
			RenateQuJian qujian = new RenateQuJian();
			qujian.setQj(key);
			qujian.setZk(map.get(key));
			intervalList.add(qujian);
		}
		Collections.sort(intervalList, new Comparator<RenateQuJian>() {
			@Override
			public int compare(RenateQuJian o1, RenateQuJian o2) {
				return Integer.valueOf(o1.getQj()).compareTo(Integer.valueOf(o2.getQj()));
			}
		});
		if(entity.getIsDefault()!=null&&entity.getIsDefault()){
			if(DateUtil.formatDateTime1(entity.getImplementDate()).equals(DateUtil.forDateFirst()+" 00:00:00")){
				this.isCanEdit=true;
			}else{
				this.isCanEdit=false;
			}
		}
	}
	
	public static List<RenateQuJian> getIntervalList(String intervalMap ) {
		List<RenateQuJian> intervalList;
		Map<String, String> map ;
		if(StringUtils.isNotBlank(intervalMap)){
			map = (Map<String, String>)JSONObject.parse(intervalMap);
		}else{
			map = new LinkedHashMap<String, String>();
		}
		intervalList = new ArrayList<>();
		for (String key : map.keySet()) {
			RenateQuJian qujian = new RenateQuJian();
			qujian.setQj(key);
			qujian.setZk(map.get(key));
			intervalList.add(qujian);
		}
		Collections.sort(intervalList, new Comparator<RenateQuJian>() {
			@Override
			public int compare(RenateQuJian o1, RenateQuJian o2) {
				return Integer.valueOf(o1.getQj()).compareTo(Integer.valueOf(o2.getQj()));
			}
		});
		return intervalList;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List<RenateQuJian> getIntervalList() {
		return intervalList;
	}

	public void setIntervalList(List<RenateQuJian> intervalList) {
		this.intervalList = intervalList;
	}

	public String getIntervalStr() {
		return intervalStr;
	}

	public void setIntervalStr(String intervalStr) {
		this.intervalStr = intervalStr;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsCanEdit() {
		return isCanEdit;
	}

	public void setIsCanEdit(Boolean isCanEdit) {
		this.isCanEdit = isCanEdit;
	}
	
	
}
