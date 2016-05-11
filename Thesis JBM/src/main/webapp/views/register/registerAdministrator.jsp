<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<style>
.colorgraph {
	height: 5px;
	border-top: 0;
	background: #993232;
	border-radius: 5px;
}
</style>

<h1 class="text-center"><spring:message code="register.admin.create" /></h1>

<div class="container">

	<div class="row" style="margin-top: 20px">
		<div
			class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
			<form:form action="register/saveAdministrator.do"
				modelAttribute="administratorForm" method="post" class="form"
				role="form">
				<form:hidden path="id" />
				<form:hidden path="version" />
				<fieldset>
					<hr class="colorgraph">
					<h3>
						<spring:message code="register.userInfoHeader" />
					</h3>
					<div class="form-group">
						<spring:message code="register.username" var="username" />
						<form:input path="username" type="text" name="username" id="username"
							class="form-control input-lg" placeholder="${username}" />
						<form:errors class="error" path="username" />
					</div>
					<div class="row">
						<div class="col-xs-6 col-md-6">
							<div class="form-group">
								<spring:message code="register.password" var="password" />
								<form:input path="password" type="password" name="password"
									id="password" class="form-control input-lg"
									placeholder="${password}" />
								<form:errors class="error" path="password" />
							</div>
						</div>
						<div class="col-xs-6 col-md-6">
							<div class="form-group">
								<spring:message code="register.passwordRepeat"
									var="repeatPassword" />
								<form:input path="repeatPassword" type="password"
									name="repeatPassword" id="repeatPassword"
									class="form-control input-lg" placeholder="${repeatPassword}" />
								<form:errors class="error" path="repeatPassword" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<spring:message code="register.email" var="email" />
						<form:input path="email" type="email" name="email" id="email"
							class="form-control input-lg" placeholder="${email}" />
						<form:errors class="error" path="username" />
					</div>
					<h3>
						<spring:message code="register.personalInfoHeader" />
					</h3>

					<div class="row">
						<div class="col-xs-6 col-md-6">
							<div class="form-group">
								<spring:message code="register.name" var="name" />
								<form:input path="name" type="text" name="name" id="name"
									class="form-control input-lg" placeholder="${name}" />
								<form:errors class="error" path="name" />
							</div>
						</div>
						<div class="col-xs-6 col-md-6">
							<div class="form-group">
								<spring:message code="register.surname" var="surname" />
								<form:input path="surname" type="text" name="surname" id="surname"
									class="form-control input-lg" placeholder="${surname}" />
								<form:errors class="error" path="surname" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-6 col-md-6">
							<div class="form-group">
								<spring:message code="register.city" var="city" />
								<form:input path="city" type="text" name="city" id="city"
									class="form-control input-lg" placeholder="${city}" />
								<form:errors class="error" path="city" />
							</div>
						</div>
						<div class="col-xs-6 col-md-6">
							<div class="form-group">
								<spring:message code="register.nationality" var="nationality" />
								<form:input path="nationality" type="text" name="nationality"
									id="nationality" class="form-control input-lg"
									placeholder="${nationality}" />
								<form:errors class="error" path="nationality" />
							</div>
						</div>
					</div>
					<hr class="colorgraph">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<input type="submit" name="save"
								class="btn btn-lg btn-primary btn-block"
								value="<spring:message code="register.create" />">
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
