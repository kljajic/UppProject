package com.process.model;

import java.io.Serializable;
import java.util.Map;

public class TaskProperty implements Serializable {

	private static final long serialVersionUID = -6770084097660394789L;
	
	private String id;
	private String name;
	private String value;
	private Boolean readable;
	private Boolean writable;
	private Boolean required;
	private String type;
	private Map<String, String> values;
	
	public TaskProperty() {
	}
	
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getReadable() {
		return readable;
	}

	public void setReadable(Boolean readable) {
		this.readable = readable;
	}

	public Boolean getWritable() {
		return writable;
	}

	public void setWritable(Boolean writable) {
		this.writable = writable;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}
}
