<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payilagam</title>
    <style>
    .table {
        margin-left: 270px; /* Adjust according to sidebar width */
        padding: 20px;
        width: 500px;
    }
    .stdform {
        position: fixed;  /* Fix the position relative to the viewport */
        top: 0;           /* Align to the top */
        right: 0;         /* Align to the right */
        margin: 10px;     /* Add some margin for spacing */
        padding: 20px;
        width: 300px;     /* Adjust width as needed */
        background: white; /* Optional: Add background color */
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); /* Optional: Add a subtle shadow */
    }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function loadTable(cid) {
            fetch("${pageContext.request.contextPath}/viewTeachers?action=loadTable&std=" + encodeURIComponent(cid))
                .then(response => response.text())
                .then(data => {
                    document.querySelector(".table").innerHTML = data;
                });
        }
    </script>
</head>
<body>
<%@ include file="sidebar.jsp" %>
<h1 style="align-self: center">Teacher Info</h1>
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

<table class="table">
    <thead>
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
<%
    List<User> users = (List<User>) request.getAttribute("users"); // Assuming it's set in the request scope
        for (User user : users) {
%>
<tr>
    <td><%=user.getFirstName()%> <%=user.getLastName()%></td>
    <td><%=user.getEmail()%></td>
    <td><button> <a href="${pageContext.request.contextPath}/oneTeacher?email=<%=user.getEmail()%>&fname=<%=user.getFirstName()%>&lname=<%=user.getLastName()%>">view</a></button></td>
</tr>
<%}%>
    </tbody>
</table>
</body>
</html>
