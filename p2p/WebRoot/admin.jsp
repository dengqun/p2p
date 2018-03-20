<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'admin.jsp' starting page</title>
    
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
    <table>
		<tr>
			<td>编号</td>
			<td>申请日期</td>
			<td>借款金额</td>
			<td>借款利率</td>
			<td>借款分类</td>
			<td>借款状态</td>
			<td>等筹进度</td>
			<td>起息日</td>
			<td>借款期限</td>
			<td>是否分期</td>
			<td>质押合同编号</td>
		</tr>
		<c:forEach items="${list}" var="bor">
			<tr>
				<td>${bor.borId}</td>
				<td>${bor.startDate}</td>
				<td>${bor.borMoney}</td>
				<td>${bor.interest}</td>
				<td>${bor.borUse}</td>
				<td>${bor.borState}</td>
				<td>等筹进度</td>
				<td>起息日</td>
				<td>${bor.borDeadline}</td>
				<td>${bor.instalment}</td>
				<td>${bor.mortgageNo}</td>
			</tr>
		</c:forEach>
	</table>
  </body>
</html>
