<%@ include file="../template/taglibs.jsp"%>

<div class="container" style="margin-top: 1%;">
	<div class="well col-md-6 " style="border-radius: 1px;">
		<p>
			Autentificare reusita.<br> Bine ai venit, <span
				style="color: #d22630"><security:authentication
					property="principal.principal" /></span>!
		</p>
	</div>

</div>
