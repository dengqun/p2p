<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
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
     		<th>编号</th>
     		<th>名称</th>
     		<th>利率</th>
     		<th>还款期限</th>
     		<th>投资金额</th>
     		<th>状态</th>
     		<th>操作</th>
     	</tr>
     	<c:forEach items="${flist_one}" var="f">
	     	<tr>
	     		<td>${f.fid}</td>
	     		<td>${f.fiName}</td>
	     		<td>${f.rate}</td>
	     		<td>${f.deadline}</td>
	     		<td>${f.money}</td>
	     		<td>${f.fstate}</td>
	     		<td><a href="#">投资</a>&nbsp;<a href="borrow/message?fid=${f.fid}">查看详情</a></td>
	     	</tr>
     	</c:forEach>
     </table>
  </body>
</html>
