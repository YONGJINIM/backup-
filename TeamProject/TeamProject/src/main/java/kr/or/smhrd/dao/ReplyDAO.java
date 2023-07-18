package kr.or.smhrd.dao;

import java.util.List;

import kr.or.smhrd.dto.ReplyDTO;

public interface ReplyDAO {
	   public int replyInsert(ReplyDTO dto);
	   public List<ReplyDTO> replySelect(int no);
	   public int replyUpdate(ReplyDTO dto);
	   public int replyDelete(int re_no);
}
