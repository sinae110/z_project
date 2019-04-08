<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
	<link rel="stylesheet" type="text/css" href="https://jsc.mm-lamp.com/jQuery-Seat-Charts/jquery.seat-charts.css">    
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/seat_style.css"> 
		<div class="wrapper">
			<div class="container">
				<div id="ticketInfo">
					<c:forEach items="${tb}" var="list" >  
					${list.startTimelist} 
					${list.st_name} 
					</c:forEach> ->
					<c:forEach items="${tb}" var="list" >  
					${list.arv_name}
					</c:forEach> 
				</div>
				
				<div id="seat-map">
					<div class="front-indicator">Front</div>
				</div>
				<div class="booking-details">
					<h2>예약 상세정보</h2>
					
					<h3> 선택된 좌석 (<span id="counter">0</span>):</h3>
					<ul id="selected-seats"></ul>

					<div id="buyticket">
					<button class="btn">예매하기</button>
					</div>
					<div id="legend"></div>
				</div>
			</div>
		</div>
		
		<script src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
		<script src="https://jsc.mm-lamp.com/jQuery-Seat-Charts/jquery.seat-charts.js"></script>
		
		<script>
		$(document).ready(function(){
	        $.ajax({
	            type : "POST",
	            url : "json.do",
	            contentType : "application/json; charset=UTF-8",
	            error : function(){
	                alert('실패');
	            },
	            success : function(data){
	                alert( "데이터 값 : " +JSON.stringify(data, ["T_TIME"])) ;
	              /* console.log(JSON.stringify(target, function(key, value){
    return key !== "age" ? value : undefined;
}));
 */
	            }
	        });
	    });


		
			var firstSeatLabel = 1;
		
			$(document).ready(function() {
				var $cart = $('#selected-seats'),
					$counter = $('#counter'),
					$total = $('#total'),
					sc = $('#seat-map').seatCharts({
					map: [
						'ee_ee',
						'ee_ee',
						'ee_ee',
						'ee_ee',
						'ee_ee',
					],
					seats: {
						e: {
							price   : 40,
							classes : 'economy-class', //your custom CSS class
							category: 'Economy Class'
						}					
					},
					naming : {
						top : false,
						getLabel : function (character, row, column) {
							return firstSeatLabel++;
						},
					},
					legend : {
						node : $('#legend'),
					    items : [
							[ 'e', 'available',   '예약가능 좌석'],
							[ 'f', 'unavailable', '예약불가능 좌석']
					    ]					
					},
					click: function () {
						if (this.status() == 'available') {
							//let's create a new <li> which we'll add to the cart items
							$('<li>'+this.data().category+' Seat # '+this.settings.label+': <b>$'+this.data().price+'</b> <a href="#" class="cancel-cart-item">[cancel]</a></li>')
								.attr('id', 'cart-item-'+this.settings.id)
								.data('seatId', this.settings.id)
								.appendTo($cart);
							
							/*
							 * Lets update the counter and total
							 *
							 * .find function will not find the current seat, because it will change its stauts only after return
							 * 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
							 */
							$counter.text(sc.find('selected').length+1);
							$total.text(recalculateTotal(sc)+this.data().price);
							
							return 'selected';
						} else if (this.status() == 'selected') {
							//update the counter
							$counter.text(sc.find('selected').length-1);
							//and total
							$total.text(recalculateTotal(sc)-this.data().price);
						
							//remove the item from our cart
							$('#cart-item-'+this.settings.id).remove();
						
							//seat has been vacated
							return 'available';
						} else if (this.status() == 'unavailable') {
							//seat has been already booked
							return 'unavailable';
						} else {
							return this.style();
						}
					}
				});

				//this will handle "[cancel]" link clicks
				$('#selected-seats').on('click', '.cancel-cart-item', function () {
					//let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
					sc.get($(this).parents('li:first').data('seatId')).click();
				});

				//let's pretend some seats have already been booked
				sc.get(['1_2', '4_1']).status('unavailable');
		
		});

		function recalculateTotal(sc) {
			var total = 0;
		
			//basically find every selected seat and sum its price
			sc.find('selected').each(function () {
				total += this.data().price;
			});
			
			return total;
		}
		
		</script>

