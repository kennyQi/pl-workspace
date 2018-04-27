package hg.demo.member.common.domain.vo;

import java.io.Serializable;

/**
 * @author zhurz
 */
public class DepartmentVO  implements Serializable {

	private String id;

	/**
	 * 名称
	 */
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
