package hg.demo.web.controller;

import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.system.SecurityConstants;
import hg.demo.member.common.spi.SecuritySPI;
import hg.demo.member.common.spi.qo.Security.QueryAuthUserSQO;
import hg.demo.web.common.UserInfo;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.operationLog.CreateOperationLogCommand;
import hg.fx.spi.OperationLogSPI;
import hg.fx.util.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by admin on 2016/5/26.
 */
public class BaseController {
	@Resource
	private SecuritySPI securityService;

	@Autowired
	private OperationLogSPI operationLogService;

	public UserInfo getUserInfo(HttpSession httpSession) {
		UserInfo userInfo = (UserInfo) httpSession
				.getAttribute(SecurityConstants.SESSION_USER_KEY);
		return userInfo;
	}

	public UserInfo setUserInfo(AuthUser authUser) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(authUser.getId());
		userInfo.setLoginName(authUser.getLoginName());
		userInfo.setDisplayName(authUser.getDisplayName());
		userInfo.setEnable(authUser.getEnable());
		Set<String> set = new HashSet<String>();
		if (set != null && set.size() > 0) {
			for (AuthRole authRole : authUser.getAuthRoleSet()) {
				set.add(authRole.getId());
			}
		}
		userInfo.setAuthRoleSet(set);
		return userInfo;
	}

	public AuthUser getAuthUser(HttpSession httpSession) {
		UserInfo userInfo = getUserInfo(httpSession);
		QueryAuthUserSQO queryAuthUserSQO = new QueryAuthUserSQO();
		queryAuthUserSQO.setLoginName(userInfo.getLoginName());
		return securityService.queryAuthUser(queryAuthUserSQO);
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
			response.reset();
			response.setContentType("application/vnd.ms-excel MSEXCEL");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ parseGBK(name) + ".xls" + "\"");
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

	public void saveLog(String content, HttpServletRequest request, Integer type) {
		// 添加操作日志
		CreateOperationLogCommand logCmd = new CreateOperationLogCommand();
		// 操作人
		logCmd.setAuthUser(getAuthUser(request.getSession()));
		logCmd.setCreateDate(new Date());
		logCmd.setContent(content);
		logCmd.setType(type);
		logCmd.setId(UUIDGenerator.getUUID());
		operationLogService.createOperationLog(logCmd);
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
				.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,2,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	public static String parseGBK(String sIn) {
		if (sIn == null || sIn.equals(""))
			return sIn;
		try {
			return new String(sIn.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException usex) {
			return sIn;
		}
	}
}
