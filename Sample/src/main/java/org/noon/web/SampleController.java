package org.noon.web;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.noon.domain.FlexVO;
import org.noon.domain.ReceiveVO;
import org.noon.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping("/sample/*")
public class SampleController {

	private static final Logger logger = Logger.getLogger(SampleController.class);

	@Autowired
	SampleService service;
	
	
	@PostMapping("/flex")
	@ResponseBody
	public String flexMoney(@RequestBody HashMap<String, Object> body, @RequestHeader HashMap<String, Object> header) throws Exception{
		FlexVO vo = new FlexVO();
		Gson gson = new Gson();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		vo.setHolder(Integer.valueOf((String)header.get("x-user-id")));
		vo.setRoom((String)header.get("x-room-id"));
		vo.setMoney((int) body.get("money"));
		vo.setPersonCnt((int) body.get("count"));
		
		service.createflexMoney(vo);
		
		resMap.put("token", vo.getToken());
		resMap.put("resMessage", vo.getResMessage());
		
		return gson.toJson(resMap);
	}
	
	@PutMapping("/receive")
	@ResponseBody
	public String receiveMoney(@RequestBody HashMap<String, Object> body, @RequestHeader HashMap<String, Object> header) throws Exception{
		ReceiveVO vo = new ReceiveVO();
		
		vo.setPno(Integer.valueOf((String)header.get("x-user-id")));
		vo.setRoom((String)header.get("x-room-id"));
		vo.setToken((String)body.get("token"));
		
		return service.updateGetMoney(vo);
	}
	
	
	@GetMapping("/info")
	@ResponseBody
	public String infoFlex(@RequestBody HashMap<String, Object> body, @RequestHeader HashMap<String, Object> header) throws Exception{
		FlexVO vo = new FlexVO();
		
		vo.setHolder(Integer.valueOf((String)header.get("x-user-id")));
		vo.setRoom((String)header.get("x-room-id"));
		vo.setToken((String)body.get("token"));
		
		return service.selectInfo(vo);
	}
	
}
