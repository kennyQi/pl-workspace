/**
 * @文件名称：FindParameters.java
 * @类路径：hgtech.jfcal.rulelogic
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-4上午10:46:43
 */
package hgtech.jfcal.rulelogic;

import hgtech.jfcal.rulelogic.demo.AmtRuleLogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @类功能说明：根据关键字提取规则逻辑中的参数
 * @类修改者：
 * @修改日期：2014-9-4上午10:46:43
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-4上午10:46:43
 * 
 */
public class FindParameters {
	public static class Parameters {
		public String name, remark = "";

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return name + "|" + remark;
		}
	}

	/**
	 * 识别这种模式 String
	 * 不积分商户=rule.parameters.get("不积分商户");//@REMARK@每个商户号后跟一英文逗号，如pos01,pos02,
	 */
	public static String pattern1 = "rule\\.parameters\\.get\\(\"(.+)\"\\)(.+)";
	public static String pattern2 = "//\\s*@REMARK@(.*)";

	/**
	 * 识别这种模式 String
	 * 不积分商户=rule.parameters.get("不积分商户");//@REMARK@每个商户号后跟一英文逗号，如pos01,pos02,
	 * 
	 * @方法功能说明：抽取规则参数，模式参见 成员 patter1 patter2
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-15下午4:21:57
	 * @修改内容：
	 * @参数：@param stream
	 * @参数：@return
	 * @参数：@throws IOException
	 * @return:LinkedList<Parameters>
	 * @throws
	 */
	public static LinkedList<Parameters> findParameters(InputStream stream) {
		LinkedList<Parameters> parameters = new LinkedList<FindParameters.Parameters>();
		String ln = null;
		Pattern pa = Pattern.compile(pattern1);
		Pattern pa2 = Pattern.compile(pattern2);

		LineNumberReader r = null;
		try {
			r = new LineNumberReader(new InputStreamReader(stream, "utf-8"));
			while ((ln = r.readLine()) != null) {
				Matcher matcher = pa.matcher(ln);
				if (matcher.find()) {
					Parameters p = new Parameters();
					p.name = matcher.group(1);
					String comment = matcher.group(2);
					Matcher matcher2 = pa2.matcher(comment);
					if (matcher2.find()) {
						p.remark = matcher2.group(1);
					}
					parameters.add(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != r) {
					r.close();
				}
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return parameters;
	}

	public static void main(String[] args) throws IOException {

		String file = "src/main/java/"
				+ AmtRuleLogic.class.getName().replace('.', '/') + ".java";

		InputStream stream = new FileInputStream(new File(file));
		for (FindParameters.Parameters p : findParameters(stream))
			System.out.println(p);
		System.out.println(System.getenv());
		System.out.println();
		System.out.println(System.getProperties());
		//
		// String
		// ln="String 不积分商户=rule.parameters.get(\"不积分商户\");//@REMARK@每个商户号后跟一英文逗号，如pos01,pos02,";
		// ln="int 多少积1分 = Integer.parseInt( rule.parameters.get(\"多少积1分\"));";
		// Pattern pa=Pattern.compile(pattern1);
		// Pattern pa2 = Pattern.compile(pattern2);
		// Matcher matcher = pa.matcher(ln);
		// if(matcher.matches()){
		// Parameters p=new Parameters();
		// p.name=matcher.group(1);
		// String comment=matcher.group(2);
		// Matcher matcher2=pa2.matcher(comment);
		// if(matcher2.find())
		// { p.remark=matcher2.group(1);
		// }
		// System.out.println(p);
		// }

	}
}
