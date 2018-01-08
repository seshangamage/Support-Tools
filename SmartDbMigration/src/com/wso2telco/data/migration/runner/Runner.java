package com.wso2telco.data.migration.runner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.wso2telco.data.migration.helper.DuplicateDataDeleteHelper;
import com.wso2telco.data.migration.helper.IdsDbDataInertHelper;
import com.wso2telco.data.migration.helper.SmartDbDataExtractionHelper;
import com.wso2telco.data.migration.util.DbConnection;

public class Runner {
	



	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter MIG Db name : ");
		String migDbName = in.nextLine();
		//String migDbName = "mig_user_db_test";
		System.out.println("Enter IS Db name : ");
		String isDbName = in.nextLine();
		//String isDbName = "smart_is_user_db_test";
		System.out.println("Enter Mysql Database UserName : ");
		String dbUserName =in.nextLine();
		//String dbUserName ="root";
		System.out.println("Enter Mysql Database password");
		String dbPassword = in.nextLine();
		//String dbPassword ="root";
		
		
		System.out.println("######### Configureing Data #######");
		DbConnection.setIsDbName(isDbName);
		DbConnection.setMigDbName(migDbName);
		DbConnection.setDbUserName(dbUserName);
		DbConnection.setDbPassword(dbPassword);
		
		System.out.println("######### Deleting Duplicate Data #######");
		deleteDupliateData();
		System.out.println("######### Exporting UM User Data #######");
		exportUmUserData();
		System.out.println("######### Exporting User Permission Data #######");
		exportUserPermissionData();
		System.out.println("######### Exporting UM Hybrid Remeber Me Data #######");
		exportUmHybridRemeberMeData();
	}
	
	private static void deleteDupliateData(){
		DuplicateDataDeleteHelper.deleteDuplicatesUmPermissionTable();
		DuplicateDataDeleteHelper.deleteDuplicatesUmCalimTable();
	}

	private static void exportUmUserData() {
		// Code to export UM_USER related Data
		try {
			ResultSet smartUmUserResultSet = SmartDbDataExtractionHelper.getAllUseresinUmUserTable();
			while (smartUmUserResultSet.next()) {
				if (!smartUmUserResultSet.getString(2).equals("admin")) {
					// inserting data to IDS um_user table
					IdsDbDataInertHelper.insertDatatoUmUserTable(smartUmUserResultSet);
					// Getting UM_ID from IDS um_user table
					int UM_ID = IdsDbDataInertHelper.getUmUserIdFromUmUserTable(smartUmUserResultSet.getString(2));
					// Getting Result set from Smart um_user_attribute table
					ResultSet smartUmUserAtributeResult = SmartDbDataExtractionHelper
							.getUmUserAttributeforSpecificUser(smartUmUserResultSet.getInt(1));
					// inserting IDS um_user_attributes
					while (smartUmUserAtributeResult.next()) {
						IdsDbDataInertHelper.insertDataoUmUserAttributeTable(smartUmUserAtributeResult, UM_ID);
					}
				}
			}
			ResultSet smartUmRoleResultSet = SmartDbDataExtractionHelper.getAllUmRoleData();
			while (smartUmRoleResultSet.next()) {
				IdsDbDataInertHelper.insertDataoUmRoleTable(smartUmRoleResultSet);
			}
			ResultSet smartUmUserRoleResultSet = SmartDbDataExtractionHelper.getAllUmUserRoleData();
			while (smartUmUserRoleResultSet.next()) {
				IdsDbDataInertHelper.insertDataToUmUserRoleTable(smartUmUserRoleResultSet);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static void exportUserPermissionData(){
		
		try{
			ResultSet umHybridRole = SmartDbDataExtractionHelper.getAllUmHybridRole();
			IdsDbDataInertHelper.insertDataToUmHybridRoleTable(umHybridRole);
			
			// UM_Domain table had the same data so no need to export on local machine 
			//ResultSet umDomainResultSet = SmartDbDataExtractionHelper.getAllUmDomain();
			//dsDbDataInertHelper.insertDataToUmDomainTable(umDomainResultSet);
			
			ResultSet umHybridUserRoleResultSet = SmartDbDataExtractionHelper.getAllUmHybridUserRole();
			IdsDbDataInertHelper.insertDataToUmHybridUserRoleTable(umHybridUserRoleResultSet);
			
			ResultSet umPermissionResultSet = SmartDbDataExtractionHelper.getAllUmPermision();
			IdsDbDataInertHelper.insertDataToUmPermissionTable(umPermissionResultSet);
			
			ResultSet umRolePermissionResultSet = SmartDbDataExtractionHelper.getAllUmRolePermision();
			IdsDbDataInertHelper.insertDataToUmRolePermissionTable(umRolePermissionResultSet);
			
			ResultSet umUserPermissionResultSet = SmartDbDataExtractionHelper.getAllUmUserPermision();
			IdsDbDataInertHelper.insertDataToUmUserPermissionTable(umUserPermissionResultSet);
			
			ResultSet umClaimResultSet = SmartDbDataExtractionHelper.getAllUmClaim();
			IdsDbDataInertHelper.insertDataToUmClaimTable(umClaimResultSet);
//			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	private static void exportSystemRoleData(){
		// Same data exist in both databases for um_system_role and um_system_user_role
	}
	
	private static void exportModuleData(){
		// no data in um_module and um_module_action tables in both databases;
	}
	
	private static void exportTenantData(){
		// no data in um_tenant and um_account_mapping tables in both databases;
	}
	
	private static void exportUmSystemUserData(){
		// same data exist in um_system_user table in both databases;
	}
	
	private static void exportUmHybridRemeberMeData(){
		ResultSet remeberMeResultSet = SmartDbDataExtractionHelper.getAllUmHybridRemeberMe();
		IdsDbDataInertHelper.insertDataToUmHybridRemeberMe(remeberMeResultSet);
	}

}
