package sample;

import javax.security.auth.Subject;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

import filenet.vw.api.VWFieldType;
import filenet.vw.api.VWModeType;
import filenet.vw.api.VWParameter;
import filenet.vw.api.VWParticipant;
import filenet.vw.api.VWSession;
import filenet.vw.api.VWStepElement;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test1= new Test();
		
		
		
		
	}
	
	
	public static  void assigngroup()
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
		
		// Workflow name to launch
		String workflowName = "TestPE";
		
		// Retrieve transfered work classes
		String[] workClassNames = peSession.fetchWorkClassNames(true);
		
		VWStepElement stepElement = peSession.createWorkflow(workflowName);
		// Get and Set Workflow parameters for the Launch Step
	//	stepElement.doLock(true);
		VWParameter[] parameters = stepElement.
				getParameters(VWFieldType.ALL_FIELD_TYPES,
				VWStepElement.FIELD_USER_AND_SYSTEM_DEFINED);
		for (int i=0;i<workClassNames.length;i++)
		{
	//	System.out.println(workClassNames[i] );
		}
		// Launch Workflow
		
		
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
		
		// Retrieve Step Element Responses
/*		String[] stepResponses = stepElement.getStepResponses();
		if(stepResponses != null) {
		int len = stepResponses.length;
		for(int j = 0; j < len; j++ ) {
		// Process Response
		System.out.println("reponse "+stepResponses[j]);
		}
		
		}
		*/

		// Process Step Element Parameters
		
	//	System.out.println("parameters.length"+parameters.length);
		
		for (int i1 = 0; i1 < parameters.length; i1++ ) {
			//boolean readOnly =(parameters[i1].getMode() == VWModeType.MODE_TYPE_IN_OUT);
					// If the parameter is editable, switch through each data type
				//	if (!readOnly) {
					// For each data type,
					// check whether the parameter is single or an array
					// and set the parameter value(s)
	//int x=		VWModeType.MODE_TYPE_OUT;
					switch (parameters[i1].getFieldType()) {
					case VWFieldType.FIELD_TYPE_STRING:
						if (parameters[i].isArray()) 
						{
						//	System.out.println("Field Name "+parameters[i].getName());
							
							//System.out.println("Field Type "+parameters[i].getFieldType());
							int res= parameters[i].getName().compareTo("Group1");
							
							int res1= parameters[i].getName().compareTo("F_Responses");
							System.out.println("res = "+ res + "  Res1= "+res1);
						if(res==0)
							{
							System.out.println(" Field Name --> "+parameters[i].getName());
							
							//		System.out.println(" Field Type --> "+parameters[i].getFieldType());
							String[] arrParamValues =new String[] {"User1"};
								stepElement.setParameterValue(parameters[i].getName(),arrParamValues,true);
							//parameters[i].setValue(arrParamValues);
								stepElement.doDispatch();
								System.out.println("After dispatch ");	
						}
							
					//	String[] arrParamValues =new String[] {"[Administrator]"};
					//	stepElement.setParameterValue(parameters[i].getName(),arrParamValues,true);
						//stepElement.doDispatch();
						}
						 else
						{
						//	 System.out.println("ESLE Field Name "+parameters[i].getName());
								
					//			System.out.println("ESLE Field Type "+parameters[i].getFieldType());
							
					//	String paramValue = "[Administrator]";
						//stepElement.setParameterValue(parameters[i].getName(),paramValue,true);
						}
						break;
						
						/*case VWFieldType.FIELD_TYPE_PARTICIPANT:
						// Instantiate a new VWParticipant array
						VWParticipant[] participant = new VWParticipant[1];
						// Set the participant name using username value
						String participantUserName = "[Administrator]";
						participant[0].setParticipantName(participantUserName);
						// Set the parameter value
						stepElement.setParameterValue
						(parameters[i1].getName(),participant,true);
						break;
						default:
						// Do not take action for other data types
						break;*/
					//}	
					}
		}
	
	
		}
}

}