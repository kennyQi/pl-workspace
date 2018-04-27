/**
 * @文件名称：TestJfadmin.java
 * @类路径：piaoljf
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月29日下午1:46:51
 */
package piaoljf;

import org.junit.Test;

import hgtech.jf.ClassUtil;
import hgtech.jf.piaol.SetupSpiApplicationContext;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月29日下午1:46:51
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月29日下午1:46:51
 *
 */
public class TestJfadmin {
	 
	@Test
	 public void testLoadClass() throws Exception{
	    SetupSpiApplicationContext.init();
	}
}
