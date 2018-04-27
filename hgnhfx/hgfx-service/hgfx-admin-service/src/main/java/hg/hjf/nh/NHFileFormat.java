/**
 * @NHFileFormat.java Create on 2015年5月28日下午1:17:18
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.hjf.nh;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.print.attribute.standard.Fidelity;

import hg.hjf.socket.ParseStr;
import hg.hjf.socket.SocketUtil;

/**
 * @类功能说明：
 * 兑换南航里程数据格式	<br>				
txt格式编码为默认值ANSI		<br>			
文件名：CMYYYYMMDD****.TXT		<br>			
反馈文件名:CMYYYYMMDD****_CZ.TXT			<br>		

 * @类修改者：
 * @修改日期：2015年5月28日下午1:17:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年5月28日下午1:17:18
 * @version：
 */
public class NHFileFormat {
//    txt格式编码为默认值ANSI	    
    static String CHarSet="GBK";
    //合作方简称代号:****（与原发文方代码保持一致）
    static String HJFSYSCode="HGTECH";
    //
    public static final String HJFDESC = "汇购汇积分";
    static String NHSYSCODE="CZ";
    
    /**
     * 字段格式以及数值
     * @类功能说明：
     * @类修改者：
     * @修改日期：2015年5月28日下午1:46:36
     * @修改说明：
     * @公司名称：浙江汇购科技有限公司
     * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
     * @创建时间：2015年5月28日下午1:46:36
     * @version：
     */
    public static class Field implements ParseStr{
	public int from,to,length;
	/**
	 * A:asc
	 * N:number
	 */
	public String type;
	public Object value;
	
	
	boolean must;
	/**
	 * 
	 * format:"1	2	2A	M	HD"//from to type spec	default-strvalue
	 * @Copyright (c) 2012 by www.hg365.com。
	 */
	public Field(String format){
	    String [] f= format.split("\\s");
	    from = Integer.parseInt(f[0]);
	    to = Integer.parseInt(f[1]);
	    type = ""+f[2].charAt(f[2].length()-1);
	    length =Integer.parseInt(f[2].substring(0,f[2].length()-1));
	    must = f[3].equals("M");
	    
	    //默认 value
	    if(f.length>=5)
		value=f[4];
	    
	}
	
	/* (non-Javadoc)
	 * @see hg.hjf.socket.ParseStr#parseStr(java.lang.String)
	 */
	@Override
	public void parseStr(String str) {
	    str= str.trim();
	    if(type.equals("N"))
		try {
		    value = Integer.parseInt(str);
		} catch (NumberFormatException e) {
		    throw new NumberFormatException("解析数值 '" + str +"' 错误,格式"+ this.toString());
		}
	    else
		value = str;
	}
	/* (non-Javadoc)
	 * @see hg.hjf.socket.ParseStr#toStr()
	 */
	@Override
	public String toStr() {
	    //check must
	    if(must && value==null)
		throw new RuntimeException("必选项:"+toString());
	    
	    String strValue;
	    //convert to strValue
	    {
		if(value!=null)
		    strValue = value.toString();
		else {
        		if(type.equals("N"))
        		    strValue ="0";
        		else
        		    strValue="";
		}
	    }
	    
	    if (type.endsWith("N"))
		return SocketUtil.toFixedString_0(strValue , length, CHarSet);
	    else if(type.endsWith("A"))
		return SocketUtil.toFixedStringPost(strValue , length, ' ', CHarSet);
	    else
		throw new RuntimeException("不认识的格式 "+type);
	};
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	 return String.format( "%s-%s %s %s val:%s",
		 from+"",
		 to +"",
		 length+type,
		 must?"M":"O",
		value);
	}
	
    }
    
    public static abstract class Row implements ParseStr{
	 
	/* (non-Javadoc)
	 * @see hg.hjf.socket.ParseStr#toStr()
	 */
	@Override
	public String toStr() {
	    StringBuffer buf= new StringBuffer();
	    for(Field f:fields())
		buf.append(f.toStr());
	    return buf.toString();
	}
	
	/* (non-Javadoc)
	 * @see hg.hjf.socket.ParseStr#parseStr(java.lang.String)
	 */
	@Override
	public void parseStr(String str) {
	    try {
		byte[] by=str.getBytes(CHarSet);
		for(Field f:fields()){
		    String strValue = new String(Arrays.copyOfRange(by, f.from-1, f.to),CHarSet );
		    f.parseStr(strValue);
		}
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }
	}
	
	abstract Field[]  fields();
    }
    
    /**\
     * 文件头
     * @类功能说明：
     * @类修改者：
     * @修改日期：2015年5月28日下午1:47:15
     * @修改说明：
     * @公司名称：浙江汇购科技有限公司
     * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
     * @创建时间：2015年5月28日下午1:47:15
     * @version：
     */
    public static class HeadRow extends Row implements ParseStr{
	 
	
	    public Field 记录号 = new Field(	"1	2	2A	M	HD");
	    public Field 文件生成日 = new Field("3	10	8N	M");
	    public Field 空格1= new Field(	"11	12	2A	O	");
	    public Field 文件类型= new Field(	"13	22	10A	M	02");
	    public Field 原发文方代= new Field(	"23	28	6A	M	"+HJFSYSCode);
	    public Field 答复方代码= new Field(	"29	34	6A	M	"+NHSYSCODE);
	    public Field 文件名= new Field(	"35	54	20A	M");
	    public Field 空格2= new Field(	"55	299	245A	O	");
	    public Field 结束符= new Field(	"300	300	1A	M	#");	
	    public Field[] fields = new Field[]{
		    记录号,
		    文件生成日,
		    空格1,
		    文件类型,
		    原发文方代,
		    答复方代码,
		    文件名,
		    空格2,
		    结束符
	    };
	    /* (non-Javadoc)
	    * @see hg.hjf.nh.NHFileFormat.Row#fields()
	    */
	    @Override
	    Field[] fields() {
	    return fields;
	    }
    }
    
//    记录行AC：用于增加里程				
    public static class ACRow extends Row implements ParseStr{
	public Field 交易代码	 = new Field(	"1	2	2A	M	AC");
	public Field AC记录流水号	 = new Field(	"3	22	20A	M");
	public Field 会员卡号	 = new Field(	"23	38	16A	M");
	public Field 姓	 = new Field(	"39	63	25A	M");
	public Field 名	 = new Field(	"64	88	25A	M");
	public Field 合作方会员级别	 = new Field(	"89	93	5A	O");
	public Field CZ会员级别代码	 = new Field(	"94	98	5A	O");
	public Field 兑换日期	 = new Field(	"99	106	8N	M");
	public Field check_out_date	 = new Field(	"107	114	8A	O");
	public Field 兑换机构代码	 = new Field(	"115	124	10A	M	"+HJFSYSCode);
	public Field 兑换机构描述	 = new Field(	"125	159	35A	O");
	public Field 促销代码	 = new Field(	"160	166	7A	O");
	public Field 基本里程数	 = new Field(	"167	174	8N	M");
	public Field 促销里程	 = new Field(	"175	182	8N	O");
	public Field 合作方会员级别促销里程	 = new Field(	"183	190	8N	O");
	public Field CZ会员级别促销里程	 = new Field(	"191	198	8N	O");
	public Field Invoice_Number	 = new Field(	"199	208	10N	O");
	public Field 空格	 = new Field(	"209	297	89A	O");
	public Field 南航反馈代码	 = new Field(	"298	299	2A	O");
	public Field 结束符	 = new Field(	"300	300	1A	M	#");
	public Field[] fields = new Field[]{
		交易代码,
		AC记录流水号,
		会员卡号,
		姓,
		名,
		合作方会员级别,
		CZ会员级别代码,
		兑换日期,
		check_out_date,
		兑换机构代码,
		兑换机构描述,
		促销代码,
		基本里程数,
		促销里程,
		合作方会员级别促销里程,
		CZ会员级别促销里程,
		Invoice_Number,
		空格,
		南航反馈代码,
		结束符
	};
	
	    @Override
	    Field[] fields() {
		return fields;
	    }
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	 return AC记录流水号.value +" 南航反馈代码:"+南航反馈代码.value;
	}
    }
    
    /**
     * 记录行AD：用于减少里程
     * @类功能说明：
     * @类修改者：
     * @修改日期：2015年5月28日下午2:33:01
     * @修改说明：
     * @公司名称：浙江汇购科技有限公司
     * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
     * @创建时间：2015年5月28日下午2:33:01
     * @version：
     */
    public static class ADRow extends  Row implements ParseStr{
	public Field 交易代码	= new Field(	"1	2	2A	M	AD");
	public Field AD记录流水号	= new Field(	"3	22	20A	M");
	public Field 会员卡号	= new Field(	"23	38	16A	M");
	public Field 姓	= new Field(	"39	63	25A	O");
	public Field 名	= new Field(	"64	88	25A	O");
	public Field 空格1	= new Field(	"89	98	10A	O");
	public Field 申请扣减日期	= new Field(	"99	106	8N	M");
	public Field 空格2	= new Field(	"107	114	8N	O");
	public Field 申请扣减机构代码	= new Field(	"115	124	10A	M	"+HJFSYSCode);
	public Field 申请扣减机构描述	= new Field(	"125	159	35A	O	" +HJFDESC);
	public Field 空格3	= new Field(	"160	166	7A	O");
	public Field 申请扣减里程数	= new Field(	"167	174	8N	M");
	public Field 空格4	= new Field(	"175	297	123A	O");
	public Field 南航反馈代码	= new Field(	"298	299	2A	O");
	public Field 结束符	= new Field(	"300	300	1A	M	#");
	
	public Field[] fields = new Field[]{
		交易代码,
		AD记录流水号,
		会员卡号,
		姓,
		名,
		空格1,
		申请扣减日期,
		空格2,
		申请扣减机构代码,
		申请扣减机构描述,
		空格3,
		申请扣减里程数,
		空格4,
		南航反馈代码,
		结束符,
	};
	
	    @Override
	    Field[] fields() {
		return fields;
	    }	
	
	public String toString() {
		 return AD记录流水号.value +" 南航反馈代码:"+南航反馈代码.value;
		}
    }
    
    //文件尾
    public static class TailRow extends Row implements ParseStr{
	public Field 记录号	= new Field(	"1	2	2A	M	$$");
	public Field 发送记录总数	= new Field(	"3	8	6N	M");
	public Field 发送AC总数	= new Field(	"9	14	6N	M");
	public Field 接收AC总数	= new Field(	"15	20	6N	O");
	public Field 发送AD总数	= new Field(	"21	26	6N	M");
	public Field 接收AD总数	= new Field(	"27	32	6N	O");
	public Field AC成功数	= new Field(	"33	38	6N	O");
	public Field AC失败数	= new Field(	"39	44	6N	O");
	public Field AD成功数	= new Field(	"45	50	6N	O");
	public Field AD失败数	= new Field(	"51	56	6N	O");
	public Field 空格	= new Field(	"57	299	243A	O");
	public Field 结束符	= new Field(	"300	300	1A	M	#");
	
	public Field[] fields = new Field[]{
		记录号		,
		发送记录总数,
		发送AC总数,
		接收AC总数,
		发送AD总数,
		接收AD总数,
		AC成功数,
		AC失败数,
		AD成功数,
		AD失败数,
		空格	, 
		结束符	 
		
	};
	
	    @Override
	    Field[] fields() {
	    return fields;
	    }
	    
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    return String.format("AC成功数 %s, AC失败数 %s,AD成功数 %s,AD失败数 %s,",
			AC成功数.value,
			AC失败数.value,
			AD成功数.value,
			AD失败数.value		    
		    );
	}
    }
    
    public static void main(String[] args) {
	 String format = "1	8	10N	O";
	Field l名	= new Field(	format);
	 l名.value="20140530";
	 String str = l名.toStr();
	 
	 Field l名2 = new Field(	format);
	 l名2.parseStr(str);
	 
	 ADRow ad  = new ADRow();
	 Field f =ad.名;
	 f.value="邢";
	 f.toStr();
    }

    /**
     * @Fields卡号姓名分隔符:卡号，姓名作为一个字段保存在jfflow表
     * 
     */
    public static final String 卡号姓名分隔符 = ":";
    public static final int 卡号_INDEX_0=0;
    public static final int 姓名_INDEX_1=1;
}
