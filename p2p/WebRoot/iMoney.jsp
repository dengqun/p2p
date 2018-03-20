<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'iMoney.jsp' starting page</title>
    
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
    <form action="invest/queren" method="post">
    	债券编号：${fentity.fid}<br/>
    	债券名称：${fentity.fiName}<br/>
    	债券总份额：${fentity.money}<br/>
    	回报利率：${fentity.rate}<br/>
    	起息日：${fentity.startDate}<br/>
    	借款期限：${fentity.deadline}<br/>
    	<table border="1">
    		<tr>
    			<th>借款编号</th>
    			<th>借款人</th>
    			<th>信用评级</th>
    			<th>借款用途</th>
    			<th>借款金额</th>
    		</tr>
    		<c:forEach items="${blist}" var="b">
    			<tr>
    				<td>${b.borId}</td>
    				<td>${b.name}</td>
    				<td>${b.credit}</td>
    				<td>${b.borUse}</td>
    				<td>${b.borMoney}</td>
    			</tr>
    		</c:forEach>
    	</table>
    	<p>请输入您的投资金额:<input type="text" name="iMoney"/></p><br/>
    	<input type="submit" value="确定投资"/>
    </form>
  </body>
</html>
