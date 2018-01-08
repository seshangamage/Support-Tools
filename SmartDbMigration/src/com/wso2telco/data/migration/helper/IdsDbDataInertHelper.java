package com.wso2telco.data.migration.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wso2telco.data.migration.util.DbConnection;

public class IdsDbDataInertHelper {

	public static int getUmUserIdFromUmUserTable(String userName) {
		try {
			Connection con = DbConnection.getSmartDbConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select UM_ID from um_user where UM_USER_NAME ='" + userName + "'");
			rs.next();
			int um_id = rs.getInt(1);
			System.out.println("Returning UM_ID = " + um_id + " for Username :" + userName);
			return um_id;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	public static void insertDatatoUmUserTable(ResultSet rs) {
		String query = " insert into um_user (UM_USER_NAME, UM_USER_PASSWORD, UM_SALT_VALUE, UM_REQUIRE_CHANGE, UM_CHANGED_TIME,UM_TENANT_ID)"
				+ " values (?, ?, ?, ?, ?, ?)";
		try {
			Connection con = DbConnection.getMigIDSDbConnection();
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, rs.getString(2));
			preparedStmt.setString(2, rs.getString(3));
			preparedStmt.setString(3, rs.getString(4));
			preparedStmt.setInt(4, rs.getInt(5));
			preparedStmt.setDate(5, rs.getDate(6));
			preparedStmt.setInt(6, rs.getInt(7));
			preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insertDataoUmUserAttributeTable(ResultSet rs, int um_user_id) {

		System.out.println("Inserting Data to IDS um_user_attribute");
		String query = " insert into um_user_attribute (UM_ATTR_NAME,UM_ATTR_VALUE,UM_PROFILE_ID,UM_USER_ID,UM_TENANT_ID)"
				+ " values (?, ?, ?, ?, ?)";
		try {
			Connection con = DbConnection.getMigIDSDbConnection();
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, rs.getString(2));
			preparedStmt.setString(2, rs.getString(3));
			preparedStmt.setString(3, rs.getString(4));
			preparedStmt.setInt(4, um_user_id);
			preparedStmt.setInt(5, rs.getInt(6));
			preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insertDataoUmRoleTable(ResultSet rs) {

		System.out.println("Inserting Data to IDS um_role");
		String query = " insert into um_role (UM_ROLE_NAME,UM_TENANT_ID,UM_SHARED_ROLE)" + " values (?, ?, ?)";
		try {
			Connection con = DbConnection.getMigIDSDbConnection();
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, rs.getString(2));
			preparedStmt.setInt(2, rs.getInt(3));
			preparedStmt.setInt(3, rs.getInt(4));
			preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insertDataToUmUserRoleTable(ResultSet rs) {

		System.out.println("Inserting Data to IDS um_user_role");
		String migdbtest = DbConnection.getMigDbName();
		String smarttestdb = DbConnection.getIsDbName();

		String query = " insert into um_user_role (UM_ROLE_ID,UM_USER_ID,UM_TENANT_ID)"
				+ " values ( (Select a.UM_ID from " + migdbtest + ".um_role a inner join " + smarttestdb
				+ ".um_role s on s.UM_ROLE_NAME = a.UM_ROLE_NAME where s.UM_ID = ? )" + ", ( Select m.UM_ID from "
				+ migdbtest + ".um_user m inner join " + smarttestdb
				+ ".um_user s on s.UM_USER_NAME = m.UM_USER_NAME where s.UM_ID =  ? ), " + " ? )";
		try {
			System.out.println(
					"UM_ROLE_ID " + rs.getString(2) + " UM_USER_ID " + rs.getInt(3) + " UM_TENANT_ID " + rs.getInt(4));
			Connection con = DbConnection.getMigIDSDbConnection();
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, rs.getString(2));
			preparedStmt.setInt(2, rs.getInt(3));
			preparedStmt.setInt(3, rs.getInt(4));
			preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Role related data Tables ~~~

	public static void insertDataToUmHybridRoleTable(ResultSet rs) {
		int x=100;
		try {
			while (rs.next()) {

				Connection con = DbConnection.getMigIDSDbConnection();
				Statement statemt = con.createStatement();
				String roleName = rs.getString(2);
				ResultSet result = statemt.executeQuery(
						"Select COUNT(*)  from um_hybrid_role where UM_ROLE_NAME ='" + roleName + "'");
				if(result.next()){
					x= result.getInt(1);
				}
				
				if (x==0) {
					System.out.println("Inserting Data to IDS um_hybrid_role");
					String query = " insert into um_hybrid_role (UM_ROLE_NAME,UM_TENANT_ID)" + " values (?, ?)";

					System.out.println("Inserting um_hybrid_role values UM_ROLE_NAME " + rs.getString(2)
							+ " UM_TENANT_ID " + rs.getInt(3));
					PreparedStatement preparedStmt = con.prepareStatement(query);
					preparedStmt.setString(1, rs.getString(2));
					preparedStmt.setInt(2, rs.getInt(3));
					preparedStmt.execute();

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insertDataToUmDomainTable(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.println("Inserting Data to IDS um_domain");
				String query = " insert into um_domain (UM_DOMAIN_NAME,UM_TENANT_ID)" + " values (?, ?)";

				System.out.println("Inserting um_domain values UM_DOMAIN_NAME " + rs.getString(2) + " UM_TENANT_ID "
						+ rs.getInt(3));
				Connection con = DbConnection.getMigIDSDbConnection();
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, rs.getString(2));
				preparedStmt.setInt(2, rs.getInt(3));
				preparedStmt.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void insertDataToUmHybridUserRoleTable(ResultSet rs) {
		String migdbtest = DbConnection.getMigDbName();
		String smarttestdb = DbConnection.getIsDbName();
		
			try {
				while (rs.next()) {
					try {
					System.out.println("Inserting Data to IDS um_hybrid_user_role");
					String query = " insert into um_hybrid_user_role (UM_USER_NAME,UM_ROLE_ID,UM_TENANT_ID,UM_DOMAIN_ID)"
							+ " values (?, " + "(Select m.UM_ID  from " + smarttestdb + ".um_hybrid_role s inner join "
							+ migdbtest + ".um_hybrid_role m on m.UM_ROLE_NAME = s.UM_ROLE_NAME where s.UM_ID = ?)," + "?, "
							+ "(Select m.UM_DOMAIN_ID from " + smarttestdb + ".um_domain s inner join " + migdbtest
							+ ".um_domain m on m.UM_DOMAIN_NAME = s.UM_DOMAIN_NAME where s.UM_DOMAIN_ID =  ? ))";

					System.out.println("Inserting um_hybrid_user_role values UM_USER_NAME " + rs.getString(2));
					Connection con = DbConnection.getMigIDSDbConnection();
					PreparedStatement preparedStmt = con.prepareStatement(query);
					preparedStmt.setString(1, rs.getString(2));
					preparedStmt.setInt(2, rs.getInt(3));
					preparedStmt.setInt(3, rs.getInt(4));
					preparedStmt.setInt(4, rs.getInt(5));
					System.out.println("~~~~~~~~~~" + preparedStmt.toString());
					preparedStmt.execute();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public static void insertDataToUmPermissionTable(ResultSet rs) {

		try {
			while (rs.next()) {
				System.out.println("Inserting Data to IDS um_permission");
				String query = " insert into um_permission (UM_RESOURCE_ID,UM_ACTION,UM_TENANT_ID,UM_MODULE_ID)"
						+ " values (?, ? , ?, ?)";

				System.out.println("Inserting um_permission values UM_RESOURCE_ID " + rs.getString(2));
				Connection con = DbConnection.getMigIDSDbConnection();
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, rs.getString(2));
				preparedStmt.setString(2, rs.getString(3));
				preparedStmt.setInt(3, rs.getInt(4));
				preparedStmt.setInt(4, rs.getInt(5));
				preparedStmt.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void insertDataToUmRolePermissionTable(ResultSet rs) {
		String migdbtest = DbConnection.getMigDbName();
		String smarttestdb = DbConnection.getIsDbName();
		try {
			while (rs.next()) {
				try {
					System.out.println("Inserting Data to IDS um_role_permission");
					String query = " insert into um_role_permission (UM_PERMISSION_ID,UM_ROLE_NAME,UM_IS_ALLOWED,UM_TENANT_ID,UM_DOMAIN_ID)"
							+ " values (( Select distinct min(m.UM_ID) from " + migdbtest
							+ ".um_permission m inner join " + smarttestdb
							+ ".um_permission s on s.UM_RESOURCE_ID = m.UM_RESOURCE_ID where m.UM_RESOURCE_ID = (Select UM_RESOURCE_ID From "
							+ smarttestdb + ".um_permission where UM_ID = ? ) And m.UM_ACTION = (Select UM_ACTION From "
							+ smarttestdb + ".um_permission where UM_ID = ? )) ," + "? ," + "? ," + "? , "
							+ "(Select m.UM_DOMAIN_ID from " + smarttestdb + ".um_domain s inner join " + migdbtest
							+ ".um_domain m on m.UM_DOMAIN_NAME = s.UM_DOMAIN_NAME where s.UM_DOMAIN_ID = ?))";

					System.out.println("Inserting um_role_permission values UM_RESOURCE_ID " + rs.getString(2));
					Connection con = DbConnection.getMigIDSDbConnection();
					PreparedStatement preparedStmt = con.prepareStatement(query);
					preparedStmt.setInt(1, rs.getInt(2));
					preparedStmt.setInt(2, rs.getInt(2));
					preparedStmt.setString(3, rs.getString(3));
					preparedStmt.setInt(4, rs.getInt(4));
					preparedStmt.setInt(5, rs.getInt(5));
					preparedStmt.setInt(6, rs.getInt(6));
					preparedStmt.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void insertDataToUmUserPermissionTable(ResultSet rs) {
		String migdbtest = DbConnection.getMigDbName();
		String smarttestdb = DbConnection.getIsDbName();
		try {
			while (rs.next()) {
				try {
					System.out.println("Inserting Data to IDS um_user_permission");
					String query = " insert into um_user_permission (UM_PERMISSION_ID,UM_USER_NAME,UM_IS_ALLOWED,UM_TENANT_ID)"
							+ " values (( Select distinct m.UM_ID from " + migdbtest + ".um_permission m inner join "
							+ smarttestdb
							+ ".um_permission s on s.UM_RESOURCE_ID = m.UM_RESOURCE_ID where m.UM_RESOURCE_ID = (Select UM_RESOURCE_ID From "
							+ smarttestdb + ".um_permission where UM_ID = ? ) And m.UM_ACTION = (Select UM_ACTION From "
							+ smarttestdb + ".um_permission where UM_ID = ? )) ," + "? ,? ,? )";

					System.out.println("Inserting um_user_permission values UM_RESOURCE_ID " + rs.getString(2));
					Connection con = DbConnection.getMigIDSDbConnection();
					PreparedStatement preparedStmt = con.prepareStatement(query);
					preparedStmt.setInt(1, rs.getInt(2));
					preparedStmt.setInt(2, rs.getInt(2));
					preparedStmt.setString(3, rs.getString(3));
					preparedStmt.setInt(4, rs.getInt(4));
					preparedStmt.setInt(5, rs.getInt(5));
					preparedStmt.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insertDataToUmClaimTable(ResultSet rs) {
		String migdbtest = DbConnection.getMigDbName();
		String smarttestdb = DbConnection.getIsDbName();
		try {
			while (rs.next()) {
				try {
					System.out.println("Inserting Data to IDS um_claim");
					String query = " insert into um_claim (UM_DIALECT_ID , UM_CLAIM_URI , UM_DISPLAY_TAG , UM_DESCRIPTION , UM_MAPPED_ATTRIBUTE_DOMAIN ,UM_MAPPED_ATTRIBUTE ,UM_REG_EX ,UM_SUPPORTED ,UM_REQUIRED ,UM_DISPLAY_ORDER ,UM_CHECKED_ATTRIBUTE ,UM_READ_ONLY ,UM_TENANT_ID)"
							+ " values (" + "(Select m.UM_ID from " + migdbtest + ".um_dialect m inner join "
							+ smarttestdb + ".um_dialect s on m.UM_DIALECT_URI = s.UM_DIALECT_URI where s.UM_ID = ?) , "
							+ "? , ? , ? ,? ,? ,? ,? ,? ,? , ? ,? ,? )";

					System.out.println("Inserting um_claim values UM_RESOURCE_ID " + rs.getInt(2));
					Connection con = DbConnection.getMigIDSDbConnection();
					PreparedStatement preparedStmt = con.prepareStatement(query);
					preparedStmt.setInt(1, rs.getInt(2));
					preparedStmt.setString(2, rs.getString(3));
					preparedStmt.setString(3, rs.getString(4));
					preparedStmt.setString(4, rs.getString(5));
					preparedStmt.setString(5, rs.getString(6));
					preparedStmt.setString(6, rs.getString(7));
					preparedStmt.setString(7, rs.getString(8));
					preparedStmt.setInt(8, rs.getInt(9));
					preparedStmt.setInt(9, rs.getInt(10));
					preparedStmt.setInt(10, rs.getInt(11));
					preparedStmt.setInt(11, rs.getInt(12));
					preparedStmt.setInt(12, rs.getInt(13));
					preparedStmt.setInt(13, rs.getInt(14));
					preparedStmt.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insertDataToUmHybridRemeberMe(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.println("Inserting Data to IDS um_hybrid_remember_me");
				String query = " insert into um_hybrid_remember_me (UM_USER_NAME,UM_COOKIE_VALUE,UM_CREATED_TIME,UM_TENANT_ID)"
						+ " values (?, ? ,? ,?)";

				System.out.println("Inserting um_hybrid_remember_me values UM_USER_NAME " + rs.getString(2));
				Connection con = DbConnection.getMigIDSDbConnection();
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, rs.getString(2));
				preparedStmt.setString(2, rs.getString(3));
				preparedStmt.setDate(3, rs.getDate(4));
				preparedStmt.setInt(4, rs.getInt(5));
				preparedStmt.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
