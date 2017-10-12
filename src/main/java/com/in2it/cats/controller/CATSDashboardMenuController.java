package com.in2it.cats.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.in2it.cats.service.CATSDashboardMenuService;
import com.in2it.cats.service.pojo.Category;
import com.in2it.cats.service.pojo.Tab;
import com.in2it.cats.stub.reporting.CustomReport;


@Controller
@RequestMapping(value="/dashboard")
@CrossOrigin(origins="http://localhost:4200")
public class CATSDashboardMenuController {
	
	@Autowired
	CATSDashboardMenuService menuService;
		
	@RequestMapping(value="/getMenuInformation",method=RequestMethod.GET)
	@ResponseBody
	public List<Category> getMenuInformation(HttpServletRequest request){	
		String userId = request.getHeader("userId");
		return menuService.getMenuInformation(userId);				
	}
	
	@RequestMapping(value="/getTabs/{timeFrom}/{timeTo}/{timeZone}",method=RequestMethod.POST)
	@ResponseBody
	public List<Tab> getTabs(HttpServletRequest request, @PathVariable String timeFrom, @PathVariable String timeTo,
			@PathVariable String timeZone) {
		String userId = request.getHeader("userId");
		return menuService.getTabs(userId, timeFrom, timeTo, timeZone);
	}
	
	@RequestMapping(value="/getTabDetails/{tabId}/{timeFrom}/{timeTo}/{timeZone}",method=RequestMethod.POST)
	@ResponseBody
	public List<List<CustomReport>> getReportDetailsForTab(HttpServletRequest request, @PathVariable int tabId,
			@PathVariable String timeFrom, @PathVariable String timeTo, @PathVariable String timeZone){
		String userId = request.getHeader("userId");
		return menuService.getReportDetailsForTab(tabId,timeFrom,timeTo,timeZone,userId);
	}
	
	/**
	 * Get customer list for the input userId
	 * @param userId
	 * @return {@link Map} of customerCode and customerName
	 */
	@RequestMapping(value="/getCustomerListForUser",method=RequestMethod.POST)
	@ResponseBody
	public Map<Long, String> getCustomerList(HttpServletRequest request){
		String userId = request.getHeader("userId");
		return menuService.getAssociatedCustomerList(userId);
	}	
	
	@RequestMapping(value="/getPriorityList",method=RequestMethod.GET)
	@ResponseBody
	public Map<Long, String> getPriorityList(){
		return menuService.getPriorityList();
	}
	
	@RequestMapping(value="/getStatusList",method=RequestMethod.GET)
	@ResponseBody
	public Map<Long, String> getStatusList(){
		return menuService.getStatusList();
	}
	
	@RequestMapping(value="/getOrderValues",method=RequestMethod.GET)
	@ResponseBody
	public Map<Long, String> getOrderValues(){
		return menuService.getOrderValues();
	}
	
}
