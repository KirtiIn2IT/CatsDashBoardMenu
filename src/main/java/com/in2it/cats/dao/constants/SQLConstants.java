package com.in2it.cats.dao.constants;



public class SQLConstants {
	public static final String GET_ROLE_BASED_CATEGORY_INFO = "select category.category_type_code as category_code,"
			+ " category.category_type_description as category_description,"
			+ " category.category_type_name as category_name, " + " subCategory.CATEGORY_CODE as subCategory_Code,"
			+ " subCategory.CATEGORY_NAME as subcategory_name," + " report.report_id," + " report.report_name,"
			+ " report.report_description," + " report.report_tags," + " data_source.DATA_FETCH_ID,"
			+ " data_source.api_url," + " data_source.api_name" + " from cats_user_role_mapping user_role,"
			+ " cats_role_master role," + " cats_role_category_mapping role_category,"
			+ " category_type_master category," + " category_master subCategory," + " cats_object_master report,"
			+ " cats_dashboard_data_source data_source" + " where  "
			+ " data_source.DATA_FETCH_ID = report.DATA_FETCH_ID"
			+ " and report.REPORT_CATEGORY = subCategory.CATEGORY_CODE" + " and subCategory.IS_ACTIVE = 'Y'"
			+ " and subCategory.CATEGORY_TYPE_CODE = category.CATEGORY_TYPE_CODE" + " and category.IS_ACTIVE = 'Y'"
			+ " and category.category_type_code = role_category.category_id"
			+ " and role_category.role_id = role.role_id " + " and role.is_active='Y'"
			+ " and role.role_id = user_role.role_id" + " and user_role.user_id = ?"
			+ " order by category_code,subCategory_Code, report_id";

	public static final String FETCH_TABS_FOR_USER = "select tab_id,tab_name from cats_user_tab_mapping where user_id =?"
			+ " and is_associated='Y' order by tab_order";

	public static final String FETCH_ASSOCIATED_CUSTOMER_LIST = "select customer.customer_code,customer.customer_name\n"
			+ " from cats_user_customer_mapping mapping,cats_customer customer\n"
			+ " where customer.customer_code = mapping.customer_code \n"
			+ " and mapping.is_active='Y' and mapping.user_id = ?";

	public static final String FETCH_REPORT_DATA_FOR_TAB = "select * from (select cust_obj_master.report_id,rep_master.report_name,cust_obj_master.report_order,\r\n" + 
			"			 rep_master.report_description, rep_master.report_tags,data_source.api_url as report_url,\r\n" + 
			"			 cust_obj_master.col_span,cust_obj_master.chart_type_id,charts.chart_type,\r\n" + 
			"			 cust_rep_param.filter_key as filter_key, cust_rep_param.key_values as custom_value,\r\n" + 
			"			 rep_param_master.key_display_name,  (select api_url from cats_dashboard_data_source\r\n" + 
			"			 where data_fetch_id = rep_param_master.param_data_fetch_id) param_master_url  from\r\n" + 
			"			 cats_report_parameter_master rep_param_master,  cats_custom_object_master cust_obj_master,\r\n" + 
			"			 cats_custom_report_parameter cust_rep_param,  cats_object_master rep_master,\r\n" + 
			"			 cats_dashboard_data_source data_source,cats_chart_master charts  where\r\n" + 
			"			 cust_obj_master.chart_type_id = charts.chart_id\r\n" + 
			"			 and data_source.data_fetch_id = rep_master.data_fetch_id\r\n" + 
			"			 and rep_master.report_id = cust_obj_master.report_id\r\n" + 
			"			 and rep_param_master.filter_key = cust_rep_param.filter_key\r\n" + 
			"			 and rep_param_master.report_id =  cust_obj_master.report_id\r\n" + 
			"			 and cust_rep_param.param_identifier = cust_obj_master.param_identifier\r\n" + 
			"			 and cust_obj_master.param_identifier != 0\r\n" + 
			"			 and cust_obj_master.is_report_associated = 'Y'\r\n" + 
			"			 and cust_obj_master.tab_id = ?\r\n" + 
			"union\r\n" + 
			"select cust_obj_master.report_id,rep_master.report_name,cust_obj_master.report_order,\r\n" + 
			"			 rep_master.report_description, rep_master.report_tags,data_source.api_url as report_url,\r\n" + 
			"			 cust_obj_master.col_span,cust_obj_master.chart_type_id,charts.chart_type,\r\n" + 
			"			 null as filter_key, null as custom_value,\r\n" + 
			"			 null as key_display_name,  null as param_master_url  from\r\n" + 
			"			 cats_custom_object_master cust_obj_master,\r\n" + 
			"			 cats_object_master rep_master,\r\n" + 
			"			 cats_dashboard_data_source data_source,cats_chart_master charts  where\r\n" + 
			"			 cust_obj_master.chart_type_id = charts.chart_id\r\n" + 
			"			 and data_source.data_fetch_id = rep_master.data_fetch_id\r\n" + 
			"			 and rep_master.report_id = cust_obj_master.report_id					\r\n" + 
			"			 and 0 = cust_obj_master.param_identifier\r\n" + 
			"			 and cust_obj_master.is_report_associated = 'Y'\r\n" + 
			"			 and cust_obj_master.tab_id = ?) report_data order by report_data.report_order";

	public static final String FETCH_CHART_TYPE_MASTER = "select chart_id, chart_type from cats_chart_master order by chart_id";

	public static final String INSERT_VALUE_INTO_TAB = "insert into cats_user_tab_mapping(user_id,tab_order,tab_name, created_by,\r\n"
			+ "created_dttm,is_associated)\r\n" + "(select ? as user_id\r\n"
			+ ",ifnull((select max(tab_order) + 1 from cats_user_tab_mapping \r\n"
			+ "where is_associated ='Y' and user_id = ?),1) tab_order,\r\n"
			+ "? as tab_name,? as created_by, now() as created_dttm, \r\n" + "'Y' as is_associated)";

	public static final String GET_REPORT_DEFAULT_KEYS = "select param_master.filter_key as filter_key, param_master.key_display_name\r\n"
			+ "from cats_report_parameter_master param_master where param_master.report_id = ?";

	public static final String INSERT_VALUE_INTO_CATS_CUSTOM_OBJECT_MASTER = "insert into cats_custom_object_master (tab_id,report_id,report_order,col_span,chart_type_id,\r\n"
			+ "is_report_associated,created_by,created_dttm, param_identifier) \r\n"
			+ "(select ? as tab_id,? as report_id, ? as report_order,\r\n"
			+ "? as col_span,(select default_chart_type_id from cats_object_master \r\n"
			+ "where report_id=?)as chart_type_id, 'Y' \r\n"
			+ "as is_report_associated,? as created_by,now() as created_dttm,?)";
	
	public static final String INSERT_VALUE_INTO_CATS_CUSTOM_OBJECT_MASTER_WITHOUT_REPORT_PARAM = "insert into cats_custom_object_master (tab_id,report_id,report_order,col_span,chart_type_id,\r\n"
			+ "is_report_associated,created_by,created_dttm) \r\n"
			+ "(select ? as tab_id,? as report_id, ? as report_order,\r\n"
			+ "? as col_span,(select default_chart_type_id from cats_object_master \r\n"
			+ "where report_id=?)as chart_type_id, 'Y' \r\n"
			+ "as is_report_associated,? as created_by,now() as created_dttm)";
	
	public static final String GET_MAX_PARAM_IDENTIFIER = "select max(param_identifier)+1 as param_identifier from cats_custom_object_master";

	public static final String GET_REPORT_LIST = "select report_id from  cats_custom_object_master where tab_id=?";

	public static final String INSERT_INTO_CUSTOM_REPORT_PARAMETER = "insert into cats_custom_report_parameter(param_identifier, filter_key, key_values, created_by, created_dttm) \r\n"
			+ "(select ? as param_identfier, ? as filter_key, ? as key_values, ? as created_by, now() as created_dttm)";
	
	public static final String ADD_NEW_REPORT = " insert into cats_custom_object_master (tab_id,report_id,report_order,col_span,chart_type_id,\r\n" + 
					   "is_report_associated,created_by,created_dttm) \r\n" + 
					   "(select ? as tab_id,? as report_id,(select max(report_order) + 1 from cats_custom_object_master \r\n" + 
				       "where IS_REPORT_ASSOCIATED ='Y' and report_id=?)as report_order ,\r\n" + 
					   "+ \"? as col_span,(select default_chart_type_id from cats_object_master \r\n" + 
					   "+ \"where report_id=?)as chart_type_id, 'Y' \r\n" + 
					   "as is_report_associated,? as created_by,now() as created_dttm)";

	public static final String GET_REPORT_ID_LIST_FOR_TAB = "select object.report_id,object.param_identifier,\r\n" + 
			" group_concat(param.filter_key) filter_keys\r\n" + 
			" from \r\n" + 
			" cats_custom_report_parameter param, \r\n" + 
			" cats_custom_object_master object\r\n" + 
			" where object.param_identifier = param.param_identifier \r\n" + 
			" and object.is_report_associated = 'Y' and object.tab_id = ?  \r\n" + 
			" group by report_id, param_identifier \r\n" + 
			" order by report_order";
	public static final String UPDATE_REPORT_IN_CATS_CUSTOM_REPORT_PARAMETER = "update cats_custom_report_parameter set key_values=?,updated_by = ?,updated_dttm=now()\r\n" + 
			"   where param_identifier=? and filter_key=?";
	public static final String INSERT_VALUE_INTO_CATS_CUSTOM_OBJECT_MASTER_NEW_REPORT = "insert into cats_custom_object_master (tab_id,report_id,report_order,col_span,chart_type_id,\r\n"
			+ "is_report_associated,created_by,created_dttm) \r\n"
			+ "(select ? as tab_id,? as report_id, (select max(report_order) + 1 from cats_custom_object_master \r\n" + 
			"			where IS_REPORT_ASSOCIATED ='Y' and report_id=?)as report_order,\r\n"
			+ "? as col_span,(select default_chart_type_id from cats_object_master \r\n"
			+ "where report_id=?)as chart_type_id, 'Y' \r\n"
			+ "as is_report_associated,? as created_by,now() as created_dttm)";
	public static final String DELETE_REPORT_FROM_CATS_CUSTOM_OBJECT_MASTER = "update cats_custom_object_master set IS_REPORT_ASSOCIATED ='N',updated_by = ?,updated_dttm=now() where TAB_ID=? and REPORT_ID=?";
	public static final String DELETE_TAB="update cats_user_tab_mapping set is_associated='N' where user_Id=? and tab_id=?";
	
	
}