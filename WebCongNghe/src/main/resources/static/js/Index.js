var app = angular.module("myApp", ["ngRoute"]);
app.config(function ($routeProvider, $locationProvider) {
    $locationProvider.hashPrefix("");
    $routeProvider.when("/home", {templateUrl: "/view/home", controller: HomeController})
        .when("/ourStore", {templateUrl: "/view/ourStore", controller: uorStoreController})
        .when("/detail/:id", {templateUrl: "/view/detail", controller: detailController})
        .when("/ourStores", {templateUrl: "/view/SearchOurStore"})
        .when("/gioHang", {templateUrl: "/view/gioHang", controller: gioHangController})
        .when("/bill", {templateUrl: "/view/bill", controller: billController})
        .when("/billAdmin", {templateUrl: "/Admin/billAdmin", controller: billAdminController})
        .otherwise({redirectTo: "home"});

});

app.controller('HomeController', function ($scope, $http) {
}).filter('startFrom', function () { // phan trang
    return function (input, start) {
        start = +start; //parse to int
        return input.slice(start);
    }
});

app.controller('uorStoreController', function ($scope, $http) {


}).filter('startFrom', function () {
    return function (input, start) {
        start = +start; //parse to int
        return input.slice(start);
    }
});

app.controller('searchOurStoreController', function ($scope, $http) {
    $scope.searchHeader = function () {
        window.open("#ourStores", '_self');
        if ($scope.keyword == null) {
            $http.get("http://localhost:8080/api/product")
                .then(function (responseData) {
                        document.getElementById("col").innerHTML = responseData.data.map(function (sp) {
                            return `
                    <div class="col-3"  >
                        <a href="#detail/${sp.id}">
                                 <div class="contents" id="contents">
                                <img src="${sp.hinhAnh}" alt=""/>
                                     <h3>${sp.sanPham.ten}</h3>
                                   <p>${sp.moTa}</p>
                                     <h6>${sp.giaBan}</h6>
                <ul>
                    <li><i class="bi bi-star-fill"></i></li>
                    <li><i class="bi bi-star-fill"></i></li>
                    <li><i class="bi bi-star-fill"></i></li>
                    <li><i class="bi bi-star"></i></li>
                    <li><i class="bi bi-star"></i></li>
                </ul>
                                    </div>
                                </a>
                    </div>
            `;
                        }).join('');
                    },
                    function (error) {
                        console.log(error);
                    });
        } else {
            window.open("#ourStores", '_self');
            $http.get("http://localhost:8080/api/searchHeader?keyword=" + $scope.keyword).then(function (responseData) {
                document.getElementById("col").innerHTML = responseData.data.map(function (sp) {
                    return `
                    <div class="col-3"  >
                        <a href="#detail/{{sp.id}}">
                                 <div class="contents" id="contents">
                                <img src="${sp.hinhAnh}" alt=""/>
                                     <h3>${sp.sanPham.ten}</h3>
                                   <p>${sp.moTa}</p>
                                     <h6>${sp.giaBan}</h6>
                <ul>
                    <li><i class="bi bi-star-fill"></i></li>
                    <li><i class="bi bi-star-fill"></i></li>
                    <li><i class="bi bi-star-fill"></i></li>
                    <li><i class="bi bi-star"></i></li>
                    <li><i class="bi bi-star"></i></li>
                </ul>
                                    </div>
                                </a>
                    </div>
            `;
                }).join('');
            }, function (error) {
                console.log(error);
            });
        }
    };
    // so trang
    $scope.numberOfPages = function () {
        return Math.ceil($scope.sanPham.length / $scope.pageSize);
    };
}).filter('startFrom', function () {
    return function (input, start) {
        start = +start; //parse to int
        return input.slice(start);
    }
});


