<%@ page language="java" contentType="text/html; charset=utf-8"  import="com.model.User"  pageEncoding="utf-8"%>
   
<%
	// 职位验证
	User user = (User)session.getAttribute("user");
	if(user==null){
		System.out.println("没有得到zhaopinId");
		response.sendRedirect("index.jsp");
		return;
	}
	String userXingming = user.getUserXingming();
	int userId = user.getUserId();
	String userPassword = user.getUserPassword();
	System.out.println("userPassword:"+userPassword);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>病历信息管理</title>
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="../jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
var url;
var userId = <%=userId%>;
	function searchYxinxi(){
		$('#dg').datagrid('load',{
			yxinxiName:$('#s_yxinxiName').val(),
			yxtypeId:$('#s_yxtypeId').combobox("getValue"),
			yonghuId:$('#s_yonghuId').combobox("getValue"),
			sdate:$('#s_sdate').datebox("getValue"),
			edate:$('#s_edate').datebox("getValue")
		});
	}
	
	function saveYxinxi(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
			
				var s=result;
				var result = eval('(' + result + ')');
			
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg);
					return;
				}else{
					$.messager.alert("系统提示","保存成功");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}
			}
		});
	}
	
	function openYxinxiAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加病历信息");
		url="../addYxinxi";
	}
	
	function resetValue(){
	}
	
	function deleteYxinxi(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].yxinxiId);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("../deleteYxinxi",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示','<font color=red>'+selectedRows[result.errorIndex].yxinxiName+'</font>'+result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	function closeYxinxiDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function openYxinxiModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑病历信息");
		$("#fm").form("load",row);
		url="../addYxinxi?yxinxiId="+row.yxinxiId;
	}
	
	function formatChakan(shujuImg, row) {
		if(shujuImg){
			return '<a target="_blank" style="color:red;" href="../' + shujuImg + '">查看' + '</a>'; 
		}else {
			return "未上传";
		}
	}
	
	function formatXiazai(shujuImgName, row) {
		if(shujuImgName){
			return '<a target="_blank" style="color:red;" href="../downFile?filename=' + shujuImgName + '">下载' + '</a>'; 
		}else {
			return "未上传";
		}
	}
	
	function datetostr(dateTime, row) {
		if(dateTime){
			var JsonDateValue = new Date(dateTime.time);
			var text = JsonDateValue.toLocaleString();
			return text;
		}else{
			return "未填写";
		}
	}
	
	function doPrint() {      
        bdhtml=window.document.body.innerHTML;      
        sprnstr="<!--startprint-->";      
        eprnstr="<!--endprint-->";      
        prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);      
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));      
        window.document.body.innerHTML=prnhtml;   
        window.print();      
	}
	
	function shangchuanYxinxi(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#shangchuan").dialog("open").dialog("setTitle","上传病历信息");
		$("#shchfm").form("load",row);
		shchurl="../shangchuanYxinxi?yxinxiId="+row.yxinxiId;
	}
	
	function closeShangchuanYxinxi(){
		$("#shangchuan").dialog("close");
		resetValue();
	}
	
	function saveShangchuanYxinxi(){
		$("#shchfm").form("submit",{
			url:shchurl,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
			
				var s=result;
				var result = eval('(' + result + ')');
			
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg);
					return;
				}else{
					$.messager.alert("系统提示","保存成功");
					resetValue();
					$("#shangchuan").dialog("close");
					$("#dg").datagrid("reload");
				}
			}
		});
	}
	
	function formatType1(shujuType1, row) {  
		if(shujuType1==0){
			return "零";
		}
		if(shujuType1==1){
			return "一";
		}
	}
	
	function formatType2(shujuType2, row) {  
		if(shujuType2==0){
			return "0";
		}
		if(shujuType2==1){
			return "1";
		}
	}
	
	function datetostr(date, row) {
		if(date){
			var date = new Date(date.time);
        	var y=date.getFullYear();
        	var m=date.getMonth()+1;
        	var d=date.getDate();
        	var h=date.getHours();
        	var m1=date.getMinutes();
        	var s=date.getSeconds();
        	m = m<10?("0"+m):m;
        	d = d<10?("0"+d):d;
        	return y+"-"+m+"-"+d;
			var text = JsonDateValue.toLocaleString();
			return text;
		}else{
			return "未填写";
		}
	}
	
	function shenheYxinxi(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要删除的数据！");
			return;
		}
		var row=selectedRows[0];
		url="../addYxinxi?yxinxiId="+row.yxinxiId;
		$.messager.confirm("系统提示","您确认要删除吗？",function(r){
			if(r){
				$.post(url,{yxinxiType:1},function(result){
					if(result.errorMsg){
						$.messager.alert("系统提示",result.errorMsg);
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert("系统提示","您已成功删除！");
						$("#dg").datagrid("reload");
					}
				},"json");
			}
		});
	}
</script>
</head>
<body style="margin: 5px;">
<!--startprint-->
	<table id="dg" title="病历信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" url="../getYxinxis?yxinxiType=0&yhbumenId=<%=userId %>" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="yxinxiId" width="10">编号</th>
				<th field="yxinxiName" width="40">名称</th>
				<th field="yxinxiMark" width="40">病史</th>
				<th field="yxinxiMark1" width="40">病状</th>
				<th field="yxinxiMark2" width="100">诊断</th>
				<th field="yxtypeId" width="20" hidden="true">类型ID</th>
				<th field="yxtypeName" width="20">类型</th>
				<th field="yonghuId" width="20" hidden="true">患者ID</th>
				<th field="yonghuName" width="20">患者</th>
				<th field="yxinxiImgName" width="20" formatter="formatXiazai">病历</th>
				<th field="yxinxiDate" width="20" formatter="datetostr">时间</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<a href="javascript:openYxinxiAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openYxinxiModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:shenheYxinxi()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:shangchuanYxinxi()" class="easyui-linkbutton" iconCls="icon-add" plain="true">上传病历</a>
		</div>
		<div>
		&nbsp;名称：&nbsp;<input type="text" name="s_yxinxiName" id="s_yxinxiName" size="10"/>
		&nbsp;时间：&nbsp;<input class="easyui-datebox" name="s_sdate" id="s_sdate" editable="false" size="10"/>->
		<input class="easyui-datebox" name="s_edate" id="s_edate" editable="false" size="10"/>
		&nbsp;类型：&nbsp;<input class="easyui-combobox" id="s_yxtypeId" name="s_yxtypeId"  data-options="panelHeight:'auto',editable:false,valueField:'yxtypeId',textField:'yxtypeName',url:'../yxtypeComboList'"/>
		&nbsp;患者：&nbsp;<input class="easyui-combobox" id="s_yonghuId" name="s_yonghuId"  data-options="panelHeight:'auto',editable:false,valueField:'yonghuId',textField:'yonghuName',url:'../yonghuComboList'"/>
		<a href="javascript:searchYxinxi()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>
<!--endprint-->
	
	<div id="dlg" class="easyui-dialog" style="width: 500px;height: 380px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
					<td>患者：</td>
					<td><input class="easyui-combobox" id="yonghuId" name="yonghuId"  data-options="panelHeight:'auto',editable:false,valueField:'yonghuId',textField:'yonghuName',url:'../yonghuComboList?yonghuType1=0&yhbumenId=<%=userId %>'"/></td>
					<td>类型：</td>
					<td><input class="easyui-combobox" id="yxtypeId" name="yxtypeId"  data-options="panelHeight:'auto',editable:false,valueField:'yxtypeId',textField:'yxtypeName',url:'../yxtypeComboList'"/></td>
				</tr>
				<tr>
					<td>名称：</td>
					<td><input type="text" name="yxinxiName" id="yxinxiName" class="easyui-validatebox" required="true"/></td>
					<td>病史：</td>
					<td><input type="text" name="yxinxiMark" id="yxinxiMark" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td valign="top">病状：</td>
					<td colspan="4"><textarea rows="7" cols="50" name="yxinxiMark1" id="yxinxiMark1"></textarea></td>
				</tr>
				<tr>
					<td valign="top">诊断：</td>
					<td colspan="4"><textarea rows="7" cols="50" name="yxinxiMark2" id="yxinxiMark2"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveYxinxi()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeYxinxiDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
<!--上传-->	
	<div id="shangchuan" class="easyui-dialog" style="width: 320px;height: 140px;padding: 10px 20px"
		closed="true" buttons="#shangchuan-buttons">
		<form id="shchfm" method="post" enctype="multipart/form-data">
			<table cellspacing="5px;">
				<tr>
					<td><input type="file" name="uploadFile" id="uploadFile" class="easyui-validatebox" required="true"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="shangchuan-buttons">
		<a href="javascript:saveShangchuanYxinxi()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeShangchuanYxinxi()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>