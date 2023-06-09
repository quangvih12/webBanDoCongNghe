let View = "/view"
let API = "/api/gioHang/getAll"
let APII = "/api/gioHang"
let ViewOurStore = "#ourStore"
$(document).ready(function () { //thực hiện các mã js khi trang Mô hình đối tượng tài liệu (DOM) đã sẵn sàng
    loadData(); //gọi hàm loadData() ra để thực thi
    Upten();
});

function Upten() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/v1/auth/session",
        success: function (responseData) {

            console.log(responseData)

            const usernameElement = document.getElementById('username');
            if (responseData === null) {
                usernameElement.textContent = '  log in My Account';
            } else if (usernameElement !== null) {
                usernameElement.textContent = responseData;
                // console.log(usernameElement.textContent)
            } else {
                console.log('bug bug bug')
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}


function loadData() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: API,
        success: function (responseData) {
            // dem so luong san pham trong gio hang
            document.getElementById('span-SlSanPham').textContent = responseData.length;

            $("#cart-content").html(responseData.map(function (item) {
                let tutals = 0;
                for (var i = 0; i < responseData.length; i++) {
                    let tutal = responseData[i].soLuong * responseData[i].donGia;
                    tutals += tutal;
                }


                document.getElementById('total-price').textContent = tutals;
                document.getElementById('span-tutal').textContent = tutals;
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
        url: APII + "/" + sanPhamId,
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

