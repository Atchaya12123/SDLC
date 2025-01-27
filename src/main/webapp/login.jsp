<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Login</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      text-align: center;
      margin: 0;
      padding: 0;
      height: 100vh;
      /* Background Image */
      background: url('${pageContext.request.contextPath}/webapp/IMGs/loginPageBG.png') center fixed;
      background-size: contain;
      background-position: center;
    }
    .logo {
      width: 140px;
      height: 110px;
      background-image: url('${pageContext.request.contextPath}/webapp/IMGs/logo.png'); /* Ensure correct path */
      background-size: cover;
      background-position: center;
      margin-right: 10px;
      padding: 0;
    }
    .logo-container {
      display: flex;
      align-items: center;
      flex-flow: column;
    }
    h3 {
      display: block;
      font-size: 18px;
      color: #474747;
      margin: 0px 0px 25px 0px;
      padding: 0;
      font-family: cursive;
    }
    .container {
      width: 30%;
      padding: 20px;
      background: rgba(255, 255, 255, 0.85); /* Semi-transparent background */
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
      border-radius: 10px;
      position: absolute;
      margin-left: 470px;
      margin-top: 100px;
      margin-bottom: 180px;
      padding: 5px;
    }
    label {
      display: block;
      font-size: 16px;
      margin-top: 5px;
    }
    input {
      width: 85%;
      padding: 8px;
      margin: 5px 0;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
    button {
      width: 100%;
      padding: 10px;
      background-color: #007bff;
      color: white;
      border: none;
      cursor: pointer;
      margin-top: 15px;
      border-radius: 5px;
    }
    button:hover {
      background-color: #45811d;
    }

  </style>
</head>
<body>

<div class="container">
<div class="logo-container">
  <div class="logo"></div>
<h1>Payilagam</h1>
<h3>The opportunites we provide is our identity</h3>
</div>
<br/>
  <form>
  <label>UserName(Email):</label>
  <input placeholder="Enter your email" name="mail" required>
    <%
      request.setAttribute("user",user);
      if(request.getRequestDispatcher())  %>
  <label>Password:</label>
  <input placeholder="Enter your Password" name="password" required>
  <button type="submit">Login</button>
  </form>
</div>
</body>
</html>