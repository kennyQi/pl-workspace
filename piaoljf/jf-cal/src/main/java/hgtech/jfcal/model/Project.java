package hgtech.jfcal.model;
import java.util.LinkedList;

/**
 * 
 * @类功能说明：积分活动，一般活动包含若干个相关的规则
 * @类修改者：
 * @修改日期：2014年11月4日上午11:11:45
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月4日上午11:11:45
 *
 */
public class Project {
	String name,id;
	public LinkedList<Rule> ruleSet=new LinkedList<Rule>();
}
