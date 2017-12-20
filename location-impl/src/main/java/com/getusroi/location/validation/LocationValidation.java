package com.getusroi.location.validation;

import org.apache.camel.Exchange;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getusroi.location.exceptions.WMSLiteDAOException;
import com.getusroi.location.exceptions.WMSLiteException;
import com.getusroi.location.services.LocationService;

@Component
public class LocationValidation {

	@Autowired
	private LocationService locationService;

	public void validateLocationGroup(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String locationGroupId = jsonObject.getString("loc_group_id");
		boolean isValid = locationService.validateLocationGroup(locationGroupId, exchange);
		if(isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Location group already exist: "+locationGroupId));
			throw new WMSLiteException();
		}
	}
	
	public void invalidateLocationGroup(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String locationGroupId = jsonObject.getString("loc_group_id");
		boolean isValid = locationService.validateLocationGroup(locationGroupId, exchange);
		if(!isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Location group does not exist: "+locationGroupId));
			throw new WMSLiteException();
		}
	}
	
	public void validateInstance(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String instanceId = jsonObject.getString("instance_id");
		boolean isValid = locationService.validateInstance(instanceId, exchange);
		if(isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Instance already exist: "+instanceId));
			throw new WMSLiteException();
		}
	}
	
	public void validateGeoGroup(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String geoGroupId = jsonObject.getString("geogroup_id");
		boolean isValid = locationService.validateGeoGroup(geoGroupId, exchange);
		if(isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Geo group already exist: "+geoGroupId));
			throw new WMSLiteException();
		}
	}
	
	public void invalidateGeoGroup(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String geoGroupId = jsonObject.getString("geogroup_id");
		boolean isValid = locationService.validateGeoGroup(geoGroupId, exchange);
		if(!isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Geo group does not exist: "+geoGroupId));
			throw new WMSLiteException();
		}
	}
	
	public void validateGeoLoc(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String geoLocId = jsonObject.getString("geoloc_id");
		boolean isValid = locationService.validateGeoLoc(geoLocId, exchange);
		if(isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Geo location already exist: "+geoLocId));
			throw new WMSLiteException();
		}
	}
	
	public void invalidateGeoLoc(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String geoLocId = jsonObject.getString("geoloc_id");
		boolean isValid = locationService.validateGeoLoc(geoLocId, exchange);
		if(!isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Geo location does not exist: "+geoLocId));
			throw new WMSLiteException();
		}
	}
	
	public void validateFractional(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String fractionalId = jsonObject.getString("fractional_id");
		boolean isValid = locationService.validateFractional(fractionalId, exchange);
		if(isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Fractional already exist: "+fractionalId));
			throw new WMSLiteException();
		}
	}
	
	public void invalidateMapId(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String mapId = jsonObject.getString("map_id");
		boolean isValid = locationService.validateMapId(mapId, exchange);
		if(!isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "MapId does not exist: "+mapId));
			throw new WMSLiteException();
		}
	}
	
	public void invalidateFractional(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String fractionalId = jsonObject.getString("fractional_id");
		boolean isValid = locationService.validateFractional(fractionalId, exchange);
		if(!isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Fractional does not exist: "+fractionalId));
			throw new WMSLiteException();
		}
	}
	
	public void validateMicroLoc(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String locId = jsonObject.getString("loc_id");
		boolean isValid = locationService.validateMicroLoc(locId, exchange);
		if(isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Micro location already exist: "+locId));
			throw new WMSLiteException();
		}
	}
	
	public void invalidateMicroLoc(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String locId = jsonObject.getString("loc_id");
		boolean isValid = locationService.validateMicroLoc(locId, exchange);
		if(!isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Micro location does not exist: "+locId));
			throw new WMSLiteException();
		}
	}
	
	public void validateOwner(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String ownerId = jsonObject.getString("owner_id");
		boolean isValid = locationService.validateOwner(ownerId, exchange);
		if(isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Owner already exist: "+ownerId));
			throw new WMSLiteException();
		}
	}
	
	public void invalidateOwner(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String ownerId = jsonObject.getString("owner_id");
		boolean isValid = locationService.validateOwner(ownerId, exchange);
		if(!isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Owner does not exist: "+ownerId));
			throw new WMSLiteException();
		}
	}
	
	public void validateTenant(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String tenantId = jsonObject.getString("tenant_id");
		boolean isValid = locationService.validateTenant(tenantId, exchange);
		if(isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Tenant already exist: "+tenantId));
			throw new WMSLiteException();
		}
	}
	
	public void invalidateTenant(Exchange exchange) throws WMSLiteDAOException, Exception {
		String body = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = new JSONObject(body.toString());
		String tenantId = jsonObject.getString("tenant_id");
		boolean isValid = locationService.validateTenant(tenantId, exchange);
		if(!isValid) {
			exchange.getIn().setBody(new JSONObject().put("message", "Tenant does not exist: "+tenantId));
			throw new WMSLiteException();
		}
	}
}
