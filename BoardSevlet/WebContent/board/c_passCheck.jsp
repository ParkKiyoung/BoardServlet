<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글삭제 비밀번호 확인</title>
<c:if test="${a==1 }">
	<script>
		alert("비밀번호 불일치");
	</script>
</c:if>
<script>
function deleteBtn(){
	if(frm.pass.value==""){
		alert("비밀번호를 입력하세요");
		return false;
	}
	frm.submit();
}
</script>
</head>
<body>
<form id=frm action=commentAction>
		<table border solid align=center>
			<tr>
				<td>비밀번호 입력</td>
				<td><input type=password id=pass name=pass></td>
				<input type=hidden id=b_num name=b_num value="${param.BOARD_NUM}">
				<input type=hidden id=c_num name=c_num value="${param.c_num}">
			</tr>
			<tr>
			<th colspan=2 align=center><input type=button value="삭제" onclick="deleteBtn()">
			<input type=button value="뒤로가기" onclick="location.href='boardView?BOARD_NUM=${param.BOARD_NUM}'"></th>
			</tr>
		</table>

	</form>

</body>
</html>