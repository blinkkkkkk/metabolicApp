<%@page import="java.util.Iterator"%>
<%@page import="com.metabolic_app.data.Patient" %>
<%@page import="com.metabolic_app.data.TestResult" %>
<%@ page import="com.metabolic_app.data.TestType" %>
<jsp:useBean id="testResults" scope="request" type="java.util.List"/>
<jsp:useBean id="patient" scope="request" type="com.metabolic_app.data.Patient"/>
<%@include file="templates/top.inc"%>
<h1><%=request.getParameter("domain")%> Results for <%=patient.getFirst_name()%>, <%=patient.getLast_name()%></h1>
<h3>Details</h3>
<ul>
    <li>NHS No: <%=patient.getNHS_No()%></li>
    <li>DoB: <%=patient.getDoB()%></li>
</ul>
<table>
    <tr>
            <%
          switch(TestType.toTest(request.getParameter("domain"))) {
                case glucose_test:
                    %>
        <td>Glucose Test Results</td>
            <%
                    break;
                case ECG:
                    %>
        <td>ECG Test Results</td>
            <%
                    break;
                case BMI:
                    %>
        <td>BMI Test Results</td>
            <%
                    break;
                case HbA1c:
                    %>
        <td>HbA1c Test Results</td>
            <%
                    break;
                case liver:
                    %>
        <td>Liver Test Results</td>
            <%
                    break;
            }
    %>
        <td>Clinician Id</td>
        <td>Date Created</td>
        <td>Message</td>
        <td>Next Test Date</td>
    </tr>
    <%
        Iterator it = testResults.iterator();
        while(it.hasNext()) {
            TestResult testResult = (TestResult) it.next();
    %>
    <tr>
    <%
          switch(testResult.getType()) {
                case glucose_test:
                    %>
                    <td><%=testResult.getGlucose_level()%></td>
                    <%
                    break;
                case ECG:
                    %>
                    <td><%=testResult.getECG()%></td>
                    <%
                    break;
                case BMI:
                    %>
                    <td><%=testResult.getBMI()%></td>
                    <%
                    break;
                case HbA1c:
                    %>
                    <td><%=testResult.getHbA1c()%></td>
                    <%
                    break;
                case liver:
                    %>
                    <td><%=testResult.getLiver()%></td>
                    <%
                    break;
            }
    %>
        <td><%=testResult.getClinician_id()%></td>
        <td><%=testResult.getDate_created().toString()%></td>
        <td><%=testResult.getMessage()%></td>
        <td><%=testResult.getNext_test().toString()%></td>
        </tr>
    <%}%>
</table>

<%@include file="templates/bottom.inc"%>