package com.getusroi.location.processor.bean;

import java.util.Map;

import org.apache.camel.Exchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.attunedlabs.eventframework.abstractbean.AbstractMetaModelBean;
import com.getusroi.location.domain.Address;
import com.getusroi.location.services.LocationService;
import com.getusroi.location.utils.JsonUtil;

/**
 * @author Anjani Pandey
 *
 */
public class CreateMicroLoc extends AbstractMetaModelBean {

	protected static final Logger logger = LoggerFactory.getLogger(CreateMicroLoc.class);

	@Autowired
	private LocationService locationService;
	
	@Override
	protected void processBean(Exchange exchange) throws Exception {
		logger.debug("CreateMicroLoc starts processing...");
		String body = exchange.getIn().getBody(String.class);
		logger.info("Body propagated: "+body);
		JSONObject jsonObject = new JSONObject(body.toString());
		String locId = jsonObject.getString("loc_id");
		String shortDesc = jsonObject.getString("short_desc");
		String longDesc = jsonObject.getString("long_desc");
		Map<String, String> attributeMap = JsonUtil.getAttributeMap(jsonObject);
		String dimId = jsonObject.getString("dim_id");
		String locCode = jsonObject.getString("loc_code");
		String tenantId = jsonObject.getString("tenant_id");
		Address address = JsonUtil.getAddress(jsonObject);
		String locAccessId = jsonObject.getString("loc_access_id");
		String locAuthId = jsonObject.getString("loc_auth_id");
		String containerId = jsonObject.getString("container_id");
		int mapId = Integer.valueOf(jsonObject.getString("map_id"));
		String locStatus = jsonObject.getString("loc_status");
		String attributeGroup = jsonObject.getString("attribute_group");
		locationService.createMicroLoc(locId, shortDesc, longDesc, attributeMap, dimId, locCode, tenantId, address, locAccessId, locAuthId, containerId, mapId, locStatus, attributeGroup, exchange);
		JSONObject response = new JSONObject();
		response.put("message", "Micro location has been created successfully.");
		exchange.getIn().setBody(response);
		logger.debug("CreateMicroLoc processed sucessfully.");
	}

}
