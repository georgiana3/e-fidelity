<%@ include file="../template/taglibs.jsp"%>

<script>
	
</script>
<div class="container" style="margin-top: 1%;">


	<div class="col-md-6">
		<div class="panel panel-default">
			<div class="panel-heading">Listarea unui singur cod awb</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-6">
						<c:url value="/applet?origine=cod" var="actiune" />
						<form:form action="${actiune}" modelAttribute="preAppletFormCod"
							method="get">
							<form:hidden value="cod" path="origine" />
							<div class="form-group">
								<label class="control-label">Introduceti un cod awb</label>
								<form:input class="form-control" type="text" path="cod" />
							</div>
							<div class="form-group">
								<input class="btn btn-posta btn-sm pull-right" type="submit"
									value="Listeaza" />
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-6">
		<div class="panel panel-default">
			<div class="panel-heading">Listarea unui borderou</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-6">
						<c:url value="/applet?origine=borderou" var="actiune" />
						<form:form action="${actiune}"
							modelAttribute="preAppletFormBorderou" method="get">
<form:hidden  value="borderou" path="origine" />
							<div class="form-group">
								<label class="control-label">Introduceti un id de
									borderou</label>
								<form:input class="form-control" type="text" path="idBorderou" />
							</div>
							<div class="form-group">
								<input class="btn btn-posta btn-sm pull-right" type="submit"
									value="Listeaza" />
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>



	<div class="col-md-6">
		<div class="panel panel-default">
			<div class="panel-heading">Listarea unei serii de coduri</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-6">
						<c:url value="/applet" var="actiune" />
						<form:form action="${actiune}" modelAttribute="preAppletFormSerie"
							method="get">
							<form:hidden value="serie" path="origine" />
							<div class="form-group">
								<label class="control-label">Codul de la care se incepe</label>
								<form:input class="form-control" type="text" path="deLa" />

							</div>
							<div class="form-group">
								<label class="control-label">Numarul de coduri de listat</label>
								<form:input class="form-control" type="text" path="cat" />

							</div>
							<div class="form-group">
								<input class="btn btn-posta btn-sm pull-right" type="submit"
									value="Listeaza" />
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
