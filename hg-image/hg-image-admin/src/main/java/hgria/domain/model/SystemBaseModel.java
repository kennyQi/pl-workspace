package hgria.domain.model;

import javax.persistence.MappedSuperclass;

import hg.common.component.BaseModel;

@MappedSuperclass
public class SystemBaseModel extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 对应表前缀
	 */
	public static final String TABLE_PREFIX = "SYS_";
	
}
