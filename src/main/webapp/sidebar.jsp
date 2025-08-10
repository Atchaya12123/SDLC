<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<body>
<link rel="stylesheet" type="text/css" href="<c:url value='/CSS/principal.css' />">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<br>
<div class="menu-container">
  <div class="header">
    <!-- Logo and Title -->
    <div class="logo-container">
      <div class="logo" style=" width: 100px;
              height: 100px;
              border-radius: 50%;
              background-image: url('${pageContext.request.contextPath}/IMGs/logo.png'); /* Ensure correct path */
              background-size: cover;
              background-position: center;
              margin-right: 10px;
              padding: 0;"></div> <!-- Logo as CSS background -->
      <span class="title">Payilagam</span>
    </div>

    <!-- User Dropdown -->
    <div class="dropdown">
      <button class="dropbtn"> ${user.firstName} ${user.lastName}</button>
      <div class="dropdown-content">
        <a href="<%=request.getContextPath()%>/logoutServlet">Change User (Logout)</a>
      </div>
      <span class="user-info">Logged in as: <p style="display: inline">${user.roleId == 0 ? 'Principal' : (user.roleId == 1 ? 'Teacher' : (user.roleId == 2 ? 'Student' : 'Unknown Role'))}</p>

        </span>
    </div>
  </div>
  <div class="dropdown">
    <button class="dropbtn">Student Menu </button>
    <div class="dropdown-content">
      <a href="${pageContext.request.contextPath}/studServlet"> View Students</a>
    </div>
  </div>

  <!-- Teacher Menu -->
  <div class="dropdown">
    <button class="dropbtn">Teacher Menu </button>
    <div class="dropdown-content">
      <a href="${pageContext.request.contextPath}/viewTeachers">View Teachers</a>
      <a href="${pageContext.request.contextPath}/addTeacher" >Add Teacher</a>
    </div>
  </div>

  <!-- School Menu -->
  <div class="dropdown">
    <button class="dropbtn">School Menu </button>
    <div class="dropdown-content">
      <a href="${pageContext.request.contextPath}/sclServlet">Add Stds/Subs to School</a>
    </div>
  </div>
</div>
<%--<div class="main-content">--%>
<%--    <div id="output">--%>
<%--        <h3 style="color:saddlebrown;">--%>
<%--            ${param.msg != null ? param.msg : ""}--%>
<%--        </h3>--%>
<%--        <!-- Output will be displayed here -->--%>
<%--    </div>--%>
<%--</div>--%>
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
