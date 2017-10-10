<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/common/taglib.jspf" %>
<!DOCTYPE html>
<html>
<head>
    <title>数据转储-后台管理-登录</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap styles-->
    <link rel="stylesheet" href="<c:url value="/assets/css/bootstrap.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/login.css"/>">
    <link rel="stylesheet" href="<c:url value="/plugins/Font-Awesome/css/font-awesome.min.css"/>">
    <script src="../plugins/jquery/jquery.js"></script>

</head>
<body class="login-content">
    <form id="form" method="post" action="login.do">
        <div class="top-content">
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1><strong class="systitle">数据转储系统</strong> 登录</h1>
                            <div class="description">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-4 form-box">
                            <div class="form-top">
                                <div class="form-top-left">
                                    <h3>登录系统 <small>(测试开发中)</small> </h3>
                                    <p>请输入用户名和密码:</p>
                                </div>
                                <div class="form-top-right">
                                    <i class="fa fa-key"></i>
                                </div>
                            </div>
                            <div class="form-bottom">
                                <div class="form-group ">
                                    <label class="sr-only ">用户名</label>
                                    <input type="text" name="usercode" maxLength="40" required="required" value="" placeholder="用户名..."
                                           class="form-username form-control">
                                </div>
                                <div class="form-group">
                                    <label class="sr-only ">密码</label>
                                    <input type="password" name="password" value="" placeholder="密码..." required="required"
                                           class="form-password form-control">
                                </div>
                                <div class="row">
                                    <div class="col-md-8" style="margin-left: 60px;">
                                        <button type="submit" class="btn">登录</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
<P>
    <c:out value="${errorMsg}"/>
</P>
<script type="text/javascript">

    $(document).ready(function(){
        $(".btn").on("click",function(){
            ajaxLogin();
        });
    })

    function ajaxLogin(){
        $(".login-form").submit();
    }
</script>
</body>
</html>




 