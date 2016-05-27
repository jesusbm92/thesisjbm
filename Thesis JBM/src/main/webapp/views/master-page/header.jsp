<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>


<style>
.logo-pos {
	margin-left: 5%;
	margin-top: 2%;
	margin-bottom: 1%;
}
</style>

<!-- <div class="logo-pos"> -->
<!-- 	<img src="images/benefits-transparente.png" alt="Benefits Co., Inc." /> -->
<!-- </div> -->

<div class="navbar navbar-default navbar-fixed-top" role="navigation"
	style="font-size: 18px;">
	<div class="container">
		<!-- 		<div class="collapse navbar-collapse" -->
		<!-- 			id="bs-example-navbar-collapse-1"> -->
		<div class="navbar-header">
			<a class="brand" href=""><img src="images/logo_navbar.png"
				style="border: 0; height: 35px; margin-top: 5px;"></a>
			<!-- 			<a class="navbar-brand" href="#" src="images/benefits-transparente.png">Benefits</a> -->
		</div>

		<ul class="nav navbar-nav">
			<!-- Do not forget the "fNiv" class for the first level links !! -->
			<security:authorize access="isAuthenticated()">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown"><spring:message code="master.page.exams" /><b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li class="arrow"></li>
						<li><a href="exam/list.do"><spring:message
									code="master.page.administrator.action.1" /></a></li>
						<li><a href="exam/create.do"><spring:message
									code="master.page.exam.create" /></a></li>
					</ul></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown"><spring:message
							code="master.page.metadata" /><b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li class="arrow"></li>
						<li><a href="metadata/list.do"><spring:message
									code="master.page.metadatas" /></a></li>
						<li><a href="metadata/create.do"><spring:message
									code="master.page.metadata.create" /></a></li>
					</ul></li>
			</security:authorize>

			<security:authorize access="isAnonymous()">
				<li class="dropdown"><a class="fNiv" href="security/login.do"><spring:message
							code="master.page.login" /></a></li>
				<li class="dropdown"><a class="fNiv"
					href="register/registerUser.do"><spring:message
							code="master.page.register" /></a></li>
			</security:authorize>

			<security:authorize access="isAuthenticated()">
				<li class="dropdown"><a class="fNiv dropdown-toggle"
					data-toggle="dropdown"> <spring:message
							code="master.page.profile" /> (<security:authentication
							property="principal.username" />) <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li class="arrow"></li>
						<li class="divider"></li>
						<li><a href="j_spring_security_logout"><spring:message
									code="master.page.logout" /> </a></li>
					</ul></li>
			</security:authorize>
		</ul>
		<ul class="nav navbar-nav navbar-right">

			<security:authorize access="hasRole('ADMIN')">
				<li><a href="converter/select.do" title='Converter'><spring:message
							code="master.page.converter" /></a></li>
			</security:authorize>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown"><spring:message
						code="master.page.language" /> <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="?language=en">EN</a></li>
					<li><a href="?language=es">ES</a></li>
				</ul></li>
		</ul>
	</div>
</div>

<div class="container">
	<div class="col-md-6 col-centered">
		<jstl:choose>
			<jstl:when test="${message != null}">
				<br />
				<br />
				<br />
				<div class="alert alert-block alert-danger">
					<a class="close" data-dismiss="alert">×</a>
					<spring:message code="${message}" />
				</div>
			</jstl:when>
			<jstl:when test="${successMessage != null}">
				<br />
				<br />
				<br />
				<div class="alert alert-block alert-success">
					<a class="close" data-dismiss="alert">×</a>
					<spring:message code="${successMessage}" />
				</div>
			</jstl:when>
			<jstl:otherwise>
				<br />
				<br />
				<br />
				<br />
			</jstl:otherwise>
		</jstl:choose>
	</div>
</div>