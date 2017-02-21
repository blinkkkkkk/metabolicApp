
<%@page import="com.metabolic_app.data.Patient" %>
<jsp:useBean id="patient" scope="request" type="com.metabolic_app.data.Patient"/>
<%@include file="templates/top.inc"%>
<h1>Options for <%=patient.getFirst_name()%>, <%=patient.getLast_name()%></h1>
<h3>Details</h3>
<ul>
    <li>NHS No: <%=patient.getNHS_No()%></li>
    <li>DoB: <%=patient.getDoB()%></li>
</ul>
<form method="post" action="">
    <input type="hidden" name="id" value="<%=patient.getId()%>">
    <input type="submit" name="selection" value="input_test"/>
    <br/>
    <input type="submit" name="selection" value="view_trends"/>
</form>

<%@include file="templates/bottom.inc"%>
