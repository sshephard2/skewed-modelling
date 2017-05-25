angular.module('demo', [])
.controller('Ticket', function($scope, $http) {
    $http.get('http://localhost:8080/book').
        then(function(response) {
            $scope.booking = response.data;
        });
});