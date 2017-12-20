package com.getusroi.location.domain;

import java.util.Map;

public class Address {

	private String addressId;
	private Map<String, String> addressDetailMap;
	private String addressType;
	private String firstName;
	private String middleName;
	private String lastName;
	private String hostKey;
	private String attributeGroup;
	private String attributeId;
	private String addressCode;
	private float latitude;
	private float longitude;
	private String addressGroupId;
	
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public Map<String, String> getAddressDetailMap() {
		return addressDetailMap;
	}
	public void setAddressDetailMap(Map<String, String> addressDetailMap) {
		this.addressDetailMap = addressDetailMap;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getHostKey() {
		return hostKey;
	}
	public void setHostKey(String hostKey) {
		this.hostKey = hostKey;
	}
	public String getAttributeGroup() {
		return attributeGroup;
	}
	public void setAttributeGroup(String attributeGroup) {
		this.attributeGroup = attributeGroup;
	}
	public String getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getAddressGroupId() {
		return addressGroupId;
	}
	public void setAddressGroupId(String addressGroupId) {
		this.addressGroupId = addressGroupId;
	}

}
