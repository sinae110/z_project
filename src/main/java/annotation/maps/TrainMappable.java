package annotation.maps;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Select;


public interface TrainMappable {

	/* XML 방식 */
	public List<?> selectTest();

	/* 어노테이션 방식 */

	/* 메인 select 박스 */
	@Select("SELECT * FROM T_SELECT ORDER BY T_NUM ASC")
	public List<HashMap<String, String>> selectlist();
	
	/* 기차출발시간 */
	@Select("SELECT * FROM T_TIME_INFO ORDER BY TT_NUM ASC")
	public List<HashMap<String, String>> first_stTime_list();
	
	/* 역정보 */
	@Select("SELECT * FROM T_STATION_INFO ORDER BY TS_CODE ASC")
	public List<HashMap<String, String>> stationInfo();
	
	/* 역정보 기차코드로 찾기 */
	@Select("SELECT * FROM T_STATION_INFO WHERE TS_CODE=#{stationCode}")
	public List<HashMap<String, String>> stationInfo_all(String stationCode);
	
	/* 기차코드로 소요시간 반환하기*/
	@Select("SELECT TS_TOTAL_TIMET FROM T_STATION_INFO WHERE TS_CODE=#{stationCode}")
	public String stationInfo_time(String stationCode);
	
	/* 기차코드로 요금 반환하기*/
	@Select("SELECT TS_FARE_ADULT FROM T_STATION_INFO WHERE TS_CODE=#{stationCode}")
	public String stationInfo_fare(String stationCode);
	
	
	/* 기차의 최종 종착역의 소요시간 반환하기: 역방향 소요시간계산*/
	@Select("SELECT TS_TOTAL_TIMET FROM T_STATION_INFO WHERE TS_CODE = (SELECT max(TS_CODE) FROM T_STATION_INFO)")
	public String lastSt_time(String startSt, String arrivalSt);
  
	/*역별 출발시간 구하기*/
	@Select("SELECT TT_START_TIMEC FROM T_TIME_INFO WHERE TT_DAYS LIKE '%'||#{day}||'%' ORDER BY TT_NUM ASC")
	public String[] first_stTime_arr(String day);
	
	/*역별 출발시간 리스트로 반환*/
	
	 @Select("SELECT * FROM T_TIME_INFO WHERE TT_DAYS LIKE '%'||#{day}||'%' ORDER BY TT_NUM ASC"
	 ) public List<HashMap<String, String>> stTime_alllist(String day);
	
}
