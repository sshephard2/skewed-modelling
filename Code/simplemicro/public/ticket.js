angular.module('demo', [])
.controller('Ticket', function($scope, $http) {
    $http.get('http://localhost:8080/search').
        then(function(response) {
            $scope.tickets = response.data;
        });
});