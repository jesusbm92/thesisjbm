<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1 class="text-center">
	<spring:message code="exercise.exercises" />
</h1>

<jstl:if test="${pickToCopy }">
	<form:form action="exercise/search.do?examId=${param.examId }"
		method="POST" modelAttribute="filterForm" role="form"
		class="form-horizontal">
		<fieldset>
			<br />
			<!-- Name input-->
			<div class="form-group">
				<form:label path="search" class="col-md-3 control-label"
					for="search">
					<spring:message code="exercise.search" />
					<form:select path="filter">
						<form:option value="Metadata" code="exercise.metadata" />
						<form:option value="Name" code="exercise.name" />
						<form:option value="Text" code="exercise.text" />

					</form:select>
					<form:errors path="filter" cssClass="error" />
				</form:label>
				<div class="col-md-6">
					<form:input path="search" id="search" name="search" type="text"
						class="form-control" />
				</div>
				<form:errors path="search" cssClass="error" />
			</div>


			<br />
			<div class="form-group">
				<div class="col-md-12 text-center">
					<input type="submit" name="save" class="btn btn-primary btn-lg"
						value="<spring:message code="exercise.search" />" />
				</div>
			</div>
		</fieldset>
	</form:form>
</jstl:if>

<div class="container">
	<div class="row">
		<div class="table-responsive">

			<display:table uid="exercisesListTable" keepStatus="false"
				name="exercises" pagesize="10" class="table table-hover"
				requestURI="${requestURI}" id="row">


				<display:column property="name" titleKey="exercise.name"
					sortable="true" />
				<display:column property="text" titleKey="exercise.text"
					sortable="false" />
				<display:column titleKey="exercise.questions">
					<a href="question/listByExercise.do?exerciseId=${row.id }"
						title='Questions'><spring:message code="exercise.questions" /></a>

				</display:column>

				<jstl:if test="${!pickToCopy }">
					<jstl:if test="${exam != null}">
						<jstl:if test="${exam.owners.contains(currentUser) }">
							<display:column>
								<a
									href="exercise/edit.do?exerciseId=${row.id}&examId=${param.examId}"><input
									class="btn btn-default" type="button"
									value="<spring:message code="exercise.edit"/>"
									onclick="self.location.href = exercise/edit.do?exerciseId=${row.id}" /></a>
							</display:column>
						</jstl:if>
					</jstl:if>
				</jstl:if>


				<security:authorize access="hasRole('ADMIN')">
					<display:column>
						<a
							href="exercise/edit.do?exerciseId=${row.id}&examId=${param.examId}"><input
							class="btn btn-default" type="button"
							value="<spring:message code="exercise.edit"/>"
							onclick="self.location.href = exercise/edit.do?exerciseId=${row.id}" /></a>
					</display:column>
				</security:authorize>

				<jstl:if test="${pickToCopy }">
					<display:column>
						<a
							href="exercise/createFromParent.do?exerciseId=${row.id}&examId=${param.examId}"><input
							class="btn btn-default" type="button"
							value="<spring:message code="exercise.create.parent"/>"
							onclick="self.location.href = exercise/edit.do?exerciseId=${row.id}" /></a>
					</display:column>
				</jstl:if>



			</display:table>

			<jstl:if test="${!pickToCopy }">
				<jstl:if test="${exam.owners != null}">
					<jstl:if test="${exam.owners.contains(currentUser) }">
						<a href="exercise/create.do?examId=${param.examId }"><input
							type="button" class="btn btn-default"
							value="<spring:message code="exercise.create"/>"
							onclick="self.location.href = exercise/create.do?examId=${param.examId}" /></a>

						<a href="exercise/allToPick.do?examId=${param.examId }"><input
							type="button" class="btn btn-default"
							value="<spring:message code="exercise.create.parent"/>"
							onclick="self.location.href = exercise/allToPick.do?examId=${param.examId }" /></a>
					</jstl:if>
				</jstl:if>
			</jstl:if>

			<security:authorize access="hasRole('ADMIN')">
				<jstl:if test="${!pickToCopy }">
					<a href="exercise/create.do?examId=${param.examId }"><input
						type="button" class="btn btn-default"
						value="<spring:message code="exercise.create"/>"
						onclick="self.location.href = exercise/create.do?examId=${param.examId}" /></a>
				</jstl:if>

				<jstl:if test="${!pickToCopy }">
					<a href="exercise/allToPick.do?examId=${param.examId }"><input
						type="button" class="btn btn-default"
						value="<spring:message code="exercise.create.parent"/>"
						onclick="self.location.href = exercise/allToPick.do?examId=${param.examId }" /></a>
				</jstl:if>
			</security:authorize>
			<%-- 
			<jstl:if test="${other }">
				<a href="exerciseGroup/administrator/list.do" type="button"> <spring:message
						code="exercise.cancel" />
				</a>
			</jstl:if> --%>
			<a href="exam/list.do"> <input type="button"
				class="btn btn-default"
				value="<spring:message code="exercise.cancel"/>"
				onclick="self.location.href = exam/list.do" /></a>


		</div>
	</div>
</div>