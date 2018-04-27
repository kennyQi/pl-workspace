package hg.fx.util;

import hg.demo.member.common.MD5HashUtil;

/**
 * 字符校验
 * @author Caihuan
 * @date   2016年6月2日
 */
public class CharacterUtil {

	//汉字unicode区间范围
	public static final int unicodeRange = ('龥'-'一');
	
	
	/**
	 * 判断 汉字 ，字母
	 * @author Caihuan
	 * @date   2016年6月2日
	 */
	public static boolean checkCharacter(String str)
	{
		for(int i=0;i<str.length();i++)
		{
			if(!isChinese(str.charAt(i)))
			{
				if(!checkLetter(str.charAt(i)))
				{
					return false;
				}
			}
			
		}
		return true;
	}
	
	/**
	 * 判断数字、字母、汉字
	 * @author Caihuan
	 * @date   2016年6月2日
	 */
	public static boolean checkCharacterOrHan(String str)
	{
		for(int i=0;i<str.length();i++)
		{
			if(!Character.isDigit(str.charAt(i)))
			{
				if(!checkLetter(str.charAt(i)))
				{
					if(!isChinese(str.charAt(i)))
					{
						return false;
					}
				}
			}
			
			
		}
		return true;
	}
	
	private static boolean checkLetter(char c)
	{
		if(('A'<=c&&c<='Z')||('a'<=c&&c<='z'))
		{
			return true;
		}
		return false;
	}
	private static boolean isChinese(char c)
	{
		int s= (c-'一');
		if(s>=0&&s<=unicodeRange)
		{
			return true;
		}
		return false;
	}
	
	 /**
     * 根据商户的公司名称获取商户的初始密码
     * @author Caihuan
     * @date   2016年6月3日
     */
    public static String getDistributorPassword(String companyName)
    {
    	if(!isChinese(companyName.charAt(0)))
    	{
    		return  MD5HashUtil.toMD5(String.valueOf(companyName.charAt(0)).toUpperCase()+"123456");
    	}
    	String firstSpell = PinYinUtil.getFirstSpell(String.valueOf(companyName.charAt(0))).toUpperCase();
    	return MD5HashUtil.toMD5(firstSpell + "123456");
    }
	public static void main(String[] args)
	{
//		System.out.println(checkCharacter("ssd")); //
//		System.out.println(checkCharacterOrHan("s123"));
//		System.out.println(checkDistributorAccount("a是ss1>1"));
	}

	/**
	 * 检查商户帐号
	 * @author Caihuan
	 * @date   2016年6月3日
	 */
	public static boolean checkDistributorAccount(String account) {
		if(checkLetter(account.charAt(0)))
		{
			for(int i=0;i<account.length();i++)
			{
				if(Character.isDigit((account.charAt(i)))||checkLetter(account.charAt(i)))
				{
					
				}
				else
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	 /**
     * 商户首拼
     * @author Caihuan
     * @date   2016年6月3日
     */
	public static String getFirstDistrobutorSpell(String companyName) {
		if(!CharacterUtil.checkLetter(companyName.charAt(0))&&!isChinese(companyName.charAt(0)))
		{
			return "@";
		}
		char first = PinYinUtil.getFirstSpell(companyName).charAt(0);
		if(!CharacterUtil.checkLetter(first))
		{
			return "@";
		}
		return String.valueOf(first);
	}
}
