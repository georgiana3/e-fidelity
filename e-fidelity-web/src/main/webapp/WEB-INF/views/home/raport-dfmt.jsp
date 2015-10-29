<%@ include file="../template/taglibs.jsp"%>

<c:choose>
	<c:when test="${persoanaJuridica}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#fromDate").datepicker();
				$("#toDate").datepicker();

				$("#fromDate").change(function() {
					if (!$("#toDate").val()) {
						$("#toDate").val($("#fromDate").val());
					}
				});

				$("#idUtilizator2").chosen({
					width : '100%'
				});
				$("#idUtilizator2").chosen().change(function() {
					var global = $(this).find("option:selected").attr("global");
					if (global === "1")
						$("#global").val("true");
					else
						$("#global").val("false");
				});

				/* Form submit different URL technique */
				var prezentatePath = '<c:url value="/raport/raport-dfmt"/>';

				$('.report-form-submitter').click(function(event) {
					event.preventDefault();

					var elementId = event.target.id;
					var $form = $("#raportForm");

					console.log("");

					if (elementId == 'btnPrezentate') {
						$form.attr('action', prezentatePath);
					} else
						$form.attr('action', toatePath);

					$form.submit();

				});

			});
		</script>
	</c:when>
	<c:otherwise>

		<script type="text/javascript">
			$(document).ready(function() {
				$("#fromDate").datepicker();
				$("#toDate").datepicker();
				$("#idUtilizator2").chosen({
					width : '100%'
				});
				$("#fromDate").change(function() {
					if (!$("#toDate").val()) {
						$("#toDate").val($("#fromDate").val());
					}
				});
				$("#idUtilizator2").chosen().change(function() {
					var global = $(this).find("option:selected").attr("global");
					if (global === "1")
						$("#global").val("true");
					else
						$("#global").val("false");
				});
			});
		</script>
	</c:otherwise>
</c:choose>

<div class="container" style="margin-top: 1%;">

	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold">Raport</h3>
				</div>
				<div class="panel-body">
					<div class="col-md-12">
						<form:form modelAttribute="raportForm" id="raportForm">

							<fildset>
							<div class="form-group">
								<div class="input-group">
									<span class="input-group-addon">De la data</span>
									<form:input class="form-control" id="fromDate" type="text"
										path="dataStart" name="dataStart" />
									<span class="input-group-addon">Pana la data</span>
									<form:input class="form-control" id="toDate" type="text"
										path="dataEnd" name="dataEnd" />
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-calendar"></i></span>
								</div>
							</div>
							<div class="form-group">
								<label for="idUtilizator" class="control-label">Utilizator
								</label>
								<c:choose>
									<c:when test="${persoanaJuridica}">
										<c:choose>
											<c:when test="${isMaster}">
												<div class="row" style="padding-bottom: 10px;">
													<div class="col-md-12">
														<input class="form-control" value="${numeUtilizator}"
															id="numeUtilizator" type="text" disabled="true"
															name="numeUtilizator" path="numeUtilizator" />
														<form:select class="form-control" path="idUtilizator"
															id="idUtilizator2">
															<form:option global="1" value="${idSucurusala}"
																label="Global" />
															<form:options items="${sucursale}" itemLabel="denumire"
																itemValue="idUtilizator"></form:options>
														</form:select>
													</div>
												</div>
											</c:when>
											<c:otherwise>
												<div class="row" style="padding-bottom: 10px;">
													<div class="col-md-12">
														<input class="form-control" value="${numeUtilizator}"
															id="numeUtilizator" type="text" disabled="true"
															name="numeUtilizator" path="numeUtilizator" />

														<form:hidden class="form-control" value="${idSucurusala}"
															id="idUtili" path="idUtilizator" />
													</div>
												</div>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<form:select class="form-control" path="idUtilizator"
											id="idUtilizator2">
											<form:option value="" label="--- Alegeti un utilizator ---" />
											<c:forEach var="client" items="${lista}">
												<form:option global="1" value="${client.idUtilizator}"
													label="${(client['class'].simpleName eq 'PersoanaFizica') 
														? client.nume.concat(' ').concat(client.prenume) : client.denumire} [${client.username}]" />
											</c:forEach>
											<c:forEach var="cont" items="${masteri}">
												<form:option global="1" value="${cont.idUtilizator}"
													label="${cont.denumire} [${cont.username} (raport Volksbank global)]" />
											</c:forEach>
										</form:select>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="row" style="padding-bottom: 10px;">
								<div class="form-group">
									<div class="col-md-5 pull-right">
										<label for="sel" class="control-label">Selectati
											formatul</label>
										<form:select path="format" name="format" class="form-control"
											id="sel">
											<option value="xls">Excel</option>
											<option value="pdf">PDF</option>
										</form:select>
									</div>
								</div>
							</div>

							</fildset>

							<form:hidden path="globalVolksbank" id="global" />
							<div class="row">
								<div class="form-group">
									<div class="col-md-5 pull-right">
										<input id="btnPrezentate" type="submit"
											value="Descarca raport"
											class="btn btn-sm btn-posta btn-block report-form-submitter" />
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
