<%@ include file="../template/taglibs.jsp"%>

<script>
	$(document).ready(function() {
		$("#judet").autocomplete({
			source : '<c:url value="/ajax/getJudete"/>',
			minLength : 3,
			delay : 500,
			select : function(event, ui) {
				event.preventDefault();
				$('#judetid').val(ui.item.value);
				$('#judet').val(ui.item.label);

				$('#localitateid').val("");
				$('#localitate').val("");
				$('#stradaid').val("");
				$('#strada').val("");
				$('#codpostal').val("");
			},
			change : function(event, ui) {
				event.preventDefault();

			}
		});

		$("#localitate").autocomplete({

			source : function(request, response) {
				$.ajax({
					url : '<c:url value="/ajax/getLocalitati"/>',
					dataType : "json",
					data : {
						term : request.term,
						judetid : $("#judetid").val()
					},
					success : function(data) {
						response(data);
					}
				});
			},
			minLength : 3,
			delay : 500,
			select : function(event, ui) {
				event.preventDefault();

				$('#localitateid').val(ui.item.value);
				$('#localitate').val(ui.item.label);

				$('#stradaid').val("");
				$('#strada').val("");
				$('#codpostal').val("");

				$.ajax({
					type : "get",
					url : '<c:url value="/ajax/areStrazi"/>',
					contentType : 'text',
					data : 'localitateid=' + $('#localitateid').val(),
					success : function(response) {
						if (response == "0") {
							$('#codpostal').val($('#localitateid').val());
						}
					},
					error : function() {
						alert('Error while request..');
					}
				});

			}
		});

		$("#strada").autocomplete({

			source : function(request, response) {
				$.ajax({
					url : '<c:url value="/ajax/getStrazi"/>',
					dataType : "json",
					data : {
						term : request.term,
						localitateid : $("#localitateid").val()
					},
					success : function(data) {
						response(data);
					}
				});
			},
			minLength : 3,
			delay : 500,
			select : function(event, ui) {
				event.preventDefault();
				$('#stradaid').val(ui.item.value);
				$('#strada').val(ui.item.label);
				$('#codpostal').val(ui.item.value);
			}
		});

	});
</script>
<div class="container" style="margin-top: 1%;">

	<div class="well">

		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title" style="font-weight: bold">Coduri
							postale</h3>
					</div>
					<div class="panel-body">

						<form class="form-horizontal">
							<fildset>
							<div class="form-group">
								<label for="judet" class="col-md-3 control-label">Judet</label>
								<div class="col-md-9">
									<input id="judet" class="form-control" placeholder="Judet" />
									<input type="hidden" id="judetid" path="judet"
										class="form-control" placeholder="Judetid" />
								</div>
							</div>
							<div class="form-group">
								<label for="localitate" class="col-md-3 control-label">Localitate</label>
								<div class="col-md-9">
									<input id="localitate" class="form-control"
										placeholder="Localitate" /> <input type="hidden"
										id="localitateid" path="localitate" class="form-control"
										placeholder="Localitateid" />
								</div>
							</div>
							<div class="form-group">
								<label for=starada class="col-md-3 control-label">Strada</label>
								<div class="col-md-9">
									<input id="strada" class="form-control" placeholder="Strada" />
									<input type="hidden" id="stradaid" class="form-control"
										placeholder="Stradaid" />
								</div>
							</div>
							<div class="form-group">
								<label for=codpostal class="col-md-3 control-label">Cod
									postal</label>
								<div class="col-md-9">
									<input path="codPostal" id="codpostal" class="form-control"
										placeholder="Cod postal" />
								</div>
							</div>
							</fildset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
