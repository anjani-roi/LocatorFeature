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
public class CreateFractional extends AbstractMetaModelBean {

	protected static final Logger logger = LoggerFactory.getLogger(CreateFractional.class);

	@Autowired
	private LocationService locationService;
	
	@Override
	protected void processBean(Exchange exchange) throws Exception {
		logger.debug("CreateFractional starts processing...");
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String fractionalId = jsonObject.getString("fractional_id");
		String shortDesc = jsonObject.getString("short_desc");
		String longDesc = jsonObject.getString("long_desc");
		Map<String, String> attributeMap = JsonUtil.getAttributeMap(jsonObject);
		int mapId = Integer.valueOf(jsonObject.getString("map_id"));
		String tenantId = jsonObject.getString("tenant_id");
		Address address = JsonUtil.getAddress(jsonObject);
		String attributeGroup = jsonObject.getString("attribute_group");
		locationService.createFractional(fractionalId, shortDesc, longDesc, attributeMap, mapId, tenantId, address, attributeGroup, exchange);
		JSONObject response = new JSONObject();
		response.put("message", "Fractional has been created successfully.");
		exchange.getIn().setBody(response);
		logger.debug("CreateFractional processed sucessfully.");
	}

}
