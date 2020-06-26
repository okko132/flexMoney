package org.noon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.noon.domain.FlexVO;
import org.noon.domain.ReceiveVO;
import org.noon.persistence.SampleDAO;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class SampleServiceImpl implements SampleService {
	@Inject
	SampleDAO dao;

	@Override
	public void createflexMoney(FlexVO vo) {
		try {
			ArrayList<Integer> list = new ArrayList<Integer>();
			String token = "";
			int count = vo.getPersonCnt(); //사람수
			int money = vo.getMoney(); //뿌리기 총금액
			
			for(int i=0; i < 3; i++) {
				token+=(char)((int)(Math.random() * 26)+65);
			} //랜덤 대문자 3자리

			//랜덤하게 분배
			for(int i=0; i < count; i++ ) {
				int randomMoney = (int)(Math.random() * money) ;
				
				if(i != count-1) {
					money -= randomMoney;
				}else{
					randomMoney = money; //마지막 남은돈
				}
				
				list.add(randomMoney);
			}
			
			vo.setMoneyList(list);
			vo.setToken(token);
			
			dao.createflexMoney(vo);
			
			vo.setResMessage("SUCCESS");
		
		} catch (Exception e) {
			vo.setToken("");
			vo.setResMessage("FAIL");
			
		}

		
	}

	@Override
	public String updateGetMoney(ReceiveVO vo) {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		try {
			List<FlexVO> moneyList = dao.selectAvailableMoney(vo);
			int idx;
			int resMoney;
			//1개만남은경우 무조건 인덱스 0번
			if(moneyList.size() == 1) {
				idx = 0;
			}else if(moneyList.size() == 0) { //이미 받았거나, 남은금액이 없을경우 음수반환
				idx = -1; // -1은 받기 불가능한 값으로 지정
			}else {
				idx = (int)(Math.random() * moneyList.size());
			}
			
			if(idx != -1) {
				vo.setDetailno(moneyList.get(idx).getDetailno());
				vo.setSummaryno(moneyList.get(idx).getSummaryno());
				
				dao.updateGetMoney(vo);
				
				resMoney = moneyList.get(idx).getMoney();
			}else {
				resMoney = -1; //줄 수 없을 경우 -1 반환
			}
			
			resMap.put("resMoney", resMoney);
			resMap.put("resMessage","SUCCESS");
			
		} catch (Exception e) {
			resMap.put("resMoney", -1);
			resMap.put("resMessage","FAIL");
		}
		return new Gson().toJson(resMap);
		
	}

	@Override
	public String selectInfo(FlexVO vo) {
		FlexVO flexVO = new FlexVO(); 
		
		try {
			flexVO = dao.selectInfo(vo);
			
			try {
				if(!flexVO.getDetailList().isEmpty()) {
					for (ReceiveVO list : flexVO.getDetailList()) {
						flexVO.setTotalReceiveMoney((list.getMoney()+flexVO.getTotalReceiveMoney())); // 받은 금액 합산
					} 
				}
			} catch (Exception e) {
			}
			
			flexVO.setResMessage("SUCCESS");
			
		} catch (Exception e) {
			flexVO = new FlexVO();
			flexVO.setResMessage("FAIL");
			
		}
		
		return new Gson().toJson(flexVO);
	}
	
	
}
