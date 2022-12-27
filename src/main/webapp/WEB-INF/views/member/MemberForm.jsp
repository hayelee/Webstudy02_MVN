<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
   <table>
      <tr>
         <th>회원아이디</th>
         <td><input class="form-control" type="text" name="memId" required="required" /></td>
      </tr>
      <tr>
         <th>비밀번호</th>
         <td><input class="form-control" type="text" name="memPass" /></td>
      </tr>
      <tr>
         <th>회원명</th>
         <td>
            <input class="form-control" type="text" name="memName" value="${member.memName }"/>
         </td>
      </tr>
      <tr>
         <th>주민번호1</th>
         <td><input class="form-control" type="text" name="memRegno1" /></td>
      </tr>
      <tr>
         <th>주민번호2</th>
         <td><input class="form-control" type="text" name="memRegno2" /></td>
      </tr>
      <tr>
         <th>생일</th>
         <td><input class="form-control" type="date" name="memBir" /></td>
      </tr>
      <tr>
         <th>우편번호</th>
         <td><input class="form-control" type="text" name="memZip" /></td>
      </tr>
      <tr>
         <th>주소1</th>
         <td><input class="form-control" type="text" name="memAdd1" /></td>
      </tr>
      <tr>
         <th>주소2</th>
         <td><input class="form-control" type="text" name="memAdd2" /></td>
      </tr>
      <tr>
         <th>집전번</th>
         <td><input class="form-control" type="text" name="memHometel" /></td>
      </tr>
      <tr>
         <th>회사전번</th>
         <td><input class="form-control" type="text" name="memComtel" /></td>
      </tr>
      <tr>
         <th>휴대폰</th>
         <td><input class="form-control" type="text" name="memHp" /></td>
      </tr>
      <tr>
         <th>이메일</th>
         <td><input class="form-control" type="text" name="memMail" /></td>
      </tr>
      <tr>
         <th>직업</th>
         <td><input class="form-control" type="text" name="memJob" /></td>
      </tr>
      <tr>
         <th>취미</th>
         <td><input class="form-control" type="text" name="memLike" /></td>
      </tr>
      <tr>
         <th>기념일</th>
         <td><input class="form-control" type="text" name="memMemorial" /></td>
      </tr>
      <tr>
         <th>기념일자</th>
         <td><input class="form-control" type="date" name="memMemorialday" /></td>
      </tr>
      <tr>
         <th>마일리지</th>
         <td><input class="form-control" type="number" name="memMileage" /></td>
      </tr>
      <tr>
         <th>탈퇴여부</th>
         <td><input class="form-control" type="text" name="memDelete" /></td>
      </tr>
      <tr>
         <td colspan="2">
            <input type="submit" value="저장" />
         </td>
      </tr>
   </table>
</form>
<jsp:include page="/includee/postScript.jsp" />
</body>
</html>