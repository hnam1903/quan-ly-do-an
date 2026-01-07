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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private DeTaiService deTaiService;

    @Autowired
    private PhanCongService phanCongService;

    @Autowired
    private DiemService diemService;

    @GetMapping
    public String adminDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User admin = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long totalUsers = userService.getAllUsers().size();
        long totalDeTai = deTaiService.getAllDeTai().size();
        long totalPhanCong = phanCongService.getAllPhanCong().size();

        model.addAttribute("admin", admin);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalDeTai", totalDeTai);
        model.addAttribute("totalPhanCong", totalPhanCong);
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users/list";
    }

    @GetMapping("/users/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/users/form";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (userService.existsByUsername(user.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username đã tồn tại!");
            return "redirect:/admin/users/new";
        }
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("success", "Tạo người dùng thành công!");
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "admin/users/form";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, RedirectAttributes redirectAttributes) {
        user.setId(id);
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("success", "Cập nhật người dùng thành công!");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/toggle")
    public String toggleUserActive(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.toggleUserActive(id);
        redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái thành công!");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "Xóa người dùng thành công!");
        return "redirect:/admin/users";
    }

    @GetMapping("/detai")
    public String listDeTai(Model model) {
        List<DeTai> deTais = deTaiService.getAllDeTai();
        model.addAttribute("deTais", deTais);
        return "admin/detai/list";
    }

    @PostMapping("/detai/{id}/duyet")
    public String duyetDeTai(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        deTaiService.updateTrangThai(id, TrangThaiDeTai.DANG_THUC_HIEN);
        redirectAttributes.addFlashAttribute("success", "Duyệt đề tài thành công!");
        return "redirect:/admin/detai";
    }

    @PostMapping("/detai/{id}/tuchoi")
    public String tuChoiDeTai(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        deTaiService.updateTrangThai(id, TrangThaiDeTai.TU_CHOI);
        redirectAttributes.addFlashAttribute("success", "Từ chối đề tài thành công!");
        return "redirect:/admin/detai";
    }

    @GetMapping("/phancong")
    public String listPhanCong(Model model) {
        List<PhanCong> phanCongs = phanCongService.getAllPhanCong();
        model.addAttribute("phanCongs", phanCongs);
        return "admin/phancong/list";
    }

    @GetMapping("/phancong/new")
    public String showCreatePhanCongForm(Model model) {
        model.addAttribute("phanCong", new PhanCong());
        model.addAttribute("sinhViens", userService.getUsersByRole(Role.ROLE_SINHVIEN));
        model.addAttribute("giangViens", userService.getUsersByRole(Role.ROLE_GIANGVIEN));
        model.addAttribute("deTais", deTaiService.getAllDeTai());
        return "admin/phancong/form";
    }

    @PostMapping("/phancong")
    public String createPhanCong(@ModelAttribute PhanCong phanCong, RedirectAttributes redirectAttributes) {
        phanCongService.savePhanCong(phanCong);
        redirectAttributes.addFlashAttribute("success", "Tạo phân công thành công!");
        return "redirect:/admin/phancong";
    }

    @GetMapping("/phancong/{id}/edit")
    public String showEditPhanCongForm(@PathVariable Long id, Model model) {
        PhanCong phanCong = phanCongService.getPhanCongById(id);
        model.addAttribute("phanCong", phanCong);
        model.addAttribute("sinhViens", userService.getUsersByRole(Role.ROLE_SINHVIEN));
        model.addAttribute("giangViens", userService.getUsersByRole(Role.ROLE_GIANGVIEN));
        model.addAttribute("deTais", deTaiService.getAllDeTai());
        return "admin/phancong/form";
    }

    @PostMapping("/phancong/{id}")
    public String updatePhanCong(@PathVariable Long id, @ModelAttribute PhanCong phanCong, RedirectAttributes redirectAttributes) {
        phanCong.setId(id);
        phanCongService.updatePhanCong(phanCong);
        redirectAttributes.addFlashAttribute("success", "Cập nhật phân công thành công!");
        return "redirect:/admin/phancong";
    }

    // Quản lý điểm
    @GetMapping("/diem")
    public String listDiem(Model model) {
        List<PhanCong> phanCongs = phanCongService.getAllPhanCong();
        model.addAttribute("phanCongs", phanCongs);
        model.addAttribute("diemService", diemService);
        return "admin/diem/list";
    }
}

