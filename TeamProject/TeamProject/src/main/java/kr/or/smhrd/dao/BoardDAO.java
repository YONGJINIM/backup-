package kr.or.smhrd.dao;

import java.util.List;

import kr.or.smhrd.dto.BoardDTO;
import kr.or.smhrd.dto.PagingDTO;

public interface BoardDAO {
	//�۵��
	public int boardWriteOk(BoardDTO dto);
	//�۸�� ���� (���߿� �߰��� �κ�:paging, search)
	public List<BoardDTO> boardList(PagingDTO pDTO); //������ �������� �ҷ��;� �ϹǷ� ListŸ�� ���
	//�ѷ��ڵ��
	public int totalRecord(PagingDTO pDTO);
	//1�� ���ڵ� ����
	public BoardDTO getBoard(int no);
	//��ȸ�� ����
	public void hitCount(int no);
	//�� ����
	public int boardEdit(BoardDTO dto);
	//�� ����
	public int boardDel(int no, String userid);
}
