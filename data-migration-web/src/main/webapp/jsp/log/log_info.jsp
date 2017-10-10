<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/jsp/common/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>日志信息</title>
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
    <i class="Hui-iconfont">&#xe67f;</i> 日志管理
    <span class="c-gray en">&gt;</span> 日志信息
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
                    <input type="text" class="input-text radius" value="" name="dsName">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-sm-2">组名：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" value="" name="username">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2">组类型：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" value="" name="creator">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-sm-2">执行人：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" value="" name="creator">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2">执行结果：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" value="" name="creator">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-sm-2">执行时间：</label>
                <div class="formControls col-xs-3">
                    <input name="startTime" type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'logmax\')||\'%y-%M-%d\'}' })" id="logmin" class="input-text radius Wdate" style="width:120px;">
                    -
                    <input name="endTime" type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'logmin\')}',maxDate:'%y-%M-%d' })" id="logmax" class="input-text radius Wdate" style="width:120px;">
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
                    <a href="javascript:void(0);" onclick="data_source_add('新增数据源','data_source_add.jsp');" class="btn btn-primary radius">
                        <i class="Hui-iconfont">&#xe604;</i> 新增
                    </a>
                    <a class="btn btn-secondary radius" onclick="data_source_edit('编辑数据源','${ctx}/dataSource/editShowDSInfo');" href="javascript:void(0);">
                        <i class="Hui-iconfont">&#xe60c;</i> 编辑
                    </a>
                </span>
    </div>
    <div class="text-c" id="content">
        <table class="table table-border table-bg table-sort">
            <thead class="text-c">
            <tr>
                <th>选择</th>
                <th>序号</th>
                <th>数据源名</th>
                <th>用户名</th>
                <th>服务器地址</th>
                <th>端口</th>
                <th>服务器名</th>
                <th>数据源说明</th>
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

    $(function(){
        ajaxQueryDSInfo(1);
    });

    function ajaxQueryDSInfo(pageIndex){
        $("#content").showLoading();
        $.ajax({
            url:"${ctx}/dataSource/queryDSInfo?currentPage=" + pageIndex + "&pageSize=" + pageSize,
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
                        var dsInfo = list[i];
                        var trHtml = "<tr>" +
                                        "<td><input type='radio' name='ds_info' value='"+ dsInfo.id +"'/></td>" +
                                        "<td>" + (i+1) + "</td>" +
                                        "<td>" + dsInfo.dsname + "</td>" +
                                        "<td>" + dsInfo.username + "</td>" +
                                        "<td>" + dsInfo.serverip + "</td>" +
                                        "<td>" + dsInfo.port + "</td>" +
                                        "<td>" + dsInfo.servername + "</td>" +
                                        "<td>" + (dsInfo.explain == null ? "无":dsInfo.explain) + "</td>" +
                                        "<td>" + dsInfo.creator + "</td>" +
                                        "<td>" + formatDate(dsInfo.createtime) + "</td>" +
                                        "<td>" + (dsInfo.isforbidden == 1 ? "是" : "否") + "</td>" +
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
                alert("网络错误");
                $("#content").hideLoading();
            }
        });

    }

    /*添加*/
    function data_source_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*编辑*/
    function data_source_edit(title,url){
        var id = $('input[name="ds_info"]:checked ').val();
        if(isBlank(id)){
            alert("请选择需要编辑的一条信息");
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