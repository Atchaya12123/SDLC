<%@ page import="java.util.List" %>
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
    .main-content {
        margin-left: 270px; /* Adjust according to sidebar width */
        padding: 20px;
        width: 500px;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
    }
    label{
        padding-top: 30px;
    }
    button{
        margin-top: 30px;
    }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>

        function loadSubs(cid) {
            fetch("${pageContext.request.contextPath}/addTeacher?action=loadSubs&std=" + encodeURIComponent(cid))
                .then(response => response.text())
                .then(data => {
                    document.getElementById("subs").innerHTML = data;
                });
        }

    </script>

</head>
<body>
<%@ include file="sidebar.jsp" %>
<form class="main-content">
    <h1>Add Teacher</h1>
    <label>First Name:</label>
    <input name="fname" placeholder="Enter teacher first name" required>
    <label>Last name:</label>
    <input name="lname" placeholder="Enter teacher last name" required>
    <label>Email:</label>
    <input name="mail" placeholder="Enter teacher email" required>
    <label>Password:</label>
    <input name="password" placeholder="Enter teacher password" required>

    <label>Standard:</label>
    <select id="class" name="std" onchange="loadSubs(this.value)" required>
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
    <label>Subject:</label>
    <select id="subs" name="sub" required><option>Subject:</option></select>
<button type="submit">Add</button>
</form>
<div class="output">

</div>
<script>
    document.querySelector(".main-content").addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent default form submission

        var formData = new FormData(this);

        // Convert FormData to URL-encoded string
        var urlEncodedData = new URLSearchParams();
        for (var pair of formData.entries()) {
            urlEncodedData.append(pair[0], pair[1]);
        }

        fetch("${pageContext.request.contextPath}/addTeacher", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: urlEncodedData.toString() // Send as URL-encoded string
        })
            .then(response => response.text())
            .then(data => {
                alert("Teacher added successfully!\n" + data);
            })
            .catch(error => console.error("Error submitting form:", error));
    });
</script>
</body>
</html>
