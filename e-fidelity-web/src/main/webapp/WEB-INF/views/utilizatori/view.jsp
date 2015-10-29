<%@ include file="../template/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {		
	});
</script>

<div class="container" style="margin-top: 1%;">
	<div class="row">
		<div class="well">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold">
						Informatii despre utilizatorul
						<c:out value="${utilizator.username}" />

					</h3>

				</div>
				<div class="panel-body"
					style="padding-bottom: 0px; padding-top: 0px;">
					<div class="row">

						<div class="col-md-6" style="padding: 0px;">
							<c:choose>
								<c:when
									test="${utilizator['class'].simpleName eq 'PersoanaFizica'}">
									<table
										class="table table-striped table-condensed table-bordered">
										<tr>
											<td class="col-md-3">Nume</td>
											<td class="col-md-9"><c:out value="${utilizator.nume}" /></td>
										</tr>
										<tr>
											<td class="col-md-3">Prenume</td>
											<td class="col-md-9"><c:out
													value="${utilizator.prenume}" /></td>
										</tr>
										<tr>
											<td class="col-md-3">CNP</td>
											<td class="col-md-9"><c:out
													value="${utilizator.cnp}" /></td>
										</tr>
										<tr>
											<td class="col-md-3">Telefon</td>
											<td class="col-md-9"><c:out
													value="${utilizator.telefon}" /></td>
										</tr>
										<tr>
											<td class="col-md-3">E-mail</td>
											<td class="col-md-9"><c:out value="${utilizator.email}" /></td>
										</tr>
									</table>
								</c:when>
								<c:when
									test="${utilizator['class'].simpleName eq 'PersoanaJuridica'}">
									<table
										class="table table-striped table-condensed table-bordered">
										<tr>
											<td class="col-md-3">Denumire</td>
											<td class="col-md-9"><c:out
													value="${utilizator.denumire}" /></td>
										</tr>
										<tr>
											<td class="col-md-3">CUI</td>
											<td class="col-md-9"><c:out
													value="${utilizator.cui}" /></td>
										</tr>
										<tr>
											<td class="col-md-3">Cont bancar</td>
											<td class="col-md-9"><c:out
													value="${utilizator.contBancar}" /></td>
										</tr>
										<tr>
											<td class="col-md-3">Telefon</td>
											<td class="col-md-9"><c:out
													value="${utilizator.telefon}" /></td>
										</tr>
										<tr>
											<td class="col-md-3">E-mail</td>
											<td class="col-md-9"><c:out value="${utilizator.email}" /></td>
										</tr>
									</table>
								</c:when>
							</c:choose>
						</div>
						<div class="col-md-6" style="padding: 0px;">
							<table class="table table-striped table-condensed table-bordered">

								<tr>
									<td class="col-md-3">Adresa</td>
									<td class="col-md-9"><c:out value="${utilizator.adresa}" /></td>
								</tr>
								<tr>
									<td class="col-md-3">Localitate</td>
									<td class="col-md-9"><c:out
											value="${utilizator.localitate}" /></td>
								</tr>
								<tr>
									<td class="col-md-3">Judet</td>
									<td class="col-md-9"><c:out value="${utilizator.judet}" /></td>
								</tr>
								<tr>
									<td class="col-md-3">Cod postal</td>
									<td class="col-md-9"><c:out
											value="${utilizator.codPostal}" /></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>