<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>list</title>
</head>
<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
<script type="text/javascript">
	function toUpdate(id){
		$.ajax({
			url:"<%=request.getContextPath()%>/toUpdate1.action",
			data:{id:id},
			dataType:"json",
			type:"post",
			success:function(obj){
			//alert(obj.did)
				$("input[id='id']").val(obj.id);
				$("input[name='name']").val(obj.name);
			//	$("input[name='dname1']").val(obj.dname);
			$("option[value='"+obj.did+"']").attr("selected","selected");
				$("div[id='Div']").attr("style","display:block");
			}
		}) 
	}
	
	
	function fy(id){
		location.href="<%=request.getContextPath()%>/list.action?currentPage="+id;
	}
	
	function search(){
	var id2=$("#searchName")[0].value;
	location.href="<%=request.getContextPath()%>/list.action?name="+id2;
	}
	
	function del(id){
		var msg = confirm("确定删除吗");
		if(msg){
			$.ajax({
			url:"<%=request.getContextPath()%>/delete",
			data:{id:id},
			dataType:"text",
			type:"post",
			success:function(obj){
				alert(obj);
				$("#tr_"+id).remove();
			}
		}) 
			
		}else{
			alert("删除失败！");
		} 
	}
	
	function add(){
	$("#addDiv").attr("style","display:block")

	}
</script>
<body>
	<input type="text" id="searchName">
	<button onclick="search()">搜索</button>
	<table border="1" >
		<tr>
			<td>编号</td>
			<td>姓名</td>
			<td>科室</td>
			<td>操作 <button onclick="add()">增加</button></td>
		</tr>
				
			<c:forEach items="${userList }" var="u">
			<tr id="tr_${u.id}">
				<td>${u.id }</td>
				<td>${u.name }</td>
				</td>
			</tr>
			</c:forEach>
		
	</table>
				<button onclick="fy(1)">首页</button>
				<button onclick="fy(${pageUtil.prevPage})">上一页</button>
				<button onclick="fy(${pageUtil.nextPage})">下一页</button>
				<button onclick="fy(${pageUtil.lastPage})">尾页</button>
				
	
	
</body>
</html>