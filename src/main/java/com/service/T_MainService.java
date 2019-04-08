package com.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vo.ticketingBean;

import annotation.maps.TrainMappable;
@Service
public class T_MainService {

	@Autowired
	TrainMappable trainMappable;
	
	/*특정 날짜의 요일구하기*/
	public String getDateDay(String date, String dateType) throws Exception {
	 	     
	    String day = "" ;
	     
	    SimpleDateFormat dateFormat = new SimpleDateFormat(dateType) ;
	    Date nDate = dateFormat.parse(date) ;
	     
	    Calendar cal = Calendar.getInstance() ;
	    cal.setTime(nDate);
	     
	    int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;

	    switch(dayNum){
	        case 1: day = "일";
	            break ;
	        case 2: day = "월";
	            break ;
	        case 3: day = "화";
	            break ;
	        case 4: day = "수";
	            break ;
	        case 5: day = "목";
	            break ;
	        case 6: day = "금";
	            break ;
	        case 7: day = "토";
	            break ;        
	    }
	    return day ;
	 
	}

	
	/*출발방향 정하기*/
	public String startStation(String startSt, String arrivalSt) {
		/*소요시간 계산하기위해 int형으로 변환하기*/
		int stst = Integer.parseInt(startSt); 
		int arst = Integer.parseInt(arrivalSt); 
		String total="";	//역간 최종소요시간
		String startStaion=trainMappable.stationInfo_all(startSt).get(0).get("TS_TOTAL_TIMET");
		if (stst < arst) {	//정방향일 때   
			total=startStaion;	//DB에 있는 소요시간을 그대로 가져옴
		} else if (stst > arst) {	//역방향일 때
			int lastTime = Integer.parseInt(trainMappable.lastSt_time(startSt, arrivalSt));	//부산역까지:150분
			int divTime = lastTime-Integer.parseInt(startStaion);
			total=Integer.toString(divTime);	//종착역에서 출발역까지의 시간차를 역으로 계산함
		}
		return total;
	}   
	
	
	/*스트링을 int로 반환하기*/
	public int parseInt(String text) {
		int parseInt=Integer.parseInt(text);
		return parseInt;
	}
	
	/*역간 차이 계산하기*/
	public String StationDiv(int startSt, int arrivalSt) {
		/*소요시간 계산하기위해 int형으로 변환하기*/
		String total="";	//역간 최종소요시간
			int divTime = arrivalSt-startSt; //도착역 누적값에서 출발역 값 빼기
			total=Integer.toString(Math.abs(divTime)); //절대값으로 반환
		return total;
	}   
	
	
	/*각역간의 소요시간으로 기차 출발시간과 도착시간 구하기*/
	public ArrayList<String> stationTimeAll(List<HashMap<String, String>> stTimelist, String startSt, String arrivalSt, String totalTime, String divTime) {
		DateFormat f = new SimpleDateFormat("kk:mm");

		ArrayList<String> station = new ArrayList<String>();	//소요시간 더해서 반환할 배열
		int ttime = Integer.parseInt(totalTime);  	//소요시간
		int atime = Integer.parseInt(divTime);  	//소요시간
		
		try {
			Calendar cal = Calendar.getInstance();
	    	for(int i=0; i<stTimelist.size(); i++) {
	        Date date = f.parse(stTimelist.get(i).get("TT_START_TIMEC"));
	        
	        cal.setTime(date);
	        cal.add(Calendar.MINUTE, ttime+atime);	//날짜 더하기
	        station.add(f.format(cal.getTime())); 	//배열에 담기
	        //System.out.println(f.format(cal.getTime()));
	    	}
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return station;
	} 
	
	
}
