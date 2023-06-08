package com.example.demo.service.impl;

import com.example.demo.entity.Cart;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.GioHang;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Role;
import com.example.demo.entity.Token;
import com.example.demo.entity.TokenType;
import com.example.demo.reponstory.CartDetailReponsitory;
import com.example.demo.reponstory.CartsReponstory;
import com.example.demo.reponstory.KhachHangReponsitory;
import com.example.demo.reponstory.ProductReponstory;
import com.example.demo.reponstory.TokenRepository;
import com.example.demo.request.AuthenticationRequest;
import com.example.demo.request.AuthenticationResponse;
import com.example.demo.request.RegisterRequest;
import com.example.demo.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SessionAttributes("myAttribute")
public class AuthenticationServiceImpl implements AuthenticationService {
    private final KhachHangReponsitory repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private static Integer currentLoginId;

    @Autowired
    private CartsReponstory cartsReponstory;

    @Autowired
    private ProductReponstory productReponstory;

    @Autowired
    private CartDetailReponsitory gioHangCTRespon;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var khacHang = KhachHang.builder()
                .ten(request.getFirstname())
                .tenDem(request.getLastname())
                .email(request.getEmail())
                .matKhau(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var saveKhachHang = repository.save(khacHang);
        var jwtToken = jwtService.generateToken(khacHang);
        saveUserToken(saveKhachHang, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpSession session, Model model) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            // Xử lý ngoại lệ đăng nhập không thành công
            return AuthenticationResponse.builder().error("Thông tin đăng nhập không chính xác").build();
        }

        var khachHang = repository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(khachHang);

        // lay ra id khach hang
        currentLoginId = khachHang.getId();
        session.setAttribute("userDetails", khachHang.getTen());

        //khi dang nhap se luu san pham trong session vao database theo id nguoi dung
        this.saveProductInCartSession(session);

        revokeAllUserTokens(khachHang);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void saveProductInCartSession(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            KhachHang kh = repository.findById(currentLoginId).get();
            GioHang _gioHang = GioHang.builder().khachHang(kh).build();
            GioHang gh = cartsReponstory.save(_gioHang);
            gh.setMa("HD" + gh.getId());
            List<GioHangChiTiet> liss = cart.getItems();
            liss.forEach(o -> {
                o.setGioHang(gh);
//                System.out.println(o.getGioHang().getId());
            });
            gioHangCTRespon.saveAll(liss);
            session.removeAttribute("cart");
//            ChiTietSanPham sanPham = productReponstory.getById();
//            Optional<GioHangChiTiet> optional = this.getById(kh.getId(), sanPham.getId());
//            if (optional.isPresent()) {
//                GioHangChiTiet gioHangChiTiet = optional.get();
//                    gioHangChiTiet.setSoLuong(soLuong + gioHangChiTiet.getSoLuong());
//                    gioHangCTRespon.save(gioHangChiTiet);
//
//            } else {
//
//            }
        }
    }

    private void saveUserToken(KhachHang khacHang, String jwtToken) {
        var token = Token.builder()
                .khachHang(khacHang)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(KhachHang khacHang) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(khacHang.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public static Integer getCurrentLoginId() {
        return currentLoginId;
    }

}
