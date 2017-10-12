package com.in2it.cats.service.pojo;

import java.util.ArrayList;
import java.util.List;

import com.in2it.cats.stub.reporting.CustomReport;

public class Tab {
	private long tabId;
	
	private String tabName;
	
	private List<List<CustomReport>> tabReports = new ArrayList<>();

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
	 * @return the tabReports
	 */
	public List<List<CustomReport>> getTabReports() {
		return tabReports;
	}

	/**
	 * @param tabReports the tabReports to set
	 */
	public void setTabReports(List<List<CustomReport>> tabReports) {
		this.tabReports = tabReports;
	}	
}