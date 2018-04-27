package slfx.mp.tcclient.tc.pojo;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.dto.jd.SceneryDto;
import slfx.mp.tcclient.tc.exception.DtoErrorException;

/**
 *	获取景点列表请求
 * @author zhangqy
 */
public class ParamSceneryList extends Param {
	/**
	 * 省份Id
	 */
	private String provinceId;
	/**
	 * 城市Id 根据城市Id查景点参数
	 */
	private String cityId;
	/**
	 * 行政区划（县）id
	 */
	private String countryId;
	/**
	 * 页数 不传默认为1
	 */
	private Integer page;
	/**
	 * 分页大小 不传默认为10，最大为100
	 */
	private String pageSize;
	/**
	 * 排序类型0-	点评由多到少
	 * 1-	景区级别
	 * 2-	同程推荐
	 * 3-	人气指数
	 */
	private String sortType;
	/**
	 *搜索关键词 用于模糊搜索
	 */
	private String keyword;
	/**
	 * 搜索字段当有keyword时必传入，多个用英文逗号隔开如：field1,field2 。有以下字段:LEGAL_REACH_STR
	 */
	private String searchFields;
	/**
	 * 星级Id 如1,2,3,4,5，可传多个，以英文逗号隔开
	 */
	private String gradeId;
	/**
	 * 主题Id 如1,2,3,4,5，可传多个，以英文逗号隔开
	 */
	private String themeId;
	/**
	 * 价格范围 如0,50，表示0到50
	 */
	private String priceRange;
	/**
	 * 坐标系统
	 */
	private String cs;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 半径 有经纬度时必传,单位:米
	 */
	private String radius;
	
	
	public ParamSceneryList() {
		super();
	}

	public void setParams(Param param1,Dto dto1) throws Exception{
		if(!(dto1 instanceof SceneryDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamSceneryList)){
			throw new DtoErrorException();
		}
		SceneryDto dto=(SceneryDto)dto1; 
		ParamSceneryList param=(ParamSceneryList)param1; 
		param.setCityId(dto.getCityId());
		param.setCountryId(dto.getCountryId());
		param.setCs(dto.getCs());
		param.setGradeId(dto.getGradeId());
		param.setKeyword(dto.getKeyword());
		param.setLatitude(dto.getLatitude());
		param.setLongitude(dto.getLongitude());
		param.setPage(dto.getPage());
		param.setPageSize(dto.getPageSize());
		param.setPriceRange(dto.getPriceRange());
		param.setProvinceId(dto.getProvinceId());
		param.setRadius(dto.getRadius());
		param.setSearchFields(dto.getSearchFields());
		param.setSortType(dto.getSortType());
		param.setThemeId(dto.getThemeId());
	}
	//================排序条件======================
	public static final Integer SORT_TYPE_COMMENT_NUM=0;//点评由多到少
	public static final Integer SORT_TYPE_AREA_LEVEL=1;//景区级别
	public static final Integer SORT_TYPE_TC_RECOMMEND=2;//同程推荐
	public static final Integer SORT_TYPE_PEOPLE_NUM=3;//人气指数
	//================坐标系统======================
	public static final Integer CS_MAPBAR=1;//mapbar
	public static final Integer CS_BAIDU=2;//百度
	public static final Integer CS_GOOGLE=3;//谷歌
	//================景区星级======================
	public static final Integer GRADE_ONE=1;//1星
	public static final Integer GRADE_TWO=2;//2星
	public static final Integer GRADE_THREE=3;//3星
	public static final Integer GRADE_FOUR=4;//4星
	public static final Integer GRADE_FIVE=5;//5星

	public static final String LEGAL_REACH_STR="city,name,nameAlias,namePYLC,nameTKPY,nameTKFPY,summary,themeName";//合法的模糊查询字段
	/**
	 * 获取合法的模糊查询
	 * 去掉重复，大小写忽略
	 * @return
	 */
	public String getAllLegalRearch(){
		String legalRearch=this.getSearchFields().toUpperCase();
		String legal=LEGAL_REACH_STR.toUpperCase();
		int sub=0;
		if(!legalRearch.isEmpty()){
			String[] fields=legalRearch.split(",");
			for(int i=0;i<fields.length;i++){
				if(!fields[i].isEmpty()){
					int j=legal.indexOf(fields[i]);
					if(j!=-1){
						legalRearch=legalRearch.replace(fields[i], legal.substring(j, j+fields[i].length()));
						legal=legal.replace(fields[i], "");
						sub=sub+fields[i].length();
					}else{
						//没有找到删点
						if(i+1<fields.length){
							legalRearch=legalRearch.substring(0,sub)+(legalRearch.substring(sub, legalRearch.length()).replace(fields[i]+",", ""));
						}else{
							legalRearch=legalRearch.substring(0,sub)+(legalRearch.substring(sub, legalRearch.length()).replace(fields[i], ""));
						}
					}
				}
			}
		}
		if(legalRearch.endsWith(",")){
			legalRearch=legalRearch.substring(0, legalRearch.length()-1);
		}
		//按同城的要求组合成对应的字符
		String[] legals=legalRearch.split(",");
		for(int i=0;i<legals.length;i++){
			int j=LEGAL_REACH_STR.toUpperCase().indexOf(legals[i]);
			legalRearch=legalRearch.replace(legals[i],LEGAL_REACH_STR.substring(j, j+(legals[i].length()) ));
		}
		return legalRearch;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getCs() {
		return cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}
	public static void main(String[] args){
		ParamSceneryList pl=new ParamSceneryList();
		System.out.println(pl.getClientIp());
		pl.setSearchFields("Name,cadsfad,name,naMe,CIty,sdf");
		System.out.println(pl.getAllLegalRearch());
	}
}
