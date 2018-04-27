/**
 * @文件名称：TestCal.java
 * @类路径：hgtech.jfcal.rulelogic
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月29日下午1:37:49
 */
package hgtech.jfcal.rulelogic;

import hgtech.jf.ClassUtil;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月29日下午1:37:49
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月29日下午1:37:49
 *
 */
public class TestCal {
	public static void main(String[] args) throws Exception {
		testLoadClass();
	}
	
	static void testLoadClass() throws Exception{
		String path="..\\jf-admin\\jfPath\\template";
		String classn="hgtech.jf.piaol.rulelogic.PiaolSignLogic";
//		hgtech.jf.piaol.rulelogic.PiaolSignLogic r;
		System.out.println(ClassUtil.loadFileClass(path, classn));
	}
}
