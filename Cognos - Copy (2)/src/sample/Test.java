package sample;

import javax.security.auth.Subject;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

import filenet.vw.api.VWFetchType;
import filenet.vw.api.VWFieldType;
import filenet.vw.api.VWModeType;
import filenet.vw.api.VWParameter;
import filenet.vw.api.VWParticipant;
import filenet.vw.api.VWQueue;
import filenet.vw.api.VWQueueQuery;
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
		
		test1.assigngroup("1AC366DE4BC55B4B810D87FAE2E27908");
		
		
	}
	
	
	public static  void assigngroup(String wbob)
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
		
/*		// Workflow name to launch
		String workflowName = "TestPE";
		
		// Retrieve transfered work classes
		String[] workClassNames = peSession.fetchWorkClassNames(true);
		
		VWStepElement stepElement = peSession.createWorkflow(workflowName);
		// Get and Set Workflow parameters for the Launch Step
	//	stepElement.doLock(true);
	 * 
	 * 
	 *
	 *
	 *
*/		
			
			// Queue Name
			String queueName = "Inbox";
			// Retrieve the Queue
			VWQueue queue = peSession.getQueue(queueName);
			// Set Query Parameters
			String wobNum = "1AC366DE4BC55B4B810D87FAE2E27908";
			VWWorkObjectNumber wob = new VWWorkObjectNumber(wobNum);
			VWWorkObjectNumber[] queryMin = new VWWorkObjectNumber[1];
			VWWorkObjectNumber[] queryMax = new VWWorkObjectNumber[1];
			String queryIndex = "F_WobNum";
			queryMin[0] = wob;
			queryMax[0] = wob;
			// Query Flags and Type to retrieve Step Elements
			int queryFlags =  VWQueue.QUERY_MIN_VALUES_INCLUSIVE + VWQueue.QUERY_MAX_VALUES_INCLUSIVE;
			int queryType = VWFetchType.FETCH_TYPE_STEP_ELEMENT;
			VWQueueQuery queueQuery = queue.createQuery(queryIndex,queryMin,queryMax,queryFlags,null,null,queryType);
			
			VWStepElement stepElement = (VWStepElement) queueQuery.next();
			
			stepElement.doLock(true);
			
		VWParameter[] parameters = stepElement.	getParameters(VWFieldType.ALL_FIELD_TYPES,VWStepElement.FIELD_USER_AND_SYSTEM_DEFINED);

		System.out.println("Parameter length " + parameters.length);
		
		System.out.println("Parameter length " + parameters[0].getName());
		
		System.out.println("Parameter length " + parameters[1].getName());
		
		System.out.println("Parameter length " + parameters[2].getName());
		
		

		String[] arrParamValues =new String[] {"User1"};
		String[] arrParamValues1 =new String[] {"Thiru"};
			//stepElement.setParameterValue(parameters[i].getName(),arrParamValues,true);
		stepElement.setParameterValue("Group1",arrParamValues,true);
		stepElement.setParameterValue("Group2",arrParamValues1,true);
		//parameters[i].setValue(arrParamValues);
		
	
		
		stepElement.doSave(true);
		
			//stepElement.doDispatch();
			System.out.println("After dispatch ");	
		

	}
}