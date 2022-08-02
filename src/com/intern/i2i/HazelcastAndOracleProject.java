package com.intern.i2i;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;


public class HazelcastAndOracleProject {

	private static long calculatePutElapsedTime(HazelcastInstance hz, int numbers) {

		IMap<Integer, Integer> map = hz.getMap("put " + numbers + " numbers");
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		for (int i = 0; i < numbers; i++) {
			map.put(i, i);
		}
		endTime = System.currentTimeMillis();

		return endTime - startTime;
	}

	private static long calculateGetElapsedTime(HazelcastInstance hz, int numbers) {
		IMap<Integer, Integer> map = hz.getMap("get " + numbers + " numbers");
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		for (int i = 0; i < numbers; i++) {
			map.put(i, i);
		}
		endTime = System.currentTimeMillis();

		return endTime - startTime;
	}

	

	public static void main(String[] args) {
		HazelcastInstance hz = HazelcastClient.newHazelcastClient();

		DbHelper dbHelper = new DbHelper();
		System.out.println("Put 20.000 data to the IMap " + calculatePutElapsedTime(hz, 20000) + "ms");
		System.out.println("Get 20.000 data from the IMap " + calculateGetElapsedTime(hz, 20000) + "ms\n");
		
		System.out.println("Put 100.000 data to the IMap " + calculatePutElapsedTime(hz, 100000) + "ms");
		System.out.println("Get 100.000 data from the IMap " + calculateGetElapsedTime(hz, 100000) + "ms\n");

		dbHelper.createHazelcastTable();
		System.out.println("Insert 20000 data to the database " + dbHelper.insertData(20000) + "ms");
		System.out.println("Select 20000 data from the database " + dbHelper.selectData(20000) + "ms\\n");
		dbHelper.truncateTable();
		System.out.println("Insert 100000 data to the database " + dbHelper.insertData(100000) + "ms");
		System.out.println("Select 100000 data from the database " + dbHelper.selectData(100000) + "ms\n");
		dbHelper.truncateTable();
	}

}
