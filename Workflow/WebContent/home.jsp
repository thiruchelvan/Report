<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.*"%>
<%@page import="com.filenet.api.collection.ContentElementList"%>
<%@page import="com.filenet.api.constants.AutoClassify"%>
<%@page import="com.filenet.api.constants.AutoUniqueName"%>
<%@page import="com.filenet.api.constants.CheckinType"%>
<%@page import="com.filenet.api.constants.DefineSecurityParentage"%>
<%@page import="com.filenet.api.constants.RefreshMode"%>
<%@page import="com.filenet.api.core.Connection"%>
<%@page import="com.filenet.api.core.ContentTransfer"%>
<%@page import="com.filenet.api.core.Domain"%>
<%@page import="com.filenet.api.core.Factory"%>
<%@page import="com.filenet.api.core.Folder"%>
<%@page import="com.filenet.api.core.ReferentialContainmentRelationship"%>
<%@page import="com.filenet.api.exception.EngineRuntimeException"%>
<%@page import="com.filenet.api.exception.ExceptionCode"%>
<%@page import="com.filenet.api.util.UserContext"%>
<%@page import="org.apache.commons.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="javax.security.auth.Subject"%>
<%@page import="com.filenet.api.core.ObjectStore"%>
<%@page import="com.filenet.api.query.SearchSQL" %>
<%@page import="com.filenet.api.query.SearchScope" %>
<%@page import="com.filenet.api.collection.EngineCollection" %>
<%@page import="com.filenet.api.core.Document" %>
<%@page import="com.filenet.api.core.Folder" %>
<%@page import="com.filenet.api.collection.StringList"%>
<%@page import="filenet.vw.api.VWSession"%>
 


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>


<body>

<%

String FILENET_USERNAME = "administrator";
String FILENET_PASSWORD	 = "filenet";

String FILENET_URI = "http://192.168.1.200:9080/wsi/FNCEWS40MTOM/";

String CE_DOMAIN	 = "FileNet";
String CE_OBJECTSTORE	 = "RCFOS1";

String PE_CONNECTION_POINT = "OS1";


//String Department="";
  String username1="administrator";
  String password1="filenet";
  username1="";
  password1="";
	   

    

	   
	   String USERID = "administrator";
	    
	    
	    
	    String PASSWORD = "filenet";
	    String OBJECT_STORE_NAME ="RCFOS1";
	   
	     String JAAS_STANZA_NAME = "FileNetP8WSI";
	//    boolean USE_SKIP = true;
	     String CE_URI = "http://192.168.1.200:9080/wsi/FNCEWS40MTOM";
	  //   System.setProperty("Djava.security.auth.login.config","E:\\Users\\Administrator\\Documents\\NCRReport\\NCR\\config\\jaas.conf.WSI");
	     //   System.setProperty("Dwasp.location","E:\\Users\\Administrator\\Documents\\NCRReport\\NCR\\config\\");
	     
	     Connection conn = Factory.Connection.getConnection(CE_URI);
     // System.out.println("CE is at " + CE_URI);
      //System.out.println("ObjectStore is " + OBJECT_STORE_NAME);
	     

	     
      
      
     
    	  System.out.println("False JASS");
          // This is the standard Subject push/pop model for the helper methods.
    	  System.out.println("\n"+conn);
    	  System.out.println("\n"+USERID);
    	  System.out.println("\n"+PASSWORD);
    	  System.out.println("\n"+JAAS_STANZA_NAME);
          Subject subject = UserContext.createSubject(conn, USERID,PASSWORD,JAAS_STANZA_NAME);
          UserContext.get().pushSubject(subject);
          
          
     
           
	   Domain dom1 = Factory.Domain.getInstance(conn, null);
	   
	   
       ObjectStore os1 = Factory.ObjectStore.getInstance(dom1,OBJECT_STORE_NAME);
	   System.out.println(dom1);
	   
	   System.out.println(os1);

 					
    VWSession peSession = new VWSession();
//	peSession.setBootstrapCEURI(FILENET_URI);

	
	peSession.setBootstrapPEURI(FILENET_URI);
	
	peSession.logon(FILENET_USERNAME, FILENET_PASSWORD, PE_CONNECTION_POINT);
	
	
	String Token = peSession.getToken();
	
	
	
	System.out.println("Connecting by url method");
	
	
	
	peSession.logonWithToken(Token, "OS1");
	
	

%>  

<%=os1 %>

<%=Token %>






</body>
</html>