<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%! Cookie[] cookies=null; %>

<%-- Handle cookie add or del --%>
<%
	Cookie firstName = new Cookie("first_name", request.getParameter("first_name"));
	Cookie lastName = new Cookie("last_name", request.getParameter("last_name"));

	firstName.setMaxAge(60 * 60 * 24);
	lastName.setMaxAge(60 * 60 * 24);

	response.addCookie(firstName);
	response.addCookie(lastName);
	
	String addCookieName = request.getParameter("addCookieName");
	String addCookieValue= request.getParameter("addCookieValue");
	String delCookieName = request.getParameter("delCookieName");
	
	cookies = request.getCookies();
	if(cookies != null){

		   for(int i = 0 ; i < cookies.length; i++){
			   if(cookies[i].getName().equals(delCookieName)){
				   cookies[i].setMaxAge(0);
				   response.addCookie(cookies[i]);
			   }
		   }
	}
	if(addCookieName != null){
		Cookie cookie = new Cookie(addCookieName, addCookieValue == null ? "" : addCookieValue);
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);
	}
%>
<html>
<h1>Setting Cookies</h1>
<ul>
	<li><p>
			<b>First Name:</b>
			<%=request.getParameter("first_name")%>
		</p></li>
	<li><p>
			<b>Last Name:</b>
			<%=request.getParameter("last_name")%>
		</p></li>
		<li><p>
			<b>addCookieName:</b>
			<%=request.getParameter("addCookieName")%>
		</p></li>
		<li><p>
			<b>addCookieValue</b>
			<%=request.getParameter("addCookieValue")%>
		</p></li>
		<li><p>
			<b>delCookieName</b>
			<%=request.getParameter("delCookieName")%>
		</p></li>
</ul>

<h1>Reading Cookies</h1>
<%
  
   cookies = request.getCookies();
   if(cookies != null){
	   out.println("<h2>Found Cookies Name and Value</h2>");
	   for(int i = 0 ; i < cookies.length; i++){
		   out.println("Name : " + cookies[i].getName() + ", ");
		   out.println("Name : " + cookies[i].getValue() + " <br/>");
	   }
   } else{
	   out.println("<h2>No Cookies founds</h2>");
   }
%>
<br/>

<h2>Add cookie</h2>
<form action="cookies.jsp"  method="GET">
Name: <input type="text" name="addCookieName">
<br/>
Value: <input type="text" name="addCookieValue">
<input type="submit" value="Submit"/>
<br/>

</form>
<h2>Del cookie</h2>
<form action="cookies.jsp"  method="GET">
Name: <input type="text" name="delCookieName">
<input type="submit" value="Submit"/>
</form>



<body>
</body>
</html>