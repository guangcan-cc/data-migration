<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/layout/common/taglib.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>系统初始化参数配置</title>
</head>
<body>
	<div class="page-container" style="padding:20px;">
		<form name="form" id="form" class="form-horizontal" method="post" action="save.do">
	
		<div class="content-head">
				<div class="head-bar"> <!--  头部板块 -->
		             <div class="head-title">
		                 <h4>系统初始化参数配置</h4>
		             </div> 
		             <div class="head-btns">
		                 <ul class="nav nav-pills">
		                    <li><button type="submit" class="btn btn-success"><i class="Hui-iconfont">&#xe632;</i> 保存配置</button></li>
		                 </ul>
		             </div> <!--  头部按钮 -->
		             <div class="head-errorMsg">${msg }</div>
		         </div> <!--  头部板块 end -->
		</div>
		<div class="content-body">	
			    <table id="DataTable" class="table table-border table-bordered table-bg table-hover table-sort" width="100%">
				    <thead>
					    <tr class="text-c">
						    <th width="20%">参数代码</th>
						    <th width="20%">参数名称</th>
						    <th width="30%">参数值</th>
						    <th width="30%">参数配置说明</th>
					    </tr>
				    </thead>
				    <tbody  >
				    <c:forEach var="configVo" items="${configList }"  varStatus="status" >
				        <tr>
						    <td align="left">
						    	${configVo.cfgKey }
						    	<input type="hidden" name="configVos[${status.index }].id" value="${configVo.id }">
						    </td>
						    <td align="left">${configVo.cfgName }</td>
						    <td style="padding: 4px;"><input type="text" name="configVos[${status.index }].cfgValue" value="${configVo.cfgValue }"  class="input-text" style="padding: 2px" > </th>
						    <td align="left">${configVo.cfgDesc }</td>
					    </tr>
				    </c:forEach>
				    </tbody>
			    </table>
		 </div>
		</form>
		<div class="content-footer">
			<div class="desc">
				(R)标记表示需要重启后才能生效
			</div>	
		</div>
	</div>
	<script type="text/javascript">
	$(function() {
		$("#form").on("submit", function(){
			layer.load(1);
		});
	});
	</script>
</body>
</html>
