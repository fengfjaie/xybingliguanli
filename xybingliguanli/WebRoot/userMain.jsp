<%@ page language="java" contentType="text/html; charset=utf-8"  import="com.model.User"  pageEncoding="utf-8"%>
   
<%
	// 职位验证
	User user = (User)session.getAttribute("user");
	if(user==null){
		System.out.println("没有得到qiuzhiId");
		response.sendRedirect("index.jsp");
		return;
	}
	String userXingming = user.getUserXingming();
	int userId = user.getUserId();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>病历管理系统</title>
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>   
</head>
<body style="background-color:#f2f9fd;">
<div class="header bg-main">
  <div class="logo margin-big-left fadein-top">
    <h1><img src="images/y.jpg" class="radius-circle rotate-hover" height="50" alt="" />病历管理系统</h1>
  </div>
  <div class="head-l">
  	<a class="button button-little bg-green"><span class="icon-home"></span><%=userXingming %></a> &nbsp;&nbsp;
  	<a class="button button-little bg-red" href="tuichu.jsp"><span class="icon-power-off"></span> 退出登录</a> 
  </div>
</div>
<div class="leftnav">
  <div class="leftnav-title"><strong><span class="icon-list"></span>菜单列表</strong></div>
  <h2><span class="icon-user"></span>个人信息</h2>
  <ul style="display:block">
    <li><a href="user/user.jsp" target="right"><span class="icon-caret-right"></span>个人信息</a></li>
    <li><a href="usermima.jsp" target="right"><span class="icon-caret-right"></span>修改密码</a></li>
  </ul>
  <h2><span class="icon-user"></span>患者管理</h2>
  <ul>
    <li><a href="user/yonghu.jsp" target="right"><span class="icon-caret-right"></span>患者管理</a></li>
  </ul>
  <h2><span class="icon-user"></span>患者病历</h2>
  <ul>
    <li><a href="user/yxinxi0.jsp" target="right"><span class="icon-caret-right"></span>病历管理</a></li>
    <li><a href="user/yxinxi1.jsp" target="right"><span class="icon-caret-right"></span>已删病历</a></li>
  </ul>
</div>
<script type="text/javascript">
$(function(){
  $(".leftnav h2").click(function(){
	  $(this).next().slideToggle(200);	
	  $(this).toggleClass("on"); 
  })
  $(".leftnav ul li a").click(function(){
	    $("#a_leader_txt").text($(this).text());
  		$(".leftnav ul li a").removeClass("on");
		$(this).addClass("on");
  })
});
</script>
<ul class="bread">
  <li>病历管理系统</li>
</ul>
<div class="admin">
  <iframe scrolling="auto" rameborder="0" src="index2.jsp" name="right" width="100%" height="100%"></iframe>
</div>
<div style="text-align:center;">
<p>病历管理系统</p>
</div>
</body>
</html>