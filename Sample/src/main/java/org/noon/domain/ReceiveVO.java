package org.noon.domain;

import lombok.Data;

@Data
public class ReceiveVO {

	int pno;
	int money;
	String token;
	String regdt;
	int detailno;
	int summaryno;
	String room;
}
