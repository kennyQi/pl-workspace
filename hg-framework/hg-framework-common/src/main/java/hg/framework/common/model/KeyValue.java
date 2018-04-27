package hg.framework.common.model;

import hg.framework.common.base.BaseObject;

import java.io.Serializable;

/**
 * 键值对
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public class KeyValue<K, V> extends BaseObject implements Serializable {

	private K key;
	private V value;

	public KeyValue() {
	}

	public KeyValue(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
