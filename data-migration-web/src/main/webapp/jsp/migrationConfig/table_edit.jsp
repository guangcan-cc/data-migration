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
</head>
<body>
<div class="page-container">
    <form class="form form-horizontal" id="form">
        <input type="hidden" value="${dmgrouptable.id}" name="id"/>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>原数据源：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="hidden" name="originaldsid" id="originaldsid" value="${dmgrouptable.originaldsid}">
                    <input type="text" class="input-text radius" readonly="readonly" value="${dmgrouptable.originaldsname}" name="originaldsname" id="originaldsname">
                </div>
            </div>
            <div class="inline">
                <div class="formControls col-sm-1" style="width: 10%;padding: 0;padding-left: 25px;margin-left: 35px;">
                    <input type="checkbox" class="check-box" id="synchro-original-datasource">
                    <label for="synchro-original-datasource">同原数据源</label>
                </div>
                <div class="formControls col-xs-4" style="">
                    <label>
                        <span class="c-red">*</span>
                        <strong>目标数据源：</strong>
                    </label>
                    <input type="hidden" name="targetdsid" id="targetdsid" value="${dmgrouptable.targetdsid}">
                    <input style="width:57%;margin-left:25px;" type="text" class="input-text radius" value="${dmgrouptable.targetdsname}" name="targetdsname" id="targetdsname">
                    <div style="position: absolute;z-index: 1000;margin-left:125px;">
                        <ul>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><strong>原用户：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="text" readonly="readonly" style="border: none;" class="input-text radius" value="${dmgrouptable.originaldsusername}" name="originaldsusername" id="originaldsusername">
                </div>
            </div>
            <div class="inline">
                <div class="formControls col-sm-1" style="width: 10%;padding: 0;padding-left: 25px;margin-left: 33px;">
                </div>
                <div class="formControls col-xs-4" style="">
                    <label style="margin-left: 25px;"><strong>目标用户：</strong></label>
                    <input style="width:57%;margin-left:25px;border: none;" readonly="readonly" type="text" class="input-text radius" value="${dmgrouptable.targetdsusername}" name="targetdsusername" id="targetdsusername">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>原表名：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="text" class="input-text radius" value="${dmgrouptable.originaltable}" name="originaltable" id="originaltable">
                </div>
            </div>
            <div class="inline">
                <div class="formControls col-sm-1" style="width: 10%;padding: 0;padding-left: 25px;margin-left: 39px;">
                </div>
                <div class="formControls col-xs-4" style="">
                    <label style="margin-left: 13px;"><span class="c-red">*</span><strong>目标表名：</strong></label>
                    <input style="width:57%;margin-left:25px;" type="text" class="input-text radius" value="${dmgrouptable.targettable}" name="targettable" id="targettable">
                </div>
            </div>
        </div>
        <hr class="mt-10">
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2"><strong>是否清理原表数据：</strong></label>
                <div class="formControls col-xs-3">
                    <select name="iscleanup" id="iscleanup" class="select-default radius" style="height: 30px;">
                        <option value="1" <c:if test="${dmgrouptable.iscleanup == 1}">selected="selected"</c:if>>是</option>
                        <option value="0" <c:if test="${dmgrouptable.iscleanup == 0}">selected="selected"</c:if>>否</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row cl" style="margin-top: 30px;">
            <div class="col-xs-8 col-xs-offset-4">
                <button id="btn_save" onClick="table_edit_submit();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
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
<script type="text/javascript" src="<c:url value="/js/datasource/group.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/common.js"/>"></script>
<script type="text/javascript">

    var ctx = "${ctx}";
    var dmdatasources;

    window.onload = function(){
        $("#targetdsname").on("input",function(){
            ajaxQueryDSName($("#targetdsname").val(),"targetdsname");
        });
        $("#synchro-original-datasource").on("change",function(){
            if($("#synchro-original-datasource").is(":checked")){
                $("#targetdsname").attr("readonly","readonly");
                $("#targetdsname").val($("#originaldsname").val());
                $("#targetdsid").val($("#originaldsid").val());
                $("#targetdsusername").val($("#originaldsusername").val());
            } else {
                $("#targetdsname").removeAttr("readonly");
            }
        });
    }

    /**
     * 编辑表信息
     */
    function table_edit_submit(){

        //验证组数据
        if(!validTableForm()){
            return ;
        }
        layer.load(2);
        ajaxEditTableInfo();
    }

    function ajaxEditTableInfo(){
        $.ajax({
            url:"${ctx}/migration/ajaxEditTableInfo",
            type:"post",
            data:$("#form").serialize(),
            dataType:"json",
            success:function(result){
                if(result.code == 0){
                    layer.alert(result.msg,{icon:1},function(){
                        parent.location.reload();
                        layer_close();
                    });
                } else {
                    layer.alert(result.msg, {icon: 6});
                }
            },
            error:function(){
                layer.alert('网络错误', {icon: 6});
            }
        });
    }

    function validTableForm(){
        if(isBlank($("#targetdsid").val())){
            layer.alert('请选择目标数据源', {icon: 6});
            return false;
        }
        if(isBlank($("#originaltable").val())){
            layer.alert('原表名不能为空', {icon: 6});
            return false;
        }
        if(isBlank($("#targettable").val())){
            layer.alert('目标表名不能为空', {icon: 6});
            return false;
        }
        return true;
    }

</script>
</body>
</html>