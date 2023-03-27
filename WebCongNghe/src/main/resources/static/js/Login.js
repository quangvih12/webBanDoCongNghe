var app = angular.module("myApp", []);

app.controller("LoginController", function ($scope, $http) {


    $scope.login = function () {
        try {
            $http.post("http://localhost:8080/api/v1/auth/authenticate", {
                email: $scope.email,
                password: $scope.password
            }).then(function (response) {

                if (response.status === 200) {
                    sessionStorage.setItem('ten', $scope.email);
                    window.open("/view", '_self');
                }

            });
        } catch (error) {
            console.log(error);
        }

    }

})