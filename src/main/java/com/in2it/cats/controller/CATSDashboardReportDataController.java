package com.in2it.cats.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import com.in2it.cats.business.impl.CATSDashboardMenuBusiness;
import com.in2it.cats.controller.helper.SaveTabCustomDeserializer;
import com.in2it.cats.service.CATSDashboardMenuService;
import com.in2it.cats.service.CATSDashboardReportDataService;
import com.in2it.cats.service.pojo.Tab;
import com.in2it.cats.stub.reporting.CustomReport;

@RestController
@RequestMapping(value="/dashboard")
@CrossOrigin(origins="http://localhost:4200")
public class CATSDashboardReportDataController {
	@Autowired
	CATSDashboardMenuBusiness business;
	
	@Autowired
	CATSDashboardMenuService menuService;
	
	@Autowired
	CATSDashboardReportDataService reportDataService;

	/**
	 * This method will store
	 * 
	 * @param tab
	 * @return tab
	 */
	@RequestMapping(value = "/saveTabData/{timeFrom}/{timeTo}/{timeZone}")
	@ResponseBody
	public Tab saveNewTabData(@RequestParam String tab, @PathVariable String timeFrom, @PathVariable String timeTo, @PathVariable String timeZone, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("Tab JSON is::" + tab);
		Tab objTab = null;
		Tab returntab = null;
		try {
			SimpleModule module = new SimpleModule();
			module.addDeserializer(Tab.class, new SaveTabCustomDeserializer());
			mapper.registerModule(module);
			objTab = mapper.readValue(tab, Tab.class);
			String userId = request.getHeader("userId");
			
			returntab = reportDataService.saveTabData(objTab, userId, timeFrom, timeTo, timeZone);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returntab;
	}
	
	
	@RequestMapping(value="/deleteTab/{tabId}",method=RequestMethod.POST)
	@ResponseBody
	public String deleteTab(@PathVariable String tabId,HttpServletRequest request) {
		
		String message="There is some problem";
		String userId = request.getHeader("userId");
		if(null!=tabId&&null!=userId) {
			Long tabid=Long.parseLong(tabId);
		    message=reportDataService.deleteTab(userId, tabid);
		}
		return message;
	}
	
	@RequestMapping(value="/addUpdateReport/{timeFrom}/{timeTo}/{timeZone}", method=RequestMethod.POST)
	@ResponseBody
	public Tab addUpdateReport(@RequestParam String tab, @PathVariable String timeFrom, @PathVariable String timeTo, @PathVariable String timeZone, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		Tab objTab =null;
		System.out.println(tab);
		String userId = request.getHeader("userId");
		
	/* 	try {
			SimpleModule module=new SimpleModule();
			module.addDeserializer(Tab.class, new SaveTabCustomDeserializer());
			mapper.registerModule(module);
			objTab= mapper.readValue(tab, Tab.class);
			objTab = reportDataService.saveUpdateNewReport(objTab,userId);
			
			
			
		}catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    */
		
		Gson gson = new Gson();
		objTab = gson.fromJson(tab, Tab.class);
		System.out.println("this is tab : "+objTab);
		System.out.println("Tab is "+objTab.getTabId());
		System.out.println("Tab Name "+objTab.getTabName());
        List<List<CustomReport>> tabReports = objTab.getTabReports();
         System.out.println("before converter ");
         for(List<CustomReport> cReportList:tabReports) {
        	 for(CustomReport cReport:cReportList) {
        		 System.out.println(cReport.getReportId());
        		 System.out.println(cReport.getFlag());
        	 }
         }
		objTab = reportDataService.saveUpdateNewReport(objTab,userId,timeFrom, timeTo, timeZone);
		return objTab;
	}
	
	
	
}