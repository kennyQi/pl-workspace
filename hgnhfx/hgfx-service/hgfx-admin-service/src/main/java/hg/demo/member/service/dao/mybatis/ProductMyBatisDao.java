package hg.demo.member.service.dao.mybatis;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

public interface ProductMyBatisDao {

	/**
	 * 规定时间内消费里程额度
	 * @author Caihuan
	 * @date   2016年6月3日
	 */
	public Long sumReserveInfo(@Param("distributorId")String distributorId,@Param("beginDate")Date beginDate,@Param("endDate")Date endDate);
}
