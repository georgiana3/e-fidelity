<%@ include file="../template/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {

		$("#judet").chosen({
			width : '100%'
		});
		$("#judet").change(function() {
			var $select = $("#judet");
			var $selectLocalitati = $("#localitate");
			var judet = $select.find("option:selected").val();
			$.ajax({
				url : '<c:url value="/ajax/getLocalitatiByJudet"/>',
				contentType : "application/json",
				data : {
					judet : judet
				},
				async : false,
				dataType : "json",
				success : function(data) {
					$selectLocalitati.html("");
					$.each(data, function(index, object) {
						$selectLocalitati.append($("<option data-codpostal='" + object.value + "'>" + object.label + "</option>"));
					});

					$selectLocalitati.trigger("chosen:updated");
					$selectLocalitati.val("${utilizator.localitate}");
					$selectLocalitati.trigger("change");

				},
				error : function() {
					alert('BAD REQUEST');
				}
			});
		}).trigger("change");

		$("#localitate").chosen({
			width : '100%'
		});

		$("#localitate").change(function() {
			selected = $("#localitate").find("option:selected");
			$("#codIndrumare").val(selected.data('codpostal'));
			$("#codPostal").val("");
		});

	});
</script>
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
						<c:url value="/utilizatori/${idUtilizator}/edit" var="action" />
						<form:form action="${action}" method="PUT"
							modelAttribute="utilizatorForm" autocomplete="off"
							class="form-horizontal">
							<div class="form-group">
								<label for="username" class="col-md-3 control-label">Cont</label>
								<div class="col-md-9">
									<form:input id="username" path="username" class="form-control"
										placeholder="Cont" disabled="true" />
								</div>
							</div>

							<div class="form-group ">
								<label for="telefon" class="col-md-3 control-label">Telefon</label>

								<div class="col-md-9">
									<form:input id="telefon" path="telefon" class="form-control"
										placeholder="Telefon" />
								</div>
							</div>

							<div class="form-group required">
								<label for="judet" class="col-md-3 control-label">Judet</label>
								<div class="col-md-9">
									<form:select path="judet" id="judet" class="form-control">
										<form:option value="" hidden="true" disabled="true"
											selected="true">Selecteaza un judet</form:option>
										<c:forEach items="${judete}" var="judetul">
											<form:option data-id="${judetul.judet}"
												value="${judetul.denumire}" label="${judetul.denumire}" />
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="form-group required">
								<label class="col-md-3 control-label" for="localitate">Localitate</label>
								<div class="col-md-9">
									<form:select id="localitate" path="localitate"
										class="form-control">
										<option>Selectati mai intai judetul</option>
									</form:select>
								</div>
							</div>


							<div class="form-group">
								<label for=codpostal class="col-md-3 control-label">Cod
									postal</label>
								<div class="col-md-9">
									<form:input path="codPostal" id="codpostal"
										class="form-control" placeholder="Cod postal" />
									<form:input path="codIndrumare" id="codIndrumare"
										class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label for="adresa" class="col-md-3 control-label">Adresa
									exacta</label>
								<div class="col-md-9">
									<form:input id="adresa" path="adresa" class="form-control"
										placeholder="Adresa exacta" />
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
												class="form-control" placeholder="Cont bancar" />
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
							
							<div class="form-group">
							<a class="btn btn-primary btn-sm pull-right"
								href="<c:url value="/utilizatori/${idUtilizator+1}/edit"/>"
								type="button">Next</a>
								
							<a class="btn btn-primary btn-sm "
								href="<c:url value="/utilizatori/${idUtilizator-1}/edit"/>"
								type="button">Previous</a>
								
							</div>
						</form:form>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>



