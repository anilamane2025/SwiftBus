<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<jsp:include page="templates/head.jsp" />
<body>
	<jsp:include page="templates/sidebar.jsp" />
	<jsp:include page="templates/header.jsp" />

	<div class="dashboard-main-body">
		<div
			class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-24">
			<h6 class="fw-semibold mb-0">Dashboard</h6>
			<ul class="d-flex align-items-center gap-2">
				<li class="fw-medium"><a href="#"
					class="d-flex align-items-center gap-1 hover-text-primary"> <iconify-icon
							icon="solar:home-smile-angle-outline" class="icon text-lg"></iconify-icon>
						Dashboard
				</a></li>
			</ul>
		</div>

		<sec:authorize access="hasRole('ADMIN')">

			<div
				class="row row-cols-xxxl-5 row-cols-lg-3 row-cols-sm-2 row-cols-1 gy-4">
				<div class="col">
					<div class="card shadow-none border bg-gradient-start-1 h-100">
						<div class="card-body p-20">
							<div
								class="d-flex flex-wrap align-items-center justify-content-between gap-3">
								<div>
									<p class="fw-medium text-primary-light mb-1">Total Agents</p>
									<h6 class="mb-0">20</h6>
								</div>
								<div
									class="w-50-px h-50-px bg-cyan rounded-circle d-flex justify-content-center align-items-center">
									<iconify-icon icon="fluent:people-20-filled"
										class="text-white text-2xl mb-0"></iconify-icon>
								</div>
							</div>

						</div>
					</div>
					<!-- card end -->
				</div>

				<div class="col">
					<div class="card shadow-none border bg-gradient-start-3 h-100">
						<div class="card-body p-20">
							<div
								class="d-flex flex-wrap align-items-center justify-content-between gap-3">
								<div>
									<p class="fw-medium text-primary-light mb-1">Total Users</p>
									<h6 class="mb-0">5,00</h6>
								</div>
								<div
									class="w-50-px h-50-px bg-info rounded-circle d-flex justify-content-center align-items-center">
									<iconify-icon icon="gridicons:multiple-users"
										class="text-white text-2xl mb-0"></iconify-icon>
								</div>
							</div>
						</div>
					</div>
					<!-- card end -->
				</div>
				<div class="col">
					<div class="card shadow-none border bg-gradient-start-4 h-100">
						<div class="card-body p-20">
							<div
								class="d-flex flex-wrap align-items-center justify-content-between gap-3">
								<div>
									<p class="fw-medium text-primary-light mb-1">Total Income</p>
									<h6 class="mb-0">$42,000</h6>
								</div>
								<div
									class="w-50-px h-50-px bg-success-main rounded-circle d-flex justify-content-center align-items-center">
									<iconify-icon icon="solar:wallet-bold"
										class="text-white text-2xl mb-0"></iconify-icon>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>

		<sec:authorize access="hasRole('AGENT')">
			<div
				class="row row-cols-xxxl-5 row-cols-lg-3 row-cols-sm-2 row-cols-1 gy-4">
				<div class="col">
					<div class="card shadow-none border bg-gradient-start-2 h-100">
						<div class="card-body p-20">
							<div
								class="d-flex flex-wrap align-items-center justify-content-between gap-3">
								<div>
									<p class="fw-medium text-primary-light mb-1">Total Booked
										Tickets</p>
									<h6 class="mb-0">15,00</h6>
								</div>
								<div
									class="w-50-px h-50-px bg-purple rounded-circle d-flex justify-content-center align-items-center">
									<iconify-icon icon="fa-solid:award"
										class="text-white text-2xl mb-0"></iconify-icon>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col">
					<div class="card shadow-none border bg-gradient-start-4 h-100">
						<div class="card-body p-20">
							<div
								class="d-flex flex-wrap align-items-center justify-content-between gap-3">
								<div>
									<p class="fw-medium text-primary-light mb-1">Total
										Commission</p>
									<h6 class="mb-0">$42,00</h6>
								</div>
								<div
									class="w-50-px h-50-px bg-success-main rounded-circle d-flex justify-content-center align-items-center">
									<iconify-icon icon="solar:wallet-bold"
										class="text-white text-2xl mb-0"></iconify-icon>
								</div>
							</div>
						</div>
					</div>
					<!-- card end -->
				</div>
			</div>
		</sec:authorize>

	</div>
	<jsp:include page="templates/footer.jsp" />
	</main>
	<jsp:include page="templates/scripts.jsp" />

</body>
</html>