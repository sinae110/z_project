<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>

  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <title>Recruit</title>
  <script>
  $( function() {
    $( "#datepicker" ).datepicker({
    	dateFormat: "yy/mm/dd",
    	minDate: 0
    });
    
  } );
  </script>
   <style>
   
	th,td {text-align:center;}

   </style>
</head>
<body>

<div class="container">            

      <form name='StSerchrm' method='GET' action='lookup'>
	  <div id="nav-content">
	    <div id="station_div" class="navbar navbar-expand-sm">
		<!-- 역 -->
		<ul class="navbar-nav">
        <li><select class="form-control" name="start_station" id="start_station" Onchange="startst_Option()"> 
              <option value="-1">출발역</option>
              <c:forEach var="i" items="${stationInfo}">
              <option value="${i.TS_CODE}">${i.TS_NAME}</option>
              </c:forEach>
            </select>
        </li>    
        <li><select class="form-control" name="arrival_station" id="arrival_station" Onchange="arrivalst_Option()"> 
              <option value="-2">도착역</option>
              <c:forEach var="i" items="${stationInfo}">
              <option value="${i.TS_CODE}">${i.TS_NAME}</option>
              </c:forEach>
            </select> 
        </li>
		</ul>
		</div>
		<div id="station_subdiv">
        <!-- 날짜 -->
        <ul class="navbar-nav">
        <li><input class="form-control" type="text" name="datepicker" id="datepicker" placeholder="출발날짜">
        <li><select class="form-control" name="start_time" id="start_time"> 
              <option value="">출발시간</option>
              <c:forEach var="i" items="${selectlist}">
              <option value="${i.T_TIME}">${i.T_TIME}시 이후</option>
              </c:forEach>
            </select> 
        </li>
        </ul> 
        <!-- 인원 -->
        <ul class="navbar-nav">
        <li><select class="form-control" name="adult" id="adult"> 
              <option value="0">어른 0명</option>
 			  <c:forEach var="i" items="${selectlist}">
              <option value="${i.T_ADULT}">어른 ${i.T_ADULT}명</option>
              </c:forEach>
            </select>
        <li><select class="form-control" name="child" id="child"> 
              <option value="0">어린이(만12세이하) 0명</option>
 			  <c:forEach var="i" items="${selectlist}">
              <option value="${i.T_CHILD}">어린이 ${i.T_CHILD}명</option>
              </c:forEach>
            </select> 
        </li>
        </ul>
        </div>
        <div>
 		<ul>
        <li><space><input type="button" class="btn" id="train_search" value="조회하기" onclick="selectCheck();" /></li>
        </ul>
		</div>  
	  </div>	
	  </form>

  <table class="table" id="maintable">
    <thead>
      <tr>
      	<th>열차번호</th>
      	<th>출발역</th>
      	<th>도착역</th>
      	<th>좌석선택</th>
      	<th>운임요금</th>
      	<th>출발시간</th>
      	<th>도착시간</th>
      	<th>소요시간</th>
      </tr>
    </thead>
    <tbody>
     <c:set var = "St_name" value = "${startSt[0].TS_NAME}"/>
     <c:set var = "Arv_name" value = "${arrivalSt[0].TS_NAME}"/>
     <c:forEach var="i" items="${startTimearr}" varStatus="status"> 
     <form:form commandName="ticketingBean" name="seatSelectfrm" method='GET' action='seat'>   
        <tr>
			<td>${i.TT_TRAINNUM}</td>
			<td>${St_name}<input type="hidden" name="ttList[${status.index}].st_name" value="${St_name}"></td>
			<td>${Arv_name}<input type="hidden" name="ttList[${status.index}].arv_name" value="${Arv_name}"></td>
			<td><input type="button" class="btn btn-warning" id="ticketing" value="좌석예매" onclick="ticketingSubmit(${status.index});" /></td>
			<td>${fare_adult}원</td>
			<td>${startTimelist[status.index]}<input type="hidden" name="ttList[${status.index}].startTimelist" value="${startTimelist[status.index]}"></td>
			<td>${arrivalTime[status.index]}<input type="hidden" name="ttList[${status.index}].arrivalTime" value="${arrivalTime[status.index]}"></td>
			<td>${howlongTime}분<input type="hidden" name="ttList[${status.index}].howlongTime" value="${howlongTime}"></td>
	  	</tr>    
	</form:form> 		
    </c:forEach> 
    </tbody>
   
 </table>

</div>  
<script>
/* 출발지와 도착역이 동일할 경우 감추기 */
function startst_Option() {
var startst = $('#start_station option:selected').val();
$("#arrival_station").find("option").each(function() {
	 if(this.value == startst) {
	  $(this).hide();
	 } else {
	  $(this).show();
	 }
	});
}

function arrivalst_Option() {
	var arrivalst = $('#arrival_station option:selected').val();
	$("#start_station").find("option").each(function() {
		 if(this.value == arrivalst) {
		  $(this).hide();
		 } else {
		  $(this).show();
		 }
		});
	}
	
function ticketingSubmit(index) {
	var frm = document.seatSelectfrm[index];
	//alert(frm)
	frm.submit();
	}
	
/* 셀렉트박스 유효성 검사 */
function selectCheck() {
	var form = document.StSerchrm;
	/*역*/
	if (form.start_station.value == form.arrival_station.value) {
              alert("출발역과 도착역이 동일합니다");
              return;
	}
	if (form.start_station.value == "-1") {
        alert("출발역을 선택하세요");
        return;
	}
	if (form.arrival_station.value == "-2") {
        alert("도착역을 선택하세요");
        return;
	}
	/*날짜*/
	if (form.datepicker.value == "") {
        alert("출발날짜를 선택하세요");
        return;
	}
	if (form.start_time.value == "") {
        alert("출발시간을 선택하세요");
        return;
	}
	/*요금*/
	if (form.adult.value == "0" && form.child.value == "0") {
        alert("승차인원을 선택하세요");
        return;
	}
	form.submit();
}
</script>

</body>
</html>
