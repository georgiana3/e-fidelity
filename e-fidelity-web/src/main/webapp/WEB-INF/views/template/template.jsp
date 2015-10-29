<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
	prefix="tilesx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html class="no-js" xmlns="http://www.w3.org/1999/xhtml" lang="en"
	xml:lang="en">
<head>

<meta name="application-name" content="@dev.build.finalName@"
	data-version="@dev.build.version@" data-build-number="@buildNumber@"
	data-build-timestamp="@dev.build.timestamp@" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>AWB</title>

<link href="<c:url value="/resources/css/bootstrap.css" />"
	rel="stylesheet" media="screen" />
<link href="<c:url value="/resources/css/bootstrap-social.css" />"
	rel="stylesheet" media="screen" />
<link href="<c:url value="/resources/css/jquery-ui.css" />"
	rel="stylesheet" media="screen" />
<link
	href="<c:url value="/resources/css/displaytag.css" />?v=@buildNumber@"
	rel="stylesheet" media="screen" />
<link href="<c:url value="/resources/css/jquery.confirmon.css" />"
	rel="stylesheet" media="screen" />
<link href="<c:url value="/resources/css/chosen.css" />"
	rel="stylesheet" media="screen" />
<link
	href="<c:url value="/resources/fonts/font-awesome/css/font-awesome.css" />"
	rel="stylesheet" media="screen" />
<link href="<c:url value="/resources/css/sb-admin.css" />"
	rel="stylesheet" media="screen" />
<link href="<c:url value="/resources/css/bootstrap-ie7.css" />"
	rel="stylesheet" media="screen" />
<link href="<c:url value="/resources/css/style.css" />?v=@buildNumber@"
	rel="stylesheet" media="screen" />

<script type="text/javascript"
	src="<c:url value="/resources/js/loadjscss_dynamically.js" />"></script>

<script type="text/javascript">
	if (!((window.location.href.indexOf(".posta.ro") > -1)
			|| (window.location.href.indexOf("10.11.0.165") > -1) || (window.location.href
			.indexOf("localhost") > -1))) {

		/* Analytics */
		(function(i, s, o, g, r, a, m) {
			i['GoogleAnalyticsObject'] = r;
			i[r] = i[r] || function() {
				(i[r].q = i[r].q || []).push(arguments)
			}, i[r].l = 1 * new Date();
			a = s.createElement(o), m = s.getElementsByTagName(o)[0];
			a.async = 1;
			a.src = g;
			m.parentNode.insertBefore(a, m)
		})(window, document, 'script',
				'//www.google-analytics.com/analytics.js', 'ga');

		ga('create', 'UA-32709640-2', 'auto');
		ga('send', 'pageview');

		/* Begin Cookie Consent plugin by Silktide - http://silktide.com/cookieconsent */
		loadjscssfile(
				"//s3.amazonaws.com/cc.silktide.com/cookieconsent.latest.min.js",
				"js");
		loadjscssfile(
				"<c:url value="/resources/js/cookieconsent_activate.js" />",
				"js");
	}
</script>
<!-- End analytics -->

<script src="<c:url value="/resources/js/jquery-1.11.0.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/js/jquery.ui.datepicker-ro.js" />"></script>
<script>
	/*** Handle jQuery plugin naming conflict between jQuery UI and Bootstrap ***/
	$.widget.bridge('uibutton', $.ui.button);
	$.widget.bridge('uitooltip', $.ui.tooltip);
</script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.confirmon.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.validate.js" />"></script>
<script src="<c:url value="/resources/js/jquery.validate.extras.js" />"></script>
<script src="<c:url value="/resources/js/chosen.jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/redirect.jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/spin.js" />"></script>
<script src="<c:url value="/resources/js/sb-admin.js" />"></script>
<script src="<c:url value="/resources/js/jquery.confirm.js" />"></script>
<script
	src="<c:url value="/resources/js/plugins/metisMenu/jquery.metisMenu.js"/>"></script>
<!-- Modernizr includes html5shiv (HTML5 support for IE) -->
<script src="<c:url value="/resources/js/modernizr-2.7.1.js" />"></script>
<script src="<c:url value="/resources/js/respond.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.blockUI.js" />"></script>
<script src="<c:url value="/resources/js/jquery.cookie.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.displaytag-ajax-1.2.js" />"></script>

<!-- AngularJS stuff -->
<script type="text/javascript"
	src="<c:url value="/resources/js/angularjs/vendor/angular/angular.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/angularjs/app.js"/>?v=@buildNumber@"></script>
<!-- End AngularJS stuff -->
<%-- Dynamically load scripts --%>
<tilesx:useAttribute id="scripts" name="scripts"
	classname="java.util.List" ignore="true" />
<c:forEach var="scriptPath" items="${scripts}">
	<script type="text/javascript"
		src="<c:url value="/resources/js/${scriptPath}"/>?v=@buildNumber@"></script>
</c:forEach>
<%-- End dynamically loading of scripts --%>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$(window).bind("resize", function() {
			h = $("#top").height();
			var myElement = document.querySelector("#content");
			var messageElem = document.querySelector("#info-message");
			messageElem.style.marginTop = "" + h - 50 + "px";
		});

		$(function() {
			// this will get the full URL at the address bar
			var url = window.location.href;

			// passes on every "a" tag 
			$("#top a").each(function() {
				// checks if its the same on the address bar
				if (url == (this.href)) {
					$(this).closest("li").addClass("active");
				}
			});
		});

		$(window).trigger("resize");
	});
</script>

<body id="parentPage" ng-app="e-fidelity">
	<div class="container" style="margin-top: 1%">
		<!-- Header -->

		<header class="navbar  bs-docs-nav navbar-fixed-top navbar-default"
			id="top" role="banner">
			<tiles:insertAttribute name="headerTop" defaultValue="" />
			<tiles:insertAttribute name="header" defaultValue="" />

		</header>

		<!-- Messages -->
		<div id="info-message" class="row">
			<div class="col-md-12">
				<c:if test="${not empty message}">
					<c:set value="alert-${fn:toLowerCase(message.type)}"
						var="alertClass" />
					<div class="alert ${alertClass}">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<%
									/* Display a message by its code. If the code was not found, it will be displayed as default text */
								%>
						<s:message code="${message.message}" arguments="${message.args}"
							text="${message.message}" />
					</div>
				</c:if>
			</div>
		</div>
		<!-- Body -->

		<div id="content" class="row">
			<tiles:insertAttribute name="body" defaultValue="" />
		</div>

		<footer>
			<tiles:insertAttribute name="footer" defaultValue="" />
		</footer>
		<!-- Footer -->
		<!-- 		<div id="footer" class="row"> -->

		<!-- 		</div> -->
	</div>
</body>
</html>