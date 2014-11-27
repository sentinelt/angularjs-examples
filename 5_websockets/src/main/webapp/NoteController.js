angular.module('NoteModule', [ 'angular-websocket' ])

.config(function(WebSocketProvider){
	WebSocketProvider.prefix('')
		.uri('ws://localhost:8080/note-websocket');
})

.controller('NoteController', function($scope, WebSocket) {
	var serverNode;
	WebSocket.onmessage(function(event) {
		serverNote = event.data;
		$scope.note = event.data;
	});

    $scope.$watch('note', function(newValue, oldValue) {
		if (WebSocket.currentState() == 'OPEN' && (serverNote != newValue)) {
			WebSocket.send(newValue);
		}
	});
});
