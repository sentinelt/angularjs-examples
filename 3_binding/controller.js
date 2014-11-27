angular.module('BasicModule', [])

.controller('BasicController', function($scope) {
	$scope.equation = '2+2';

	$scope.$watch('equation', function(newValue, oldValue) {
		$scope.result = eval(newValue);
	});
});
