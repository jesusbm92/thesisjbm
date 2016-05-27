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
					action="converter/select.do" modelAttribute="fileform">
		

					<fieldset>

						<br />
						<!-- Name -->
						<div class="form-group">
							<form:label path="directory" class="col-md-3 control-label"
								for="directory">
								<spring:message code="converter.directory" />
							</form:label>
							<div class="col-md-6">
								<form:input path="directory" id="directory" type="text" />
							</div>
							<form:errors path="directory" cssClass="error" />

						</div>

						<!-- Form actions -->
						<div class="form-group">
							<div class="col-md-12 text-center">
								<input type="submit" name="save" class="btn btn-primary btn-lg"
									value="<spring:message code="metadata.save" />" />
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>