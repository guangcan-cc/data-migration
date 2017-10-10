<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/common/taglib.jspf" %>
<%@page isErrorPage="true"%>
<!DOCTYPE html>
<head>
<title>异常500</title>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap.css" >
<link rel="stylesheet" type="text/css" href="${ctx}/plugins/Font-Awesome/css/font-awesome.css" />
<style>
<!--
H1 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 22px;
}

H2 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 16px;
}

H3 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 14px;
}

BODY {
	font-family: Tahoma, Arial, sans-serif;
	color: black;
	background-color: white;
}

td.tname {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	white-space: nowrap;
	font-weight: bold;
	text-align: right;
}

P {
	font-family: Tahoma, Arial, sans-serif;
	background: white;
	color: black;
	font-size: 12px;
}

A {
	color: black;
}

A.name {
	color: black;
}

HR {
	color: #525D76;
}


	*{
			margin: 0;
			padding: 0;
		}
		body{
			background: #f0f0f0;
		}
		.Container500{
			width: 722px;
			padding: 50px;
			margin: 100px auto;
			background: #fcfcfc;
			border: 1px solid #b3b3b3;
		    border-radius: 4px;
		    box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff;
		}
		.Container500 .bg_img{
			position: relative;
			background: url(${ctx}/assets/img/500.png) no-repeat;
			width: 600px;
			height: 302px;
		}
		.Container500 .bg_img button{
			position: absolute;
			left: 0;
			width: 160px;
			bottom: 50px;
			text-align: left;
		}
		.Container500 .bg_img button .fa{
			float: right;
			line-height: 23px;
		}
		.Container500 .bg_img button.btn2{
			bottom: 0;
		}
		
			.errormsg{
			font-family: "Microsoft YaHei","Microsoft JhengHei","黑体";
			word-wrap:break-word;
			margin-bottom:20px;
		    font-size: 24px;
		    font-weight: bold;
		    color: #DD6003;
		}
		
		 
		#ErrorInfo{
			margin:0 auto;
		}
		
-->
</style>
<script type="text/javascript">
		var display=false;
		function showError(){
			var errorDiv=document.getElementById('ErrorInfo');
			if(display==false){
				errorDiv.style.display='';
				display=true;
			}else{
				errorDiv.style.display='none';
				display=false;
			}
		}
</script>
	
<%!
private String getPartialServletStackTrace(Throwable t) {
    StringBuilder trace = new StringBuilder();
    trace.append(t.toString()).append('\n');
    StackTraceElement[] elements = t.getStackTrace();
    int pos = elements.length;
    for (int i = elements.length - 1; i >= 0; i--) {
        if ((elements[i].getClassName().startsWith
             ("org.apache.catalina.core.ApplicationFilterChain"))
            && (elements[i].getMethodName().equals("internalDoFilter"))) {
            pos = i;
            break;
        }
    }
    for (int i = 0; i < pos; i++) {
        if (!(elements[i].getClassName().startsWith
              ("org.apache.catalina.core."))) {
            trace.append('\t').append(elements[i].toString()).append('\n');
        }
    }
    return trace.toString();
}

private static String filter(String message) {

    if (message == null)
        return (null);

    char content[] = new char[message.length()];
    message.getChars(0, message.length(), content, 0);
    StringBuilder result = new StringBuilder(content.length + 50);
    for (int i = 0; i < content.length; i++) {
        switch (content[i]) {
        case '<':
            result.append("&lt;");
            break;
        case '>':
            result.append("&gt;");
            break;
        case '&':
            result.append("&amp;");
            break;
        case '"':
            result.append("&quot;");
            break;
        default:
            result.append(content[i]);
        }
    }
    return (result.toString());

}
%>
<%
	StringBuilder sb = new StringBuilder();
	String stackTrace = getPartialServletStackTrace(exception);
    sb.append("<pre>");
    sb.append(filter(stackTrace));
    sb.append("</pre><hr>");
    
    
    int loops = 0;
    Throwable rootCause = exception.getCause();
    while (rootCause != null && (loops < 10)) {
        stackTrace = getPartialServletStackTrace(rootCause);
        sb.append("<pre>");
        sb.append((stackTrace));
        sb.append("</pre><hr>");
        // In case root cause is somehow heavily nested
        rootCause = rootCause.getCause();
        loops++;
    }
%>
</head>
<body>
<div class="Container500">
		<div class="errormsg">${pageContext.exception.message}</div>
		<div class="bg_img">
			<button class="fm-btn btn btn1 btn-primary" onclick="history.back()">返回上一页<i class="fa fa-hand-o-right" aria-hidden="true"></i></button>
			<button class="fm-btn btn btn2 btn-primary" onclick="showError()">查看详情<i class="fa fa-search-plus" aria-hidden="true"></i></button>
		</div>
	</div>
	<hr size="1" noshade="noshade">
	<div  id="ErrorInfo" style="display:none;">
	  	<table width="100%" border="1">
			<tr valign="top">
				<td width="5%" class="tname"><b>Error:</b></td>
				<td>${pageContext.exception}</td>
			</tr>
			<tr valign="top">
				<td  class="tname"><b>URI:</b></td>
				<td>${pageContext.errorData.requestURI}</td>
			</tr>
			<tr valign="top">
				<td  class="tname"><b>Status code:</b></td>
				<td>${pageContext.errorData.statusCode}</td>
			</tr>
			<tr valign="top">
				<td  class="tname"><b>Stack trace:</b></td>
				<td><%=sb.toString() %></td>
			</tr>
		</table>
	</div>
	
</body>
</html>