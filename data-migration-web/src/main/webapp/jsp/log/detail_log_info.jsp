<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/jsp/common/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>日志详情</title>
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
                <td>${dmgroup.extractscript}</td>
                <th>备份脚本</th>
                <td>${dmgroup.backupscript}</td>
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
    <div class="text-c">
        <table class="table table-border table-bg table-sort">
            <thead class="text-c">
            <tr>
                <th>序号</th>
                <th>原数据源</th>
                <th>原用户</th>
                <th>原表名</th>
                <th>目标数据源</th>
                <th>目标用户</th>
                <th>目标表名</th>
                <th>是否清理原表数据</th>
            </tr>
            </thead>
            <tbody class="text-c">
            <c:forEach items="${tables}" var="table" varStatus="i">
                <tr>
                    <td>${i.index+1}</td>
                    <td>${table.originaldsname}</td>
                    <td>${table.originaldsusername}</td>
                    <td>${table.originaltable}</td>
                    <td>${table.targetdsname}</td>
                    <td>${table.targetdsusername}</td>
                    <td>${table.targettable}</td>
                    <td>
                        <c:if test="${table.iscleanup == 1}">是</c:if>
                        <c:if test="${table.iscleanup == 0}">否</c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <p align="center" style="font-size: 20px;">处理信息</p>
    </div>
    <div class="text-c">
        <table class="table table-border table-bg table-sort">
            <thead class="text-c">
            <tr>
                <th>序号</th>
                <th>原数据源</th>
                <th>原用户</th>
                <th>原表名</th>
                <th>目标数据源</th>
                <th>目标用户</th>
                <th>目标表名</th>
                <th>是否清理原表数据</th>
                <th>执行时间</th>
                <th>参数</th>
                <th>存储过程</th>
                <th>应迁移数量</th>
                <th>实际迁移数量</th>
                <th>失败数量</th>
                <th>失败原因</th>
                <th>是否已迁移</th>
            </tr>
            </thead>
            <tbody class="text-c">
            <c:forEach items="${handleLogVOs}" var="vo" varStatus="i">
                <tr>
                    <td>${i.index+1}</td>
                    <td>${vo.dmgrouptable.originaldsname}</td>
                    <td>${vo.dmgrouptable.originaldsusername}</td>
                    <td>${vo.dmgrouptable.originaltable}</td>
                    <td>${vo.dmgrouptable.targetdsname}</td>
                    <td>${vo.dmgrouptable.targetdsusername}</td>
                    <td>${vo.dmgrouptable.targettable}</td>
                    <td>
                        <c:if test="${vo.dmgrouptable.iscleanup == 1}">是</c:if>
                        <c:if test="${vo.dmgrouptable.iscleanup == 0}">否</c:if>
                    </td>
                    <td>
                        <fmt:formatDate value="${vo.dmhandlemsglog.handlestarttime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        <br>
                        <fmt:formatDate value="${vo.dmhandlemsglog.handleendtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>${vo.dmhandlemsglog.migrationparam}</td>
                    <td>${vo.dmhandlemsglog.procedurename}</td>
                    <td>${vo.dmhandlemsglog.datacount}</td>
                    <td>${vo.dmhandlemsglog.handlecount}</td>
                    <td>${vo.dmhandlemsglog.failedcount}</td>
                    <td><a href="javascript:void(0);" onclick="">查看</a></td>
                    <td>
                        <c:if test="${vo.dmhandlemsglog.issuccess == 1}">
                            <div class='btn btn-secondary radius' style='height: 20px;padding: 0 5px;line-height: 15px;'><i class='Hui-iconfont'>&#xe6a7;</i></div>
                        </c:if>
                        <c:if test="${vo.dmhandlemsglog.issuccess == 2}">
                            <div class='btn btn-danger radius' style='height: 20px;padding: 0 5px;line-height: 15px;'><i class='Hui-iconfont'>&#xe6a6;</i></div>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/plugins/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.form.js"/>"></script>
<script type="text/javascript" src="<c:url value="/admin/js/common.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/layer/2.1/layer.js"/>"></script>
<script type="text/javascript" src="<c:url value="/plugins/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.showLoading.min.js"/>"></script>
<script type="text/javascript">

</script>
</body>
</html>