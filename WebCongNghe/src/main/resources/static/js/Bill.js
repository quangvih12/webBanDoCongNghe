window.billController = function ($scope, $http) {
    $scope.HoaDon = [];
    $scope.HoaDons = [];
    $scope.HoaDonChoXacNhan = [];
    $scope.HoaDondangGiao = [];
    $scope.HoaDonDaGiao = [];
    $scope.HoaDonDaHuy = [];
    $scope.HoaDonDaDoiTra = [];
    $scope.trangThaiTT = 0;
    //getAll
    $http.get("http://localhost:8080/api/hoaDonD/home/" + $scope.trangThaiTT)
        .then(function (responseData) {
                $scope.HoaDon = responseData.data;
                console.log(responseData.data);
            },
            function (error) {
                console.log(error);
            });


    $http.get("http://localhost:8080/api/hoaDonD/home/" + 1)
        .then(function (responseData) {
                $scope.HoaDondangGiao = responseData.data;
            },
            function (error) {
                console.log(error);
            });


    $http.get("http://localhost:8080/api/hoaDonD/home/" + 2)
        .then(function (responseData) {
                $scope.HoaDonDaGiao = responseData.data;
            },
            function (error) {
                console.log(error);
            });


    $http.get("http://localhost:8080/api/hoaDonD/home/" + -1)
        .then(function (responseData) {
                $scope.HoaDonDaHuy = responseData.data;
                console.log(responseData.data);
            },
            function (error) {
                console.log(error);
            });


    $http.get("http://localhost:8080/api/hoaDonD/home/" + 3)
        .then(function (responseData) {
                $scope.HoaDonDaDoiTra = responseData.data;
            },
            function (error) {
                console.log(error);
            });


    // get by product theo id
    $scope.getProductById = function (id) {
        $http.get("http://localhost:8080/api/hoaDonD/" + id)
            .then(function (responseData) {
                    $scope.HoaDons = responseData.data;
                    // console.log($scope.HoaDons)
                },
                function (error) {
                    console.log(error);
                });
    }

    // huy hoa don
    $scope.huyHoaDon = function (id) {
        $http.put("http://localhost:8080/api/hoaDonD/" + id, {
            trangThaiTT: -1
        })
            .then(function (responseData) {
                    // $scope.HoaDons = responseData.data;

                        if (responseData.data.statusCode == "warning") {
                            swal.fire({
                                title: responseData.data.data,
                                icon: "warning",
                                buttons: true,
                                dangerMode: true,
                            })
                                .then((willDelete) => {
                                    if (willDelete) {
                                        swal.fire("đơn hàng đã được hủy!!!!", {
                                            icon: "success",
                                        });
                                        window.open("#bill", '_self');
                                    } else {
                                        swal.fire("Your imaginary file is safe!");
                                    }
                                });

                        }


                },
                function (error) {
                    console.log(error);
                });
    }

}