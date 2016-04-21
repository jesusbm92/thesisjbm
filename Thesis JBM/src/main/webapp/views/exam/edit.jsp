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
body {
	padding-top: 20px;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-10 col-md-offset-1">
			<div class="well well-sm">
				<form:form action="exercise/administrator/edit.do"
					modelAttribute="exercise" role="form" class="form-horizontal"
					enctype="multipart/form-data">
					<form:hidden path="id" />
					<form:hidden path="version" />
					<form:hidden path="exerciseGroups" />
					<form:hidden path="entityLanguage" />

					<fieldset>
						<h1 class="text-center">
							<spring:message code="exercise.details" />
						</h1>
						<br />
						<div class="form-group">
							<form:label path="muscle" for="muscleId"
								class="col-md-3 control-label">
								<spring:message code="exercise.muscle" />
							</form:label>
							<div class="col-sm-6">
								<form:select path="muscle" id="muscleId" class="form-control">
									<jstl:forEach var="var" items="${map}">
										<form:option value="${var.value}">${var.key}</form:option>
									</jstl:forEach>
								</form:select>

							</div>
							<form:errors path="muscle" cssClass="error" />
						</div>
						<!-- Name input-->
						<div class="form-group">
							<form:label path="name" class="col-md-3 control-label" for="name">
								<spring:message code="exercise.name" />
							</form:label>
							<div class="col-md-6">
								<form:input path="name" id="name" name="name" type="text"
									class="form-control" />
							</div>
							<form:errors path="name" cssClass="error" />
						</div>

						<div class="form-group">
							<form:label path="description" class="col-md-3 control-label"
								for="description">
								<spring:message code="training.description" />
							</form:label>
							<div class="col-md-6">
								<form:textarea path="description" id="description"
									name="description" type="text" class="form-control" />
							</div>
							<form:errors path="description" cssClass="error" />
						</div>
						<div class="form-group">
							<form:label path="repetitions" class="col-md-3 control-label"
								for="repetitions">
								<spring:message code="exercise.repetitions" />
							</form:label>
							<div class="col-md-6">
								<form:input path="repetitions" id="repetitions"
									name="repetitions" type="text" class="form-control" />
							</div>
							<form:errors path="repetitions" cssClass="error" />
						</div>
						<div class="form-group">
							<form:label path="cycles" class="col-md-3 control-label"
								for="cycles">
								<spring:message code="exercise.cycles" />
							</form:label>
							<div class="col-md-6">
								<form:input path="cycles" id="cycles" name="cycles"
									type="text" class="form-control" />
							</div>
							<form:errors path="cycles" cssClass="error" />
						</div>
						<div class="form-group">
							<form:label path="urlYoutube" class="col-md-3 control-label"
								for="urlYoutube">
								<spring:message code="exercise.urlYoutube" />
							</form:label>
							<div class="col-sm-6">
								<form:input path="urlYoutube" id="urlYoutube"
									class="form-control" />
								<a
									href="http://www.youtube.com/results?search_query=${exercise.name}"
									type="button" target="_blank"><spring:message
										code="exercise.addLink" /></a>
							</div>
							<form:errors path="urlYoutube" cssClass="error" />
						</div>
						<div class="form-group">
							<form:label path="image" class="col-md-3 control-label"
								for="image">
								<spring:message code="exercise.image" />
							</form:label>
							<div class="col-md-6">
								<form:input path="image" id="image" type="file" />
							</div>
							<form:errors path="image" cssClass="error" />

							<jstl:if test="${exercise.validImage }">
								<img src="image/showExercise.do?exerciseId=${exercise.id }"
									style="height: 100px" class="img-thumbnail" />
							</jstl:if>
						</div>
						<br />
						<div class="form-group">
							<div class="col-md-12 text-center">
								<input type="submit" name="save" class="btn btn-primary btn-lg"
									value="<spring:message code="plan.save" />" />
								<jstl:if test="${!create}">
									<a class="btn btn-primary btn-lg" data-toggle="modal"
										data-target="#basicModal"><spring:message
											code="plan.delete" /></a>
									<div class="modal fade" id="basicModal" tabindex="-1"
										role="dialog" aria-labelledby="basicModal" aria-hidden="true">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-hidden="true">&times;</button>
													<h4 class="modal-title" id="myModalLabel">
														<spring:message code="plan.confirm.title" />
													</h4>
												</div>
												<div class="modal-body">
													<h3>
														<spring:message code="plan.confirm.body" />
													</h3>
												</div>
												<div class="modal-footer">
													<button type="submit" name="delete" class="btn btn-default"
														onclick="history.back()">
														<spring:message code="plan.confirm.yes" />
													</button>
													<button type="button" class="btn btn-primary"
														data-dismiss="modal">
														<spring:message code="plan.confirm.no" />
													</button>
												</div>
											</div>
										</div>
									</div>
								</jstl:if>
								<a href="plan/administrator/list.do"><input type="button"
									class="btn btn-primary btn-lg"
									value="<spring:message code="plan.cancel"/>" id="cancelar"
									name="cancelar"
									onclick="self.location.href = plan/administrator/list.do" /></a>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>
