package com.wso2telco.data.migration.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

	private static Connection dbSmartCon;
	private static Connection dbMigIdsCon;
	
	private static String isDbName; 
	private static String migDbName; 
	private static String dbUserName; 
	private static String dbPassword; 
	
	

	public static String getIsDbName() {
		return isDbName;
	}

	public static void setIsDbName(String isDbName) {
		DbConnection.isDbName = isDbName;
	}

	public static String getMigDbName() {
		return migDbName;
	}

	public static void setMigDbName(String migDbName) {
		DbConnection.migDbName = migDbName;
	}

	public static String getDbUserName() {
		return dbUserName;
	}

	public static void setDbUserName(String dbUserName) {
		DbConnection.dbUserName = dbUserName;
	}

	public static String getDbPassword() {
		return dbPassword;
	}

	public static void setDbPassword(String dbPassword) {
		DbConnection.dbPassword = dbPassword;
	}

	public static Connection getSmartDbConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if (dbSmartCon == null) {
				dbSmartCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+isDbName, dbUserName, dbPassword);
				return dbSmartCon;
			} else {
				return dbSmartCon;
			}

		} catch (Exception e) {
			System.out.println(e);
			return dbSmartCon;
		}
	}
	
	public static Connection getMigIDSDbConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if (dbMigIdsCon == null) {
				dbMigIdsCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+migDbName, dbUserName, dbPassword);
				return dbMigIdsCon;
			} else {
				return dbMigIdsCon;
			}

		} catch (Exception e) {
			System.out.println(e);
			return dbMigIdsCon;
		}
	}

}
