<%@ include file="../template/taglibs.jsp"%>


<div class="container" style="margin-top: 1%;">
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="panel panel-default">
				<div class="panel-body">
					<p><s:message code="label.home.notsignedin.welcome"/>.</p>
					<p><s:message code="label.home.upload.loginhint"/>.</p>

					<div class="col-md-3 pull-right" style="margin-top:5%; text-align:right;">
						<a href='<s:url value="/login" />'
							class="btn btn-large btn-posta"><s:message code="label.home.notsignedin.loginbutton"/></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>