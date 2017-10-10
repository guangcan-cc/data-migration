<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/jsp/common/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>数据源信息</title>
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
    <form class="form form-horizontal" id="form-dataSource-add">
        <input type="hidden" value="${datasource.id}" name="id"/>
        <input type="hidden" value="${datasource.createtime}" name="createtime"/>
        <input type="hidden" value="${datasource.creator}" name="creator"/>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2"><span class="c-red">*</span>数据源名：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" onchange="inputOnChange();" value="${datasource.dsname}" name="dsname" id="dsName">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-sm-2">是否禁用：</label>
                <div class="formControls col-xs-3">
                    <select name="isforbidden" class="select-default radius"  onchange="inputOnChange();">
                        <option value="1" <c:if test="${datasource.isforbidden == 1}">selected="selected"</c:if>>是</option>
                        <option value="0" <c:if test="${datasource.isforbidden == 0}">selected="selected"</c:if>>否</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>用户名：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius"  onchange="inputOnChange();" value="${datasource.username}" name="username" id="username">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>密码：</label>
                <div class="formControls col-xs-3">
                    <input type="password" class="input-text radius"  onchange="inputOnChange();" value="${datasource.password}" name="password" id="password">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>服务器地址：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius"  onchange="inputOnChange();" value="${datasource.serverip}" name="serverip" id="serverip">
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>端口：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" onchange="inputOnChange();" value="${datasource.port}" name="port" id="port">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>服务名称：</label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" onchange="inputOnChange();" value="${datasource.servername}" name="servername" id="servername">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2">数据源说明：</label>
                <div class="formControls col-xs-3">
                    <textarea type="text" class="textarea radius"  onchange="inputOnChange();" name="explain">${datasource.explain}</textarea>
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"></label>
                <div class="formControls col-xs-3 mt-30 ml-50">
                    <button id="btn-connect" onClick="connectTest();" class="btn btn-success-outline radius" style="width: 100px;" type="button">&nbsp;&nbsp;连接&nbsp;&nbsp;</button>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-xs-offset-4">
                <button id="btn-edit" class="btn btn-primary radius disabled" type="button"><i class="Hui-iconfont">&#xe632;</i> 编辑</button>
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
<script type="text/javascript" src="<c:url value="/js/common.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/datasource/datasource.js"/>"></script>
<script type="text/javascript">


    //input值是否变化
    function inputOnChange(){
        var btn_connect = $("#btn-connect");
        if(btn_connect.hasClass("disabled")){
            btn_connect.removeClass("disabled");
        }
        var btn_edit = $("#btn-edit");
        if(!btn_edit.hasClass("disabled")){
            btn_edit.removeAttr("onclick");
            btn_edit.addClass("disabled");
        }
    }

    function connectTest(){
        if(!checkForm()){
            return ;
        }
        $(".page-container").showLoading();
        $.ajax({
            url:"${ctx}/dataSource/connectTest",
            type:"post",
            data:$("#form-dataSource-add").serialize(),
            dataType:"json",
            success:function(result){
                $(".page-container").hideLoading();
                if(result.code == 0){
                    layer.msg("连接成功");
                    $("#btn-connect").addClass("active");
                    $("#btn-connect").addClass("disabled");
                    $("#btn-edit").removeClass("disabled");
                    $("#btn-edit").attr("onclick","editDSInfo();");
                }else{
                    layer.alert('连接失败！请核对信息。', {icon: 6});
                }
            },
            error:function(){
                $(".page-container").hideLoading();
                layer.alert('网络错误', {icon: 6});
            }
        });

    }
    function editDSInfo(){
        if(!checkForm()){
            return ;
        }
        $.ajax({
            url:"${ctx}/dataSource/editDSInfo",
            type:"post",
            data:$("#form-dataSource-add").serialize(),
            dataType:"json",
            success:function(result){
                layer.msg(result.msg);
            },
            error:function(){
                layer.alert('网络错误', {icon: 6});
            }
        });
    }

</script>
</body>
</html>