<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="/includes/header.jsp" %>

<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center flex-wrap mb-3">
        <h2 class="mb-0">Manage Water Requests</h2>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
    </div>

    <!-- Message after an action -->
    <c:choose>
        <c:when test="${param.msg == 'updated'}">
            <div class="alert alert-success">Request status updated.</div>
        </c:when>
        <c:when test="${param.msg == 'notallowed'}">
            <div class="alert alert-warning">That change is not allowed for this request's current status.</div>
        </c:when>
        <c:when test="${param.msg == 'invalid'}">
            <div class="alert alert-warning">Invalid action.</div>
        </c:when>
        <c:when test="${param.msg == 'error'}">
            <div class="alert alert-danger">Something went wrong. Please try again.</div>
        </c:when>
    </c:choose>

    <c:if test="${not empty error}">
        <div class="alert alert-danger"><c:out value="${error}"/></div>
    </c:if>

    <!-- Filter buttons -->
    <div class="mb-3 d-flex flex-wrap gap-2">
        <a class="btn btn-sm ${empty selectedStatus ? 'btn-primary' : 'btn-outline-primary'}"
           href="${pageContext.request.contextPath}/admin/requests">All</a>
        <a class="btn btn-sm ${selectedStatus == 'Pending' ? 'btn-primary' : 'btn-outline-primary'}"
           href="${pageContext.request.contextPath}/admin/requests?status=Pending">Pending</a>
        <a class="btn btn-sm ${selectedStatus == 'Approved' ? 'btn-primary' : 'btn-outline-primary'}"
           href="${pageContext.request.contextPath}/admin/requests?status=Approved">Approved</a>
        <a class="btn btn-sm ${selectedStatus == 'Completed' ? 'btn-primary' : 'btn-outline-primary'}"
           href="${pageContext.request.contextPath}/admin/requests?status=Completed">Completed</a>
        <a class="btn btn-sm ${selectedStatus == 'Rejected' ? 'btn-primary' : 'btn-outline-primary'}"
           href="${pageContext.request.contextPath}/admin/requests?status=Rejected">Rejected</a>
    </div>

    <c:if test="${empty requests}">
        <div class="alert alert-info">No requests found.</div>
    </c:if>

    <c:if test="${not empty requests}">
        <div class="table-responsive">
            <table class="table table-striped table-hover align-middle bg-white shadow-sm">
                <thead class="table-primary">
                    <tr>
                        <th>#</th>
                        <th>Society</th>
                        <th>Litres</th>
                        <th>Delivery Date</th>
                        <th>Urgency</th>
                        <th>Remarks</th>
                        <th>Submitted</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${requests}">
                        <tr>
                            <td>${r.id}</td>
                            <td><c:out value="${r.societyName}"/></td>
                            <td>${r.requiredQuantity}</td>
                            <td>${r.preferredDate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${r.urgency == 'High'}"><span class="badge bg-danger">High</span></c:when>
                                    <c:when test="${r.urgency == 'Medium'}"><span class="badge bg-warning text-dark">Medium</span></c:when>
                                    <c:otherwise><span class="badge bg-secondary">Low</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${r.remarks}"/></td>
                            <td><fmt:formatDate value="${r.createdAt}" pattern="dd MMM yyyy, HH:mm"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${r.status == 'Pending'}"><span class="badge bg-warning text-dark">Pending</span></c:when>
                                    <c:when test="${r.status == 'Approved'}"><span class="badge bg-info text-dark">Approved</span></c:when>
                                    <c:when test="${r.status == 'Completed'}"><span class="badge bg-success">Completed</span></c:when>
                                    <c:otherwise><span class="badge bg-dark">Rejected</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/admin/requests"
                                      class="d-flex gap-1">
                                    <input type="hidden" name="id" value="${r.id}">
                                    <input type="hidden" name="filter" value="<c:out value='${selectedStatus}'/>">

                                    <c:if test="${r.status == 'Pending'}">
                                        <button type="submit" name="action" value="approve"
                                                class="btn btn-sm btn-success">Approve</button>
                                        <button type="submit" name="action" value="reject"
                                                class="btn btn-sm btn-outline-danger">Reject</button>
                                    </c:if>

                                    <c:if test="${r.status == 'Approved'}">
                                        <button type="submit" name="action" value="complete"
                                                class="btn btn-sm btn-primary">Mark Completed</button>
                                    </c:if>

                                    <c:if test="${r.status == 'Completed' || r.status == 'Rejected'}">
                                        <span class="text-muted small">No action</span>
                                    </c:if>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<%@ include file="/includes/footer.jsp" %>