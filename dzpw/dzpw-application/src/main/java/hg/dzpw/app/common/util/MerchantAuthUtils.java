package hg.dzpw.app.common.util;

import org.apache.commons.lang.StringUtils;

import hg.dzpw.app.pojo.vo.MerchantSessionUserVo;
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
public class MerchantAuthUtils {

	private static ThreadLocal<MerchantSessionUserVo> sessionUser = new ThreadLocal<MerchantSessionUserVo>();

	/**
	 * @方法功能说明：设置当前线程的会话用户
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-4下午3:57:15
	 * @修改内容：
	 * @参数：@param vo
	 * @return:void
	 * @throws
	 */
	public static void setCurrentSessionUserVo(MerchantSessionUserVo vo) {
		sessionUser.set(vo);
	}
	
	/**
	 * @方法功能说明：获取当前线程的会话用户
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-4下午4:04:56
	 * @修改内容：
	 * @参数：@return
	 * @return:MerchantSessionUserVo
	 * @throws
	 */
	public static MerchantSessionUserVo getCurrentSessionUserVo() {
		return sessionUser.get();
	}

	/**
	 * @方法功能说明：检查是否来源于当前线程会话用户的景区
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-4下午3:57:28
	 * @修改内容：
	 * @参数：@param scenicSpotId
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	public static void checkFromScenicSpot(String scenicSpotId)
			throws DZPWException {

		if (sessionUser.get() == null
				|| !StringUtils.equals(sessionUser.get().getScenicSpotId(), scenicSpotId))
			
			throw new DZPWException(DZPWException.NO_AUTH, "无权操作");
	}
	
	public static void main(String[] args) throws DZPWException {
		
		MerchantSessionUserVo vo = new MerchantSessionUserVo();
		vo.setScenicSpotId("123");
		setCurrentSessionUserVo(vo);
		checkFromScenicSpot("123");

		// 新创建的线程无权限
		new Thread(new Runnable() {
			public void run() {
				try {
					checkFromScenicSpot("123");
				} catch (DZPWException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
