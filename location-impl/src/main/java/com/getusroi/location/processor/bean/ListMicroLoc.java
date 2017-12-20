package com.getusroi.location.processor.bean;

import java.util.List;
import java.util.Map;

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
public class ListMicroLoc extends AbstractMetaModelBean {

	protected static final Logger logger = LoggerFactory.getLogger(ListMicroLoc.class);

	@Autowired
	private LocationService locationService;
	
	@Override
	protected void processBean(Exchange exchange) throws Exception {
		logger.debug("ListMicroLoc starts processing...");
		String httpQuery = (String) exchange.getIn().getHeader("CamelHttpQuery");
		String locId = null;
		if(httpQuery != null) {
			locId = httpQuery.split("=")[1];
		}
		List<Map<String, Object>> microLocList = locationService.listMicroLoc(locId, exchange);
		JSONObject response = new JSONObject();
		response.put("micro_locations", microLocList);
		exchange.getIn().setBody(response);
		logger.debug("ListMicroLoc processed sucessfully.");
	}

}
