package org.noon.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.noon.domain.FlexVO;
import org.noon.domain.ReceiveVO;
import org.springframework.stereotype.Repository;

@Repository
public class SampleDAOImpl implements SampleDAO {

	String namespace = "org.noon.mapper.sampleMapper";
	
	@Inject
	SqlSession session;
	
	@Override
	public void createflexMoney(FlexVO vo) throws Exception{
		session.insert(namespace+".summaryInsert", vo);
		
		for (Integer money : vo.getMoneyList()) { //사람수 만큼 금액분배
			FlexVO tempVO = new FlexVO();
			tempVO.setSummaryno(vo.getSummaryno());
			tempVO.setMoney(money);
			session.insert(namespace+".detailInsert", tempVO);
		};
		
	}

	@Override
	public void updateGetMoney(ReceiveVO vo) throws Exception{
		session.update(namespace+".updateDetailMoney", vo);
	}

	@Override
	public List<FlexVO> selectAvailableMoney(ReceiveVO vo) throws Exception{
		
		List<FlexVO> checkList= session.selectList(namespace+".selectValidCheck", vo);
		List<FlexVO> moneyList= new ArrayList<FlexVO>();
		if(checkList.size() == 0) { //check해서 없는 경우 금액리스트 추출
			moneyList= session.selectList(namespace+".selectAvailableMoney", vo);
		}
		return moneyList;
	}

	@Override
	public FlexVO selectInfo(FlexVO vo) throws Exception{
		FlexVO flexVO = session.selectOne(namespace+".selectInfo", vo);
		return flexVO;
	}
	
	
	
}
