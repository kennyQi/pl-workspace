package hsl.h5.control;

import hg.system.model.meta.City;
import hg.system.qo.CityQo;
import hg.system.service.CityService;
import hsl.api.base.ApiResponse;
import hsl.h5.base.result.api.ApiResult;
import hsl.h5.base.result.user.QueryUserResult;
import hsl.h5.base.utils.CachePool;
import hsl.h5.base.utils.OpenidTracker;
import hsl.h5.control.constant.Constants;
import hsl.h5.exception.HslapiException;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.user.HslUserBindAccountQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.inter.user.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSON;

/**
 * 票量游Action基类
 * @author 胡永伟
 */
public class HslCtrl {
	@Autowired
	private UserService userService;
	
	@Autowired
	private CityService cityService;
	
	private TreeMap<String,String> cityMap = new TreeMap<String,String>();

	protected Logger log = Logger.getLogger(this.getClass());
	
	protected boolean success(ApiResult apiresult) {
		String result = apiresult.getResult();
		return "1".equals(result);
	}
	
	protected String getHslErrResponseJsonStr(Exception e) {
		log.error("hsl.err", e);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setResult("0");
		apiResponse.setMessage("系统繁忙,请稍候");
		return JSON.toJSONString(apiResponse);
	}
	
	/**
	 * 获取用户id
	 * @param request
	 * @return
	 * @throws HslapiException
	 */
	protected String getUserId(
			HttpServletRequest request) throws HslapiException {
		if (isWCBrowser(request)) {
			String openid = OpenidTracker.get();
			String userId = CachePool.getUser(openid);
			if (userId == null) {
				userId = getUserIdByOpenid(openid);
			}
			return userId;
		} else {
			return (String) request.getSession().getAttribute("user");
		}
	}
	
	/**
	 * 根据userid获取用户信息
	 * @throws HslapiException 
	 */
	protected UserDTO getUserByUserId(String userId) 
			throws HslapiException {
		HslUserBindAccountQO userBindAccountQO=new HslUserBindAccountQO();
		HslUserQO hslUserQO = new HslUserQO();
		hslUserQO.setId(userId);
		userBindAccountQO.setUserQO(hslUserQO);
		QueryUserResult response = queryUser(userBindAccountQO);
		if (success(response)) {
			return response.getUserDTO();
		} else {
			throw new HslapiException(response.getMessage());
		}
	}
	
	/**
	 * 根据openid获取用户id
	 * @param openid
	 * @return
	 */
	private String getUserIdByOpenid(String openid) {
		HslUserBindAccountQO userBindAccountQO=new HslUserBindAccountQO();
		HslUserQO hslUserQO=new HslUserQO();
		userBindAccountQO.setAccountType(1);
		userBindAccountQO.setBindAccountId(openid);
		userBindAccountQO.setUserQO(hslUserQO);
		QueryUserResult queryUserResult = queryUser(userBindAccountQO);
		if (success(queryUserResult)&&null!=queryUserResult.getUserDTO()) {
			String userId = queryUserResult.getUserDTO().getId();
			CachePool.setUser(openid, userId);
			return userId;
		} else {
			return null;
		}
	}
	
	/**
	 * 查询用户
	 * @param userQO
	 * @return
	 */
	public QueryUserResult queryUser(HslUserBindAccountQO userBindAccountQO){
		QueryUserResult userResponse=new QueryUserResult();
		try {
			UserDTO userDTO=userService.queryUser(userBindAccountQO);
			userResponse.setUserDTO(userDTO);
			userResponse.setResult(ApiResponse.RESULT_CODE_OK);
			userResponse.setMessage("用户查询成功");
		} catch (UserException e) {
			userResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			userResponse.setMessage(e.getMessage());
		}
		return userResponse;
	}

	/**
	 * 验证是否是微信用户
	 * @param request
	 * @return
	 */
	protected boolean isWCBrowser(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent").toLowerCase();
		if (agent.indexOf("micromessenger") > -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 修饰日志信息标题
	 * @param title
	 * @return
	 */
	protected String newHeader(String title){
		return "<<---------------移动端--"+title+"--------------->>\r\n";
	}
	
	protected String newLog(String title, String head, String msg){
		StringBuilder log = new StringBuilder();
		log.append("<<---------------移动端--"+title+"--------------\r\n\n\n");
		log.append("\t\t\t\t\t\t------" + head + "------\r\n\n");
		log.append(msg+"\r\n\n");
		log.append("\n\n\t\t\t\t\t\t---------------移动端--"+title+"-------------->>\r\n");
		return log.toString();
	}
	
	protected String printMsg(String head,String msg){
		StringBuilder log = new StringBuilder();
		log.append("\t\t\t\t\t\t------" + head + "------\r\n\n");
		log.append(msg+"\r\n\n");
		return log.toString();
	}
	
	/**
	 * 自动将yyyy-MM-dd格式的参数转换成Date型参数
	 * @param binder
	 */
	@InitBinder 
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); 
	}
	
	public TreeMap<String,String> getCityMap(){
		if(cityMap.isEmpty()){
			CityQo cityQo = new CityQo();
			List<City> cityList = cityService.queryList(cityQo);
			if(cityList!=null&&cityList.size()>0){
				for(City city : cityList){
					System.out.println(city.getCode()+","+city.getName());
					cityMap.put(city.getCode(), city.getName());
				}
			}
		}
		return cityMap;
	}
}
