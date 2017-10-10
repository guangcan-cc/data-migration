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
                    <input type="text" class="input-text radius" value="" name="groupId">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-sm-2">组名：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" value="" name="groupName">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2">执行人：</label>
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
                <button class="btn btn-success" type="button" onclick="ajaxQueryLogInfo(1);"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
            </div>
        </div>
    </div>
    </form>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <hr>
    </div>
    <div class="text-c" id="content">
        <table class="table table-border table-bg table-sort">
            <thead class="text-c">
            <tr>
                <th>序号</th>
                <th>日志ID</th>
                <th>组信息</th>
                <th>执行时间</th>
                <th>执行人</th>
                <th>执行结果</th>
                <th>执行详情</th>
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
        ajaxQueryLogInfo(1);
    });

    function ajaxQueryLogInfo(pageIndex){
        $("#content").showLoading();
        $.ajax({
            url:"${ctx}/log/queryLogInfo?currentPage=" + pageIndex + "&pageSize=" + pageSize,
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
                        var info = list[i];
                        var handleresult = info.handleresult;
                        if(handleresult == 0){
                            handleresult = "<div class='btn btn-danger radius' style='height: 20px;padding: 0 5px;line-height: 15px;'><i class='Hui-iconfont'>&#xe6a6;</i></div> 失败";
                        } else if (handleresult == 1){
                            handleresult = "<div class='btn btn-secondary radius' style='height: 20px;padding: 0 5px;line-height: 15px;'><i class='Hui-iconfont'>&#xe6a7;</i></div> 成功";
                        } else if (handleresult == 2){
                            handleresult = "<div class='btn btn-default radius' style='height: 20px;padding: 0 5px;line-height: 15px;'><i class='Hui-iconfont'>&#xe6f9;</i></div> 正在执行";
                        }
                        var trHtml = "<tr>" +
                                        "<td>" + (i+1) + "</td>" +
                                        "<td>" + info.id + "</td>" +
                                        "<td>" + info.groupid + "-" + info.groupname + "</td>" +
                                        "<td>" + formatDateFull(info.handlestarttime) + "—" + formatDateFull(info.handleendtime) + "</td>" +
                                        "<td>" + info.handleperson + "</td>" +
                                        "<td>" + handleresult + "</td>" +
                                        "<td><a href='javascript:void(0);' onclick=\"show_detail('" + info.id + "','" + info.groupid + "');\">查看</a></td>" +
                                    "</tr>";
                        $("#tbody").append(trHtml);
                    }
                    var pageTotal = pager.pageTotal;
                    if(pageTotal == 1){
                        return ;
                    }
                    var pageHtml = "";
                    if(currentPage > 1){
                        pageHtml = "<a class='n' href='#' onclick='ajaxQueryLogInfo(" + (currentPage-1) + ")'><上一页</a>";
                    }
                    if(pageTotal <= 10 || (pageTotal > 10 && currentPage < 7)){
                        for(var i = 1; i <= pageTotal; i++){
                            if(i == currentPage){
                                pageHtml += "<a class='active' href='#'> " + i + " </a>";
                            } else {
                                pageHtml += "<a href='#' onclick='ajaxQueryLogInfo(" + i + ")'> " + i + " </a>";
                            }
                        }
                    } else {
                        if(currentPage+4 > pageTotal){
                            for(var i = currentPage-5; i <= pageTotal; i++){
                                if(i == currentPage){
                                    pageHtml += "<a class='active' href='#'> " + i + " </a>";
                                } else {
                                    pageHtml += "<a href='#' onclick='ajaxQueryLogInfo(" + i + ")'> " + i + " </a>";
                                }
                            }
                        } else {
                            for(var i = currentPage-5; i <= currentPage+4; i++){
                                if(i == currentPage){
                                    pageHtml += "<a class='active' href='#'> " + i + " </a>";
                                } else {
                                    pageHtml += "<a href='#' onclick='ajaxQueryLogInfo(" + i + ")'> " + i + " </a>";
                                }
                            }
                        }
                    }
                    if(currentPage < pageTotal){
                        pageHtml += "<a class='n' href='#' onclick='ajaxQueryLogInfo(" + (currentPage+1) + ")'>下一页></a>";
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

    /*查看组表信息*/
    function show_detail(logId,groupId){
        var index = layer.open({
            type: 2,
            title: "日志详情",
            content: "${ctx}/log/showDetailLogInfo?logId=" + logId + "&groupId=" + groupId
        });
        layer.full(index);
    }
</script>
</body>
</html>