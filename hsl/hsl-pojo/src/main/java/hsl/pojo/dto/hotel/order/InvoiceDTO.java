package hsl.pojo.dto.hotel.order;
import java.io.Serializable;
import java.math.BigDecimal;
public class InvoiceDTO implements Serializable{
	private static final long serialVersionUID = 1218401892082279726L;
	/**
	 * 标题
	 */
    protected String title;
    /**
     * 名称
     */
    protected String itemName;
    /**
     * 金额
     */
    protected BigDecimal amount;
    /**
     * 接受人
     */
    protected RecipientDTO recipient;

    /**
	 * 发票状态 false--未处理、true--已开票
	 */
	private Boolean status;
	/**
	 * 邮寄状态 false--未邮寄、true--已邮寄
	 */
	private Boolean deliveryStatus;
    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the itemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the value of the itemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemName(String value) {
        this.itemName = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the recipient property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientDTO }
     *     
     */
    public RecipientDTO getRecipient() {
        return recipient;
    }

    /**
     * Sets the value of the recipient property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientDTO }
     *     
     */
    public void setRecipient(RecipientDTO value) {
        this.recipient = value;
    }

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

}
