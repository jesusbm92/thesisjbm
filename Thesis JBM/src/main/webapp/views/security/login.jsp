<%--
 * login.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<style>
.colorgraph {
	height: 5px;
	border-top: 0;
	background: #993232;
	border-radius: 5px;
}
</style>

<div class="container">

	<div class="row" style="margin-top: 20px">
		<div
			class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
			<form:form action="j_spring_security_check"
				modelAttribute="credentials" role="form">
				<fieldset>
					<h2>
						<spring:message code="master.page.login" />
					</h2>
					<hr class="colorgraph">
					<div class="form-group">
						<spring:message code="security.username" var="username" />
						<input path="username" type="text" name="username" id="username"
							class="form-control input-lg" placeholder="${username}">
						<form:errors class="error" path="username" />
					</div>
					<div class="form-group">
						<spring:message code="security.password" var="password" />
						<input path="password" type="password" name="password"
							id="password" class="form-control input-lg"
							placeholder="${password}">
						<form:errors class="error" path="username" />
					</div>
					</span> <br />
					<jstl:if test="${showError == true}">
						<div class="error">
							<spring:message code="security.login.failed" />
						</div>
					</jstl:if>
					<div class="row">

						<div class="col-xs-12 col-sm-12 col-md-12">
							<input type="submit" class="btn btn-lg btn-success btn-block"
								value="<spring:message code="security.login" />">
						</div>
					</div>
					<br />
				</fieldset>
			</form:form>
		</div>
	</div>

</div>

<br />
<br />
<br />