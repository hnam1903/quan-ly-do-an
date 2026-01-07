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

import java.util.List;

@Controller
@RequestMapping("/giangvien")
public class GiangVienController {
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
    public String giangVienDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User giangVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<DeTai> deTais = deTaiService.getDeTaiByGiangVien(giangVien);
        List<PhanCong> phanCongs = phanCongService.getPhanCongByGiangVienHuongDan(giangVien);

        model.addAttribute("giangVien", giangVien);
        model.addAttribute("totalDeTai", deTais.size());
        model.addAttribute("totalPhanCong", phanCongs.size());
        return "giangvien/dashboard";
    }

    // Quản lý đề tài
    @GetMapping("/detai")
    public String listDeTai(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User giangVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<DeTai> deTais = deTaiService.getDeTaiByGiangVien(giangVien);
        model.addAttribute("deTais", deTais);
        return "giangvien/detai/list";
    }

    @GetMapping("/detai/new")
    public String showCreateDeTaiForm(Model model) {
        model.addAttribute("deTai", new DeTai());
        return "giangvien/detai/form";
    }

    @PostMapping("/detai")
    public String createDeTai(@ModelAttribute DeTai deTai, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User giangVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        deTai.setGiangVien(giangVien);
        deTai.setTrangThai(TrangThaiDeTai.CHO_DUYET);
        deTaiService.saveDeTai(deTai);
        redirectAttributes.addFlashAttribute("success", "Tạo đề tài thành công!");
        return "redirect:/giangvien/detai";
    }

    @GetMapping("/detai/{id}/edit")
    public String showEditDeTaiForm(@PathVariable Long id, Model model) {
        DeTai deTai = deTaiService.getDeTaiById(id);
        model.addAttribute("deTai", deTai);
        return "giangvien/detai/form";
    }

    @PostMapping("/detai/{id}")
    public String updateDeTai(@PathVariable Long id, @ModelAttribute DeTai deTai, RedirectAttributes redirectAttributes) {
        deTai.setId(id);
        deTaiService.updateDeTai(deTai);
        redirectAttributes.addFlashAttribute("success", "Cập nhật đề tài thành công!");
        return "redirect:/giangvien/detai";
    }

    @PostMapping("/detai/{id}/delete")
    public String deleteDeTai(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        deTaiService.deleteDeTai(id);
        redirectAttributes.addFlashAttribute("success", "Xóa đề tài thành công!");
        return "redirect:/giangvien/detai";
    }

    // Duyệt đăng ký
    @GetMapping("/dangky")
    public String listDangKy(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User giangVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<DeTai> deTais = deTaiService.getDeTaiByGiangVien(giangVien);
        model.addAttribute("deTais", deTais);
        return "giangvien/dangky/list";
    }

    @GetMapping("/dangky/detai/{deTaiId}")
    public String listDangKyByDeTai(@PathVariable Long deTaiId, Model model) {
        DeTai deTai = deTaiService.getDeTaiById(deTaiId);
        List<DangKy> dangKys = dangKyService.getDangKyByDeTai(deTai);
        model.addAttribute("deTai", deTai);
        model.addAttribute("dangKys", dangKys);
        return "giangvien/dangky/detail";
    }

    @PostMapping("/dangky/{id}/duyet")
    public String duyetDangKy(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        dangKyService.duyetDangKy(id);
        redirectAttributes.addFlashAttribute("success", "Duyệt đăng ký thành công!");
        return "redirect:/giangvien/dangky";
    }

    @PostMapping("/dangky/{id}/tuchoi")
    public String tuChoiDangKy(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        dangKyService.tuChoiDangKy(id);
        redirectAttributes.addFlashAttribute("success", "Từ chối đăng ký thành công!");
        return "redirect:/giangvien/dangky";
    }

    // Quản lý tiến độ
    @GetMapping("/tiendo")
    public String listTienDo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User giangVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<PhanCong> phanCongs = phanCongService.getPhanCongByGiangVienHuongDan(giangVien);
        model.addAttribute("phanCongs", phanCongs);
        return "giangvien/tiendo/list";
    }

    @GetMapping("/tiendo/phancong/{phancongId}")
    public String listTienDoByPhanCong(@PathVariable Long phancongId, Model model) {
        PhanCong phanCong = phanCongService.getPhanCongById(phancongId);
        List<TienDo> tienDos = tienDoService.getTienDoByPhanCong(phanCong);
        model.addAttribute("phanCong", phanCong);
        model.addAttribute("tienDos", tienDos);
        return "giangvien/tiendo/detail";
    }

    @GetMapping("/tiendo/phancong/{phancongId}/new")
    public String showCreateTienDoForm(@PathVariable Long phancongId, Model model) {
        PhanCong phanCong = phanCongService.getPhanCongById(phancongId);
        TienDo tienDo = new TienDo();
        tienDo.setPhanCong(phanCong);
        model.addAttribute("tienDo", tienDo);
        return "giangvien/tiendo/form";
    }

    @PostMapping("/tiendo")
    public String createTienDo(@ModelAttribute TienDo tienDo, RedirectAttributes redirectAttributes) {
        tienDoService.saveTienDo(tienDo);
        redirectAttributes.addFlashAttribute("success", "Cập nhật tiến độ thành công!");
        return "redirect:/giangvien/tiendo/phancong/" + tienDo.getPhanCong().getId();
    }

    @GetMapping("/diem")
    public String listDiem(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User giangVien = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<PhanCong> phanCongs = phanCongService.getPhanCongByGiangVienHuongDan(giangVien);
        model.addAttribute("phanCongs", phanCongs);
        return "giangvien/diem/list";
    }

    @GetMapping("/diem/phancong/{phancongId}")
    public String showDiemForm(@PathVariable Long phancongId, Model model) {
        PhanCong phanCong = phanCongService.getPhanCongById(phancongId);
        Diem diem = diemService.getDiemByPhanCong(phanCong)
                .orElse(new Diem());
        diem.setPhanCong(phanCong);
        model.addAttribute("diem", diem);
        return "giangvien/diem/form";
    }

    @PostMapping("/diem")
    public String saveDiem(@ModelAttribute Diem diem, RedirectAttributes redirectAttributes) {
        if (diem.getId() == null) {
            diemService.saveDiem(diem);
        } else {
            diemService.updateDiem(diem);
        }
        redirectAttributes.addFlashAttribute("success", "Lưu điểm thành công!");
        return "redirect:/giangvien/diem";
    }
}



