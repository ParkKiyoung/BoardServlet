<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>
	function boardview(num) {
		location.href = "boardView?BOARD_NUM=" + num;
	}
</script>
<title>게시판</title>
</head>
<body>
	<h3 ailgn=center>게시글 보기</h3>
	<table align=center border solid>
		<tr>
			<td>글번호</td>
			<td>글제목</td>
			<td>작성자</td>
			<td>작성일자</td>
			<td>조회수</td>
			<td>첨부파일</td>
		</tr>
		<c:forEach items="${list}" var="i">
			<tr>
				<td>${i.BOARD_NUM }</td>
				<td><a href="javascript:boardview(${i.BOARD_NUM})">${i.BOARD_SUBJECT }</td>
				<td>${i.BOARD_NAME }</td>
				<td>${i.BOARD_DATE }</td>
				<td>${i.BOARD_READCOUNT }</td>
				<c:if test="${i.BOARD_FILE!=''}">
					<td>O</td>
				</c:if>
				<c:if test="${i.BOARD_FILE==''}">
					<td>X</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	<div align = center><input type = button value = "글작성" onclick = "location.href='writeForm.jsp'"></div>

</body>
</html>