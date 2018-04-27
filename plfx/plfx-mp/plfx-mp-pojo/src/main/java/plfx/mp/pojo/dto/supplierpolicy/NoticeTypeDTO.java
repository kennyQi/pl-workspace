package plfx.mp.pojo.dto.supplierpolicy;

import java.io.Serializable;
import java.util.List;

/**
 * 须知类型
 * 
 * @author Administrator
 * 
 */
public class NoticeTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 序号
	 */
	private Integer sort;

	public List<NoticeItemDTO> noticeItems;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<NoticeItemDTO> getNoticeItems() {
		return noticeItems;
	}

	public void setNoticeItems(List<NoticeItemDTO> noticeItems) {
		this.noticeItems = noticeItems;
	}

}