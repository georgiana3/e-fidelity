<%@ include file="../template/taglibs.jsp"%>

<script>
	function userSelected() {
		var selectedUser = $("#idUtilizator2").val();
		if (selectedUser) {
			if (selectedUser == 4640 || selectedUser == 4025) { // hardcode 4 life de lene
				$("#btnEmag").show();
				$("#noticeEmag").show();
			} else {
				$("#btnEmag").hide();
				$("#noticeEmag").hide();
			}
		}
	}
</script>

<c:choose>
	<c:when test="${persoanaJuridica}">
		<script type="text/javascript">
			$(document)
					.ready(
							function() {

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

								$("#idUtilizator2").chosen().change(
										function() {
											var global = $(this).find(
													"option:selected").attr(
													"global");
											if (global === "1")
												$("#global").val("true");
											else
												$("#global").val("false");
										});

								/* Form submit different URL technique */
								var prezentatePath = '<c:url value="/raport/raport-complex"/>';
								var toatePath = '<c:url value="/raport/raport-complex-toate"/>';
								var cecPath = '<c:url value="/raport/raport-cec"/>';
								var emagPath = '<c:url value="/emag/rapoarte"/>';

								$('.report-form-submitter')
										.click(
												function(event) {
													event.preventDefault();

													var elementId = event.target.id;
													var $form = $("#raportForm");

													if (elementId == 'btnPrezentate') {
														$form.attr('action',
																prezentatePath);
													} else if (elementId == 'btnCEC') {
														$form.attr('action',
																cecPath);
													} else if (elementId == 'btnEmag') {
														$form.attr('action',
																emagPath);
													} else {
														$form.attr('action',
																toatePath);
													}

													$form.submit();

												});

								$("#idUtilizator2").trigger("change");
							});
		</script>
	</c:when>
	<c:otherwise>

		<script type="text/javascript">
			$(document)
					.ready(
							function() {
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
								$("#idUtilizator2").chosen().change(
										function() {
											var global = $(this).find(
													"option:selected").attr(
													"global");
											if (global === "1")
												$("#global").val("true");
											else
												$("#global").val("false");
										});
								/* Form submit different URL technique */
								var prezentatePath = '<c:url value="/raport/raport-complex"/>';
								var toatePath = '<c:url value="/raport/raport-complex-toate"/>';
								var cecPath = '<c:url value="/raport/raport-cec"/>';
								var emagPath = '<c:url value="/emag/rapoarte"/>';

								$('.report-form-submitter')
										.click(
												function(event) {
													event.preventDefault();

													var elementId = event.target.id;
													var $form = $("#raportForm");

													if (elementId == 'btnPrezentate') {
														$form.attr('action',
																prezentatePath);
													} else if (elementId == 'btnCEC') {
														$form.attr('action',
																cecPath);
													} else if (elementId == 'btnEmag') {
														$form.attr('action',
																emagPath);
													} else {
														$form.attr('action',
																toatePath);
													}

													$form.submit();
												});

								$("#idUtilizator2").trigger("change");
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
							<div id="noticeEmag" style="display: none; margin-bottom: 10px">Pentru
								un raport lunar eMAG se va completa, prin conventie, doar campul
								"data de la" cu oricare zi de dupa cea de intai a lunii
								raportate!</div>
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
								<label for="idUtilizator" class="control-label">Client </label>
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
											onchange="userSelected()" id="idUtilizator2">
											<form:option selected="true" value=""
												label="--- Alegeti un client ---" />
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
										<input id="btnCreate" type="submit"
											value="Descarca raport create"
											class="btn btn-sm btn-posta btn-block report-form-submitter" />
										<input id="btnPrezentate" type="submit"
											value="Descarca raport prezentate"
											class="btn btn-sm btn-posta btn-block report-form-submitter" />
										<security:authorize access="hasAnyRole('PROGRAMMER')">
											<input id="btnCEC" type="submit" value="Descarca raport CEC"
												class="btn btn-sm btn-posta btn-block report-form-submitter" />
										</security:authorize>
										<input id="btnEmag" style="display: none;" type="submit"
											value="Descarca raport lunar eMAG"
											class="btn btn-sm btn-block btn-info report-form-submitter" />
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
