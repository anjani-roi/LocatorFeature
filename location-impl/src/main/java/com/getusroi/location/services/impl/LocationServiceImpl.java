package com.getusroi.location.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.query.Query;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.attunedlabs.eventframework.abstractbean.LeapMetaModelBean;
import com.getusroi.location.domain.Address;
import com.getusroi.location.services.LocationService;
import com.getusroi.location.utils.DatabaseUtils;

@Component("locationService")
public class LocationServiceImpl implements LocationService {

	public static Logger logger = Logger.getLogger(LocationServiceImpl.class);
	
	@Autowired
	private LeapMetaModelBean leapMetaModelBean;
	
	/*################# Create Locations Services #################*/

	@Override
	public void createLocationGroup(String locationGroupId,
			Map<String, String> attributeMap, String shortDesc,
			String longDesc, String locAccessId, int absPriority,
			int deltaPriority, String locAuthId, String attributeGroup,
			Exchange exchange) throws Exception {
		
		String descId = getNextSequentialLabel("desc_id", exchange);
		String descType = "LocationGroup";
		String localeId = "ENGLISH";
		
		if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() > 20) {
			shortDesc = longDesc.substring(0, 20);
		}
		else if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() <= 20) {
			shortDesc = longDesc;
		}
		
		// Create description
		createDescription(descId, descType, localeId, shortDesc, longDesc, exchange);
		String attributeId = createAttributes(attributeMap, exchange);
		List<String> columns = new ArrayList<String>();
		columns.add("loc_group_id");
		columns.add("attribute_id");
		columns.add("desc_id");
		columns.add("loc_access_id");
		columns.add("abs_priority");
		columns.add("delta_priority");
		columns.add("loc_auth_id");
		columns.add("attribute_group");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(locationGroupId);
		insertableValues.add(attributeId);
		insertableValues.add(descId);
		insertableValues.add(locAccessId);
		insertableValues.add(absPriority);
		insertableValues.add(deltaPriority);
		insertableValues.add(locAuthId);
		insertableValues.add(attributeGroup);
		
		leapMetaModelBean.doLeapInsert("Location_Group", columns, insertableValues, exchange);
		
	}

	@Override
	public void createInstanceMap(String instanceId, String uomGroup,
			String geoGroupId, String geoLocId, String fractionalId, Exchange exchange) throws Exception {
		
		List<String> columns = new ArrayList<String>();
		columns.add("instance_id");
		columns.add("uom_group");
		columns.add("geogroup_id");
		columns.add("geoloc_id");
		columns.add("fractional_id");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(instanceId);
		insertableValues.add(uomGroup);
		insertableValues.add(geoGroupId);
		insertableValues.add(geoLocId);
		insertableValues.add(fractionalId);
		
		leapMetaModelBean.doLeapInsert("Instance_Map", columns, insertableValues, exchange);
		
	}

	@Override
	public void createFractional(String fractionalId, String shortDesc,
			String longDesc, Map<String, String> attributeMap, int mapId,
			String tenantId, Address address, String attributeGroup,
			Exchange exchange) throws Exception {

		String descId = getNextSequentialLabel("desc_id", exchange);
		String descType = "Fractional";
		String localeId = "ENGLISH";
		
		if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() > 20) {
			shortDesc = longDesc.substring(0, 20);
		}
		else if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() <= 20) {
			shortDesc = longDesc;
		}
		
		// Create description
		createDescription(descId, descType, localeId, shortDesc, longDesc, exchange);
		
		String attributeId = createAttributes(attributeMap, exchange);
		
		// Creating address
		address.setAddressType("Fractional");
		address.setAttributeId(attributeId);
		createAddress(address, mapId, tenantId, attributeGroup, exchange);
		
		// Creating fractional
		List<String> columns = new ArrayList<String>();
		columns.add("fractional_id");
		columns.add("desc_id");
		columns.add("attribute_id");
		columns.add("address_id");
		columns.add("attribute_group");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(fractionalId);
		insertableValues.add(descId);
		insertableValues.add(attributeId);
		insertableValues.add(address.getAddressId());
		insertableValues.add(attributeGroup);
		
		leapMetaModelBean.doLeapInsert("Fractional", columns, insertableValues, exchange);
	}

	@Override
	public void createGeoGroup(String geoGroupId, String shortDesc,
			String longDesc, Map<String, String> attributeMap, int mapId,
			String tenantId, Address address, String attributeGroup,
			Exchange exchange) throws Exception {
		
		String descId = getNextSequentialLabel("desc_id", exchange);
		String descType = "GeoGroup";
		String localeId = "ENGLISH";
		
		if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() > 20) {
			shortDesc = longDesc.substring(0, 20);
		}
		else if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() <= 20) {
			shortDesc = longDesc;
		}
		
		// Create description
		createDescription(descId, descType, localeId, shortDesc, longDesc, exchange);
		
		String attributeId = createAttributes(attributeMap, exchange);
		
		// Creating address
		address.setAddressType("GeoGroup");
		address.setAttributeId(attributeId);
		createAddress(address, mapId, tenantId, attributeGroup, exchange);
		
		// Creating fractional
		List<String> columns = new ArrayList<String>();
		columns.add("geogroup_id");
		columns.add("desc_id");
		columns.add("attribute_id");
		columns.add("address_id");
		columns.add("attribute_group");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(geoGroupId);
		insertableValues.add(descId);
		insertableValues.add(attributeId);
		insertableValues.add(address.getAddressId());
		insertableValues.add(attributeGroup);
		
		leapMetaModelBean.doLeapInsert("GeoGroup", columns, insertableValues, exchange);
		
	}

	@Override
	public void createInstance(String instanceId, String ownerId,
			String shortDesc, String longDesc,
			Map<String, String> attributeMap, int mapId, String tenantId,
			Address address, String attributeGroup, Exchange exchange) throws Exception {
		
		String descId = getNextSequentialLabel("desc_id", exchange);
		String descType = "Instance";
		String localeId = "ENGLISH";
		
		if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() > 20) {
			shortDesc = longDesc.substring(0, 20);
		}
		else if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() <= 20) {
			shortDesc = longDesc;
		}
		
		// Create description
		createDescription(descId, descType, localeId, shortDesc, longDesc, exchange);
		
		String attributeId = createAttributes(attributeMap, exchange);
		
		// Creating address
		address.setAddressType("Instance");
		address.setAttributeId(attributeId);
		createAddress(address, mapId, tenantId, attributeGroup, exchange);
		
		// Creating fractional
		List<String> columns = new ArrayList<String>();
		columns.add("instance_id");
		columns.add("owner_id");
		columns.add("desc_id");
		columns.add("attribute_id");
		columns.add("address_id");
		columns.add("attribute_group");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(instanceId);
		insertableValues.add(ownerId);
		insertableValues.add(descId);
		insertableValues.add(attributeId);
		insertableValues.add(address.getAddressId());
		insertableValues.add(attributeGroup);
		
		leapMetaModelBean.doLeapInsert("Instance", columns, insertableValues, exchange);
		
	}

	@Override
	public void createGeoLoc(String geoLocId, String shortDesc,
			String longDesc, Map<String, String> attributeMap, int mapId,
			String tenantId, Address address, String attributeGroup,
			Exchange exchange) throws Exception {
		
		String descId = getNextSequentialLabel("desc_id", exchange);
		String descType = "GeoLoc";
		String localeId = "ENGLISH";
		
		if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() > 20) {
			shortDesc = longDesc.substring(0, 20);
		}
		else if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() <= 20) {
			shortDesc = longDesc;
		}
		
		// Create description
		createDescription(descId, descType, localeId, shortDesc, longDesc, exchange);
		
		String attributeId = createAttributes(attributeMap, exchange);
		
		// Creating address
		address.setAddressType("GeoLoc");
		address.setAttributeId(attributeId);
		createAddress(address, mapId, tenantId, attributeGroup, exchange);
		
		// Creating fractional
		List<String> columns = new ArrayList<String>();
		columns.add("geoloc_id");
		columns.add("desc_id");
		columns.add("attribute_id");
		columns.add("address_id");
		columns.add("attribute_group");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(geoLocId);
		insertableValues.add(descId);
		insertableValues.add(attributeId);
		insertableValues.add(address.getAddressId());
		insertableValues.add(attributeGroup);
		
		leapMetaModelBean.doLeapInsert("GeoLoc", columns, insertableValues, exchange);
		
	}

	@Override
	public void createMicroLoc(String locId, String shortDesc, String longDesc,
			Map<String, String> attributeMap, String dimId, String locCode,
			String tenantId, Address address, String locAccessId,
			String locAuthId, String containerId, int mapId,
			String locStatus, String attributeGroup, Exchange exchange) throws Exception {
		
		String descId = getNextSequentialLabel("desc_id", exchange);
		String descType = "MicroLoc";
		String localeId = "ENGLISH";
		
		if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() > 20) {
			shortDesc = longDesc.substring(0, 20);
		}
		else if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() <= 20) {
			shortDesc = longDesc;
		}
		
		// Create description
		createDescription(descId, descType, localeId, shortDesc, longDesc, exchange);
		
		String attributeId = createAttributes(attributeMap, exchange);
		
		// Creating address
		address.setAddressType("MicroLoc");
		address.setAttributeId(attributeId);
		createAddress(address, mapId, tenantId, attributeGroup, exchange);
		
		// Creating fractional
		List<String> columns = new ArrayList<String>();
		columns.add("loc_id");
		columns.add("desc_id");
		columns.add("attribute_id");
		columns.add("dim_id");
		columns.add("loc_cod");
		columns.add("address_id");
		columns.add("loc_access_id");
		columns.add("loc_auth_id");
		columns.add("attribute_group");
		columns.add("container_id");
		columns.add("map_id");
		columns.add("loc_status");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(locId);
		insertableValues.add(descId);
		insertableValues.add(attributeId);
		insertableValues.add(dimId);
		insertableValues.add(locCode);
		insertableValues.add(address.getAddressId());
		insertableValues.add(locAccessId);
		insertableValues.add(locAuthId);
		insertableValues.add(attributeGroup);
		insertableValues.add(containerId);
		insertableValues.add(mapId);
		insertableValues.add(locStatus);
		
		leapMetaModelBean.doLeapInsert("MicroLoc", columns, insertableValues, exchange);
		
		
	}

	@Override
	public void createLocationGroupAsgn(String locationGroupId, String locId,
			Exchange exchange) throws Exception {
		
		List<String> columns = new ArrayList<String>();
		columns.add("loc_group_id");
		columns.add("loc_id");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(locationGroupId);
		insertableValues.add(locId);
		
		leapMetaModelBean.doLeapInsert("Location_Group_Asgn", columns, insertableValues, exchange);
		
	}

	@Override
	public void createOwner(String ownerId, String shortDesc, String longDesc,
			Map<String, String> attributeMap, int mapId, String tenantId,
			Address address, String attributeGroup, Exchange exchange) throws Exception {
		
		String descId = getNextSequentialLabel("desc_id", exchange);
		String descType = "Owner";
		String localeId = "ENGLISH";
		
		if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() > 20) {
			shortDesc = longDesc.substring(0, 20);
		}
		else if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() <= 20) {
			shortDesc = longDesc;
		}
		
		// Create description
		createDescription(descId, descType, localeId, shortDesc, longDesc, exchange);
		
		String attributeId = createAttributes(attributeMap, exchange);
		
		// Creating address
		address.setAddressType("Owner");
		address.setAttributeId(attributeId);
		createAddress(address, mapId, tenantId, attributeGroup, exchange);
		
		// Creating fractional
		List<String> columns = new ArrayList<String>();
		columns.add("owner_id");
		columns.add("desc_id");
		columns.add("attribute_id");
		columns.add("address_id");
		columns.add("attribute_group");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(ownerId);
		insertableValues.add(descId);
		insertableValues.add(attributeId);
		insertableValues.add(address.getAddressId());
		insertableValues.add(attributeGroup);
		
		leapMetaModelBean.doLeapInsert("Owner", columns, insertableValues, exchange);
		
	}

	@Override
	public void createTenant(String tenantId, String shortDesc,
			String longDesc, Map<String, String> attributeMap, int mapId,
			Address address, String attributeGroup, Exchange exchange) throws Exception {
		
		String descId = getNextSequentialLabel("desc_id", exchange);
		String descType = "Tenant";
		String localeId = "ENGLISH";
		
		if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() > 20) {
			shortDesc = longDesc.substring(0, 20);
		}
		else if((shortDesc == null || shortDesc.isEmpty()) && longDesc.length() <= 20) {
			shortDesc = longDesc;
		}
		
		// Create description
		createDescription(descId, descType, localeId, shortDesc, longDesc, exchange);
		
		String attributeId = createAttributes(attributeMap, exchange);
		
		// Creating address
		address.setAddressType("Tenant");
		address.setAttributeId(attributeId);
		createAddress(address, mapId, tenantId, attributeGroup, exchange);
		
		// Creating fractional
		List<String> columns = new ArrayList<String>();
		columns.add("tenant_id");
		columns.add("desc_id");
		columns.add("attribute_id");
		columns.add("address_id");
		columns.add("attribute_group");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(tenantId);
		insertableValues.add(descId);
		insertableValues.add(attributeId);
		insertableValues.add(address.getAddressId());
		insertableValues.add(attributeGroup);
		
		leapMetaModelBean.doLeapInsert("Tenant", columns, insertableValues, exchange);
		
	}

	@Override
	public void createTenantMap(int mapId, String tenantId, String uomGroup,
			Exchange exchange) throws Exception {
		
		List<String> columns = new ArrayList<String>();
		columns.add("map_id");
		columns.add("tenant_id");
		columns.add("uom_group");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(mapId);
		insertableValues.add(tenantId);
		insertableValues.add(uomGroup);
		
		leapMetaModelBean.doLeapInsert("Tenant_Map", columns, insertableValues, exchange);
		
	}

	@Override
	public boolean validateLocationGroup(String locationGroupId, Exchange exchange) throws Exception {
		
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(locationGroupId);
		
		List<String> selectableColumns = new ArrayList<String>();
		selectableColumns.add("loc_group_id");
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("Location_Group", "loc_group_id=?", predicatefieldList, selectableColumns, exchange);
		
		return dataSet.next();
		
	}

	@Override
	public boolean validateInstance(String instanceId, Exchange exchange) throws Exception {
		
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(instanceId);
		
		List<String> selectableColumns = new ArrayList<String>();
		selectableColumns.add("instance_id");
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("Instance", "instance_id=?", predicatefieldList, selectableColumns, exchange);
		
		return dataSet.next();
		
	}

	@Override
	public boolean validateGeoGroup(String geoGroupId, Exchange exchange) throws Exception {
		
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(geoGroupId);
		
		List<String> selectableColumns = new ArrayList<String>();
		selectableColumns.add("geogroup_id");
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("GeoGroup", "geogroup_id=?", predicatefieldList, selectableColumns, exchange);
		
		return dataSet.next();
		
	}

	@Override
	public boolean validateGeoLoc(String geoLocId, Exchange exchange) throws Exception {
		
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(geoLocId);
		
		List<String> selectableColumns = new ArrayList<String>();
		selectableColumns.add("geoloc_id");
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("GeoLoc", "geoloc_id=?", predicatefieldList, selectableColumns, exchange);
		
		return dataSet.next();
		
	}

	@Override
	public boolean validateFractional(String fractionalId, Exchange exchange) throws Exception {
		
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(fractionalId);
		
		List<String> selectableColumns = new ArrayList<String>();
		selectableColumns.add("fractional_id");
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("Fractional", "fractional_id=?", predicatefieldList, selectableColumns, exchange);
		
		return dataSet.next();
		
	}
	
	@Override
	public boolean validateMapId(String mapId, Exchange exchange) throws Exception {
		
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(mapId);
		
		List<String> selectableColumns = new ArrayList<String>();
		selectableColumns.add("map_id");
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("Instance_Map", "map_id=?", predicatefieldList, selectableColumns, exchange);
		
		return dataSet.next();
		
	}
	
	@Override
	public boolean validateMicroLoc(String locId, Exchange exchange) throws Exception {
		
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(locId);
		
		List<String> selectableColumns = new ArrayList<String>();
		selectableColumns.add("loc_id");
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("MicroLoc", "loc_id=?", predicatefieldList, selectableColumns, exchange);
		
		return dataSet.next();
		
	}
	
	@Override
	public boolean validateOwner(String ownerId, Exchange exchange) throws Exception {
		
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(ownerId);
		
		List<String> selectableColumns = new ArrayList<String>();
		selectableColumns.add("owner_id");
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("Owner", "owner_id=?", predicatefieldList, selectableColumns, exchange);
		
		return dataSet.next();
		
	}
	
	@Override
	public boolean validateTenant(String tenantId, Exchange exchange) throws Exception {
		
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(tenantId);
		
		List<String> selectableColumns = new ArrayList<String>();
		selectableColumns.add("tenant_id");
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("Tenant", "tenant_id=?", predicatefieldList, selectableColumns, exchange);
		
		return dataSet.next();
		
	}
	
	private void createDescription(String descId, String descType,
			String localeId, String shortDesc, String longDesc, Exchange exchange) throws Exception {
		
		List<String> columns = new ArrayList<String>();
		columns.add("desc_id");
		columns.add("desc_typ");
		columns.add("locale_id");
		columns.add("short_desc");
		columns.add("long_desc");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(descId);
		insertableValues.add(descType);
		insertableValues.add(localeId);
		insertableValues.add(shortDesc);
		insertableValues.add(longDesc);
		
		leapMetaModelBean.doLeapInsert("Description", columns, insertableValues, exchange);
		
	}
	
	private String createAttributes(Map<String, String> attributeMap, Exchange exchange) throws Exception {

		String attributeId = getNextSequentialLabel("attribute_id", exchange);
		
		List<String> columns = new ArrayList<String>();
		columns.add("attribute_id");
		columns.add("attribute_type");
		columns.add("attribute_value");
		
		for(Entry<String, String> attribute : attributeMap.entrySet()) {
			
			List<Object> insertableValues = new ArrayList<Object>();
			insertableValues.add(attributeId);
			insertableValues.add(attribute.getKey());
			insertableValues.add(attribute.getValue());
			
			leapMetaModelBean.doLeapInsert("Attribute", columns, insertableValues, exchange);
			
		}
		
		return attributeId;
	}
	
	private void createAddress(Address address, int mapId, String tenantId, String attributeGroup, Exchange exchange) throws Exception {
		
		String addressId = getNextSequentialLabel("address_id", exchange);
		address.setAddressId(addressId);
		
		createAddressHdr(address, mapId, tenantId, exchange);
		createAddressDetail(address, exchange);
		
	}
	
	private void createAddressHdr(Address address, int mapId, String tenantId, Exchange exchange) throws Exception {
		
		List<String> columns = new ArrayList<String>();
		columns.add("address_id");
		columns.add("map_id");
		columns.add("tenant_id");
		columns.add("address_type");
		columns.add("first_name");
		columns.add("middle_name");
		columns.add("last_name");
		columns.add("host_key");
		columns.add("attribute_group");
		columns.add("attribute_id");
		columns.add("address_code");
		columns.add("latitude");
		columns.add("longitude");
		
		List<Object> insertableValues = new ArrayList<Object>();
		insertableValues.add(address.getAddressId());
		insertableValues.add(mapId);
		insertableValues.add(tenantId);
		insertableValues.add(address.getAddressType());
		insertableValues.add(address.getFirstName());
		insertableValues.add(address.getMiddleName());
		insertableValues.add(address.getLastName());
		insertableValues.add(address.getHostKey());
		insertableValues.add(address.getAttributeGroup());
		insertableValues.add(address.getAttributeId());
		insertableValues.add(address.getAddressCode());
		insertableValues.add(address.getLatitude());
		insertableValues.add(address.getLongitude());
		
		leapMetaModelBean.doLeapInsert("Address_Hdr", columns, insertableValues, exchange);
			
	}
	
	private void createAddressDetail(Address address, Exchange exchange) throws Exception {
		
		String addressId = address.getAddressId();
		Map<String, String> addressDetailMap = address.getAddressDetailMap();
		
		List<String> columns = new ArrayList<String>();
		columns.add("address_id");
		columns.add("column_name");
		columns.add("column_value");
		
		for(Entry<String, String> attribute : addressDetailMap.entrySet()) {
			
			List<Object> insertableValues = new ArrayList<Object>();
			insertableValues.add(addressId);
			insertableValues.add(attribute.getKey());
			insertableValues.add(attribute.getValue());
			
			leapMetaModelBean.doLeapInsert("Address_Dtl", columns, insertableValues, exchange);
			
		}
		
	}
	
	/*################# List Locations Services #################*/
	
	@Override
	public List<Map<String, Object>> listMicroLoc(String locIdArg, Exchange exchange) throws Exception {
		JdbcDataContext dataContext = leapMetaModelBean.getJdbcDataContext(exchange);
		
		Table microLoc = dataContext.getTableByQualifiedLabel("MicroLoc");
		Table description = dataContext.getTableByQualifiedLabel("Description");

		Column microLoc_locId = microLoc.getColumnByName("loc_id");
		Column microLoc_descId = microLoc.getColumnByName("desc_id");
		Column description_descId = description.getColumnByName("desc_id");
		
		DataSet dataSet = null; 
		
		if(locIdArg == null || locIdArg.isEmpty()) {
			dataSet = dataContext.query().from(microLoc).and(description).selectAll().where(microLoc_descId).eq(description_descId).execute();
		} else {
			dataSet = dataContext.query().from(microLoc).and(description).selectAll().where(microLoc_descId).eq(description_descId).and(microLoc_locId).eq(locIdArg).execute();
		}
		
		List<Map<String, Object>> microLocList = (List<Map<String, Object>>) DatabaseUtils.getRowMapList(dataSet);
		for(Map<String, Object> microLocMap : microLocList) {
			Map<String, Object> addressMap = getAddress(microLocMap.get("address_id") != null ? microLocMap.get("address_id").toString() : null, exchange);
			List<Map<String, String>> attributeMap = getAttributes(microLocMap.get("attribute_id") != null ? microLocMap.get("attribute_id").toString() : null, exchange);
			microLocMap.put("address", addressMap);
			microLocMap.put("attributes", attributeMap);
			microLocMap.put("location_groups", getLocationGroup(microLocMap.get("loc_id") != null ? microLocMap.get("loc_id").toString() : null, exchange));
		}
		return microLocList;
	}
	
	@Override
	public List<Map<String, Object>> listLocationGroup(String locGroupIdArg, Exchange exchange) throws Exception {
		JdbcDataContext dataContext = leapMetaModelBean.getJdbcDataContext(exchange);
		
		Table locationGroup = dataContext.getTableByQualifiedLabel("Location_Group");
		Table description = dataContext.getTableByQualifiedLabel("Description");

		Column locationGroup_locGrpId = locationGroup.getColumnByName("loc_group_id");
		Column locationGroup_descId = locationGroup.getColumnByName("desc_id");
		Column description_descId = description.getColumnByName("desc_id");
		
		DataSet dataSet = null; 
		
		if(locGroupIdArg == null || locGroupIdArg.isEmpty()) {
			dataSet = dataContext.query().from(locationGroup).and(description).selectAll().where(locationGroup_descId).eq(description_descId).execute();
		} else {
			dataSet = dataContext.query().from(locationGroup).and(description).selectAll().where(locationGroup_descId).eq(description_descId).and(locationGroup_locGrpId).eq(locGroupIdArg).execute();
		}
		
		List<Map<String, Object>> locGroupList = (List<Map<String, Object>>) DatabaseUtils.getRowMapList(dataSet);
		for(Map<String, Object> locGroupMap : locGroupList) {
			List<Map<String, String>> attributeMap = getAttributes(locGroupMap.get("attribute_id") != null ? locGroupMap.get("attribute_id").toString() : null, exchange);
			locGroupMap.put("attributes", attributeMap);
		}
		return locGroupList;
		
	}

	private List<Map<String, Object>> getLocationGroup(String locId, Exchange exchange) throws Exception {
		JdbcDataContext dataContext = leapMetaModelBean.getJdbcDataContext(exchange);
		
		Table table = dataContext.getTableByQualifiedLabel("Location_Group_Asgn");
		Query query = new Query().from(table).where("loc_id='"+locId+"'").select("loc_group_id");
		DataSet dataSet = dataContext.executeQuery(query);
		
		List<Map<String, Object>> locGroupList = new ArrayList<Map<String, Object>>();
		
		while(dataSet.next()) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			Row row = dataSet.getRow();
			map.put("loc_group_id", row.getValue(table.getColumnByName("loc_group_id")).toString());
			locGroupList.add(map);
		}
		
		return locGroupList;
	}

	private List<Map<String, String>> getAttributes(String attributeId, Exchange exchange) throws Exception {
		if(attributeId == null || attributeId.isEmpty()) {
			return new ArrayList<Map<String,String>>();
		}
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(attributeId);
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("Attribute", "attribute_id=?", predicatefieldList, null, exchange);

		List<Map<String, String>> microLocAttributeList = new ArrayList<Map<String, String>>();

		while (dataSet.next()) {
			Map<String, String> microLocAttributeMap = new LinkedHashMap<String, String>();
			Row row = dataSet.getRow();
			String key = row.getValue(1).toString();
			String value = row.getValue(2) != null ? row.getValue(2).toString() : null;
			microLocAttributeMap.put("attribute_name", key);
			microLocAttributeMap.put("attribute_value", value);
			microLocAttributeList.add(microLocAttributeMap);
		}

		return microLocAttributeList;
	}
	
	private List<Map<String, String>> getAddressDtl(String addressId, Exchange exchange) throws Exception {
		
		List<String> predicatefieldList = new ArrayList<String>();
		predicatefieldList.add(addressId);
		
		DataSet dataSet = leapMetaModelBean.doLeapSelect("Address_Dtl", "address_id=?", predicatefieldList, null, exchange);
		
		List<Map<String, String>> addressDtlList = new ArrayList<Map<String, String>>();
		
		while (dataSet.next()) {
			Map<String, String> addressDtlMap = new LinkedHashMap<String, String>();
			Row row = dataSet.getRow();
			String key = row.getValue(2).toString();
			String value = row.getValue(3) != null ? row.getValue(3).toString() : null;
			addressDtlMap.put("column_name", key);
			addressDtlMap.put("column_value", value);
			addressDtlList.add(addressDtlMap);
		}
		
		return addressDtlList;
	}
	
	private Map<String, Object> getAddress(String addressId, Exchange exchange) throws Exception {

		if(addressId == null || addressId.isEmpty()) {
			return new HashMap<String, Object>();
		}
		JdbcDataContext dataContext = leapMetaModelBean.getJdbcDataContext(exchange);
		
		Table table = dataContext.getTableByQualifiedLabel("Address_Hdr");
		Query query = new Query().from(table).where("address_id='"+addressId+"'").selectAll();
		
		DataSet dataSet = dataContext.executeQuery(query);
		
		Map<String, Object> addressHdrMap = new LinkedHashMap<String, Object>();
		
		while(dataSet.next()) {
			Row row = dataSet.getRow();
			addressHdrMap.put("map_id", row.getValue(table.getColumnByName("map_id")) != null ? row.getValue(table.getColumnByName("map_id")).toString() : null);
			addressHdrMap.put("tenant_id", row.getValue(table.getColumnByName("tenant_id")).toString());
			addressHdrMap.put("address_type", row.getValue(table.getColumnByName("address_type")).toString());
			addressHdrMap.put("first_name", row.getValue(table.getColumnByName("first_name")) != null ? row.getValue(table.getColumnByName("first_name")).toString() : null);
			addressHdrMap.put("middle_name", row.getValue(table.getColumnByName("middle_name")) != null ? row.getValue(table.getColumnByName("middle_name")).toString() : null);
			addressHdrMap.put("last_name", row.getValue(table.getColumnByName("last_name")) != null ? row.getValue(table.getColumnByName("last_name")).toString() : null);
			addressHdrMap.put("host_key", row.getValue(table.getColumnByName("host_key")) != null ? row.getValue(table.getColumnByName("host_key")).toString() : null);
			addressHdrMap.put("attribute_group", row.getValue(table.getColumnByName("attribute_group")) != null ? row.getValue(table.getColumnByName("attribute_group")).toString() : null);
			String attributeId = row.getValue(table.getColumnByName("attribute_id")) != null ? row.getValue(table.getColumnByName("attribute_id")).toString() : null;
			addressHdrMap.put("attributes", getAttributes(attributeId, exchange));
			addressHdrMap.put("address_code", row.getValue(table.getColumnByName("address_code")) != null ? row.getValue(table.getColumnByName("address_code")).toString() : null);
			addressHdrMap.put("latitude", row.getValue(table.getColumnByName("latitude")) != null ? String.valueOf(row.getValue(table.getColumnByName("latitude"))) : null);
			addressHdrMap.put("longitude", row.getValue(table.getColumnByName("longitude")) != null ? String.valueOf(row.getValue(table.getColumnByName("longitude"))) : null);
			addressHdrMap.put("address_group_id", row.getValue(table.getColumnByName("address_group_id")) != null ? row.getValue(table.getColumnByName("address_group_id")).toString() : null);
		}
		
		List<Map<String, String>> addressDtl = getAddressDtl(addressId, exchange);
		addressHdrMap.put("address_detail", addressDtl);
		
		return addressHdrMap;
	}

	public String getNextSequentialLabel(String labelId, Exchange exchange) throws Exception{
		
		String integerSuffix=null;
		String label = null;
		
		try {
			
			// Get values from database and build label Id
			
			List<String> predicatefieldList = new ArrayList<String>();
			predicatefieldList.add(labelId);
			
			Map<String, String> rowMap = (Map<String, String>) DatabaseUtils.getRowMap(leapMetaModelBean.doLeapSelect("Sequence", "label_id=?", predicatefieldList, null, exchange));
			
			String prefix = rowMap.get("prefix");
			int totalLength = Integer.valueOf(rowMap.get("total_length"));
			int newValue = Integer.valueOf(rowMap.get("cur_val")) + Integer.valueOf(rowMap.get("increment_by"));
			
			integerSuffix = (new Integer(newValue)).toString();
			int embeddedZeroLength = totalLength - (prefix.length() + integerSuffix.length());
			
			for(int i=0; i<embeddedZeroLength; i++) {
				integerSuffix = "0".concat(integerSuffix);
			}
			label = prefix.concat(integerSuffix);
			
			// Update cur_val into database
			
			List<Object> predicatefieldListUpdate = new ArrayList<Object>();
			predicatefieldListUpdate.add(label);
			
			List<String> updatableColumns = new LinkedList<String>();
			updatableColumns.add("cur_val");
			
			List<Object> updatableValues = new LinkedList<Object>();
			updatableValues.add(newValue);
			
			leapMetaModelBean.doLeapUpdate("Sequence", "label_id=?", predicatefieldListUpdate, updatableColumns, updatableValues, exchange);
			
			
		} catch (DataAccessException dae){
			dae.printStackTrace();
		}
		return label;
	}
}