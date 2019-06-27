<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>职位信息管理</title>
<link rel="stylesheet" type="text/css"
	href="../jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript"
	src="../jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript"
	src="../jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function datetostr(date, row){
		var JsonDateValue = new Date(date.time);
		var text = JsonDateValue.toLocaleString(); 
		return text;
	}




</script>


</head>
<body style="margin: 5px;">
	<table id="dg" title="日志信息" class="easyui-datagrid" fitColumns="true"
		pagination="true" url="../getRizhis" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="rizhiId" width="50">编号</th>
				<th field="rizhiName" width="50">用户名</th>
				<th field="dengluIp" width="50">IP</th>
				<th field="date" width="50" formatter="datetostr">日期</th>
			</tr>
		</thead>
	</table>
</body>
</html>