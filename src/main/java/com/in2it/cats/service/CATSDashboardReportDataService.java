package com.in2it.cats.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in2it.cats.business.impl.CATSDashboardMenuBusiness;
import com.in2it.cats.business.pojo.TabBO;
import com.in2it.cats.service.helper.CATSDashboardFormToBOConvertor;
import com.in2it.cats.service.pojo.Tab;
import com.in2it.cats.stub.reporting.CustomReport;

@Service
public class CATSDashboardReportDataService {

	@Autowired
	CATSDashboardMenuBusiness business;
	
	@Autowired
	CATSDashboardMenuService menuService;
	
	/**
	 * 
	 * @param tab
	 * @param userId
	 * @param timeFrom
	 * @param timeTo
	 * @param timeZone
	 * @return
	 */
	public Tab saveTabData(Tab tab, String userId,String timeFrom, String timeTo, String timeZone){
		TabBO tabBO = business.saveNewTab(CATSDashboardFormToBOConvertor.convertTabFormToBO(tab), userId);
		
		List<List<CustomReport>> customReportList = menuService.getReportDetailsForTab(tabBO.getTabId(), timeFrom, timeTo, timeZone, userId);
		
		Tab returntab = new Tab();
		returntab.setTabId(tabBO.getTabId());
		returntab.setTabName(tabBO.getTabName());
		returntab.setTabReports(customReportList);
		return returntab;
	}
	
  /**
   * 
   * @param userId
   * @param tabId
   * @return
   */
	public String deleteTab(String userId,long tabId) {
		return business.deleteTab(userId, tabId);
	}
	
	
	/*
	 * 
	 */
	
	public Tab saveUpdateNewReport(Tab objTab, String userId, String timeFrom, String timeTo, String timeZone) {
		TabBO tabBo = business.addUpdateReport(CATSDashboardFormToBOConvertor.convertTabFormToBO(objTab), userId);
		/*
		 * String timeTo="1501041518120"; 
		 * String timeFrom="1500984300000";
		 */

		List<List<CustomReport>> customReportList = menuService.getReportDetailsForTab(tabBo.getTabId(), timeFrom,
				timeTo, timeZone, userId);
		Tab returntab = new Tab();
		returntab.setTabId(tabBo.getTabId());
		returntab.setTabName(tabBo.getTabName());
		returntab.setTabReports(customReportList);

		return returntab;
	}
}
