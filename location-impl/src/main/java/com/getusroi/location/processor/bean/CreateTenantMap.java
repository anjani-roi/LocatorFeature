package com.getusroi.location.processor.bean;

import org.apache.camel.Exchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.attunedlabs.eventframework.abstractbean.AbstractMetaModelBean;
import com.getusroi.location.services.LocationService;

/**
 * @author Anjani Pandey
 *
 */
public class CreateTenantMap extends AbstractMetaModelBean {

	protected static final Logger logger = LoggerFactory.getLogger(CreateTenantMap.class);

	@Autowired
	private LocationService locationService;
	
	@Override
	protected void processBean(Exchange exchange) throws Exception {
		logger.debug("CreateTenantMap starts processing...");
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String tenantId = jsonObject.getString("tenant_id");
		int mapId = Integer.valueOf(jsonObject.getString("map_id"));
		String uomGroup = jsonObject.getString("uom_group");
		locationService.createTenantMap(mapId, tenantId, uomGroup, exchange);
		JSONObject response = new JSONObject();
		response.put("message", "Tenant map has been created successfully.");
		exchange.getIn().setBody(response);
		logger.debug("CreateTenantMap processed sucessfully.");
	}

}
