package slfx.yg.open.utils;

import hg.log.util.HgLogger;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * @类功能说明：拼音工具类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:19:07
 * @版本：V1.0
 *
 */
public class PinYinUtil {
	
	public static String chNameToPinYin(String chName){
		
		if ("".equals(chName) || null == chName) {
	           return null;
	    }
		
		char[] srcCharArray = chName.toCharArray();//中文字符数组
		if(srcCharArray.length<=1){
			return null;
		}
		int index = 0;
		if(srcCharArray.length>3){
			index = 1;
		}
		
	    int srcCount = srcCharArray.length;
	    StringBuffer sb = new StringBuffer();
		
	    for (int i = 0; i < srcCount; i++) {
	    	String yp = charToPinyin(srcCharArray[i], false, null);
	    	if(yp==null){
	    		yp = "";
	    	}
	    	sb.append(yp);
	    	if(index==i){
	    		sb.append("/");//判断 在姓、名直接拼接/
	    	}
	    }
		return sb.toString();
	}
	
	
	 private static String charToPinyin(char src, boolean isPolyphone, String separator) {
	       // 创建汉语拼音处理类
	       HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	       // 输出设置，大小写，音标方式
	       defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
	       defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不输出声调

	       StringBuffer tempPinying = new StringBuffer();
	       
	       // 如果是中文
	       if (src > 128) {
	           try {
	               // 转换得出结果
	               String[] strs = PinyinHelper.toHanyuPinyinStringArray(src, defaultFormat);
	                        
	               // 是否查出多音字，默认是查出多音字的第一个字符
	               if (isPolyphone && null != separator) {
	                   for (int i = 0; i < strs.length; i++) {
	                       tempPinying.append(strs[i]);
	                       if (strs.length != (i + 1)) {
	                           // 多音字之间用特殊符号间隔起来
	                           tempPinying.append(separator);
	                       }
	                   }
	               } else {
	            	   //选择多音字
	            	   switch (src){
	            	   		case '瞿': tempPinying.append(strs[1]); break;
	            	   		case '重': tempPinying.append(strs[1]); break;
	            	   		case '区': tempPinying.append(strs[1]); break;
	            	   		case '仇': tempPinying.append(strs[1]); break;
	            	   		case '秘': tempPinying.append(strs[1]); break;
	            	   		case '解': tempPinying.append(strs[2]); break;
	            	   		case '单': tempPinying.append(strs[2]); break;
	            	   		case '朴': tempPinying.append(strs[1]); break;
	            	   		case '查': tempPinying.append(strs[1]); break;
	            	   		case '尉': tempPinying.append(strs[1]); break;
	            	   		case '缪': tempPinying.append(strs[1]); break;
	            	   		default:   tempPinying.append(strs[0]); break;
	            	   }
	               }
	           } catch (BadHanyuPinyinOutputFormatCombination e) {
	        	   HgLogger.getInstance().error("yangkang", "PinYinUtil->charToPinyin->exception[汉语拼音处理]:" + HgLogger.getStackTrace(e));
	               return null;
	           }
	       } else {
	           tempPinying.append(src);
//	    	   return null;
	       }
	       return tempPinying.toString();
	   }
	
	 
	 public static void main(String[] args) {
		String s = PinYinUtil.chNameToPinYin("谭登t");
		System.out.println(s);
	}
}
