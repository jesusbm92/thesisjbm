<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@page import="domain.Exercise"%>
<%@page import="domain.Question"%>

<h1 class="text-center">
	<spring:message code="question.questions" />
</h1>

<div class="container">
	<div class="row">
		<div class="table-responsive">

			<display:table uid="questionsListTable" keepStatus="false"
				name="questions" pagesize="10" class="table table-hover"
				requestURI="${requestURI}" id="row">


				<display:column property="name" titleKey="question.name"
					sortable="true" />
				<display:column property="text" titleKey="question.text"
					sortable="false" />
				<display:column titleKey="question.xml">
					<a href="javascript:void(0);"
						onclick='javascript:window.open("question/xml.do?questionId=${row.id}", "_blank", "scrollbars=1,resizable=1,height=500,width=900");'
						title='XML'><spring:message code="question.seexml" /></a>
				</display:column>
				<display:column titleKey="question.answers">
					<a href="javascript:void(0)"
						onclick='javascript:window.open("answer/listByQuestion.do?questionId=${row.id }", "_blank", "scrollbars=1,resizable=1,height=500,width=900");'
						title='Answers'><spring:message code="question.answers" /></a>
				</display:column>
				<display:column property="difficulty" titleKey="question.difficulty"
					sortable="true" />
				<display:column property="weight" titleKey="question.weight"
					sortable="false" />
				<display:column property="weightfail" titleKey="question.weightfail"
					sortable="false" />
				<display:column titleKey="question.metadata">
					<jstl:forEach items="${row.metadata }" var="meta">
						<jstl:if
							test="${row.metadata.indexOf(meta)!= row.metadata.size()-1 }">
				${meta.getName() }, 
				</jstl:if>
						<jstl:if
							test="${row.metadata.indexOf(meta)== row.metadata.size()-1 }">
				${meta.getName() } 
				</jstl:if>
					</jstl:forEach>
				</display:column>

				<display:column>
					<jstl:if test="${row.owner != null}">
						<jstl:if test="${row.owner.equals(currentUser) }">
							<a
								href="question/edit.do?questionId=${row.id}&exerciseId=${param.exerciseId}"><input
								class="btn btn-default" type="button"
								value="<spring:message code="question.edit"/>"
								onclick="self.location.href = question/edit.do?questionId=${row.id}&exerciseId=${param.exerciseId}" /></a>
						</jstl:if>
					</jstl:if>
				</display:column>

				<security:authorize access="hasRole('ADMIN')">
					<display:column>
						<a
							href="question/edit.do?questionId=${row.id}&exerciseId=${param.exerciseId}"><input
							class="btn btn-default" type="button"
							value="<spring:message code="question.edit"/>"
							onclick="self.location.href = question/edit.do?questionId=${row.id}&exerciseId=${param.exerciseId}" /></a>
					</display:column>
				</security:authorize>

				<jstl:if test="${pickToCopy }">
					<display:column>
						<a
							href="question/createFromParent.do?questionId=${row.id}&exerciseId=${param.exerciseId}"><input
							class="btn btn-default" type="button"
							value="<spring:message code="question.create.parent"/>"
							onclick="self.location.href = question/createFromParent.do?questionId=${row.id}&exerciseId=${param.exerciseId}" /></a>
					</display:column>
				</jstl:if>



			</display:table>

			<jstl:if test="${exam.owners != null}">
				<jstl:if test="${exam.owners.contains(currentUser) }">
					<jstl:if test="${!pickToCopy }">
						<a href="question/create.do?exerciseId=${param.exerciseId }"><input
							type="button" class="btn btn-default"
							value="<spring:message code="question.create"/>"
							onclick="self.location.href = question/create.do?exerciseId=${param.exerciseId }" /></a>
					</jstl:if>
					<jstl:if test="${!pickToCopy }">
						<a href="question/allToPick.do?exerciseId=${param.exerciseId }"><input
							type="button" class="btn btn-default"
							value="<spring:message code="question.create.parent"/>"
							onclick="self.location.href = question/allToPick.do?exerciseId=${param.exerciseId }" /></a>
					</jstl:if>

				</jstl:if>
			</jstl:if>
			<security:authorize access="hasRole('ADMIN')">
				<jstl:if test="${!pickToCopy }">
					<a href="question/create.do?exerciseId=${param.exerciseId }"><input
						type="button" class="btn btn-default"
						value="<spring:message code="question.create"/>"
						onclick="self.location.href = question/create.do?exerciseId=${param.exerciseId }" /></a>
				</jstl:if>
				<jstl:if test="${!pickToCopy }">
					<a href="question/allToPick.do?exerciseId=${param.exerciseId }"><input
						type="button" class="btn btn-default"
						value="<spring:message code="question.create.parent"/>"
						onclick="self.location.href = question/allToPick.do?exerciseId=${param.exerciseId }" /></a>
				</jstl:if>
			</security:authorize>
			<%-- 
			<jstl:if test="${other }">
				<a href="questionGroup/administrator/list.do" type="button"> <spring:message
						code="question.cancel" />
				</a>
			</jstl:if> --%>
			<a href="history.back()"> <input type="button"
				class="btn btn-default"
				value="<spring:message code="question.cancel"/>"
				onclick="history.back()" /></a>


		</div>
	</div>
</div>