package sample;

import javax.security.auth.Subject;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

import filenet.vw.api.VWAttachment;
import filenet.vw.api.VWDataField;
import filenet.vw.api.VWException;
import filenet.vw.api.VWFetchType;
import filenet.vw.api.VWFieldType;
import filenet.vw.api.VWModeType;
import filenet.vw.api.VWParameter;
import filenet.vw.api.VWParticipant;
import filenet.vw.api.VWQueue;
import filenet.vw.api.VWQueueQuery;
import filenet.vw.api.VWRoster;
import filenet.vw.api.VWRosterElement;
import filenet.vw.api.VWRosterQuery;
import filenet.vw.api.VWSession;
import filenet.vw.api.VWStepElement;
import filenet.vw.api.VWWorkObjectNumber;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test1= new Test();
		
		//test1.assigngroup1("DF0432E3BB34F94599EA729A5FB8F0C5");
		String ss= test1.assigngroup("03E24B4FB4386C488A1EBC3EBA27EB97");
		
		
		
	}
	
	
	public static  void assigngroup1(String wbob)
	{
		 String FILENET_USERNAME = "administrator";
		 String FILENET_PASSWORD	 = "filenet";

		 String FILENET_URI = "http://192.168.1.200:9080/wsi/FNCEWS40MTOM/";

		 String CE_DOMAIN	 = "FileNet";
		 String CE_OBJECTSTORE	 = "RCFOS1";

		 String PE_CONNECTION_POINT = "OS1";

		 Connection ceConnection = 
					Factory.Connection.getConnection(FILENET_URI);
					System.out.println("Connection Name:"+ ceConnection);

					Subject ceSubject = UserContext.createSubject(ceConnection, FILENET_USERNAME, FILENET_PASSWORD, null);
					System.out.println("CESubject:"+ceSubject);


					UserContext.get().pushSubject(ceSubject);
					Domain ceDomain = Factory.Domain.fetchInstance(ceConnection, CE_DOMAIN, null);
					System.out.println("CE Domain deails:"+ceDomain);

					ObjectStore ceObjectStore = Factory.ObjectStore.fetchInstance(ceDomain, CE_OBJECTSTORE, null);

					UserContext.get().popSubject();
					System.out.println("trying to connect with pe");
		VWSession peSession = new VWSession();
		//	peSession.setBootstrapCEURI(FILENET_URI);
			
			peSession.setBootstrapPEURI(FILENET_URI);
			System.out.println("Connecting by url method");
			
			peSession.logon(FILENET_USERNAME, FILENET_PASSWORD, PE_CONNECTION_POINT);
		
		// Set Roster Name
		String rosterName= "DefaultRoster";
		// Retrieve Roster Object and Roster count
		VWRoster roster = peSession.getRoster(rosterName);
		
		System.out.println("Workflow Count: " + roster.fetchCount());
	
		// Set Query Parameters
		int queryFlags=VWRoster.QUERY_NO_OPTIONS;
		String queryFilter="";
	//	String wobNum = wbob;
		// VWWorkObjectNumber class takes care of the value format
		// used in place of F_WobNum and F_WorkFlowNumber
		//Object[] substitutionVars = {new VWWorkObjectNumber(wobNum)};
		//int fetchType = VWFetchType.FETCH_TYPE_ROSTER_ELEMENT;
		int queryType = VWFetchType.FETCH_TYPE_STEP_ELEMENT;
		int queryType1 = VWFetchType.FETCH_TYPE_ROSTER_ELEMENT;
		// Perform Query
		VWRosterQuery query = roster.createQuery(null,null,null,queryFlags,queryFilter,null,queryType);
		
		VWRosterQuery query2 = roster.createQuery(null,null,null,queryFlags,queryFilter,null,queryType);
		
		VWRosterQuery query1 = roster.createQuery(null,null,null,queryFlags,queryFilter,null,queryType1);
		
		//VWStepElement stepElement1 = (VWStepElement) query.next();
		
	//	System.out.println("Step Name " + stepElement.getStepName());
	//	System.out.println("------------------------------------------------------------------------------------------------");	
		
		/*while(query1.hasNext()) {
			
			VWRosterElement rosterItem1 = (VWRosterElement) query1.next();
			
			System.out.println("Work Object Number " + rosterItem1.getWorkObjectNumber());
			
			System.out.println("Subject  " + rosterItem1.getSubject());

			System.out.println("Launch Date  " + rosterItem1.getLaunchDate());
			
			System.out.println("Originator  " + rosterItem1.getOriginator());
			
			System.out.println("ID  " + rosterItem1.getDataField("ID"));
			
			System.out.println("Name  " + rosterItem1.getDataField("Name"));
			
			System.out.println("Address  " + rosterItem1.getDataField("Address"));
			
			System.out.println("------------------------------------------------------------------------------------------------");
			
				
			}*/
		
		
		
/*
		System.out.println("Parameter length " + parameters.length);
		

		
		
		
VWParameter[] parameters1 = stepElement.getParameters(VWFieldType.ALL_FIELD_TYPES,VWStepElement.FIELD_USER_AND_SYSTEM_DEFINED);
		
		System.out.println("Attach Parameters Length -- > " + parameters1.length);
		
		System.out.println("------------------------------------------------------------------------------------------------");

		for (int i = 0; i < parameters1.length; i++ ) {
			
			System.out.println(" Field Name -->  " + parameters1[i].getName());
			
			System.out.println("------------------------------------------------------------------------------------------------");
			
		}
		*/
		
		
		
		System.out.println("------------------------------------------------------------------------------------------------");
		
		while(query2.hasNext()) {
			
		
		VWStepElement stepElement2 = (VWStepElement) query2.next();
		
		
		
		VWParameter[] parameters = stepElement2.getParameters(VWFieldType.ALL_FIELD_TYPES,VWStepElement.FIELD_USER_AND_SYSTEM_DEFINED);
	for(int j=0;j<parameters.length;j++)
	{	
		
		System.out.println("parameters Name --> " + parameters[j].getName());
		
	
		
	}
	
	
	
	
	System.out.println("Step Name --> " + stepElement2.getStepName());
	
	System.out.println("Subject Name --> " + stepElement2.getSubject());
	
	System.out.println("WON--> " + stepElement2.getWorkObjectNumber());
	
	System.out.println("getParticipantNamePx Name --> " + stepElement2.getParticipantNamePx());
	
	System.out.println("------------------------------------------------------------------------------------------------");
		}
		
		
	//	while(query.hasNext()) {
			
			
		//	VWStepElement stepElement = (VWStepElement) query.next();
			
		//	stepElement.doLock(true);
			
		//	VWParameter[] parameters = stepElement.	getParameters(VWFieldType.ALL_FIELD_TYPES,VWStepElement.FIELD_USER_AND_SYSTEM_DEFINED);
			
			//System.out.println("parameters Name --> " + parameters[j].getName());
			
			//j=j+1;
			
		/*System.out.println("Step Name --> " + stepElement.getStepName());
			
		System.out.println("WON--> " + stepElement.getWorkObjectNumber());
		
		System.out.println("ID--> " + stepElement.getParameter("ID"));
		
		System.out.println("Name --> " + stepElement.getParameter("Name"));
		
		System.out.println("Contact No --> " + stepElement.getParameter("Contactno"));
		
		System.out.println("Address --> " + stepElement.getParameter("Address"));
		
		System.out.println("Attach1 --> " + stepElement.getParameter("Attach1"));
		
		System.out.println("Group 1 --> " + stepElement.getParameter("Group1"));
		
		System.out.println("Group 2 --> " + stepElement.getParameter("Group2"));
		
		System.out.println("ParticipantName --> " + stepElement.getParticipantName());*/

	//	System.out.println("------------------------------------------------------------------------------------------------");
		
		
		
		//stepElement.doSave(false);
		
	//	stepElement.doLock(true);
		
//		stepElement.doAbort();	
		
		
	//	}
		
		 /*VWAttachment[] vwattach = (VWAttachment[]) stepElement.getParameterValue("Attach1");
		 
		 System.out.println("No of Attachement" + vwattach.length);
		 
		 System.out.println("Attach Name" + vwattach[0].getAttachmentName());
		 
	
		for (int i = 0; i < vwattach.length; i++ ) {
			
			 System.out.println("Attach Name " + vwattach[i].getAttachmentName());
			 System.out.println("Attach ID  " + vwattach[i].getId());
		}*/
		
		
	/*	String[] arrParamValues =new String[] {"User1"};
		String[] arrParamValues1 =new String[] {"Thiru"};
			//stepElement.setParameterValue(parameters[i].getName(),arrParamValues,true);
		stepElement.setParameterValue("Group1",arrParamValues,true);
		stepElement.setParameterValue("Group2",arrParamValues1,true);
		stepElement.setParameterValue("ID","DDDSSSSDDDFFFFDDDDDD",true);
		//parameters[i].setValue(arrParamValues);
*/		

		
		//stepElement.doDispatch();
		
	//	stepElement.getComment();
		
	//	stepElement.doLock(false);
		
		
		// Process Results
		/*while(query.hasNext()) {
		VWRosterElement rosterItem = (VWRosterElement) query.next();
		
		System.out.println(" Field getAuthoredStepName  "  + rosterItem.getAuthoredStepName());
         
		VWDataField[] vwfield = rosterItem.getDataFields();
		
		for (int i = 0; i < vwfield.length; i++ ) {
			
			System.out.println(" Field Name  " + vwfield[i].getName());
			
		}
		
		
		
		System.out.println("length  " + 	vwfield.length);
		System.out.println("ID  " + rosterItem.getFieldValue("ID"));
		System.out.println("Name" + rosterItem.getFieldValue("Name"));
		System.out.println("Address  " + rosterItem.getFieldValue("Address"));
		System.out.println("Group1  " + rosterItem.getFieldValue("Group1"));
		System.out.println("Group2  " + rosterItem.getFieldValue("Group2"));
		
		System.out.println("WF Number: "+rosterItem.getWorkflowNumber());
		System.out.println("WOB Number:	"+rosterItem.getWorkObjectNumber());
				System.out.println("F_StartTime: " +
				rosterItem.getFieldValue("F_StartTime"));
				System.out.println("F_Subject: " +
				rosterItem.getFieldValue("F_Subject"));
			
		}
		
		
		System.out.println("hai");*/

		
	
		peSession.logoff();
		
			System.out.println("After dispatch ");	
		
		
		
	}
	public static  String  assigngroup(String wbob)
	{
		
		 String FILENET_USERNAME = "Administrator";
		 String FILENET_PASSWORD	 = "filenet";

		 String FILENET_URI = "http://192.168.1.200:9080/wsi/FNCEWS40MTOM/";

		 String CE_DOMAIN	 = "FileNet";
		 String CE_OBJECTSTORE	 = "RCFOS1";

		 String PE_CONNECTION_POINT = "OS1";
		 
		 String Group1 = null;

		 Connection ceConnection = 
					Factory.Connection.getConnection(FILENET_URI);
					System.out.println("Connection Name:"+ ceConnection);

					Subject ceSubject = UserContext.createSubject(ceConnection, FILENET_USERNAME, FILENET_PASSWORD, null);
					System.out.println("CESubject:"+ceSubject);


					UserContext.get().pushSubject(ceSubject);
					Domain ceDomain = Factory.Domain.fetchInstance(ceConnection, CE_DOMAIN, null);
					System.out.println("CE Domain deails:"+ceDomain);

					ObjectStore ceObjectStore = Factory.ObjectStore.fetchInstance(ceDomain, CE_OBJECTSTORE, null);

					UserContext.get().popSubject();
					System.out.println("trying to connect with pe");
					
		VWSession peSession = new VWSession();
		//	peSession.setBootstrapCEURI(FILENET_URI);
			
			peSession.setBootstrapPEURI(FILENET_URI);
			System.out.println("Connecting by url method");
			
			peSession.logon(FILENET_USERNAME, FILENET_PASSWORD, PE_CONNECTION_POINT);
			
			System.out.println("WOB " + wbob );
		
	
			// Queue Name
			String queueName = "Inbox";
			// Retrieve the Queue
			VWQueue queue = peSession.getQueue(queueName);
			// Set Query Parameters
			String wobNum = wbob;
			VWWorkObjectNumber wob = new VWWorkObjectNumber(wobNum);
			VWWorkObjectNumber[] queryMin = new VWWorkObjectNumber[1];
			VWWorkObjectNumber[] queryMax = new VWWorkObjectNumber[1];
			String queryIndex = "F_WobNum";
			queryMin[0] = wob;
			queryMax[0] = wob;
			// Query Flags and Type to retrieve Step Elements
			int queryFlags =  VWQueue.QUERY_MIN_VALUES_INCLUSIVE + VWQueue.QUERY_MAX_VALUES_INCLUSIVE + VWQueue.QUERY_LOCK_OBJECTS ;
			int queryType = VWFetchType.FETCH_TYPE_STEP_ELEMENT;
			VWQueueQuery queueQuery = queue.createQuery(queryIndex,queryMin,queryMax,queryFlags,null,null,queryType);
			
			System.out.println(queueQuery.fetchCount());
			
			try {
			
			VWStepElement stepElement = (VWStepElement) queueQuery.next();
			
		//	stepElement.doLock(true);
			
			
	//	VWParameter[] parameters = stepElement.	getParameters(VWFieldType.ALL_FIELD_TYPES,VWStepElement.FIELD_USER_AND_SYSTEM_DEFINED);

	//	System.out.println("Parameter length " + parameters.length);
		
	/*	System.out.println("Parameter length " + parameters[0].getName());
		
		System.out.println("Parameter length " + parameters[1].getName());
		
		System.out.println("Parameter length " + parameters[2].getName());
		*/
		
		System.out.println("WON " + stepElement.getWorkObjectNumber());
		
		System.out.println("ID " + stepElement.getParameter("ID"));
		
		System.out.println("Name " + stepElement.getParameter("Name"));
		
		System.out.println("Contact No " + stepElement.getParameter("Contactno"));
		
		System.out.println("Address " + stepElement.getParameter("Address"));
		
		System.out.println("Attach1 " + stepElement.getParameter("Attach1"));

		System.out.println("Group 1 " + stepElement.getParameter("Group1"));
		
		System.out.println("Group 2 " + stepElement.getParameter("Group2"));
		
		//stepElement.setParameterValue("ID", stepElement.getParameter("ID").toString(),true);
		
		stepElement.doSave(true);
		
		  Group1 = "{User1,Thiru}";
		
		
	//	stepElement.doSave(true);
		
	//	stepElement.doLock(true);
		
		
			}catch(VWException vwe)
			{
				System.out.println("\nVWException Key: " + vwe.getKey() + "\n");
				System.out.println("VWException Cause Class Name: "
				+ vwe.getCauseClassName() + "\n");
				System.out.println("VWException CauseDescription: "
				+ vwe.getCauseDescription()+ "\n");
				System.out.println("VWException Message: " + vwe.getMessage());
			}
			
		
		/*
		VWParameter[] parameters1 = stepElement.getParameters(VWFieldType.FIELD_TYPE_ATTACHMENT,VWStepElement.FIELD_USER_AND_SYSTEM_DEFINED);
		
				
		System.out.println("Attach Parameters Length -- > " + parameters1.length);
		
		 VWAttachment[] vwattach = (VWAttachment[]) stepElement.getParameterValue("Attach1");
		 
		 System.out.println("No of Attachement " + vwattach.length);
		 
		 System.out.println("Attach Name " + vwattach[0].getAttachmentName());
		 */
	
	/*	for (int i = 0; i < vwattach.length; i++ ) {
			
			 System.out.println("Attach Name " + vwattach[i].getAttachmentName());
			 System.out.println("Attach ID  " + vwattach[i].getId());*/
			
	/*	
		if (parameters[i].isArray()) {
			// Get the value for the VWAttachment
			 VWAttachment attachment =(VWAttachment) parameters1[i].getValue();
			
			System.out.println("Attach Parameters -- > " +parameters1[i].getValue());		
		}
		
		*/
			
		//}
		
		
	/*	String[] arrParamValues =new String[] {"User1"};
		String[] arrParamValues1 =new String[] {"Thiru"};
			//stepElement.setParameterValue(parameters[i].getName(),arrParamValues,true);
		stepElement.setParameterValue("Group1",arrParamValues,true);
		//stepElement.setParameterValue("Group2",arrParamValues1,true);
		stepElement.setParameterValue("ID","DDDSSSSDDDFFFFDDDDDD",true);
		//parameters[i].setValue(arrParamValues);
*/		

		
		//stepElement.doDispatch();
		
	//	stepElement.getComment();
		
	//	stepElement.doLock(false);
		
	
		
	//	stepElement.doLock(true);
		
		
	//	System.out.println("hai");

		
		//stepElement.doSave(true);
		
		//System.out.println("hai");
		
		//peSession.logoff();
		
		
		
			System.out.println("After dispatch ");
			return Group1;	
		

	}
}