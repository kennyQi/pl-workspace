package hg.dzpw.app.pojo.vo;

import hg.dzpw.domain.model.scenicspot.ScenicSpot;

import java.io.Serializable;

/**
 * @类功能说明：商户管理员会话用户视图对象
 * @类修改者：
 * @修改日期：2015-2-11上午10:01:03
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-11上午10:01:03
 */
public class MerchantSessionUserVo implements Serializable {
	private static final long serialVersionUID = 1L;

	// ------------------ 管理员信息 ------------------

	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	/**
	 * 景区名称
	 */
	private String scenicSpotName;

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 景区管理员登录
	 */
	public MerchantSessionUserVo(ScenicSpot scenicSpot) {
		setScenicSpotId(scenicSpot.getId());
		setScenicSpotName(scenicSpot.getBaseInfo().getName());
		setLoginName(scenicSpot.getSuperAdmin().getAdminLoginName());
		setName(scenicSpot.getBaseInfo().getShortName());
	}

	public MerchantSessionUserVo() {
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
