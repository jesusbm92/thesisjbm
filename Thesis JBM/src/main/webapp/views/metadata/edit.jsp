<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>




<div class="container">
	<div class="row">
		<div class="col-md-8 col-md-offset-2">
			<div class="well well-sm">
				<form:form class="form-horizontal" method="POST"
					action="metadata/edit.do" modelAttribute="metadata">

					<form:hidden path="id" />
					<form:hidden path="version" />
					<form:hidden path="questions" />
					
					<fieldset>

						<!-- Name -->
						<div class="form-group">
							<form:label path="name" class="col-md-3 control-label" for="name">
								<spring:message code="metadata.name" />
							</form:label>
							<div class="col-md-6">
								<form:input path="name" id="name" name="name" type="text"
									class="form-control" />
							</div>
							<form:errors path="name" cssClass="error" />
						</div>


						<!-- Form actions -->
						<div class="form-group">
							<div class="col-md-12 text-center">
								<input type="submit" name="save" class="btn btn-primary btn-lg"
									value="<spring:message code="metadata.save" />" />
<%-- 								<jstl:if test="${!create}"> --%>
<!-- 									<a class="btn btn-primary btn-lg" data-toggle="modal" -->
<%-- 										data-target="#basicModal"><spring:message --%>
<%-- 											code="metadata.delete" /></a> --%>
<!-- 									<div class="modal fade" id="basicModal" tabindex="-1" -->
<!-- 										role="dialog" aria-labelledby="basicModal" aria-hidden="true"> -->
<!-- 										<div class="modal-dialog"> -->
<!-- 											<div class="modal-content"> -->
<!-- 												<div class="modal-header"> -->
<!-- 													<button type="button" class="close" data-dismiss="modal" -->
<!-- 														aria-hidden="true">&times;</button> -->
<!-- 													<h4 class="modal-title" id="myModalLabel"> -->
<%-- 														<spring:message code="metadata.confirm.title" /> --%>
<!-- 													</h4> -->
<!-- 												</div> -->
<!-- 												<div class="modal-body"> -->
<!-- 													<h3> -->
<%-- 														<spring:message code="metadata.confirm.body" /> --%>
<!-- 													</h3> -->
<!-- 												</div> -->
<!-- 												<div class="modal-footer"> -->
<!-- 													<button type="submit" name="delete" class="btn btn-default" -->
<!-- 														onclick="history.back()"> -->
<%-- 														<spring:message code="metadata.confirm.yes" /> --%>
<!-- 													</button> -->
<!-- 													<button type="button" class="btn btn-primary" -->
<!-- 														data-dismiss="modal"> -->
<%-- 														<spring:message code="metadata.confirm.no" /> --%>
<!-- 													</button> -->
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->
<%-- 								</jstl:if> --%>
								<a href="metadata/list.do"><input type="button"
									class="btn btn-primary btn-lg"
									value="<spring:message code="metadata.cancel"/>" id="cancelar"
									name="cancelar"
									onclick="self.location.href = metadata/list.do" /></a>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>