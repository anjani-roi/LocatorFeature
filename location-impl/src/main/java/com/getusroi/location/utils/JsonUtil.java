package com.getusroi.location.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.getusroi.location.domain.Address;

public class JsonUtil {
	
	public static Address getAddress(JSONObject jsonObject) throws JSONException {
		JSONObject addressJsonObj = jsonObject.getJSONObject("address");
		Address address = new Address();
		address.setFirstName(addressJsonObj.getString("first_name"));
		address.setMiddleName(addressJsonObj.getString("middle_name"));
		address.setLastName(addressJsonObj.getString("last_name"));
		address.setHostKey(addressJsonObj.getString("host_key"));
		address.setAddressCode(addressJsonObj.getString("address_code"));
		address.setLatitude(addressJsonObj.getInt("latitude"));
		address.setLongitude(addressJsonObj.getInt("longitude"));
		address.setAddressGroupId(addressJsonObj.getString("address_group_id"));
		
		// Getting address details
		JSONArray addressDetail = jsonObject.getJSONObject("address").getJSONArray("address_detail");
		Map<String, String> addressDetailMap = new HashMap<String, String>();
		for(int i=0; i<addressDetail.length(); i++) {
			JSONObject addressDetailObj = addressDetail.getJSONObject(i);
			addressDetailMap.put(addressDetailObj.getString("column_name"), addressDetailObj.getString("column_value"));
		}
		
		address.setAddressDetailMap(addressDetailMap);
		
		return address;
	}
	
	public static Map<String, String> getAttributeMap(JSONObject jsonObject) throws JSONException {
		JSONArray attributes = jsonObject.getJSONArray("attributes");
		Map<String, String> attributeMap = new HashMap<String, String>();
		for(int i=0; i<attributes.length(); i++) {
			JSONObject attribute = attributes.getJSONObject(i);
			attributeMap.put(attribute.getString("attribute_name"), attribute.getString("attribute_value"));
		}
		return attributeMap;
	}
}
