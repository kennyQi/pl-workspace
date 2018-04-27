/**
 * @NHFile.java Create on 2015年5月28日下午2:45:36
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.hjf.nh;

import hg.hjf.nh.NHFileFormat.ACRow;
import hg.hjf.nh.NHFileFormat.ADRow;
import hg.hjf.nh.NHFileFormat.Field;
import hg.hjf.nh.NHFileFormat.HeadRow;
import hg.hjf.nh.NHFileFormat.TailRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015年5月28日下午2:45:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年5月28日下午2:45:36
 * @version：
 */
public class NHFile {
     /**
     * @Fields_511111111111:TODO
     */
    private static final String _511111111111 = "511111111111";
    /**
     * @Fields_411111111111:TODO
     */
    private static final String _411111111111 = "411111111111";
    HeadRow head = new HeadRow();
    public List<NHFileFormat.ACRow> acList= new ArrayList<NHFileFormat.ACRow>();
    public List<NHFileFormat.ADRow> adList = new ArrayList<NHFileFormat.ADRow>();
    public TailRow tail = new TailRow() ;
 
    public static Map<String, String> resultCodeMap = new HashMap<String, String>();
    public static String RETCODE_OK="01";
    
    static {
	resultCodeMap.put("01",	"成功录入/退办              ");
	resultCodeMap.put("02",	"重复申请                   ");
	resultCodeMap.put("03",	"明珠卡号错误               ");
	resultCodeMap.put("04",	"明珠卡号与姓名不符         ");
	resultCodeMap.put("05",	"缺少兑换日期或兑换日期错误 ");
	resultCodeMap.put("08",	"缺少记录流水号             ");
	resultCodeMap.put("10",	"交易代码不是AC/AD          ");
	resultCodeMap.put("12",	"AD后里程将变为负数         ");

    }
    
    /**
     * @return 
     * 生成南航文件
     * @方法功能说明：
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年6月1日上午10:15:11
     * @version：
     * @修改内容：
     * @参数：@param date8
     * @参数：@param path
     * @参数：@throws IOException
     * @return:void
     * @throws
     */
    public String toFile(String date8,File path) throws IOException{
	//CMYYYYMMDD****.TXT
	String sendFileName = getSendFileName(date8);
	toFile(date8, path, sendFileName);
	return sendFileName;
    }
    
    //生成反馈文件。模拟南航测试用
    private void toBackFile(String date8,File path) throws IOException{
	//CMYYYYMMDD****.TXT
	String sendFileName =   "CM" + date8 + NHFileFormat.HJFSYSCode +"_CZ";;
	toFile(date8, path, sendFileName);
    }
    /**
     * @方法功能说明：
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年6月1日上午10:58:57
     * @version：
     * @修改内容：
     * @参数：@param date8
     * @参数：@param path
     * @参数：@param sendFileName 不含扩展名
     * @参数：@throws IOException
     * @return:void
     * @throws
     */
    void toFile(String date8, File path, String sendFileName) throws IOException {
//    System.out.println(path.getAbsolutePath());
	File file = new File(path, sendFileName);
	OutputStreamWriter w = new  OutputStreamWriter( new FileOutputStream(file), NHFileFormat.CHarSet);
	
//	System.out.println("w.getEncoding() "+w.getEncoding());
	//head
	head.文件生成日.value=date8;
	head.文件名.value = sendFileName;
	w.write(head.toStr()+"\r\n");
	
	//row
	for(ACRow r:acList)
	    w.write(r.toStr()+"\r\n");

	for(ADRow r:adList)
	    w.write(r.toStr()+"\r\n");
	
	//tail
	tail.发送AD总数.value = adList.size();
	tail.发送AC总数.value = acList.size();
	tail.发送记录总数.value = acList.size() + adList.size();
	w.write(tail.toStr());
	w.flush();
	w.close();
	
//	System.out.println(file +" write ok.");
    }

    /**
     * @throws IOException 
     * 解析南航反馈文件
     * @方法功能说明：
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年6月1日上午10:15:24
     * @version：
     * @修改内容：
     * @参数：@param file
     * @return:void
     * @throws
     */
    public void parseFile(String file) throws IOException{
//	if(!file.endsWith("_CZ.TXT"))
//	    throw new RuntimeException("反馈文件名:CMYYYYMMDD****_CZ.TXT");
	
	List<String> lines = org.apache.commons.io.FileUtils.readLines(new File(file), NHFileFormat.CHarSet);
	for(String l :lines){
	    String rowType =l.substring(0,2);
	    if(rowType.equals("HD"))
		 head.parseStr(l);
	    else if(rowType.equals("AC")){
		ACRow e = new ACRow();
		e.parseStr(l);
		acList.add(e);
	    }else if(rowType.equals("AD")){
		ADRow e = new ADRow();
		e.parseStr(l);
		adList.add(e);
	    }else if(rowType.equals("$$")){
		tail.parseStr(l);
	    }
		
	}
	
    }
    /**
     * @方法功能说明：发送文件名 不含扩展名
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年5月31日下午12:48:13
     * @version：
     * @修改内容：
     * @参数：@param date8
     * @参数：@return
     * @return:String
     * @throws
     */
    String getSendFileName(String date8) {
	return "CM" + date8 + NHFileFormat.HJFSYSCode +".TXT";
    }
    
    /**
     * 
     * @方法功能说明：
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年5月31日下午12:48:40
     * @version：
     * @修改内容：
     * @参数：@param date8
     * @参数：@return
     * @return:String
     * @throws
     */
    String getFeedBackFileName(String date8) {
 	return "CM" + date8 + NHFileFormat.HJFSYSCode +"_CZ.TXT";
     }
    

    
    /**
     *校验卡
     *南航明珠俱乐部12位会员卡号的第一位为校验位，与另外11位数字组成南航会员卡号。
举例说明：南航卡号   180006600036
           1     8   0   0   0   6   6   0   0   0   3   6
        校验位   2   3   4   5   6   7   8   9   10  11  12

计算方法：
1、 将第2位至第12位的数字相加，得总和29：
8+0+0+0+6+6+0+0+0+3+6=29
2、 将总和除于7，得余数1
29/7=4余1
3、 余数1即为第一位校验位的数值。
     */
    static public boolean checkCardValid(String card){
	if(card.length()!=12)
	    return false;
	
	String check=card.charAt(0)+"";
	int sum =0;
	for(int i=1;i<12;i++)
	{
	    sum += Integer.parseInt(card.charAt(i)+"");
	}
	int mod = sum % 7;
	return (Integer.parseInt(check)==mod);
	
    }
    /**
     * 生成完整卡号，带上校验位
     * @param card 11位卡号
     * @return
     */
    static public String completeCardWithCheck(String card){
    	if(card.length()!=11)
    		throw new RuntimeException("length must 11");
    	
    	int sum =0;
    	for(int i=0;i<card.length();i++)
    	{
    	    sum += Integer.parseInt(card.charAt(i)+"");
    	}
    	int mod = sum % 7;
    	return mod +card;
    }
    
    static public String genCardNo(int no){
    	String card=String.format("%011d", no);
		return completeCardWithCheck(card);
    }
    
    public static void main(String[] args) throws IOException {
	
//    	System.out.println(String.format("%011d", 123));
//	System.out.println(checkCardValid(_411111111111));
//	System.out.println(checkCardValid(_511111111111));
	
 
	
//	_genFile();
//	_genBackFile();
//	_parseFile();
//	_testParseFile("d:\\CM20150611HGTECH_CZ.TXT");
    }

    /**
     * @方法功能说明：
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年6月1日上午10:12:06
     * @version：
     * @修改内容：
     * @参数：@throws IOException
     * @return:void
     * @throws
     */
    static void _genFile() throws IOException {
	String date="20150611";
	NHFile nhfile = new NHFile();

	//head
	
	//row
	ACRow e = new ACRow();
	e.AC记录流水号.value=System.currentTimeMillis()+"";
	e.会员卡号.value=_411111111111;//正确卡号
	e.兑换日期.value="20150525";
	e.基本里程数.value=10000;
	e.姓.value="XINGLIJUN";
	e.名.value="XINGLIJUN";
	e.空格.value="fullflowid";
	nhfile.acList.add(e);

	ACRow e2 = new ACRow();
	e2.AC记录流水号.value=System.currentTimeMillis()+1;
	e2.会员卡号.value=_511111111111;//错误卡号
	e2.兑换日期.value="20150526";
	e2.基本里程数.value=20000;
	e2.姓.value ="邢";
	e2.名.value="立军2";
	nhfile.acList.add(e2);
	
	//tail
	nhfile.toFile(date, new File("./"));
    }
    static void _genBackFile() throws IOException {
	String date="20150531";
	NHFile nhfile = new NHFile();

	//head
	
	//row
	ADRow e = new ADRow();
	e.AD记录流水号.value=System.currentTimeMillis()+"";
	e.会员卡号.value=_411111111111;//正确卡号
	e.申请扣减日期.value="20150525";
	e.申请扣减里程数.value=10000;
	e.名.value="XING";
	e.南航反馈代码.value="01";
	nhfile.adList.add(e);

	ADRow e2 = new ADRow();
	e2.AD记录流水号.value=System.currentTimeMillis()+"a";
	e2.会员卡号.value="13010219730114";
	e2.申请扣减日期.value="20150526";
	e2.申请扣减里程数.value=20000;
	e2.名.value="XING2";
	e2.南航反馈代码.value="03";
	nhfile.adList.add(e2);
	
	//tail
	nhfile.tail.AD成功数.value="1";
	nhfile.tail.AD失败数.value="1";
	nhfile.tail.接收AD总数.value="2";
	
	nhfile.toBackFile(date, new File("./"));
    }
    
    static void _parseFile() throws IOException {
	String file=".\\CM20150531HGTECH_CZ.TXT";
	_testParseFile(file);
    }

    /**
     * @方法功能说明：
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年6月11日下午2:11:05
     * @version：
     * @修改内容：
     * @参数：@param file
     * @参数：@throws IOException
     * @return:void
     * @throws
     */
    public static void _testParseFile(String file) throws IOException {
	NHFile nhfile = new NHFile();
	nhfile.parseFile(file);
	
	StringWriter w = new StringWriter();
	//row
	for(ACRow r:nhfile.acList)
	    w.write(r.toString()+ resultCodeMap.get(r.南航反馈代码.value)+ "\n");

	for(ADRow r:nhfile.adList)
	    w.write(r.toString()+"\n");

	w.write(nhfile.tail.toString());
	
//	System.out.println(w);
    }
    
    
}

