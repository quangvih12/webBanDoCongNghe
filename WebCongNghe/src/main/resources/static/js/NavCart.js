let View = "/view"
let API = "/api/gioHang/getAll"
let APII = "/api/gioHang"
let ViewOurStore = "#ourStore"
$(document).ready(function () { //thực hiện các mã js khi trang Mô hình đối tượng tài liệu (DOM) đã sẵn sàng
    $("#sinh_vien_error").text("");
    loadData(); //gọi hàm loadData() ra để thực thi


});

function loadData() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: API,
        success: function (responseData) {
            // dem so luong san pham trong gio hang
            document.getElementById('span-SlSanPham').textContent = responseData.length;

            $("#cart-content").html(responseData.map(function (item) {
                //tutal
                let giaBan = item.chiTietSP.giaBan;
                let soLuong = item.soLuong;

                let tutal = parseInt(giaBan) * parseInt(soLuong);


                for (var i = 0; i <= responseData.length; i++) {
                    tutal += tutal;
                }

                document.getElementById('total-price').textContent = tutal;
                document.getElementById('span-tutal').textContent = tutal;

                // lay ra ten trong sesion sau dó add do header
                const username = sessionStorage.getItem('ten');
                const usernameElement = document.getElementById('username');
                if (username === null) {
                    usernameElement.textContent = '  log in My Account';
                } else if (usernameElement !== null) {
                    usernameElement.textContent = username;
                    console.log(usernameElement.textContent)
                } else {
                    console.log('bug bug bug')
                }


                return `
                 <div class="cart-box" >
                   <img src="${item.chiTietSP.hinhAnh}" alt="" class="cart-img" />
             <div class="detail-box">
             <div class="cart-product-title">${item.chiTietSP.sanPham.ten}</div>
                <div class="cart-price"><span>$</span>${item.chiTietSP.giaBan}</div>
                 <input type="number" value="${item.soLuong}" class="cart-quantity"  />
               </div>
                  <i class="bi bi-trash3-fill cart-remove"  onclick="Remove(${item.id})"  ></i>
                 
              </div>
                `
            }))
            /*
            hàm map sẽ return về 1 đoạn template string chứa cả 1 dòng trong table
            trong mảng có bao nhiêu phần tử sẽ lặp bấy nhiêu lần và hiển thị
            */
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function Remove(sanPhamId) {
    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: APII + "/gioHang/" + sanPhamId,
        data: JSON.stringify(sanPhamId),
        dataType: 'json',
        success: function () {
            window.open(View, '_self');
            document.querySelector(".cart").classList.remove("active");

        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

