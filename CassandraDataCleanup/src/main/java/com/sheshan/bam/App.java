package com.sheshan.bam;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.model.RowImpl;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;

/**
 * sheshan@wso2telco.com!
 *
 */
public class App {
	
	private static int timeOffsetHours ;
	private static int timeOffsetMins ;
	private static String CASANDRA_USERNAME ;
	private static String CASANDRA_PASSWORD ;
	private static int CASNDRA_PORT ;
	private static String CASANDRA_HOST ;
	private static long startTimeStamp;
	private static long endTimeStamp;
	private static final String CASANDRA_KEYSPACE = "EVENT_KS";


	public static void main(String[] args) {
		
		Cluster cluster;
		loadDatafromPropertyFile();
		startTimeStamp = 1262345324000L;
		System.out.println("Enter archive end TimeStamp :");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -14);
		endTimeStamp = cal.getTimeInMillis();
		
		
		System.out.println("CASANDRA_HOST:" +CASANDRA_HOST +"\n CASANDRA_PORT : "+ CASNDRA_PORT 
		+ "\n CASANDRA_USERNAME : "+ CASANDRA_USERNAME +"\n CASANDRA_PASSWORD : ****** \n Recovery Starting Timestamp "+startTimeStamp+"\n Recovery Ending Timestamp :"+endTimeStamp);

		try {

			Map<String, String> credentials = new HashMap<String, String>();
			credentials.put("username", CASANDRA_USERNAME.trim());
			credentials.put("password", CASANDRA_PASSWORD.trim());
			cluster = (Cluster) HFactory.getOrCreateCluster("Test Cluster",
					new CassandraHostConfigurator(CASANDRA_HOST + ":" + CASNDRA_PORT), credentials);
			ConfigurableConsistencyLevel consistencyLevel = new ConfigurableConsistencyLevel();
			consistencyLevel.setDefaultReadConsistencyLevel(HConsistencyLevel.ONE);
			Keyspace keyspace = HFactory.createKeyspace(CASANDRA_KEYSPACE, cluster, consistencyLevel);
			printSouthBoundResuest(keyspace);
			printSouthBoundResponse(keyspace);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printSouthBoundResuest(Keyspace keySpace) {

		CqlQuery cqlQuery = new CqlQuery(keySpace, StringSerializer.get(), StringSerializer.get(),
				StringSerializer.get());
		cqlQuery.setQuery("select * from mife_statistics_southbound_request");
		QueryResult<CqlRows<String, String, ByteBuffer>> result = cqlQuery.execute();
		CqlRows rows = result.get();
		//System.out.println("row Count:" + rows.getCount());
		try{
			int j =1;
			int k =0;
	    PrintWriter writer = new PrintWriter("southbound_request"+j+".sql", "UTF-8");
		for (int i = 0; i < rows.getCount(); i++) {
			RowImpl row = (RowImpl) rows.getList().get(i);
			{
				String[] stringPartilas = row.getKey().toString().split(":");			
				String time = stringPartilas[0];
				if(k>= 3000){
					k=0;
					System.out.println("southbound_request"+j+".sql File created");
					writer.close();
					writer = new PrintWriter("southbound_request"+ j++ +".sql", "UTF-8");
				}
				long logarchivetime = Long.parseLong(time);
				if( startTimeStamp <= logarchivetime && logarchivetime <= endTimeStamp){
					ColumnSlice<String, ByteBuffer> slice = row.getColumnSlice();
					String insertQueryString = writeSqltofile(row);
					writer.println(insertQueryString);
					System.out.println(insertQueryString);
					try{
					System.out.println("Going to delete row key value = "+row.getKey());
					String deleteStatement = "DELETE FROM mife_statistics_southbound_request WHERE key = '"+row.getKey()+"'";
					cqlQuery.setQuery(deleteStatement);
					cqlQuery.execute();
					System.out.println("Sucessfully Deleted row key value = "+row.getKey());
					}
					catch(Exception e){
						e.printStackTrace();
					}
					k++;
			    }
			
			}
		}
	    writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void printSouthBoundResponse(Keyspace keySpace) {
		

		CqlQuery cqResponselQuery = new CqlQuery(keySpace, StringSerializer.get(), StringSerializer.get(),
				StringSerializer.get());
		cqResponselQuery.setQuery("select * from mife_statistics_southbound_response");
		QueryResult<CqlRows<String, String, ByteBuffer>> responseResult = cqResponselQuery.execute();
		CqlRows responseRows = responseResult.get();
		//System.out.println("row Count:" + responseRows.getCount());
		try{
			int j =1;
			int k =0;
			PrintWriter writer = new PrintWriter("southbound_response"+j+".sql", "UTF-8");
		for (int i = 0; i < responseRows.getCount(); i++) {
			RowImpl row = (RowImpl) responseRows.getList().get(i);
			{
				
				
				String[] stringPartilas = row.getKey().toString().split(":");			
				String time = stringPartilas[0];
				if(k>= 3000){
					k=0;
					System.out.println("southbound_response"+j+".sql File created");
					writer.close();
					writer = new PrintWriter("southbound_response"+ j++ +".sql", "UTF-8");
				}
				long logarchivetime = Long.parseLong(time);
				if( startTimeStamp <= logarchivetime && logarchivetime <= endTimeStamp){
					String insertResponseQueryString = writeSResponseqltofile(row);
				    writer.println(insertResponseQueryString);
				    System.out.println(insertResponseQueryString);
					try{
					System.out.println("Going to delete row key value = "+row.getKey());
					String deleteStatement = "DELETE FROM mife_statistics_southbound_response WHERE key = '"+row.getKey()+"'";
					cqResponselQuery.setQuery(deleteStatement);
					cqResponselQuery.execute();
					System.out.println("Sucessfully Deleted row key value = "+row.getKey());
					}
					catch(Exception e){
						e.printStackTrace();
					}
					k++;
			    }

			}
		}
	    writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		


	}
	
	
	private static String writeSqltofile(RowImpl row){
		try{
			StringBuilder insertQueryBuilder = new StringBuilder();
			ColumnSlice<String, ByteBuffer> slice = row.getColumnSlice();
			
			insertQueryBuilder
			.append("INSERT INTO SB_API_REQUEST_SUMMARY (messageRowID,api,api_version,version,apiPublisher,consumerKey,userId,context,request_count,hostName,resourcePath,method,requestId,operatorId,chargeAmount,purchaseCategoryCode,jsonBody,year,month,day,time) VALUES (");
			insertQueryBuilder.append("'"+row.getKey()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_api").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_api_version").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("Version").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_apiPublisher").getValue() + "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_consumerKey").getValue() + "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_userId").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_context").getValue()+ "',");
			insertQueryBuilder.append(1+ ",");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_hostName").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_resourcePath").getValue() + "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_method").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_requestId").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_operatorId").getValue()+ "',");
			if(slice.getColumnByName("payload_chargeAmount")==null){
				insertQueryBuilder.append("NULL" + ",");
			}
			else{
				insertQueryBuilder.append("'"+slice.getColumnByName("payload_chargeAmount").getValue() + "',");
			}
			if(slice.getColumnByName("payload_purchaseCategoryCode")==null){
				insertQueryBuilder.append("NULL" + ",");
			}
			else{
				insertQueryBuilder.append("'"+slice.getColumnByName("payload_purchaseCategoryCode").getValue() + "',");
			}
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_jsonBody").getValue()+ "',");
			
			String[] stringPartilas = row.getKey().toString().split(":");			
			String time = stringPartilas[0];
			if (time !=null || !time.toString().isEmpty()) {
				Long requestimeLong = Long.parseLong(time);
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(requestimeLong);
				cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+ timeOffsetHours);
				cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)+ timeOffsetMins);
				Date requestTime = cal.getTime();
				insertQueryBuilder.append(cal.get(Calendar.YEAR)+ ",");
				insertQueryBuilder.append((cal.get(Calendar.MONTH) + 1) + ",");
				insertQueryBuilder.append(cal.get(Calendar.DAY_OF_MONTH) + ",");
				insertQueryBuilder.append( "'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(requestTime) + "');");
			}
			
	    
			//System.out.println(insertQueryBuilder.toString());
			return insertQueryBuilder.toString();

		} catch (Exception e) {
            e.printStackTrace();
            return "error";
		}
	}
	
	
	private static String writeSResponseqltofile(RowImpl row){
		try{
			StringBuilder insertQueryBuilder = new StringBuilder();
			ColumnSlice<String, ByteBuffer> slice = row.getColumnSlice();
			
			insertQueryBuilder
			.append("INSERT INTO SB_API_RESPONSE_SUMMARY (messageRowID,api,api_version,version,apiPublisher,consumerKey,userId,context,serviceTime,response_count,hostName,resourcePath,method,requestId,operatorId,responseCode,msisdn,operatorRef,chargeAmount,purchaseCategoryCode,exceptionId,exceptionMessage,jsonBody,year,month,day,time) VALUES (");
			insertQueryBuilder.append("'"+row.getKey()+ "',");
			if(slice.getColumnByName("payload_api")==null){
				insertQueryBuilder.append("NULL" + ",");
			}
			else{
				insertQueryBuilder.append("'"+slice.getColumnByName("payload_api").getValue()+ "',");
			}
			if(slice.getColumnByName("payload_api_version")==null){
				insertQueryBuilder.append("NULL" + ",");
			}
			else{
				insertQueryBuilder.append("'"+slice.getColumnByName("payload_api_version").getValue()+ "',");
			}
			insertQueryBuilder.append("'"+slice.getColumnByName("Version").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_apiPublisher").getValue() + "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_consumerKey").getValue() + "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_userId").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_context").getValue()+ "',");
			//insertQueryBuilder.append("'"+slice.getColumnByName("payload_serviceTime").getValue()+ "',");
			insertQueryBuilder.append(10+",");
			insertQueryBuilder.append(1+ ",");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_hostName").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_resourcePath").getValue() + "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_method").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_requestId").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_operatorId").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_responseCode").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_msisdn").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_operatorRef").getValue()+ "',");
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_chargeAmount").getValue()+ "',");
			if(slice.getColumnByName("payload_purchaseCategoryCode")==null){
				insertQueryBuilder.append("NULL" + ",");
			}
			else{
				insertQueryBuilder.append("'"+slice.getColumnByName("payload_purchaseCategoryCode").getValue() + "',");
			}
			if(slice.getColumnByName("payload_exceptionId")==null){
				insertQueryBuilder.append("NULL" + ",");
			}
			else{
				insertQueryBuilder.append("'"+slice.getColumnByName("payload_exceptionId").getValue() + "',");
			}
			if(slice.getColumnByName("payload_exceptionMessage")==null){
				insertQueryBuilder.append("NULL" + ",");
			}
			else{
				insertQueryBuilder.append("'"+slice.getColumnByName("payload_exceptionMessage").getValue() + "',");
			}	
			insertQueryBuilder.append("'"+slice.getColumnByName("payload_jsonBody").getValue()+ "',");
			String[] stringPartilas = row.getKey().toString().split(":");			
			String time = stringPartilas[0];
			if (time !=null || !time.toString().isEmpty()) {
				Long requestimeLong = Long.parseLong(time);
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(requestimeLong);
				cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+ timeOffsetHours);
				cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)+ timeOffsetMins);
				Date requestTime = cal.getTime();
				insertQueryBuilder.append(cal.get(Calendar.YEAR)+ ",");
				insertQueryBuilder.append((cal.get(Calendar.MONTH) + 1) + ",");
				insertQueryBuilder.append(cal.get(Calendar.DAY_OF_MONTH) + ",");
				insertQueryBuilder.append( "'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(requestTime) + "');");
			}
			
	    
			//System.out.println(insertQueryBuilder.toString());
			return insertQueryBuilder.toString();

		} catch (Exception e) {
            e.printStackTrace();
            return "error";
		}
	}
	
	
	public static void loadDatafromPropertyFile(){
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);
			CASANDRA_HOST =prop.getProperty("dbhost");
			CASNDRA_PORT =Integer.parseInt(prop.getProperty("dbport"));
			CASANDRA_USERNAME = prop.getProperty("dbuser");
			CASANDRA_PASSWORD =prop.getProperty("dbpassword");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}

//1370766194297
//1470764324656
//1470764795096
//1584722703000
//1262345324000





