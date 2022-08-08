package com.intern.i2i;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class HazelcastHelper {
	
	private HazelcastInstance hz = null;
	private Config cnfg = new Config();
	private IMap<Integer, Integer> map;
	
	public HazelcastHelper() {
		cnfg.setClusterName("dev");
		hz = HazelcastClient.newHazelcastClient();
		map = hz.getMap("numbers");
	}
	
	public long calculatePutElapsedTime(int numbers) {
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		for (int i = 0; i < numbers; i++) {
			map.put(i, i);
		}
		endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

	public long calculateGetElapsedTime(int numbers) {
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		for (int i = 0; i < numbers; i++) {
			map.get(i);
		}
		endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
}
