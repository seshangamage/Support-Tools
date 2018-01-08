package com.wso2telco.data.migration.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wso2telco.data.migration.util.DbConnection;

public class SmartDbDataExtractionHelper {

	public static ResultSet getAllUseresinUmUserTable() {

		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_user");
			System.out.println(" ~~~ Smart DB Select * from um_user");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static ResultSet getUmUserAttributeforSpecificUser(int um_user_id) {

		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_user_attribute where UM_USER_ID =" + um_user_id);
			System.out.println(" ~~~ Smart DB Selecting data from um_user_attribute for UM_USER_ID =" + um_user_id);
			// while(rs.next()) {
			// System.out.println(rs.getString(2)+" "+rs.getString(3)+"
			// "+rs.getString(4)+" "+rs.getInt(5)+" "+rs.getInt(6));
			// }
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static ResultSet getAllUmRoleData() {

		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_role where  UM_ROLE_NAME  NOT Like 'admin'");
			System.out.println(" ~~~ Smart DB Select * from um_role ");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ResultSet getAllUmUserRoleData() {

		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_user_role");
			System.out.println(" ~~~ Smart DB Select * from um_role ");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static ResultSet getAllUmSharedUserRoleData() {
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_shared_user_role");
			System.out.println(" ~~~ Smart DB Select * from um_shared_user_role ");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	// Role related data Tables ~~~	
	
	public static ResultSet getAllUmHybridRole(){
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_hybrid_role");
			System.out.println(" ~~~ Smart DB Select * from um_hybrid_role");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}
	
	public static ResultSet getAllUmHybridUserRole(){
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_hybrid_user_role");
			System.out.println(" ~~~ Smart DB Select * from um_hybrid_user_role");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}
	
	public static ResultSet getAllUmDomain(){
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_domain");
			System.out.println(" ~~~ Smart DB Select * from um_domain");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}
	
	public static ResultSet getAllUmPermision(){
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_permission");
			System.out.println(" ~~~ Smart DB Select * from um_permission");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}
	
	public static ResultSet getAllUmRolePermision(){
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_role_permission");
			System.out.println(" ~~~ Smart DB Select * from um_role_permission");
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;		
	}
	
	public static ResultSet getAllUmUserPermision(){
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_user_permission");
			System.out.println(" ~~~ Smart DB Select * from um_user_permission" + rs.last());
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;		
	}
	
	
	// Diletc related Data
	public static ResultSet getAllUmDilect(){
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_dialect");
			System.out.println(" ~~~ Smart DB Select * from um_dialect");
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;		
	}
	
	public static ResultSet getAllUmClaim(){
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_claim");
			System.out.println(" ~~~ Smart DB Select * from um_claim");
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;		
	}
	
	//System role related Data
	
	public static ResultSet getAllUmSystemRole(){
		return null;
	}
	
	
	public static ResultSet getAllUmHybridRemeberMe(){
		Statement stmt;
		ResultSet rs = null;
		try {
			Connection con = DbConnection.getSmartDbConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("Select * from um_hybrid_remember_me");
			System.out.println(" ~~~ Smart DB Select * from um_hybrid_remember_me");
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;		
	}
	
	

}
