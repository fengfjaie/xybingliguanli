<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="js/pintuer.js"></script>  
<title>病历管理系统</title>
</head>
<script language="javascript">
	function checklogin() {
		if (document.login.account.value == '') {
			alert('请输入帐户');
			document.login.account.focus();
			return false
		}
		if (document.login.password.value == '') {
			alert('请输入密码');
			document.login.password.focus();
			return false
		}
	}
</script>
<body>
<div class="bg"></div>
<div class="container">
    <div class="line bouncein">
        <div class="xs6 xm4 xs3-move xm4-move">
            <div style="height:150px;"></div>
            <div class="media media-y margin-big-bottom">           
            </div>         
            <form class="formname" action="loginUser" name="login" method="post" onSubmit="return checklogin();">
            <div class="panel loginbox">
                <div class="text-center margin-big padding-big-top"><h1>病历管理系统</h1></div>
                <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="text" class="input input-big" name="userName" id="userName" placeholder="登录账号" data-validate="required:请填写账号" />
                            <span class="icon icon-user margin-small"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="password" class="input input-big" name="password" id="password" placeholder="登录密码" data-validate="required:请填写密码" />
                            <span class="icon icon-key margin-small"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-group">
                        <div class="field field-icon-right">
                        <select value="${loginType}"  name="loginType" class="input input-big">
							<option value="admin">管理员</option>
							<option value="user">医生</option>
						</select>
                        </div>
                    </div>
                </div>
                <div style="padding:30px;"><input type="submit" class="button bg-main text-big input-big" value="登录" style="width:100%"></div>
                <div style="padding:10px;"><font color="red">${error }</font></div>
            </div>
            </form>          
        </div>
    </div>
</div>

</body>
</html>