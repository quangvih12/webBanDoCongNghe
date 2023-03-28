window.gioHangController = function ($scope, $http) {
    var myDiv = document.getElementById("right_gh");
    var div = document.getElementById("table");
    $scope.gioHang = [];

    let tutals = 0;
    let lenght = 0;
    //getAll
    $http.get("http://localhost:8080/api/gioHang/getAll")
        .then(function (response) {
                $scope.gioHang = response.data;
                lenght = response.data.length;
                response.data.map(function (sp) {

                    tutals = 0;
                    for (var i = 0; i < response.data.length; i++) {
                        let tutal = response.data[i].soLuong * response.data[i].donGia;
                        tutals += tutal;
                    }
                    console.log(tutals)
                    document.getElementById('tongthanhtoan').textContent = tutals;

                })
            },
            function (error) {
                console.log(error);
            });

//thanh toan
    $scope.save = function () {
        $http.post("http://localhost:8080/api/hoaDon/createhoaDon" + "?TongTienHoaDon=" + tutal, {
                tenNguoiNhan: $scope.tenNguoiNhan,
                diaChi: $scope.diaChi,
                sdt: $scope.sdt,
                phuongThucTT: {id: $scope.phuongThucTT},
                ghiChu: $scope.ghiChu
            }
        ).then(function (response) {
            if (response.status === 201) {
                $("#modal_thanhToan").modal('show');
            }

        });
    }

    $scope.isDisabled = true;
    $scope.getDetails = function (idsp) {
        angular.forEach($scope.gioHang, function (item) {
            // console.log(item.isChecked)
            if (item.isChecked) {
                // luu id san pham vao session
                $http.post("http://localhost:8080/api/hoaDon/save-checkbox" + "?idSanPham=" + idsp
                ).then(function (response) {
                });
                $scope.isDisabled = false;
                // console.log('ok');
            } else if (item.isChecked === false) {
                console.log('bug')
                // remove id san pham vao session
                $http.post("http://localhost:8080/api/hoaDon/remove-checkbox" + "?idSanPham=" + idsp
                ).then(function (response) {
                });
                $scope.isDisabled = true;
            } else {
                console.log('bug bug')
            }
        });
    };

    $scope.delete = function (id) {
        $http.delete("http://localhost:8080/api/gioHang/" + id,
        ).then(function (response) {
            window.open('/view#gioHang', '_self');
        });
    }

//cong so luong
    $scope.congSoLuong = function (id, soLuong) {
        $http.put("http://localhost:8080/api/gioHang/gioHangs/" + id, {
                soLuong: soLuong
            }
        ).then(function (response) {
            if (response.status === 200) {
                window.open('/view#gioHang', '_self');
            }
        });
    }
//tru so luong
    $scope.truSoLuong = function (id, soLuong) {
        $http.put("http://localhost:8080/api/gioHang/gioHang/" + id, {
                soLuong: soLuong
            }
        ).then(function (response) {
            if (response.status === 200) {
                window.open('/view#gioHang', '_self');
            }
        });
    }

// chuyển đến phần tử
    $scope.chuyenPhanTu = function scrollToElement(id) {
        myDiv.style.display = "block";
        div.style.display = "none";
        var element = document.getElementById(id);
        element.scrollIntoView({behavior: "smooth"});

    }

    $scope.vePhanTu = function scrollToElement(id) {
        myDiv.style.display = "none";
        div.style.display = "block";
        var element = document.getElementById(id);
        element.scrollIntoView({behavior: "smooth"});

    }
}