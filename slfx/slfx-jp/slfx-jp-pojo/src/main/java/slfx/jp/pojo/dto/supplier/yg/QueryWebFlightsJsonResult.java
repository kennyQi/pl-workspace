package slfx.jp.pojo.dto.supplier.yg;

/**
 * 
 * @类功能说明：查询航班信息返回的JSON数据DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:58:04
 * @版本：V1.0
 *
 */
public class QueryWebFlightsJsonResult {
	/**
	 * "S":{ "0":[ GDS用时, 航班查询用时, 匹配政策用时, 总用时,” 航班数据来源”,” 获取航班座位数唯一标识”,” 预留”] },
	 */
	private String S;
	
	/**
	 * "A": { "航空公司二字码":["航空公司名称","航空公司简称","是否有网上值机"] },
	 * 航空公司信息列表格式，例子："CA":["中国国际航空公司","中国航空","1"]
	 */
	private String A;
	
	/**
	 * "P": { "机场三字码":["机场简称","机场全称","机场所在城市","城市三字码"] },
	 * 机场信息列表格式，例子："PEK":["首都机场","首都国际机场","北京","BJS"]
	 */
	private String P;
	
	/**
	 * "J": { "机型代码":["机型名称","型号","机体",最少人数,最多人数] }, 
	 * 机型列表格式，例子："763":["波音763","767-300/300ER","大型",203,290]
	 */
	private String J;
	
	/**
	 * "D": { "日期序号"："日期" },
	 * 航班日期字典，例子："2":"2013/03/09"
	 */
	private String D;
	
	/**
	 * "F": { "航班号":["起飞机场代码","到达机场代码","起飞日期序号","起飞时间","到达日期序号","到达时间",飞行时长(以分钟记),"机型",共享航班号(没有为空),经停次数,"起飞航站楼","到达航站楼",”有无餐食”,机建,燃油,里程,Y舱价格] },
	 * 航班信息列表格式,例子："CZ7689":["PEK","PVG","2","21:05","3","23:25",80,"333","FM9878",0,"T2","T1",”S”,1,50,130,1300,1500]
	 */
	private String F;
	
	/**
	 * "H": [ ["去程航班号","最低价","最低价舱位类型",[[票面价,折扣,代理费,奖励,结算,"散列后的字符串（8位）","剩余座位","舱位代码","舱位类型",退改规定ID,"是否为申请舱","是否允许儿童预订","价格来源","报价ID","产品编号"]]] ],
     *  单程航线价格列表格式，例子：["CA135",1200,"T",[[1300,45,5,200,1200,"abcdefgh","SA","Y","F",5,'N',"N","NFD","1234","234"]]]
	 */
	private String H;
	
	/**
	 * "T": { "退改ID"：["退票规定","改期规定","签转规定"] },
     * 退改规定字典表，例子："1":["退票规定","改期规定","签转规定"]
	 */
	private String T;
	
	/**
	 * "C": {"政策ID"：["政策"] },
	 * 舱位政策字典表，例子："1":["政策"]
	 */
	private String C;
	
	/**
	 * "W":["起始日期id",[价格]],
	 * 一周底价列表，例子："W":[2,[880,780,900,1200,450,680,790]]
	 */
	private String W;
	
	/**
	 * "Q":["出发城市三字码","到达城市三字码",出发日期id,"起飞时间范围","航空公司2字母",经停类型,"航程类型"],
	 * 查询参数返回，"Q":["BJS","SHA",2,"0000","CA",2,"OW"]
	 */
	private String Q;
	
	/**
	 * "O":["随机8位加密串","获取航班座位数唯一标识"],
	 * 其他参数，例子："O":["abcdefgh","1234"]
	 */
	private String O;

	public String getS() {
		return S;
	}

	public void setS(String s) {
		S = s;
	}

	public String getA() {
		return A;
	}

	public void setA(String a) {
		A = a;
	}

	public String getP() {
		return P;
	}

	public void setP(String p) {
		P = p;
	}

	public String getJ() {
		return J;
	}

	public void setJ(String j) {
		J = j;
	}

	public String getD() {
		return D;
	}

	public void setD(String d) {
		D = d;
	}

	public String getF() {
		return F;
	}

	public void setF(String f) {
		F = f;
	}

	public String getH() {
		return H;
	}

	public void setH(String h) {
		H = h;
	}

	public String getT() {
		return T;
	}

	public void setT(String t) {
		T = t;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getW() {
		return W;
	}

	public void setW(String w) {
		W = w;
	}

	public String getQ() {
		return Q;
	}

	public void setQ(String q) {
		Q = q;
	}

	public String getO() {
		return O;
	}

	public void setO(String o) {
		O = o;
	}
	
}
