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
First Name: <input type="text" name="first_name">
<br/>
Last Name: <input type="text" name="last_name">
<input type="submit" value="Submit"/>
</form>

</body>
</html>
