package com.in2it.cats.business.pojo;

import java.util.ArrayList;
import java.util.List;

public class ReportFilterBO {
	private String filterKey;

	private String keyDisplayName;

	private List<FilterParamBO> filterParamList = null;

	/**
	 * @return the filterKey
	 */
	public String getFilterKey() {
		return filterKey;
	}

	/**
	 * @param filterKey the filterKey to set
	 */
	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}

	/**
	 * @return the keyDisplayName
	 */
	public String getKeyDisplayName() {
		return keyDisplayName;
	}

	/**
	 * @param keyDisplayName the keyDisplayName to set
	 */
	public void setKeyDisplayName(String keyDisplayName) {
		this.keyDisplayName = keyDisplayName;
	}

	/**
	 * @return the filterParamList
	 */
	public List<FilterParamBO> getFilterParamList() {
		return filterParamList;
	}

	/**
	 * @param filterParamList the filterParamList to set
	 */
	public void setFilterParamList(List<FilterParamBO> filterParamList) {
		this.filterParamList = filterParamList;
	}		
}
