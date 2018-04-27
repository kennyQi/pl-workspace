package pl.cms.domain.entity.comment;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;
@Embeddable
public class CommentStatus {
	

	/**
	 * 是否展示 1 展示 -1 不展示
	 */
	@Type(type = "yes_no")
	@Column(name = "STAT_IS_SHOW")
	private Boolean show;

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

}
