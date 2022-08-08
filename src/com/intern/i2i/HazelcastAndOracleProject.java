package com.intern.i2i;

public class HazelcastAndOracleProject {

	public static void main(String[] args) {
		
		HazelcastHelper hz = new HazelcastHelper();
		DbHelper dbHelper = new DbHelper();
		System.out.println("Put 20.000 data to the IMap " + hz.calculatePutElapsedTime(20000) + " ms");
		System.out.println("Get 20.000 data from the IMap " + hz.calculateGetElapsedTime(20000) + " ms\n");
		
		System.out.println("Put 100.000 data to the IMap " + hz.calculatePutElapsedTime(100000) + " ms");
		System.out.println("Get 100.000 data from the IMap " + hz.calculateGetElapsedTime(100000) + " ms\n");

		dbHelper.createHazelcastTable();
		System.out.println("Insert 20000 data to the database " + dbHelper.insertData(20000) + " ms");
		System.out.println("Select 20000 data from the database " + dbHelper.selectData(20000) + " ms\n");
		dbHelper.truncateTable();
		System.out.println("Insert 100000 data to the database " + dbHelper.insertData(100000) + " ms");
		System.out.println("Select 100000 data from the database " + dbHelper.selectData(100000) + " ms\n");
		dbHelper.truncateTable();
	}

}
