package com.example.demo.service.impl.AdminServiceImpl;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.DongSp;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.NamSanXuat;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.thuongHieu;
import com.example.demo.reponstory.DongSanPhamReponsitory;
import com.example.demo.reponstory.MauSacReponsitory;
import com.example.demo.reponstory.NamSanXuatReponsitory;
import com.example.demo.reponstory.SanPhamReponsitory;
import com.example.demo.reponstory.ThuongHieuReponsitory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


@Service
public class ExcelUploadServiceImpl {

    @Autowired
    private SanPhamReponsitory sanPhamReponsitory;
    @Autowired
    private DongSanPhamReponsitory dongSanPhamReponsitory;
    @Autowired
    private NamSanXuatReponsitory namSanXuatReponsitory;
    @Autowired
    private MauSacReponsitory mauSacReponsitory;
    @Autowired
    private ThuongHieuReponsitory thuongHieuReponsitory;

    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<ChiTietSanPham> getCustomersDataFromExcel(InputStream inputStream) {
        List<ChiTietSanPham> listChiTietSp = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
//
            if (workbook != null) {
//                System.out.println("Workbook co ton tai");
                if (sheet != null) {
//                    System.out.println("sheet ton tai");
                    int rowIndex = 0;
                    for (Row row : sheet) {
                        if (rowIndex == 0) {
                            rowIndex++;
                            continue;
                        }
                        Iterator<Cell> cellIterator = row.iterator();
                        int cellIndex = 0;
                        ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {

                                case 0 -> {
                                    int idSanPham = (int) cell.getNumericCellValue();
                                    SanPham sanPham = sanPhamReponsitory.findById(idSanPham).orElse(null);
                                    chiTietSanPham.setSanPham(sanPham);
                                }


                                case 1 -> {
                                    int id = (int) cell.getNumericCellValue();
                                    DongSp dongSp = dongSanPhamReponsitory.findById(id).orElse(null);
                                    chiTietSanPham.setDongsp(dongSp);
                                }
                                case 2 -> {
                                    int id = (int) cell.getNumericCellValue();
                                    MauSac mauSac = mauSacReponsitory.findById(id).orElse(null);
                                    chiTietSanPham.setMauSac(mauSac);
                                }
                                case 3 -> {
                                    int id = (int) cell.getNumericCellValue();
                                    thuongHieu thuongHieu = thuongHieuReponsitory.findById(id).orElse(null);
                                    chiTietSanPham.setThuongHieu(thuongHieu);
                                }
                                case 4 -> {
                                    int id = (int) cell.getNumericCellValue();
                                    NamSanXuat namSanXuat = namSanXuatReponsitory.findById(id).orElse(null);
                                    chiTietSanPham.setNSX(namSanXuat);
                                }
                                case 5 -> chiTietSanPham.setGiaBan(new BigDecimal(cell.getNumericCellValue()));
                                case 6 -> chiTietSanPham.setGiaNhap(new BigDecimal(cell.getNumericCellValue()));
                                case 7 -> chiTietSanPham.setHinhAnh(cell.getStringCellValue());
                                case 8 -> chiTietSanPham.setSoLuongTon((int) cell.getNumericCellValue());
                                case 9 -> chiTietSanPham.setTrangThai((int) cell.getNumericCellValue());
                                case 10 -> chiTietSanPham.setMoTa(cell.getStringCellValue());
                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        listChiTietSp.add(chiTietSanPham);

                    }
                } else {
                    System.out.println("sheet ko ton tai.");
                }
            } else {
                System.out.println("Workbook is null. Không thể đọc dữ liệu từ Excel.");
            }
//            listChiTietSp.forEach(o -> System.out.println(
//                    "sp: " + o.getSanPham().getId()
//                            + "dsp: " + o.getDongsp().getId()
//                            + "ms: " + o.getMauSac().getId()
//                            + "th: " + o.getThuongHieu().getId()
//                            + "nsx: " + o.getNSX().getId()
//                            + "gB: " + o.getGiaBan()
//                            + "gN: " + o.getGiaNhap()
//                            + "ha: " + o.getHinhAnh()
//                            + " sl: " + o.getSoLuongTon()
//                            + "tt: " + o.getTrangThai()
//                            + "mt: " + o.getMoTa()
//
//            ));
        } catch (IOException e) {
            e.getStackTrace();
        }
        return listChiTietSp;
    }
}
