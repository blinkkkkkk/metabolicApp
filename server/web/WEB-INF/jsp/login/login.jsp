<%@include file="../templates/login.inc"%>
<td class="content">
<h1>(Clinician) Login to the Application</h1>
<form method="post" action="">
    <%
    String message = (String) request.getAttribute("message");
    if(message != null) {
        out.println("<p>" + message + "</p>");
    }
    %>
    <div>
        Username: <input type="text" name="username" size="40"/>
    </div>
    <div>
        Password: <input type="password" name="password" size="40"/>
    </div>
    <div>
        <input type="submit" value="Login"/>
    </div>
</form>

<%@include file="../templates/bottom.inc"%>