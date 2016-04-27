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
		<spring:message code="metadata.metadatas" />
	</h1>

	<div class="container">
		<div class="row">
			<div class="table-responsive">

				<display:table uid="metadatasListTable" keepStatus="false" name="metadatas"
					pagesize="10" class="table table-hover" requestURI="${requestURI}"
					id="row">


					<display:column property="name" titleKey="metadata.name"
						sortable="true" />
					<display:column>
						<a href="metadata/administrator/edit.do?metadataId=${row.id}"><input
							class="btn btn-default" type="button"
							value="<spring:message code="metadata.edit"/>"
							onclick="self.location.href = metadata/administrator/edit.do?metadataId=${row.id}" /></a>
					</display:column>

					<display:column>
						<a href="urldelete"><input class="btn btn-default"
							type="button" value="<spring:message code="metadata.delete"/>"
							onclick="self.location.href = metadata/administrator/edit.do?metadataId=${row.id}" /></a>
					</display:column>



				</display:table>
				<jstl:if test="${!other }">
					<a href="metadata/administrator/create.do"><input type="button"
						class="btn btn-default"
						value="<spring:message code="metadata.create"/>"
						onclick="self.location.href = metadata/administrator/create.do" /></a>
				</jstl:if>
				<%-- 
			<jstl:if test="${other }">
				<a href="metadataGroup/administrator/list.do" type="button"> <spring:message
						code="metadata.cancel" />
				</a>
			</jstl:if> --%>
				<a href="#"> <input type="button" class="btn btn-default"
					value="<spring:message code="metadata.cancel"/>"
					onclick="self.location.href = #" /></a>


			</div>
		</div>
	</div>
</security:authorize>