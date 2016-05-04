<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="isAuthenticated()">
	<h1 class="text-center">
		<spring:message code="exam.exams" />
	</h1>

	<div class="container">
		<div class="row">
			<div class="table-responsive">

				<display:table uid="examsListTable" keepStatus="false" name="exams"
					pagesize="10" class="table table-hover" requestURI="${requestURI}"
					id="row">


					<display:column property="name" titleKey="exam.name"
						sortable="true" />
					<display:column property="difficulty" titleKey="exam.difficulty"
						sortable="true" />
					<display:column property="date" titleKey="exam.date"
						sortable="true" />
					<display:column titleKey="exam.xml">
						<a href="javascript:void(0);"
							onclick='javascript:window.open("exam/xml.do?examId=${row.id}", "_blank", "scrollbars=1,resizable=1,height=500,width=900");'
							title='XML'><spring:message code="exam.seexml" /></a>
					</display:column>
					<display:column titleKey="exam.exercises">
						<a href="exercise/listByExam.do?examId=${row.id }" title='XML'><spring:message
								code="exam.exercises" /></a>
					</display:column>
					<display:column>
						<a href="exam/edit.do?examId=${row.id}"><input
							class="btn btn-default" type="button"
							value="<spring:message code="exam.edit"/>"
							onclick="self.location.href = exam/edit.do?examId=${row.id}" /></a>
					</display:column>



				</display:table>
					<a href="exam/create.do"><input type="button"
						class="btn btn-default"
						value="<spring:message code="exam.create"/>"
						onclick="self.location.href = exam/create.do" /></a>
				<%-- 
			<jstl:if test="${other }">
				<a href="examGroup/administrator/list.do" type="button"> <spring:message
						code="exam.cancel" />
				</a>
			</jstl:if> --%>
				<a href="#"> <input type="button" class="btn btn-default"
					value="<spring:message code="exam.cancel"/>"
					onclick="self.location.href = #" /></a>


			</div>
		</div>
	</div>
</security:authorize>