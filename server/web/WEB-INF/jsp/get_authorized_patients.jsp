<%@page import="java.util.Iterator" %>
<%@page import="com.metabolic_app.data.Patient" %>
<%@page import="com.metabolic_app.data.Clinician" %>
<jsp:useBean id="clinician" scope="request" type="com.metabolic_app.data.Clinician"/>
<jsp:useBean id="patients" scope="request" type="java.util.List"/>
<%@include file="templates/top.inc"%>
<h2><%out.print("Authorized Patients for " + clinician.getUsername());%></h2>
<ul>
<%
    Iterator it = patients.iterator();
    while(it.hasNext()) {
        Patient patient = (Patient) it.next();
%>
<li>
    <form method="get" action="/patient/view">
        <input type="hidden" name="id" value="<%=patient.getId()%>"/>
        <p>"><%=patient.getFirst_name()%>, <%=patient.getLast_name()%></p>
    <br/>
    <p>DoB: <%=patient.getDoB()%></p>
    <br/>
    <p>NHS no: <%=patient.getNHS_No()%></p>
    <br/>
        <div>
        <input type="submit" value="select"/>
        </div>
    </form>
</li>
<%}%>
</ul>
<%@include file="templates/bottom.inc"%>
