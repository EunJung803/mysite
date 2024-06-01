<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				
				<!-- 게시글 리스트 -->
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:set var="count" value="${totalBoardCount }" />
					<c:forEach items="${list }" var="vo" varStatus="status" >
						<tr>
							<td>${count - ((currPageNum-1) *  5) - status.index}</td>
							<c:choose>
								<c:when test="${vo.depth eq 0 }">
									<td style="text-align:left; padding-left:${20*vo.depth }px">
										<a href="${pageContext.servletContext.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a>
									</td>
								</c:when>
								<c:otherwise>
				                   	<td style="text-align:left; padding-left:${20*vo.depth }px">
				                   		<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png">
										<a href="${pageContext.servletContext.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a>
									</td>
				                </c:otherwise>
							</c:choose>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
							<c:choose>
								<c:when test='${authUser.no eq vo.userNo }'>
									<a href="#" class="del" onclick="confirmDelete(${vo.no})">삭제</a>
									<%-- <a href="${pageContext.request.contextPath}/board?a=delete&no=${vo.no }" class="del">삭제</a> --%>
								</c:when>
							</c:choose>
							</td>
						</tr>		
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					<c:if test="${blockStartPageNo > 1}">
						<li><a href="${pageContext.request.contextPath}/board?p=${blockStartPageNo - pagesPerBlock}">◀</a></li>
					</c:if>
					
					<c:forEach var="i" begin="${blockStartPageNo }" end="${blockEndPageNo }">
			            <c:choose>
			                <c:when test="${i == currPageNum}">
			                    <li class="selected">${i}</li>
			                </c:when>
			                <c:otherwise>
			                    <li><a href="${pageContext.request.contextPath}/board?p=${i}">${i}</a></li>
			                </c:otherwise>
			            </c:choose>
			        </c:forEach>
					 
					<c:if test="${blockEndPageNo < totalPages}">
						<li><a href="${pageContext.request.contextPath}/board?p=${blockStartPageNo + pagesPerBlock}">▶</a></li>
					</c:if> 
					</ul>
				</div>
				
				<!-- 글쓰기 버튼 -->
				<c:choose>
					<c:when test='${not empty authUser }'>
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board?a=writeform" id="new-book">글쓰기</a>
					</div>		
					</c:when>
				</c:choose>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
	
	<script>
	    function confirmDelete(no) {
	        if (confirm("해당 게시물을 삭제하시겠습니까?")) {
	            window.location.href = "${pageContext.request.contextPath}/board?a=delete&no=" + no;
	            alert('삭제 되었습니다.');
	        }
	    }
	</script>
	
</body>
</html>