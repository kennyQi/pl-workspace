/**
 * @JfSecurityUtil.java Create on 2015年1月27日下午8:22:41
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jf.security;

import hg.common.util.Md5FileUtil;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015年1月27日下午8:22:41
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月27日下午8:22:41
 * @version：
 */
public class JfSecurityUtil {

    /**
     * @方法功能说明：返回签名后的结果
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午8:10:12
     * @version：
     * @修改内容：
     * @参数：@param time
     * @参数：@param code
     * @参数：@param passK
     * @参数：@return
     * @return:String
     * @throws
     */
    public static String md5(String data,String time,  String passK) {
        String newSign = data + time+ passK;
        String md5 = Md5FileUtil.MD5(newSign);
        return md5;
    }

}
