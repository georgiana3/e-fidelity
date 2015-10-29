<%@ include file="../template/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {

	});
</script>

<div class="container" style="margin-top: 1%;">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center" style="font-weight: bold;">
						<span class="glyphicon glyphicon-search"></span> Cautare
						utilizatori
					</h3>
				</div>
				<div class="panel-body">
					<c:url value="/utilizatori" var="action" />
					<form:form action="${action}" method="get"
						modelAttribute="utilizatorSearchForm" class="form-horizontal">

						<fildset>
						<div class="form-group">
							<label for="username" class="col-md-4 control-label">Username</label>
							<div class="col-md-8">
								<form:input path="username" id="username"
									class="form-control input-sm" />
							</div>
						</div>

						<div class="form-group">
							<label for="nume" class="col-md-4 control-label">Nume</label>
							<div class="col-md-8">
								<form:input path="nume" id="nume" class="form-control input-sm" />
							</div>
						</div>

						<div class="form-group">
							<label for="tipUtilizator" class="col-md-4 control-label">Tip
								Utilizator</label>
							<div class="col-md-8">
								<form:select path="tipUtilizator" class="form-control input-sm"
									id="tipUtilizator">

									<form:option value="" hidden="true" disabled="true"
										selected="true">Tip persoana</form:option>
									<form:option value="">Deselectare</form:option>
									<optgroup label="Tip utilizator">
										<form:option value="FIZICA">Persoana fizica</form:option>
										<form:option value="JURIDICA">Persoana juridica</form:option>
									</optgroup>
								</form:select>
							</div>
						</div>
						</fildset>
						<div class="form-group">
							<div class="col-md-3 pull-right">
								<input type="submit" value="Cauta"
									class="btn btn-sm btn-posta btn-block" />
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">
			<div class="well">
				<display:table sort="list" requestURI="#rezultat" id="trez"
					class="table table-striped table-bordered table-condensed"
					name="utilizatori" pagesize="15" export="false"
					style="font-size:12px;">
					<display:column property="username" title="Username"
						sortable="true" headerClass="sortable" />
					<display:column media="html" title="Nume / Denumire Firma">
						<c:if test="${trez['class'].simpleName eq 'PersoanaJuridica'}">
							<c:out value="${trez.denumire}" />
						</c:if>
						<c:if test="${trez['class'].simpleName eq 'PersoanaFizica'}">
							<c:out value="${trez.nume} ${trez.prenume}" />
						</c:if>
					</display:column>
					<display:column property="email" title="Email" sortable="true"
						headerClass="sortable" />
					<display:column property="telefon" title="Telefon" sortable="true"
						headerClass="sortable" />
					<display:column property="tipUtilizator" title="Tip Persoana"
						sortable="true" headerClass="sortable" />
					<display:column media="html" title="Status">
						<c:choose>
							<c:when test="${trez.valid}">activ</c:when>
							<c:otherwise>inactiv</c:otherwise>
						</c:choose>
					</display:column>
					<display:column media="html" title="Optiuni"
						style="text-align:center; width:130px;">

						<c:if test="${trez.valid}">
							<c:url var="action"
								value="/utilizatori/${trez.idUtilizator}/invalideaza" />





						</c:if>
						<c:if test="${not trez.valid}">


							<c:url var="action"
								value="/utilizatori/${trez.idUtilizator}/valideaza" />
						</c:if>

						<form class="form form-inline" action="${action}" method="post">
							<div class="btn-block btn-group">
								<a class="btn btn-primary btn-xs"
									href="<c:url value="/utilizatori/${trez.idUtilizator}"/>"
									type="button">Vezi</a>
								<c:if test="${trez.valid}">

									<input type="submit" class="btn btn-xs btn-danger"
										value="Invalideaza" />
								</c:if>
								<c:if test="${not trez.valid}">
									<input type="submit" class="btn btn-xs btn-info"
										value="Revalideaza" />
								</c:if>
							</div>
						</form>
					</display:column>
				</display:table>
			</div>
		</div>
	</div>
</div>
