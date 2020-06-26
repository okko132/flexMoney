package org.noon.service;

import org.noon.domain.FlexVO;
import org.noon.domain.ReceiveVO;

public interface SampleService {
	public void createflexMoney(FlexVO vo);
	public String updateGetMoney(ReceiveVO vo);
	public String selectInfo(FlexVO vo);
}
