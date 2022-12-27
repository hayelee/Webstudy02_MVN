<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<c:if test="${not empty message }">
	<script>
		alert("${message}");
	</script>
	<c:remove var="message" scope="session"/>
</c:if>
</head>

<body>
	<form method="post" action="<c:url value="/login/loginProcess.do"/>">
		<ul>
			<li>
				<c:set var="savedId" value="${cookie['saveId']['value'] }"/>
				<input type="text" name="memId" placeholder="아이디" value="${not empty validId ? validId : savedId }"/>
				<input type="checkbox" name="saveId" ${not empty savedId ? 'checked' : '' }/>아이디기억하기
				<c:remove var="validId" scope="session"/>
			</li>
			<li>
				<input type="password" name="memPass" placeholder="비밀번호"/>
				<input type="submit" value="로그인" /> 
			</li>
		</ul>
	</form>
</body>
</html>