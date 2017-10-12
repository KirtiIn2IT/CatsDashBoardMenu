package com.in2it.cats.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in2it.cats.business.impl.CATSDashboardMenuBusiness;
import com.in2it.cats.business.pojo.CategoryBO;
import com.in2it.cats.business.pojo.CustomReportBO;
import com.in2it.cats.business.pojo.ReportBO;
import com.in2it.cats.business.pojo.SubCategoryBO;
import com.in2it.cats.business.pojo.TabBO;
import com.in2it.cats.service.helper.CATSDashboardBOToFormConvertor;
import com.in2it.cats.service.pojo.Category;
import com.in2it.cats.service.pojo.SubCategory;
import com.in2it.cats.service.pojo.Tab;
import com.in2it.cats.stub.reporting.ChartData;
import com.in2it.cats.stub.reporting.CustomReport;
import com.in2it.cats.stub.reporting.Report;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Service
public class CATSDashboardMenuService {
	@Autowired
	CATSDashboardMenuBusiness business;
	
	@Value("${kpi.reporting.url}")
	String kpiReportingURL;
	
	/**
	 * Service layer implementation for getMenuInformation
	 * @return {@link List} of {@link Category}
	 */
	public List<Category> getMenuInformation(String userId){			
		List<Category> categoryList = null;
		Category category = null;
		SubCategory subCategory = null;
		Report report = null;
		List<CategoryBO> categoryBOList = business.getMenuOptionsForUser(userId);
		List<SubCategoryBO> subCategoryBOList = null;
		List<ReportBO> reportBOList = null;
		List<Report> reportList = null;
		List<SubCategory> subCategoryList = null;
				
		if(null != categoryBOList && !categoryBOList.isEmpty()){
			categoryList = new ArrayList<>();
			
			for(CategoryBO categoryBO:categoryBOList){
				category = new Category();
				
				category.setCategoryCode(categoryBO.getCategoryCode());
				category.setCategoryDescription(categoryBO.getCategoryDescription());
				category.setCategoryName(categoryBO.getCategoryName());
				subCategoryBOList = categoryBO.getSubCategories();
				subCategoryList = new ArrayList<>();
				for(SubCategoryBO subCategoryBO: subCategoryBOList){
					subCategory = new SubCategory();
					
					subCategory.setSubCategoryCode(subCategoryBO.getSubCategoryCode());
					subCategory.setSubCategoryDescription(subCategoryBO.getSubCategoryDescription());
					subCategory.setSubCategoryName(subCategoryBO.getSubCategoryName());
					reportBOList = subCategoryBO.getReports();
					reportList = new ArrayList<>();
					for(ReportBO reportBO: reportBOList){
						report = new Report();
						report.setReportId(reportBO.getReportId());
						report.setReportDescription(reportBO.getReportDescription());
						report.setReportName(reportBO.getReportName());
						report.setReportTags(reportBO.getReportTags());
						report.setReportURL(reportBO.getReportURL());
						reportList.add(report);
					}
					subCategory.setReports(reportList);
					subCategoryList.add(subCategory);
				}
				
				category.setSubCategories(subCategoryList);
				categoryList.add(category);
			}
		}
		
		return categoryList;
				
	}
	
	/**
	 * Service layer implementation for getTabs for the userId
	 * @param userId
	 * @return {@link List} of {@link Tab}
	 */
	public List<Tab> getTabs(String userId, String timeFrom, String timeTo, String timeZone){
		System.out.println("with in /getTabs");
		List<TabBO> tabBOList = business.fetchTabs(userId);
		Tab tab = null;
		List<Tab> tabList = null;
		int tabCount = 0;
		if(null != tabBOList && !tabBOList.isEmpty()){
			tabList = new ArrayList<>();
			
			for(TabBO tabBO: tabBOList){
				tab = new Tab();
				tab.setTabId(tabBO.getTabId());
				tab.setTabName(tabBO.getTabName());
				if(0 == tabCount){
					tab.setTabReports(getReportDetailsForTab(tab.getTabId(), timeFrom, timeTo, timeZone, userId));;
				}
				
				tabList.add(tab);
				tabCount++;
			}
		}
		
		return tabList;
	}
	
	/**
	 * Service layer implementation for getReportDetailsForTab
	 * @param tabId
	 * @return {@link List} of row {@link List} of {@link CustomReport}
	 */
	public List<List<CustomReport>> getReportDetailsForTab(long tabId, String timeFrom, String timeTo, String timeZone,
			String userId) {
		List<List<CustomReport>> customReportList = null;
		// List<CustomReport> rowList = null;
		ChartData chartData = null;
		try {
			List<CustomReportBO> reportBOList = business.getReportListForTabId(tabId);
			customReportList = CATSDashboardBOToFormConvertor
					.convertCustomReportBOToForm(reportBOList);

			if (null != customReportList && !customReportList.isEmpty()) {
				for (List<CustomReport> rowList : customReportList) {
					for (CustomReport report : rowList) {
						chartData = callToGetReportData(timeFrom, timeTo, timeZone, userId, report);
						report.setReportData(chartData);
					}
				}
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customReportList;
	}
	
	/**
	 * Get customer list for the input userId
	 * @param userId
	 * @return {@link Map} of customerCode and customerName
	 */
	public Map<Long, String> getAssociatedCustomerList(String userId){
		return business.getAssociatedCustomerList(userId);
	}	
	
	/**
	 * Get master priority list
	 * @return {@link Map} of priorityId as key and Priority name as value
	 */
	public Map<Long, String> getPriorityList(){
		return business.getPriorityList();
	}
	
	/**
	 * Get master status list
	 * @return {@link Map} of statusId as key and status name as value
	 */
	public Map<Long, String> getStatusList(){
		return business.getStatusList();
	}	
	
	/**
	 * 
	 * @return
	 */
	public Map<Long, String> getOrderValues() {
		return business.getOrderValues();
	}

	private ChartData callToGetReportData(String timeFrom, String timeTo, String timeZone, String userId,
			CustomReport report) throws JsonProcessingException {
		String reportURL = report.getReportURL();
		Client restClient = Client.create();
		
		ObjectMapper mapper = new ObjectMapper();
		String strReport = mapper.writeValueAsString(report);
		
		System.out.println("Calling URL "+reportURL +" for report::\n"+strReport);
		
		StringBuffer urlBuffer = new StringBuffer(kpiReportingURL);
		urlBuffer.append(reportURL.trim());
		
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		try {
			params.add("report", URLEncoder.encode(strReport, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		params.add("userId", userId); 
		params.add("timeFrom", timeFrom); 
		params.add("timeTo", timeTo); 
		params.add("timeZone", timeZone); 
		WebResource webResource = restClient.resource(urlBuffer.toString()).queryParams(params);
		ClientResponse response = webResource.accept("application/json").type(MediaType.APPLICATION_JSON)
				.header("Content-Type","application/json;charset=UTF-8")
		        .header("cache-control", "no-cache")
	            .post(ClientResponse.class);
		
		//TODO: Complete this error handling logic
		/*if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
				+ response.getStatus());
			   return "Error";
		}*/
		String chartData = response.getEntity(String.class);
		System.out.println(chartData);
		 
		ChartData output = null;
		try {
			output = mapper.readValue(chartData, ChartData.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
}
