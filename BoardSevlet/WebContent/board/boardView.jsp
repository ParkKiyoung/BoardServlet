<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>
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
			<td>${bb.BOARD_NUM }</td>
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
			<td colspan=3><a href="#">${bb.BOARD_FILE }</a></td>
		</tr>
	</table>
	<div align=center>
		<input type=button value="목록으로" onclick="location.href='list.bo'">
		<input type=button value="수정" onclick="updateBtn(${bb.BOARD_NUM})">
		<input type=button value="삭제" onclick="deleteBtn(${bb.BOARD_NUM})">
	</div>

</body>
</html>