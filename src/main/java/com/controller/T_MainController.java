package com.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.CommandMap;
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
		
		String day=tms.getDateDay(datepicker, "yy/MM/dd");
		m.addAttribute("startTimearr", trainMappable.stTime_alllist(day));	//요일별 시간표
		
		String colName="";  
		colName="TS_TOTAL_TIMET";
		String stationDiv=tms.StationDiv(start_station, arrival_station, colName);
		m.addAttribute("howlongTime", stationDiv); 
		
		String totalTime=tms.startStation(start_station, arrival_station, colName); //출발역까지 소요시간가져오기
		m.addAttribute("startTimelist", tms.stationTimeAll(trainMappable.stTime_alllist(day), start_station, arrival_station, totalTime, "0"));	//출발시간
   
		m.addAttribute("arrivalTime", tms.stationTimeAll(trainMappable.stTime_alllist(day), start_station, arrival_station, totalTime, stationDiv)); //도착시간
		   
		colName="TS_FARE_ADULT";
		m.addAttribute("fare_adult", tms.StationDiv(start_station, arrival_station, colName)); //요금
		
		
		return "main";
	}     
	  
	@RequestMapping(value = { "/seat" }, method = RequestMethod.GET)
	public String seat(ModelMap m) {
		m.addAttribute("selectlist", trainMappable.selectlist());
		return "seat";
	}
	
}