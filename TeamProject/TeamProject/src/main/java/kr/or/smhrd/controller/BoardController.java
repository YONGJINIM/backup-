package kr.or.smhrd.controller;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.or.smhrd.dto.BoardDTO;
import kr.or.smhrd.dto.PagingDTO;
import kr.or.smhrd.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService service;
	
	//게시판 목록
	@GetMapping("/boardList")
	public ModelAndView bloardList(PagingDTO pDTO) {
		
		pDTO.setTotalRecord(service.totalRecord(pDTO));  //총 레코드 수
		List<BoardDTO> list = service.boardList(pDTO); //해당페이지의 레코드 선택
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list",list);
		mav.addObject("pDTO", pDTO);
		mav.setViewName("board/boardList");
		return mav;
	}
	
	//글쓰기 폼
	@GetMapping("/boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardWrite");
		return mav;
	}
	
	//글쓰기 DB에 기록
	@PostMapping("/boardWriteOk")
	public ResponseEntity<String> boardWriteOk(BoardDTO dto, HttpServletRequest request) {
		dto.setUserid((String)request.getSession().getAttribute("logId"));
		dto.setIp(request.getRemoteAddr());
		
		int result = 0;
		try {
			result = service.boardWriteOk(dto);
		} catch (Exception e) {
			System.out.println("게시판 글 등록 예외 발생..." + e.getMessage());
		}
		
		String tag = "<script>";
		if(result>0) {
			tag += "location.href='/smhrd/board/boardList';";
		}//성공 -> 게시판목록으로 이동
		else {
			tag += "alert('글등록이 실패하였습니다.');";
			tag += "history.back();";	
		} //실패 -> 글등록 폼으로 이동
		tag += "</script>";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html",Charset.forName("UTF-8")));
		
		//ResponseEntity 객체는 프론트페이지를 작성할 수 있다.
		return new ResponseEntity<String>(tag, headers, HttpStatus.OK);
	}
	
	//글 내용 보기
	@GetMapping("/boardView")
	public ModelAndView boardView(int no, PagingDTO pDTO) {
		service.hitCount(no);//조회수 증가
		BoardDTO dto = service.getBoard(no); //레코드 선택
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		mav.addObject("pDTO", pDTO);
		mav.setViewName("board/boardView");
		return mav;
	}
	
	//글 수정 폼
	@GetMapping("/boardEdit")
	public ModelAndView boardEdit(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", service.getBoard(no));
		mav.setViewName("board/boardEdit");
		return mav;
	}
	
	//글수정 DB에 기록
	@PostMapping("/boardEditOk")
	public ModelAndView boardEditOk(BoardDTO dto, HttpSession session) {
		dto.setUserid((String)session.getAttribute("logId"));
		
		int result = service.boardEdit(dto);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", dto.getNo());
		if(result>0){//성공 -> 글내용보기로 이동
			mav.setViewName("redirect:boardView");
		} else{//실패 -> 수정폼으로 다시 이동
			mav.setViewName("redirect:boardEdit");
		}
		return mav;
	}
	
	//글 삭제
	@GetMapping("/boardDel")
	public ModelAndView boardDel(int no, HttpSession session) {
		int result = service.boardDel(no, (String)session.getAttribute("logId"));
		
		ModelAndView mav = new ModelAndView();
		if(result>0) { //성공 -> 글목록으로 이동
			mav.setViewName("redirect:boardList");
		}else { //실패 -> 글내용보기로 이동
			mav.addObject("no", no);
			mav.setViewName("redirect:boardView");
		}
		return mav;
	}
}
