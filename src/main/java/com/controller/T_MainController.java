package com.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.CommandMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.T_MainService;
import com.vo.ticketingBean;

import annotation.maps.TestMappable;
import annotation.maps.TrainMappable;

@Controller

public class T_MainController {

	@Autowired
	TrainMappable trainMappable;

	@Autowired
	T_MainService tms;

	/*Ajax 테스트: db 쿼리문 넘기기*/
	@RequestMapping(value = "/json.do", method=RequestMethod.POST) 
	public @ResponseBody List<HashMap<String, String>> testJson() throws Exception {
		
	    List<HashMap<String, String>> resultList = trainMappable.selectlist();

	    return resultList; 

	}
	
	@RequestMapping(value = { "/help" }, method = RequestMethod.GET)
	public String helpPage(ModelMap m) {
		m.addAttribute("message", trainMappable.selectlist());
		
		return "help";
	} 
	
	@RequestMapping(value = { "/main", "/" }, method = RequestMethod.GET)
	public String trainMain(ModelMap m) throws Exception {
		m.addAttribute("selectlist", trainMappable.selectlist());
		m.addAttribute("stationInfo", trainMappable.stationInfo());
		
		return "main";
	}
	   
	@RequestMapping(value = { "/lookup" }, method = RequestMethod.GET)
	public String trainSearch(ModelMap m, @RequestParam("start_station") String start_station, @RequestParam("arrival_station") String arrival_station, @RequestParam("datepicker") String datepicker, @RequestParam("start_time") String start_time) throws Exception {
		m.addAttribute("selectlist", trainMappable.selectlist());
		m.addAttribute("stationInfo", trainMappable.stationInfo());
		m.addAttribute("startSt", trainMappable.stationInfo_all(start_station));	//사용자가 선택한 출발역
		m.addAttribute("arrivalSt", trainMappable.stationInfo_all(arrival_station));	//사용자가 선택한 도착역
		
		/*요일별 열차시간표*/
		String day=tms.getDateDay(datepicker, "yy/MM/dd");
		m.addAttribute("startTimearr", trainMappable.stTime_alllist(day));	
		
		/*역간 소요시간*/
		int time_from_startSt = tms.parseInt(trainMappable.stationInfo_time(start_station));
		int time_from_arvSt = tms.parseInt(trainMappable.stationInfo_time(arrival_station));
		String howlongTime=tms.StationDiv(time_from_startSt, time_from_arvSt);
		m.addAttribute("howlongTime", howlongTime); 
		
		/*진행방향에따른 운행시간*/
		String totalTime=tms.startStation(start_station, arrival_station); //출발역까지 소요시간가져오기
		m.addAttribute("startTimelist", tms.stationTimeAll(trainMappable.stTime_alllist(day), start_station, arrival_station, totalTime, "0"));	//출발시간
		m.addAttribute("arrivalTime", tms.stationTimeAll(trainMappable.stTime_alllist(day), start_station, arrival_station, totalTime, howlongTime)); //도착시간

		/*역간 요금*/
		int fare_from_startSt = tms.parseInt(trainMappable.stationInfo_fare(start_station));
		int fare_from_arvSt = tms.parseInt(trainMappable.stationInfo_fare(arrival_station));
		m.addAttribute("fare_adult", tms.StationDiv(fare_from_startSt, fare_from_arvSt)); 
		
		return "main";
	}     
	
	@RequestMapping(value = { "/seat" }, method = RequestMethod.GET)
	public String seat(ModelMap m, ticketingBean tb, HttpServletRequest request) {
		m.addAttribute("selectlist", trainMappable.selectlist());
		m.addAttribute("tb", tb.getTtList());
		return "seat"; 
	}
	
}