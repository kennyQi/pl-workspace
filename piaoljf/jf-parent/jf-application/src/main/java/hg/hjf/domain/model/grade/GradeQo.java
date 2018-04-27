package hg.hjf.domain.model.grade;

import hg.common.component.BaseQo;
import hg.common.model.qo.DwzPaginQo;

public class GradeQo extends BaseQo{
	private String code;

	public GradeQo(String gradeCode) {
		code = gradeCode;
	}

	public GradeQo() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
