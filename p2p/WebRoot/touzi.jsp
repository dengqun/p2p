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
  				<td>真实姓名:${user.name}</td>
  				<td>身份证号:${user.idNum}</td>
  				<td>银行卡号:${user.bankNo}</td>
  				<td>账户余额:${user.balance}</td>
  				<td>手机号:${user.phone}</td>
  				<td>邮箱:${user.email}</td>
  			</tr>
  		</table>
  		<br/>
     <table>
     	<tr>
     		<th>编号</th>
     		<th>借款总金额</th>
     		<th>投资金额</th>
     		<th>投资占比</th>
     		<th>预期收益</th>
     		<th>借款期限</th>
     		<th>是否分期</th>
     		<th>投资状态</th>
     	</tr>
     	<c:forEach items="${flist_two}" var="f">
	     	<tr>
		     	<th>${f.fid}</th>
	     		<th>${f.money}</th>
	     		<th>${f.investMoney}</th>
	     		<th>${f.scale}</th>
	     		<th>${f.ieRevenue}</th>
	     		<th>${f.deadline}</th>
	     		<th>否</th>
	     		<th>${f.fstate}</th>
	     	</tr>
     	</c:forEach>
     </table>
     <a href="invest/nonentity">未投资</a>
  </body>
</html>
