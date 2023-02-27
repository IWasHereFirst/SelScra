<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Hello World!</title>
</head>
<body>
<form action="@{/logout}" method="post">
    <input type="submit" value="Sign Out"/>
</form>
</body>
</html>