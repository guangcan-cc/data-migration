<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/jsp/common/taglib.jspf"%>
<c:set var="extractscript" value="${dmgroup.extractscript}" />
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
<div class="page-container">
    <div class="pd-5 bg-1 bk-gray">
        <p align="center" style="font-size: 20px;">组信息</p>
    </div>
    <div>
        <table class="table table-border table-bg">
            <tr>
                <th>组ID：</th>
                <td>${dmgroup.id}</td>
                <th>是否发送邮件</th>
                <td>
                    <c:choose>
                        <c:when test="${dmgroup.issendemail == 1}">是</c:when>
                        <c:otherwise>否</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>组名：</th>
                <td>${dmgroup.groupname}</td>
                <th>邮箱列表</th>
                <td>${dmgroup.emailset}</td>
            </tr>
            <tr>
                <th>组类型：</th>
                <td>
                    <c:choose>
                        <c:when test="${dmgroup.type == 0}">数据迁移</c:when>
                        <c:otherwise>数据迁移数据校验</c:otherwise>
                    </c:choose>
                </td>

                <th></th>
                <td></td>
            </tr>
            <tr>
                <th>组说明：</th>
                <td>${dmgroup.explain}</td>
                <th>是否禁用</th>
                <td>
                    <c:choose>
                        <c:when test="${dmgroup.isforbidden == 1}">是</c:when>
                        <c:otherwise>否</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>

            </tr>
            <tr>
                <th>是否提数：</th>
                <td>
                    <c:choose>
                        <c:when test="${dmgroup.isdataextracted == 1}">是</c:when>
                        <c:otherwise>否</c:otherwise>
                    </c:choose>
                </td>
                <th>迁移表默认条件：</th>
                <td>${dmgroup.defaultcondition}</td>
            </tr>
            <tr>
                <th>中间表名：</th>
                <td>${dmgroup.midtablename}</td>
                <th>是否备份中间表：</th>
                <td>
                    <c:choose>
                        <c:when test="${dmgroup.isbackupmidtable == 1}">是</c:when>
                        <c:otherwise>否</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>提数脚本：</th>
                <td colspan="3"><a class="btn-link" href="javascript:void(0);" onclick="show_extractscript();">点击查看</a></td>
                <%--<th>备份脚本</th>
                <td>${dmgroup.backupscript}</td>--%>
            </tr>
            <tr>
                <th>原数据源：</th>
                <td>${dmgroup.originaldsname}</td>
                <th>目标数据源</th>
                <td>${dmgroup.targetdsname}</td>
            </tr>
            <tr>
                <th>原用户：</th>
                <td>${dmgroup.originaldsusername}</td>
                <th>目标用户</th>
                <td>${dmgroup.targetdsusername}</td>
            </tr>
        </table>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <p align="center" style="font-size: 20px;">表信息</p>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-5">
                <span class="l">
                    <a href="javascript:void(0);" onclick="table_add('表新增','${ctx}/jsp/migrationConfig/table_add.jsp?groupId=${dmgroup.id}');" class="btn btn-primary radius">
                        <i class="Hui-iconfont">&#xe604;</i> 新增
                    </a>
                    <a class="btn btn-primary radius" onclick="table_edit('表编辑','${ctx}/migration/editShowTableInfo');" href="javascript:void(0);">
                        <i class="Hui-iconfont">&#xe60c;</i> 编辑
                    </a>
                    <a class="btn btn-primary radius" onclick="table_delete();" href="javascript:void(0);">
                        <i class="Hui-iconfont">&#xe609;</i> 删除
                    </a>
                </span>
    </div>
    <div class="text-c" id="content">
        <table class="table table-border table-bg table-sort">
            <thead class="text-c">
            <tr>
                <th>选择</th>
                <th>序号</th>
                <th>原数据源</th>
                <th>原用户</th>
                <th>原表名</th>
                <th>目标数据源</th>
                <th>目标用户</th>
                <th>目标表名</th>
                <th>是否清理原表数据</th>
                <th>详情</th>
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
        ajaxQueryInfo(1);
    });

    function ajaxQueryInfo(pageIndex){
        $("#content").showLoading();
        $.ajax({
            url:"${ctx}/migration/queryTables?currentPage=" + pageIndex + "&pageSize=" + pageSize,
            type:"post",
            data:{id:"${dmgroup.id}"},
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
                        var tableInfo = list[i];
                        var trHtml = "<tr>" +
                                        "<td><input type='radio' name='tableId' value='"+ tableInfo.id +"'/></td>" +
                                        "<td>" + (i+1) + "</td>" +
                                        "<td>" + tableInfo.originaldsname + "</td>" +
                                        "<td>" + tableInfo.originaldsusername + "</td>" +
                                        "<td>" + tableInfo.originaltable + "</td>" +
                                        "<td>" + tableInfo.targetdsname + "</td>" +
                                        "<td>" + tableInfo.targetdsusername + "</td>" +
                                        "<td>" + tableInfo.targettable + "</td>" +
                                        "<td>" + (tableInfo.iscleanup == 1 ? "是" : "否") + "</td>" +
                                        "<td><a class='btn-link' href='javascript:void(0);' onclick='showDetailView(\""+ tableInfo.id +"\");'>查看</a></td>" +
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
    function table_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*编辑*/
    function table_edit(title,url){
        var id = $('input[name="tableId"]:checked ').val();
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
    /*删除*/
    function table_delete(){
        var id = $('input[name="tableId"]:checked ').val();
        if(isBlank(id)){
            layer.alert('请选择需要删除的一条信息', {icon: 6});
            return ;
        }
        layer.confirm('确定要删除？', {
            btn: ['删除','取消'] //按钮
        }, function(){
            $.ajax({
                url:"${ctx}/migration/ajaxDeleteTableInfo?id=" + id,
                type:"post",
                dataType:"json",
                success:function(result){
                    if(result.code == 0){
                        layer.msg(result.msg);
                        ajaxQueryInfo(currentPage);
                    } else {
                        layer.msg(result.msg);
                    }
                },
                error:function(){
                    layer.alert('网络错误', {icon: 6});
                }
            });
        });
    }

    /**
     * 查看详情
     */
    function showDetailView(id){
        var index = parent.layer.open({
            type: 2,
            title: "表详情",
            content: "${ctx}/jsp/migrationConfig/table_detail.jsp?id=" + id
        });
        parent.layer.full(index);
        layer_close();
    }

    function show_extractscript(){
        var extractscript = "${extractscript}";
        layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            shadeClose: true,
            content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">' + extractscript + '</div>'
        });
    }
</script>
</body>
</html>