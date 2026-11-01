<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>

<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center flex-wrap mb-3 gap-2">
        <h2 class="mb-0">Manage Suppliers</h2>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
    </div>

    <!-- Message after an action -->
    <c:choose>
        <c:when test="${param.msg == 'added'}">
            <div class="alert alert-success">Supplier added.</div>
        </c:when>
        <c:when test="${param.msg == 'updated'}">
            <div class="alert alert-success">Supplier updated.</div>
        </c:when>
        <c:when test="${param.msg == 'notfound'}">
            <div class="alert alert-warning">That supplier was not found.</div>
        </c:when>
        <c:when test="${param.msg == 'error'}">
            <div class="alert alert-danger">Something went wrong. Please try again.</div>
        </c:when>
    </c:choose>

    <c:if test="${not empty error}">
        <div class="alert alert-danger"><c:out value="${error}"/></div>
    </c:if>

    <div class="row g-4">

        <!-- Add / Edit form -->
        <div class="col-lg-4">
            <div class="card shadow-sm p-3">
                <h5 class="mb-3">
                    <c:choose>
                        <c:when test="${form.id != 0}">Edit Supplier</c:when>
                        <c:otherwise>Add Supplier</c:otherwise>
                    </c:choose>
                </h5>

                <c:if test="${not empty errors}">
                    <div class="alert alert-danger">
                        <ul class="mb-0">
                            <c:forEach var="e" items="${errors}">
                                <li><c:out value="${e}"/></li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/admin/suppliers">
                    <input type="hidden" name="id" value="<c:if test='${form.id != 0}'>${form.id}</c:if>">

                    <div class="mb-3">
                        <label class="form-label">Supplier name *</label>
                        <input type="text" name="supplierName" class="form-control" maxlength="100"
                               value="<c:out value='${form.supplierName}'/>" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Contact person *</label>
                        <input type="text" name="contactPerson" class="form-control" maxlength="100"
                               value="<c:out value='${form.contactPerson}'/>" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Contact number (10 digits) *</label>
                        <input type="text" name="contactNumber" class="form-control" maxlength="10"
                               value="<c:out value='${form.contactNumber}'/>" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Tanker capacity (litres) *</label>
                        <input type="number" name="tankerCapacity" class="form-control" min="500" max="50000"
                               value="<c:if test='${form.tankerCapacity > 0}'>${form.tankerCapacity}</c:if>" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Availability *</label>
                        <select name="availabilityStatus" class="form-select" required>
                            <option value="Available" ${form.availabilityStatus == 'Available' ? 'selected' : ''}>Available</option>
                            <option value="Not Available" ${form.availabilityStatus == 'Not Available' ? 'selected' : ''}>Not Available</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary">Save Supplier</button>
                    <c:if test="${form.id != 0}">
                        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/suppliers">Cancel</a>
                    </c:if>
                </form>
            </div>
        </div>

        <!-- Supplier table -->
        <div class="col-lg-8">
            <c:if test="${empty suppliers}">
                <div class="alert alert-info">No suppliers found.</div>
            </c:if>

            <c:if test="${not empty suppliers}">
                <div class="table-responsive">
                    <table class="table table-striped table-hover align-middle bg-white shadow-sm">
                        <thead class="table-primary">
                            <tr>
                                <th>#</th>
                                <th>Supplier</th>
                                <th>Contact</th>
                                <th>Capacity (L)</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${suppliers}">
                                <tr>
                                    <td>${s.id}</td>
                                    <td><c:out value="${s.supplierName}"/></td>
                                    <td>
                                        <c:out value="${s.contactPerson}"/><br>
                                        <span class="text-muted small"><c:out value="${s.contactNumber}"/></span>
                                    </td>
                                    <td>${s.tankerCapacity}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${s.availabilityStatus == 'Available'}">
                                                <span class="badge bg-success">Available</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Not Available</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a class="btn btn-sm btn-outline-primary"
                                           href="${pageContext.request.contextPath}/admin/suppliers?edit=${s.id}">Edit</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>
</div>

<%@ include file="/includes/footer.jsp" %>