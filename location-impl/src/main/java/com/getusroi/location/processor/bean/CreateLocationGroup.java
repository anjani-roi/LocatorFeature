/**
 * 
 */
package com.getusroi.location.processor.bean;

import java.util.Map;

import org.apache.camel.Exchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.attunedlabs.eventframework.abstractbean.AbstractMetaModelBean;
import com.getusroi.location.services.LocationService;
import com.getusroi.location.utils.JsonUtil;

/**
 * @author Anjani Pandey
 *
 */
public class CreateLocationGroup extends AbstractMetaModelBean {
	
	protected static final Logger logger = LoggerFactory.getLogger(CreateLocationGroup.class);

	@Autowired
	private LocationService locationService;

	@Override
	protected void processBean(Exchange exchange) throws Exception {
		logger.debug("CreateLocationGroup starts processing...");
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String locationGroupId = jsonObject.getString("loc_group_id");
		Map<String, String> attributeMap = JsonUtil.getAttributeMap(jsonObject);
		String shortDesc = jsonObject.getString("short_desc");
		String longDesc = jsonObject.getString("long_desc");
		String locAccessId = jsonObject.getString("loc_access_id");
		int absPriority = Integer.valueOf(jsonObject.getString("abs_priority"));
		int deltaPriority = Integer.valueOf(jsonObject.getString("delta_priority"));
		String locAuthId = jsonObject.getString("loc_auth_id");
		String attributeGroup = jsonObject.getString("attribute_group");
		locationService.createLocationGroup(locationGroupId, attributeMap, shortDesc, longDesc, locAccessId, absPriority, deltaPriority, locAuthId, attributeGroup, exchange);
		JSONObject response = new JSONObject();
		response.put("message", "Location group has been created successfully.");
		exchange.getIn().setBody(response);
		logger.debug("CreateLocationGroup processed successfully.");
	}

}
