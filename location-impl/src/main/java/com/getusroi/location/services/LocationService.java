package com.getusroi.location.services;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;

import com.getusroi.location.domain.Address;
import com.getusroi.location.domain.MicroLoc;
import com.getusroi.location.exceptions.WMSLiteDAOException;



public interface LocationService {

	void createLocationGroup(String locationGroupId,
			Map<String, String> attributeMap, String shortDesc,
			String longDesc, String locAccessId, int absPriority,
			int deltaPriority, String locAuthId, String attributeGroup,
			Exchange exchange) throws Exception;

	void createInstanceMap(String instanceId, String uomGroup,
			String geoGroupId, String geoLocId, String fractionalId, Exchange exchange) throws Exception;

	void createFractional(String fractionalId, String shortDesc,
			String longDesc, Map<String, String> attributeMap, int mapId,
			String tenantId, Address address, String attributeGroup,
			Exchange exchange) throws Exception;

	void createGeoGroup(String geoGroupId, String shortDesc, String longDesc,
			Map<String, String> attributeMap, int mapId, String tenantId,
			Address address, String attributeGroup, Exchange exchange) throws Exception;

	void createInstance(String instanceId, String ownerId, String shortDesc,
			String longDesc, Map<String, String> attributeMap, int mapId,
			String tenantId, Address address, String attributeGroup,
			Exchange exchange) throws Exception;

	void createGeoLoc(String geoLocId, String shortDesc, String longDesc,
			Map<String, String> attributeMap, int mapId, String tenantId,
			Address address, String attributeGroup, Exchange exchange) throws Exception;

	void createMicroLoc(String locId, String shortDesc, String longDesc,
			Map<String, String> attributeMap, String dimId, String locCode,
			String tenantId, Address address, String locAccessId,
			String locAuthId, String containerId, int mapId,
			String locStatus, String attributeGroup, Exchange exchange) throws Exception;

	void createLocationGroupAsgn(String locationGroupId, String locId,
			Exchange exchange) throws Exception;

	void createOwner(String ownerId, String shortDesc, String longDesc,
			Map<String, String> attributeMap, int mapId, String tenantId,
			Address address, String attributeGroup, Exchange exchange) throws Exception;

	void createTenant(String tenantId, String shortDesc, String longDesc,
			Map<String, String> attributeMap, int mapId, Address address,
			String attributeGroup, Exchange exchange) throws Exception;

	void createTenantMap(int mapId, String tenantId, String uomGroup,
			Exchange exchange) throws Exception;

	boolean validateLocationGroup(String locationGroupId, Exchange exchange) throws Exception;

	boolean validateInstance(String instanceId, Exchange exchange) throws Exception;

	boolean validateGeoGroup(String geoGroupId, Exchange exchange) throws Exception;

	boolean validateGeoLoc(String geoLocId, Exchange exchange) throws Exception;

	boolean validateFractional(String fractionalId, Exchange exchange) throws Exception;

	boolean validateMicroLoc(String locId, Exchange exchange) throws Exception;

	boolean validateOwner(String ownerId, Exchange exchange) throws Exception;

	boolean validateTenant(String tenantId, Exchange exchange) throws Exception;

	boolean validateMapId(String mapId, Exchange exchange) throws Exception;

	List<Map<String, Object>> listMicroLoc(String microLoc, Exchange exchange) throws Exception;

	List<Map<String, Object>> listLocationGroup(String logGroupIdArg, Exchange exchange) throws Exception;

}
