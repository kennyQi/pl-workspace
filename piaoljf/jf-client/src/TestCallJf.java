import hg.common.page.Pagination;

import java.net.URL;
import java.net.URLConnection;


import com.alibaba.fastjson.JSON;

/**
 * @文件名称：TestCallJf.java
 * @类路径：
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年11月6日下午3:27:29
 */

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年11月6日下午3:27:29
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月6日下午3:27:29
 *
 */
public class TestCallJf {

	public void testqueryjf() throws Exception{
		String url="http://localhost:8080/jf-admin/service/queryjf?pageNum=1&numPerPage=20&code=xiaoying";
		URL netUrl=new URL(url);
		URLConnection con = netUrl.openConnection();
		byte[] b=new byte[1024000];
		int len=con.getInputStream().read(b);
		String s=new String(b,0,len,"utf-8");
		Pagination obj = JSON.parseObject(s,Pagination.class);
		System.out.println(obj);
	}
}
