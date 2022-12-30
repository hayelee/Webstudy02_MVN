<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
<table>
	<thead>
		<tr>
			<th>일련번호</th>
			<th>상품분류</th>
			<th>상품명</th>
			<th>거래처명</th>
			<th>구매가</th>
			<th>판매가</th>
			<th>상품구매자수</th> <!-- cart테이블을 이용해서 조인을하든..인라인..어쩌고를 하든... -->
		</tr>
	</thead>
	<tbody>
		<c:set var="prodList" value="${member.prodList }"/> 
		<c:choose>
			<c:when test="${not empty prodList }">
				<c:forEach items="${prodList }" var="prod">
					<tr>
						<td>${prod.prodId }</td>
						<td>
							<c:url value="/prod/prodView.do" var="prodViewURL">
								<c:param name="what" value="${prod.prodId }" />
							</c:url>
							<a href="${prodViewURL }">${prod.prodName }</a>
						</td>
						<td>${prod.prodLgu }</td>
						<td>${prod.lprodNm }</td>
						<td>${prod.buyer.buyerName }</td>
						<td>${prod.prodCost }</td>
						<td>${prod.prodPrice }</td>
						<td>${prod.prodMileage }</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="7">구매기록 없음.</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="7">
				<div id="searchUI">
					<select name="searchType">
						<option value>전체</option>
						<option value="lprodNm">전체</option>
						<option value="buyerName">거래처명</option>
						<option value="prodName">상품명</option>
					</select>
					<input type="text" name="searchWord" />
					<input type="button" id="searchBtn" value="검색" />
				</div>
			</td>
		</tr>
	</tfoot>
<jsp:include page="/includee/postScript.jsp" />
</table>
</body>
</html>