<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/jsp/common/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>编辑用户</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Bookmark" href="/favicon.ico" >
    <link rel="Shortcut Icon" href="/favicon.ico" />
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/h-ui/css/H-ui.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/h-ui/css/H-ui.admin.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/plugins/Hui-iconfont/1.0.7/iconfont.css"/>" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="page-container">
    <form class="form form-horizontal" id="form">
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>用户名：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="text" readonly="readonly" value="${dmuserinfo.usercode}" name="usercode" style="border: none;">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>用户昵称：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="text" class="input-text radius" value="${dmuserinfo.username}" name="username">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>用户密码：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="password" class="input-text radius" value="${dmuserinfo.password}" name="password">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>再次确认密码：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="password" class="input-text radius" value="${dmuserinfo.password}" name="passwordBak">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><strong>联系电话：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="text" class="input-text radius" value="${dmuserinfo.phonenumber}" name="phonenumber">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><strong>邮箱：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="text" class="input-text radius" value="${dmuserinfo.email}" name="email">
                </div>
            </div>
        </div>
        <hr class="mt-10">
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2"><strong>是否有效：</strong></label>
                <div class="formControls col-xs-3">
                    <select name="isvalid" class="select-default radius" style="height: 30px;">
                        <option value="1" <c:if test="${dmuserinfo.isvaild == 1}">selected="selected"</c:if>>是</option>
                        <option value="0" <c:if test="${dmuserinfo.isvaild == 0}">selected="selected"</c:if>>否</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row cl" style="margin-top: 30px;">
            <div class="col-xs-8 col-xs-offset-4">
                <button id="btn_save" onClick="ajaxAddTableInfo();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
                <button class="btn btn-secondary radius" type="reset"><i class="Hui-iconfont">&#xe632;</i> 重置</button>
                <button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;返回&nbsp;&nbsp;</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="<c:url value="/plugins/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/layer/2.1/layer.js"/>"></script>
<script type="text/javascript" src="<c:url value="/h-ui/js/H-ui.admin.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.showLoading.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/datasource/group.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/common.js"/>"></script>
<script type="text/javascript">

    window.onload = function(){
        $("[name='passwordBak']").blur(function(){
            if($("[name='passwordBak']").val() == ""){
                layer.tips('请输入再次确认密码', '[name="passwordBak"]');
                return ;
            }
            if($("[name='passwordBak']").val() != $("[name='password']").val()){
                layer.tips('与用户密码不一致', '[name="passwordBak"]');
            }
        });
    }

    function ajaxAddTableInfo(){
        //验证组数据
        if(!validForm()){
            return ;
        }
        $.ajax({
            url:"${ctx}/user/updateUserInfo",
            type:"post",
            data:$("#form").serialize(),
            dataType:"json",
            success:function(result){
                if(result.code == 0){
                    layer.alert(result.msg,{icon:1},function(){
                        parent.location.reload();
                        layer_close();
                    });
                } else {
                    layer.alert(result.msg, {icon: 6});
                }
            },
            error:function(){
                layer.alert('网络错误', {icon: 6});
            }
        });
    }

    function validForm(){
        if(isBlank($("[name='usercode']").val())){
            layer.alert('用户名不能为空', {icon: 6});
            return false;
        }
        if(isBlank($("[name='username']").val())){
            layer.alert('用户昵称不能为空', {icon: 6});
            return false;
        }
        if(isBlank($("[name='password']").val())){
            layer.alert('密码不能为空', {icon: 6});
            return false;
        }
        if(isBlank($("[name='passwordBak']").val())){
            layer.alert('请输入再次确认密码', {icon: 6});
            return false;
        }
        if($("[name='passwordBak']").val() != $("[name='password']").val()){
            layer.alert('两次输入密码不一致', {icon: 6});
            return false;
        }
        if(!isBlank($("[name='email']").val())){
            if(!isEmail($("[name='email']").val())){
                layer.alert('请输入正确的邮箱地址', {icon: 6});
                return false;
            }
        }
        return true;
    }

</script>
</body>
</html>