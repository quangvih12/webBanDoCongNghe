package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class HomeController {
    @GetMapping()
    public String index() {
        return "Index";
    }

    @GetMapping("/home")
    public String home() {
        return "Home";
    }

    @GetMapping("/ourStore")
    public String uorStore() {
        return "OurStore";
    }

    @GetMapping("/SearchOurStore")
    public String searchOurStore() {
        return "SearchOurStore";
    }

    @GetMapping("/header")
    public String header() {
        return "layout/Header";
    }

    @GetMapping("/footer")
    public String footer() {
        return "layout/Footer";
    }

    @GetMapping("/detail")
    public String detail() {
        return "ProductDetails";
    }

    @GetMapping("/gioHang")
    public String gioHang() {
        return "Cart";
    }

    @GetMapping("/bill")
    public String bill() {
        return "bill";
    }


}
