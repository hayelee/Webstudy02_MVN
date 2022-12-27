<%@ page language="java" contentType="text/html; charset=UTF-8" buffer="10kb"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" flush="false"/>
</head>
<body>
<div class="container">
	<jsp:include page='${contentPage}' />
</div>
<jsp:include page="/includee/postScript.jsp" flush="false"/>
</body>
</html>