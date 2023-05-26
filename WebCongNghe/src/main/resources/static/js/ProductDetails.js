window.detailController = function ($scope, $http, $routeParams) {
    var id = $routeParams.id;
    $scope.soLuong = 0;
    $scope.sps = [];

    $http.get("http://localhost:8080/api/product/" + id).then(function (response) {
        $scope.sps = response.data;
        console.log($scope.sps);
    });

    // them sp vao gio hang
    $scope.createGioHangCT = function (idSp) {
        // alert(parseInt(sq) + 1)
        if ($scope.soLuong <= 0) {
            $("#modal_sanP").modal('show');
        } else if ($scope.soLuong == null) {
            $("#modal_sanP").modal('show');
        } else {
            $http.post("http://localhost:8080/api/gioHang/add" + "?soLuong=" + $scope.soLuong + "&idSanPHamCT=" + idSp).then(function (response) {
                if (response.status === 200) {
                    // window.open("/view", '_self');
                    // $("#add-San-Pham").modal('hide');
                    if (response.data.statusCode == "error") {
                        Swal.fire({
                            icon: 'error',
                            title: response.data.data
                        })
                    }
                }

            });
        }

    };


};

