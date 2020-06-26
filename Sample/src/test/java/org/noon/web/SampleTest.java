package org.noon.web;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.noon.domain.FlexVO;
import org.noon.domain.ReceiveVO;
import org.noon.service.SampleService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class SampleTest {

	private static Logger logger = Logger.getLogger(SampleTest.class);
	
	@Resource
	SampleService service;
	
	@Test
	public void flexMoneyTest(){ //뿌리기 테스트
		FlexVO vo = new FlexVO();
		
		//테스트 값 세팅
		vo.setMoney(100000);
		vo.setPersonCnt(10);
		vo.setHolder(4);
		vo.setRoom("a");
		
		service.createflexMoney(vo);
	}
	
	@Test
	public void receiveMoneyTest(){ //받기 테스트
		ReceiveVO vo = new ReceiveVO();
		
		//테스트 값 세팅
		vo.setPno(5);
		vo.setRoom("a");
		vo.setToken("LMR");
		
		//-1 반환 시 뿌린사람이거나 이미 받은사람 
		logger.info(service.updateGetMoney(vo));
	}
	
	@Test
	public void selectInfo(){ //조회 테스트
		FlexVO vo = new FlexVO();
		
		//테스트값 세팅
		vo.setHolder(4);
		vo.setRoom("a");
		vo.setToken("LMR");
		
		//-1 반환 시 뿌린사람이거나 이미 받은사람 
		logger.info(service.selectInfo(vo));
	}

	
}
