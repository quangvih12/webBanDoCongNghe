var app = angular.module("myApp", []);

app.controller("LoginController", function ($scope, $http,$rootScope) {
    $rootScope.isLoggedIn = false;
    $scope.login = function () {

        $http.post("http://localhost:8080/api/v1/auth/authenticate", {
            email: $scope.email,
            password: $scope.password
        }).then(function (response) {
            if (response.data.error !== null) {
                document.getElementById('error').textContent = response.data.error;
                console.log($scope.error);
            } else {
                $rootScope.isLoggedIn = true;
                console.log( "login: "+$rootScope.isLoggedIn);
                sessionStorage.setItem('ten', $scope.email);
                window.open("/view", '_self');
            }

        });


    }

})