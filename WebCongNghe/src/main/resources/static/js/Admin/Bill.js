window.billAdminController = function ($scope, $http) {
    $scope.Bill = [];
    $http.get("http://localhost:8080/api/BillAdmin/" + 0)
        .then(function (responseData) {
                $scope.Bill = responseData.data;
                console.log(responseData.data);
            },
            function (error) {
                console.log(error);
            });


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

}