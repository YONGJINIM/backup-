package kr.or.smhrd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.smhrd.dao.BoardDAO;
import kr.or.smhrd.dto.BoardDTO;
import kr.or.smhrd.dto.PagingDTO;

@Service
public class BoardServiceImp implements BoardService {
	@Autowired
	BoardDAO dao;

	@Override
	public int boardWriteOk(BoardDTO dto) {
		return dao.boardWriteOk(dto);
	}

	@Override
	public List<BoardDTO> boardList(PagingDTO pDTO) {
		return dao.boardList(pDTO);
	}

	@Override
	public int totalRecord(PagingDTO pDTO) {
		return dao.totalRecord(pDTO);
	}

	@Override
	public BoardDTO getBoard(int no) {
		return dao.getBoard(no);
	}

	@Override
	public void hitCount(int no) {
		dao.hitCount(no);
	}

	@Override
	public int boardEdit(BoardDTO dto) {
		return dao.boardEdit(dto);
	}

	@Override
	public int boardDel(int no, String userid) {
		return dao.boardDel(no, userid);
	}
}
