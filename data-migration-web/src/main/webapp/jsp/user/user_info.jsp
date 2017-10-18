<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/jsp/common/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>用户管理</title>
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
    <i class="Hui-iconfont">&#xe67f;</i> 系统管理
    <span class="c-gray en">&gt;</span> 用户管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i>
    </a>
</nav>
<div class="page-container">
    <form id="query-form">
    <div class="text-c">
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2">用户昵称：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" value="" name="username">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-sm-2">是否有效：</label>
                <div class="formControls col-xs-3">
                    <select name="isvalid" class="select-default radius">
                        <option value="">全部</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div style="margin-right: 100px;margin-top: 5px;">
                <button class="btn btn-success" type="button" onclick="ajaxQueryUserInfo(1);"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
            </div>
        </div>
    </div>
    </form>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="user_add('新增用户信息','user_add.jsp');" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe604;</i> 新增
            </a>
            <c:if test="${sessionScope.user.userRole == 0}">
                <a class="btn btn-secondary radius" onclick="user_edit('编辑用户信息','${ctx}/user/editShowUserInfo');" href="javascript:void(0);">
                    <i class="Hui-iconfont">&#xe60c;</i> 编辑
                </a>
            </c:if>
        </span>
    </div>
    <div class="text-c" id="content">
        <table class="table table-border table-bg table-sort">
            <thead class="text-c">
            <tr>
                <th>选择</th>
                <th>序号</th>
                <th>用户名</th>
                <th>用户昵称</th>
                <th>联系电话</th>
                <th>邮箱</th>
                <th>创建时间</th>
                <th>是否有效</th>
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
        ajaxQueryUserInfo(1);
    });

    function ajaxQueryUserInfo(pageIndex){
        $("#content").showLoading();
        $.ajax({
            url:"${ctx}/user/queryUserInfo?currentPage=" + pageIndex + "&pageSize=" + pageSize,
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
                        var userInfo = list[i];
                        var trHtml = "<tr>" +
                                        "<td><input type='radio' name='user_info' value='"+ userInfo.usercode +"'/></td>" +
                                        "<td>" + (i+1) + "</td>" +
                                        "<td>" + userInfo.usercode + "</td>" +
                                        "<td>" + userInfo.username + "</td>" +
                                        "<td>" + (userInfo.phonenumber == null ? "无" : userInfo.phonenumber) + "</td>" +
                                        "<td>" + (userInfo.email == null ? "无" : userInfo.email) + "</td>" +
                                        "<td>" + formatDate(userInfo.createtime) + "</td>" +
                                        "<td>" + (userInfo.isvalid == 1 ? "有效" : "无效") + "</td>" +
                                    "</tr>";
                        $("#tbody").append(trHtml);
                    }
                    var pageTotal = pager.pageTotal;
                    if(pageTotal == 1){
                        return ;
                    }
                    var pageHtml = "";
                    if(currentPage > 1){
                        pageHtml = "<a class='n' href='#' onclick='ajaxQueryUserInfo(" + (currentPage-1) + ")'><上一页</a>";
                    }
                    if(pageTotal <= 10 || (pageTotal > 10 && currentPage < 7)){
                        for(var i = 1; i <= pageTotal; i++){
                            if(i == currentPage){
                                pageHtml += "<a class='active' href='#'> " + i + " </a>";
                            } else {
                                pageHtml += "<a href='#' onclick='ajaxQueryUserInfo(" + i + ")'> " + i + " </a>";
                            }
                        }
                    } else {
                        if(currentPage+4 > pageTotal){
                            for(var i = currentPage-5; i <= pageTotal; i++){
                                if(i == currentPage){
                                    pageHtml += "<a class='active' href='#'> " + i + " </a>";
                                } else {
                                    pageHtml += "<a href='#' onclick='ajaxQueryUserInfo(" + i + ")'> " + i + " </a>";
                                }
                            }
                        } else {
                            for(var i = currentPage-5; i <= currentPage+4; i++){
                                if(i == currentPage){
                                    pageHtml += "<a class='active' href='#'> " + i + " </a>";
                                } else {
                                    pageHtml += "<a href='#' onclick='ajaxQueryUserInfo(" + i + ")'> " + i + " </a>";
                                }
                            }
                        }
                    }
                    if(currentPage < pageTotal){
                        pageHtml += "<a class='n' href='#' onclick='ajaxQueryUserInfo(" + (currentPage+1) + ")'>下一页></a>";
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
    function user_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*编辑*/
    function user_edit(title,url){
        var id = $('input[name="user_info"]:checked ').val();
        if(isBlank(id)){
            alert("请选择需要编辑的一条信息");
            return ;
        }
        var index = layer.open({
            type: 2,
            title: title,
            content: url + "?usercode=" + id
        });
        layer.full(index);
    }
</script>
</body>
</html>