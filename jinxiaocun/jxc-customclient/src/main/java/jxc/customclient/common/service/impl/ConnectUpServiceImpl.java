//package com.sflx.mp.common.service.impl;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.sflx.mp.common.init.SystemInit;
//import com.sflx.mp.common.service.ConnectUpService;
//import com.sflx.mp.tc.dto.jd.SceneryDto;
//import com.sflx.mp.tc.pojo.Response;
//import com.sflx.mp.tc.service.ConnectService;
//@Service
//@Transactional
//public class ConnectUpServiceImpl implements ConnectUpService{
//	
////	private ConnectService  service;
//	//获取系统参数
//	private SystemInit systemInit;
//	
//	@Override
//	public Response getSceneryList(String key,SceneryDto dto) {
//		ConnectService cs=systemInit.getServiceMap().get(key);
//		return cs.getResponseByRequest(dto);
//	}
//	
//	
//}
