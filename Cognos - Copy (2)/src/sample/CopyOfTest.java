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

public class CopyOfTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CopyOfTest test1= new CopyOfTest();
		
		test1.assigngroup();
		
		
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
	

		String[] arrParamValues =new String[] {"User1"};
		String[] arrParamValues1 =new String[] {"User1"};
			//stepElement.setParameterValue(parameters[i].getName(),arrParamValues,true);
		stepElement.setParameterValue("Group1",arrParamValues,true);
		stepElement.setParameterValue("Group2",arrParamValues1,true);
		//parameters[i].setValue(arrParamValues);
			stepElement.doDispatch();
			System.out.println("After dispatch ");	
		

	}
}