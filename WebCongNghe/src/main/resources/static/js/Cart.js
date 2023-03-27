window.gioHangController = function ($scope, $http) {
    var myDiv = document.getElementById("right_gh");
    var div = document.getElementById("table");
    $scope.gioHang = [];

    let tutal = 0;
    let lenght = 0;
    //getAll
    $http.get("http://localhost:8080/api/gioHang/getAll")
        .then(function (response) {
                $scope.gioHang = response.data;
                lenght = response.data.length;
                response.data.map(function (sp) {

                    let giaBan = sp.chiTietSP.giaBan;
                    let soLuong = sp.soLuong;

                    tutal = parseInt(giaBan) * parseInt(soLuong);

                    for (var i = 0; i <= response.data.length; i++) {
                        tutal += tutal;
                    }
                    document.getElementById('tongthanhtoan').textContent = tutal;

                })
            },
            function (error) {
                console.log(error);
            });

//thanh toan
    $scope.save = function () {
        $http.post("http://localhost:8080/api/gioHang/createhoaDon" + "?TongTienHoaDon=" + tutal, {
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

    // luu id san pham vao session
    $scope.saveCheckbox = function (idsp, sluong) {
        $http.post("http://localhost:8080/api/gioHang/save-checkbox" + "?idSanPham=" + idsp
        ).then(function (response) {

        });
    }

    $scope.delete = function (id) {
        $http.delete("http://localhost:8080/api/gioHang/delete/" + id,
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