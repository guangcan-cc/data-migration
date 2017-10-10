<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/layout/common/taglib.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>用户权限管理</title>
<style>
	section.content {width:100%;position:absolute;height:100%;}
	.container {position:absolute;width:100%;top:43px;bottom:0;padding:0;}
	#userList {position:absolute;width:100%;top:86px;bottom:0;}
	#userList ul {position:absolute;width:100%;top:0;bottom:0;overflow-y:scroll;}
	#userList li {height:40px;}
	#userList li .btn [name=options] {margin-right:10px;}
	#DataTable td {padding:0;height:37px;line-height:35px;}
	#DataTable label.btn {border:0;text-align:center;text-indent:0;}
	.userTabMessage tbody td {padding:0;height:40px;border-bottom: 5px solid #225aa5;background:#ddd;line-height:39px;}
	.userTabMessage label {float:left;height:39px;width:34%;text-align:right;font-size:14px;font-weight:600;}
	.userTabMessage input {float:right;height:30px;width:64%;font-size:15px;margin-top:3px;}
	.userTabMessage.tab1 label {width:17%;}
	.userTabMessage.tab1 input {width: 35%;float:left;margin-left:2%;}
	.userTabMessage.tab2 label {width:17%;}
	.userTabMessage.tab2 input {width: 32%;float:left;margin-left:2%;}
	.tabContainer {position:absolute;width:100%;top:86px;bottom:0;overflow-y:scroll;}
</style>
</head>
<body>
    <div class="container userPo">
    	<div class="pull-left col-xs-3 h-b-p">
    		<div class="cl pd-5 bg-color2">
    			<span class="l text">用户查询</span>
    			<span class="r">
	    			<a class="btn btn-sm radius" onclick="searchUser()"><i class="Hui-iconfont">&#xe665;</i> 查询</a> 
    			</span>
    		</div>
    		<table class="table table-border table-bg table-sort userTabMessage tab1" width="100%">
			    <tbody class="text-c">
			    	<tr class="text-c">
			    		<td>
			    			<label>用户:</label><input type="text" name="userCode" placeholder=" 用户代码">
			    			<span style="float:left;margin-left:2%;width:2%;">-</span>
			    			<input type="text" name="userName" placeholder=" 用户名称">
			    		</td>
			    	</tr>
			    </tbody>
		    </table>
		    <div id="userList">
		    	<ul class="ul-oddEven">
		    		<c:forEach var="userVo" items="${userVos}" varStatus="status">
		    			<li><label class="btn"><input type="radio" name="options" onclick="linkage('${userVo.userCode}')" <c:if test="${status.index=='0'}">checked</c:if>>${userVo.userName}</label></li>
			    	</c:forEach>
		    	</ul>
		    </div>
		</div>
    	<div class="pull-right col-xs-9 h-b-p userMessage">
	    	<form name="form" id="form" class="form-horizontal sectionBody" method="post">
		   		<div class="cl pd-5 bg-color2">
		   			<span class="l text">用户权限配置</span>
		   			<span class="r">
		    			<a class="btn btn-sm radius" onclick="save()"><i class="Hui-iconfont">&#xe632;</i> 保存</a> 
		   			</span>
		   		</div>
		   		<table class="table table-border table-bg table-sort userTabMessage tab2" width="100%">
				    <tbody class="text-c">
				    	<tr class="text-c">
				    		<td><label>用户:</label><input type="text" name="userMessage" disabled style="color:#333;" value=" ${userCode}"></td>
				    	</tr>
				    </tbody>
			    </table>
			    <div class="tabContainer">
				    <table id="DataTable" class="table table-border table-bordered table-bg table-hover table-striped table-sort">
					    <thead>
						    <tr class="text-c">
							    <th width="15%">岗位代码</th>
							    <th width="28%">岗位名称</th>
							    <th width="7%">授权</th>
							    <th width="18%">生效日期</th>
							    <th width="18%">失效日期</th>
							    <th width="14%">创建人</th>
						    </tr>
					    </thead>
					    <tbody class="text-c">
					    	<c:forEach var="gradeVo" items="${gradeVos}" varStatus="status">
						    	<tr>
						    		<input name="gradeVo[${status.index}].id" type="hidden" value="${gradeVo.id}">
						    		<td>
						    			<input name="gradeVo[${status.index}].gradeCode" type="hidden" value="${gradeVo.gradeCode}">${gradeVo.gradeCode}
						    		</td>
						    		<td>
						    			<input name="gradeVo[${status.index}].gradeCname" type="hidden" value="${gradeVo.gradeCname}">${gradeVo.gradeCname}
						    		</td>
						    		
						    		<c:forEach var="userGrade" items="${gradeVo.saaUserGrades }">
						    			<c:if test="${userGrade.userCode==userCode }">
						    				<c:set var="userGradeVo" value="${userGrade}"></c:set>
						    			</c:if>
						    		</c:forEach>
						    		<input name="gradeVo[${status.index}].saaUserGrades[0].id" type="hidden" value="${userGradeVo.id}">
						    		<input name="gradeVo[${status.index}].saaUserGrades[0].userCode" type="hidden" value="${userCode}">
						    		<input name="gradeVo[${status.index}].saaUserGrades[0].comCode" type="hidden" value="00000000" value="${userGradeVo.comCode}">
						    		<input name="gradeVo[${status.index}].saaUserGrades[0].grantWay" value="${userGradeVo.grantWay}" type="hidden">
						    		<input name="gradeVo[${status.index}].saaUserGrades[0].validFlag" value="${userGradeVo.validFlag}" type="hidden">
						    		
						    		<td>
						    			<label class="btn"><input type="checkbox" name="gradeVo[${status.index}].saaUserGrades[0].validFlag" <c:if test="${userGradeVo.validFlag=='1'}">checked</c:if> value="${userGradeVo.validFlag}"></label>
						    		</td>
						    		<td><c:set var="validDate"><fmt:formatDate value="${userGradeVo.validDate}" pattern="yyyy-MM-dd"/></c:set>
						    			<input type="text" name="gradeVo[${status.index}].saaUserGrades[0].validDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'logmin${status.index}\')||\'%y-%M-%d\'}',maxDate:'#F{$dp.$D(\'logmax${status.index}\')}'})" id="logmin${status.index}" class="input-text Wdate" value="${validDate}">
						    		</td>
						    		<td><c:set var="invalidDate"><fmt:formatDate value="${userGradeVo.invalidDate}" pattern="yyyy-MM-dd"/></c:set>
						    			<input type="text" name="gradeVo[${status.index}].saaUserGrades[0].invalidDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'logmin${status.index}\')}',minDate:'%y-%M-%d'})" id="logmax${status.index}" class="input-text Wdate" value="${invalidDate}">
						    		</td>
						    		<td>
						    			<input type="hidden" name="gradeVo[${status.index}].saaUserGrades[0].createUser" type="hidden" value="${userGradeVo.createUser}">${userGradeVo.createUser}
						    		</td>
								</tr>
							</c:forEach>
					    </tbody>
				    </table>
			    </div>
			</form>
    	</div>
    </div>
    <script type="text/javascript" src="${ctx}/admin/js/AjaxEdit.js"></script>
	<script type="text/javascript">
	
	$(function() {
		$('#form').on('click', 'input:checkbox',function(){
			var tsName = this.getAttribute('name');
			if(this.checked){
				$('input[name=\"'+tsName+'\"]').each(function(){
					this.value = 1;
				});
			}else{
				$('input[name=\"'+tsName+'\"]').each(function(){
					this.value = 0;
				});
			}
		});
	});
	
	// 搜索用户
	function searchUser(){
		$.ajax({
			async : false,
			cache:false,
			type: 'POST',
			data: {
				userCode: $('input[name="userCode"]').val(),
				userName: $('input[name="userName"]').val()
			},
			dataType : "html",
			url : "getUsers.ajax", //请求的action路径
			error: function () { //请求失败处理函数
				layer.msg("搜索失败！");
			},
			success:function(data){ //请求成功后处理函数。 
				$('#userList').html('').append(data);
				linkage($(':checked').val());
			}
		});
	}
	
	// 选中用户时联动权限表
	function linkage(uCode){
		//layer.load(2);
		$.ajax({
			async : false,
			cache:false,
			type: 'POST',
			data: {
				userCode: uCode
			},
			dataType : "html",
			url : "getGrades.ajax", //请求的action路径
			beforeSend: function(XMLHttpRequest){
				layer.load(); //打开loading
			},
			complete: function(XMLHttpRequest, textStatus){
				layer.closeAll('loading'); //关闭loading
			},
			error: function () { //请求失败处理函数
				layer.msg("数据获取失败！");
			},
			success:function(data){ //请求成功后处理函数。
				$('#form').html('').append(data);
			}
		});
	}
	
	// 保存操作
	function save(){
		/*layer.load(2);
		$.ajax({
			async : false,
			cache:false,
			type: 'POST',
			data: $("#form").serialize(), // 你的formid(form序列化)
			dataType : "json",
			url : "save.do", //请求的action路径
			error: function () { //请求失败处理函数
				layer.closeAll('loading');
				layer.msg("保存失败！");
			},
			success:function(data){ //请求成功后处理函数。
				layer.closeAll('loading');
				layer.msg("保存成功！");
			}
		});*/
		//绑定表单
		var ajaxEdit = new AjaxEdit($('#form'));
		ajaxEdit.targetUrl = "save.do";
		//ajaxEdit.rules = rule;
		ajaxEdit.beforeSubmit = function(data) {};
		ajaxEdit.afterSuccess = function(data) {
			if(data.status == "200"){
				layer.alert('保存成功', {
					  icon: 1,
					  skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
					})
				//layer.msg("保存成功！");
			}else{
				layer.msg("保存失败！");
			}

		};
		// 绑定表单
		ajaxEdit.bindForm();
		$("#form").submit();
	}
	
	</script>
</body>
</html>
