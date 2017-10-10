function readyGroup(){
    $("#originaldsname").on("input",function(e){
        ajaxQueryDSName(e);
    });
    $("#originaldsname").blur(function(){
        setTimeout(function(){
            $("#originaldsname").next().hide();
        },100);
    });
    $("#originaldsname").on("focus",function(e){
        ajaxQueryDSName(e);
    });
    $("#targetdsname").on("input",function(e){
        ajaxQueryDSName(e);
    });
    $("#targetdsname").blur(function(){
        setTimeout(function(){
            $("#targetdsname").next().hide();
        },100);
    });
    $("#targetdsname").on("focus",function(e){
        if($(this).attr("readonly") == "readonly"){
            return ;
        }
        ajaxQueryDSName(e);
    });
}

/**
 * 生成提数脚本
 */
function buildNumberScript() {

    var midtablename = $("#midtablename").val();
    if(isBlank(midtablename)){
        layer.tips("请输入中间表名","#midtablename");
        return ;
    }
    var defaultcondition = $("#defaultcondition").val();
    if(isBlank(defaultcondition)){
        layer.tips("请输入默认迁移条件","#defaultcondition");
        return ;
    }
    var numberScript = "insert into " + midtablename + " select " + defaultcondition +
        " from 主表 where startTime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and startTime<to_date(?,'yyyy-mm-dd hh24:mi:ss')";
    $("#extractscript").val(numberScript);
    isBuildScript = true;
    $("#btn_save").removeClass("disabled");
}

/**
 * ajax模糊查询数据源
 * @param dsName
 * @param e
 */
function ajaxQueryDSName(e) {
    var dsName = $(e.target).val();
    if(isBlank(dsName)){
        $(e.target).next().hide();
        return ;
    }
    $.ajax({
        url : ctx+"/migration/ajaxQueryDSName",
        type : "post",
        data : {dsName:dsName},
        dataType : "json",
        success : function(result){
            if(result.code == 0){
                dmdatasources = result.dmdatasources;
                var liHtml = "";
                if(!!dmdatasources){
                    for(var i = 0; i< dmdatasources.length; i++){
                        liHtml += "<li>" + dmdatasources[0].dsname + "</li>";
                    }
                    $(e.target).next().find("ul").html(liHtml);
                    i = 0;
                    $(e.target).next().find("ul").children("li").each(function(){
                        $(this).click(function(){
                            selectDSName(e,i);
                            i++;
                        });
                    });
                    $(e.target).next().show();
                }
            }
        }
    });
}

/**
 * 选择数据源
 */
function selectDSName(e,i) {
    $(e.target).next().hide();
    if($(e.target).attr("name") == "originaldsname"){
        $(e.target).next().find("ul").html("");
        $("#originaldsid").val(dmdatasources[i].id);
        $("#originaldsname").val(dmdatasources[i].dsname);
        $("#originaldsusername").val(dmdatasources[i].username);

        if($("#synchro-original-datasource").is(":checked")){
            $("#targetdsid").val(dmdatasources[i].id);
            $("#targetdsname").val(dmdatasources[i].dsname);
            $("#targetdsusername").val(dmdatasources[i].username);
        }
    } else {
        $(e.target).next().find("ul").html("");
        $("#targetdsid").val(dmdatasources[i].id);
        $("#targetdsname").val(dmdatasources[i].dsname);
        $("#targetdsusername").val(dmdatasources[i].username);
    }
}

function validGroupForm(){

    if(isBlank($("#groupname").val())){
        layer.alert('请输入组名', {icon: 6});
        return false;
    }
    if(!isBlank($("#emailset").val())){
        var emailSet = $("#emailset").val().split(";");
        for(var i = 0; i < emailSet.length; i++){
            if(!isEmail(emailSet[i])){
                layer.alert('请输入正确的邮箱地址', {icon: 6});
                return false;
            }
        }
    }
    if(isBlank($("#originaldsid").val())){
        layer.alert('请选择原数据源', {icon: 6});
        return false;
    }
    if(!isBuildScript){
        layer.alert('请重新生成提数脚本', {icon: 6});
        return false;
    }
    return true;
}

function removeSelectedDisabled(){
    $("[name='type']").removeAttr("disabled");
    $("[name='isdataextracted']").removeAttr("disabled");
}


