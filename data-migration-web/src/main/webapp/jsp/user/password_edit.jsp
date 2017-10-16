<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/jsp/common/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改密码</title>
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
        <div class="row cl text-c">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>请输入原密码：</strong></label>
            <div class="formControls col-xs-3" style="width: 20%;">
                <input type="password" class="input-text radius" value="" name="passwordOld">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>请输入新密码：</strong></label>
            <div class="formControls col-xs-3" style="width: 20%;">
                <input type="password" class="input-text radius" value="" name="passwordNew">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>请确认新密码：</strong></label>
            <div class="formControls col-xs-3" style="width: 20%;">
                <input type="password" class="input-text radius" value="" name="passwordNewBak">
            </div>
        </div>
        <div class="row cl" style="margin-top: 30px;">
            <div class="col-xs-8 col-xs-offset-1">
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
        $("[name='passwordNewBak']").blur(function(){
            if($("[name='passwordNewBak']").val() == ""){
                layer.tips('请输入确认新密码', '[name="passwordNewBak"]');
                return ;
            }
            if($("[name='passwordNewBak']").val() != $("[name='passwordNew']").val()){
                layer.tips('与用户新密码不一致', '[name="passwordNewBak"]');
            }
        });
    }

    function ajaxAddTableInfo(){
        //验证组数据
        if(!validForm()){
            return ;
        }
        $.ajax({
            url:"${ctx}/user/updatePassword",
            type:"post",
            data:$("#form").serialize(),
            dataType:"json",
            success:function(result){
                if(result.code == 0){
                    top.location = "${ctx}/user/logout.do";
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
        if(isBlank($("[name='passwordOld']").val())){
            layer.alert('请输入原密码', {icon: 6});
            return false;
        }
        if(isBlank($("[name='passwordNew']").val())){
            layer.alert('请输入新密码', {icon: 6});
            return false;
        }
        if(isBlank($("[name='passwordNewBak']").val())){
            layer.alert('请输入确认新密码', {icon: 6});
            return false;
        }
        if($("[name='passwordNewBak']").val() != $("[name='passwordNew']").val()){
            layer.alert('两次输入密码不一致', {icon: 6});
            return false;
        }
        return true;
    }

</script>
</body>
</html>