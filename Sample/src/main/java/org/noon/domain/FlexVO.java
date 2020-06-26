package org.noon.domain;

import java.util.ArrayList;

import lombok.Data;
@Data
public class FlexVO {
	
	String token;
	String room;
	int holder;
	int personCnt;
	int money;
	int totalReceiveMoney;
	int summaryno;
	int detailno;
	String regdt;
	ArrayList<Integer> moneyList;
	ArrayList<ReceiveVO> detailList = new ArrayList<ReceiveVO>();
	String resMessage;

}
