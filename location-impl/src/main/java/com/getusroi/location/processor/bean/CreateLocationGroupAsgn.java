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
public class CreateLocationGroupAsgn extends AbstractMetaModelBean {

	protected static final Logger logger = LoggerFactory.getLogger(CreateLocationGroupAsgn.class);

	@Autowired
	private LocationService locationService;
	
	@Override
	protected void processBean(Exchange exchange) throws Exception {
		logger.debug("CreateLocationGroupAsgn starts processing...");
		String body = exchange.getIn().getBody(String.class);
		logger.info("Body propagated: "+body);
		JSONObject jsonObject = new JSONObject(body.toString());
		String locId = jsonObject.getString("loc_id");
		String locationGroupId = jsonObject.getString("loc_group_id");
		locationService.createLocationGroupAsgn(locationGroupId, locId, exchange);
		JSONObject response = new JSONObject();
		response.put("message", "Location group assignment has been created successfully.");
		exchange.getIn().setBody(response);
		logger.debug("CreateLocationGroupAsgn processed sucessfully.");
	}

}
