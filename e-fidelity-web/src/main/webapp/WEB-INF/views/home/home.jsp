<%@ include file="../template/taglibs.jsp"%>

<div class="container">	
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold">Autentificare</h3>
				</div>
				<div class="panel-body">
					<p>
						Bine ai venit,
						<security:authentication property="principal.principal" />!
						<a href = "<c:url value="/logout"/>">Logout</a>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>



