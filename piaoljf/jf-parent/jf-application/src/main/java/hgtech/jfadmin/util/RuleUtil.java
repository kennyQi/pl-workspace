/**
 * @文件名称：RuleUtil.java
 * @类路径：hgtech.jfadmin
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月23日下午4:15:52
 */
package hgtech.jfadmin.util;

import hgtech.jfcal.model.Rule;
import hgtech.jfcal.rulelogic.FindParameters;
import hgtech.jfcal.rulelogic.FindParameters.Parameters;
import hgtech.jfcal.rulelogic.demo.AmtRuleLogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月23日下午4:15:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月23日下午4:15:52
 *
 */
public class RuleUtil {
	public static String toStr(Map parameters){
		return JSONObject.toJSONString(parameters);
	}
	
	public static Map getParameters(String ruleParameters){
		return (Map) JSONObject.parseObject(ruleParameters, Map.class);
	}
	
	/**
	 * @param endDate 
	 * @param startDate 
	 * @param status 
	 * 
	 * @方法功能说明：在期限、且不作废
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月21日上午10:46:42
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public static boolean isValid(String status, Date startDate, Date endDate){
		long now=new Date().getTime();
		return (!Rule.STATUS_N.equalsIgnoreCase(status)) 
				&& (startDate.getTime()<=now && now<=endDate.getTime());
	}
	
	public static void main(String[] args) throws IOException {
//		Map p= new HashMap();
//		p.put("a","100");
//		p.put("b", "tom");
//		
//		String str = toStr(p);
//		System.out.println(str);
//		System.out.println(getParameters(str));
//		
//		List l  =new ArrayList();
//		FindParameters.Parameters pa=new Parameters();
//		pa.name="a";pa.remark="re";
//		l.add(pa);
//		 pa=new Parameters();
//		pa.name="ab";pa.remark="re";
//		l.add(pa);
//		System.out.println(JSONObject.toJSONString(l));
		
		
		System.out.println();
		Class<AmtRuleLogic> class1 = AmtRuleLogic.class;
		String file ="../jf-cal/calSrc/"+class1.getName().replace('.', '/')+".java";
		InputStream stream = new FileInputStream(new File(file));
		System.out.println("从文件 "+file +"中抽取的参数 :");
		LinkedList<Parameters> findParameters = FindParameters.findParameters(stream);
		for(FindParameters.Parameters p: findParameters)
			System.out.println("name:"+p.name+" remark:"+p.remark);
		System.out.println(JSONObject.toJSONString(findParameters));
		stream.close();
	}
}
