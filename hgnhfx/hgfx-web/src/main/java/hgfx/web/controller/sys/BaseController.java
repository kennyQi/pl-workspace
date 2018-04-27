package hgfx.web.controller.sys;

import hg.demo.member.common.domain.model.system.SecurityConstants;
import hg.fx.domain.DistributorUser;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.qo.DistributorUserSQO;
import hg.fx.util.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by admin on 2016/5/26.
 */
public class BaseController {
	@Resource
	private DistributorUserSPI distributorUserSPIService;

	/**
	 * 从session获取的用户信息
	 * 
	 * @author Caihuan
	 * @date 2016年6月5日
	 */
	public DistributorUser getSessionUserInfo(HttpSession httpSession) {
		DistributorUser userInfo = (DistributorUser) httpSession
				.getAttribute(SecurityConstants.SESSION_USER_KEY);
		return userInfo;
	}

	/**
	 * 从sql获取的最新的用户信息
	 * 
	 * @author Caihuan
	 * @date 2016年6月5日
	 */
	public DistributorUser getUser(HttpSession httpSession) {
		DistributorUser userInfo = getSessionUserInfo(httpSession);
		DistributorUserSQO sqo = new DistributorUserSQO();
		sqo.setAccount((userInfo.getLoginName()));
		sqo.setEqAccount(true);//精确查询
		sqo.setUserRemoved(false); //逻辑删除的账号可以重新被添加，所以要保证唯一性
		return distributorUserSPIService.queryUnique(sqo);
	}

	/**
	 * excel导出
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-3 上午9:50:48
	 * @param wb
	 * @param response
	 * @throws IOException
	 */
	public void outputExcel(HSSFWorkbook wb, HttpServletResponse response,
			String path, String fileName) throws IOException {
		InputStream inStream = null;
		try {
			// 创建指定位置目录
			File f = new File(path);
			if (!f.exists()) {
				f.mkdir();
			}
			// 将文件写在本地
			FileOutputStream fout = new FileOutputStream(fileName);

			wb.write(fout);
			fout.close();
			// 将文件输入并下载 读到流中
			inStream = new FileInputStream(fileName); // 文件的存放路径
			// 设置输出的格式
			String name = "订单列表" + DateUtil.formatDate1(new Date());

			String isoName = parseGBK(name);
			response.reset();
			response.setContentType("application/x-msdownload MSEXCEL");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ isoName + ".xls" + "\"");
			// 循环取出流中的数据
			byte[] b = new byte[100];
			int len;
			while ((len = inStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @方法功能说明：手机号码校验 
	 *                移动：134、135、136、137、138、139、150、151、157(TD)、158、159、182、187、
	 *                188 联通：130、131、132、152、155、156、185、186
	 *                电信：133、153、180、189、（1349卫通）
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月6日 上午10:01:33
	 * @修改内容：
	 * @param mobile
	 * @return
	 */
	public static boolean isMobileNO(String mobile) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,2,3,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	// 将GBK字符转化为ISO码
	public static String parseGBK(String sIn) {
		if (sIn == null || sIn.equals(""))
			return sIn;
		try {
			return new String(sIn.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException usex) {
			return sIn;
		}
	}

	/**
	 * 
	 * @方法功能说明：用户退出
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月7日 上午11:55:09
	 * @修改内容：
	 * @param httpSession
	 */
	protected void removeUser(HttpSession httpSession) {
		httpSession.removeAttribute(SecurityConstants.SESSION_USER_KEY);
	}

	/**
	 * 
	 * @方法功能说明：重置session中的用户
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月7日 下午12:00:02
	 * @修改内容：
	 * @param httpSession
	 */
	protected void resetUser(HttpSession httpSession) {
		DistributorUser userInfo = getSessionUserInfo(httpSession);
		DistributorUserSQO sqo = new DistributorUserSQO();
		sqo.setId((userInfo.getId()));
		httpSession.setAttribute(SecurityConstants.SESSION_USER_KEY,
				distributorUserSPIService.queryUnique(sqo));
	}
	
	public static void main(String[] args) {
//		System.out.println(isMobileNO("18336751120"));
	}

}
