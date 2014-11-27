angular.module('BasicModule', ['ngAnimate'])

.controller('BasicController', function($scope) {
	var i = 4;
	$scope.rows = [ 'First row', 'Second row', 'Third row' ];

	$scope.add = function() {
		$scope.rows.push('Row number ' + i);
		i++;
	}
});
