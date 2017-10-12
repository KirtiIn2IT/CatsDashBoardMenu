package com.in2it.cats.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.in2it.cats.dao.constants.SQLConstants;
import com.in2it.cats.dao.pojo.CATSDashboardMenuEntity;
import com.in2it.cats.dao.pojo.CustomReportEntity;
import com.in2it.cats.dao.pojo.FilterParamEntity;
import com.in2it.cats.dao.pojo.ReportFilterEntity;
import com.in2it.cats.dao.pojo.TabEntity;
import com.in2it.cats.dao.rowMapper.CATSDashboardMenuRowMapper;

@Component
public class CATSDashboardMenuDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * This method gets the left navigation menu info for the input userId
	 */
	public List<CATSDashboardMenuEntity> getCategoryList(final String userId) {
		String sqlQuery = SQLConstants.GET_ROLE_BASED_CATEGORY_INFO;
		List<CATSDashboardMenuEntity> menuEntityList = null;

		try {
			menuEntityList = jdbcTemplate.query(sqlQuery, new Object[] { userId }, new CATSDashboardMenuRowMapper());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return menuEntityList;
	}

	/**
	 * This method fetches list of tabs for the input userId
	 * 
	 * @param userId
	 * @return {@link List} of {@link TabEntity}
	 */
	public List<TabEntity> fetchTabs(final String userId) {
		return jdbcTemplate.query(SQLConstants.FETCH_TABS_FOR_USER, new Object[] { userId },
				new RowMapper<TabEntity>() {

					@Override
					public TabEntity mapRow(ResultSet rs, int arg1) throws SQLException {
						TabEntity tab = new TabEntity();
						tab.setTabId(rs.getInt("tab_id"));
						tab.setTabName(rs.getString("tab_name"));
						return tab;
					}
				});
	}

	/**
	 * DAO implementation for fetching the associated customer details for the input
	 * userId
	 * 
	 * @param userId
	 * @return {@link Map} of associated customers with key as Customer Code and
	 *         value as Customer Name
	 */
	public Map<Long, String> getAssociatedCustomerList(final String userId) {
		return jdbcTemplate.query(SQLConstants.FETCH_ASSOCIATED_CUSTOMER_LIST,
				new ResultSetExtractor<Map<Long, String>>() {
					@Override
					public Map<Long, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<Long, String> associatedCustomerMap = new HashMap<>();

						while (rs.next()) {
							associatedCustomerMap.put(rs.getLong("customer_code"), rs.getString("customer_name"));
						}

						return associatedCustomerMap;
					}
				}, new Object[] { userId });
	}

	public List<CustomReportEntity> getReportDataForTab(final long tabId) {
		return jdbcTemplate.query(SQLConstants.FETCH_REPORT_DATA_FOR_TAB, new RowMapper<CustomReportEntity>() {

			@Override
			public CustomReportEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				CustomReportEntity reportEntity = new CustomReportEntity();
				reportEntity.setReportId(rs.getInt("report_id"));
				reportEntity.setReportName(rs.getString("report_name"));
				reportEntity.setReportDescription(rs.getString("report_description"));
				reportEntity.setReportTags(rs.getString("report_tags"));
				reportEntity.setReportUrl(rs.getString("report_url"));
				reportEntity.setColSpan(rs.getInt("col_span"));
				reportEntity.setChartTypeId(rs.getInt("chart_type_id"));
				reportEntity.setChartType(rs.getString("chart_type"));
				reportEntity.setFilterkey(rs.getString("filter_key"));
				reportEntity.setCustomFilterValue(rs.getString("custom_value"));
				reportEntity.setFilterKeyName(rs.getString("key_display_name"));
				reportEntity.setParamMasterUrl(rs.getString("param_master_url"));
				return reportEntity;
			}

		}, new Object[] { tabId, tabId });
	}

	/**
	 * DAO implementation to fetch Chart-Type Master Data
	 * 
	 * @return
	 */
	public Map<Integer, String> getChartTypeMaster() {
		return jdbcTemplate.query(SQLConstants.FETCH_CHART_TYPE_MASTER, new ResultSetExtractor<Map<Integer, String>>() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> chartTypeMap = new HashMap<>();

				while (rs.next()) {
					chartTypeMap.put(rs.getInt("chart_id"), rs.getString("chart_type"));
				}
				return chartTypeMap;
			}
		}, new Object[] {});
	}

	/**
	 * This method saves the tab detail and return the tabId
	 * 
	 * @param userId
	 * @param tabName
	 * @return tabId
	 */
	public long saveTab(final String userId, final String tabName) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQLConstants.INSERT_VALUE_INTO_TAB,
						new String[] { "TAB_ID" });
				ps.setString(1, userId);
				ps.setString(2, userId);
				ps.setString(3, tabName);
				ps.setString(4, userId);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * This method stores report detail in cats_custom_object_master table when new
	 * report created
	 * 
	 * @param tabId
	 * @param reportId
	 * @param colSpan
	 * @param filterKey
	 * @param graphType
	 * @param userId
	 */
	public void saveReport(long tabId, int reportId, int reportOrder, int colSpan, String userId, int paramIdentifier) {		
		System.out.println("tabId::"+tabId);
		System.out.println("reportId::"+reportId);
		System.out.println("reportOrder::"+reportOrder);
		System.out.println("colSpan::"+colSpan);
		System.out.println("userId::"+userId);

		
		/*KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQLConstants.INSERT_VALUE_INTO_CATS_CUSTOM_OBJECT_MASTER,
						new String[] { "PARAM_IDENTIFIER" });
				ps.setLong(1, tabId);
				ps.setInt(2, reportId);
				ps.setInt(3, reportOrder);
				ps.setInt(4, colSpan);
				ps.setInt(5, reportId);
				ps.setString(6, userId);
				ps.setInt(7, paramIdentifier);
				return ps;
			}
		}, keyHolder);		
		return keyHolder.getKey().longValue();*/
		
		jdbcTemplate.update(SQLConstants.INSERT_VALUE_INTO_CATS_CUSTOM_OBJECT_MASTER, new Object[] {tabId,reportId,reportOrder,colSpan,reportId,userId,paramIdentifier});
	}
	
	/**
	 * This method stores report detail in cats_custom_object_master table when new
	 * report created
	 * 
	 * @param tabId
	 * @param reportId
	 * @param colSpan
	 * @param filterKey
	 * @param graphType
	 * @param userId
	 */
	public void saveReport(long tabId, int reportId, int reportOrder, int colSpan, String userId) {
		System.out.println("tabId::" + tabId);
		System.out.println("reportId::" + reportId);
		System.out.println("reportOrder::" + reportOrder);
		System.out.println("colSpan::" + colSpan);
		System.out.println("userId::" + userId);

		/*KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						SQLConstants.INSERT_VALUE_INTO_CATS_CUSTOM_OBJECT_MASTER_WITHOUT_REPORT_PARAM,
						new String[] { "PARAM_IDENTIFIER" });
				ps.setLong(1, tabId);
				ps.setInt(2, reportId);
				ps.setInt(3, reportOrder);
				ps.setInt(4, colSpan);
				ps.setInt(5, reportId);
				ps.setString(6, userId);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().longValue();*/
		
		jdbcTemplate.update(SQLConstants.INSERT_VALUE_INTO_CATS_CUSTOM_OBJECT_MASTER_WITHOUT_REPORT_PARAM, new Object[] {tabId,reportId,reportOrder,colSpan,reportId,userId});
	}
	
	/**
	 * Database call to get the maximum value of param identifier generated for any
	 * custom report entry
	 * 
	 * @return paramIdentifier
	 */
	public int getMaxParamIdValue() {
		return jdbcTemplate.queryForObject(SQLConstants.GET_MAX_PARAM_IDENTIFIER, Integer.class);
	}

	/**
	 * This method makes a database call to get the filterKeyList for the input
	 * reportId
	 * 
	 * @param reportId
	 * @return {@link List} of filterKeys
	 */
	public List<ReportFilterEntity> getFilterKeyListForReport(int reportId) {
		return jdbcTemplate.query(SQLConstants.GET_REPORT_DEFAULT_KEYS, new RowMapper<ReportFilterEntity>() {
			@Override
			public ReportFilterEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				ReportFilterEntity reportFilter = new ReportFilterEntity();
				reportFilter.setFilterKey(rs.getString("filter_key"));
				reportFilter.setKeyDisplayName(rs.getString("key_display_name"));
				return reportFilter;
			}
		}, new Object[] { reportId });

	}
	
	/**
	 * This object inserts data in CATS_CUSTOM_REPORT_PARAMETER table for the input entity list
	 * @param filterParamEntityList
	 */
	public void saveCustomReportParam(List<FilterParamEntity> filterParamEntityList) {
		List<Object[]> batchParamList = new ArrayList<>();
		Object[] param = null;

		if(!filterParamEntityList.isEmpty()){
			for (FilterParamEntity filterParam : filterParamEntityList) {
				param = new Object[] { filterParam.getParamIdentifier(), filterParam.getKey(), filterParam.getValues(),
						filterParam.getCreatedBy() };
				batchParamList.add(param);
			}		
			jdbcTemplate.batchUpdate(SQLConstants.INSERT_INTO_CUSTOM_REPORT_PARAMETER, batchParamList);
		}
	}

	/**
	 * 
	 * @param tabId
	 * @param reportId
	 * @param colSpan
	 * @param userId
	 * @return
	 */
	public long saveNewReport(long tabId, int reportId, int colSpan, String userId) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQLConstants.ADD_NEW_REPORT,
						new String[] { "PARAM_IDENTIFIER" });
				ps.setLong(1, tabId);
				ps.setInt(2, reportId);
				ps.setInt(3, reportId);
				ps.setInt(4, colSpan);
				ps.setInt(5, reportId);
				ps.setString(6, userId);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().longValue();

	}
	 
	/**
	 * This method makes a database call to fetch the existing reportId list and
	 * corresponding paramIdentifiers for the input tabId
	 * 
	 * @param tabId
	 * @return {@link Map} of reportId as key and paramIdentifier as Value
	 */
	public Map<Integer, Map<Long,String[]>> getReportListForTab(long tabId) {
		return jdbcTemplate.query(SQLConstants.GET_REPORT_ID_LIST_FOR_TAB,
				new ResultSetExtractor<Map<Integer, Map<Long,  String[]>>>() {
					@Override
					public Map<Integer, Map<Long,  String[]>> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<Integer, Map<Long,  String[]>> reportIdParamIdMap = new HashMap<>();
						String[] keys = null;
						Map<Long, String[]> paramIdKeysMap = null;
						int lastReportId = -1;

						while (rs.next()) {
							keys = rs.getString("filter_keys").split(",");
							
							if(lastReportId != rs.getInt("report_id")) {
								paramIdKeysMap = new HashMap<>();
								reportIdParamIdMap.put(rs.getInt("report_id"), paramIdKeysMap);
							}							
							
							paramIdKeysMap.put(rs.getLong("param_identifier"), keys);
							lastReportId = rs.getInt("report_id");							
						}
						return reportIdParamIdMap;
					}
				}, new Object[] { tabId });
	}
	
	public void updateInCatsCustomReportParameter(String keyValue,String userId,long paramIdentifier,String filterKey) {
		jdbcTemplate.update(SQLConstants.UPDATE_REPORT_IN_CATS_CUSTOM_REPORT_PARAMETER,new Object[] {keyValue,userId,paramIdentifier,filterKey});
		
	}
	
	public String deleteTab(String userId,long tabId) {
		jdbcTemplate.update(SQLConstants.DELETE_TAB,new Object[] {userId,tabId});
		String message = "Tab Deleted Successfully";
		return message;
	}
	
	public List<Integer> reportIdList(int tabId) {
		return jdbcTemplate.queryForList(SQLConstants.GET_REPORT_LIST, new Object[] {}, Integer.class);
	}
	
	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long saveReportInExistingTab(long tabId, int reportId, int colSpan, String userId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQLConstants.INSERT_VALUE_INTO_CATS_CUSTOM_OBJECT_MASTER_NEW_REPORT,
						new String[] { "PARAM_IDENTIFIER" });
				ps.setLong(1, tabId);
				ps.setInt(2, reportId);
				ps.setInt(3, reportId);
				ps.setInt(4, colSpan);
				ps.setInt(5, reportId);
				ps.setString(6, userId);
				return ps;
			}
		}, keyHolder);		
		return keyHolder.getKey().longValue();
	}
/**
 * method for deleting a report from database
 * @param tabId
 * @param reportId
 */
	public void deleteReport(long tabId, int reportId) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(SQLConstants.DELETE_REPORT_FROM_CATS_CUSTOM_OBJECT_MASTER,new Object[] {tabId,tabId,reportId});
		
	}
}