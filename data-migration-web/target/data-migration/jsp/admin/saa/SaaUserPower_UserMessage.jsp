<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/layout/common/taglib.jspf"%>

	<div class="cl pd-5 bg-color2">
		<span class="l text">用户权限配置</span>
		<span class="r">
 		<a class="btn btn-sm radius" onclick="save()"><i class="Hui-iconfont">&#xe632;</i> 保存</a> 
		</span>
	</div>
  	<table class="userTabMessage tab2" class="table table-border table-bg table-sort" width="100%">
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
