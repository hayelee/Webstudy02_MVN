<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
	<table class="table table-bordered">
		<tr>
			<th>상품아이디</th>
			<td>${prod.prodId}</td>
		</tr>
		<tr>
			<th>상품명</th>
			<td>${prod.prodName}</td>
		</tr>
		<tr>
			<th>상품분류</th>
			<td>${prod.lprodNm}</td>
		</tr>
		<tr>
			<th>거래처</th>
			<td>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>거래처명</th>
							<th>담당자명</th>
							<th>지역</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="buyer" value="${prod.buyer }" />
						<tr>
							<td>
								<c:url value="/buyer/buyerView.do" var="buyerViewURL">
									<c:param name="what" value="${prod.prodBuyer }" />
								</c:url>
								<a href="${buyerViewURL }">${buyer.buyerName }</a>
							</td>
							<td>${buyer.buyerCharger }</td>
							<td>${buyer.buyerAdd1 }</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>구매가</th>
			<td>${prod.prodCost}</td>
		</tr>
		<tr>
			<th>판매가</th>
			<td>${prod.prodPrice}</td>
		</tr>
		<tr>
			<th>세일가</th>
			<td>${prod.prodSale}</td>
		</tr>
		<tr>
			<th>상품요약</th>
			<td>${prod.prodOutline}</td>
		</tr>
		<tr>
			<th>상품상세</th>
			<td>${prod.prodDetail}</td>
		</tr>
		<tr>
			<th>상품이미지</th>
			<td>${prod.prodImg}</td>
		</tr>
		<tr>
			<th>재고</th>
			<td>${prod.prodTotalstock}</td>
		</tr>
		<tr>
			<th>입고일</th>
			<td>${prod.prodInsdate}</td>
		</tr>
		<tr>
			<th>적정재고</th>
			<td>${prod.prodProperstock}</td>
		</tr>
		<tr>
			<th>크기</th>
			<td>${prod.prodSize}</td>
		</tr>
		<tr>
			<th>색상</th>
			<td>${prod.prodColor}</td>
		</tr>
		<tr>
			<th>배송방법</th>
			<td>${prod.prodDelivery}</td>
		</tr>
		<tr>
			<th>단위</th>
			<td>${prod.prodUnit}</td>
		</tr>
		<tr>
			<th>입고량</th>
			<td>${prod.prodQtyin}</td>
		</tr>
		<tr>
			<th>출고량</th>
			<td>${prod.prodQtysale}</td>
		</tr>
		<tr>
			<th>마일리지</th>
			<td>${prod.prodMileage}</td>
		</tr>
		<tr>
			<td colspan="2">
				<c:url value='/prod/prodList.do' var="listURL"/>
				<a class="btn btn-secondary" href="${listURL }">목록으로</a>
			</td>
		</tr>
		<tr>
			<th>구매자목록</th>
			<td>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>구매자명</th>
							<th>휴대폰</th>
							<th>이메일</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty prod.memberSet }">
								<c:forEach items="${prod.memberSet }" var="user">
									<c:url value="/member/memberView.do" var="memberViewURL">
										<c:param name="who" value="${user.memId }" />
									</c:url>
									<tr>
										<td>
											<a href="${memberViewURL }">${user.memName }</a>
										</td>
										<td>${user.memHp }</td>
										<td>${user.memMail }</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="3">제발 팔려라!</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
<jsp:include page="/includee/postScript.jsp" />	
</body>
</html>