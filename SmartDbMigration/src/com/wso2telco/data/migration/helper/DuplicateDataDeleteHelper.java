package com.wso2telco.data.migration.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wso2telco.data.migration.util.DbConnection;

public class DuplicateDataDeleteHelper {
	
	public static void deleteDuplicatesUmPermissionTable(){
		String query = "Select s.UM_ID from "+DbConnection.getIsDbName()+".um_permission s inner join "+DbConnection.getMigDbName()+".um_permission m on m.UM_RESOURCE_ID = s.UM_RESOURCE_ID AND m.UM_ACTION = s.UM_ACTION";	      
		String deleteQuery = "Delete From um_permission where UM_ID = ?";
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				con = DbConnection.getSmartDbConnection();
				PreparedStatement preparedStmt = con.prepareStatement(deleteQuery);
				preparedStmt.setInt(1, rs.getInt(1));
				preparedStmt.execute();
			}
			System.out.println(" ~~~ Smart DB Delete * duplicates from um_permission");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void deleteDuplicatesUmCalimTable(){
		String query = "Select s.UM_ID from "+DbConnection.getMigDbName()+".um_claim m inner join "+DbConnection.getIsDbName()+".um_claim s on m.UM_CLAIM_URI = s.UM_CLAIM_URI;";	      
		String deleteQuery = "Delete From um_claim where UM_ID = ?";
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				con = DbConnection.getSmartDbConnection();
				PreparedStatement preparedStmt = con.prepareStatement(deleteQuery);
				preparedStmt.setInt(1, rs.getInt(1));
				preparedStmt.execute();
			}
			System.out.println(" ~~~ Smart DB Delete * duplicates from um_claim");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

/*

*/