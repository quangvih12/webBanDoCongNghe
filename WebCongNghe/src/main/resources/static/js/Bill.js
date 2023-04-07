window.billController = function ($scope, $http) {
    $scope.HoaDon =[];
    $scope.HoaDons =[];
    //getAll
    $http.get("http://localhost:8080/api/hoaDonD")
        .then(function (responseData) {
                $scope.HoaDon = responseData.data;
            },
            function (error) {
                console.log(error);
            });

    // get by product theo id
    $scope.getProductById =function (id) {
        $http.get("http://localhost:8080/api/hoaDonD/"+ id)
            .then(function (responseData) {
                    $scope.HoaDons = responseData.data;
                    console.log( $scope.HoaDons)
                },
                function (error) {
                    console.log(error);
                });
    }

    // huy hoa don
    $scope.huyHoaDon =function (id) {
        $http.put("http://localhost:8080/api/hoaDonD/"+ id,{
            trangThaiTT: -1
        })
            .then(function (responseData) {
                    $scope.HoaDons = responseData.data;
                    console.log('ok');
                    window.open("#bill", '_self');
                },
                function (error) {
                    console.log(error);
                });
    }

}