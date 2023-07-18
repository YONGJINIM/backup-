package kr.or.smhrd.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.smhrd.dao.ReplyDAO;
import kr.or.smhrd.dto.ReplyDTO;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Inject
	ReplyDAO dao;

	@Override
	public int replyInsert(ReplyDTO dto) {
		
		return dao.replyInsert(dto);
	}

	@Override
	public List<ReplyDTO> replySelect(int no) {
		// TODO Auto-generated method stub
		return dao.replySelect(no);
	}

	@Override
	public int replyUpdate(ReplyDTO dto) {
		return dao.replyUpdate(dto);
	}

	@Override
	public int replyDelete(int re_no) {
		return dao.replyDelete(re_no);
	}
}
