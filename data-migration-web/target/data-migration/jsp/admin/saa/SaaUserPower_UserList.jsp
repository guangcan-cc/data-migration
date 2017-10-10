<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/layout/common/taglib.jspf"%>

<ul class="ul-oddEven">
  	<c:forEach var="userVo" items="${userVos}" varStatus="status">
  		<li><label class="btn"><input type="radio" name="options" onclick="linkage('${userVo.userCode}')" <c:if test="${status.index=='0'}">checked</c:if> value="${userVo.userCode}">${userVo.userName}</label></li>
   	</c:forEach>
</ul>
