package com.in2it.cats.dao.pojo;

import java.util.List;

/**
 * 
 * @author In2IT Technologies
 * @version 1.0
 * @since v1.0
 *
 */
public class SubCategoryEntity {
	private String categoryCode;
	
	private String categoryTypeCode;
	
	private String categoryName;
	
	private String categoryDescription;
	
	private boolean isActive;
	
	private List<ReportEntity> reports;

	/**
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param categoryCode the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * @return the categoryTypeCode
	 */
	public String getCategoryTypeCode() {
		return categoryTypeCode;
	}

	/**
	 * @param categoryTypeCode the categoryTypeCode to set
	 */
	public void setCategoryTypeCode(String categoryTypeCode) {
		this.categoryTypeCode = categoryTypeCode;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the categoryDescription
	 */
	public String getCategoryDescription() {
		return categoryDescription;
	}

	/**
	 * @param categoryDescription the categoryDescription to set
	 */
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the reports
	 */
	public List<ReportEntity> getReports() {
		return reports;
	}

	/**
	 * @param reports the reports to set
	 */
	public void setReports(List<ReportEntity> reports) {
		this.reports = reports;
	}
}
