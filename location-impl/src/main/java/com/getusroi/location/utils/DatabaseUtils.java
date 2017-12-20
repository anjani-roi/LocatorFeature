package com.getusroi.location.utils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.query.SelectItem;

public class DatabaseUtils {
	
	public static String getColumnValue(DataSet dataSet) {
		Object obj = null;
		while (dataSet.next()) {
			Row row = dataSet.getRow();
			obj = row.getValue(0);
		}
		return obj != null ? obj.toString() : null;
	}
	
	public static Map<String, ? extends Object> getRowMap(DataSet dataSet) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		while (dataSet.next()) {
			Row row = dataSet.getRow();
			SelectItem[] selectItems = row.getSelectItems();
			for (SelectItem selectItem : selectItems) {
				map.put(selectItem.getColumn().getName(), row.getValue(selectItem.getColumn()) != null ? row.getValue(selectItem.getColumn()).toString() : null);
			}
		}
		return map;
	}
	
	public static Object getRowMapList(DataSet dataSet) {
		List<Map<String, ? extends Object>> mapList = new LinkedList<Map<String, ? extends Object>>();
		while (dataSet.next()) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			Row row = dataSet.getRow();
			SelectItem[] selectItems = row.getSelectItems();
			for (SelectItem selectItem : selectItems) {
				map.put(selectItem.getColumn().getName(), row.getValue(selectItem.getColumn()) != null ? row.getValue(selectItem.getColumn()).toString() : null);
			}
			mapList.add(map);
		}
		return mapList;
	}
}
