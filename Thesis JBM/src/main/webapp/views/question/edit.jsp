<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@page import="domain.Statistic"%>
<%@page import="domain.Question"%>

<style>
body {
	padding-top: 20px;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-10 col-md-offset-1">
			<div class="well well-sm">
				<form:form action="question/edit.do?exerciseId=${param.exerciseId }"
					method="POST" modelAttribute="question" role="form"
					class="form-horizontal">
					<form:hidden path="id" />
					<form:hidden path="version" />
					
					<form:hidden path="statistic" />
					
					<form:hidden path="answers" />
					<form:hidden path="exercises" />
					<form:hidden path="xml" />
					<form:hidden path="difficulty" />




					<fieldset>
						<br />
						<!-- Name input-->
						<div class="form-group">
							<form:label path="name" class="col-md-3 control-label" for="name">
								<spring:message code="question.name" />
							</form:label>
							<div class="col-md-6">
								<form:input path="name" id="name" name="name" type="text"
									class="form-control" />
							</div>
							<form:errors path="name" cssClass="error" />
						</div>

						<div class="form-group">
							<form:label path="text" class="col-md-3 control-label" for="text">
								<spring:message code="question.text" />
							</form:label>
							<div class="col-md-6">
								<form:input path="text" id="text" name="text" type="text"
									class="form-control" />
							</div>
							<form:errors path="text" cssClass="error" />
						</div>


						<div class="form-group">
							<form:label path="weight" class="col-md-3 control-label"
								for="weight">
								<spring:message code="question.weight" />
							</form:label>
							<div class="col-md-6">
								<form:input path="weight" id="weight" name="weight"
									type="number" min="0" step="0.01" minFractionDigits="2" maxFractionDigits="2"
									class="form-control" />
							</div>
							<form:errors path="weight" cssClass="error" />
						</div>

						<div class="form-group">
							<form:label path="weightfail" class="col-md-3 control-label"
								for="weightfail">
								<spring:message code="question.weightfail" />
							</form:label>
							<div class="col-md-6">
								<form:input path="weightfail" id="weightfail" name="weightfail"
									type="number" min="0" step="0.01" minFractionDigits="2" maxFractionDigits="2"
									class="form-control" />
							</div>
							<form:errors path="weightfail" cssClass="error" />
						</div>
						
						<form:select multiple="${metadatas.size()}" items="${metadatas}"
									itemLabel="name" id="id" code="question.metadata" path="metadata"
									class="form-control multiselect" />
								<form:errors path="metadata" cssClass="error" />


						<br />
						<div class="form-group">
							<div class="col-md-12 text-center">
								<input type="submit" name="save" class="btn btn-primary btn-lg"
									value="<spring:message code="question.save" />" />
								<jstl:if test="${!create}">
									<a class="btn btn-primary btn-lg" data-toggle="modal"
										data-target="#basicModal"><spring:message
											code="question.delete" /></a>
									<div class="modal fade" id="basicModal" tabindex="-1"
										role="dialog" aria-labelledby="basicModal" aria-hidden="true">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-hidden="true">&times;</button>
													<h4 class="modal-title" id="myModalLabel">
														<spring:message code="question.confirm.title" />
													</h4>
												</div>
												<div class="modal-body">
													<h3>
														<spring:message code="question.confirm.body" />
													</h3>
												</div>
												<div class="modal-footer">
													<button type="submit" name="delete" class="btn btn-default">
														<spring:message code="question.confirm.yes" />
													</button>
													<button type="button" class="btn btn-primary"
														data-dismiss="modal">
														<spring:message code="question.confirm.no" />
													</button>
												</div>
											</div>
										</div>
									</div>
								</jstl:if>
								<a href="history.back()"><input
									type="button" class="btn btn-primary btn-lg"
									value="<spring:message code="question.cancel"/>" id="cancelar"
									name="cancelar"
									onclick="history.back()" /></a>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>
