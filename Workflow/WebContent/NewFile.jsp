<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>




<%@page import="filenet.vw.api.VWAttachment" %>
<%@page import="filenet.vw.api.VWDataField" %>
<%@page import="filenet.vw.api.VWException" %>
<%@page import="filenet.vw.api.VWFetchType" %>
<%@page import="filenet.vw.api.VWFieldType" %>
<%@page import="filenet.vw.api.VWModeType"%>
<%@page import="filenet.vw.api.VWParameter"%>
<%@page import="filenet.vw.api.VWParticipant"%>
<%@page import="filenet.vw.api.VWQueue"%>
<%@page import="filenet.vw.api.VWQueueQuery"%>
<%@page import="filenet.vw.api.VWRoster"%>
<%@page import="filenet.vw.api.VWRosterElement"%>
<%@page import="filenet.vw.api.VWRosterQuery"%>
<%@page import="filenet.vw.api.VWSession"%>
<%@page import="filenet.vw.api.VWStepElement"%>
<%@page import="filenet.vw.api.VWWorkObjectNumber"%>
 
 


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>


<body>
<%String wbob=request.getParameter("wobNum"); %>

<%=wbob %>

<%=session.getAttribute("token1" ) %>

<%String token = ""+session.getAttribute("token1" ); %>

<% System.out.println("test" + wbob); %>

<%

 VWSession peSession1 = new VWSession();

peSession1.logonWithToken(token, "OS1");




%>

<%


	
		// Queue Name
			String queueName = "Inbox";
			// Retrieve the Queue
			VWQueue queue = peSession1.getQueue(queueName);
			// Set Query Parameters
			String wobNum = wbob;
			VWWorkObjectNumber wob = new VWWorkObjectNumber(wobNum);
			VWWorkObjectNumber[] queryMin = new VWWorkObjectNumber[1];
			VWWorkObjectNumber[] queryMax = new VWWorkObjectNumber[1];
			String queryIndex = "F_WobNum";
			queryMin[0] = wob;
			queryMax[0] = wob;
			// Query Flags and Type to retrieve Step Elements
			int queryFlags =  VWQueue.QUERY_MIN_VALUES_INCLUSIVE + VWQueue.QUERY_MAX_VALUES_INCLUSIVE  ;
			int queryType = VWFetchType.FETCH_TYPE_STEP_ELEMENT;
			
			
			VWQueueQuery queueQuery = queue.createQuery(queryIndex,queryMin,queryMax,queryFlags,null,null,queryType);
			
			System.out.println(queueQuery.fetchCount());
			
			VWStepElement stepElement = (VWStepElement) queueQuery.next();
			
					System.out.println("WON " + stepElement.getWorkObjectNumber());
					



%>
 






</body>
</html>