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
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 数据迁移
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i>
    </a>
</nav>
<div class="page-container">
    <form id="paramForm">
        <div class="mt-15">
            <div class="col-4 inline">
                <label><span class="c-red">*</span><strong>迁移组：</strong></label>
                <select name="group">
                    <option value="1">组ID</option>
                    <option value="2">组名</option>
                </select>
            </div>
            <div class="col-3 inline" style="margin-left: 22px;">
                <input type="text" class="input-text" name="groupId" id="groupId" style="width: 203px;"/>
                <div class="auto-query" style="height: auto;display: none;margin-left: 187px;">
                    <ul>
                    </ul>
                </div>
                <input type="text" class="input-text" name="groupName" id="groupName" style="display: none;width: 203px;"/>
                <div class="auto-query" style="height: auto;display: none;margin-left: 187px;">
                    <ul>
                    </ul>
                </div>
            </div>
        </div>
        <div class="mt-30">
            <div class="col-4 inline">
                <label><span class="c-red">*</span><strong>参数：</strong></label>
                <select name="paramType" style="margin-left: 13px;">
                    <option value="1">起止时间</option>
                    <option value="2">业务号</option>
                </select>
            </div>
            <div class="col-3 inline" id="paramTime">
                <input name="startTime" type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'logmax\')||\'%y-%M-%d\'}' })" id="logmin" class="input-text Wdate" style="width:120px;">
                -
                <input name="endTime" type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'logmin\')}',maxDate:'%y-%M-%d' })" id="logmax" class="input-text Wdate" style="width:120px;">
            </div>
            <div class="col-3 inline" style="display: none;" id="paramValue">
                <input type="text" name="paramValue" class="input-text" style="width: 400px;"/>
            </div>
        </div>
    </form>
    <div class="mt-30 ml-50">
        <button class="btn btn-success radius ml-30" type="button" onclick="showHandleContent();"><i class="Hui-iconfont">&#xe665;</i> 查看处理内容</button>
        <button class="btn btn-warning radius ml-20" type="button" onclick="ajaxMigrate();"><i class="Hui-iconfont">&#xe6e6;</i> 迁移</button>
        <button class="btn btn-warning radius ml-20" type="button" onclick="ajaxRestore();"><i class="Hui-iconfont">&#xe6e6;</i> 还原</button>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/plugins/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.form.js"/>"></script>
<script type="text/javascript" src="<c:url value="/admin/js/common.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/layer/2.1/layer.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.showLoading.min.js"/>"></script>
<script type="text/javascript">

    $(document).ready(function(){
        $("[name='paramType']").on("change",function(){
            if($(this).val() == 1){
                $("#paramTime").show();
                $("#paramValue").hide();
            } else {
                $("#paramTime").hide();
                $("#paramValue").show();
            }
        });
        $("[name='group']").on("change",function(){
            if($(this).val() == 1){
                $("#groupId").show();
                $("#groupName").hide();
            } else {
                $("#groupId").hide();
                $("#groupName").show();
            }
        });
        $("#groupId").on("input",function(e){
            ajaxQueryGroupId(e);
        });
        $("#groupId").on("focus",function(e){
            ajaxQueryGroupId(e);
        });
        $("#groupId").blur(function() {
            setTimeout(function() {  //进行延时处理，时间单位为千分之一秒
                $("#groupId").next().hide();
            }, 100)
        });

        $("#groupName").on("input",function(e){
            ajaxQueryGroupName(e);
        });
        $("#groupName").on("focus",function(e){
            ajaxQueryGroupName(e);
        });
        $("#groupName").blur(function() {
            setTimeout(function() {  //进行延时处理，时间单位为千分之一秒
                $("#groupName").next().hide();
            }, 100)
        });
    });

    function ajaxQueryGroupId(e){
        var groupId = e.target.value;
        if(isBlank(groupId)){
            $(e.target).next().hide();
            return ;
        }
        $.ajax({
            url : "${ctx}/migration/ajaxQueryGroupId",
            type : "post",
            data : {groupId:groupId},
            dataType : "json",
            success : function(result){
                if(result.code == 0){
                    var groupIds = result.groupIds;
                    var liHtml = "";
                    if(!!groupIds){
                        for(var i = 0; i< groupIds.length; i++){
                            liHtml += "<li>" + groupIds[i] + "</li>";
                        }
                    }
                    var $ul = $("#groupId").next().find("ul");
                    $ul.html(liHtml);
                    $ul.children("li").each(function(){
                        $(this).click(function(){
                            $("#groupId").val($(this).text());
                        });
                    });
                    $("#groupId").next().show();
                }
            }
        });
    }

    function ajaxQueryGroupName(e){
        var groupName = e.target.value;
        if(isBlank(groupName)){
            $(e.target).next().hide();
            return ;
        }
        $.ajax({
            url : "${ctx}/migration/ajaxQueryGroupName",
            type : "post",
            data : {groupName:groupName},
            dataType : "json",
            success : function(result){
                if(result.code == 0){
                    var groupNames = result.groupNames;
                    var liHtml = "";
                    if(!!groupNames){
                        for(var i = 0; i< groupNames.length; i++){
                            liHtml += "<li>" + groupNames[i] + "</li>";
                        }
                        var $ul = $("#groupName").next().find("ul");
                        $ul.html(liHtml);
                        $ul.children("li").each(function(){
                            $(this).click(function(){
                                $("#groupName").val($(this).text());
                            });
                        });
                    }
                    $("#groupName").next().show();
                }
            }
        });
    }

    function selectThis($this){
        var parentDiv = $($this).parent("div");
        parentDiv.prev().val($($this).html());
        parentDiv.hide();
    }

    function showHandleContent(){

        var group = $("[name='group']").val();
        var url = "";
        if(group == 1){
            var groupId = $("#groupId").val();
            if(groupId == ""){
                layer.alert("请输入组ID",{icon : 6});
                return ;
            }
            url = "handle_content.jsp?groupId=" + groupId;

        } else {
            var groupName = $("#groupName").val();
            if(groupName == ""){
                layer.alert("请输入组名",{icon : 6});
                return ;
            }
            url = "handle_content.jsp?groupName=" + groupName;
        }
        var index = layer.open({
            type: 2,
            title: "处理内容",
            content: encodeURI(url)
        });
        layer.full(index);
    }

    function ajaxMigrate(){
        if(!vaildData()){
            return ;
        }
        $.ajax({
            url : "${ctx}/migration/ajaxMigrate",
            type : "post",
            data : $("#paramForm").serialize(),
            dataType : "json",
            success : function(result){
                if(result.code == 0){
                    layer.alert(result.msg,{icon: 6});
                } else {
                    layer.alert(result.msg,{icon: 6});
                }
            },
            error:function(){
                layer.alert("网络错误",{icon: 6});
            }
        });
    }
    function vaildData(){
        if($("[name='group']").val() == 1 && $("#groupId").val() == ""){
            layer.msg("组ID不能为空");
            return false;
        } else if($("[name='group']").val() == 2 && $("#groupName").val() == ""){
            layer.msg("组名不能为空");
            return false;
        }

        if($("[name='paramType']").val() == 1 && ($("#logmin").val() == "" || $("#logmax").val() == "")){
            layer.msg("起止时间不能为空");
            return false;
        } else if($("[name='paramType']").val() == 2 && $("[name='paramValue']").val() == ""){
            layer.msg("业务号不能为空");
            return false;
        }
        return true;
    }

    function ajaxRestore(){
        if(!vaildData()){
            return ;
        }
        $.ajax({
            url : "${ctx}/migration/ajaxRestore",
            type : "post",
            data : $("#paramForm").serialize(),
            dataType : "json",
            success : function(result){
                if(result.code == 0){
                    layer.alert(result.msg,{icon: 6});
                } else {
                    layer.alert(result.msg,{icon: 6});
                }
            },
            error:function(){
                layer.alert("网络错误",{icon: 6});
            }
        });
    }

</script>
</body>
</html>