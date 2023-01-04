<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<c:if test="${not empty message }">
	<script>
		alert("${message}");
	</script>
</c:if>
</head>
<body>
	<h4>가입 양식</h4>
	<form method="post">
		<table class="table table-bordered">
			<tr>
				<th>회원아이디</th>
				<td><input class="form-control" type="text" 
					name="memId" value="${member.memId}" /><span class="text-danger">${errors.memId}</span></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input class="form-control" type="text" 
					name="memPass" value="${member.memPass}" /><span
					class="text-danger">${errors.memPass}</span></td>
			</tr>
			<tr>
				<th>회원명</th>
				<td><input class="form-control" type="text" 
					name="memName" value="${member.memName}" /><span
					class="text-danger">${errors.memName}</span></td>
			</tr>
			<tr>
				<th>주민번호1</th>
				<td><input class="form-control" type="text" name="memRegno1"
					value="${member.memRegno1}" /><span class="text-danger">${errors.memRegno1}</span></td>
			</tr>
			<tr>
				<th>주민번호2</th>
				<td><input class="form-control" type="text" name="memRegno2"
					value="${member.memRegno2}" /><span class="text-danger">${errors.memRegno2}</span></td>
			</tr>
			<tr>
				<th>생일</th>
				<td><input class="form-control" type="date" 
					name="memBir" value="${member.memBir}" /><span class="text-danger">${errors.memBir}</span></td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td><input class="form-control" type="text" 
					name="memZip" value="${member.memZip}" /><span class="text-danger">${errors.memZip}</span></td>
			</tr>
			<tr>
				<th>주소1</th>
				<td><input class="form-control" type="text" 
					name="memAdd1" value="${member.memAdd1}" /><span
					class="text-danger">${errors.memAdd1}</span></td>
			</tr>
			<tr>
				<th>주소2</th>
				<td><input class="form-control" type="text" 
					name="memAdd2" value="${member.memAdd2}" /><span
					class="text-danger">${errors.memAdd2}</span></td>
			</tr>
			<tr>
				<th>집전번</th>
				<td><input class="form-control" type="text" name="memHometel"
					value="${member.memHometel}" /><span class="text-danger">${errors.memHometel}</span></td>
			</tr>
			<tr>
				<th>회사전번</th>
				<td><input class="form-control" type="text" name="memComtel"
					value="${member.memComtel}" /><span class="text-danger">${errors.memComtel}</span></td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td><input class="form-control" type="text" name="memHp"
					value="${member.memHp}" /><span class="text-danger">${errors.memHp}</span></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input class="form-control" type="text" name="memMail"
					value="${member.memMail}" /><span class="text-danger">${errors.memMail}</span></td>
			</tr>
			<tr>
				<th>직업</th>
				<td><input class="form-control" type="text" name="memJob"
					value="${member.memJob}" /><span class="text-danger">${errors.memJob}</span></td>
			</tr>
			<tr>
				<th>취미</th>
				<td><input class="form-control" type="text" name="memLike"
					value="${member.memLike}" /><span class="text-danger">${errors.memLike}</span></td>
			</tr>
			<tr>
				<th>기념일</th>
				<td><input class="form-control" type="text" name="memMemorial"
					value="${member.memMemorial}" /><span class="text-danger">${errors.memMemorial}</span></td>
			</tr>
			<tr>
				<th>기념일자</th>
				<td><input class="form-control" type="date"
					name="memMemorialday" value="${member.memMemorialday}" /><span
					class="text-danger">${errors.memMemorialday}</span></td>
			</tr>
			<tr>
				<th>마일리지</th>
				<td><input class="form-control" type="number" name="memMileage"
					value="${member.memMileage}" /><span class="text-danger">${errors.memMileage}</span></td>
			</tr>
			<tr>
				<th>탈퇴여부</th>
				<td><input class="form-control" type="text" name="memDelete"
					value="${member.memDelete}" /><span class="text-danger">${errors.memDelete}</span></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" class="btn btn-success"
					value="저장"></td>
			</tr>
		</table>
	</form>
	<jsp:include page="/includee/postScript.jsp" />
</body>
</html>