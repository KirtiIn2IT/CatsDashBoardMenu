package com.in2it.cats.service.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.in2it.cats.business.pojo.CustomReportBO;
import com.in2it.cats.business.pojo.FilterParamBO;
import com.in2it.cats.business.pojo.ReportFilterBO;
import com.in2it.cats.stub.reporting.CustomReport;
import com.in2it.cats.stub.reporting.FilterParam;
import com.in2it.cats.stub.reporting.ReportFilter;

@Component
public class CATSDashboardBOToFormConvertor {
	public static List<List<CustomReport>> convertCustomReportBOToForm(List<CustomReportBO> reportBOList) {
		CustomReport custRep = null;
		List<ReportFilterBO> reportFilterBOList = null;
		List<ReportFilter> reportFilterList = null;
		ReportFilter reportFilter = null;

		List<FilterParamBO> filterParamBOList = null;
		List<FilterParam> filterParamList = null;
		FilterParam filterParam = null;
		int lastRowCount = 1;
		List<List<CustomReport>> rowList = null;

		if (null != reportBOList && !reportBOList.isEmpty()) {
			rowList = new ArrayList<>();

			List<CustomReport> custRepList = new ArrayList<>();
			rowList.add(custRepList);

			for (CustomReportBO reportBO : reportBOList) {
				custRep = new CustomReport();
				custRep.setReportId(reportBO.getReportId());
				custRep.setReportName(reportBO.getReportName());
				custRep.setReportDescription(reportBO.getReportDescription());
				custRep.setReportTags(reportBO.getReportTags());
				custRep.setReportURL(reportBO.getReportURL());
				custRep.setColSpan(reportBO.getColSpan() * 4);
				custRep.setGraphType(reportBO.getChartType());

				reportFilterBOList = reportBO.getReportFilterList();
				if (null != reportFilterBOList && !reportFilterBOList.isEmpty()) {
					reportFilterList = new ArrayList<>();
					for (ReportFilterBO reportFilterBO : reportFilterBOList) {
						reportFilter = new ReportFilter();

						reportFilter.setFilterKey(reportFilterBO.getFilterKey());
						reportFilter.setKeyDisplayName(reportFilterBO.getKeyDisplayName());

						filterParamBOList = reportFilterBO.getFilterParamList();

						if (null != filterParamBOList && !filterParamBOList.isEmpty()) {
							filterParamList = new ArrayList<>();
							for (FilterParamBO filterParamBO : filterParamBOList) {
								filterParam = new FilterParam();
								filterParam.setId(filterParamBO.getId());
								filterParam.setParamValue(filterParamBO.getParamValue());
								filterParam.setSelected(filterParamBO.isSelected());
								filterParamList.add(filterParam);
							}

							reportFilter.setFilterValues(filterParamList);
						}

						reportFilterList.add(reportFilter);
					}

					custRep.setReportFilterList(reportFilterList);
				}

				if (lastRowCount == reportBO.getRowCount()) {
					custRepList.add(custRep);
				} else if (lastRowCount < reportBO.getRowCount()) {
					custRepList = new ArrayList<>();
					rowList.add(custRepList);
					custRepList.add(custRep);
				}

				lastRowCount = reportBO.getRowCount();

			}

		}
		return rowList;
	}

}
