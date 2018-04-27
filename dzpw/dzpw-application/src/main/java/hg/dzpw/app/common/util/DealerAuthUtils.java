package hg.dzpw.app.common.util;

import org.apache.commons.lang.StringUtils;

import hg.dzpw.app.pojo.vo.DealerSessionUserVo;
import hg.dzpw.pojo.exception.DZPWException;

/**
 * @类功能说明：商户端权限工具
 * @类修改者：
 * @修改日期：2015-3-4下午3:56:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-4下午3:56:52
 */
public class DealerAuthUtils {

	private static ThreadLocal<DealerSessionUserVo> sessionUser = new ThreadLocal<DealerSessionUserVo>();

	/**
	 * @方法功能说明：设置当前线程的会话用户
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-4下午3:57:15
	 * @修改内容：
	 * @参数：@param vo
	 * @return:void
	 * @throws
	 */
	public static void setCurrentSessionUserVo(DealerSessionUserVo vo) {
		sessionUser.set(vo);
	}
	
	/**
	 * @方法功能说明：获取当前线程的会话用户
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-4下午4:04:56
	 * @修改内容：
	 * @参数：@return
	 * @return:DealerSessionUserVo
	 * @throws
	 */
	public static DealerSessionUserVo getCurrentSessionUserVo() {
		return sessionUser.get();
	}

	/**
	 * @方法功能说明：检查是否来源于当前线程会话用户的经销商
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-4下午3:57:28
	 * @修改内容：
	 * @参数：@param scenicSpotId
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	public static void checkFromDealer(String dealerId)
			throws DZPWException {

		if (sessionUser.get() == null
				|| !StringUtils.equals(sessionUser.get().getDealerId(), dealerId))
			
			throw new DZPWException(DZPWException.NO_AUTH, "无权操作");
	}
	

}
