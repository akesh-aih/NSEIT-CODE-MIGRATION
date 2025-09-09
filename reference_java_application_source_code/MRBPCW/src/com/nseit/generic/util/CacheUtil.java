package com.nseit.generic.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil
{
	private Map<String, String> passwordEncKeyMap = new ConcurrentHashMap<String, String>(1000, 0.75F, 1000);
	
	
}
