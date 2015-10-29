<%@ include file="../template/taglibs.jsp"%>

<script type="text/javascript">
	function refreshCaptcha() {
		$("#captcha_image").attr("src", "<c:url value='/jcaptcha.jpg'/>");
	}
	$(document)
			.ready(
					function() {
						
						$("#parola").tooltip();
						
						fizicaJuridica();
						$("#judet").chosen({
							width : '100%'
						});
						$("#localitate").chosen({
							width : '100%'
						});

						$('#codPostal')
								.popover(
										{
											'trigger' : 'click',
											'selector' : '',
											'placement' : 'top',
											'title' : '',
											'content' : 'Pentru a gasi un cod postal da click <a target="_blank" href="http://www.coduripostale.com/cgi-bin/coduri_postale.cgi">aici</a>',
											'html' : 'true'
										});

						$('#codPostal').click(function (e) {
							e.stopPropagation();
							});
						$(document).click(function (e) {
							if (($('.popover').has(e.target).length == 0) || $(e.target).is('.close')) {
							$('#codPostal').popover('hide');
							}
						});
						
                        <c:if test = "${utilizatorForm.judet != null}">
                            populeazaLocalitati();
                            $("#localitate").val("${utilizatorForm.localitate}");
                            $("#localitate").trigger("chosen:updated");
                        </c:if>

					});
</script>

<script type="text/javascript">
	function populeazaLocalitati() {
		var $select = $("#judet");
		var $selectLocalitati = $("#localitate");
		var judet = $select.find("option:selected").val();
		$
				.ajax({
					url : '<c:url value="/ajax/getLocalitatiByJudet"/>',
					contentType : "application/json",
					data : {
						judet : judet
					},
					async : false,
					dataType : "json",
					success : function(data) {
						$selectLocalitati.html("");
						$
								.each(
										data,
										function(index, object) {
											$selectLocalitati
													.append($("<option data-codpostal='" + object.value + "'>"
															+ object.label
															+ "</option>"));
										});

						$selectLocalitati.trigger("chosen:updated");

					},
					error : function(e) {
						console.log(e.responseText);
					}
				});
	}
</script>

<script type="text/javascript">
	function fizicaJuridica() {
		var tipPersoana = $('#tipPersoana option:selected');
		if (tipPersoana.val() == 'FIZICA') {
			$("#nume").show();
			$("#prenume").show();
			$("#cnp").show();
			$("#cui").hide();
			$("#contBancar").hide();
			$("#denumire").hide();
			$("#sucursala").hide();

		} else if (tipPersoana.val() == 'JURIDICA') {
			$("#nume").hide();
			$("#prenume").hide();
			$("#cnp").hide();
			$("#cui").show();
			$("#contBancar").show();
			$("#denumire").show();
			$("#sucursala").show();
		} else {
			$("#nume").hide();
			$("#prenume").hide();
			$("#cnp").hide();
			$("#cui").hide();
			$("#contBancar").hide();
			$("#denumire").hide();
			$("#sucursala").hide();
		}
	}
</script>

<div class="container" style="margin-top: 1%;">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold">Creare cont
						nou</h3>
				</div>
				<div class="panel-body">
					<c:url value="/utilizatori" var="action" />
					<form:form action="${action}" method="POST"
						modelAttribute="utilizatorForm" autocomplete="off">
						<div class="form-group">
							<div class="input-group merged">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-user"></span></span>
								<form:input id="username" path="username" class="form-control"
									placeholder="Cont" />
								<span class="input-group-addon">*</span>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group merged">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-lock"></span></span>
								<form:input id="parola"
									title="Parola trebuie sa contina cel putin o cifra, o litera mica, o litera mare, un caracter special si o dimensiune cuprinsa intre 8 si 20 de caractere."
									path="parola" class="form-control" placeholder="Parola"
									type="password" />
								<span class="input-group-addon">*</span>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group merged">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-lock"></span></span>
								<form:input id="confParola" path="confParola"
									class="form-control" placeholder="Confirmare parola"
									type="password" />
								<span class="input-group-addon">*</span>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group merged">
								<form:input id="email" path="email" class="form-control"
									placeholder="Adresa de email" />
								<span class="input-group-addon">*</span>

							</div>
						</div>

						<div class="form-group">
							<div class="input-group merged">
								<form:select path="tipPersoana" class="form-control"
									id="tipPersoana" onchange="fizicaJuridica()">
									<optgroup label="Tip persoana">
										<form:option value="" hidden="true" disabled="true"
											selected="true">Tip persoana</form:option>
										<form:option value="FIZICA">Persoana fizica</form:option>
										<form:option value="JURIDICA">Persoana juridica</form:option>
									</optgroup>
								</form:select>
								<span class="input-group-addon">*</span>
							</div>
						</div>


						<div class="form-group" id="nume">
							<div class="input-group merged">
								<form:input path="nume" class="form-control" placeholder="Nume" />
								<span class="input-group-addon">*</span>

							</div>
						</div>
						<div class="form-group" id="prenume">
							<div class="input-group merged">
								<form:input path="prenume" class="form-control"
									placeholder="Prenume" />
								<span class="input-group-addon">*</span>

							</div>
						</div>
						<div class="form-group" id="cnp">
							<%--<div class="input-group merged">--%>
							<form:input path="cnp" class="form-control" placeholder="CNP" />
							<%--<span class="input-group-addon">*</span>--%>

							<%--</div>--%>
						</div>

						<div class="form-group" id="denumire">
							<div class="input-group merged">
								<form:input path="denumire" class="form-control"
									placeholder="Denumire" />
								<span class="input-group-addon">*</span>

							</div>
						</div>

						<div class="form-group" id="cui">
							<form:input path="cui" class="form-control" placeholder="CUI" />
						</div>
						<div class="form-group" id="contBancar">
							<form:input path="contBancar" class="form-control"
								placeholder="Cont bancar" />
						</div>
						
						<div class="form-group" id="sucursala">
							<form:input path="sucursala" class="form-control"
								placeholder="Sucursala / Filiala" />
						</div>
						<div class="form-group" id="telefon">
							<div class="input-group merged">
								<form:input path="telefon" class="form-control"
									placeholder="Telefon" />
								<span class="input-group-addon">*</span>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group merged">
								<form:select path="judet" id="judet" class="form-control"
									placeholder="Judet " onchange="populeazaLocalitati()">
									<form:option value="" hidden="true" disabled="true"
										selected="true">Selecteaza un judet</form:option>
									<c:forEach items="${judete}" var="judetul">
										<form:option data-id="${judetul.judet}"
											value="${judetul.denumire}" label="${judetul.denumire}" />
									</c:forEach>
								</form:select>
								<span class="input-group-addon">*</span>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group merged">
								<form:select id="localitate" path="localitate"
									class="form-control">
									<option value="">Selectati mai intai judetul</option>
								</form:select>
								<span class="input-group-addon">*</span>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group merged">
								<form:textarea path="adresa" class="form-control"
									placeholder="Adresa" />
								<span class="input-group-addon">*</span>
							</div>
						</div>
						<div class="form-group">
							<%--<div class="input-group merged">--%>
							<form:input path="codPostal" class="form-control"
								placeholder="Cod Postal" />
							<%--<span class="input-group-addon">*</span>--%>
							<%--</div>--%>
						</div>

						<div class="panel panel-default">
							<div class="panel-body">
								<div class="container-fluid">

									<div class="input-group">
										<div class="row">
											<div class="col-md-12">
												<img id="captcha_image" " src=<c:url value="/jcaptcha.jpg"/> />
											</div>
										</div>
										<div class="row form-horizontal">
											<div class="input-group">
												<div class="col-md-7">
													<input class="form-control " type="text" name="jcaptcha"
														placeholder="CAPTCHA" />
												</div>

												<div class="col-md-2"></div>
												<div class="col-md-2">
													<buttom class="btn btn-primary  "
														onclick="refreshCaptcha();" title="Refresh CAPTCHA">
													<span class="glyphicon glyphicon-refresh"></span>
													</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<p>Campurile marcate cu * sunt obligatorii</p>
						</div>
						<input type="submit" value="Inregistrare"
							class="btn btn-sm btn-posta btn-block" />
					</form:form>

				</div>
			</div>
		</div>
	</div>
</div>



