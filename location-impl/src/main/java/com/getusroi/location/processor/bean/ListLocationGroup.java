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
public class ListLocationGroup extends AbstractMetaModelBean {

	protected static final Logger logger = LoggerFactory.getLogger(ListLocationGroup.class);

	@Autowired
	private LocationService locationService;
	
	@Override
	protected void processBean(Exchange exchange) throws Exception {
		logger.debug("ListLocationGroup starts processing...");
		String httpQuery = (String) exchange.getIn().getHeader("CamelHttpQuery");
		String locGroupId = null;
		if(httpQuery != null) {
			locGroupId = httpQuery.split("=")[1];
		}
		List<Map<String, Object>> locationGroupList = locationService.listLocationGroup(locGroupId, exchange);
		JSONObject response = new JSONObject();
		exchange.getIn().setBody(response.put("location_groups", locationGroupList));
		logger.debug("ListLocationGroup processed sucessfully.");
	}

}
