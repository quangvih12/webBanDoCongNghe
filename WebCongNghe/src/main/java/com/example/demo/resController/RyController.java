package com.example.demo.resController;


import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class RyController {

//    @Autowired
//    private SanPhamResponstory sanPhamResponstory;
//
//
//    @GetMapping("/home")
//    public ResponseEntity<List<ChiTietSanPham>> getAll(@RequestParam(required = false) String keyword) {
//        try {
//            //tao 1 doi tuong ChiTietSanPham
//            List<ChiTietSanPham> sanPhamList = new ArrayList<>();
//            // neu name == null thi get All con neu name != null thi goi ham tim kiem
//            if (keyword == null) {
//                sanPhamResponstory.findAll().forEach(sanPhamList::add);
//            } else {
//                sanPhamResponstory.findAllBySanphamTen("%" + keyword + "%").forEach(sanPhamList::add);
//            }
//            // kiểm tran SanPhamList có phần tử hay không
//            // nếu SanPhamList rỗng thì trả về mã trạng thái 'NO_CONTENT' đã sử lý thành công nhưng ko có sanPham trả về
//            // nếu SanPhamList không rỗng thì trả về mã trạng thái 'OK' đã sử lý thành công  vả có sanPham trả về
//            if (sanPhamList.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            } else {
//                return new ResponseEntity<>(sanPhamList, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // detail
//    @GetMapping("/home/{id}")
//    public ResponseEntity<ChiTietSanPham> getSanPhamById(@PathVariable("id") Integer id) {
//        Optional<ChiTietSanPham> sanPhamData = sanPhamResponstory.findById(id);
//
//        //    kiểm tra xem giá trị trong Optional có tồn tại hay không.
//        if (sanPhamData.isPresent()) {
//            return new ResponseEntity<>(sanPhamData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(sanPhamData.get(), HttpStatus.NOT_FOUND);
//        }
//    }

//    @GetMapping("/hello")
//    public   List<ChiTietSanPham> sayHello(@RequestParam("keyword") String keyword) {
//
//            return sanPhamResponstory.findAllBySanphamTen(keyword);
//    }
}
