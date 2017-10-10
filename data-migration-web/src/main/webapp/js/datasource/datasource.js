/**
 * Created by Denny on 2017/9/6.
 */
function checkForm(){
    if($("#dsName").val() == ""){
        layer.tips('请输入数据源名', '#dsName');
        return false;
    }
    if($("#username").val() == ""){
        layer.tips('请输入用户名', '#username');
        return false;
    }
    if(isBlank($("#password").val())){
        layer.tips('请输入密码', '#password');
        return false;
    }
    if($("#serverip").val() == ""){
        layer.tips('请输入服务器地址', '#serverip');
        return false;
    }
    if($("#port").val() == ""){
        layer.tips('请输入端口号', '#port');
        return false;
    }
    if($("#servername").val() == ""){
        layer.tips('请输入服务名称', '#servername');
        return false;
    }
    return true;
}


