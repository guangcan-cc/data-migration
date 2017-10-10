<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/jsp/common/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>迁移配置</title>
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
    <link href="<c:url value="/css/showLoading.css"/>" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="page-container">
    <div style="padding: 5px;">
        <ul id="Huifold1" class="Huifold">
            <li class="item">
                <h4>表字段信息<b>+</b></h4>
                <div class="info">
                    <table class="table table-border table-bg table-bordered radius">
                        <thead class="text-c">
                        <tr>
                            <th width="80%">原表信息</th>
                            <th width="20%">展开</th>
                        </tr>
                        </thead>
                        <tbody class="text-c" id="table_info_tbody">
                        </tbody>
                    </table>
                </div>
            </li>
            <li class="item">
                <h4>表关联信息<b>+</b></h4>
                <div class="info">
                    <table class="table table-border table-bg table-bordered radius">
                        <thead class="text-c">
                        <tr>
                            <th>级别</th>
                            <th>用户名</th>
                            <th>表名</th>
                            <th>主键名</th>
                            <th>主键</th>
                            <th>外键名</th>
                            <th>外键</th>
                            <th>备注</th>
                        </tr>
                        </thead>
                        <tbody class="text-c" id="ref_info_tbody">
                        </tbody>
                    </table>
                </div>
            </li>
            <li class="item">
                <h4>表处理内容<b>+</b></h4>
                <div class="info">
                    <div class="btn-group">
                        <button class="btn btn-primary radius" onclick="showProcedureDIV(this,1);">执行处理</button>
                        <button class="btn btn-primary radius" onclick="showProcedureDIV(this,2);">还原处理</button>
                    </div>
                    <div id="execute" style="display: none;">
                        <div class="mt-15">
                            <span class="btn btn-primary">创建/更新目标表</span>
                            &nbsp;
                            <button class="btn btn-default radius" style="height: 29px;" onclick="editProcedure(this);">编辑</button>
                            <button class="btn btn-default radius" style="height: 29px;" onclick="ajaxEditHandle(this);">保存</button>
                            <button class="btn btn-default radius" style="height: 29px;" onclick="cancelEdit(this);">取消</button>
                        </div>
                        <div>
                            <textarea name="handleContent" class="textarea radius" style="width: 50%;"></textarea>
                        </div>
                        <div class="mt-15">
                            <span class="btn btn-primary">创建迁移存储过程</span>
                            &nbsp;
                            <button class="btn btn-default radius" style="height: 29px;" onclick="editProcedure(this);">编辑</button>
                            <button class="btn btn-default radius" style="height: 29px;" onclick="ajaxEditMigration(this);">保存</button>
                            <button class="btn btn-default radius" style="height: 29px;" onclick="cancelEdit(this);">取消</button>
                        </div>
                        <div>
                            <textarea name="handleProcedure" class="textarea radius" style="width: 50%;height: 400px;"></textarea>
                        </div>
                    </div>
                    <div id="restore" style="display: none;">
                        <div class="mt-15">
                            <span class="btn btn-primary">创建还原存储过程</span>
                            &nbsp;
                            <button class="btn btn-default radius" style="height: 29px;" onclick="editProcedure(this);">编辑</button>
                            <button class="btn btn-default radius" style="height: 29px;" onclick="ajaxEditRestore(this);">保存</button>
                            <button class="btn btn-default radius" style="height: 29px;" onclick="cancelEdit(this);">取消</button>
                        </div>
                        <div>
                            <textarea name="restoreProcedure" class="textarea radius" style="width: 50%;height: 400px;"></textarea>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/plugins/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/layer/2.1/layer.js"/>"></script>
<script type="text/javascript" src="<c:url value="/h-ui/js/H-ui.js"/>"></script>
<script type="text/javascript" src="<c:url value="/h-ui/js/H-ui.admin.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.showLoading.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/common.js"/>"></script>
<script type="text/javascript">
    window.onload = function(){
        $(".page-container").showLoading();
        $.ajax({
            url:"${ctx}/migration/queryTableDetail?id=${param.id}",
            type:"post",
            dataType:"json",
            success:function(result){
                $(".page-container").hideLoading();
                if(result.code == 0){
                    var tableInfoVOList = result.tableInfoVOList;
                    var trHtml;
                    if(!!tableInfoVOList){
                        trHtml = "";
                        for(var i = 0; i < tableInfoVOList.length; i++){
                            trHtml += "<tr>" +
                                        "<td>" + tableInfoVOList[i].tableName + "</td>" +
                                        "<td><button class='btn btn-primary radius' onclick='showNext(this);'>+</button></td>" +
                                      "</tr>";
                            var fields = tableInfoVOList[i].fields;
                            if(!!fields){
                                trHtml += "<tr style='display: none;'>" +
                                                "<td colspan='2'>" +
                                                    "<table class='table table-border table-bordered radius' style='background-color: #5eb95e;'>" +
                                                        "<tr>" +
                                                            "<th style='width: 50%;'>字段</th>" +
                                                            "<th style='width: 50%;'>描述</th>" +
                                                        "</tr>";
                                for(var k = 0; k < fields.length; k++){
                                    trHtml +=            "<tr>" +
                                                            "<td>" + fields[k].field + "</td>" +
                                                            "<td>" + fields[k].desc + "</td>" +
                                                          "</tr>";
                                }
                                trHtml +=           "</table>" +
                                                "</td>" +
                                            "</tr>";
                            }
                        }
                        $("#table_info_tbody").html(trHtml);
                    }
                    var relatedInfoVOList = result.relatedInfoVOList;
                    if(!!relatedInfoVOList){
                        trHtml = "";
                        for(var i = 0; i < relatedInfoVOList.length; i++){
                            var vo = relatedInfoVOList[i];
                            var foreignKeyNames = vo.foreignKeyNames;
                            var foreignKeyNamesStr = "-";
                            if(!!foreignKeyNames){
                                foreignKeyNamesStr = "";
                                for(var k = 0; k < foreignKeyNames.length; k++){
                                    foreignKeyNamesStr += foreignKeyNames[k];
                                }
                            }
                            var foreignKeys = vo.foreignKeys;
                            var foreignKeysStr = "-";
                            if(!!foreignKeyNames){
                                foreignKeysStr = "";
                                for(var k = 0; k < foreignKeys.length; k++){
                                    foreignKeysStr += foreignKeys[k];
                                }
                            }
                            trHtml += "<tr>" +
                                            "<td>" + (i+1) + "</td>" +
                                            "<td>" + vo.username + "</td>" +
                                            "<td>" + vo.tableName + "</td>" +
                                            "<td>" + (vo.primaryKeyName == null ? "-" : vo.primaryKeyName) + "</td>" +
                                            "<td>" + (vo.primaryKey == null ? "-" : vo.primaryKey) + "</td>" +
                                            "<td>" + foreignKeyNamesStr + "</td>" +
                                            "<td>" + foreignKeysStr + "</td>" +
                                            "<td>-</td>" +
                                        "</tr>";
                        }
                        $("#ref_info_tbody").html(trHtml);
                    }
                    var dmgrouptable = result.dmgrouptable;
                    $("[name='handleProcedure']").val(dmgrouptable.handleprocedure);
                    $("[name='handleProcedure']").attr("readonly","readonly");
                    $("[name='restoreProcedure']").val(dmgrouptable.restoreprocedure);
                    $("[name='restoreProcedure']").attr("readonly","readonly");
                    $("[name='handleContent']").val(dmgrouptable.handletargettable);
                    $("[name='handleContent']").attr("readonly","readonly");
                } else {
                    layer.alert(result.msg, {icon: 6});
                }
            },
            error:function(){
                $(".page-container").hideLoading();
                layer.alert('网络错误', {icon: 6});
            }
        });

        $("#Huifold1 .item h4:first").find("b").html("-");
        $("#Huifold1 .item .info:first").show();
        $.Huifold("#Huifold1 .item h4","#Huifold1 .item .info","fast",3,"click"); /*5个参数顺序不可打乱，分别是：相应区,隐藏显示的内容,速度,类型,事件*/
    }

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

    function showNext($this){
        var _next = $($this).parent().parent().next();
        if(_next.is(':hidden')){
            _next.show();
            $($this).html("-");
        } else {
            _next.hide();
            $($this).html("+");
        }
    }

    function editProcedure($this){
        if($($this).hasClass("active")){
            return ;
        }
        $($this).addClass("active");
        $($this).parent().next().children().removeAttr("readonly");
    }

    function cancelEdit($this){
        $($this).prev().prev().removeClass("active");
        $($this).parent().next().children().attr("readonly","readonly");
    }

    function ajaxEditMigration($this){
        $.ajax({
            url:"${ctx}/migration/ajaxEditMigration",
            type:"post",
            data:{id:"${param.id}",handleProcedure:$("[name='handleProcedure']").val()},
            dataType:"json",
            success:function(result){
                if(result.code == 0){
                    $($this).prev().removeClass("active");
                    $("[name='handleProcedure']").attr("readonly","readonly");
                    layer.msg(result.msg);
                } else {
                    layer.alert(result.msg, {icon: 6});
                }
            },
            error:function(){
                layer.alert('网络错误', {icon: 6});
            }
        });
    }

    function ajaxEditRestore($this){
        $.ajax({
            url:"${ctx}/migration/ajaxEditRestore",
            type:"post",
            data:{id:"${param.id}",restoreProcedure:$("[name='restoreProcedure']").val()},
            dataType:"json",
            success:function(result){
                if(result.code == 0){
                    $($this).prev().removeClass("active");
                    $("[name='restoreProcedure']").attr("readonly","readonly");
                    layer.msg(result.msg);
                } else {
                    layer.alert(result.msg, {icon: 6});
                }
            },
            error:function(){
                layer.alert('网络错误', {icon: 6});
            }
        });
    }

    function ajaxEditHandle($this){
        $.ajax({
            url:"${ctx}/migration/ajaxEditHandle",
            type:"post",
            data:{id:"${param.id}",handleContent:$("[name='handleContent']").val()},
            dataType:"json",
            success:function(result){
                if(result.code == 0){
                    $($this).prev().removeClass("active");
                    $("[name='handleContent']").attr("readonly","readonly");
                    layer.msg(result.msg);
                } else {
                    layer.alert(result.msg, {icon: 6});
                }
            },
            error:function(){
                layer.alert('网络错误', {icon: 6});
            }
        });
    }
</script>
</body>
</html>