<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Apply.jsp' starting page</title>
    
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
  <form action="/p2p/applyFor">
 uid: <input type="text" id="uid" name="uid" >
   借款金额	:<input type="text" id="borMoney" name="borMoney" /><br/>
   年化利率	:<input type="text" id="interest" name="interest" /><br/>
 抵押合同编号	:<input type="text" id="mortgageNo" name="mortgageNo" /><br/>
   款型用途	:<input type="text" id="borUse" name="borUse" /><br/>
   申請日期 <input type="text" id="borDate" name="borDate">

还款日期	:<input type="text" id="borDeadline" name="borDeadline" /><br/>
 
是否分期 	:<input type="text" id="instalment" name="instalment"><br/>
应还金额     js计算
<input type="submit" value="提交申请">
</form>

  </body>
</html>
