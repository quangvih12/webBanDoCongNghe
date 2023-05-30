
// san pham ban cham va chay nhat
select  pt.id_chi_tietsp,COUNT(*) AS tutor_sold, SUM(PT.so_luong)AS soluong from hoa_don_chi_tiet pt join hoa_don hd on pt.id_hoa_don = hd.id
where hd.trang_thai_tt = -1
GROUP BY pt.id_chi_tietsp
ORDER BY tutor_sold DESC



