package com.in2it.cats.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.in2it.cats.dao.pojo.CATSDashboardMenuEntity;

public class CATSDashboardMenuRowMapper implements RowMapper<CATSDashboardMenuEntity>{

	@Override
	public CATSDashboardMenuEntity mapRow(ResultSet rs, int arg1) throws SQLException {
		CATSDashboardMenuEntity dashboardMenu = new CATSDashboardMenuEntity();
		dashboardMenu.setCategoryTypeCode(rs.getString("category_code"));
		dashboardMenu.setCategoryTypeDescription(rs.getString("category_description"));
		dashboardMenu.setCategoryTypeName(rs.getString("category_name"));
		dashboardMenu.setCategoryCode(rs.getString("subCategory_Code"));
		dashboardMenu.setCategoryName(rs.getString("subcategory_name"));
		dashboardMenu.setReportId(rs.getInt("report_id"));
		dashboardMenu.setReportName(rs.getString("report_name"));
		dashboardMenu.setReportDescription(verifyStringValue(rs.getString("report_description")));
		dashboardMenu.setReportTags(verifyStringValue(rs.getString("report_description")));
		dashboardMenu.setDataFetchId(rs.getInt("DATA_FETCH_ID"));
		dashboardMenu.setApiURL(rs.getString("api_url"));
		dashboardMenu.setApiName(rs.getString("api_name"));
		return dashboardMenu;	
	}
	
	private String verifyStringValue(String value) {
		return null == value?"":value;
	}

}
