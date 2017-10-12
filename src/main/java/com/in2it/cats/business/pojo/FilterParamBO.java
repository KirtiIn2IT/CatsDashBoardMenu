package com.in2it.cats.business.pojo;

public class FilterParamBO {
	private Number id;
	
	private String paramValue;
	
	private boolean isSelected = false;

	/**
	 * @return the id
	 */
	public Number getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Number id) {
		this.id = id;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
