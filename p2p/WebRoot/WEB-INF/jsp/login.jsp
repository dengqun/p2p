<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" 
"http://www.w3.org/TR/html4/frameset.dtd">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css" />
	-->
	<!-- 引入jquery和easyui的一些文件 -->
	<script  src ="scripts/easyui/jquery-3.2.1.min.js"></script>
	<script src ="scripts/easyui/jquery.easyui.min.js"></script>
	<!-- <link href="scripts/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />  
	<link href="scripts/easyui/themes/icon.css" rel="stylesheet" type="text/css" />   -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src ="scripts/login.js"></script>
  </head>
  
  <body style="background-color:#028893; background-image:url(images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">
	<form action="login" method = "post">
		<input name = "username" id = "username" type = "text"><br>
		<input name = "password" id = "password" type = "password"><br>
		<input type = "submit" value = "提交">
	</form>
	
	
	<%-- <div id="mainBody">
		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div>
	</div>
	<div class="loginbody">
		<span class="systemlogo" style="margin:0;padding:0;display:block;margin-top: 75px;"></span>
		<div class="loginbox loginbox1">
			<form id="login-form">
				<ul>
					<li><input id="username" name="username" type="text" class="loginuser" placeholder="手机号或邮箱" />
					</li>
					<li><input id="password" name="password" type="password" class="loginpwd" placeholder="密码" />
					</li>
					<li class="yzm">
						<span>
							<input name="code" type="text" value="验证码" id="validCode" class="validate[required]" onclick="JavaScript:this.value=''" /> 
						</span>
						<cite>
							<a href="javascript:refreshCheckCode('login_codeImg');">
								<img id="login_codeImg" width="114" height="46" border="0" src="<%=request.getContextPath()%>/randomCode"> 
							</a> 
						</cite>
					</li>
					<li>
						<input  type="button" class="loginbtn" value="登录" style="line-height:0" onclick="doLogin()">
							
							<label><input name="isSaveUsername" type="checkbox"  />记住用户名
							</label>
							<!-- 
							<label>
								<a href="#" onclick="getUserPwd()">忘记密码？</a> 
							</label>
							 -->
							<div class="err" id="loginErrorTip" style="color:red"></div>
					</li>
				</ul>
			</form>
		</div>
	</div> --%>
	</body>
</html>
