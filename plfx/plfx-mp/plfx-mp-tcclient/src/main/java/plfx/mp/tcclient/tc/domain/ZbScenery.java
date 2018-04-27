package plfx.mp.tcclient.tc.domain;


/**
 * 周边景区
 * @author zhangqy
 *
 */
public class ZbScenery {
	/**
	 * 景点Id 
	 */
	private Integer id;
	/**
	 * 景点名
	 */
	private String name;
	/**
	 * 票价
	 */
	private Double amount;
	/**
	 * 景点级别
	 */
	private String grade;
	private String adress;
	private Double amountAdvice;
	private String imgpath;
	private Double distance;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public Double getAmountAdvice() {
		return amountAdvice;
	}
	public void setAmountAdvice(Double amountAdvice) {
		this.amountAdvice = amountAdvice;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	
}
