<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html lang="kor">
<head>
<!-- Theme Made By www.w3schools.com - No Copyright -->
<title>
<tiles:insertAttribute name="title"/>
</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Montserrat"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<style>
html {
height: 100%;
}
body {
margin: 0;
height: 100%;
}
.container {
min-height: 70%;
position: relative;
}

footer {
position: relative;
bottom: 0;
}


 	#maintable {margin-top:1%;}
    li { list-style: none }
	tab {padding-left: 4em;}
	space {padding-left: 1em;}
    #train_search {height:45px; width:90px; color:white; background-color:#1F2358;}
    #nav-content {margin:4% 0% 10% 0%; }
    #pagebutton {text-align:center; margin:5% 5%}
    #center {text-align:center;}
    #datepicker {width:120px;}

	#ticketInfo {font-weight:bold; font-size:1.2em; margin-top:20px;}

.bg-1 {
	background-color: #1abc9c; /* Green */
	color: #ffffff;
}

.bg-2 {
	background-color: #474e5d; /* Dark Blue */
	color: #ffffff;
}

.bg-3 {
	background-color: #ffffff; /* White */
	color: #555555;
}

.bg-4 {
	background-color: #2f2f2f; /* Black Gray */
	color: #fff;
}

.container-fluid {
	padding-top: 50px;
	padding-bottom: 50px;
}

.navbar {
	padding-top: 15px;
	padding-bottom: 15px;
	border: 0;
	border-radius: 0;
	margin-bottom: 0;
	font-size: 12px;
	letter-spacing: 5px;
}

.navbar-nav  li a:hover {
	color: #1abc9c !important;
}
</style>
</head>
<body>

	<tiles:insertAttribute name="menu" />
	
	<tiles:insertAttribute name="header" />

	<tiles:insertAttribute name="body" />

</body>

	<!-- Footer -->
	<footer class="container-fluid bg-4 text-center">
		<tiles:insertAttribute name="footer" />
	</footer>

</html>