<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>

<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center flex-wrap mb-4">
        <h2 class="mb-0">Admin Dashboard</h2>
        <form method="post" action="${pageContext.request.contextPath}/admin/logout">
            <button type="submit" class="btn btn-outline-danger">Logout</button>
        </form>
    </div>

    <div class="alert alert-success">
        Welcome, <strong><c:out value="${sessionScope.adminUser}"/></strong>. You are logged in.
    </div>

    <p class="text-muted">Statistics, request management and supplier management will appear here in the next steps.</p>
</div>

<%@ include file="/includes/footer.jsp" %>