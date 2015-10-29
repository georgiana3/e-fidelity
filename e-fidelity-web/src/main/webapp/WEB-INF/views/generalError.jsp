<%@ include file="template/taglibs.jsp"%>

<div class="container">
	<div class="well">
		<div class="row">
			<div class="col-md-8">${errorMessage}</div>
			<div class="col-md-4 pull-right">
				<img width="300" height="300"
					src="<c:url value='/resources/img/error.png'/>" />
			</div>
		</div>
	</div>
</div>