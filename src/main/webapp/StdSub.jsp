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
        /* Basic styles for the page */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }

        /* Updated dropdown styles */
        .dropdown-container {
            position: relative;
            display: inline-block;
            margin: 20px;
        }

        .dropdown-button {
            width: inherit;
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .dropdown-button:hover {
            background-color: #45a049;
        }

        .dropdown-list {
            display: none;
            position: absolute;
            background-color: white;
            min-width: 160px;
            box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
            z-index: 1;
            border-radius: 5px;
        }

        .dropdown-list p {
            padding: 12px 16px;
            margin: 0;
            color: #333;
            font-size: 14px;
        }
        .dropdown-container:hover .dropdown-list {
            display: block;
        }
        /* Style for the main content */
        .main-content {
            padding: 20px;
        }
        select {
            padding: 10px;
            margin: 10px 0;
            font-size: 16px;
            width: 200px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function loadSubs(cid) {
            fetch("${pageContext.request.contextPath}/addTeacher?action=loadSubs&std=" + encodeURIComponent(cid))
                .then(response => response.text())
                .then(data => {
                    document.getElementById("SelSubs").innerHTML = data;
                });
        }
    </script>
</head>
<body>
<%@ include file="sidebar.jsp" %>
<div class="main-content">
    <h1>School Info</h1>
    <form>
        <label>Offered Stds: (Select Std to view subs)</label>
        <select onchange="loadSubs(this.value)">
            <option>Stds</option>
            <% List<Integer> stds = (List<Integer>) request.getAttribute("stds");
                for(int std: stds) { %>
            <option><%= std %></option>
            <% } %>
        </select>
    </form>

    <div class="dropdown-container">
        <button class="dropdown-button">All subs ▼</button>
        <div class="dropdown-list" id="AllSubs">
            <% List<String> subs = (List<String>) request.getAttribute("subs");
                for(String sub: subs) { %>
            <p><%= sub %></p>
            <% } %>
        </div>
    </div>

    <div class="dropdown-container">
        <button class="dropdown-button">Subs in selected std ▼</button>
        <div class="dropdown-list" id="SelSubs">
        </div>
    </div>
    <form id="addT">
        <h2>Add New Std or Sub</h2>
        <label>Enter Std(range 1-12):</label>
        <select  style="display: block; margin-bottom:30px" name="std" required>
            <%for(int i=1 ;i<13;i++){%>
            <option><%=i%></option>
            <%}%>
        </select>
        <label>Enter Sub:</label>
        <input type="text" id="searchBox" onkeyup="searchWords()" placeholder="Type to search..." name="sub" required>
        <ul id="results" style="list-style-type: none; padding: 0; margin-left: 50px; margin-top:0; max-height: 200px; overflow-y: auto; border: 1px solid #ddd; position: absolute; width: 200px; background-color: white;"></ul>
        <button type="submit">Add Std and Sub</button>
        <h4></h4>
    </form>
</div>
<script>
    document.querySelector("#addT").addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent default form submission
        var formData = new FormData(this);
        // Convert FormData to URL-encoded string
        var urlEncodedData = new URLSearchParams();
        for (var pair of formData.entries()) {
            //Form submission will be blocked with required
            urlEncodedData.append(pair[0], pair[1]); //Key Value pairs
        }
        fetch("${pageContext.request.contextPath}/sclServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: urlEncodedData.toString() // Send as URL-encoded string
        })
            .then(response => response.text())
            .then(data => {
                alert(data);
            })
            .catch(error => console.error("Error submitting form:", error));
        location.reload();
    });

    //SEARCH
    function searchWords() {
        console.log("function reached");
        let query = document.getElementById("searchBox").value;
        // Clear the results if the search box is empty
        if (query.length === 0) {
            document.getElementById("results").innerHTML = "";
            return;
        }
        // Fetch data from the server with the query parameter
        fetch("${pageContext.request.contextPath}/sclServlet?query="+query)
            .then(response => response.json())
            .then(data => {
                let resultsDiv = document.getElementById("results");
                resultsDiv.innerHTML = ""; // Clear previous results
                console.log("fetch is functioning");
                // Generate list items for each word in the results
                data.forEach(word => {
                    let li = document.createElement("li");
                    li.textContent = word;
                    li.style.padding = "8px";
                    li.style.cursor = "pointer";
                    // Add hover effect for the list items
                    li.onmouseover = () => li.style.backgroundColor = "#f1f1f1";
                    li.onmouseout = () => li.style.backgroundColor = "";

                    // Add click event to select the word from the dropdown
                    li.onclick = () => {
                        document.getElementById("searchBox").value = word; // Set search box value to the selected word
                        resultsDiv.innerHTML = ""; // Clear the dropdown after selection
                    };

                    resultsDiv.appendChild(li); // Add list item to the results
                });
            })
            .catch(error => console.error("Error fetching data:", error));
    }
</script>
</body>
</html>
