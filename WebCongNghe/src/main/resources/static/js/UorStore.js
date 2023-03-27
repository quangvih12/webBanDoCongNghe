window.uorStoreController = function ($scope, $http) {
    $scope.sanPhams = [];
    $scope.sanPham = [];
    $scope.currentPage = 0;
    $scope.pageSize = 16;

    //getAll
    $http.get("http://localhost:8080/api/product/getAll")
        .then(function (responseData) {
                $scope.sanPham = responseData.data;
            },
            function (error) {
                console.log(error);
            });

    // sort gia ban
    $scope.sortBy = function () {
        if ($scope.sort == null) {
            $http.get("http://localhost:8080/api/product/getAll")
                .then(function (response) {
                        $scope.sanPham = response.data;
                    },
                    function (error) {
                        console.log(error);
                    });
        } else {
            $http.get("http://localhost:8080/api/product/getAll" + "?nameSort=" + $scope.sort)
                .then(function (response) {
                        $scope.sanPham = response.data;
                    },
                    function (error) {
                        console.log(error);
                    });
        }


    };

    // search price
    $scope.searchPrice = function () {
        if ($scope.from == null || $scope.to == null) {
            $scope.errorFrom = 'bạn cần nhập giá trị ';
        } else {
            $scope.errorFrom = '';
            $http.get("http://localhost:8080/api/product/productSearchPrice" + "?from=" + $scope.from + "&to=" + $scope.to)
                .then(function (response) {
                        $scope.sanPham = response.data;
                    },
                    function (error) {
                        console.log(error);
                    });

        }
    };

    // so trang
    $scope.numberOfPages = function () {
        return Math.ceil($scope.sanPham.length / $scope.pageSize);
    };

    // search san pham
    $scope.searchSanPham = function () {
        $http.get("http://localhost:8080/api/product/searchHeader?keyword=" + $scope.keyword).then(function (response) {
            $scope.sanPham = response.data;
        }, function (error) {
            console.log(error);
        });
    };

}