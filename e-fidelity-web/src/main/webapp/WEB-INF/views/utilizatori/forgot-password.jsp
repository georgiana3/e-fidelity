<%@ include file="../template/taglibs.jsp"%>


<div class="container" style="margin-top: 1%;">

	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold">Resetare parola</h3>
				</div>
				<div class="panel-body">
					<c:url value="/utilizatori/forgot-password" var="action" />
					<form:form action="${action}" method="POST"
						 autocomplete="off">
						<div class="form-group">
							<label>Introduceti adresa de mail </label>
							<input id="email" name="email" class="form-control"
								placeholder="Email" />
						</div>

						<input type="submit" value="Schimba parola"
							class="btn btn-sm btn-posta btn-block" />
					</form:form>

				</div>
			</div>
		</div>
	</div>
</div>



