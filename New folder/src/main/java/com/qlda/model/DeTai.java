package com.qlda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "de_tai")
public class DeTai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên đề tài không được để trống")
    @Column(nullable = false)
    private String tenDeTai;

    @Column(columnDefinition = "TEXT")
    private String moTa;

    @Column(columnDefinition = "TEXT")
    private String yeuCau;

    @NotNull(message = "Số lượng sinh viên không được để trống")
    @Min(value = 1, message = "Số lượng sinh viên phải lớn hơn 0")
    @Column(nullable = false)
    private Integer soLuongSinhVien;

    private String congNghe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrangThaiDeTai trangThai = TrangThaiDeTai.CHO_DUYET;

    @ManyToOne
    @JoinColumn(name = "giang_vien_id", nullable = false)
    private User giangVien;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenDeTai() {
        return tenDeTai;
    }

    public void setTenDeTai(String tenDeTai) {
        this.tenDeTai = tenDeTai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }

    public Integer getSoLuongSinhVien() {
        return soLuongSinhVien;
    }

    public void setSoLuongSinhVien(Integer soLuongSinhVien) {
        this.soLuongSinhVien = soLuongSinhVien;
    }

    public String getCongNghe() {
        return congNghe;
    }

    public void setCongNghe(String congNghe) {
        this.congNghe = congNghe;
    }

    public TrangThaiDeTai getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiDeTai trangThai) {
        this.trangThai = trangThai;
    }

    public User getGiangVien() {
        return giangVien;
    }

    public void setGiangVien(User giangVien) {
        this.giangVien = giangVien;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}



