package hsl.pojo.command.ad;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：修改广告状态（显示-隐藏）Command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2014年12月25日下午3:14:57
 * @版本：V1.0
 *
 */
public class HslUpdateAdStatusCommand extends BaseCommand{
	private static final long serialVersionUID = 1L;
	/**
	 * 广告的ID
	 */
	private String id;
	/**
	 * 按广告优先级和广告位的加载条数，自动维护是否显示状态
	 */
	private Boolean isShow;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getIsShow() {
		return isShow;
	}
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	
}
