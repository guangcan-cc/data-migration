<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/layout/common/taglib.jspf"%>

<style>
	.Wdate {width:100%;}
</style>

	<div class="page-container">
		<form name="fm" id="form" class="form-horizontal" role="form">
			<div class="content-body">
				<div class="row form-inline ">
					<div class="form-group col-xs-12 col-md-12">
						<label class="col-xs-3 form-label"><em class="input-must">*</em>用户编码:</label>
						<div class="col-xs-4">
							<input type="text" class="input-text size-M <c:if test="${saveType=='update'}">disabled</c:if>" <c:if test="${saveType=='update'}">readonly="readonly"</c:if> datatype="*" name="userCode" value="${saaUserVo.userCode}">
						</div>
						<div class="col-xs-5 form-text">
							
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-12">
						<label class="col-xs-3 form-label"><em class="input-must">*</em>用户名称:</label>
						<div class="col-xs-4">
							<input type="text" class="input-text size-M" datatype="*" name="userName" value="${saaUserVo.userName}">
						</div>
						<div class="col-xs-5 form-text">
							
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-12">
						<label class="col-xs-3 form-label"><c:if test="${saveType=='add' }"><em class="input-must">*</em></c:if> 密码:</label>
						<div class="col-xs-4 ">
							<input type="text" class="input-text size-M" <c:if test="${saveType=='add' }">datatype="*"</c:if> name="password">
						</div>
						<div class="col-xs-5 form-text">
							<c:if test="${saveType=='update' }"> (为空默认为原始密码)</c:if>
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-12">
						<label class="col-xs-3 form-label">邮箱:</label>
						<div class="col-xs-4">
							<input type="text" class="input-text size-M" datatype="e" ignore="ignore" name="email" value="${saaUserVo.email}">
						</div>
						<div class="col-xs-5 form-text">
							
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-12">
						<label class="col-xs-3 form-label">手机号:</label>
						<div class="col-xs-4">
							<input type="text" class="input-text size-M" datatype="m" ignore="ignore" name="mobileNo" value="${saaUserVo.mobileNo}">
						</div>
						<div class="col-xs-5 form-text">
							
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-12">
						<label class="col-xs-3 form-label">公司代码:</label>
						<div class="col-xs-4">
							<input type="text" class="input-text size-M" name="comCode" value="${saaUserVo.comCode}">
						</div>
						<div class="col-xs-5 form-text">
							
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-12">
						<label class="col-xs-3 form-label">密码设定时间:</label>
						<div class="col-xs-4">
							<input type="text" class="input-text size-M Wdate" name="pwdSetDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'logmin\')||\'%y-%M-%d\'}',maxDate:'#F{$dp.$D(\'logmax\')}'})" id="logmin" value="${saaUserVo.pwdSetDate}">
						</div>
						<div class="col-xs-5 form-text">
							
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-12">
						<label class="col-xs-3 form-label">密码失效时间:</label>
						<div class="col-xs-4">
							<input type="text" class="input-text size-M Wdate" name="pwdExpDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'logmin\')}',minDate:'%y-%M-%d'})" id="logmax" value="${saaUserVo.pwdExpDate}">
						</div>
						<div class="col-xs-5 form-text">
							
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-12">
						<label class="col-xs-3 form-label"><em class="input-must">*</em>有效性:</label>
						<div class="col-xs-4 form-input" >
	 						<label ><input type="radio" name="validFlag" value="1" <c:if test='${saaUserVo.validFlag!=0}'>checked</c:if>>有效</label>
							<label ><input type="radio" name="validFlag" value="0" <c:if test='${saaUserVo.validFlag==0}'>checked</c:if>>无效</label>
						</div>
						<div class="col-xs-5 form-text">
							
						</div>
					</div>
				</div>
			</div>
			<div class="content-footer">
				<div class="layer-btnbar">
					 <button class="btn btn-primary" type="submit" onclick="save()"><i class="Hui-iconfont">&#xe632;</i>保存</button> 
					 <button type="button" class="btn btn-default"   onclick="closeLayer()" ><i class="Hui-iconfont">&#xe6a6;</i>关闭</button>
				</div>
			</div>
		</form>
	</div>
	
	<script type="text/javascript">
	
	/* function save(){
		
		$.ajax({
			async : false,
			cache:false,
			type: 'POST',
			data: $("#form").serialize(),// 你的formid(form序列化)
			dataType : "json",
			url : "save.do", //请求的action路径
			error: function () { //请求失败处理函数
				layer.msg("保存失败！");
			},
			success:function(data){ //请求成功后处理函数。 
				if(data.status == "200"){					
					layer.msg("保存成功！");
				}
			}
		});
	} */
	
	$(function(){
		
		//绑定表单
		var ajaxEdit = new AjaxEdit($('#form'));
		ajaxEdit.targetUrl = "save.do";
		//ajaxEdit.rules = rule;
		ajaxEdit.beforeSubmit = function(data) {};
		ajaxEdit.afterSuccess = function(data) {
			if(data.status == "200"){
				// 父页面重新查询
				parent.search();
				layer.confirm('保存成功！', {
					  	btn: ['确定'] //按钮
					}, function(){
					    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					    parent.layer.close(index); //再执行关闭 
					});
				//layer.msg("保存成功！");
			}else{
				layer.msg("保存失败！");
			}

		};
		// 绑定表单
		ajaxEdit.bindForm();
    })
    
    function save(){
    	$("#form").submit();
    }
	
	</script>
