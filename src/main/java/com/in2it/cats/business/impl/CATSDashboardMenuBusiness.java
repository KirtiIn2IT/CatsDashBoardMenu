package com.in2it.cats.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.in2it.cats.business.pojo.CategoryBO;
import com.in2it.cats.business.pojo.CustomReportBO;
import com.in2it.cats.business.pojo.FilterParamBO;
import com.in2it.cats.business.pojo.ReportBO;
import com.in2it.cats.business.pojo.ReportFilterBO;
import com.in2it.cats.business.pojo.SubCategoryBO;
import com.in2it.cats.business.pojo.TabBO;
import com.in2it.cats.common.ApplicationConstants;
import com.in2it.cats.dao.impl.CATSDashboardMenuDAO;
import com.in2it.cats.dao.pojo.CATSDashboardMenuEntity;
import com.in2it.cats.dao.pojo.CustomReportEntity;
import com.in2it.cats.dao.pojo.FilterParamEntity;
import com.in2it.cats.dao.pojo.ReportFilterEntity;
import com.in2it.cats.dao.pojo.TabEntity;


@Component
public class CATSDashboardMenuBusiness {
	
	@Autowired
	CATSDashboardMenuDAO dao;
	
	@Value("#{'${incident.priority.List}'.split(',')}")
	List<String> priorityList;
	
	@Value("#{'${incident.status.List}'.split(',')}")
	List<String> statusList;
	
	@Value("#{'${order.List}'.split(',')}")
	List<String> orderList;
	
	public List<CategoryBO> getMenuOptionsForUser(String userId){
		
		List<CategoryBO> categoryList = null;
		CategoryBO category = null;
		SubCategoryBO subCategory = null;
		ReportBO report = null;
		List<ReportBO> reportList = null;
		List<SubCategoryBO> subCategoryList = null;
		int count = 0;
		
		String lastCategoryCode = null;
		String lastSubCategoryCode = null;		
		
		List<CATSDashboardMenuEntity> menuEntityList = dao.getCategoryList(userId);
		
		if(null != menuEntityList && !menuEntityList.isEmpty()) {
			categoryList = new ArrayList<>();
			
			for(CATSDashboardMenuEntity entity: menuEntityList) {
				if(0 == count) {
					category = new CategoryBO();
					category.setCategoryCode(entity.getCategoryTypeCode());
					category.setCategoryName(entity.getCategoryTypeName());
					category.setCategoryDescription(entity.getCategoryTypeDescription());
					lastCategoryCode = entity.getCategoryTypeCode();
					categoryList.add(category);
					
					subCategory = new SubCategoryBO();
					subCategory.setSubCategoryCode(entity.getCategoryCode());
					subCategory.setSubCategoryName(entity.getCategoryName());
					subCategory.setSubCategoryDescription(entity.getCategoryDescription());
					lastSubCategoryCode = entity.getCategoryCode();
					
					subCategoryList = new ArrayList<>();
					subCategoryList.add(subCategory);
					category.setSubCategories(subCategoryList);
					
					report = new ReportBO();
					report.setReportId(entity.getReportId());
					report.setReportName(entity.getReportName());
					report.setReportDescription(entity.getReportDescription());
					report.setReportTags(entity.getReportTags());
					report.setReportURL(entity.getApiURL());
					reportList= new ArrayList<>();
					reportList.add(report);
					subCategory.setReports(reportList);					
				}else {
					report = new ReportBO();
					report.setReportId(entity.getReportId());
					report.setReportName(entity.getReportName());
					report.setReportDescription(entity.getReportDescription());
					report.setReportTags(entity.getReportTags());
					report.setReportURL(entity.getApiURL());
					// If sub-category is same as last
					if(lastSubCategoryCode.equalsIgnoreCase(entity.getCategoryCode())) {
						subCategory.getReports().add(report);						
					}else {
						// Sub-category has changed
						subCategory = new SubCategoryBO();
						subCategory.setSubCategoryCode(entity.getCategoryCode());
						subCategory.setSubCategoryName(entity.getCategoryName());
						subCategory.setSubCategoryDescription(entity.getCategoryDescription());
						lastSubCategoryCode = entity.getCategoryCode();
						
						reportList= new ArrayList<>();
						reportList.add(report);
						subCategory.setReports(reportList);
						
						if(lastCategoryCode.equalsIgnoreCase(entity.getCategoryTypeCode())) {
							// Category is same but sub-category has changed							
							category.getSubCategories().add(subCategory);
						}else {
							// Both category and sub-category have changed
							category = new CategoryBO();
							category.setCategoryCode(entity.getCategoryTypeCode());
							category.setCategoryName(entity.getCategoryTypeName());
							category.setCategoryDescription(entity.getCategoryTypeDescription());
							lastCategoryCode = entity.getCategoryTypeCode();
							
							subCategoryList = new ArrayList<>();
							subCategoryList.add(subCategory);
							
							category.setSubCategories(subCategoryList);
							categoryList.add(category);
							
						}
					}
				}
				count++;
			}
		}
		
		return categoryList;
	}
	
	/**
	 * 
	 * @param userId
	 * @return {@link List} of {@link TabBO}
	 */
	public List<TabBO> fetchTabs(String userId){
		List<TabEntity> tabEntityList = dao.fetchTabs(userId);
		
		List<TabBO> tabBOList = null;
		TabBO tabBO = null;
		if(null != tabEntityList && !tabEntityList.isEmpty()){
			tabBOList = new ArrayList<>();
			for(TabEntity tabEntity: tabEntityList){
				tabBO = new TabBO();
				tabBO.setTabId(tabEntity.getTabId());
				tabBO.setTabName(tabEntity.getTabName());
				tabBOList.add(tabBO);
			}
		}
		
		return tabBOList;
	}	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Map<Long, String> getAssociatedCustomerList(String userId){
		return dao.getAssociatedCustomerList(userId);
	}
	
	/**
	 * Business layer implementation to fetch priority Id and values master list 
	 * @return {@link Map} of priorityId as key priority String as value
	 */
	public Map<Long, String> getPriorityList(){
		Map<Long, String> priorityMap = new HashMap<>();
		String[] aStrSplitted = null;
		for(String priority: priorityList){
			aStrSplitted = priority.split("=");
			priorityMap.put(Long.parseLong(aStrSplitted[0]), aStrSplitted[1]);
		}
		
		return priorityMap;
	}
	
	/**
	 * Business layer implementation to fetch status Id and values master list 
	 * @return
	 */
	public Map<Long, String> getStatusList(){
		Map<Long, String> statusMap = new HashMap<>();
		String[] aStrSplitted = null;
		for(String status: statusList){
			aStrSplitted = status.split("=");
			statusMap.put(Long.parseLong(aStrSplitted[0]), aStrSplitted[1]);
		}
		
		return statusMap;
	}
	
	/**
	 * Business layer implementation to fetch order Id and values master list 
	 * @return
	 */
	public Map<Long, String> getOrderValues(){
		Map<Long, String> statusMap = new HashMap<>();
		String[] aStrSplitted = null;
		for(String order: orderList){
			aStrSplitted = order.split("=");
			statusMap.put(Long.parseLong(aStrSplitted[0]), aStrSplitted[1]);
		}
		
		return statusMap;
	}
	
	/**
	 * 
	 * @param tabId
	 * @return
	 */
	public List<CustomReportBO> getReportListForTabId(long tabId){
		List<CustomReportBO> reportList = null;
		List<CustomReportEntity> reportEntityList = dao.getReportDataForTab(tabId);
		int count = 0;
		CustomReportBO reportBO = null;
		ReportFilterBO reportFilter = null;
		List<ReportFilterBO> reportFilterList = null;
		List<FilterParamBO> filterParamList = null;
		int lastReportId = -1;
		
		int index = -1;
		int nextReportIndex = -1;
		int rowCount = 0;
		boolean isNewReport = true;
		
		
		
		if (null != reportEntityList && !reportEntityList.isEmpty()) {
			for (CustomReportEntity reportEntity : reportEntityList) {
				boolean hasFilters = false;
				if(null != reportEntity.getFilterkey())
					hasFilters = true;
				if (0 == count) {
					reportBO = new CustomReportBO();
					reportList = new ArrayList<>();
					reportList.add(reportBO);
					
					lastReportId = reportEntity.getReportId();
					reportBO.setReportId(lastReportId);
					
					reportBO.setReportName(reportEntity.getReportName());
					reportBO.setReportDescription(reportEntity.getReportDescription());
					reportBO.setReportTags(reportEntity.getReportTags());
					reportBO.setReportURL(reportEntity.getReportUrl());
					reportBO.setColSpan(reportEntity.getColSpan());
					
					reportBO.setChartTypeId(reportEntity.getChartTypeId());
					reportBO.setChartType(reportEntity.getChartType());

					if(hasFilters) {
						reportFilter = new ReportFilterBO();
						reportFilterList = new ArrayList<>();
						reportFilter.setFilterKey(reportEntity.getFilterkey());
						reportFilter.setKeyDisplayName(reportEntity.getFilterKeyName());
						
						StringTokenizer tokenizer = new StringTokenizer(reportEntity.getCustomFilterValue(), ",");
						List<Long> filterValues = new ArrayList<>();
						while (tokenizer.hasMoreElements()) {
							filterValues.add(Long.parseLong((String) tokenizer.nextElement()));
						}
						
						filterParamList = getFilterParamMasterList(reportEntity.getFilterkey(),filterValues);
						reportFilter.setFilterParamList(filterParamList);
						
						reportFilterList.add(reportFilter);
						
						reportBO.setReportFilterList(reportFilterList);		
					}							
				}else if(0 < count){					
					if(lastReportId != reportEntity.getReportId()){
						reportBO = new CustomReportBO();
						isNewReport = true;
						reportList.add(reportBO);
						
						lastReportId = reportEntity.getReportId();
						reportBO.setReportId(lastReportId);
						
						reportBO.setReportName(reportEntity.getReportName());
						reportBO.setReportDescription(reportEntity.getReportDescription());
						reportBO.setReportTags(reportEntity.getReportTags());
						reportBO.setReportURL(reportEntity.getReportUrl());
						reportBO.setColSpan(reportEntity.getColSpan());						
					
						reportBO.setChartTypeId(reportEntity.getChartTypeId());
						reportBO.setChartType(reportEntity.getChartType());
						
						if(hasFilters) {
							reportFilter = new ReportFilterBO();
							reportFilterList = new ArrayList<>();

							reportFilter.setFilterKey(reportEntity.getFilterkey());
							reportFilter.setKeyDisplayName(reportEntity.getFilterKeyName());
							
							StringTokenizer tokenizer = new StringTokenizer(reportEntity.getCustomFilterValue(), ",");
							List<Long> filterValues = new ArrayList<>();
							while (tokenizer.hasMoreElements()) {
								filterValues.add(Long.parseLong((String) tokenizer.nextElement()));
							}
							
							filterParamList = getFilterParamMasterList(reportEntity.getFilterkey(),filterValues);
							reportFilter.setFilterParamList(filterParamList);
							
							reportFilterList.add(reportFilter);
							
							reportBO.setReportFilterList(reportFilterList);
						}						
					}else{
						isNewReport = false;
						
						// We have the last instantiated reportBO object
						reportFilterList = reportBO.getReportFilterList();
						reportFilter = new ReportFilterBO();
						
						reportFilter.setFilterKey(reportEntity.getFilterkey());
						reportFilter.setKeyDisplayName(reportEntity.getFilterKeyName());
						
						StringTokenizer tokenizer = new StringTokenizer(reportEntity.getCustomFilterValue(), ",");
						List<Long> filterValues = new ArrayList<>();
						while (tokenizer.hasMoreElements()) {
							filterValues.add(Long.parseLong((String) tokenizer.nextElement()));
						}
						
						filterParamList = getFilterParamMasterList(reportEntity.getFilterkey(),filterValues);
						reportFilter.setFilterParamList(filterParamList);
						
						reportFilterList.add(reportFilter);
					}
					
				}
				
				// Report Index and row calculation
				if(isNewReport){
					if( 0 == count){
						index = 0;
					}else{
						if(reportEntity.getColSpan() > 3*rowCount - nextReportIndex){
							index = 3*rowCount - nextReportIndex + 1;
						}else{
							index = nextReportIndex;
						}
					}
					
					nextReportIndex = index + reportEntity.getColSpan();
					rowCount = getRowCount(index);
					count++;
					
					reportBO.setReportIndex(index);
					reportBO.setRowCount(rowCount);
				}				
			}
		}
		
		return reportList;
	}
	
	/**
	 * Business layer implementation to fetch chart-type master data
	 * @return
	 */
	public Map<Integer, String> getChartTypeMasterData(){
		return dao.getChartTypeMaster();
	}
	
	/**
	 * This method use to save the value of a Tab in cats_user_tab_mapping table
	 * @param tab
	 * @param userId
	 */
	@Transactional
	public TabBO saveNewTab(TabBO tab,String userId) {
		int reportId = -1;;
		int colSpan;		
		List<ReportFilterEntity> filterKeyEntityList = null;
		List<FilterParamBO> filterParamBOList = null;
		long paramIdentifier = -1;
		String filterKey = null;
		//ReportFilterBO reportFilterBO = null;
		FilterParamEntity filterParamEntity = null;
		//List<ReportFilterBO> reportFilterBOList = null;
		long tabId = 0L;
		
		int maxParamIdentifier = dao.getMaxParamIdValue();
		List<FilterParamEntity> filterParamEntityList = new ArrayList<>();
			
		if (null != tab) {			
			// Insert data in tab table and create new tab for this user
			tabId = dao.saveTab(userId, tab.getTabName());		
			
			tab.setTabId(tabId);

			List<CustomReportBO> customReport = tab.getReportList();			
			
			int reportOrder = 1;
			for (CustomReportBO cReport : customReport) {				
				reportId = cReport.getReportId(); 
				colSpan = (cReport.getColSpan()); 
				
				// Get report filter key list
				filterKeyEntityList = dao.getFilterKeyListForReport(reportId);
								
				// In case there are no filter parameters for this report
				if(null != filterKeyEntityList && !filterKeyEntityList.isEmpty()){
					
					// Insert Report in custom_object_master table
					dao.saveReport(tabId, reportId, reportOrder, colSpan, userId, maxParamIdentifier);
					//reportFilterBOList = new ArrayList<>();
					//cReport.setReportFilterList(reportFilterBOList);
				
					for (ReportFilterEntity filterKeyEntity : filterKeyEntityList) {					
						filterKey = filterKeyEntity.getFilterKey();
						filterParamBOList = getFilterParamListForFilterKey(filterKey);
											
						filterParamEntity = new FilterParamEntity();
						
						filterParamEntity.setCreatedBy(userId);
						filterParamEntity.setKey(filterKey);
						filterParamEntity.setParamIdentifier(maxParamIdentifier);
						filterParamEntity.setValues(getFilterKeyValues(filterParamBOList));
						filterParamEntityList.add(filterParamEntity);						
					}
					maxParamIdentifier++;
				}else {
					dao.saveReport(tabId, reportId, reportOrder, colSpan, userId);
				}
				reportOrder++;
			}
			
			dao.saveCustomReportParam(filterParamEntityList);
			
			//tab.setReportList(getReportListForTabId(tabId));

		}
		return tab;
	}
	
	/**
	 * 
	 * @param userId
	 * @param tabId
	 * @return
	 */
	public String deleteTab(String userId,long tabId) {
		return dao.deleteTab(userId,tabId);
	}
	
	/**
	 * 
	 * @param tabBo
	 * @param userId
	 * @return
	 */	
	@Transactional
	public TabBO addUpdateReport(TabBO tabBo, String userId) {
		TabBO returnTab = new TabBO();
		
		List<ReportFilterEntity> filterKeyEntityList = null;
		List<FilterParamBO> filterParamBOList = null;
		String filterKey = null;
		FilterParamEntity filterParamEntity = null;
		List<FilterParamEntity> filterParamEntityList = new ArrayList<>();  // 
		Map<Long,String[]> paramIdKeysMap = null;
		Set<Long> paramIdSet = null;
		List<ReportFilterBO> reportFilterBOList = null; 
		String[] filterKeys =null;
		
		if(null!=tabBo) {
			returnTab.setTabName(tabBo.getTabName());
			returnTab.setTabId(tabBo.getTabId());
			long tabId = tabBo.getTabId();
			Map<Integer, Map<Long,String[]>> reportIdParamMap = dao.getReportListForTab(tabId);			
			List<CustomReportBO> customReportBoList = tabBo.getReportList();
			
			for(CustomReportBO cReportBo:customReportBoList){				
				if(cReportBo.getFlag()==1){
					// This is an existing report with updates required for parameters
					paramIdKeysMap = reportIdParamMap.get(cReportBo.getReportId());
					paramIdSet = paramIdKeysMap.keySet(); 
					reportFilterBOList = cReportBo.getReportFilterList(); 
					System.out.println("this is the value of flag "+cReportBo.getFlag());
					System.out.println("this is value of "+reportFilterBOList.size());
					
					for(Long paramId: paramIdSet ) {
					    filterKeys = paramIdKeysMap.get(paramId);
					    for(int i=0;i<filterKeys.length;i++) {
					    	String filterKeysDb = filterKeys[i];
					    	
					    	   for(ReportFilterBO reportFilterBo:reportFilterBOList) {
					    		   filterKey =  reportFilterBo.getFilterKey();
					    		   if(filterKeysDb.equals(filterKey)) {
					    			   String filterValuesId =null;
					   				StringBuffer filterValueId = new StringBuffer("");
					    			   List<FilterParamBO> filterParamList = reportFilterBo.getFilterParamList();
					    			   for(FilterParamBO filterParamBo:filterParamList) {
					    				   Number id=filterParamBo.getId();
					    				   boolean isSelected= filterParamBo.isSelected();
					    				   if(isSelected) {
					    					   filterValueId.append(id+",");
										    }
					    				 }
					    			   filterValuesId = filterValueId.toString();
					    			   if(filterValuesId.lastIndexOf(",") == filterValuesId.length()-1){
					    				   filterValuesId= filterValuesId.substring(0, filterValuesId.length()-1);
					    			    } 
					    			   dao.updateInCatsCustomReportParameter(filterValuesId,userId,paramId,filterKey);
					    		   }
					    	   }
					       }
						}
				}
				if(cReportBo.getFlag()==2) {
					// This is a new report on the existing tab
					int reportId = cReportBo.getReportId();
					long paramIdentifier = dao.saveReportInExistingTab(tabId, reportId,cReportBo.getColSpan(),
							userId);
					// Get report filter key list
					filterKeyEntityList = dao.getFilterKeyListForReport(reportId);
					if (null != filterKeyEntityList && !filterKeyEntityList.isEmpty()) {
						for (ReportFilterEntity filterKeyEntity : filterKeyEntityList) {
							filterKey = filterKeyEntity.getFilterKey();
							filterParamBOList = getFilterParamListForFilterKey(filterKey);
							filterParamEntity = new FilterParamEntity();
							filterParamEntity.setCreatedBy(userId);
							filterParamEntity.setKey(filterKey);
							filterParamEntity.setParamIdentifier(paramIdentifier);
							filterParamEntity.setValues(getFilterKeyValues(filterParamBOList));
							filterParamEntityList.add(filterParamEntity);
						}
					}
					dao.saveCustomReportParam(filterParamEntityList);
				}
				if (3 == cReportBo.getFlag()) {
					dao.deleteReport(tabId, cReportBo.getReportId());
				}
			}
		}		
		return returnTab;
	}
	/**
	 * 
	 * @param filterParamBOList
	 * @return
	 */
	private String getFilterKeyValues(List<FilterParamBO> filterParamBOList) {
		StringBuffer values = new StringBuffer("");
		int listSize = filterParamBOList.size();
		int count = 0;
		for(FilterParamBO filterParamBO: filterParamBOList) {
			values.append(filterParamBO.getId());
			if(count < listSize-1){
				values.append(",".intern());
			}
			count++;
			
		}		
		return values.toString();
	}
	
	private List<FilterParamBO> getFilterParamListForFilterKey(String filterKey){
		List<FilterParamBO> filterParamBOList =  new ArrayList<>();
		Map<? extends Number, String> paramMap = getFilterKeyValueMap(filterKey);
		Set<? extends Number> keySet = null;
		FilterParamBO filterParam = null;
		
		keySet = paramMap.keySet();
		if (null != keySet && !keySet.isEmpty()) {
			for (Number key : keySet) {
				filterParam = new FilterParamBO();
				filterParam.setId(key);
				filterParam.setParamValue(paramMap.get(key));
				filterParam.setSelected(true);				
				filterParamBOList.add(filterParam);
			}
		}		
		return filterParamBOList;		
	}
	
	/**
	 * 
	 * @param filterKey
	 * @param filterValues
	 * @param fetchDefault
	 *            - true if the filter values have to be fetched for default report,
	 *            else false
	 * @return
	 */
	private List<FilterParamBO> getFilterParamMasterList(String filterKey, List<Long> filterValues) {
		List<FilterParamBO> filterParamBOList = new ArrayList<>();
		Map<? extends Number, String> paramMap = null;
		FilterParamBO filterParam = null;
		Set<? extends Number> keySet = null;

		paramMap = getFilterKeyValueMap(filterKey);

		keySet = paramMap.keySet();
		if (null != keySet && !keySet.isEmpty()) {
			for (Number key : keySet) {
				filterParam = new FilterParamBO();
				filterParam.setId(key);
				filterParam.setParamValue(paramMap.get(key));
				
				if ((null == filterValues || filterValues.isEmpty())
						|| (null != filterValues && !filterValues.isEmpty() && filterValues.contains(key))) {
					filterParam.setSelected(true);
				}
				filterParamBOList.add(filterParam);
			}
		}

		return filterParamBOList;
	}
	
	private Map<? extends Number, String> getFilterKeyValueMap(String filterKey) {
		Map<? extends Number, String> paramMap = null;

		switch (filterKey) {
		case ApplicationConstants.FILTER_KEY_CUSTOMER:
			paramMap = getAssociatedCustomerList("java123");
			break;

		case ApplicationConstants.FILTER_KEY_PPRIORITY:
			paramMap = getPriorityList();
			break;

		case ApplicationConstants.FILTER_KEY_STATUS:
			paramMap = getStatusList();
			break;

		case ApplicationConstants.FILTER_KEY_CHART_TYPE:
			paramMap = getChartTypeMasterData();
			break;
			
		case ApplicationConstants.FILTER_KEY_ORDER_TYPE:
			paramMap = getOrderValues();
			break;

		default:
			break;
		}
		
		return paramMap;
	}
	
	/**
	 * 
	 * @param index
	 * @return rowCount
	 */
	private int getRowCount(int index) {
		int rowCount = 0;

		switch (index) {
		case 0:
		case 1:
		case 2:

			rowCount = 1;
			break;

		case 3:
		case 4:
		case 5:
			rowCount = 2;
			break;

		case 6:
		case 7:
		case 8:
			rowCount = 3;
			break;

		default:
			break;
		}
		
		return rowCount;
	}

	
}