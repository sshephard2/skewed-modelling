angular.module("worldTicket", [])
.controller('ticketSearch', function($scope, $http) {
	
	// Available sports and hostnames of their microservices
	$scope.availSports = [
		{sport:'Athletics', service:'http://51.140.60.47:8080'},
		{sport:'Cycling', service:'http://51.140.56.220:8080'},
	];
	
	// Search function
	$scope.search = function() {
		
		// Get hostname
		$scope.microserviceHost = $scope.availSports.find(function(obj) {return obj.sport === $scope.searchSport}).service;
		
		// Build search query string
		var queryString = '';
		if ($scope.searchSport) {
			queryString = "sport=" + $scope.searchSport;
		}
		if (queryString && $scope.searchDay) {
			queryString += "&day=" + $scope.searchDay;
		}
		
		// Consume RESTful API
		$http.get($scope.microserviceHost + "/search?" + queryString)
		.then(function(response) {
			$scope.searchResult = response.data;
		});
	};
});