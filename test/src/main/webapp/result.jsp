<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>查询结果界面</title>
</head>
<body>
	<table border="1">
		<tr>
			<td>书名</td>
			<td>价格</td>
		</tr>
		<%
		    Map<String, Integer> result = (Map<String, Integer>) request.getAttribute("result");
		    for (Map.Entry<String, Integer> entry : result.entrySet()) {
		%>
		<tr>
			<td><%=entry.getKey()%></td>
			<td><%=entry.getValue()%></td>
		</tr>
		<%
		    }
		%>
	</table>
</body>
</html>