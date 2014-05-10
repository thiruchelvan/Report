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


public class Copy_2_of_PEConnection {

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
		
		VWParameter[] parameters = stepElement.getParameters(VWFieldType.ALL_FIELD_TYPES,VWStepElement.FIELD_USER_AND_SYSTEM_DEFINED);
		
		System.out.println("2222222222--------------------------------------------");	
		
		
		// Get the name, type, mode, and value for each parameter
		for (int i = 0; i < parameters.length; i++ ) {
		// Get Parameter Name
		String parameterName = parameters[i].getName();
		// Get Parameter Type (Field Type Flag)
		String parameterType = VWFieldType.getLocalizedString
		(parameters[i].getFieldType());
		// Get Parameter Mode (Field Mode Flag)
		String parameterMode = VWModeType.getLocalizedString
		(parameters[i].getMode());
		// Get Parameter Value
		String parameterValue =parameters[i].getStringValue();
		}
		// Retrieve Step Element Responses
		String[] stepResponses = stepElement.getStepResponses();
		if(stepResponses != null) {
		int len = stepResponses.length;
		for(int j = 0; j < len; j++ ) {
		// Process Response
		System.out.println("stepresponse"+stepResponses[j]);
		}
		
		
		}
		
		System.out.println("3333333333333--------------------------------------------");	
		
		// Lock the Step Element
		stepElement.doLock(true);
		// Process Step Element Parameters
		for (int i = 0; i < parameters.length; i++ ) {
			
			System.out.println("444444444444--------------------------------------------");	
		// Check parameter mode
		boolean readOnly =
		(parameters[i].getMode() == VWModeType.MODE_TYPE_IN);
		// If the parameter is editable, switch through each data type
		if (!readOnly) {
		// For each data type,
		// check whether the parameter is single or an array
		// and set the parameter value(s)
			System.out.println("VWFieldType.FIELD_TYPE_STRING:"+VWFieldType.FIELD_TYPE_STRING);
		switch (parameters[i].getFieldType())
		{
		
		case VWFieldType.FIELD_TYPE_STRING:
			System.out.println("5555555555--------------------------------------------");	
		/*if (parameters[i].isArray()) {
		String[] arrParamValues =
		new String[] {"value_1", "value_2", "value_3"};
		stepElement.setParameterValue
		(parameters[i].getName(),arrParamValues,true);
		} else {
		String paramValue = "value_1";
		stepElement.setParameterValue(parameters[i].getName(),paramValue,true);
		}*/
		
		System.out.println("777777777777--------------------------------------------");	
		// Instantiate a new VWParticipant array
		VWParticipant[] participant = new VWParticipant[1];
		// Set the participant name using username value
		String participantUserName = "Administrator";
		participant[0].setParticipantName(participantUserName);
		// Set the parameter value
		stepElement.setParameterValue(parameters[i].getName(),participant,true);
		System.out.println("Inside partipants");
		break;
		
		case VWFieldType.FIELD_TYPE_ATTACHMENT:
			System.out.println("66666666--------------------------------------------");	
			if (!parameters[i].isArray()) {
			// Get the value for the VWAttachment
			VWAttachment attachment =
			(VWAttachment) parameters[i].getValue();
			// Set the attachment name
			attachment.setAttachmentName("Document Title");
			// Set the attachment description
			attachment.setAttachmentDescription
			("A document added programmatically");
			// Set the type of object (Document)
			attachment.setType
			(VWAttachmentType.ATTACHMENT_TYPE_DOCUMENT);
			// Set the library type and name (CE Object Store)
			attachment.setLibraryType
			(VWLibraryType.LIBRARY_TYPE_CONTENT_ENGINE);
			attachment.setLibraryName("ObjectStoreName");
			// Set the document ID and version
			attachment.setId
			("{BBE5AD7F-2449-4DC3-AA38-012A65EC4286}");
			attachment.setVersion
			("{BBE5AD7F-2449-4DC3-AA38-012A65EC4286}");
			// Set the parameter value
			stepElement.setParameterValue
			(parameters[i].getName(),attachment,true);
			}
			break;
		case VWFieldType.FIELD_TYPE_PARTICIPANT:
			
			System.out.println("777777777777--------------------------------------------");	
			// Instantiate a new VWParticipant array
			VWParticipant[] participant1 = new VWParticipant[1];
			// Set the participant name using username value
			String participantUserName1 = "Thiru";
			participant1[0].setParticipantName(participantUserName1);
			// Set the parameter value
			stepElement.setParameterValue(parameters[i].getName(),participant1,true);
			System.out.println("Inside partipants");
			break;
			default:
			// Do not take action for other data types
			break;
			}
			}
		}
		// Set the value for the system-defined Response parameter
		if (stepElement.getStepResponses() != null) {
		String responseValue = "Ok";
		stepElement.setSelectedResponse(responseValue);
		}
		
}
}
