package com.in2it.cats.controller.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.in2it.cats.service.pojo.Tab;
import com.in2it.cats.stub.reporting.CustomReport;

/**
 * 
 * @author In2IT Technologies Ltd.
 *
 */
public class SaveTabCustomDeserializer extends StdDeserializer<Tab>{

	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	public SaveTabCustomDeserializer() {
		this(null);
	}
	
	public SaveTabCustomDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Tab deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		JsonNode tabNameNode = node.get("tabName");
		String tabName = tabNameNode.asText();

		JsonNode tabReports = node.path("tabReports");
		CustomReport report = null;
		List<List<CustomReport>> reportList = new ArrayList<>();
		List<CustomReport> customReportList = new ArrayList<>();
		reportList.add(customReportList);
		int rowLength = 0;

		if (tabReports.isArray()) {
			for (JsonNode reportNode : tabReports) {

				if (reportNode.isArray()) {
					rowLength = reportNode.size();
					System.out.println("Size of row node is::" + rowLength);

					for (int i = 0; i < rowLength; i++) {
						JsonNode reportObj = reportNode.get(i);
						report = new CustomReport();
						report.setReportId(reportObj.path("reportId").asInt());
						report.setReportName(reportObj.path("reportName").asText());
						report.setReportURL(reportObj.path("reportURL").asText());
						report.setColSpan(reportObj.path("colSpan").asInt());
						report.setFlag((short)reportObj.path("flag").asInt());
						customReportList.add(report);
					}
				}
			}
		}
		Tab tab = new Tab();
		tab.setTabName(tabName);
		tab.setTabReports(reportList);
		return tab;
	}
}
