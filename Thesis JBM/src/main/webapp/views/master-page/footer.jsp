<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="date" class="java.util.Date" />


<div class="container">
<hr style="border-color: #C0C2C4;">
	<footer>
		<p>
			Copyright &copy; <fmt:formatDate value="${date}"
					pattern="yyyy" /> Jesus Barba
		</p>
	</footer>
</div>