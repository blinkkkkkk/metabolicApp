<%@include file="templates/top.inc"%>

<h1>Select a domain:</h1>

<select name="domain" form="main_form">
    <option value="glucose_test">Glucose Test</option>
    <option value="liver">Renal Tests</option>
    <option value="ECG">ECG</option>
    <option value="BMI">BMI</option>
    <option value="HbA1c">HbA1c</option>
</select>

<form method="get" action="<%

    String selection = (String) request.getParameter("selection");
    if(selection.equals("input_test")) out.print("/patient/input_test_results");
    else if(selection.equals("view_trends")) out.print("/patient/view_trends");
    else {response.sendRedirect("/getpatients");return;}

%>" id="main_form">
    <input type="hidden" name="id" value="<%=request.getParameter("id")%>">
    <input type="hidden" name="selection" value="<%=request.getParameter("selection")%>">

    <input type="submit" value="Select"/>
</form>

<%@include file="templates/bottom.inc"%>
