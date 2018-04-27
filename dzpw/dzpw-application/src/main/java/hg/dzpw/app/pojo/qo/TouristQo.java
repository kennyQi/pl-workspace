package hg.dzpw.app.pojo.qo;

import java.util.Date;
import hg.common.component.BaseQo;

/**
 * @类功能说明：游客Qo
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-10下午3:25:01
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class TouristQo extends BaseQo{
	/**
	 * 姓名
	 * */
	private String name;
	
	/**
	 * 姓名模糊查询
	 */
	private Boolean nameLike = false;
	
	/**
	 * 证件类型
	 * */
	private Integer idType;
	
	/**
	 * 证件号
	 * */
	private String idNo;
	
	/**
	 * 证件号模糊查询
	 */
	private Boolean idNoLike = false;
	
	/**
	 * 性别
	 * */
	private Integer gender;
	
	/**
	 * 手机号码
	 */
	private String telephone;
	
	/**
	 * 年龄查询开始条件
	 */
	private Integer ageStart;
	
	/**
	 * 年龄查询结束条件
	 */
	private Integer ageEnd;
	
	/**
	 * 首次购买时间查询开始条件
	 */
	private Date firstBuyDateStart;
	
	/**
	 * 首次购买时间查询结束条件
	 */
	private Date firstBuyDateEnd;
	/**
	 * 首次购买时间排序 >0 asc, <0 desc
	 */
	private Integer firstBuyDateSort;
	private Integer pageSize;
	private Integer pageNum;
	/**
	 * 页面首次购买时间查询开始条件
	 */
	private String firstBuyDateStartStr;
	/**
	 * 页面首次购买时间查询结束条件
	 */	
	private String firstBuyDateEndStr;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public Boolean getNameLike() {
		return nameLike;
	}
	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}
	public Integer getIdType() {
		return idType;
	}
	public void setIdType(Integer idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo == null ? null : idNo.trim();
	}
	public Boolean getIdNoLike() {
		return idNoLike;
	}
	public void setIdNoLike(Boolean idNoLike) {
		this.idNoLike = idNoLike;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getAgeStart() {
		return ageStart;
	}
	public void setAgeStart(Integer ageStart) {
		this.ageStart = ageStart;
	}
	public Integer getAgeEnd() {
		return ageEnd;
	}
	public void setAgeEnd(Integer ageEnd) {
		this.ageEnd = ageEnd;
	}
	public Date getFirstBuyDateStart() {
		return firstBuyDateStart;
	}
	public void setFirstBuyDateStart(Date firstBuyDateStart) {
		this.firstBuyDateStart = firstBuyDateStart;
	}
	public Date getFirstBuyDateEnd() {
		return firstBuyDateEnd;
	}
	public void setFirstBuyDateEnd(Date firstBuyDateEnd) {
		this.firstBuyDateEnd = firstBuyDateEnd;
	}
	public Integer getFirstBuyDateSort() {
		return firstBuyDateSort;
	}
	public void setFirstBuyDateSort(Integer firstBuyDateSort) {
		this.firstBuyDateSort = firstBuyDateSort;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public String getFirstBuyDateStartStr() {
		return firstBuyDateStartStr;
	}
	public void setFirstBuyDateStartStr(String firstBuyDateStartStr) {
		this.firstBuyDateStartStr = firstBuyDateStartStr == null ? null : firstBuyDateStartStr.trim();
	}
	public String getFirstBuyDateEndStr() {
		return firstBuyDateEndStr;
	}
	public void setFirstBuyDateEndStr(String firstBuyDateEndStr) {
		this.firstBuyDateEndStr = firstBuyDateEndStr == null ? null : firstBuyDateEndStr.trim();
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}