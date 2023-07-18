<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style> 
	.board_list, .page>ul{ overflow:auto; }

	.board_list>li{
		float:left;
		height:40px;
		line-height:40px;
		border-bottom:1px solid #ddd;
		width:10%;
		
	}
	.board_list>li:nth-child(6n+3){ 
		width:50%;
		white-space:nowrap; 
		overflow:hidden; 
		text-overflow:ellipsis; 
	}
	
	.page {
		text-align: center; /* 가운데 정렬 */
		margin-top: 20px; /* 위쪽 여백 추가 */
	}
	.page li{
		display: inline-block; /* 인라인 요소로 배치 */
		width:40px;
		height:40px;
		text-align:center;
		border-radius: 50%; /* 테두리를 둥글게 */
		line-height: 40px;
		font-size: 18px; /* 글씨 크기 조절 */
		margin: 0 5px; /* 좌우 여백 추가 */
	}
	.page .active {
		background-color: #f2f2f2; /* 현재 페이지 배경색 설정 */
		color: #333; /* 현재 페이지 글자색 설정 */
		font-weight: bold; /* 현재 페이지 글자 굵게 설정 */
	}
	.page .prev,
	.page .next {
		font-weight: bold; /* 이전, 다음 페이지 스타일링 */
	}
	.search{
		text-align:center;
	}
	.total_record {
		margin-bottom: 10px; /* 아래쪽 여백 추가 */
	}
</style>

<main>
	<h1>게시판 목록</h1>
	<div class="total_record">총 게시글 수: ${pDTO.totalRecord}개</div>
	<div><a href="/smhrd/board/boardWrite">글쓰기</a></div>
	<ul class="board_list">
		<li>&nbsp;</li>
		<li>no</li>
		<li>제목</li>
		<li>글쓴이</li>
		<li>등록일</li>
		<li>조회수</li>
		
		<c:forEach var="dto" items="${list}">
			<li><input type="checkbox" /></li>
			<li>${dto.no }</li>
			<li><a href='/smhrd/board/boardView?no=${dto.no}&nowPage=${pDTO.nowPage}<c:if test="${pDTO.searchWord!=null}">&searchKey=${pDTO.searchKey}&searchWord=${pDTO.searchWord}</c:if>'>${dto.subject}</a></li>
			<li>${dto.userid }</li>
			<li>${dto.writedate }</li>
			<li>${dto.hit }</li>
		</c:forEach>
	</ul>
	
	<div class="page">
		<ul>
			<!-- 이전 페이지 -->
			<c:if test="${pDTO.nowPage > 1}">
				<li class="prev"><a href='/smhrd/board/boardList?nowPage=${pDTO.nowPage-1}<c:if test="${pDTO.searchWord!=null}">&searchKey=${pDTO.searchKey}&searchWord=${pDTO.searchWord}</c:if>'>prev</a></li>
			</c:if>
			
			<!-- 페이지 번호 -->
			<c:forEach var="p" begin="${pDTO.startPageNum}" end="${pDTO.startPageNum+pDTO.onePageNumCount-1}" step="1">
				<c:if test="${p <= pDTO.totalPage}">
					<c:if test="${p == pDTO.nowPage}">
						<li class="active">${p}</li>
					</c:if>
					<c:if test="${p != pDTO.nowPage}">
						<li><a href='/smhrd/board/boardList?nowPage=${p}<c:if test="${pDTO.searchWord!=null}">&searchKey=${pDTO.searchKey}&searchWord=${pDTO.searchWord}</c:if>'>${p}</a></li>
					</c:if>
				</c:if>
			</c:forEach>

			<!-- 다음 페이지 -->
			<c:if test="${pDTO.nowPage < pDTO.totalPage}">
				<li class="next"><a href='/smhrd/board/boardList?nowPage=${pDTO.nowPage+1}<c:if test="${pDTO.searchWord!=null}">&searchKey=${pDTO.searchKey}&searchWord=${pDTO.searchWord}</c:if>'>next</a></li>
			</c:if>
		</ul>
	</div>
	
	<div class="search">
		<form action="/smhrd/board/boardList">
			<select name="searchKey" id="">
				<option value="subject">제목</option>
				<option value="content">글내용</option>
				<option value="userid">글쓴이</option>
			</select>
			<input type="text" name="searchWord" id="searchWord" />
			<input type="submit" value="Search" />
		</form>
	</div>
</main>
