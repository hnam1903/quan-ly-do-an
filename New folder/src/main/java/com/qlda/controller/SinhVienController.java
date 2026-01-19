package com.qlda.controller;

import com.qlda.model.*;
import com.qlda.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {
    @Autowired
    private UserService userService;

    @Autowired
    private DeTaiService deTaiService;

    @Autowired
    private DangKyService dangKyService;

    @Autowired
    private PhanCongService phanCongService;

    @Autowired
    private TienDoService tienDoService;

    @Autowired
    private DiemService diemService;

    @GetMapping
    public String sinhVienDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User sinhVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<DangKy> dangKys = dangKyService.getDangKyBySinhVien(sinhVien);
        List<PhanCong> phanCongs = phanCongService.getPhanCongBySinhVien(sinhVien);

        model.addAttribute("sinhVien", sinhVien);
        model.addAttribute("totalDangKy", dangKys.size());
        model.addAttribute("totalPhanCong", phanCongs.size());
        return "sinhvien/dashboard";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User sinhVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", sinhVien);
        return "sinhvien/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User sinhVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        sinhVien.setHoTen(user.getHoTen());
        sinhVien.setEmail(user.getEmail());
        sinhVien.setSoDienThoai(user.getSoDienThoai());
        sinhVien.setLop(user.getLop());
        sinhVien.setChuyenNganh(user.getChuyenNganh());
        sinhVien.setMaSinhVien(user.getMaSinhVien());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            sinhVien.setPassword(user.getPassword());
        }

        userService.updateUser(sinhVien);
        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/sinhvien/profile";
    }

    @GetMapping("/detai")
    public String listDeTai(Model model) {
        List<DeTai> deTais = deTaiService.getDeTaiByTrangThai(TrangThaiDeTai.DANG_THUC_HIEN);
        model.addAttribute("deTais", deTais);
        return "sinhvien/detai/list";
    }

    @GetMapping("/detai/{id}")
    public String viewDeTaiDetail(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User sinhVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DeTai deTai = deTaiService.getDeTaiById(id);
        boolean daDangKy = dangKyService.existsBySinhVienAndDeTai(sinhVien, deTai);
        DangKy dangKy = null;
        if (daDangKy) {
            List<DangKy> allDangKy = dangKyService.getDangKyBySinhVien(sinhVien);
            for (DangKy dk : allDangKy) {
                if (dk.getDeTai().getId().equals(id)) {
                    dangKy = dk;
                    break;
                }
            }
        }

        model.addAttribute("deTai", deTai);
        model.addAttribute("daDangKy", daDangKy);
        model.addAttribute("dangKy", dangKy);
        return "sinhvien/detai/detail";
    }

    @PostMapping("/detai/{id}/dangky")
    public String dangKyDeTai(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User sinhVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DeTai deTai = deTaiService.getDeTaiById(id);

        if (dangKyService.existsBySinhVienAndDeTai(sinhVien, deTai)) {
            redirectAttributes.addFlashAttribute("error", "Bạn đã đăng ký đề tài này rồi!");
            return "redirect:/sinhvien/detai/" + id;
        }

        DangKy dangKy = new DangKy();
        dangKy.setSinhVien(sinhVien);
        dangKy.setDeTai(deTai);
        dangKy.setTrangThai(TrangThaiDangKy.DANG_CHO);
        dangKyService.saveDangKy(dangKy);

        redirectAttributes.addFlashAttribute("success", "Đăng ký đề tài thành công!");
        return "redirect:/sinhvien/detai/" + id;
    }

    @GetMapping("/dangky")
    public String listDangKy(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User sinhVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<DangKy> dangKys = dangKyService.getDangKyBySinhVien(sinhVien);
        model.addAttribute("dangKys", dangKys);
        return "sinhvien/dangky/list";
    }

    @GetMapping("/tiendo")
    public String listTienDo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User sinhVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<PhanCong> phanCongs = phanCongService.getPhanCongBySinhVien(sinhVien);
        model.addAttribute("phanCongs", phanCongs);
        return "sinhvien/tiendo/list";
    }

    @GetMapping("/tiendo/phancong/{phancongId}")
    public String viewTienDoDetail(@PathVariable Long phancongId, Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User sinhVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        PhanCong phanCong = phanCongService.getPhanCongById(phancongId);

        if (phanCong.getSinhVien() == null || !phanCong.getSinhVien().getId().equals(sinhVien.getId())) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xem tiến độ của đề tài này!");
            return "redirect:/sinhvien/tiendo";
        }

        List<TienDo> tienDos = tienDoService.getTienDoByPhanCong(phanCong);

        Map<Integer, TienDo> tienDoMap = new HashMap<>();
        for (TienDo tienDo : tienDos) {
            tienDoMap.put(tienDo.getTuan(), tienDo);
        }
        
        model.addAttribute("phanCong", phanCong);
        model.addAttribute("tienDos", tienDos);
        model.addAttribute("tienDoMap", tienDoMap);
        return "sinhvien/tiendo/detail";
    }

    @GetMapping("/diem")
    public String viewDiem(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User sinhVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<PhanCong> phanCongs = phanCongService.getPhanCongBySinhVien(sinhVien);
        model.addAttribute("phanCongs", phanCongs);
        model.addAttribute("diemService", diemService);
        return "sinhvien/diem/list";
    }
}

