<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
$(document).ready(function(){
	getNomalList(1); //노말 리스트 출력
	
	$("#searchBtn").click(function(){
		getSearchList(1,$("#field").val(),$("#word").val());
	});//검색 리스트 출력
})
	function boardview(num) {
		location.href = "boardView?BOARD_NUM=" + num;
	}
	function getNomalList(pageNum){//일반 리스트
		$("#listResult").load("list.bo",{"pageNum":pageNum},function(data){
			$("#listResult").html(data);
		});
	};
	function getSearchList(pageNum,field,word){
		$("#listResult").load("search.bo",{"pageNum":pageNum,"field":field,"word":word},function(data){
			$("#listResult").html(data);
		});
	}
	
</script>

<title>게시판</title>
</head>
<body>
	<h3 align=center>게시글 보기</h3>
	<br>
	<br>
	<br>
	<div id = listResult>
	<!-- 리스트 출력부분 -->
	</div>
	<div align=center>
		<input type=button value="글작성" onclick="location.href='writeForm.jsp'">
		<select id=field name=field>
			<option value = "BOARD_SUBJECT">제목</option>
			<option value = "BOARD_NAME">작성자</option>
			<option value = "BOARD_DATE">작성일</option>
		</select> 
		<input type=text id=word name=word> <input type=button	id=searchBtn value = "검색">
	</div>

</body>
</html>