<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
   function delChk(){
      
      //확인(true),취소(false)
      if(confirm("글을 삭제하시겠습니까?")){
         location.href = "/smhrd/board/boardDel?no=${dto.no}";   
      }
   }
   $(function(){
      //댓글목록 가져오기
      function replyAllList(){
         
         $.ajax({
            url:'/smhrd/reply/replyList',
            data:{
               no:${dto.no} //원글글번호
            },
            success:function(replyResult){
               console.log(replyResult);
               
               $("#replyList").html("");
               
               $(replyResult).each(function(i,coment){
                  var tag = "<li><div>";
                  tag += "<b>"+coment.userid+"("+coment.writedate+")</b>";
                  //수정삭제
                  if(coment.userid == '${logId}'){ // 'Gogoma' == 'Goguma' 실행위치가 달라서 싱글쿼터 
                	  tag += "<input type='button' value ='Edit'/>";
                	  tag += "<input type='button' value ='Del' title='"+coment.re_no+"'/>";
                	  tag += "<p>"+coment.coment+"</p></div>";
                	  
                	  // -- 수정폼 
                	  tag +="<div style ='display:none'>";
                	  tag +="<form>";                	  
                	  tag += "<textarea style='width:400px' name='coment'>";                	                  	  
                	  // 글내용 수정, 댓글번호 
                	  tag +=coment.coment;
                	  tag += "</textarea>";
                	  
                	  tag +="<input type='hidden' name='re_no' value='"+coment.re_no+"'/>";
                	  tag +="<input type='button' value='수정하기'/>";
                	  tag += "</form>";
                	  tag += "</div>";
                	               	                  	  
                  }else{             
                  	tag += "<p>"+coment.coment+"</p></div>";
                  }
                  tag += "</li>";
                  
                  $("#replyList").append(tag);
               });
            },
            error:function(e){
               console.log(e.responseText);
            }
         });
      }
      
      //댓글쓰기
      $("#replyFrm").submit(function(){
         //form태그의 기본이동의 기능을가진 action으로 이동하는것을 해제
         event.preventDefault();
         
         //1. 댓글입력확인
         if($("#coment").val() == ""){
            alert("댓글을 입력하세요..")
            return false;
         }
         //2. 데이터준비 no=99&coment=댓글내용 -> 폼내의 값을 쿼리로 만들어주는 함수
         var params = $("#replyFrm").serialize();
         console.log('params', params);
         //3. ajax실행
         $.ajax({
            url: '/smhrd/reply/replyWrite',
            type: 'POST',
            data: params,
            success: function(result){
               console.log(result);
               //이미 디비에 등록된 글 폼에서 지우기
               $("#coment").val("");
               //댓글리스트 다시 출력한다.
               replyAllList();
            },
            error:function(e){
               console.log(e.responseText);
            }
         });
      });
      
      // 댓글수정폼
      // $("#replyList input[value=Edit]").click();
      $(document).on('click','#replyList input[value=Edit]',function(){
    	 //수정폼은 보여주고 
    	 $(this).parent().css('display','none');    	     	 
    	 //해당댓글은 숨기고
    	 $(this).parent().next().css('display','block');
      });
      
      // 댓글 수정 DB
      $(document).on('button','#replyList input[value=수정하기]',function(){
    	 var params = $(this).parent().serialize();
    	 
    	 $.ajax({
    		 url :'/smhrd/reply/replyEditOK',
    		 data : params,
    		 type:'POST',
    		 success:function(result){
    			 if(result=='0'){
					alert('댓글이 수정되지 않았습니다.');    				 
    			 }else{
    				replyAllList(); 
    			 }
    		 },error:function(){
    			 console.log("댓글수정실패")
    		 }
    	 });
    	 
      });
      // 댓글삭제
      $(document).on('click','#replyList input[value=Del]',function(){
    	 // 댓글번호
    	 var re_no = $(this).attr('title')
    	 $.ajax({
    		url:"/smhrd/reply/replyDel",
    		data:{
    			re_no:re_no
    		},
    		success:function(result){
    			if(result=='0'){
    				alert("댓글이 삭제되지 않았습니다.");
    			}else{
    				replyAllList();
    			}
    		},error:function(e){
    			console.log("댓글삭제 에러 발생");
    		}
    	 });
    	 
    	 
      });
      
      //해당글의 댓글 목록
      replyAllList();
   });
</script>
<main>
   <h1>글내용보기</h1>
   <div>
      <a href='/smhrd/board/boardList?nowPage=${pDTO.nowPage}<c:if test="${pDTO.searchWord != null }">&searchKey=${pDTO.searchKey }&searchWord=${pDTO.searchWord }</c:if>'>목록</a>
   </div>
   <ul>
      <li>글번호 : ${dto.no }</li>
      <li>글쓴이 : ${dto.userid }</li>
      <li>조회수 : ${dto.hit }</li>
      <li>등록일 : ${dto.writedate }</li>
      <li>제목 : ${dto.subject }</li>
      <li>글내용<br/>
         ${dto.content }</li>
   </ul>
   <div>
      <!-- session의 로그인아이디(logId)와 현재글의 글쓴이(userid)가 같으면 수정, 삭제표시한다 -->
      <c:if test="${logId==dto.userid }">
         <a href="/smhrd/board/boardEdit?no=${dto.no }">수정</a> 
         <a href="javascript:delChk()">삭제</a>
      </c:if>
   </div>
   <!-- 댓글달기 -->
   <style>
      #coment{width:500px; height:80px;}
      #replyList>li{board-bottom:1px solid #add; padding:5px 0px;}
   </style>
   <div id="reply">
      <!-- 로그인시 댓글 폼 -->
      <c:if test="${logStatus=='Y' }">
         <form method="post" id="replyFrm">
            <input type="hidden" name="no" value="${dto.no }"><!-- 원글번호 -->
            <textarea name="coment" id="coment"></textarea>
            <input type="submit" value="댓글등록하기"/>
         </form>
      </c:if>      
      <hr>     
      <ul id="replyList">
      </ul>
   </div>
</main>