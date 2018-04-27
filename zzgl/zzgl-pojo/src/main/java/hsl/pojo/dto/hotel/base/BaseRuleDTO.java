package hsl.pojo.dto.hotel.base;
import java.io.Serializable;
/**
 * @类功能说明：规则基础类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年3月12日上午10:26:37
 * @版本：V1.0
 *
 */
public class BaseRuleDTO  implements Serializable{
	private static final long serialVersionUID = 721467584383797590L;
	/**
	 * 描述
	 */
    protected String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String value) {
        this.description = value;
    }

}
