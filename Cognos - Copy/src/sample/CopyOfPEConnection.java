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

import filenet.vw.api.VWException;
import filenet.vw.api.VWFieldType;
import filenet.vw.api.VWMapDefinition;
import filenet.vw.api.VWModeType;
import filenet.vw.api.VWParticipant;
import filenet.vw.api.VWSession;
import filenet.vw.api.VWStepDefinition;


public class CopyOfPEConnection {

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

		System.out.println("Property: " + property.get_DisplayName());
		}

		UserContext.get().popSubject();
		System.out.println("trying to connect with pe");

		VWSession peSession = new VWSession();
	//	peSession.setBootstrapCEURI(FILENET_URI);
		
		peSession.setBootstrapPEURI(FILENET_URI);
		System.out.println("Connecting by url method");


		peSession.logon(FILENET_USERNAME, FILENET_PASSWORD, PE_CONNECTION_POINT);

		String[] queueNames = 
		peSession.fetchQueueNames(VWSession.QUEUE_PROCESS);

		for (String queue : queueNames) {
		System.out.println("Queue: " + queue);
		
		//addStep(VWMapDefinition mapDef, String stepName, String userName)
		
		String stepName = null; 
		String userName = null;
		
		
		VWMapDefinition mapDef =null;
		
		 VWStepDefinition    newStepDef = null;
	        int                 nStepId = -1;
	        VWParticipant[]     participants = null;

	        try
	        {
	            // create step as destination step for new route
	            newStepDef = mapDef.createStep(stepName);
	            nStepId = newStepDef.getStepId();

	            // create parameters
	            newStepDef.createParameter( "Field1_Integer", VWModeType.MODE_TYPE_IN, "99", VWFieldType.FIELD_TYPE_INT, false );
	            newStepDef.createParameter( "Field2_String", VWModeType.MODE_TYPE_OUT, "Field2_String", VWFieldType.FIELD_TYPE_STRING, true );

	            // assign the queue name
	            newStepDef.setQueueName("Inbox");

	            // assign a participant to the step
	            participants = new VWParticipant[1];
	            participants[0] = new VWParticipant();
	            participants[0].setParticipantName("\"" + userName + "\"");
	            newStepDef.setParticipants(participants);

	            // set a step deadline
	            newStepDef.setDeadline(1000);

	            // set a step reminder
	            newStepDef.setReminder(500);

	            // set the step description
	            newStepDef.setDescription("This is the description for " + stepName + ".");

	            //Set map location (for display in Designer)
	            newStepDef.setLocation(new java.awt.Point(nStepId * 100, 150));

	            //Write out the successful step-creation status
	            System.out.println("Creation and initialization of: " + stepName + " is complete.");
	        }
	        catch (Exception ex)
	        {
	           

	        
		
		}
			
		

	}
	
	}
}

