angular.module('NoteModule', [ 'ngResource' ])

.controller('NoteController', function($scope, $resource) {
	var Note = $resource('note', {}, { });

	var note = Note.get(function() {
		//success
		$scope.note = note;

		$scope.$watch('note.content', function(newValue, oldValue) {
			Note.save($scope.note, function() {
			}, function() {
				alert("Error saving the note");
			});
		});
	}, function() {
		//error
		alert("Error contacting REST service");
	});


});
