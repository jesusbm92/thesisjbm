<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1 class="text-center">
	<spring:message code="exam.exams" />
</h1>

<div class="container">
	<div class="row">
		<div class="table-responsive">

			<display:table uid="examsListTable" keepStatus="false"
				name="exams" pagesize="10" class="table table-hover"
				requestURI="${requestURI}" id="row">


				<display:column property="name" titleKey="exam.name"
					sortable="true" />
				<display:column>
					<a href="exam/administrator/edit.do?examId=${row.id}"><input
						class="btn btn-default" type="button"
						value="<spring:message code="exam.edit"/>"
						onclick="self.location.href = exam/administrator/edit.do?examId=${row.id}" /></a>
				</display:column>



			</display:table>
			<jstl:if test="${!other }">
				<a href="exam/administrator/create.do"><input type="button"
					class="btn btn-default"
					value="<spring:message code="exam.create"/>"
					onclick="self.location.href = exam/administrator/create.do" /></a>
			</jstl:if>
			<%-- 
			<jstl:if test="${other }">
				<a href="examGroup/administrator/list.do" type="button"> <spring:message
						code="exam.cancel" />
				</a>
			</jstl:if> --%>
			<a href="examGroup/administrator/list.do"> <input
				type="button" class="btn btn-default"
				value="<spring:message code="exam.cancel"/>"
				onclick="self.location.href = examGroup/administrator/list.do" /></a>


		</div>
	</div>
</div>