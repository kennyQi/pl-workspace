package hg.demo.member.service.dao.mybatis;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author Caihuan
 * @date   2016年6月7日
 */
public interface DistributorMyBatisDao {

	public Integer getDistributorNum();
	
	/**
	 * 获取手机号的数量
	 * @author Caihuan
	 * @date   2016年6月8日
	 */
	public Integer getPhoneCount(@Param("phone")String phone);
}
