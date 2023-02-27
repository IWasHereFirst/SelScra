<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Spring Security Example </title>
</head>
<body>
<form action="login" method="post">
  <div><label> User Name : <input type="text" name="username"/> </label></div>
  <div><label> Password: <input type="password" name="password"/> </label></div>
  <div><input type="submit" value="Sign In"/></div>
</form>
<script>
 var d = new Date();
    document.write(d.toISOString() + "<br>"); // Display the ISO standardized date string
      document.write(d.toTimeString() + "<br>");
</script>
<a href="registration">registration</a>
</body>
</html>