package kr.or.smhrd.dao;

import java.util.List;

import kr.or.smhrd.dto.BoardDTO;
import kr.or.smhrd.dto.PagingDTO;

public interface BoardDAO {
	//글등록
	public int boardWriteOk(BoardDTO dto);
	//글목록 선택 (나중에 추가될 부분:paging, search)
	public List<BoardDTO> boardList(PagingDTO pDTO); //데이터 여러개를 불러와야 하므로 List타입 사용
	//총레코드수
	public int totalRecord(PagingDTO pDTO);
	//1개 레코드 선택
	public BoardDTO getBoard(int no);
	//조회수 증가
	public void hitCount(int no);
	//글 수정
	public int boardEdit(BoardDTO dto);
	//글 삭제
	public int boardDel(int no, String userid);
}
