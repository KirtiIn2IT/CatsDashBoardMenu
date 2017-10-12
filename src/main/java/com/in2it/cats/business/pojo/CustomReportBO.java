package com.in2it.cats.business.pojo;

import java.util.List;

public class CustomReportBO extends ReportBO {
	private int colSpan;
	
	private List<ReportFilterBO> reportFilterList;
	
	private int reportIndex = -1;
	
	private int rowCount = -1;	
	
	private String chartType;
	
	private int chartTypeId;
	
	private short flag;
	
	/**
	 * @return the colSpan
	 */
	public int getColSpan() {
		return colSpan;
	}

	/**
	 * @param colSpan the colSpan to set
	 */
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	/**
	 * @return the reportFilterList
	 */
	public List<ReportFilterBO> getReportFilterList() {
		return reportFilterList;
	}

	/**
	 * @param reportFilterList the reportFilterList to set
	 */
	public void setReportFilterList(List<ReportFilterBO> reportFilterList) {
		this.reportFilterList = reportFilterList;
	}

	/**
	 * @return the reportIndex
	 */
	public int getReportIndex() {
		return reportIndex;
	}

	/**
	 * @param reportIndex the reportIndex to set
	 */
	public void setReportIndex(int reportIndex) {
		this.reportIndex = reportIndex;
	}

	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the chartType
	 */
	public String getChartType() {
		return chartType;
	}

	/**
	 * @param chartType the chartType to set
	 */
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	/**
	 * @return the chartTypeId
	 */
	public int getChartTypeId() {
		return chartTypeId;
	}

	/**
	 * @param chartTypeId the chartTypeId to set
	 */
	public void setChartTypeId(int chartTypeId) {
		this.chartTypeId = chartTypeId;
	}

	/**
	 * @return the flag
	 */
	public short getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(short flag) {
		this.flag = flag;
	}

	

	
}