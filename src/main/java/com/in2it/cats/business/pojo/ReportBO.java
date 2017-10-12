package com.in2it.cats.business.pojo;

/**
 * This class is a place-holder for a reports meta-data. The actual data to be
 * displayed on report will be fetched by calling the API defined by reportURL.
 * 
 * @author In2IT Technologies
 * @version 1.0
 * @since v1.0
 *
 */
public class ReportBO {
	/**
	 * Report Id
	 */
	private int reportId;
	/**
	 * Report Name
	 */
	private String reportName;

	/**
	 * Description about this report
	 */
	private String reportDescription;

	/**
	 * 
	 */
	private String reportTags;

	/**
	 * API URL from where the data for this report will be fetched
	 */
	private String reportURL;

	/**
	 * @return the reportId
	 */
	public int getReportId() {
		return reportId;
	}

	/**
	 * @param reportId
	 *            the reportId to set
	 */
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName
	 *            the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the reportDescription
	 */
	public String getReportDescription() {
		return reportDescription;
	}

	/**
	 * @param reportDescription
	 *            the reportDescription to set
	 */
	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

	/**
	 * @return the reportTags
	 */
	public String getReportTags() {
		return reportTags;
	}

	/**
	 * @param reportTags
	 *            the reportTags to set
	 */
	public void setReportTags(String reportTags) {
		this.reportTags = reportTags;
	}

	/**
	 * @return the reportURL
	 */
	public String getReportURL() {
		return reportURL;
	}

	/**
	 * @param reportURL
	 *            the reportURL to set
	 */
	public void setReportURL(String reportURL) {
		this.reportURL = reportURL;
	}

}
