<%@ include file="../template/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {
		$("#fromDate").datepicker();
		$("#toDate").datepicker();
	});
</script>
<div class="container" style="margin-top: 1%;">

	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold">Raport</h3>
				</div>
				<div class="panel-body">
					<c:url value="/raport/general" var="action" />
					<div class="col-md-12">
						<form:form class="form-horizontal" action="${action}"
							modelAttribute="raportForm" method="GET">
							<fieldset>
								<div class="form-group">
									<div class="input-group">
										<span class="input-group-addon">De la data</span>
										<form:input class="form-control" id="fromDate" type="text"
											path="dataStart" />
										<span class="input-group-addon">Pana la data</span>
										<form:input class="form-control" id="toDate" type="text"
											path="dataEnd" />
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-calendar"></i></span>

									</div>
								</div>
								<div class="form-group">

									<div class="col-md-4 pull-right">
										<label for="sel" class="control-label">Selectati
											formatul</label> <select name="format" class="form-control" id="sel">
											<option value="xls">Excel</option>
											<option value="pdf">PDF</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-4 pull-right">
										<input type="submit" value="Descarca"
											class="btn btn-sm btn-posta btn-block" />
									</div>
								</div>
							</fieldset>
						</form:form>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
