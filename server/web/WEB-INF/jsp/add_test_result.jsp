<%@page import="com.metabolic_app.data.Patient" %>
<%@ page import="com.metabolic_app.data.TestType" %>
<jsp:useBean id="patient" scope="request" type="com.metabolic_app.data.Patient"/>
<%@include file="templates/top.inc"%>
<table>
    <h1>Add a test result to <%=patient.getFirst_name()%>, <%=patient.getLast_name()%></h1>
    <h3>NHS no.: <%=patient.getNHS_No()%></h3>
    <h3>DoB: <%=patient.getDoB()%></h3>
    <tr>
    <form method="post" action="">
        <%
            String message = (String) request.getAttribute("err_message");
            if(message != null) {
                out.println("<p>" + message + "</p>");
            }
        %>
        <br/>
            <%
                switch(TestType.toTest(request.getParameter("domain"))) {
                    case glucose_test:
            %>
            <div>
                Glucose Test Results: <input type="text" name="glucose_level">
            </div>
            <%
                    break;
                case ECG:
            %>
            <div>
                ECG Test Results: <input type="text" name="ECG"/>
            </div>
            <%
                    break;
                case BMI:
            %>
            <div>
                BMI Test Results: <input type="text" name="BMI"/>
            </div>
            <%
                    break;
                case HbA1c:
            %>
            <div>
                HbA1c Test Results: <input type="text" name="HbA1c"/>
            </div>
            <%
                    break;
                case liver:
            %>
            <div>
                Liver Test Results: <input type="text" name="liver"/>
            </div>
            <%
                        break;
                }
            %>
        <br/>

        <div>
            Message: <input type="text" name="message" size="40"/>
        </div>
        <br/>
        <div>
            Next-Test-Date (Leave Blank for default): <input type="datetime" name="next_test" size="40"/>
        </div>
        <br/>
        <div>
            <input type="submit" value="Submit"/>
        </div>
        <input type="hidden" value="<%=patient.getId()%>"/>
        <input type="hidden" value="<%=request.getParameter("domain")%>"/>
    </form>
    </tr>
</table>
<%@include file="templates/bottom.inc"%>