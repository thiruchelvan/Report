<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>

<form method="post" action="j_security_check">
			<div class="field">
				<div>
					<label for="j_username">User name:</label>
				</div>
				<div>
					<input type="text" size="25" name="j_username">
				</div>
			</div>
			<div class="field">
				<div>
					<label for="j_password">Password:</label>
				</div>
				<div>
					<input type="password" size="25" name="j_password">
				</div>
			</div>
			<div class="buttonArea">
				<input type="submit" name="login" value="Login">
			</div>
		</form>

</body>
</html>