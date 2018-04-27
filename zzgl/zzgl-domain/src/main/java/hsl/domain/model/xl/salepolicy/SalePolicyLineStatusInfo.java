package hsl.domain.model.xl.salepolicy;

import hsl.domain.model.M;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import plfx.xl.pojo.system.SalePolicyConstants;

/**
 * @类功能说明：政策状态
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月18日下午4:25:27
 * @版本：V1.0
 */
@Embeddable
public class SalePolicyLineStatusInfo {
	/**
	 * 政策状态
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer salePolicyLineStatus = 1;
	
	/**
	 * @方法功能说明：是否未发布
	 * @修改者名字：chenyanshou
	 * @修改时间：2014年12月24上午10:00:51
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean release() {
		if (SalePolicyConstants.DO_NOT_RELEASE == salePolicyLineStatus) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @方法功能说明：是否已取消
	 * @修改者名字：chenyanshou
	 * @修改时间：2014年12月24上午10:00:51
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean cancel() {
		if (SalePolicyConstants.SALE_CANCEL == salePolicyLineStatus) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @方法功能说明：是否已发布
	 * @修改者名字：chenyanshou
	 * @修改时间：2014年12月24上午10:00:51
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean success() {
		if (SalePolicyConstants.SALE_SUCCESS == salePolicyLineStatus) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @方法功能说明：是否已开始
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月12日上午10:00:51
	 * @修改内容：
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean start() {
		if (SalePolicyConstants.SALE_START == salePolicyLineStatus) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @方法功能说明：是否已结束
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月12日上午10:00:51
	 * @修改内容：
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean down() {
		if (SalePolicyConstants.SALE_DOWN == salePolicyLineStatus) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @方法功能说明：开始价格政策
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月4日下午1:33:50
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void startSalePolicy(){
		setSalePolicyLineStatus(SalePolicyConstants.SALE_START);
	}
	
	/**
	 * 
	 * @方法功能说明：下架价格政策
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月4日下午1:34:19
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void downSalePolicy(){
		setSalePolicyLineStatus(SalePolicyConstants.SALE_DOWN);
	}

	public Integer getSalePolicyLineStatus() {
		return salePolicyLineStatus;
	}
	public void setSalePolicyLineStatus(Integer salePolicyLineStatus) {
		this.salePolicyLineStatus = salePolicyLineStatus;
	}
}