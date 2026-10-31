<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/includes/header.jsp" %>

<div class="container my-5" style="max-width: 700px;">
    <h2 class="mb-4">Submit a Water Request</h2>

    <c:if test="${not empty errors}">
        <div class="alert alert-danger">
            <ul class="mb-0">
                <c:forEach var="e" items="${errors}">
                    <li><c:out value="${e}"/></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/request" class="card shadow-sm p-4">

        <div class="mb-3">
            <label class="form-label">Society name *</label>
            <input type="text" name="societyName" class="form-control" maxlength="100"
                   value="<c:out value='${param.societyName}'/>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Contact person *</label>
            <input type="text" name="contactPerson" class="form-control" maxlength="100"
                   value="<c:out value='${param.contactPerson}'/>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Contact number (10 digits) *</label>
            <input type="text" name="contactNumber" class="form-control" maxlength="10"
                   value="<c:out value='${param.contactNumber}'/>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Society address</label>
            <input type="text" name="address" class="form-control" maxlength="255"
                   value="<c:out value='${param.address}'/>">
        </div>

        <div class="mb-3">
            <label class="form-label">Required water quantity (litres) *</label>
            <input type="number" name="quantity" class="form-control" min="100" max="100000"
                   value="<c:out value='${param.quantity}'/>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Preferred delivery date *</label>
            <input type="date" name="preferredDate" class="form-control"
                   value="<c:out value='${param.preferredDate}'/>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Urgency *</label>
            <select name="urgency" class="form-select" required>
                <option value="Low"    ${param.urgency == 'Low' ? 'selected' : ''}>Low</option>
                <option value="Medium" ${param.urgency == 'Medium' ? 'selected' : ''}>Medium</option>
                <option value="High"   ${param.urgency == 'High' ? 'selected' : ''}>High</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Remarks (optional)</label>
            <textarea name="remarks" class="form-control" rows="3" maxlength="255"><c:out value="${param.remarks}"/></textarea>
        </div>

        <button type="submit" class="btn btn-primary">Submit Request</button>
    </form>
</div>

<%@ include file="/includes/footer.jsp" %>