package slfx.yg.open.utils;

import hg.log.util.HgLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import slfx.jp.qo.client.PolicyByPnrQo;

/**
 * 
 * @类功能说明：PNR工具类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:19:36
 * @版本：V1.0
 *
 */
public class PnrUtil {
	
	public static Map<Integer,String> weekMap = new HashMap<Integer,String>();
	public static Map<Integer,String> monthMap = new HashMap<Integer,String>();
	
	static {
		//Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday|Mo,Tu,We,Th,Fr,Sa,Su
		weekMap.put(1, "SU");	//周日
		weekMap.put(2, "MO");	//周一
		weekMap.put(3, "TU");	//周二
		weekMap.put(4, "WE");	//周三
		weekMap.put(5, "TH");	//周四
		weekMap.put(6, "FR");	//周五
		weekMap.put(7, "SA");	//周六
	
		//1,2,3,4,5,6,7,8,9,10,11,12|Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
		monthMap.put(0, "JAN");
		monthMap.put(1, "FEB");
		monthMap.put(2, "MAR");
		monthMap.put(3, "APR");
		monthMap.put(4, "MAY");
		monthMap.put(5, "JUN");
		monthMap.put(6, "JUL");
		monthMap.put(7, "AUG");
		monthMap.put(8, "SEP");
		monthMap.put(9, "OCT");
		monthMap.put(10, "NOV");
		monthMap.put(11, "DEC");
	}
	
	public static String readTxtFile(String filePath) {
		StringBuffer strBuff = new StringBuffer();
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					strBuff.append(lineTxt + "\n\r");

				}
				read.close();
			} else {
				HgLogger.getInstance().error("qiuxianxiang", "找不到指定的文件"+filePath);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("qiuxianxiang", "读取文件内容出错"+filePath+",exception:"+HgLogger.getStackTrace(e));
		}

		return strBuff.toString();
	}
	
	public static String getPnrText(PolicyByPnrQo qo) {
		HgLogger.getInstance().info("yuqz", "PnrUtil->封装pnrText->PolicyByPnrQo:" + JSON.toJSONString(qo));
		Calendar cale = Calendar.getInstance();
		cale.setTime(qo.getDepartureDate());
		
		String filePath = PnrUtil.class.getClassLoader().getResource("").getPath()+"OneWayPnrTextTemplate.txt";
		HgLogger.getInstance().info("yuqz", "PnrUtil->封装pnrText->pnrText位置:" + filePath);
		System.out.println(filePath);
		String pnrText = readTxtFile(filePath);
		
		//-封装pnrText参数
		pnrText = pnrText.replace("{0}", qo.getPassengerName());
		pnrText = pnrText.replace("{1}", qo.getPnr());
		pnrText = pnrText.replace("{2}", qo.getFlightNo());
		pnrText = pnrText.replace("{3}", qo.getClassNo());
		String param4Week = weekMap.get(cale.get(Calendar.DAY_OF_WEEK));
		String param4 = param4Week + getDate(cale);
		pnrText = pnrText.replace("{4}", param4);
		pnrText = pnrText.replace("{5}", qo.getBoardPoint()+qo.getOffPoint());
		
		String param6 =  getTime(cale);//0106  1点零6分
		pnrText = pnrText.replace("{6}", param6);
		pnrText = pnrText.replace("{10}", qo.getCarrier());
		pnrText = pnrText.replace("{13}", qo.getFlightNo());
		String param14 = qo.getClassNo()+ getDate(cale);
		pnrText = pnrText.replace("{14}", param14);
		
		cale.setTime(qo.getTktStart());  //重新设置日历时间为出票时间
		String param8 = getTime(cale);
		pnrText = pnrText.replace("{8}", param8);
		String param9 = getDate(cale);
		pnrText = pnrText.replace("{9}", param9);
		String param11 = param9+ String.valueOf(cale.get(Calendar.YEAR)).substring(2, 4);  //21JUL14
		pnrText = pnrText.replace("{11}", param11);
		String param12 = param8;
		pnrText = pnrText.replace("{12}", param12);
		
		cale.setTime(qo.getArrivalDate());  //重新设置日历时间为到达时间
		String param7 = getTime(cale);
		pnrText = pnrText.replace("{7}", param7);
		
		pnrText = pnrText.replace("{15}", qo.getCarrier());//新的pnr文本增加的
		
		HgLogger.getInstance().info("yuqz", "PnrUtil->封装pnrText->pnrText:" + pnrText);
		return pnrText;
	}
	
	/**
	 * 
	 * @方法功能说明：创建真实PNR文本
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月17日下午7:42:07
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String getRealPnrText(PolicyByPnrQo qo) {
		
		Calendar cale = Calendar.getInstance();
		cale.setTime(qo.getDepartureDate());
		
		String filePath = PnrUtil.class.getClassLoader().getResource("").getPath()+"OneWayPnrTextTemplate.txt";
		System.out.println(filePath);
		String pnrText = readTxtFile(filePath);
		
		//-封装pnrText参数
		pnrText = pnrText.replace("{0}", qo.getPassengerName());
		pnrText = pnrText.replace("{1}", qo.getPnr());
		pnrText = pnrText.replace("{2}", qo.getFlightNo());
		pnrText = pnrText.replace("{3}", qo.getClassNo());
		String param4Week = weekMap.get(cale.get(Calendar.DAY_OF_WEEK));
		String param4 = param4Week + getDate(cale);
		pnrText = pnrText.replace("{4}", param4);
		pnrText = pnrText.replace("{5}", qo.getBoardPoint()+qo.getOffPoint());
		
		String param6 =  getTime(cale);//0106  1点零6分
		pnrText = pnrText.replace("{6}", param6);
		pnrText = pnrText.replace("{10}", qo.getCarrier());
		pnrText = pnrText.replace("{13}", qo.getFlightNo());
		String param14 = qo.getClassNo()+ getDate(cale);
		pnrText = pnrText.replace("{14}", param14);
		
		cale.setTime(qo.getTktStart());  //重新设置日历时间为出票时间
		String param8 = getTime(cale);
		pnrText = pnrText.replace("{8}", param8);
		String param9 = getDate(cale);
		pnrText = pnrText.replace("{9}", param9);
		String param11 = param9+ String.valueOf(cale.get(Calendar.YEAR)).substring(2, 4);  //21JUL14
		pnrText = pnrText.replace("{11}", param11);
		String param12 = param8;
		pnrText = pnrText.replace("{12}", param12);
		
		cale.setTime(qo.getArrivalDate());  //重新设置日历时间为到达时间
		String param7 = getTime(cale);
		pnrText = pnrText.replace("{7}", param7);
		
		pnrText = pnrText.replace("{15}", qo.getCarrier());//新的pnr文本增加的
		
		return pnrText;
	}
	
	//获取格式 0106  1点零6分 的时间
	private static String getTime(Calendar cale) {
		String paramHour = cale.get(Calendar.HOUR_OF_DAY) < 10 ? "0"+cale.get(Calendar.HOUR_OF_DAY) : cale.get(Calendar.HOUR_OF_DAY)+"";
		String paramMin = cale.get(Calendar.MINUTE) < 10 ? "0"+cale.get(Calendar.MINUTE) : cale.get(Calendar.MINUTE)+"";
		String param =  paramHour + paramMin ;//0106  1点零6分
		return param;//0106  1点零6分
	}
	//获取格式21JUN  7月21日 的日期
	private static String getDate(Calendar cale) {
		String paramMonth = monthMap.get(cale.get(Calendar.MONTH));
		int paramDay =  cale.get(Calendar.DAY_OF_MONTH);
		if(paramDay < 10){
			return ("0"+paramDay+paramMonth);
		}else{
			return (paramDay+paramMonth);//21JUN			
		}
	}
	
	public static void main(String[] args) {
		Date departureDate = new Date();
		Date arrivalDate = new Date(departureDate.getTime()+1000*60*5);
		//航班号、仓位、出发日期、到达日期、起飞机场代码、到达机场代码、航司编码
		PolicyByPnrQo qo = new PolicyByPnrQo("CA8902","H",departureDate,arrivalDate,"PEK","DLC","CA");
		//如果还要设置其他信息请在qo添加
	
		String pnrText = getPnrText(qo);
		
		System.out.println(pnrText);
	}
}
