package zzpl.domain.model.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import zzpl.domain.model.M;
import hg.common.component.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_APP + "APP")
public class APP extends BaseModel {

	@Column(name = "APP_NAME", length = 512)
	private String APPName;

	public String getAPPName() {
		return APPName;
	}

	public void setAPPName(String aPPName) {
		APPName = aPPName;
	}

}
