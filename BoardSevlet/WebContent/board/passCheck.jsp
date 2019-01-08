<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%-- <%
	int num = Integer.parseInt(request.getParameter("BOARD_NUM"));
	int checkNum = Integer.parseInt(request.getParameter("checkNum"));
	//1번이면 수정, 2번이면 삭제로 연결 
%> --%>
<c:if test="${a==1 }">
	<script>
		alert("비밀번호 불일치")
	</script>
</c:if>
<script>
	function updateBtn() {
		if (frm.pass.value == "") {
			alert("비밀번호를 입력해주세요");
			return false;
		}
		frm.submit();
	}
	function deleteBtn(num){
		if (frm.pass.value == "") {
			alert("비밀번호를 입력해주세요");
			return false;
		}
		location.href ="delete.bo?num="+num+"&pass="+frm.pass.value;
	}
</script>
</head>
<body>
	<form id=frm action=update.bo >
		<table border solid align=center>
			<tr>
				<td>비밀번호 입력</td>
				<td><input type=password id=pass name=pass></td>
				<input type=hidden id=num name=num value="${param.BOARD_NUM}">
			</tr>
			<tr>
			<c:if test="${param.checkNum==1}">
			<th colspan=2 align=center><input type=button value="확인" onclick="updateBtn()">
			</c:if>
			<c:if test="${param.checkNum==2}">
			<th colspan=2 align=center><input type=button value="삭제" onclick="deleteBtn(${param.BOARD_NUM})">
			</c:if>
			<input type=button value="뒤로가기"	onclick="location.href='boardView?BOARD_NUM=${param.BOARD_NUM}'"></th>
			</tr>
		</table>

	</form>
</body>
</html>