<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("newline", "\n"); %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">작성자</td>
						<td>${vo.userName }</td>
					</tr>	
					<tr>
						<td class="label">작성일</td>
						<td>${vo.regDate }</td>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(fn:replace(fn:replace(vo.contents, ">", "&gt;"), "<", "&lt;"), newline, "<br>") }
							</div>
						</td>
					</tr>
				</table>
				
				<div class="bottom">
					<a href="${pageContext.request.contextPath}/board">글목록</a>
					<c:choose>
						<c:when test='${authUser.no eq vo.userNo }'>
							<a href="${pageContext.request.contextPath}/board/update/${vo.no }">글수정</a>
						</c:when>
					</c:choose>
				</div>
				
				<!-- 답글쓰기 버튼 -->
				<c:choose>
					<c:when test='${not empty authUser }'>
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board/write" id="new-book">답글쓰기 </a>
					</div>		
					</c:when>
				</c:choose>
				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
<c:if test='${param.result == "success" }'>
	<script>alert('성공적으로 수정 하였습니다.')</script>
</c:if>