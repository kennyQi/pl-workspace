package hg.system.model.dto;

import java.io.Serializable;
import org.apache.commons.collections.KeyValue;

public class Attr implements KeyValue, Serializable {

	private static final long serialVersionUID = -6969400281629759587L;

	private Object key;
	private Object value;

	public Attr(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public Object getKey() {
		return key;
	}

	@Override
	public Object getValue() {
		return value;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
