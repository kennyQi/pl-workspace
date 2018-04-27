package hg.jxc.admin.common;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FormatMoney {
	
	/**
	 * 保留两位小数    不四舍五入
	 * @param money
	 * @param df
	 * @return
	 */
	public static Double formatMoney1(Double money, DecimalFormat df){
		if(money>=0){
			df.setRoundingMode(RoundingMode.FLOOR);
		}else{
			df.setRoundingMode(RoundingMode.DOWN);
		}
		return new Double(df.format(money));
	}
	
	
}
