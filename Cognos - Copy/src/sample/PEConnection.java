package sample;

import java.util.Iterator;

import javax.security.auth.Subject;

import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.PropertyDefinition;
import com.filenet.api.collection.PropertyDefinitionList;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

import filenet.vw.api.VWAttachment;
import filenet.vw.api.VWAttachmentType;
import filenet.vw.api.VWException;
import filenet.vw.api.VWFetchType;
import filenet.vw.api.VWFieldType;
import filenet.vw.api.VWLibraryType;
import filenet.vw.api.VWMapDefinition;
import filenet.vw.api.VWModeType;
import filenet.vw.api.VWParameter;
import filenet.vw.api.VWParticipant;
import filenet.vw.api.VWQueue;
import filenet.vw.api.VWQueueQuery;
import filenet.vw.api.VWSession;
import filenet.vw.api.VWStepDefinition;
import filenet.vw.api.VWStepElement;
import filenet.vw.api.VWWorkObjectNumber;


public class PEConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		 String FILENET_USERNAME = "administrator";
		 String FILENET_PASSWORD	 = "filenet";

		 String FILENET_URI = "http://192.168.1.200:9080/wsi/FNCEWS40MTOM/";

		 String CE_DOMAIN	 = "FileNet";
		 String CE_OBJECTSTORE	 = "RCFOS1";

		 String PE_CONNECTION_POINT = "OS1";

		

		/*
		* Connect to Content Engine and retrieve a list of 
		* properties for the base 'Document' document class
		*/
		Connection ceConnection = 
		Factory.Connection.getConnection(FILENET_URI);
		System.out.println("Connection Name:"+ ceConnection);

		Subject ceSubject = UserContext.createSubject(ceConnection, FILENET_USERNAME, FILENET_PASSWORD, null);
		System.out.println("CESubject:"+ceSubject);


		UserContext.get().pushSubject(ceSubject);

		Domain ceDomain = Factory.Domain.fetchInstance(ceConnection, CE_DOMAIN, null);
		System.out.println("CE Domain deails:"+ceDomain);

		ObjectStore ceObjectStore = Factory.ObjectStore.fetchInstance(ceDomain, CE_OBJECTSTORE, null);

		ClassDefinition classDef = Factory.ClassDefinition.fetchInstance(ceObjectStore, "Document", null);

		PropertyDefinitionList properties = classDef.get_PropertyDefinitions();

		for (Iterator propertyIter = properties.iterator(); propertyIter.hasNext();) {
		PropertyDefinition property = (PropertyDefinition) propertyIter.next();

		//System.out.println("Property: " + property.get_DisplayName());
		}

		UserContext.get().popSubject();
		System.out.println("trying to connect with pe");

		VWSession peSession = new VWSession();
	//	peSession.setBootstrapCEURI(FILENET_URI);
		
		peSession.setBootstrapPEURI(FILENET_URI);
		System.out.println("Connecting by url method");


		peSession.logon(FILENET_USERNAME, FILENET_PASSWORD, PE_CONNECTION_POINT);

		//String[] queueNames = 	peSession.fetchQueueNames(VWSession.QUEUE_PROCESS);
/*
		for (String queue : queueNames) {
		System.out.println("Queue: " + queue);
		}
		// Workflow name to launch
		String workflowName = "TestPE";
		// Retrieve transfered work classes
		String[] workClassNames = peSession.fetchWorkClassNames(true);
		for (int i=0;i<workClassNames.length;i++)
		System.out.println(workClassNames[i] );
		// Launch Workflow
		VWStepElement stepElement = peSession.createWorkflow(workflowName);
		// Get and Set Workflow parameters for the Launch Step
	
		// Dispatch Worflow Launch Step
		stepElement.doDispatch();
		
		*/
		// Queue Name
		String queueName = "Inbox";
		// Retrieve the Queue
		VWQueue queue = peSession.getQueue(queueName);
		// Set Query Parameters
		String wobNum = "7A14EA464DFEE245B849A6D50E9CF546";
		VWWorkObjectNumber wob = new VWWorkObjectNumber(wobNum);
		System.out.println("wob"+wob);
		VWWorkObjectNumber[] queryMin = new VWWorkObjectNumber[1];
		VWWorkObjectNumber[] queryMax = new VWWorkObjectNumber[1];
		String queryIndex = "F_WobNum";
		queryMin[0] = wob;
		queryMax[0] = wob;
		System.out.println("11111--------------------------------------------");	
		
		
		// Query Flags and Type to retrieve Step Elements
		//int queryFlags = VWQueue.QUERY_READ_LOCKED;
		int queryType = VWFetchType.FETCH_TYPE_STEP_ELEMENT;
		
		int queryFlags = VWQueue.QUERY_MIN_VALUES_INCLUSIVE + VWQueue.QUERY_MAX_VALUES_INCLUSIVE; 


		
		
		VWQueueQuery queueQuery = queue.createQuery(queryIndex,queryMin,queryMax,queryFlags,null,null,queryType);
		// Get an individual Step Element
		VWStepElement stepElement = (VWStepElement) queueQuery.next();
		
	//	VWStepElement stepElement1 = (VWStepElement) queueQuery.next();
		
		//VWStepElement stepElement2 = (VWStepElement) queueQuery.next();
		
		
		
		System.out.println(stepElement.getStepName());

		//queueQuery.next();
		
		// stepElement1 = (VWStepElement) queueQuery.next();
		
	//	 System.out.println(stepElement1.getStepName());
		
		 
		VWParameter[] parameters = stepElement.getParameters(VWFieldType.ALL_FIELD_TYPES,VWStepElement.FIELD_USER_AND_SYSTEM_DEFINED);
		
		System.out.println("2222222222--------------------------------------------");	
		
		System.out.println("System.out.println"+parameters.length);
		

		
		
		for (int i = 0; i < parameters.length; i++ ) {
		
	//	System.out.println("getParticipantName"+stepElement.getParticipantName());
		
	//	boolean readOnly =(parameters[1].getMode() == VWModeType.MODE_TYPE_IN);
		System.out.println("parameters[i] -->"+parameters[i].getName());
		
		System.out.println("parameters[i] -->"+parameters[i].getFieldType());
		
		System.out.println("parameters[i] -->"+parameters[i].getStringValue());
		
		System.out.println("parameters[i] -->"+parameters[i].getDescription());
		
		//System.out.println("VWFieldType.FIELD_TYPE_STRING--> " + VWFieldType.FIELD_TYPE_STRING);
	//	System.out.println("VWFieldType.FIELD_TYPE_PARTICIPANT--> " + VWFieldType.FIELD_TYPE_PARTICIPANT);
		
		int x= VWFieldType.FIELD_TYPE_PARTICIPANT;
		
		System.out.println("Count -----------------------------------   --> "+i);
		
		VWParticipant[] participant = new VWParticipant[3];	
		
		// Set the participant name using username value
		String participantUserName = "User1";
		participant[i].setParticipantName(participantUserName);
		// Set the parameter value
		stepElement.setParameterValue(parameters[i].getName(),participant,true);
		System.out.println("Inside partipants");
		
		}
}
}
