<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/includes/header.jsp" %>

<section class="hero text-center">
    <div class="container">
        <h1 class="fw-bold">A Digital Platform for<br>Community Water Supply Management</h1>
        <p class="lead mt-3">Housing societies submit water requirements, and administrators
           coordinate tanker deliveries from one place.</p>
        <a class="btn btn-light btn-lg mt-2" href="${pageContext.request.contextPath}/request">
            Submit a Water Request
        </a>
    </div>
</section>

<div class="container my-5">
    <div class="row g-4">
        <div class="col-md-4">
            <div class="card h-100 shadow-sm p-3">
                <h4 class="text-primary">1. Submit</h4>
                <p>Societies enter their water requirement, preferred date and urgency.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100 shadow-sm p-3">
                <h4 class="text-primary">2. Review</h4>
                <p>The administrator reviews and approves requests.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100 shadow-sm p-3">
                <h4 class="text-primary">3. Deliver</h4>
                <p>Tankers are coordinated and deliveries are marked complete.</p>
            </div>
        </div>
    </div>
</div>

<%@ include file="/includes/footer.jsp" %>
