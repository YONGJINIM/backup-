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
	
	//�Խ��� ���
	@GetMapping("/boardList")
	public ModelAndView bloardList(PagingDTO pDTO) {
		
		pDTO.setTotalRecord(service.totalRecord(pDTO));  //�� ���ڵ� ��
		List<BoardDTO> list = service.boardList(pDTO); //�ش��������� ���ڵ� ����
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list",list);
		mav.addObject("pDTO", pDTO);
		mav.setViewName("board/boardList");
		return mav;
	}
	
	//�۾��� ��
	@GetMapping("/boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardWrite");
		return mav;
	}
	
	//�۾��� DB�� ���
	@PostMapping("/boardWriteOk")
	public ResponseEntity<String> boardWriteOk(BoardDTO dto, HttpServletRequest request) {
		dto.setUserid((String)request.getSession().getAttribute("logId"));
		dto.setIp(request.getRemoteAddr());
		
		int result = 0;
		try {
			result = service.boardWriteOk(dto);
		} catch (Exception e) {
			System.out.println("�Խ��� �� ��� ���� �߻�..." + e.getMessage());
		}
		
		String tag = "<script>";
		if(result>0) {
			tag += "location.href='/smhrd/board/boardList';";
		}//���� -> �Խ��Ǹ������ �̵�
		else {
			tag += "alert('�۵���� �����Ͽ����ϴ�.');";
			tag += "history.back();";	
		} //���� -> �۵�� ������ �̵�
		tag += "</script>";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html",Charset.forName("UTF-8")));
		
		//ResponseEntity ��ü�� ����Ʈ�������� �ۼ��� �� �ִ�.
		return new ResponseEntity<String>(tag, headers, HttpStatus.OK);
	}
	
	//�� ���� ����
	@GetMapping("/boardView")
	public ModelAndView boardView(int no, PagingDTO pDTO) {
		service.hitCount(no);//��ȸ�� ����
		BoardDTO dto = service.getBoard(no); //���ڵ� ����
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		mav.addObject("pDTO", pDTO);
		mav.setViewName("board/boardView");
		return mav;
	}
	
	//�� ���� ��
	@GetMapping("/boardEdit")
	public ModelAndView boardEdit(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", service.getBoard(no));
		mav.setViewName("board/boardEdit");
		return mav;
	}
	
	//�ۼ��� DB�� ���
	@PostMapping("/boardEditOk")
	public ModelAndView boardEditOk(BoardDTO dto, HttpSession session) {
		dto.setUserid((String)session.getAttribute("logId"));
		
		int result = service.boardEdit(dto);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", dto.getNo());
		if(result>0){//���� -> �۳��뺸��� �̵�
			mav.setViewName("redirect:boardView");
		} else{//���� -> ���������� �ٽ� �̵�
			mav.setViewName("redirect:boardEdit");
		}
		return mav;
	}
	
	//�� ����
	@GetMapping("/boardDel")
	public ModelAndView boardDel(int no, HttpSession session) {
		int result = service.boardDel(no, (String)session.getAttribute("logId"));
		
		ModelAndView mav = new ModelAndView();
		if(result>0) { //���� -> �۸������ �̵�
			mav.setViewName("redirect:boardList");
		}else { //���� -> �۳��뺸��� �̵�
			mav.addObject("no", no);
			mav.setViewName("redirect:boardView");
		}
		return mav;
	}
}
