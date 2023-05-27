
// san pham ban cham va chay nhat
select  pt.id_chi_tietsp,COUNT(*) AS tutor_sold, SUM(PT.so_luong)AS soluong from hoa_don_chi_tiet pt
WHERE pt.ngay_tao = '2023-04-06 23:57:52.0000000'
GROUP BY pt.id_chi_tietsp
ORDER BY tutor_sold DESC



