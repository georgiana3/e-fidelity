<%@ include file="./taglibs.jsp"%>

<security:authorize access="hasRole('PROGRAMMER') ">
	<script type="text/javascript">
		function clearServerCache() {
			$action = '<c:url value="/developer/cache/clear-hibernate-l2-cache"/>';
			$form = $('<form action = "' + $action + '" method = "POST"/>');
			$form.submit();
		}
	</script>
</security:authorize>


<div class="container-fluid menuTop">

	<nav class="navbar menu-top" role="navigation">
		<!-- 	<div class="container-fluid menuTop"> -->

		<ul class='menu-top '>
			<li style="float: left; margin-left:-42px"><a 
				href='<s:url value="/faq"></s:url>'> <span 
					class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> 
					Ajutor
			</a></li>
			<security:authorize access="isAuthenticated()">
				<li style="float: right; color: white"><span><security:authentication
							property="principal.principal" /></span> (<a
					href="<s:url value="/logout"></s:url>"> <span
						class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
						Delogare
				</a>)</li>
				<li style="float: right;border-left: 1px solid #000;"><a
					href="<c:url value="/utilizatori/edit"/> "> <span
						class="glyphicon glyphicon-edit" aria-hidden="true"></span>
						Editare cont
				</a></li>



			</security:authorize>
			<security:authorize access="!isAuthenticated()">
				<li style="float: right"><a
					href='<s:url value="/login"></s:url>'>Autentificare </a></li>
			</security:authorize>

		</ul>
		<!-- 	</div> -->
	</nav>
</div>

<%-- <c:set var="langspec" --%>
<%-- 			value="${cookie['org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE'].value}" /> --%>
<!-- Limba -->
<!-- 			<li class="dropdown"><a href="#" data-toggle="dropdown" -->
<%-- 				class="dropdown-toggle"><s:message code="label.generic.language" /> --%>
<%-- 					(${fn:toUpperCase( --%>
<%-- 							param.lang ne null ? param.lang : (langspec ne null ? langspec : "RO"))}) --%>
<!-- 					<strong class="caret"></strong> </a> -->
<!-- 				<ul class="dropdown-menu"> -->
<!-- 					<li><a href="?lang=ro">RO</a></li> -->
<!-- 					<li><a href="?lang=en">EN</a></li> -->
<!-- 				</ul></li> -->