<%@include file="../templates/login.inc"%>
<td class="content">
    <h1>(Clinician) Create an account on the Application</h1>
    <form method="post" action="">
        <%
            String message = (String) request.getAttribute("message");
            if(message != null) {
                out.println("<p>" + message + "</p>");
            }
        %>
        <div>
            Full Name: <input type="text" name="full_name" size="40"/>
        </div>
        <div>
            Practice No.: <input type="text" name="practice_no" size="40"/>
        </div>
        <div>
            GMC No.: <input type="text" name="GMC_no" size="40"/>
        </div>
        <div>
            Email: <input type="text" name="email" size="40"/>
        </div>
        <div>
            Password: <input type="password" name="password" size="40"/>
        </div>
        <div>
            <input type="submit" value="Login"/>
        </div>
    </form>

<%@include file="../templates/bottom.inc"%>