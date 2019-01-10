<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
#tt {
	text-align:center;
}
</style>
<div align=center>총 게시글 수 : ${cnt }</div>
<table align=center border solid>
	<tr style = "background-color:pink">
		<th>글번호</th>
		<th>글제목</th>
		<th>작성자</th>
		<th>작성일자</th>
		<th>조회수</th>
		<th>첨부파일</th>
	</tr>
	<c:set var="number" value="${cnt}">	</c:set><!--게시글 번호 -->	
	<c:forEach items="${list}" var="i">
		<tr>
			<td id = "tt">
			<c:out value="${number}"></c:out>
			<c:set var="number" value ="${number-1}"></c:set>
			</td>
			<td width = 250 >
			<c:if test="${i.BOARD_RE_LEV>0}">
			<img src = "./level.gif" width="${i.BOARD_RE_LEV*5}" height = "16">
			<img src = "./re.gif">
			</c:if>
			<a href="javascript:boardview(${i.BOARD_NUM})">${i.BOARD_SUBJECT }</a>
			<c:if test="${i.CO_CNT!=0}">
			[${i.CO_CNT}]
			</c:if>
			</td>
			<td id = "tt">${i.BOARD_NAME }</td>
			<td id = "tt">${i.BOARD_DATE }</td>
			<td id = "tt">${i.BOARD_READCOUNT }</td>
			<c:if test="${i.BOARD_FILE!=''}">
				<td id = "tt">O</td>
			</c:if>
			<c:if test="${i.BOARD_FILE==''}">
				<td id = "tt">X</td>
			</c:if>
		</tr>
	</c:forEach>
</table>
<div align=center>
	<!-- 이전 -->
	<c:if test="${startPage>blockPage }">
		<a href="javascript:getNomalList(${startPage-blockPage})">[이전]</a>
	</c:if>
	<!-- 페이지 블록 출력 -->
	<c:forEach begin="${startPage}" end="${endPage}" var="i">
		<c:if test="${i==currentPage}">[${i}]</c:if>
		<c:if test="${i!=currentPage}">
			<a href="javascript:getNomalList(${i})">[${i}]</a>
		</c:if>
	</c:forEach>
	<!-- 다음 -->
	<c:if test="${endPage<totPage}">
		<a href="javascript:getNomalList(${endPage+1})">[다음]</a>
	</c:if>
</div>