/**
 * Copyright (c) 2011-2016, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tcll.core.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Ehcache缓存工厂
 */
public class EhcacheFactory extends BaseCacheFactory {
	
	private static CacheManager cacheManager;
	private static volatile Object locker = new Object();
	private static final Logger log = LoggerFactory.getLogger(EhcacheFactory.class);
	
	private static CacheManager getCacheManager() {
		if (cacheManager == null) {
			synchronized (EhcacheFactory.class) {
				if (cacheManager == null) {
					cacheManager = CacheManager.create();
				}
			}
		}
		return cacheManager;
	}
	
	static Cache getOrAddCache(String cacheName) {
		CacheManager cacheManager = getCacheManager();
		//获取缓存对象
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			synchronized(locker) {
				cache = cacheManager.getCache(cacheName);
				if (cache == null) {
					log.warn("无法找到缓存 [" + cacheName + "]的配置, 使用默认配置.");
					cacheManager.addCacheIfAbsent(cacheName);
					cache = cacheManager.getCache(cacheName);
					log.debug("缓存 [" + cacheName + "] 启动.");
				}
			}
		}
		return cache;
	}
	//.put 将元素添加到缓存
	public void put(String cacheName, Object key, Object value) {
		//new Element  创建元素
		getOrAddCache(cacheName).put(new Element(key, value));
	}
	//获取缓存
	@SuppressWarnings("unchecked")
	public <T> T get(String cacheName, Object key) {
		Element element = getOrAddCache(cacheName).get(key);
		return element != null ? (T)element.getObjectValue() : null;
	}
	//返回缓存中所有元素键的列表，不管它们是否过期
	@SuppressWarnings("rawtypes")
	public List getKeys(String cacheName) {
		//返回缓存中所有元素键的列表，不管它们是否过期
		return getOrAddCache(cacheName).getKeys();
	}
	//根据key删除元素
	public void remove(String cacheName, Object key) {
		getOrAddCache(cacheName).remove(key);
	}
	//删除所有元素
	public void removeAll(String cacheName) {
		getOrAddCache(cacheName).removeAll();
	}

}
