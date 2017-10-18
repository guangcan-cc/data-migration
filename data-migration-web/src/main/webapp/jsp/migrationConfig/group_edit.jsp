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
    <form class="form form-horizontal" id="form-group-edit">
        <input type="hidden" name="creator" value="${dmgroup.creator}"/>
        <input type="hidden" name="createtime" value="${dmgroup.createtime}"/>
        <div class="row cl" style="margin-top: 0px;">
            <div class="inline">
                <label class="form-label col-sm-2"><strong>组ID：</strong></label>
                <div class="formControls col-xs-3">
                    <input type="text" readonly="readonly" class="input-text radius" value="${dmgroup.id}" name="id" style="border: none;"/>
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><strong>是否发送邮件：</strong></label>
                <div class="formControls col-xs-3">
                    <select name="issendemail" class="select-default radius" style="height: 30px;">
                        <option value="1" <c:if test="${dmgroup.issendemail == 1}">selected="selected"</c:if>>是</option>
                        <option value="0" <c:if test="${dmgroup.issendemail == 0}">selected="selected"</c:if>>否</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2"><span class="c-red">*</span><strong>组名：</strong></label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" value="${dmgroup.groupname}" name="groupname" id="groupname">
                </div>
            </div>
            <div class="inline" style="display: none;">
                <label class="form-label col-xs-4 col-sm-2"><strong>邮箱列表：</strong></label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" placeholder="多个邮箱地址以英文 ; 分割" value="${dmgroup.emailset}" name="emailset" id="emailset">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>组类型：</strong></label>
                <div class="formControls col-xs-3">
                    <select name="type" class="select-default radius" disabled="disabled">
                        <option value="0" <c:if test="${dmgroup.type == 0}">selected="selected"</c:if>>数据迁移</option>
                        <option value="1" <c:if test="${dmgroup.type == 1}">selected="selected"</c:if>>数据校验</option>
                    </select>
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><strong>是否禁用：</strong></label>
                <div class="formControls col-xs-3">
                    <select name="isforbidden" class="select-default radius" style="height: 30px;">
                        <option value="0" <c:if test="${dmgroup.isforbidden == 0}">selected="selected"</c:if>>否</option>
                        <option value="1" <c:if test="${dmgroup.isforbidden == 1}">selected="selected"</c:if>>是</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><strong>组说明：</strong></label>
                <div class="formControls col-xs-3">
                    <textarea class="textarea radius" name="explain">${dmgroup.explain}</textarea>
                </div>
            </div>
        </div>
        <hr class="mt-10">
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>原数据源：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="hidden" name="originaldsid" id="originaldsid" value="${dmgroup.originaldsid}">
                    <input type="text" class="input-text radius" value="${dmgroup.originaldsname}" name="originaldsname" id="originaldsname">
                    <div style="display: none;" class="auto-query">
                        <ul>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="inline">
                <div class="formControls col-sm-1" style="width: 10%;padding: 0;padding-left: 25px;margin-left: 21px;">
                    <input type="checkbox" class="check-box"  <c:if test="${dmgroup.issynchroorisource == 1}">checked="checked" readonly="readonly"</c:if> value="1" name="issynchroorisource" id="synchro-original-datasource">
                    <label for="synchro-original-datasource">同原数据源</label>
                </div>
                <div class="formControls col-xs-4" style="">
                    <label><strong>目标数据源：</strong></label>
                    <input type="hidden" name="targetdsid" id="targetdsid" value="${dmgroup.targetdsid}">
                    <input style="width:57%;margin-left:25px;" type="text" readonly="readonly" class="input-text radius" value="${dmgroup.targetdsname}" name="targetdsname" id="targetdsname">
                    <div style="margin-left:115px;display: none;" class="auto-query">
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
                    <input type="text" readonly="readonly" style="border: none;" class="input-text radius" value="${dmgroup.originaldsusername}" name="originaldsusername" id="originaldsusername">
                </div>
            </div>
            <div class="inline">
                <div class="formControls col-sm-1" style="width: 10%;padding: 0;padding-left: 25px;margin-left: 21px;">
                </div>
                <div class="formControls col-xs-4" style="">
                    <label style="margin-left: 13px;"><strong>目标用户：</strong></label>
                    <input style="width:57%;margin-left:25px;border: none;" readonly="readonly" type="text" class="input-text radius" value="${dmgroup.targetdsusername}" name="targetdsusername" id="targetdsusername">
                </div>
            </div>
        </div>
        <hr class="mt-10">
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2"><strong>是否提数：</strong></label>
                <div class="formControls col-xs-3">
                    <select name="isdataextracted" id="isdataextracted" class="select-default radius" style="height: 30px;" disabled="disabled">
                        <option value="1" <c:if test="${dmgroup.isdataextracted == 1}">selected="selected"</c:if>>是</option>
                        <option value="0" <c:if test="${dmgroup.isdataextracted == 0}">selected="selected"</c:if>>否</option>
                    </select>
                </div>
            </div>
        </div>
        <div id="extracted">
            <div class="row cl">
                <div class="inline">
                    <label class="form-label col-xs-4 col-sm-2"><strong>中间表名：</strong></label>
                    <div class="formControls col-xs-3">
                        <input type="text" class="input-text radius" value="${dmgroup.midtablename}" name="midtablename" id="midtablename">
                    </div>
                </div>
                <div class="inline">
                    <label class="form-label col-xs-4 col-sm-2"><strong>迁移默认条件：</strong></label>
                    <div class="formControls col-xs-3">
                        <input type="text" class="input-text radius" value="${dmgroup.defaultcondition}" name="defaultcondition" id="defaultcondition">
                    </div>
                </div>
            </div>
            <div class="row cl">
                <div class="inline">
                    <label class="form-label col-xs-4 col-sm-2"></label>
                    <div class="formControls col-xs-3">
                        <button onClick="buildNumberScript();" class="btn btn-success radius" type="button"><i class="Hui-iconfont">&#xe644;</i> 生成提数脚本</button>
                    </div>
                </div>
                <div class="inline" style="display: none;">
                    <label class="form-label col-xs-4 col-sm-2"><strong>是否备份中间表：</strong></label>
                    <div class="formControls col-xs-3">
                        <select name="isbackupmidtable" class="select-default radius" style="height: 30px;">
                            <option value="1" <c:if test="${dmgroup.isbackupmidtable == 1}">selected="selected"</c:if>>是</option>
                            <option value="0" <c:if test="${dmgroup.isbackupmidtable == 0}">selected="selected"</c:if>>否</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row cl">
                <div class="inline">
                    <label class="form-label col-xs-4 col-sm-2"><strong>提数脚本：</strong></label>
                    <div class="formControls col-xs-3">
                        <textarea class="textarea radius" name="extractscript" id="extractscript">${dmgroup.extractscript}</textarea>
                    </div>
                </div>
                <div class="inline" style="display: none;">
                    <label class="form-label col-xs-4 col-sm-2"><strong>备份脚本：</strong></label>
                    <div class="formControls col-xs-3">
                        <textarea class="textarea radius" name="backupscript">${dmgroup.backupscript}</textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="row cl" style="margin-top: 30px;">
            <div class="col-xs-8 col-xs-offset-4">
                <button id="btn_save" onClick="group_edit_submit();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 编辑</button>
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
    var isBuildScript = true;
    var dmdatasources;

    window.onload = function(){
        var groupname = $("#groupname");
        groupname.on("change",function(){
            if(isBlank(groupname.val())){
                layer.tips('请输入用户名', '#groupname');
            }
        });
        if($("#isdataextracted").val() == 0){
            $("#extracted").hide();
            $("#btn_save").removeClass("disabled");
            isBuildScript = true;
        }
        $("#isdataextracted").on("change",function(){
            if($(this).val() == 0){
                $("#extracted").hide();
            } else {
                $("#extracted").show();
                $("#btn_save").addClass("disabled");
            }
        });
        if($("[name=issendemail]").val() == 1){
            $("#emailset").parent().parent().show();
        }
        $("[name=issendemail]").on("change",function(){
            if($(this).val() == 0){
                $("#emailset").parent().parent().hide();
            } else {
                $("#emailset").parent().parent().show();
            }
        });
        $("[name=isbackupmidtable]").on("change",function(){
            if($(this).val() == 0){
                $("[name='backupscript']").parent().parent().hide();
            } else {
                $("[name='backupscript']").parent().parent().show();
            }
        });

        $("#synchro-original-datasource").on("change",function(){
            if($("#synchro-original-datasource").is(":checked")){
                $("#targetdsname").attr("readonly","readonly");
                $("#targetdsname").val($("#originaldsname").val());
                $("#targetid").val($("#originalid").val());
                $("#targetusername").val($("#originalusername").val());
            } else {
                $("#targetdsname").removeAttr("readonly");
            }
        });
        $("#extracted").find("input").each(function(){
            $(this).on("change",function(){
                isBuildScript = false;
            });
        });
        readyGroup();
    }

    /**
     * 编辑组信息
     */
    function group_edit_submit(){

        //验证组数据
        if(!validGroupForm()){
            return ;
        }
        removeSelectedDisabled();

        $.ajax({
            url:"${ctx}/migration/editGroupInfo",
            type:"post",
            data:$("#form-group-edit").serialize(),
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

</script>
</body>
</html>