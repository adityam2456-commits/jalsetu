<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>

<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center flex-wrap mb-4 gap-2">
        <h2 class="mb-0">Admin Dashboard</h2>
        <div class="d-flex gap-2">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/requests">Manage Requests</a>
            <form method="post" action="${pageContext.request.contextPath}/admin/logout">
                <button type="submit" class="btn btn-outline-danger">Logout</button>
            </form>
        </div>
    </div>

    <div class="alert alert-success">
        Welcome, <strong><c:out value="${sessionScope.adminUser}"/></strong>. You are logged in.
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger"><c:out value="${error}"/></div>
    </c:if>

    <c:if test="${not empty stats}">
        <p class="text-muted">Click a request card to see those requests.</p>
        <div class="row g-4">

            <div class="col-6 col-md-4">
                <div class="card shadow-sm text-center p-3 h-100">
                    <div class="display-6 fw-bold text-primary stat-number" data-count="${stats.societies}">${stats.societies}</div>
                    <div class="text-muted">Registered Societies</div>
                </div>
            </div>

            <div class="col-6 col-md-4">
                <div class="card shadow-sm text-center p-3 h-100">
                    <div class="display-6 fw-bold text-primary stat-number" data-count="${stats.suppliers}">${stats.suppliers}</div>
                    <div class="text-muted">Water Suppliers</div>
                </div>
            </div>

            <div class="col-6 col-md-4">
                <a class="text-decoration-none" href="${pageContext.request.contextPath}/admin/requests">
                    <div class="card shadow-sm text-center p-3 h-100 border-primary">
                        <div class="display-6 fw-bold text-primary stat-number" data-count="${stats.requests}">${stats.requests}</div>
                        <div class="text-muted">Total Requests</div>
                    </div>
                </a>
            </div>

            <div class="col-6 col-md-4">
                <a class="text-decoration-none" href="${pageContext.request.contextPath}/admin/requests?status=Pending">
                    <div class="card shadow-sm text-center p-3 h-100 border-warning">
                        <div class="display-6 fw-bold text-warning stat-number" data-count="${stats.pending}">${stats.pending}</div>
                        <div class="text-muted">Pending</div>
                    </div>
                </a>
            </div>

            <div class="col-6 col-md-4">
                <a class="text-decoration-none" href="${pageContext.request.contextPath}/admin/requests?status=Approved">
                    <div class="card shadow-sm text-center p-3 h-100 border-info">
                        <div class="display-6 fw-bold text-info stat-number" data-count="${stats.approved}">${stats.approved}</div>
                        <div class="text-muted">Approved</div>
                    </div>
                </a>
            </div>

            <div class="col-6 col-md-4">
                <a class="text-decoration-none" href="${pageContext.request.contextPath}/admin/requests?status=Completed">
                    <div class="card shadow-sm text-center p-3 h-100 border-success">
                        <div class="display-6 fw-bold text-success stat-number" data-count="${stats.completed}">${stats.completed}</div>
                        <div class="text-muted">Completed Deliveries</div>
                    </div>
                </a>
            </div>

        </div>
    </c:if>
</div>

<%@ include file="/includes/footer.jsp" %>