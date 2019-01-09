<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
function commentBtn(){//댓글 입력 메서드
	if(commentFrm.name.value==""){
		alert("이름을 입력하세요");
		return false;
	}
	if(commentFrm.pass.value==""){
		alert("비밀번호를 입력하세요");
		return false;
	}
	if(commentFrm.msg.value==""){
		alert("내용을 입력하세요");
		return false;
	}
	commentFrm.submit();
}

function updateBtn(num){
	location.href = "passCheck.jsp?BOARD_NUM="+num+"&checkNum=1"//1은 수정
}
function deleteBtn(num){
	location.href = "passCheck.jsp?BOARD_NUM="+num+"&checkNum=2"//2는 삭제
}
</script>
<title>글 보기</title>
</head>
<body>
	<table border solid align=center>
		<tr>
			<td>작성자</td>
			<td>${bb.BOARD_NAME }</td>
			<td>조회수</td>
			<td>${bb.BOARD_READCOUNT }</td>

		</tr>
		<tr>
			<td>제목</td>
			<td>${bb.BOARD_SUBJECT }</td>
			<td>작성날짜</td>
			<td>${bb.BOARD_DATE }</td>
		</tr>
		<tr>
			<th colspan=4>글 내용</th>
		</tr>
		<tr>
			<td colspan=4><textarea id=BOARD_CONTENT name=BOARD_CONTENT
					rows="30" cols=100 readonly>${bb.BOARD_CONTENT }</textarea></td>
		</tr>
		<tr>
			<td>파일첨부</td>
			<td colspan=3><a href="file_down.jsp?file_name=${bb.BOARD_FILE}">${bb.BOARD_FILE }</a></td>
		</tr>
	</table>
	<div align=center>
		<input type=button value="목록으로" onclick="location.href='boardList.jsp'">
		<input type=button value="수정" onclick="updateBtn(${bb.BOARD_NUM})">
		<input type=button value="삭제" onclick="deleteBtn(${bb.BOARD_NUM})">
		<input type=button value="답글" onclick="location.href='replyForm.jsp?BOARD_NUM=${bb.BOARD_NUM}&BOARD_RE_LEV=${bb.BOARD_RE_LEV}&BOARD_RE_REF=${bb.BOARD_RE_REF}&BOARD_RE_STEP=${bb.BOARD_RE_STEP}'">
	</div>
	<br>
	
	<div align = center id = commentResult><!-- 댓글 출력 부분 -->
	댓글란
	<hr>
	
	<!-- 댓글 입력부분 -->
	<table>
	<c:forEach items="${arr }" var ="i">
	<tr>
	<td>이름 : ${i.c_name}</td>
	<td>댓글 : ${i.c_msg}</td>
	<td>작성시간 : ${i.c_date}</td>
	<td>
	<input type = button value = "삭제" onclick ="location.href='c_passCheck.jsp?c_num=${i.c_num}&BOARD_NUM=${i.b_num}'"></td>
	</tr>
	</c:forEach>
	</table>
	</div>
	<form id = commentFrm action = commentAction method = "post">
	<div align = center >
	<input type = hidden id = b_num name = b_num value = "${bb.BOARD_NUM }">
	이름 : <input type = text id = name  name = name >
	비밀번호 : <input type = password id = pass name = pass>
	<br>
	<textarea id = msg name = msg rows=5 cols = 80></textarea>
	<input type = button  value = "댓글입력" onclick="commentBtn()">
	</div>
	</form>

</body>
</html>