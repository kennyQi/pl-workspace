package service.test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import junit.framework.TestCase;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
public class PinyinHelperTest extends TestCase
{
	public static boolean isChineseChar(String str){
	       boolean temp = false;
	       Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
	       Matcher m=p.matcher(str); 
	       if(m.find()){ 
	           temp =  true;
	       }
	       return temp;
	   }
	public static void main(String[] args) {
		String t="阿勒泰|安康|阿克苏|鞍山|安庆|安顺|阿拉善左旗|阿里|阿拉善右旗|阿尔山|百色|包头|毕节|北海|博乐|保山|北京|巴彦淖尔|昌都|常德|长春|朝阳|赤峰|长治|重庆|长海|长沙|成都|常州|池州|长白山|大同|达县|稻城|丹东|迪庆|大连|大理市|敦煌|东营|大庆|德令哈|德宏|鄂尔多斯|额济纳旗|恩施|二连浩特|福州|阜阳|佛山|抚远|广州|广汉|格尔木|广元|固原|赣州|贵阳|桂林|光化|红原|海口|河池|邯郸|黑河|呼和浩特|合肥|杭州|淮安|怀化|海拉尔|哈密市|哈尔滨|和田市|汉中|黄山|景德镇|加格达奇|嘉峪关|井冈山|金昌|九江|晋江|佳木斯|济宁|锦州|鸡西|九寨沟|揭阳|济南|库车|康定|喀什市|凯里|喀纳斯|昆明|库尔勒|克拉玛依|黎平|龙岩|兰州|梁平|丽江|荔波|吕梁|临沧|拉萨|林西|洛阳|连云港|临沂|柳州|泸州|林芝|牡丹江|绵阳|梅州|满洲里|漠河|南昌|南充|宁波|南京|那拉提|南宁|南阳|南通|攀枝花|普洱|且末|庆阳|黔江|衢州|齐齐哈尔|秦皇岛|青岛|日喀则|神农架|上海|沈阳|沙市|石家庄|三亚|深圳|台州|塔城|腾冲|铜仁市|通辽|天水|吐鲁番|通化|天津|唐山|太原|乌兰浩特|乌鲁木齐|潍坊|威海|文山县|温州|乌海|武汉|武夷山|无锡|梧州|万州|兴义|夏河|西双版纳|襄阳|西昌|锡林浩特|西安|厦门|西宁|徐州|延安|银川|伊春|永州|榆林|宜宾|运城|宜春|宜昌|伊宁市|义乌|延吉|烟台|盐城|扬州|玉树县|郑州|张家界|舟山|张掖|昭通|中山|湛江|中卫|张家口|珠海|遵义|";
		String[] t1=t.split("\\|");
		String r="";
		for(int i=0;i<t1.length;i++){
			String s1=t1[i].trim();
			if(isChineseChar(s1)){
				System.out.println(s1);
				HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
				format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
				format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
				String quanpin="";
				String jianpin="";
				for(int j=0;j<s1.length();j++){
					char c=s1.charAt(j);
					try {
						String[] s=PinyinHelper.toHanyuPinyinStringArray(c, format);
						quanpin+=s[0];
						jianpin+=s[0].substring(0, 1);
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				}
				s1="'"+s1+"|"+quanpin+"|"+jianpin+"'";
				r+=s1+",";
			}
		}
		System.out.println(r);
	}
}
