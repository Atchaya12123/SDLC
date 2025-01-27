<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>





<jsp:useBean id="user" class="model.User" scope="session"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payilagam</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/CSS/principal.css' />">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>

<br>
<div class="menu-container">
    <div class="header">
        <!-- Logo and Title -->
        <div class="logo-container">
            <div class="logo" style=" width: 100px;
            height: 100px;
            border-radius: 50%;
            background-image: url('../IMGs/header.png'); /* Ensure correct path */
            background-size: cover;
            background-position: center;
            margin-right: 10px;
            padding: 0;"></div> <!-- Logo as CSS background -->
            <span class="title">Payilagam</span>
        </div>

        <!-- User Dropdown -->
        <div class="dropdown">
            <button class="dropbtn">▼ ${user.user_name}</button>
            <div class="dropdown-content">
                <a href="<%=request.getContextPath()%>/login">Change User (Logout)</a>
            </div>
            <span class="user-info">Logged in as: <p style="display: inline">${user.role_id == 0 ? 'Principal' : (user.role_id == 1 ? 'Teacher' : (user.role_id == 2 ? 'Student' : 'Unknown Role'))}</p>

        </span>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">Student Menu ▼</button>
        <div class="dropdown-content">
            <a href="#" onclick="viewStudents()">View Students</a> <!-- Trigger the function -->
        </div>
    </div>

    <!-- Teacher Menu -->
    <div class="dropdown">
        <button class="dropbtn">Teacher Menu ▼</button>
        <div class="dropdown-content">
            <a href="#" onclick="viewTeachers()">View Teachers</a>
            <a href="${pageContext.request.contextPath}/getAllStds?action=getStds" >Add Teacher</a>
            <a href="#" onclick="">Allocate Subjects</a>
            <a href="#" onclick="removeTeacherForm()">Remove Teacher</a>
        </div>
    </div>

    <!-- School Menu -->
    <div class="dropdown">
        <button class="dropbtn">School Menu ▼</button>
        <div class="dropdown-content">
            <a href="#" onclick="loadStdSubForm()">Add Stds to School</a>
            <a href="#" onclick="viewStdSub()">View Stds and Subs</a>
        </div>
    </div>
</div>
<div class="main-content">
    <div id="output">
        <h3 style="color:saddlebrown;">
            ${param.msg != null ? param.msg : ""}
        </h3>
        <!-- Output will be displayed here -->
    </div>
</div>
<script>

    function addTeacher() {
        fetch("/getAllStds?action=getStds,{method='GET'}");
    }
    function viewTeachers() {
        window.location.href = "/viewTeachers"; // Redirect to the servlet
    }
</script>
</body>
<footer class="footer">&copy; 2025 School Management System. All rights reserved.</footer>
</html>
