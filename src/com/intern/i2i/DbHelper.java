package com.intern.i2i;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbHelper {

	private String dbURL = "jdbc:oracle:thin:@localhost:1521:TEST";
	private String username = "UMIT";
	private String password = "SAMUR";
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet results;
	private String query;

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbURL, username, password);
	}

	public void showErrorMessages(SQLException exception) {
		System.out.println("Error: " + exception.getMessage());
		System.out.println("Error code: " + exception.getErrorCode());
	}

	public void createHazelcastTable() {
		conn = null;
		stmt = null;
		query = "";
		
		try {
			conn = getConnection();
			if (conn == null) {
				System.out.println("There is an connecting error");
				return;
			}
			stmt = conn.createStatement();
			//query = "CREATE TABLE Hazelcast(Numbers INT)";
			
			query = "DECLARE\r\n" 
					+ "sql_stmt long;\r\n" 
					+ "BEGIN\r\n" 
					+ " sql_stmt:='CREATE TABLE Hazelcast (Numbers INT)';\r\n"
					+ " EXECUTE IMMEDIATE sql_stmt;\r\n" 
					+ " EXCEPTION\r\n"
					+ " WHEN OTHERS THEN\r\n" 
					+ " IF SQLCODE = -955 THEN\r\n"
					+ " 	NULL;\r\n" 
					+ " ELSE\r\n" 
					+ " 	RAISE;\r\n"
					+ " END IF;\r\n" 
					+ "END;";
			stmt.execute(query);
			
			conn.close();
		} catch (SQLException e) {
			showErrorMessages(e);
		}
	}

	public long insertData(int numbersOfData) {
		conn = null;
		pstmt = null;
		long startTime, endTime, elapsedTime = 0;
		query = "";

		try {
			conn = getConnection();
			query = "INSERT INTO Hazelcast VALUES(?)";
			pstmt = conn.prepareStatement(query);
			startTime = System.currentTimeMillis();
			for (int i = 0; i < numbersOfData; i++) {

				pstmt.setInt(1, i);
				pstmt.executeUpdate();
			}
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			showErrorMessages(e);
		}
		return elapsedTime;
	}

	public long selectData(int numbersOfData) {
		conn = null;
		stmt = null;
		results = null;
		long startTime, endTime, elapsedTime = 0;
		query = "";
		try {
			conn = getConnection();
			stmt = conn.createStatement();

			query = "SELECT Numbers FROM Hazelcast";
			results = stmt.executeQuery(query);

			ArrayList<Integer> arrayListNumber = new ArrayList<Integer>();
			startTime = System.currentTimeMillis();
			while (results.next()) {
				arrayListNumber.add(results.getInt(1));
			}
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			showErrorMessages(e);
		}
		return elapsedTime;
	}

	/*
	public void dropTableFromDb() {
		conn = null;
		//DbHelper helper = new DbHelper();
		stmt = null;
		query = "";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			query = "DROP TABLE Hazelcast";
			stmt.execute(query);

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			showErrorMessages(e);
		}
	}
*/
	public void truncateTable() {
		conn = null;
		stmt = null;
		query = "";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			query = "TRUNCATE TABLE Hazelcast";
			stmt.execute(query);

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			showErrorMessages(e);
		}
	}
}
