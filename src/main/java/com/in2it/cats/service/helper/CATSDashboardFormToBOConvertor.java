package com.in2it.cats.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.in2it.cats.business.pojo.CustomReportBO;
import com.in2it.cats.business.pojo.FilterParamBO;
import com.in2it.cats.business.pojo.ReportFilterBO;
import com.in2it.cats.business.pojo.TabBO;
import com.in2it.cats.service.pojo.Tab;
import com.in2it.cats.stub.reporting.CustomReport;
import com.in2it.cats.stub.reporting.FilterParam;
import com.in2it.cats.stub.reporting.ReportFilter;

public class CATSDashboardFormToBOConvertor {
	public static List<CustomReportBO> convertCustomReportListFormToBO(List<List<CustomReport>> reportList){
		List<CustomReportBO> customReportBOList = new ArrayList<>();
		CustomReportBO reportBO = null;
		
		for(List<CustomReport> rowList: reportList){
			for(CustomReport report: rowList){
				reportBO = new CustomReportBO();
				System.out.println("Ïnside Convertor::Conversion for report Id::"+report.getReportId());
				reportBO.setReportId(report.getReportId());
				reportBO.setReportName(report.getReportName());
				reportBO.setReportDescription(report.getReportDescription());
				reportBO.setReportTags(report.getReportTags());
				reportBO.setReportURL(report.getReportURL());
				reportBO.setColSpan(report.getColSpan()/4);
				reportBO.setFlag(report.getFlag());
				
				List<ReportFilterBO> reportFilterBOList = null;
				List<ReportFilter> reportFilterList = report.getReportFilterList();
				if(null != reportFilterList && !reportFilterList.isEmpty()) {
					reportFilterBOList = new ArrayList<>();
					
					for(ReportFilter reportFilter: reportFilterList) {
						reportFilterBOList.add(convertReportFilterFormToBO(reportFilter));
					}
					reportBO.setReportFilterList(reportFilterBOList);
				}				
				customReportBOList.add(reportBO);
			}
		}		
		return customReportBOList;
	}
	
	public static ReportFilterBO convertReportFilterFormToBO(ReportFilter reportFilter) {
		ReportFilterBO reportFilterBO = new ReportFilterBO();

		reportFilterBO.setFilterKey(reportFilter.getFilterKey());
		reportFilterBO.setKeyDisplayName(reportFilter.getKeyDisplayName());

		List<FilterParamBO> filterParamBOList = null;
		List<FilterParam> filterParamList = reportFilter.getFilterValues();
		if (null != filterParamList && !filterParamList.isEmpty()) {
			filterParamBOList = new ArrayList<>();
			for (FilterParam filterParam : reportFilter.getFilterValues()) {
				filterParamBOList.add(convertFilterParamFormToBO(filterParam));
			}
			
			reportFilterBO.setFilterParamList(filterParamBOList);
		}
		return reportFilterBO;
	}
	
	public static final FilterParamBO convertFilterParamFormToBO(FilterParam filterParam) {
		FilterParamBO filterParamBO = new FilterParamBO();		
		filterParamBO.setId(filterParam.getId());
		filterParamBO.setParamValue(filterParam.getParamValue());
		filterParamBO.setSelected(filterParam.isSelected());		
		return filterParamBO;		
	}
	
	public static TabBO convertTabFormToBO(Tab tab){
		TabBO tabBO = new TabBO();
		
		tabBO.setTabId(tab.getTabId());
		tabBO.setTabName(tab.getTabName());
		tabBO.setReportList(convertCustomReportListFormToBO(tab.getTabReports()));
		
		return tabBO;
	}
}
