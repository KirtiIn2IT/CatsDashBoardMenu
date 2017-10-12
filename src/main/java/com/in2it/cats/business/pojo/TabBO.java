package com.in2it.cats.business.pojo;

import java.util.List;

public class TabBO {
	private long tabId;
	
	private String tabName;
	
	private List<CustomReportBO> reportList = null;	

	/**
	 * @return the tabId
	 */
	public long getTabId() {
		return tabId;
	}

	/**
	 * @param tabId the tabId to set
	 */
	public void setTabId(long tabId) {
		this.tabId = tabId;
	}

	/**
	 * @return the tabName
	 */
	public String getTabName() {
		return tabName;
	}

	/**
	 * @param tabName the tabName to set
	 */
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	/**
	 * @return the reportList
	 */
	public List<CustomReportBO> getReportList() {
		return reportList;
	}

	/**
	 * @param reportList the reportList to set
	 */
	public void setReportList(List<CustomReportBO> reportList) {
		this.reportList = reportList;
	}
}
