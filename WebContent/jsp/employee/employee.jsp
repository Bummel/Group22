<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Site</title>
<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/css.css" />" />
</head>
<body>

<div id="top">
	<div id="top_content">
		<jsp:include page="../head/head.jsp" />
		
		<div class="top_navi">
				<c:url value="employeeMitglVerw" var="url1">
					<c:param name="uid" value="${currentUser.identifier}" />
				</c:url>
				<section><a href ="${url1}">Mitgliederverwaltung</a></section>
				
				<c:url value="employeeFinanzVerw" var="url2">
					<c:param name="uid" value="${currentUser.identifier}" />
				</c:url>
				<section><a href ="${url2}">Finanzverwaltung</a></section>
							
				<sp:hasCapability capabilityName="admin">
					<c:url value="employeeSpieleVerw" var="url3">
						<c:param name="uid" value="${currentUser.identifier}" />
					</c:url>
					<section><a href ="${url3}">Spieleverwaltung</a></section>	
				</sp:hasCapability>	
		</div>	
		
		<div class="sub_navi" >
			<br>
		</div>	
	</div>
</div>
		
<div id="middle">

	<div class="main_content">

				<div class="current_content">
				Blablabla
				
				<sp:forEach items="${currentUser.getCapabilities()}" var="cap">
				<br>${cap}
				</sp:forEach>
				
				<br><br>
					
				</div>
	</div>
	
	<div class="side">

		<div id="side_calendar">
			<p>CALENDAR</p>
		</div>
		
		<div id="side_tipps">
			<p>TIPPS</p>
		</div>
	</div>

</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>




</body>
</html>



