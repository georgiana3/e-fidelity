<%@ include file="../template/taglibs.jsp"%>

<script type="text/javascript">
	function refreshCaptcha() {
		$("#captcha_image").attr("src", "<c:url value='/jcaptcha.jpg'/>");
	}

	$(document).ready(function() {
		// 		$('.login-panel').height($('#imagine').height());
	});
</script>

<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-1" id="panel-auth">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold">
						<s:message code="label.login.title" />
					</h3>
				</div>
				<div class="panel-body">
					<form
						action='<c:url value="/j_spring_security_check"/>'
						method="post">
						<c:if test="${not empty param['error']}">
							<input id="hidden_error" type="hidden"
								value="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}">
							<div class="alert alert-error" style="margin-bottom: 10px;">
								<s:message code="message.login.error" />
							</div>
						</c:if>
						<fieldset>
							<div class="form-group">
								<div class="input-group merged">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-user"></span></span> <input
										class="form-control"
										placeholder="<s:message code="label.login.username"/>"
										name="j_username" type="text" autofocus>
								</div>
							</div>
							<div class="form-group">
								<div class="input-group merged">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-lock"></span></span> <input
										class="form-control"
										placeholder="<s:message code="label.login.password"/>"
										name="j_password" type="password" value="">
								</div>
							</div>
							<c:if test="${not empty  captcha}">
								<s:message code="message.login.captchawarn" />
								<div class="panel panel-default">
									<div class="panel-body">
										<div class="container-fluid">

											<div class="input-group">
												<div class="row">
													<div class="col-md-12">
														<img id="captcha_image"
															" src=<c:url value="/jcaptcha.jpg"/> />
													</div>
												</div>
												<div class="row form-horizontal">
													<div class="input-group">
														<div class="col-md-7">
															<input class="form-control " type="text" name="jcaptcha"
																placeholder="CAPTCHA" />
														</div>

														<div class="col-md-2"></div>
														<div class="col-md-2">
															<buttom class="btn btn-primary  "
																onclick="refreshCaptcha();" title="Refresh CAPTCHA">
															<span class="glyphicon glyphicon-refresh"></span>
															</button>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:if>

							<div class="checkbox">
								<label> <input name="_spring_security_remember_me"
									type="checkbox"> <s:message
										code="label.login.rememberme" />
								</label>
							</div>
							<input type="submit" value="Autentificare"
								class="btn btn-sm btn-posta btn-block" />
							<div class="row">
								<div class="col-md-6">
									<a href="<c:url value="/utilizatori/new"/>"><s:message
											code="label.login.register" /></a>
								</div>
								<div class="col-md-6">
									<a href="<c:url value="/utilizatori/forgot-password"/>"
										class="pull-right"><s:message
											code="label.login.forgotpassword" /></a>
								</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<div class="col-md-6">

			<div id="myCarousel" class="carousel slide" data-interval="9000"
				data-ride="carousel" style="margin-top: 16%;">
				<!-- Carousel indicators -->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<!-- 
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
					 -->
				</ol>

				<!-- Carousel items -->
				<div class="carousel-inner">
					<div class="active item ">
						<a href="http://www.prioripost.ro/" target="_blank"><img
							src="<c:url value="/resources/img/prioripost2.jpg"/>" /> </a>
					</div>
					<!-- 
					<div class="item">
						<img id="imagine"
							src="<c:url value="/resources/img/prioripost2.jpg"/>" />
					</div>
					<div class="item">
						<img src="<c:url value="/resources/img/prioripost2.jpg"/>" />
					</div>
					 -->
				</div>
				<!-- Carousel nav -->
				<a class="carousel-control left" href="#myCarousel"
					data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>


				</a> <a class="carousel-control right" href="#myCarousel"
					data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-10 col-md-offset-1">
			<div class="well">
				<h4>Despre AWB Posta Romana</h4>
				<p>AWB Posta Romana este o aplicatie online creata special
					pentru a veni in ajutorul clientilor nostri, oferindu-le mai mult
					control asupra gestionarii expeditiilor. Principalele facilitati
					ale aplicatiei AWB Posta Romana au fost gandite in functie de
					nevoile specifice ale clientilor, care acum beneficiaza de:</p>
				<p>- crearea si vizualizarea documentelor de transport AWB</p>
				<p>- incarcarea, editarea si vizualizarea borderourilor</p>
				<p>- gestionarea online a expeditiilor efectuate intr-o anumita
					perioada de timp</p>
				<p>- generarea de rapoarte statistice atat pentru statusul
					trimiterilor cat si pentru incasarea rambursurilor</p>


			</div>

		</div>
	</div>