package com.in2it.cats.dao.pojo;

public class CustomReportEntity extends ReportEntity {
	private int colSpan;
	
	private String customFilterValue;
	
	private int chartTypeId;
	
	private String chartType;

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
	 * @return the customFilterValue
	 */
	public String getCustomFilterValue() {
		return customFilterValue;
	}

	/**
	 * @param customFilterValue the customFilterValue to set
	 */
	public void setCustomFilterValue(String customFilterValue) {
		this.customFilterValue = customFilterValue;
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
}
