<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>
<title>user密码修改</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="css/colorpicker.css" />
<link rel="stylesheet" href="css/datepicker.css" />
<link rel="stylesheet" href="css/uniform.css" />
<link rel="stylesheet" href="css/select2.css" />
<link rel="stylesheet" href="css/matrix-style2.css" />
<link rel="stylesheet" href="css/matrix-media.css" />
<link rel="stylesheet" href="css/bootstrap-wysihtml5.css" />
<link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800'
	rel='stylesheet' type='text/css'>

<style>
.control-group .controls label {
	display: inline-block;
}
</style>
</head>

	<script type="text/javascript">
		function check() {
			var userPassword = document.form1.userPassword.value;
			var userPassword1 = document.form1.userPassword1.value;
			var userPassword2 = document.form1.userPassword2.value;
			var reg = /^[a-zA-Z0-9_]{6,}$/;

			if (document.form1.userPassword1.value == "") {
				alert("请输入确认密码");
				document.form1.userPassword1.focus();
				return false;
			}
			if (document.form1.userPassword1.value != document.form1.userPassword2.value) {
				alert("两次输入密码不一致");
				document.form1.userPassword2.focus();
				return false;
			}
		}
	</script>
<body>
<div id="content">
  <div id="content-header">
      <h1>密码修改</h1>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-pencil"></i> </span>
            <h5>请您输入原密码和新密码！</h5>
          </div>
          <div class="widget-content nopadding">
            <form id="form-wizard" class="form-horizontal" method="post" name="form1" action="mimaUser" onSubmit="return check()">
              <div id="form-wizard-1" class="step">
                <div class="control-group">
                  <label class="control-label">原密码</label>
                  <div class="controls">
                    <input id="userPassword" type="password" name="userPassword" />
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">新密码</label>
                  <div class="controls">
                    <input id="userPassword1" type="password" name="userPassword1" />
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">再一遍</label>
                  <div class="controls">
                    <input id="userPassword2" type="password" name="userPassword2" />
                  </div>
                </div>
              </div>
              <div class="form-actions">
                <input id="back" class="btn btn-primary" type="reset" value="重置" />
                <input id="next" class="btn btn-primary" type="submit" value="提交" />
                <div id="status"></div>
              </div>
              <div id="submitted"></div>
              <li><font color="red">${error }</font></li>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>