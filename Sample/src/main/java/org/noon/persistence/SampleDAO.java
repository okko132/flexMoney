package org.noon.persistence;

import java.util.List;

import org.noon.domain.FlexVO;
import org.noon.domain.ReceiveVO;

public interface SampleDAO {
	public void createflexMoney(FlexVO vo) throws Exception;
	public void updateGetMoney(ReceiveVO vo) throws Exception;
	public List<FlexVO> selectAvailableMoney(ReceiveVO vo) throws Exception;
	public FlexVO selectInfo(FlexVO vo) throws Exception;
}
