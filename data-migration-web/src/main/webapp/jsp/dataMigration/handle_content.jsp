<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/jsp/common/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>数据迁移</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Bookmark" href="/favicon.ico" >
    <link rel="Shortcut Icon" href="/favicon.ico" />
    <link href="<c:url value="/h-ui/css/H-ui.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/h-ui/css/H-ui.admin.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/plugins/Hui-iconfont/1.0.7/iconfont.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/css/showLoading.css"/>" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="page-container">
    <div class="info ml-40">
        <div class="btn-group">
            <button class="btn btn-primary radius" onclick="showProcedureDIV(this,1);">执行处理</button>
            <button class="btn btn-primary radius" onclick="showProcedureDIV(this,2);">还原处理</button>
        </div>
        <div id="execute" style="display: none;">
            <div class="mt-15">
                <span class="btn btn-primary">创建/更新目标表</span>
            </div>
            <div name="handleContent" style="width: 50%;height: 400px;border:1px solid;">
            </div>
            <div class="mt-15">
                <span class="btn btn-primary">创建迁移存储过程</span>
            </div>
            <div name="handleProcedure" style="width: 50%;height: 400px;border:1px solid;">
            </div>
        </div>
        <div id="restore" style="display: none;">
            <div class="mt-15">
                <span class="btn btn-primary">创建还原存储过程</span>
            </div>
            <div name="restoreProcedure" style="width: 50%;height: 400px;border:1px solid;">
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/h-ui/js/H-ui.admin.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.form.js"/>"></script>
<script type="text/javascript" src="<c:url value="/admin/js/common.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/layer/2.1/layer.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.showLoading.min.js"/>"></script>
<script type="text/javascript">

    $(function(){
        $.ajax({
            url:"${ctx}/migration/ajaxQueryTables?groupId=${param.groupId}&groupName=${param.groupName}",
            type:"get",
            dataType:"json",
            success:function(result){
                if(result.code == 0){
                    var dmgrouptables = result.dmgrouptables;
                    if(!!dmgrouptables){
                        var handleContent = "";
                        var handleProcedure = "";
                        var restoreProcedure = "";
                        for(var i = 0; i < dmgrouptables.length; i++){
                            var dmgrouptable = dmgrouptables[i];
                            if(dmgrouptable.handletargettable != null){
                                handleContent += dmgrouptable.handletargettable + "<br>";
                            }
                            handleProcedure += dmgrouptable.handleprocedure + "<br>";
                            restoreProcedure += dmgrouptable.restoreprocedure + "<br>";
                        }
                        $("[name='handleContent']").html(handleContent);
                        $("[name='handleProcedure']").html(handleProcedure);
                        $("[name='restoreProcedure']").html(restoreProcedure);
                    }
                } else {
                    layer.alert(result.msg,{icon:1},function(){
                        layer_close();
                    });
                }
            },
            error:function(){
                layer.alert('网络错误', {icon: 6});
            }
        });
    });

    function showProcedureDIV($this,type){
        if(type == 1){
            $("#execute").show();
            $("#restore").hide();
        } else if(type == 2){
            $("#restore").show();
            $("#execute").hide();
        }
        $($this).parent().children().eq(type-1).addClass("active");
        $($this).parent().children().eq(3-type-1).removeClass("active");
    }

</script>
</body>
</html>