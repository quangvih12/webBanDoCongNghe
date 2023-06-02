window.billAdminController = function ($scope, $http, Upload) {
    $scope.Bill = [];
    $scope.product = [];
    $scope.totalPagess = [1, 2, 3];
    $scope.totalPages = [];
    $scope.page = 0;
    $scope.size = 10;

    $http.get("http://localhost:8080/api/BillAdmin/" + 0)
        .then(function (responseData) {
                $scope.Bill = responseData.data;
                // console.log(responseData.data);
            },
            function (error) {
                console.log(error);
            });

    // quan tri san pham
    $http.get("http://localhost:8080/api/productAdmin?page=" + $scope.page + "&size=" + $scope.size)
        .then(function (responseData) {
                $scope.product = responseData.data.content;
                // $scope.totalPages = responseData.data.totalPages;
                for (var i = 1; i <= responseData.data.totalPages - 1; i++) {
                    $scope.totalPages.push(i);
                }
            },
            function (error) {
                console.log(error);
            });
    $scope.goToPage = function (page) {
        $http.get("http://localhost:8080/api/productAdmin?page=" + page + "&size=" + $scope.size)
            .then(function (responseData) {
                    $scope.product = responseData.data.content;
                },
                function (error) {
                    console.log(error);
                });
    }
    $scope.XacNhanHoaDon = function (id) {
        $http.put("http://localhost:8080/api/BillAdmin/xacNhanHoaDon/" + id, {
            trangThaiTT: 1
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
                                    swal.fire("đơn hàng đã được xác nhận !!!!", {
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

    $scope.UploadExcel = function () {
        if ($scope.fileList) {
            Upload.upload({
                url: 'http://localhost:8080/api/productAdmin/upload-ChiTietSp-data',
                data: {file: $scope.fileList[0]}
            }).then(function (response) {
                if (response.data.statusCode == "error") {
                    swal.fire({
                        icon: 'success',
                        title: response.data.data
                    })
                        .then((value) => {
                            window.open("#billAdmin", '_self');
                        });
                }
            }, function (error) {
                // Xử lý lỗi
                console.log(error);
            });
        }
    };


}