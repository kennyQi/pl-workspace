package hgfx.web.util;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;





public class logUtil extends Thread{
	/**
	 * 获取数据库对象
	 * @param String DBname   操作的数据库
	 * @param String collection   操作的表
     * @return
	 */
	
	@SuppressWarnings("deprecation")
	public static DBCollection getDB(String DBname,String collection){
		   if(StringUtils.isBlank(DBname)){
			  DBname=LogConfig.getValue("db_name");
		    }
		   if(StringUtils.isBlank(collection)){
			  collection=LogConfig.getValue("collection");
		   }
		    String ip=LogConfig.getValue("mongo_ip");
		    String  port=LogConfig.getValue("mongo_port");
		    Mongo mongo = new Mongo(ip,Integer.parseInt(port));
			DB db = mongo.getDB(DBname);
			DBCollection  user = db.getCollection(collection);
			return user;
		
	}
	
	/**
	 * 查询日志
	 * @param collection 如不需要指定表，传null，如需指定，请调用getDB方法，传入指定的数据库名和表名
	 * @param userName   操作人
	 * @param update     操作类型  （定义为常量，LogConstants）;
 	 * @param detail     操作明细
	 * @return
	 */
	public static List<DBObject>  getLog(DBCollection  collection,String userName,String update,String detail){
		if(collection==null){
			collection=getDB(null,null);
		}
		List<DBObject> list = new ArrayList<DBObject>();
		BasicDBObject query = new BasicDBObject();
	    if(StringUtils.isNotBlank(userName)){
	    	query.put("userName", userName);
	    }    
	    if(StringUtils.isNotBlank(update)){
	    	query.put("update", update);
	    }
	    if(StringUtils.isNotBlank(detail)){
	    	query.put("detail", detail);
	    }
		DBCursor cursor = collection.find(query).sort(new BasicDBObject("createTime", -1)).limit(10);
		while (cursor.hasNext()) {
			list.add(cursor.next());
		}
		
		
		
		return list;
		
		
	}
	
	/**
	 * 新增日志
	 * @param collection 如果不许要指定表，传null
	 * @param userName   操作人
	 * @param update     操作类型  （定义为常量，在LogConstants定义）;
	 * @param detail     操作明细
	 */
	public static  void addLog(DBCollection  collection,String userName,String update,String detail){
		if(collection==null){
			collection = getDB(null,null);
		}
		BasicDBObject query = new BasicDBObject();
		query.put("userName", userName);
		query.put("update", update);
		query.put("detail", detail);
		query.put("createTime", new Date());
		collection.insert(query);
		
		
		
	}

	public  void test(){
		logUtil util = new logUtil();
		util.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

	public static void main(String[] args) {
	
		//logUtil.addLog();
		/*List<Object> list = new ArrayList<Object>();
		DBCursor cursor = (DBCursor) logUtil.getLog();
		while (cursor.hasNext()) {
			list.add(cursor.next());
			
		}
		 
		int[] a=new int[]{3,4,5,6,0};
		int[] b=new int[]{0,3,4,5,6};
		System.err.println(Arrays.equals(a, b));*/
		/* JSONArray array = logUtil.getLog(null,null,null);
		 System.out.println(array);
		for (int i = 0; i < array.size(); i++) {
			
			Object object = array.get(i);
			Map<String,Object> json = (Map<String, Object>) JSONObject.toJSON(object);
			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			System.out.println(time.format(json.get("createTime")));
			*/
		}
		
	}
	
 

