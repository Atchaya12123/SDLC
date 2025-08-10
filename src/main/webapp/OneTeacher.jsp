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
        }
        table, th, td {
            border: 1px solid saddlebrown;
            border-collapse: collapse; /* Merges borders for a cleaner look */
        }
        th, td {
            padding: 8px;
            text-align: center;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>

        function loadSubs(cid) {
            let title = document.getElementById("title").innerText;
            let words = title.split(" ");
            let lastWord = words[words.length-1];
            fetch("${pageContext.request.contextPath}/oneTeacher?action=loadSubs&std="+encodeURIComponent(cid)+"&email="+encodeURIComponent(lastWord))
                .then(response => response.text())
                .then(data => {
                    document.getElementById("subs").innerHTML = data;
                });}

    </script>
</head>
<body>
<%@ include file="sidebar.jsp" %>
<div class="main-content">
<%
    String html = request.getAttribute("html").toString();
    List<Integer> stds = (List<Integer>) request.getAttribute("stds");
%>
<%=html%>

<form class="addStdSub">
    <label>Add More Classes/Subjects:</label>
    <select id="std" name="std" onchange="loadSubs(this.value)" on>
        <option>Std</option>
        <% for(int std: stds){%>
        <option><%=std%></option>
        <%}%>
    </select>
    <select id="subs" name="sub"><option>Sub</option></select>
    <button type="submit">submit</button>
</form>
</div>
<script>
    document.querySelector(".addStdSub").addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent default form submission
        let title = document.getElementById("title").innerText;
        let words = title.split(" ");
        let email = words[words.length-1];
        var formData = new FormData(this);

        // Convert FormData to URL-encoded string
        var urlEncodedData = new URLSearchParams();
        for (var pair of formData.entries()) {
            urlEncodedData.append(pair[0], pair[1]); //Key Value pairs
        }
        urlEncodedData.append("action", "add");
        urlEncodedData.append("email", email);

        fetch("${pageContext.request.contextPath}/oneTeacher", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: urlEncodedData.toString() // Send as URL-encoded string
        })
            .then(response => response.text())
            .then(data => {
                alert("Class and Subject added successfully!\n" + data);
            })
            .catch(error => console.error("Error submitting form:", error));
        location.reload();

    });
    //REMOVE TEACHER
    function remove(tid,std,sub){
        console.log("fetch reached");
        if (confirm("Do you want to proceed?")) {
            // Convert FormData to URL-encoded string
            var urlEncodedData = new URLSearchParams();
            urlEncodedData.append("tid", tid);
            urlEncodedData.append("std", std);
            urlEncodedData.append("sub", sub);
            urlEncodedData.append("action", "remove");
            fetch("${pageContext.request.contextPath}/oneTeacher", {

                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: urlEncodedData.toString() // Send as URL-encoded string
            })
                .then(response => response.text())
                .then(data => {
                    alert("teacher removed successfully!\n" + data);
                })
                .catch(error => console.error("Error submitting form:", error));
        } else {
            console.log("User clicked Cancel");
            return
        }
        var ele = document.getElementById(""+sub+std);
        if(ele){
            ele.parentNode.removeChild(ele);
        }
    }
</script>
</body>
</html>
