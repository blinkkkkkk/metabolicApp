<%@page import="com.metabolic_app.data.Patient" %>
<%@page import="com.metabolic_app.data.InboxEntry"%>
<%@ page import="com.metabolic_app.data.EntryType" %>
<jsp:useBean id="patient" scope="request" type="com.metabolic_app.data.Patient"/>
<jsp:useBean id="entry" scope="request" type="com.metabolic_app.data.InboxEntry"/>
<%@include file="templates/top.inc"%>
<h1><%=entry.getEntryType().toString()%> from <%=patient.getFirst_name()%>, <%=patient.getLast_name()%></h1>
<h3>Patient Details</h3>
<ul>
    <li>NHS No: <%=patient.getNHS_No()%></li>
    <li>DoB: <%=patient.getDoB()%></li>
</ul>

<%
    if(entry.getEntryType() == EntryType.REQUEST) {
%>
<h2>Accept or reject <%=entry.getEntryType().toString()%>?</h2></p>
<form method="post" action="">
    <input type="hidden" name="id" value="<%=patient.getId()%>">
    <input type="submit" name="selection" value="accept"/>
    <br/>
    <input type="submit" name="selection" value="reject"/>
</form>
<%} else { %>
<img src="/patient/image/?entry_id=<%=entry.getId()%>&id=<%=patient.getId()%>">
<form method="post" action="">
    <input type="selection" name="dismiss" value="dismiss"/>
</form>
<%}%>
<h5>Note: Pressing back does not dismiss or reject the submission.</h5>
<form method="get" action="/viewInbox">
    <input type="submit" value="Back"/>
</form>

<%@include file="templates/bottom.inc"%>
