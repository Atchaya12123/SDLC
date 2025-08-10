<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Payilagam</title>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Payilagam</title>
        <style>
            .main-content {
                margin-left: 270px; /* Adjust according to sidebar width */
                padding: 20px;
                width: 500px;
            }
        </style>
</head>
<body>
<%@ include file="sidebar.jsp" %>
<div class="main-content">
    <h1>Student Info</h1>
<form class="stdform">
    <a href="${pageContext.request.contextPath}/viewTeachers">All Stds</a>
    <label>Standard:</label>
    <select id="class" name="std" onchange="loadTable(this.value)" required>
        <option>Std</option>
        <%
            List<Integer> stds = (List<Integer>) request.getAttribute("stds"); // Assuming it's set in the request scope
            System.out.println("Stds got");
            if (stds != null) {
                System.out.println("Its not null");
                for (int std : stds) {
        %>
        <option value="<%= std %>">   <%= std %></option>
        <%}}%>
    </select>
</form>
<table>
    <thead>
    <tr>
        <th>SlNo</th>
        <th>Name</th>
        <th>School Id</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<List<Object>> studs = (List<List<Object>>)(request.getAttribute("studs"));
        for(List<Object> obj: studs){
    %>
    <tr>
        <td><%=obj.get(0)%></td>
        <td><%=obj.get(1)%> <%=obj.get(2)%></td>
        <td><%=obj.get(3)%></td>
        <td><button> <a href="${pageContext.request.contextPath}/studServlet?sid=<%=obj.get(3)%>&fname=<%=obj.get(1)%>&lname=<%=obj.get(2)%>">view</a></button></td>
    </tr>
    <%}%>
    </tbody>
</table>
</div>
</body>
</html>
