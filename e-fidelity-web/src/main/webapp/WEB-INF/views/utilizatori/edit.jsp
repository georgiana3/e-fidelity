<%@ include file="../template/taglibs.jsp"%>
<script>
	$(document)
			.ready(
					function() {
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
						
						populeazaLocalitati();
						$("#localitate").val("${utilizatorForm.localitate}")
								.trigger("chosen:updated");
						/*
						 // hide image not found icon
						 $("#sigla").error(function() {
						 $(this).hide();
						 });
						 */
					});
</script>
<script type="text/javascript">
	function populeazaLocalitati() {
		var $select = $("#judet");
		var $selectLocalitati = $("#localitate");
		var judet = $select.find("option:selected").val();
		if (judet) {
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
	}
</script>
<style>
textarea {
	resize: none;
}
</style>
<div class="container" style="margin-top: 1%;">

	<div class="well">

		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title" style="font-weight: bold">Editare
							cont</h3>
					</div>
					<div class="panel-body">
						<c:url value="/utilizatori/editdata" var="action" />
						<form:form action="${action}" method="POST"
							modelAttribute="utilizatorForm" autocomplete="off"
							class="form-horizontal">
							<div class="form-group">
								<label for="username" class="col-md-3 control-label">Cont</label>
								<div class="col-md-9">
									<form:input id="username" path="username" class="form-control"
										placeholder="Cont" readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label for="email" class="col-md-3 control-label">Adresa
									de email</label>
								<div class="col-md-9">
									<form:input id="email" path="email" class="form-control"
										placeholder="Adresa de email" readonly="true" />
								</div>
							</div>
							<div class="form-group ">
								<label for="telefon" class="col-md-3 control-label">Telefon</label>

								<div class="col-md-9">
									<form:input id="telefon" path="telefon" class="form-control"
										placeholder="Telefon" />
								</div>
							</div>

							<div class="form-group">
								<label for="judet" class="col-md-3 control-label">Judet</label>
								<div class="col-md-9">
									<form:select path="judet" id="judet" class="form-control"
										placeholder="Judet " onchange="populeazaLocalitati()">
										<form:option value="" hidden="true" disabled="true"
											selected="true">Selecteaza un judet</form:option>
										<c:forEach items="${judete}" var="judetul">
											<form:option data-id="${judetul.judet}"
												value="${judetul.denumire}" label="${judetul.denumire}" />
										</c:forEach>
									</form:select>
								</div>
							</div>

							<div class="form-group">
								<label for="localitate" class="col-md-3 control-label">Localitate</label>
								<div class="col-md-9">
									<form:select id="localitate" path="localitate"
										class="form-control">
										<option value="">Selectati mai intai judetul</option>
									</form:select>
								</div>
							</div>


							<div class="form-group">
								<label for=codpostal class="col-md-3 control-label">Cod
									postal</label>
								<div class="col-md-9">
									<form:input path="codPostal" id="codPostal"
										class="form-control" placeholder="Cod postal" />
								</div>
							</div>

							<div class="form-group">
								<label for="adresa" class="col-md-3 control-label">Adresa
								</label>
								<div class="col-md-9">
									<form:textarea id="adresa" path="adresa" class="form-control"
										placeholder="Adresa" />
								</div>
							</div>

							<c:choose>
								<c:when
									test="${utilizatorCurent['class'].simpleName eq 'PersoanaFizica'}">
									<div class="form-group">
										<label for="nume" class="col-md-3 control-label">Nume</label>
										<div class="col-md-9">
											<form:input id="nume" path="nume" class="form-control"
												placeholder="Nume" />
										</div>
									</div>
									<div class="form-group">
										<label for="prenume" class="col-md-3 control-label">Prenume</label>
										<div class="col-md-9">
											<form:input id="prenume" path="prenume" class="form-control"
												placeholder="Prenume" />
										</div>
									</div>
									<div class="form-group">
										<label for="cnp" class="col-md-3 control-label">CNP</label>
										<div class="col-md-9">
											<form:input id="cnp" path="cnp" class="form-control"
												placeholder="CNP" />
										</div>
									</div>
								</c:when>
								<c:when
									test="${utilizatorCurent['class'].simpleName eq 'PersoanaJuridica'}">

									<div class="form-group">
										<label for="denumire" class="col-md-3 control-label">Denumire</label>
										<div class="col-md-9">
											<form:input id="denumire" path="denumire"
												class="form-control" placeholder="denumire" />
										</div>
									</div>

									<div class="form-group">
										<label for="cui" class="col-md-3 control-label">CUI</label>
										<div class="col-md-9">
											<form:input id="cui" path="cui" class="form-control"
												placeholder="CUI" />
										</div>
									</div>
									<div class="form-group">
										<label for="contBancar" class="col-md-3 control-label">Cont
											bancar</label>
										<div class="col-md-9">
											<form:input id="contBancar" path="contBancar"
												class="form-control" placeholder="Cont Bancar" />
										</div>
									</div>

									<div class="form-group">
										<label for="sucursala" class="col-md-3 control-label">Sucursala</label>
										<div class="col-md-9">
											<form:input id="sucursala" path="sucursala"
												class="form-control" placeholder="Sucursala / Filiala" />
										</div>
									</div>

								</c:when>
							</c:choose>
							<div class="form-group">
								<div class="col-md-3 pull-right">
									<input type="submit" value="Modifica cont"
										class="btn btn-sm btn-posta btn-block " />
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			<div class="col-md-6 ">
				<div class=" panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title" style="font-weight: bold">Schimbare
							parola</h3>
					</div>
					<div class="panel-body">
						<c:url value="/utilizatori/editparola" var="action" />
						<form:form action="${action}" method="POST"
							modelAttribute="modificareParolaForm" autocomplete="off"
							class="form-horizontal">
							<div class="form-group">
								<label for="parolaVeche" class="col-md-3 control-label">Parola
									veche</label>
								<div class="col-md-9">
									<form:input id="parolaVeche" path="parolaVeche"
										class="form-control" placeholder="Parola veche"
										type="password" />
								</div>
							</div>
							<div class="form-group">
								<label for="parola" class="col-md-3 control-label">Parola
									noua</label>
								<div class="col-md-9">
									<form:input id="parola" path="parola" class="form-control"
										placeholder="Parola" type="password" />
								</div>

							</div>
							<div class="form-group">
								<label for="confParola" class="col-md-3 control-label">Confirmare
									parola </label>
								<div class="col-md-9">
									<form:input id="confParola" path="confParola"
										class="form-control" placeholder="Confirmare parola"
										type="password" />
								</div>

							</div>
							<div class="form-group">
								<div class="col-md-3 pull-right">
									<input type="submit" value="Schimba parola"
										class="btn btn-sm btn-posta btn-block" />
								</div>
							</div>
						</form:form>
					</div>
				</div>
				<%-- Sigla --%>
				<%-- 
				<div class=" panel panel-default"
					<c:if  test="${utilizatorCurent['class'].simpleName ne 'PersoanaJuridica'}" >hidden="true"</c:if>>
					<div class="panel-heading">
						<h3 class="panel-title" style="font-weight: bold">Schimbare
							sigla</h3>
					</div>


					<div class="panel-body">
						<div class="row">
							<div class="col-md-3 ">
								<img id="sigla" width="100" height="100"
									src="<c:url value='/utilizatori/sigla'/>" />
							</div>
						</div>
						<div>
							<c:url value="/utilizatori/change-image" var="action" />
							<form:form modelAttribute="changeImageForm" method="POST"
								action="${action}" enctype="multipart/form-data"
								class="form-horizontal">
								<fildset>
								<div class="form-group">
									<div class="btn btn-file">
										<form:input path="file" type="file" class="form-control" />
									</div>
									<label class="col-md-8">Incarcati o sigla cu
										dimensiunea maxim 300x300 px si maxim 0.5 MB</label> <input
										type="submit" class="btn btn-posta btn-sm"
										value="Schimba sigla" />
								</div>
								</fildset>
							</form:form>
						</div>
					</div>
					--%>
			</div>
		</div>
	</div>
</div>
</div>



