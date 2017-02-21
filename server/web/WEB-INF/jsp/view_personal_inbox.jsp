<%@page import="java.util.Iterator"%>
<%@page import="com.metabolic_app.data.InboxEntry" %>
<%@ page import="com.metabolic_app.data.EntryType" %>
<jsp:useBean id="inboxEntries" scope="request" type="java.util.List"/>
<%@include file="templates/top.inc"%>
<h1>Clinician Inbox</h1>
    <ul>
    <%
        Iterator it = inboxEntries.iterator();
        while(it.hasNext()) {
            InboxEntry inboxEntry = (InboxEntry) it.next();
    %>
    <li><%=inboxEntry.getEntryType().toString()%> from <%=inboxEntry.getName()%>
        <form method="get" action="<%
        if(inboxEntry.getEntryType()==EntryType.SUBMISSION)
            out.print("/patient/view-inbox-submission");
        else
            out.print("/view-inbox-request");

        %>">
        <input type="hidden" name="id" value="<%=inboxEntry.getPatient_id()%>"/>
            <input type="hidden" name="entry_id" value="<%=inboxEntry.getId()%>"/>

            <input type="submit" value="view"/>
        </form>
    </li>
    <%}%>
    </ul>

<%@include file="templates/bottom.inc"%>