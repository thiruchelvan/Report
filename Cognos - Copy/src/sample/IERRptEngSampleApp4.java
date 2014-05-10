package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.util.Id;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import com.ibm.ier.report.db.DBDescriptor;
import com.ibm.ier.report.db.DBType;
import com.ibm.ier.report.db.impl.JDBC_DBDescriptor;
import com.ibm.ier.report.service.Report_Services;
import com.ibm.ier.report.service.ReportDataResult;


public class IERRptEngSampleApp4 {

	/**
	 * Replace the value for the variables below for Content Engine user context,
	 * database context, and the report parameters. 
	 */
	
	/**
	 * Content engine user context.
	 */	
	private static String CE_URL 		= "http://192.168.1.200:9080/wsi/FNCEWS40MTOM"; 			// Example: "http://ceServerName:cePort/wsi/FNCEWS40MTOM"
	private static String FPOS_NAME 	= "FPOS"; 			// Example: "FPOS"
	private static String USER_NAME 	= "administrator";		// Example: "p8admin"
	private static String PASSWORD 		= "filenet"; 	// Example: "password"

	/**
	 * Database context for report data.
	 */	
	private static DBType DB_TYPE 		= DBType.DB2;				// Example: DBType.DB2
	private static String DB_SERVER 	= "192.168.1.200"; 		// Example: "dbServerName"
	private static String DB_PORT 		= "50000";				// Example: "50000"
	private static String DB_DATABASE 	= "IER_REP"; 				// Example: "REPORTS"
	private static String DB_USERNAME 	= "db2admin"; 		// Example: "db2admin"
	private static String DB_PASSWORD 	= "filenet";		// Example: "db2Password"

	/**
	 * Report parameter value.
	 */	
	// A file plan location in the file plan object store (FPOS).
	private static String PARAM_VALUE_FILEPLAN_NAME = "/Records Management/File Plan/";	// Example: "/Records Management/File Plan"
	// User name for the report.
	private static String PARAM_VALUE_USER_NAME 	= "Administrator";		// Example: "Administrator"
	// The start date of the report criteria.
	private static String PARAM_VALUE_START_DATE 	= "2014-01-01";		// Example: "2012-01-01"
	// The end date of the report criteria.
	private static String PARAM_VALUE_END_DATE 		= "2015-10-01";		// Example: "2012-10-01"

	/***********************************************************************/
	
	private static UserContext jaceUC = null;
	private static String rptDefinitionIdent = "Items associated with a disposition schedule";
	private static String rptJobId = null;

	public static void main(String[] args) {
		try {
			// Establish database context.
			Map<String, String> dbContext = new HashMap<String, String>();
			dbContext.put(DBDescriptor.DB_Server, DB_SERVER);
			dbContext.put(DBDescriptor.DB_Port, DB_PORT);
			dbContext.put(DBDescriptor.DB_Database, DB_DATABASE);
			dbContext.put(DBDescriptor.DB_Username, DB_USERNAME);
			dbContext.put(DBDescriptor.DB_Password, DB_PASSWORD);
			dbContext.put(DBDescriptor.DB_Schema, "IER_REPORT");

			DBDescriptor dbDescriptor = new JDBC_DBDescriptor(DB_TYPE,
					dbContext);
			dbDescriptor.verify();

			// Set value for report parameters
			Map<String, Object> rptParams = new HashMap<String, Object>();
			rptParams.put("disposal_schedule", "1M");
		//	rptParams.put("ros_name", "RCFOS1");
			//rptParams.put("user_name", PARAM_VALUE_USER_NAME);
			//rptParams.put("start_date", PARAM_VALUE_START_DATE);
			//rptParams.put("end_date", PARAM_VALUE_END_DATE);
			/*List<String> entityTypeList = new ArrayList<String>(3);
			entityTypeList.add("RecordCategory");
			entityTypeList.add("RecordFolder");
			entityTypeList.add("Volume");
		//	entityTypeList.add("Record");
			rptParams.put("rm_entity_type", entityTypeList);*/

			// Get the Jace ObjectStore for File Plan Object Store (FPOS).
			ObjectStore jaceObjStore = getJaceObjectStore();

			System.out.println("\nRunning test for report definition: "
					+ rptDefinitionIdent);

			Report_Services rptServices = new Report_Services(dbDescriptor);

			// You can specify a job id for the report. If the report job id is	not specified
			// when calling the Report_Services.generateReportData() method,
			// the report engine will generate a report job id automatically.
			// rptJobId = "12345";
			rptJobId = Id.createId().toString();

			// Get the report queries with the provided parameter values.
			// This step is for information purpose and is optional.
			System.out.println(rptDefinitionIdent);
			System.out.println(jaceObjStore.toString());
			System.out.println(rptParams.toString());
			rptDefinitionIdent = "/Records Management/Report Definitions/" + rptDefinitionIdent;
			List<String> rptQueries = rptServices.getReportQueries(
					rptDefinitionIdent, jaceObjStore, rptParams);
			System.out.println("\nGenerated report query:");
			for (int i = 0; i < rptQueries.size(); i++) {
				System.out.println(rptQueries.get(i).toString());
			}

			// Generate the report data to the database.
			ReportDataResult result = rptServices.generateReportData(
					rptDefinitionIdent, jaceObjStore, rptParams, rptJobId);

			System.out.println("Report job id: " + result.getJobID());
			System.out.println("Total Database Rows Inserted: "
					+ result.getTotalRowsProcessed());

			// Delete the report data from the database.
			// ReportDataResult deleteResult =
			// 		rptServices.deleteReportData(result.getJobID(),
			// 		rptDefinitionIdent, jaceObjStore);
			// System.out.println("\nTotal Database Rows Deleted: " +
			// 		deleteResult.getTotalRowsProcessed());

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (jaceUC != null)
				jaceUC.popSubject();
		}
	}

	private static ObjectStore getJaceObjectStore() {
		String JAAS_STANZA = "FileNetP8WSI";
		Connection jaceConnection = Factory.Connection.getConnection(CE_URL);
		Subject jaceSubject = UserContext.createSubject(jaceConnection,
				USER_NAME, PASSWORD, JAAS_STANZA);
		jaceUC = UserContext.get();
		jaceUC.pushSubject(jaceSubject);
		Domain jaceDomain = Factory.Domain.fetchInstance(jaceConnection, null,
				null);
		ObjectStore fpos = Factory.ObjectStore.fetchInstance(jaceDomain,
				FPOS_NAME, null);

		return fpos;
	}

}

