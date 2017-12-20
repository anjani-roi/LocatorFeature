package com.getusroi.location.processor.bean;

import org.apache.camel.Exchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.attunedlabs.eventframework.abstractbean.AbstractMetaModelBean;
import com.getusroi.location.services.LocationService;

/**
 * @author anjani_pandey
 *
 */
public class CreateInstanceMap extends AbstractMetaModelBean {

	protected static final Logger logger = LoggerFactory.getLogger(CreateInstanceMap.class);

	@Autowired
	private LocationService locationService;
	
	@Override
	protected void processBean(Exchange exchange) throws Exception {
		logger.debug("CreateInstanceMap starts processing...");
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String instanceId = jsonObject.getString("instance_id");
		String uomGroup = jsonObject.getString("uom_group");
		String geoGroupId = jsonObject.getString("geogroup_id");
		String geoLocId = jsonObject.getString("geoloc_id");
		String fractionalId = jsonObject.getString("fractional_id");
		locationService.createInstanceMap(instanceId, uomGroup, geoGroupId, geoLocId, fractionalId, exchange);
		JSONObject response = new JSONObject();
		response.put("message", "Instance Map has been created successfully.");
		exchange.getIn().setBody(response);
		logger.debug("CreateInstanceMap processed successfully.");
	}

}
