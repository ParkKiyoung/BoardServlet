<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>
	function submitBtn() {

		if (frm.BOARD_NAME.value == "") {
			alert("이름을 입력해주세요");
			return false;
		}
		if (frm.BOARD_PASS.value == "") {
			alert("비번을 입력해주세요");
			return false;
		}
		if (frm.BOARD_SUBJECT.value == "") {
			alert("제목을 입력해주세요");
			return false;
		}
		if (frm.BOARD_CONTENT.value == "") {
			alert("내용을 입력해주세요");
			return false;
		}
		frm.submit();
	}
</script>
<title>글 수정</title>
</head>
<body>
	<form action=update.bo align=center name=frm
		enctype="multipart/form-data" method=post>
		<table border solid align=center>
			<tr>
				<td>작성자</td>
				<td><input type=text id=BOARD_NAME name=BOARD_NAME size=30
					value="${bb.BOARD_NAME}"> <input type=hidden id=BOARD_NUM
					name=BOARD_NUM value="${bb.BOARD_NUM }"></td>
				<td>비밀번호</td>
				<td><input type=password id=BOARD_PASS name=BOARD_PASS size=30></td>
			</tr>
			<tr>
				<td>제목</td>
				<td colspan=3><input type=text id=BOARD_SUBJECT
					name=BOARD_SUBJECT size=50 value="${bb.BOARD_SUBJECT }"></td>
			</tr>
			<tr>
				<td colspan=4>글 내용</td>
			</tr>
			<tr>
				<td colspan=4><textarea id=BOARD_CONTENT name=BOARD_CONTENT
						rows="30" cols=100>${bb.BOARD_CONTENT }</textarea></td>
			</tr>
			<tr>
				<td>파일첨부</td>
				<td colspan=3><input type=file id=BOARD_FILE name=BOARD_FILE></td>
				<!-- 보안상의 이유로 input type file 에서는 value값을 넣을 수 없다. -->
			</tr>
			<tr>
				<td colspan=4><input type=button value="글수정"
					onclick="submitBtn()"> <input type=reset value="취소">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>