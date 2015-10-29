<%@ include file="./taglibs.jsp"%>

<style>
#clearCacheLink {
	color: yellow
}

#clearCacheLink:hover {
	color: black
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('.big-navbar-item').width(Math.max.apply(Math, $('.big-navbar-item').map(function() {
			return $(this).outerWidth();
		}).get()));
		$('.big-navbar-item').height(Math.max.apply(Math, $('.big-navbar-item').map(function() {
			return $(this).outerHeight();
		}).get()));

		$(window).trigger("resize");
	});
</script>

<security:authorize access="hasRole('PROGRAMMER') ">
	<script type="text/javascript">
		function clearServerCache() {
			$action = '<c:url value="/developer/cache/clear-hibernate-l2-cache"/>';
			$form = $('<form action = "' + $action + '" method = "POST"/>');
			$form.submit();
		}
	</script>
</security:authorize>


<div class="container-fluid" id="menu">
	<div class="navbar-header">
		<button class="navbar-toggle" type="button" data-toggle="collapse"
			data-target=".bs-navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
	</div>
	<nav class="collapse navbar-collapse bs-navbar-collapse special-menu"
		role="navigation" style="width: 100%; height: 100%;">



		<ul class="nav navbar-nav " id="mmenu"
			style="width: 100%; height: 100%;">

			<!-- 			<li class="big-navbar-item hidden-xs "><div -->
			<!-- 					style="cursor: pointer;" -->
			<%-- 					onclick="window.location='<c:url value="/"/>'" id="logobox"></div></li> --%>

			<!-- TOATA LUMEA -->
			<li class="big-navbar-item"><a class="meniuFix"
				href="<c:url value="/"/> "> <span
					class="glyphicon glyphicon-home" aria-hidden="true"></span> <br>
					<s:message code="label.header.home" />
			</a></li>
			<!-- DOAR NELOGATI -->
			<!-- 			TODO:Volksbank hardcoding  ? -->
			<security:authorize access="!isAuthenticated()">
				<li class="big-navbar-item"><a class="meniuFix"
					href="<c:url value="/utilizatori/new"/> "> <span
						class="glyphicon glyphicon-pencil" aria-hidden="true"></span> <br>
						<s:message code="label.header.utilizator.new" /></a></li>
			</security:authorize>
			<!-- DOAR LOGATI -->



		</ul>




	</nav>
</div>