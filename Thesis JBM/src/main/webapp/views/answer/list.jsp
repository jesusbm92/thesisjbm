<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1 class="text-center">
	<spring:message code="answer.answers" />
</h1>

<div class="container">
	<div class="row">
		<div class="table-responsive">

			<display:table uid="answersListTable" keepStatus="false"
				name="answers" pagesize="10" class="table table-hover"
				requestURI="${requestURI}" id="row">


				<display:column property="name" titleKey="answer.name"
					sortable="false" />
				<display:column property="text" titleKey="answer.text"
					sortable="false" />
				<display:column property="isCorrect" titleKey="answer.iscorrect"
					sortable="true" />
				<display:column property="penalty" titleKey="answer.penalty"
					sortable="true" />

				<security:authorize access="hasRole('CUSTOMER')">

					<jstl:if test="${exam.owner != null}">
						<jstl:if test="${exam.owner.equals(currentUser) }">
							<display:column>
								<a
									href="answer/edit.do?answerId=${row.id}&questionId=${param.questionId}"><input
									class="btn btn-default" type="button"
									value="<spring:message code="answer.edit"/>"
									onclick="self.location.href = answer/edit.do?answerId=${row.id}&questionId=${param.questionId}" /></a>
							</display:column>

						</jstl:if>
					</jstl:if>
				</security:authorize>
				<security:authorize access="hasRole('ADMIN')">
					<display:column>
						<a
							href="answer/edit.do?answerId=${row.id}&questionId=${param.questionId}"><input
							class="btn btn-default" type="button"
							value="<spring:message code="answer.edit"/>"
							onclick="self.location.href = answer/edit.do?answerId=${row.id}&questionId=${param.questionId}" /></a>
					</display:column>

				</security:authorize>

			</display:table>
			<security:authorize access="hasRole('CUSTOMER')">
				<jstl:if test="${exam.owner != null}">
					<jstl:if test="${exam.owner.equals(currentUser) }">
						<jstl:if test="${!other }">
							<a href="answer/create.do?questionId=${param.questionId}"><input
								type="button" class="btn btn-default"
								value="<spring:message code="answer.create"/>"
								onclick="self.location.href = answer/create.do?questionId=${param.questionId}" /></a>
						</jstl:if>
					</jstl:if>
				</jstl:if>
			</security:authorize>

			<security:authorize access="hasRole('ADMIN')">
				<a href="answer/create.do?questionId=${param.questionId}"><input
					type="button" class="btn btn-default"
					value="<spring:message code="answer.create"/>"
					onclick="self.location.href = answer/create.do?questionId=${param.questionId}" /></a>

			</security:authorize>
			<%-- 
			<jstl:if test="${other }">
				<a href="answerGroup/administrator/list.do" type="button"> <spring:message
						code="answer.cancel" />
				</a>
			</jstl:if> --%>
			<a href="history.back()"> <input type="button"
				class="btn btn-default"
				value="<spring:message code="answer.cancel"/>"
				onclick="history.back()" /></a>


		</div>
	</div>
</div>