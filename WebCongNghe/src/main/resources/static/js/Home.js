window.HomeController = function ($scope, $http,$rootScope) {
    $scope.sanPham = [];
    $scope.currentPage = 0;
    $scope.pageSize = 16;
    $scope.sanPham4 = [];
    $scope.sanPhamSpecial = [];
    $scope.sanPhamPopular = [];
    console.log( "login: "+$rootScope.isLoggedIn);

    $http.get("http://localhost:8080/api/product/getAll")
        .then(function (response) {
                $scope.sanPham = response.data;
                for (var i = 0; i < 6; i++) {
                    $scope.sanPham4.push(response.data[i]);
                }
                for (var i = 7; i < 13; i++) {
                    $scope.sanPhamSpecial.push(response.data[i]);
                }
            },
            function (error) {
                console.log(error);
            });


    // search danh muc
    $scope.searchSmartWatch = function () {
        let t = angular.element(document.querySelector('#SmartWatch')).text();
        console.log(t);

        $http.get("http://localhost:8080/api/product/productDongSP" + "?name=" + t)
            .then(function (response) {
                    $scope.sanPham = response.data;
                    console.log($scope.sanPham);
                },
                function (error) {
                    console.log(error);
                });
    };

    $scope.searchSpeaker = function () {
        let t = angular.element(document.querySelector('#Speaker')).text();
        console.log(t);

        $http.get("http://localhost:8080/api/product/productDongSP" + "?name=" + t)
            .then(function (response) {
                    $scope.sanPham = response.data;
                    console.log($scope.sanPham);
                },
                function (error) {
                    console.log(error);
                });
    };

    $scope.searchLapTop = function () {
        let t = angular.element(document.querySelector('#Laptops')).text();
        console.log(t);

        $http.get("http://localhost:8080/api/product/productDongSP" + "?name=" + t)
            .then(function (response) {
                    $scope.sanPham = response.data;
                    console.log($scope.sanPham);
                },
                function (error) {
                    console.log(error);
                });
    };

    $scope.searchSmartPhone = function () {
        let t = angular.element(document.querySelector('#SamrtPhone')).text();
        console.log(t);

        $http.get("http://localhost:8080/api/product/productDongSP" + "?name=" + t)
            .then(function (response) {
                    $scope.sanPham = response.data;
                    console.log($scope.sanPham);
                },
                function (error) {
                    console.log(error);
                });
    };


}