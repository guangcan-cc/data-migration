<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/layout/common/taglib.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>岗位权限管理</title>
<style>
	section.content {width:100%;position:absolute;height:100%;}
	.container {position:absolute;width:100%;top:42px;bottom:0;padding:0;}
	#treeDemo {padding:0;} .ztree {padding:0;}
	.postPower.container .l.text{color:#333;font-size:14px;font-weight:600;line-height:31px;}
	.postPower .active,.postPower .ul-hover .active:hover {background:#ccc;color:#000;}
	.postPower .postList {position:absolute;top:39px;width:100%;bottom:0;overflow-y:scroll;}
	.postPower .postList li {font-size:14px;cursor:pointer;padding:6px 0;text-indent:20px;}
	.zTreeDemoBackground {position:absolute;left:0;right:0;top:204px;bottom:0;padding:10px 0 10px 10px;}
	.table .select-box {height:39px;border:solid 2px #eee;border-style:inset;background:#fff;}
</style>
</head>
<body>
	<form name="form" id="form" class="form-horizontal sectionBody" method="post">
	    <div class="cl pd-5 bg-color2 bk-gray">
	    	<span class="l text">岗位权限管理</span>
		    <span class="r">
			    <a class="btn btn-sm radius" onclick="save()"><i class="Hui-iconfont">&#xe632;</i> 保存配置</a> 
		    </span>
	    </div>
	    <div class="container postPower">
	    	<div class="pull-left col-xs-3 h-b-p">
	    		<div class="cl bg-1 bk-gray">
	    			<span class="l title"> 岗位列表</span>
	    			<span class="r">
		    			<a id="addPost" class="btn btn-sm radius" onclick="submitMessage('add', '')">
		    				<i class="Hui-iconfont">&#xe717;</i> 添加岗位
		    			</a> 
	    			</span>
	    		</div>
	    		<ul class="postList ul-hover ul-hshadow ul-border">
	    			<c:forEach var="gradeVoList" items="${gradeVos}" varStatus="status">
		    			<li onclick="submitMessage('modify', '${gradeVoList.id}')" data-gid="${gradeVoList.id}" <c:if test="${status.index=='0'}">class="active"</c:if>>${gradeVoList.gradeCname}</li>
			    	</c:forEach>
	    		</ul>
	    	</div>
	    	<div class="pull-right col-xs-9 h-b-p">
	    		 <%@ include file="SaaGrade_Edit.jspf" %>
	    	</div>
	    </div>
	</form>
	<script type="text/javascript">
	
	$(function() {
				
		// 岗位选择连动
		$('.postList').on('click', 'li', function(){
			$(this).addClass('active').siblings().removeClass('active');
		});
		
		//初始化ztree 
		setZtree($('ul.postList li:first-child').data('gid'));
		
	});
	
	function zTreeOnCheck(event, treeId, treeNode) {
	    // console.log(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked + "," + treeNode.key);
	    var nodes = zTree.getCheckedNodes(true);//获取ztree中checked勾选的节点
	    for(var i=0; i<nodes.length; i++){
			$('.iptVal').each(function(){
				if(this.value == nodes[i].key){
					var reg = /\d+/g,
						thisName = this.getAttribute('name'),
						result = reg.exec(thisName),
						valsT = "saaGradeTasks[" + result + "].flag";
					$('input[name=\"'+valsT+'\"]').val('1');
					return false; // break----用return false; continue --用return ture;
				}
			});
		}
	    var nodes2 = zTree.getCheckedNodes(false);//获取ztree中checked未被勾选的节点
	    for(var i=0; i<nodes2.length; i++){
			$('.iptVal').each(function(){
				if(this.value == nodes2[i].key){
					var reg = /\d+/g,
						thisName = this.getAttribute('name'),
						result = reg.exec(thisName),
						valsT = "saaGradeTasks[" + result + "].flag";
					$('input[name=\"'+valsT+'\"]').val('0');
					return false; // break----用return false; continue --用return ture;
				}
			});
		}
	};
	// 生成ztree
	var ztree, zNodes;
	function setZtree(gId){
		// ztree
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onCheck: zTreeOnCheck
			}
		};
		$.ajax({
			async : false,
			cache:false,
			type: 'POST',
			data: {gradeId: gId},
			dataType : "json",
			url: "getTreeNodes.ajax",//请求的action路径
			error: function () {//请求失败处理函数
				layer.msg("请求失败！");
			},
			success:function(data){ //请求成功后处理函数。 
				zNodes = data; //把后台封装好的简单Json格式赋给treeNodes
			}
		});
		var t = $("#treeDemo");
		t = $.fn.zTree.init(t, setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
	}
	
	//添加和修改
	function submitMessage(type, gradeid){
		$.ajax({
			async : false,
			cache:false,
			type: 'POST',
			data: {
				saveType: type,
				gradeId: gradeid
			},
			dataType : "html",
			url: "addOrModify.do",//请求的action路径
			error: function () {//请求失败处理函数
				layer.msg("操作失败！");
			},
			success:function(data){ //请求成功后处理函数。 
				$('.postList li').removeClass('active');
				$('#form .pull-right').html('').append(data);
				setZtree(gradeid);
			}
		});
	}
	
	// 保存
	function save(){
		/* // 修改所有被选中的ztree节点的value
		$('.iptCheVal').each(function(){ // 将ztree对应的所有input值归0
			this.value = 0;
		});
 		var nodes = zTree.getCheckedNodes(true);//获取ztree中checked勾选的节点
		for(var i=0; i<nodes.length; i++){
			$('.iptVal').each(function(){
				if(this.value == nodes[i].key){
					var reg = /\d+/g,
						thisName = this.getAttribute('name'),
						result = reg.exec(thisName),
						valsT = "saaGradeTasks[" + result + "].flag";
					$('input[name=\"'+valsT+'\"]').val('1');
					return false;
				}
			});
		} */
			
		//绑定表单
		var ajaxEdit = new AjaxEdit($('#form'));
		ajaxEdit.targetUrl = "saveGrade.do";
		//ajaxEdit.rules = rule;
		ajaxEdit.beforeSubmit = function(data) {};
		ajaxEdit.afterSuccess = function(data) {
			if(data.status == "200"){
				layer.alert('保存成功', {
					  icon: 1,
					  skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
					});
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
