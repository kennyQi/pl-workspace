/**
 * @文件名称：IndustryType.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-5下午2:39:25
 */
package hgtech.jfaccount;

import hgtech.jf.entity.Entity;
import hgtech.jf.entity.StringUK;
import hgtech.jf.tree.WithChildren;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *  消费形态。为层次结构。如商旅行业|旅游|门票。包含所属行业分类以及具体消费类型等
 * @类修改者：
 * @修改日期：2014-9-5下午2:39:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-5下午2:39:25
 *
 */
public class TradeType implements WithChildren<TradeType> ,Serializable, Entity<StringUK>{
    
	/**
     * @FieldsserialVersionUID:TODO
     */
    private static final long serialVersionUID = -6538471043786260959L;
	/**
	 * 编码，唯一约束
	 */
	public String code;
	public String name;
	public boolean status;
	public String clientName;
	/**
	 * 上级类别
	 */
	public TradeType upperType;
	public LinkedList<WithChildren<TradeType>> sublist=new LinkedList<WithChildren<TradeType>>();
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.WithChildren#getSubList()
	 */
	@Override
	public LinkedList<WithChildren<TradeType>> getSubList() {
		return sublist;
	}
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.WithChildren#getMe()
	 */
	@Override
	public TradeType getMe() {
		return this;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	public String getId() {
		return code;
	}
	public void setId(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TradeType getUpperType() {
		return upperType;
	}
	public void setUpperType(TradeType upperType) {
		this.upperType = upperType;
	}
	public LinkedList<WithChildren<TradeType>> getSublist() {
		return sublist;
	}
	public void setSublist(LinkedList<WithChildren<TradeType>> sublist) {
		this.sublist = sublist;
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#readUK()
	 */
	@Override
	public StringUK readUK() {
	    return new StringUK(code);
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#putUK(java.lang.Object)
	 */
	@Override
	public void putUK(StringUK uk) {
	    code=uk.getS();
	    
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#syncUK()
	 */
	@Override
	public void syncUK() {
	}
	/**
	 * @return the status
	 */
	public boolean isStatus() {
	    return status;
	}
	public boolean getStatus() {
	    return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
	    this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
			TradeType o  =(TradeType) obj;
			if(o==null)
				return super.equals(obj);
			else
				return code.equals(o.code);
	}
}
