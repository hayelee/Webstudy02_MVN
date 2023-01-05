<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<title>Insert title here</title>
</head>
<body>
<pre>
	DB에 저장한 데이터
	textPart : ${textPart }
	numPart : ${numPart }
	dataPart : ${dataPart }
	file metadata : ${fileMetadata }
	<!-- session에 있는 데이터 삭제 -->
	<c:remove var="textpart"/>
	<c:remove var="numPart"/>
	<c:remove var="dataPart"/>
</pre>
<c:forEach items="${fileMetadata }" var="md"> <!-- md는 한건의 metadata가 들어있어요 -->
	<img src="<c:url value='${md }'/>" /> <br /> <!-- 이미지가 아닌 파일은 받을 필요도 저장할 필요도 없음! -->
</c:forEach>
<c:remove var="fileMetadata"/>
<form method="post" enctype="multipart/form-data" 
		action="${pageContext.request.contextPath }/file/upload.do">
<ul>
	<li>
		문자 데이터 : <input type="text" name="textPart" />
	</li>
	<li>
		숫자(?) 데이터 : <input type="number" name="numPart" />
	</li>
	<li>
		날짜(?) 데이터 : <input type="date" name="dataPart" />
	</li>
	<li>
		파일 데이터 : <input type="file" name="filePart1" accept="image/"/>
	</li>
	<li>
		파일 데이터 : <input type="file" name="filePart2" />
	</li>
	<li>
		<input type="submit" value="업로드" />
	</li>
</ul>
</form>
</body>
</html>