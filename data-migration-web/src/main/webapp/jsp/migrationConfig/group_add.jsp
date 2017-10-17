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
    <form class="form form-horizontal" id="form-group-add">
        <div class="row cl" style="margin-top: 0px;">
            <div class="inline">
                <label class="form-label col-sm-2"><strong>组ID：</strong></label>
                <div class="formControls col-xs-3">
                    <input type="text" readonly="readonly" class="input-text radius" value="" name="id" style="border: none;"/>
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><strong>是否发送邮件：</strong></label>
                <div class="formControls col-xs-3">
                    <select name="issendemail" class="select-default radius" style="height: 30px;">
                        <option value="1">是</option>
                        <option value="0" selected="selected">否</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2"><span class="c-red">*</span><strong>组名：</strong></label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" value="" name="groupname" id="groupname">
                </div>
            </div>
            <div class="inline" style="display: none;">
                <label class="form-label col-xs-4 col-sm-2"><strong>邮箱列表：</strong></label>
                <div class="formControls col-xs-3">
                    <input type="text" class="input-text radius" placeholder="多个邮箱地址以英文 ; 分割" value="" name="emailset" id="emailset">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>组类型：</strong></label>
                <div class="formControls col-xs-3">
                    <select name="type" class="select-default radius" disabled="disabled">
                        <option value="0" selected="selected">数据迁移</option>
                        <option value="1">数据校验</option>
                    </select>
                </div>
            </div>
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><strong>组说明：</strong></label>
                <div class="formControls col-xs-3">
                    <textarea class="textarea radius" name="explain"></textarea>
                </div>
            </div>
        </div>
        <hr class="mt-10">
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><strong>原数据源：</strong></label>
                <div class="formControls col-xs-3" style="width: 20%;">
                    <input type="hidden" name="originaldsid" id="originaldsid">
                    <input type="text" class="input-text radius" value="" name="originaldsname" id="originaldsname">
                    <div class="auto-query" style="display: none;">
                        <ul>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="inline">
                <div class="formControls col-sm-1" style="width: 10%;padding: 0;padding-left: 25px;margin-left: 21px;">
                    <input type="checkbox" class="check-box" checked="checked" value="1" name="issynchroorisource" id="synchro-original-datasource">
                    <label for="synchro-original-datasource">同原数据源</label>
                </div>
                <div class="formControls col-xs-4" style="">
                    <label><strong>目标数据源：</strong></label>
                    <input type="hidden" name="targetdsid" id="targetdsid">
                    <input style="width:57%;margin-left:25px;" type="text" readonly="readonly" class="input-text radius" value="" name="targetdsname" id="targetdsname">
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
                    <input type="text" readonly="readonly" style="border: none;" class="input-text radius" value="" name="originaldsusername" id="originaldsusername">
                </div>
            </div>
            <div class="inline">
                <div class="formControls col-sm-1" style="width: 10%;padding: 0;padding-left: 25px;margin-left: 21px;">
                </div>
                <div class="formControls col-xs-4" style="">
                    <label style="margin-left: 13px;"><strong>目标用户：</strong></label>
                    <input style="width:57%;margin-left:25px;border: none;" readonly="readonly" type="text" class="input-text radius" value="" name="targetdsusername" id="targetdsusername">
                </div>
            </div>
        </div>
        <hr class="mt-10">
        <div class="row cl">
            <div class="inline">
                <label class="form-label col-sm-2"><strong>是否提数：</strong></label>
                <div class="formControls col-xs-3">
                    <select name="isdataextracted" id="isdataextracted" class="select-default radius" style="height: 30px;" disabled="disabled">
                        <option value="1" selected="selected">是</option>
                        <option value="0">否</option>
                    </select>
                </div>
            </div>
        </div>
        <div id="extracted">
            <div class="row cl">
                <div class="inline">
                    <label class="form-label col-xs-4 col-sm-2"><strong>中间表名：</strong></label>
                    <div class="formControls col-xs-3">
                        <input type="text" class="input-text radius" value="" name="midtablename" id="midtablename">
                    </div>
                </div>
                <div class="inline">
                    <label class="form-label col-xs-4 col-sm-2"><strong>迁移默认条件：</strong></label>
                    <div class="formControls col-xs-3">
                        <input type="text" class="input-text radius" value="" name="defaultcondition" id="defaultcondition">
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
                            <option value="1">是</option>
                            <option value="0" selected="selected">否</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row cl">
                <div class="inline">
                    <label class="form-label col-xs-4 col-sm-2"><strong>提数脚本：</strong></label>
                    <div class="formControls col-xs-3">
                        <textarea class="textarea radius" name="extractscript" id="extractscript"></textarea>
                    </div>
                </div>
                <div class="inline" style="display: none;">
                    <label class="form-label col-xs-4 col-sm-2"><strong>备份脚本：</strong></label>
                    <div class="formControls col-xs-3">
                        <textarea class="textarea radius" name="backupscript"></textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="row cl" style="margin-top: 30px;">
            <div class="col-xs-8 col-xs-offset-4">
                <button id="btn_save" onClick="group_save_submit();" class="btn btn-primary radius disabled" type="button"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
                <button onClick="article_save();" class="btn btn-secondary radius" type="reset"><i class="Hui-iconfont">&#xe632;</i> 重置</button>
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
    var isBuildScript = false;
    var dmdatasources;

    window.onload = function(){
        $("[name='id']").val("G" + new Date().getTime());
        var groupname = $("#groupname");
        groupname.on("change",function(){
            if(isBlank(groupname.val())){
                layer.tips('请输入用户名', '#groupname');
            }
        });
        $("#isdataextracted").on("change",function(){
            if($("#isdataextracted").val() == 0){
                $("#extracted").hide();
                $("#btn_save").removeClass("disabled");
                isBuildScript = true;
            } else {
                $("#extracted").show();
                $("#btn_save").addClass("disabled");
            }
        });
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
                $("#targetdsid").val($("#originaldsid").val());
                $("#targetdsusername").val($("#originaldsusername").val());
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
     * 保存组信息
     */
    function group_save_submit(){
        //验证组数据
        if(!validGroupForm()){
            return ;
        }
        //移除不可编辑的下拉选项
        removeSelectedDisabled();
        $.ajax({
            url:"${ctx}/migration/addGroupInfo",
            type:"post",
            data:$("#form-group-add").serialize(),
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