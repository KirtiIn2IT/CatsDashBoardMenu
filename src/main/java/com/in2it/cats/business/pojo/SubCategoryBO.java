package com.in2it.cats.business.pojo;

import java.util.List;

/**
 * This class contains the sub-category information.
 * 
 * @author In2IT Technologies
 * @version 1.0
 * @since v1.0
 *
 */
public class SubCategoryBO {
	private String subCategoryCode;
	
	/**
	 * Sub-category name
	 */
	private String subCategoryName;

	/**
	 * sub-category description
	 */
	private String subCategoryDescription;

	/**
	 * Reports to be displayed as a part of this sub-category
	 */
	private List<ReportBO> reports;

	/**
	 * @return the subCategoryName
	 */
	public String getSubCategoryName() {
		return subCategoryName;
	}

	/**
	 * @param subCategoryName the subCategoryName to set
	 */
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	/**
	 * @return the subCategoryDescription
	 */
	public String getSubCategoryDescription() {
		return subCategoryDescription;
	}

	/**
	 * @param subCategoryDescription the subCategoryDescription to set
	 */
	public void setSubCategoryDescription(String subCategoryDescription) {
		this.subCategoryDescription = subCategoryDescription;
	}

	/**
	 * @return the reports
	 */
	public List<ReportBO> getReports() {
		return reports;
	}

	/**
	 * @param reports the reports to set
	 */
	public void setReports(List<ReportBO> reports) {
		this.reports = reports;
	}

	/**
	 * @return the subCategoryCode
	 */
	public String getSubCategoryCode() {
		return subCategoryCode;
	}

	/**
	 * @param subCategoryCode the subCategoryCode to set
	 */
	public void setSubCategoryCode(String subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
	}

}
