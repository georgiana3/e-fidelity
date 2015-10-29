<%@ include file="../template/taglibs.jsp"%>


<div class="container" style="margin-top: 1%;">

	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold">Schimbare
						parola</h3>
				</div>
				<div class="panel-body">
					<c:url value="/utilizatori/renew-password" var="action" />
					<form action="${action}" method="POST" autocomplete="off">
						<div class="form-group">
							<label>Parola</label> <input type="password" id="parola"
								name="parola" class="form-control" />
						</div>

						<div class="form-group">
							<label>Confirmare parola</label> <input id="parola1"
								type="password" name="parola1" class="form-control" />
						</div>

						<div style="display: none" class="form-group">
							<input type="hidden" id="codreset" name="codreset" value="${codreset}"
								class="form-control" />
						</div>

						<input type="submit" value="Salveaza parola"
							class="btn btn-sm btn-posta btn-block" />
					</form>

				</div>
			</div>
		</div>
	</div>
</div>



