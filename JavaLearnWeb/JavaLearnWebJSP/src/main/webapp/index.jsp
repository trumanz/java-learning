<html>
<head><title>Hello World Titile</title></head>
<body>
<%-- This is HTML comments --%>
<h2>Hello World!</h2>
<%-- This is JSP comments --%>

<%-- page Directive --%>
<%@ page buffer="8kb" %>
<%@ page import="java.util.*"%>
<p>Today's date: <%= (new Date()).toLocaleString()%><p>

<%-- include Directive --%>
<%@ include file="footer.jsp" %>

<%! int i=0; %>

<%
out.println("Your IP address is " + request.getRemoteAddr());
%>

<p>
Today's date: <%= (new java.util.Date()).toLocaleString()%>
</p>

<form action="cookies.jsp"  method="GET">
addCookieName: <input type="text" name="addCookieName">
<br/>
addCookieValue: <input type="text" name="addCookieValue">
<input type="submit" value="Submit"/>
</form>
<%-- session --%>
<%

Date createTime = new Date(session.getCreationTime());
Date lastAccessTime = new Date(session.getLastAccessedTime());

String useIDkey  = new String("userID");
String userID  = new String("ezoucai");
Integer visitCount = new Integer(0);
String visitCountKey = new String("visitCount");

if(session.isNew()){
	session.setAttribute(useIDkey, userID);
	session.setAttribute(visitCountKey, visitCount);
}

visitCount = (Integer)session.getAttribute(visitCountKey);
visitCount = visitCount + 1;
userID =  (String)session.getAttribute(userID);
session.setAttribute(visitCountKey, visitCount);

%>

<br/>

<table border="1" align="center">
	<tr bgcolor="#949494">
		<th>Session info</th>
		<th>Value</th>
	</tr>
	<tr>
		<th>id</th>
		<th><% out.print(session.getId()); %>></th>
	</tr>
	<tr>
		<th>Creation Time</th>
		<th><% out.print(createTime); %>></th>
	</tr>
	<tr>
		<th>Time of Last Access</th>
		<th><% out.print(lastAccessTime); %>></th>
	</tr>
	<tr>
		<th>User ID</th>
		<th><% out.print(userID); %>></th>
	</tr>
	<tr>
		<th>Number of visits</th>
		<th><% out.print(visitCount); %>></th>
	</tr>
</table>

</body>
</html>
