'use strict';
/**
 * Controller for awb
 */
app.controller('AwbCtrl', [
		'$scope',
		function($scope) {

			$scope.checkMandat = function() {
				return !($scope.ramburs
						&& (angular.element('#tipTrimitere').find(
								'option:selected').data('codclient')
						|| angular.element('#tipTrimitere').find(
						'option:selected').data("tipmandat"))
						);
			};
		} ]);