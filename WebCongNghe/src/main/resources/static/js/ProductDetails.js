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
        $http.post("http://localhost:8080/api/gioHang/add" + "?soLuong=" + $scope.soLuong + "&idSanPHamCT=" + idSp).then(function (response) {
            if (response.status === 200) {
                if (response.data.statusCode == "error") {
                    swal.fire({
                        icon: 'error',
                        title: response.data.data
                    })
                        .then((value) => {
                            window.open("/view#/detail/"+idSp, '_self');
                        });
                } else if (response.data.statusCode == "success") {
                    swal.fire({
                        icon: 'success',
                        title: response.data.data
                    })
                        .then((value) => {
                            window.open("/view#/detail/"+idSp, '_self');
                        });
                }
            }

        });
    };


};

