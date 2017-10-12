package com.in2it.cats.dao.pojo;

public class FilterParamEntity {
	private long paramIdentifier;
	
	private String key;
	
	private String values;
	
	private String createdBy;

	/**
	 * @return the paramIdentifier
	 */
	public long getParamIdentifier() {
		return paramIdentifier;
	}

	/**
	 * @param paramIdentifier the paramIdentifier to set
	 */
	public void setParamIdentifier(long paramIdentifier) {
		this.paramIdentifier = paramIdentifier;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the values
	 */
	public String getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(String values) {
		this.values = values;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
