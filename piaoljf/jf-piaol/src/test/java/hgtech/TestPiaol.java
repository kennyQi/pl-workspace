/**
 * @文件名称：TestCommon.java
 * @类路径：hgtech
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月8日上午10:25:15
 */
package hgtech;

import hgtech.jf.piaol.SetupApplicationContextDemo票量网;
import hgtech.jfaccount.JfAccountTypeUK;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月8日上午10:25:15
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月8日上午10:25:15
 *
 */
public class TestPiaol {

	@Test
	public void testEntityHome(){
		Map m=new HashMap();
		m.put(new JfAccountTypeUK(SetupApplicationContextDemo票量网.sysDomain, SetupApplicationContextDemo票量网.ct成长), "dsfs");
		m.put(new JfAccountTypeUK(SetupApplicationContextDemo票量网.sysDomain, SetupApplicationContextDemo票量网.ct成长), "dsfs");
		m.put(new JfAccountTypeUK(SetupApplicationContextDemo票量网.sysDomain, SetupApplicationContextDemo票量网.ct成长), "dsfs");
		System.out.println(m.size());
	}
}
