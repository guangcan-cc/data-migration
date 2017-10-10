<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/layout/common/taglib.jspf"%>

<style>
	.container {position:absolute;width:100%;top:40px;bottom:0;padding:0;}
	.table .select-box {width:64%;height:39px;border:solid 2px #eee;border-style:inset;background:#fff;}
</style>

<table id="DataTable" class="table table-border table-bg table-sort tabForm" width="100%">
    <tbody class="text-c">
    	<tr class="text-c">
    		<td class="col-xs-6"><label class="col-xs-4"><font class="c-red">*</font>岗位编码:</label>
    			<input type="hidden" name="saaGradeVo.id" value="${gradeVo.id}">
    			<input type="text" class="col-xs-8 iText" datatype="*" name="saaGradeVo.gradeCode" value="${gradeVo.gradeCode}">
    		</td>
    		<td class="col-xs-6">
    			<label class="col-xs-4"><font class="c-red">*</font>岗位名称:</label>
    			<input type="text" class="col-xs-8 iText" datatype="*" name="saaGradeVo.gradeCname" value="${gradeVo.gradeCname}">
    		</td>
    	</tr>
    	<tr class="text-c">
    		<td class="col-xs-6">
    			<label class="col-xs-4"><font class="c-red">*</font>公司编码:</label>
    			<input type="text" class="col-xs-8 iText" datatype="*" name="saaGradeVo.comCode" value="${gradeVo.comCode}">
    		</td>
    		<td class="col-xs-6">
    			<label class="col-xs-4">是否有效:</label>
				<select name="saaGradeVo.validFlag" class="col-xs-8 select-s" value="${gradeVo.validFlag}">
					<option value="1">有效</option>
					<option value="0">无效</option>
				</select>
			</td>
    	</tr>
    	<tr class="text-c">
    		<td class="col-xs-6">
    			<label class="col-xs-4">创建人:</label>
    			<input type="text" class="col-xs-8 iText" name="saaGradeVo.createUser" value="${gradeVo.createUser}" disabled>
    			</td>
    		<td class="col-xs-6">
	    		<c:set var="createTime"><fmt:formatDate value="${gradeVo.createTime}" pattern="yyyy-MM-dd HH:mm"/></c:set>
    			<label class="col-xs-4">创建时间:</label>
    			<input type="text" class="col-xs-8 iText" name="createTime" value="${createTime}" disabled>
    		</td>
    	</tr>
    	<tr class="text-c">
    		<td colspan="2" class="col-xs-12">
    			<label class="col-xs-2">备注:</label>
    			<input type="text" class="col-xs-10 iText" name="saaGradeVo.remark" value="${gradeVo.remark}">
    		</td>
    	</tr>
    </tbody>
</table>
<div class="cl bg-1 bk-gray">
    <span class="l title">岗位功能权限</span>
</div>
<div class="zTreeDemoBackground">
	<ul id="treeDemo" class="ztree" style="width:100%;height:100%;overflow:auto;"></ul>
	<div>
	 	<c:forEach var="nodeVo" items="${nodeVoList}" varStatus="status">
		 	<input type="hidden" name="saaGradeTasks[${status.index}].id" value="${nodeVo.gradeTaskId }">
		 	<input type="hidden" name="saaGradeTasks[${status.index}].gradeId" value="${nodeVo.gradeId }">
		 	<input type="hidden" name="saaGradeTasks[${status.index}].taskId" class="iptVal" value="${nodeVo.taskId }">
		 	<input type="hidden" name="saaGradeTasks[${status.index}].flag" value="${nodeVo.checked?1:0 }">

	 	</c:forEach>
	</div>
</div>

