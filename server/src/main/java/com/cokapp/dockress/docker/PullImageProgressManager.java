package com.cokapp.dockress.docker;

import java.util.Map;

import com.cokapp.cokits.util.JsonUtils;
import com.github.dockerjava.api.model.ResponseItem.ProgressDetail;

import jersey.repackaged.com.google.common.collect.Maps;

public class PullImageProgressManager {
	
	private static Map<String, ProgressDetail> progresses = Maps.newHashMap();
	
	public static void addProgress(String id, ProgressDetail detail){
		if(progresses.containsKey(id)){
			progresses.remove(id);
		}
		progresses.put(id, detail);
	}
	public static void removeProgress(String id){
		progresses.remove(id);
	}
	
	public static String getJson(){
		if(progresses.isEmpty()){
			return "{}";
		}	
		
		return JsonUtils.writeValueAsString(progresses);
	}
	
	
	
}
