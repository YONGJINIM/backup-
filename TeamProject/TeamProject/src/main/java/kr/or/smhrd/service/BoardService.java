package kr.or.smhrd.service;

import java.util.List;

import kr.or.smhrd.dto.BoardDTO;
import kr.or.smhrd.dto.PagingDTO;

public interface BoardService {
	public int boardWriteOk(BoardDTO dto);
	public List<BoardDTO> boardList(PagingDTO pDTO);
	public int totalRecord(PagingDTO pDTO);
	public BoardDTO getBoard(int no);
	public void hitCount(int no);
	public int boardEdit(BoardDTO dto);
	public int boardDel(int no, String userid);
}
