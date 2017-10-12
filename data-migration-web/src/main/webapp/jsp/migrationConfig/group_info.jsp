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
    <link href="<c:url value="/h-ui/css/H-ui.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/h-ui/css/H-ui.admin.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/plugins/Hui-iconfont/1.0.7/iconfont.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/css/showLoading.css"/>" rel="stylesheet" type="text/css" />
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 迁移配置
    <span class="c-gray en">&gt;</span> 组数据查询
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i>
    </a>
</nav>
<div class="page-container">
    <form id="query-form">
        <div class="text-c">
            <div class="row cl">
                <div class="inline">
                    <label class="form-label col-sm-2">组ID：</label>
                    <div class="formControls col-xs-3">
                        <input type="text" class="input-text radius" value="" name="id">
                    </div>
                </div>
                <div class="inline">
                    <label class="form-label col-sm-2">创建人：</label>
                    <div class="formControls col-xs-3">
                        <input type="text" class="input-text radius" value="" name="creator">
                    </div>
                </div>
            </div>
            <div class="row cl">
                <div class="inline">
                    <label class="form-label col-sm-2">组名：</label>
                    <div class="formControls col-xs-3">
                        <input type="text" class="input-text radius" value="" name="groupname">
                    </div>
                </div>
                <div class="inline">
                    <label class="form-label col-sm-2">创建时间：</label>
                    <div class="formControls col-xs-3">
                        <input name="startTime" type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'logmax\')||\'%y-%M-%d\'}' })" id="logmin" class="input-text radius Wdate" style="width:120px;">
                        -
                        <input name="endTime" type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'logmin\')}',maxDate:'%y-%M-%d' })" id="logmax" class="input-text radius Wdate" style="width:120px;">
                    </div>
                </div>
            </div>
            <div class="row cl">
                <div class="inline">
                    <label class="form-label col-sm-2">组类型：</label>
                    <div class="formControls col-xs-3">
                        <select name="type" class="select-default radius">
                            <option value="">全部</option>
                            <option value="1">数据迁移</option>
                            <option value="0">数据校验</option>
                        </select>
                    </div>
                </div>
                <div class="inline">
                    <label class="form-label col-sm-2">是否禁用：</label>
                    <div class="formControls col-xs-3">
                        <select name="isForbidden" class="select-default radius">
                            <option value="">全部</option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row cl">
                <div style="margin-right: 100px;margin-top: 5px;">
                    <button class="btn btn-success" type="button" onclick="ajaxQueryDSInfo(1);"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
                </div>
            </div>
        </div>
    </form>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
                <span class="l">
                    <a href="javascript:void(0);" onclick="group_add('组新增信息','group_add.jsp');" class="btn btn-primary radius">
                        <i class="Hui-iconfont">&#xe604;</i> 新增组
                    </a>
                    <a class="btn btn-primary radius" onclick="group_edit('组编辑信息','${ctx}/migration/editShowGroupInfo');" href="javascript:void(0);">
                        <i class="Hui-iconfont">&#xe60c;</i> 编辑组
                    </a>
                    <a class="btn btn-primary radius" onclick="show_tables('组表配置信息','${ctx}/migration/showTablesInGroup');" href="javascript:void(0);">
                        <i class="Hui-iconfont">&#xe60c;</i> 查看组表配置
                    </a>
                </span>
    </div>
    <div class="text-c" id="content">
        <table class="table table-border table-bg table-sort">
            <thead class="text-c">
            <tr>
                <th>选择</th>
                <th>序号</th>
                <th>组ID</th>
                <th>组名</th>
                <th>组类型</th>
                <th>组说明</th>
                <th>原数据源</th>
                <th>原用户</th>
                <th>是否提数</th>
                <th>中间表名</th>
                <th>是否发送邮件</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>是否禁用</th>
            </tr>
            </thead>
            <tbody class="text-c" id="tbody">
            </tbody>
        </table>
        <div class="pages">
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/plugins/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.form.js"/>"></script>
<script type="text/javascript" src="<c:url value="/admin/js/common.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/layer/2.1/layer.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.showLoading.min.js"/>"></script>
<script type="text/javascript">

    var currentPage = 1;
    var pageSize = 4;

    $(document).ready(function(){
        ajaxQueryDSInfo(1);
    });

    function ajaxQueryDSInfo(pageIndex){
        $("#content").showLoading();
        $.ajax({
            url:"${ctx}/migration/queryGroupInfo?currentPage=" + pageIndex + "&pageSize=" + pageSize,
            type:"post",
            data:$("#query-form").serialize(),
            dataType:"json",
            success:function(result){
                $("#content").hideLoading();
                if(result.code == 0){
                    $("#tbody").html("");
                    var pager = result.pager;
                    currentPage = pager.currentPage;
                    pageSize = pager.pageSize;
                    var list = pager.content;
                    for(var i = 0; i < list.length; i++){
                        var groupInfo = list[i];
                        var trHtml = "<tr>" +
                                        "<td><input type='radio' name='groupId' value='"+ groupInfo.id +"'/></td>" +
                                        "<td>" + (i+1) + "</td>" +
                                        "<td>" + groupInfo.id + "</td>" +
                                        "<td>" + groupInfo.groupname + "</td>" +
                                        "<td>" + (groupInfo.type == 0 ? "数据迁移" : "数据校验") + "</td>" +
                                        "<td>" + (groupInfo.explain == null ? "无" : groupInfo.explain) + "</td>" +
                                        "<td>" + groupInfo.originaldsname + "</td>" +
                                        "<td>" + groupInfo.originaldsusername + "</td>" +
                                        "<td>" + (groupInfo.isdataextracted == 1 ? "是" : "否") + "</td>" +
                                        "<td>" + (groupInfo.midtablename == null ? "无" : groupInfo.midtablename) + "</td>" +
                                        "<td>" + (groupInfo.issendemail == 1 ? "是" : "否") + "</td>" +
                                        "<td>" + groupInfo.creator + "</td>" +
                                        "<td>" + formatDate(groupInfo.createtime) + "</td>" +
                                        "<td>" + (groupInfo.isforbidden == 1 ? "是" : "否") + "</td>" +
                                    "</tr>";
                        $("#tbody").append(trHtml);
                    }
                    var pageTotal = pager.pageTotal;
                    if(pageTotal == 1){
                        return ;
                    }
                    var pageHtml = "";
                    if(currentPage > 1){
                        pageHtml = "<a class='n' href='#' onclick='ajaxQueryDSInfo(" + (currentPage-1) + ")'><上一页</a>";
                    }
                    if(pageTotal <= 10 || (pageTotal > 10 && currentPage < 7)){
                        for(var i = 1; i <= pageTotal; i++){
                            if(i == currentPage){
                                pageHtml += "<a class='active' href='#'> " + i + " </a>";
                            } else {
                                pageHtml += "<a href='#' onclick='ajaxQueryDSInfo(" + i + ")'> " + i + " </a>";
                            }
                        }
                    } else {
                        if(currentPage+4 > pageTotal){
                            for(var i = currentPage-5; i <= pageTotal; i++){
                                if(i == currentPage){
                                    pageHtml += "<a class='active' href='#'> " + i + " </a>";
                                } else {
                                    pageHtml += "<a href='#' onclick='ajaxQueryDSInfo(" + i + ")'> " + i + " </a>";
                                }
                            }
                        } else {
                            for(var i = currentPage-5; i <= currentPage+4; i++){
                                if(i == currentPage){
                                    pageHtml += "<a class='active' href='#'> " + i + " </a>";
                                } else {
                                    pageHtml += "<a href='#' onclick='ajaxQueryDSInfo(" + i + ")'> " + i + " </a>";
                                }
                            }
                        }
                    }
                    if(currentPage < pageTotal){
                        pageHtml += "<a class='n' href='#' onclick='ajaxQueryDSInfo(" + (currentPage+1) + ")'>下一页></a>";
                    }
                    $(".pages").html(pageHtml);
                } else {
                    alert(result.msg);
                }
            },
            error:function(){
                layer.alert('网络错误', {icon: 6});
                $("#content").hideLoading();
            }
        });

    }

    /*添加*/
    function group_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*编辑*/
    function group_edit(title,url){
        var id = $('input[name="groupId"]:checked ').val();
        if(isBlank(id)){
            layer.alert('请选择需要编辑的一条信息', {icon: 6});
            return ;
        }
        var index = layer.open({
            type: 2,
            title: title,
            content: url + "?id=" + id
        });
        layer.full(index);
    }
    /*查看组表信息*/
    function show_tables(title,url){
        var id = $('input[name="groupId"]:checked ').val();
        if(isBlank(id)){
            layer.alert('请选择查看组', {icon: 6});
            return ;
        }
        var index = layer.open({
            type: 2,
            title: title,
            content: url + "?id=" + id
        });
        layer.full(index);
    }
</script>
</body>
</html>