<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


</head>

<body>
	
	<a href="/p2p/api/BorMain?userid=2&token=6ffeb2ce0ba7414ea5dbad4e752e9e44">登陆借款主页</a>
	<a href="/p2p/Apply.jsp">apply</a>
	<a href="/p2p/rePay?uid=1&borId=2" >还款</a>
	<a href="/p2p/Withdraw?uid=1&money=100&date=2001-10-10">提现</a>

	<a href="/p2p/Recharge?uid=1&money=100&date=2001-10-10">充值</a>
	
</body>
</html>
