<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>SelScra Registration</title>
</head>
<body>

<div>Create new user</div>
<form action="RegistrationServlet" method="post">
    <div><label> Email: <input type="text" name="email"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <input type="hidden" id="timestamp" name="timestamp"/>
    <script>
        var d = new Date();
        date = d.toISOString().replace("T", " ").substring(0,11)
        time = d.toTimeString().substring(0, 15).split(" ")
        time[1] = time[1].substr(3)
        var timestamp = date + time[0] + time[1]
        document.querySelector("#timestamp").value=timestamp
        document.write(timestamp)
    </script>
    <div><input type="submit" value="Sign In"/></div>
</form>
<div th:if="${CREATED_USER}">
    <p>${CREATED_USER.id}</p>
    <p>${CREATED_USER.email}</p>
    <p>${CREATED_USER.password}</p>
    <p>${CREATED_USER.last_logged}</p>
</div>
</body>
</html>