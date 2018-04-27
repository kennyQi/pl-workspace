package hg.demo.member.common.domain.result;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class BaseDomain implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8738928713888445025L;

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
