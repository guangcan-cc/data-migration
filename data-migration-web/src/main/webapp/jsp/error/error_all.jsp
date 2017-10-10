<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/taglib.jspf" %>
<%@page isErrorPage="true"%>
<!DOCTYPE html>
<head>
<title>Error Page</title>
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
-->
</style>
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
	<h1>Http.${pageContext.errorData.statusCode}-${pageContext.exception}</h1>
	<HR size="1" noshade="noshade">
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
</body>
</html>