<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>用户管理</title>
<style>
	#DataTable tr td:first-child, tr th:first-child {padding:0;}
	label.btn {border:0;text-align:center;text-indent:0;}
</style>
</head>
<body>
	
	<div class="page-container">
		<form name="form" id="form" class="form-horizontal" method="post">
			<div class="content-head">
				<div class="head-bar"> <!--  头部板块 -->
	              	<div class="head-title">
	                  	<h4>用户管理</h4>
	              	</div> 
	              	<div class="head-btns">
	                  	<ul class="nav nav-pills">
	                     	<li><button class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 查询</button></li>
	                     	<li><button type="reset" class="btn btn-warning">重置条件</button></li>
	                  	</ul>
	              	</div> <!--  头部按钮 -->
	          	</div> <!--  头部板块 end -->
			</div>
			<div class="content-body">
				<div class="query-condition">
					<form id="form" name="form" class="form-horizontal" role="form" method="post">
						<div class="row form-inline ">
							<div class="form-group col-sm-6 col-md-4">
								<label class="col-sm-4 form-label">用户编码:</label>
								<div class="col-sm-8">
									<input type="text" name="userCode" placeholder=" 用户编码" class="input-text size-M">
								</div>
							</div>
							<div class="form-group col-sm-6 col-md-4">
								<label class="col-sm-4 form-label">用户名称:</label>
								<div class="col-sm-8">
									<input type="text" name="userCode" placeholder=" 用户名称" class="input-text size-M">
								</div>
							</div>
						</div>
					</form>
	
				</div>
				<!--  头部板块 end -->
				<hr class="line-blue">
	 			<div class="table-btns">
				    <a class="btn btn-secondary size-S radius" onclick="openWin('add')">
				    <i class="Hui-iconfont">&#xe600;</i> 增加</a>
				    <a class="btn btn-secondary size-S radius" onclick="openWin('update')">
				    <i class="Hui-iconfont">&#xe6df;</i> 修改</a>
				    <a class="btn btn-danger size-S radius" onclick="deleteBussModule()">
				    <i class="Hui-iconfont">&#xe6e2;</i> 删除</a>
	            </div>
				<div class="query-result">
					<table id="DataTable" class="table table-border table-bordered table-bg table-hover table-sort" width="100%">
					    <thead>
						    <tr class="text-c">
						    	<th width="6%"><label class="btn"><input class="checkAll" name="" type="checkbox" value=""></label></th>
						    	<th>用户编码</th>
							    <th>用户名称</th>
							    <th>公司代码</th>
							    <th>手机号码</th>
							    <th>电子邮箱</th>
							    <th>密码设定时间</th>
							    <th>密码失效时间</th>
							    <th>创建时间</th>
							    <th>修改时间</th>
							    <th>有效状态</th>
						    </tr>
					    </thead>
					    <tbody class="text-c">
					    </tbody>
				    </table>
				</div>
			</div>
		</form>
	</div>
	
	
	<script type="text/javascript">
	
	$(function() {
		bindValidForm($('#form'), search);
		
		// 重置操作
		$("#resetBtn").on('click', function(){
			$(":input").val(this.defaultValue);
		});
		
		// 全选按钮
		$("#form").on("click", "input", function(){
			var $that = $(this);			
			if($that.attr('class') == 'checkAll'){
				$(":checkbox").each(function(){  						
					if($that.prop('checked') == true){
						this.checked = true;	 
					}else{
						this.checked = false;	   						
					}
				});
			}else{
				var count = 0;
				$(":checkbox[name='userCodes']").each(function(){
					if(this.checked == true){						
						count++; //统计input被选中的个数
					}
				});
				if(count > 0){
					$('input:checkbox[class="checkAll"]').prop('checked', true);
				}else{
					$('input:checkbox[class="checkAll"]').prop('checked', false);
				}
			}
	   	});
		
	});
	

	var columns = [ 
		{"data" : null}, //
		{"data" : "userCode"}, //用户编码
  		{"data" : "userName"}, //用户名称
  		{"data" : "comCode"}, //公司代码
  		{"data" : "mobileNo"}, //手机号码
  		{"data" : "email"}, //电子邮箱
  		{"data" : "pwdSetDate"}, //密码设定时间
  		{"data" : "pwdExpDate"}, //密码失效时间
  		{"data" : "createTime"}, //创建时间
  		{"data" : "updateTime"}, //修改时间
  		{"data" : "validFlag"} //有效状态
  	];
	
	function rowCallback(row, data, displayIndex, displayIndexFull) {
		$('td:eq(0)', row).html("<label class=\"btn\"><input name=\"userCodes\" type=\"checkbox\" value=\""+data.userCode+"\"></label>");
	}
	
	/*查询*/
	function search(){
		var ajaxList = new AjaxList("#DataTable");
		ajaxList.targetUrl = 'search.do';// + $("#form").serialize();
		ajaxList.postData = $("#form").serializeJson();
		ajaxList.rowCallback = rowCallback;
		ajaxList.columns = columns;
		ajaxList.query();
	}
	
	//打开新增或修改页面
	function openWin(saveType){
		var title,
		userCode="";
		if(saveType=="add"){
			title = "新增模块";
		}else{
			title = "修改模块";
			var $userCode = $("input:checkbox[name='userCodes']:checked");
			if($userCode.length==0){
				layer.msg("请选择需要修改的模块！");
				return;
			}else if($userCode.length>1){
				layer.msg("只能选择一个需要修改的模块！");
				return;
			}else{
				userCode = $userCode.val();
				console.log(userCode);
			}
		}
		
		var params = "?saveType="+saveType+"&userCode="+userCode;
		var url = "openEditWin.do";
		var index = layer.open({
			type: 2,
			area: ['700px', '390px'],
			skin: 'layui-layer-rim', //加上边框
			title: title,
			content: url+params
		});
		//layer.full(index);
	}
	
	//删除业务模块
	function deleteBussModule(){
		
		var $userCode = $("input:checkbox[name='userCodes']:checked");
		if($userCode.length==0){
			layer.msg("请选择需要删除的用户！");
			return;
		}else{
			layer.confirm('是否删除选中项？', {
				 	btn: ['确定','取消'] //按钮
				}, function(){
					var users = [];
					$userCode.each(function(i,o){
						users.push($(this).val());
					});
					var params = {"userCodes" : users};
					$.ajax({
						url : "delete.do",
						type : "post",
						data : params,
						async : false,
						success : function(result) {
							layer.msg('删除成功');
							$(":checkbox[name='userCodes']").each(function(){
								if(this.checked == true){						
									$(this).parents('tr').remove();
									$('input:checkbox[name="checkAll"]').prop('checked', false);
								}
							});
						}
					});
				});
			
			
		}
		
	}

	</script>
</body>
</html>
