package hg.common.util;

import hg.common.model.DBResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

public class JDBCRequester {
	/**
	 * 返回连接
	 * @param classDriverName
	 * @param url
	 * @param userName
	 * @param password
	 * @param timeOut
	 * @return
	 */
	public static DBResponse getDBResponse(String classDriverName,String url,String userName,String password,int timeOut,String sql){
		DBResponse res = new DBResponse();
		Date sendTime = new Date();
		res.setSendTime(sendTime);
		try{			
			Class.forName(classDriverName);			
			//数据库setLongTime 设置的秒数 所以这里  需要转换一下 进一法   0 则为无限设置
			int timeOutTemp = (int) Math.ceil((double)timeOut / 1000);
			DriverManager.setLoginTimeout(timeOutTemp);
			Connection conn = DriverManager.getConnection(url,userName,password);
			if(sql!=null&&!sql.equals("")){
				Statement stam = conn.createStatement();
				stam.execute(sql);
			}
			Date handOverTime = new Date();
			Long responseTime = handOverTime.getTime()-sendTime.getTime();
			if(responseTime>timeOut){
				res.setHandOverTime(new Date());
				res.setContent("请求连接超时！");
				res.setResponseTime(0L);
				res.setResponseState(3);	
			}else{			
				res.setHandOverTime(handOverTime);
				res.setResponseState(1);
				res.setResponseTime(handOverTime.getTime()-sendTime.getTime());
				res.setContent("数据库-访问成功！-语句执行成功！");
				conn.close();	
			}	
		}catch(Exception e){
			res.setHandOverTime(new Date());
			res.setResponseTime(0L);
			res.setResponseState(3);
			res.setContent("数据库--访问失败！");
		} 
		return res;
	}
	
}
