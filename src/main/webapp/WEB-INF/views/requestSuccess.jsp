<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/includes/header.jsp" %>

<div class="container my-5 text-center" style="max-width: 600px;">
    <div class="card shadow-sm p-5">
        <h2 class="text-success">Request Submitted</h2>
        <p class="mt-3">Your water request has been saved with the status <strong>Pending</strong>.
           The administrator will review it soon.</p>
        <div class="mt-3">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/request">Submit Another Request</a>
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/">Back to Home</a>
        </div>
    </div>
</div>

<%@ include file="/includes/footer.jsp" %>