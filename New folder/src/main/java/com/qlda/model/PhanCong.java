package com.qlda.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "phan_cong")
public class PhanCong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sinh_vien_id", nullable = false)
    private User sinhVien;

    @ManyToOne
    @JoinColumn(name = "de_tai_id", nullable = false)
    private DeTai deTai;

    @ManyToOne
    @JoinColumn(name = "giang_vien_huong_dan_id")
    private User giangVienHuongDan;

    @ManyToOne
    @JoinColumn(name = "giang_vien_phan_bien_id")
    private User giangVienPhanBien;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(User sinhVien) {
        this.sinhVien = sinhVien;
    }

    public DeTai getDeTai() {
        return deTai;
    }

    public void setDeTai(DeTai deTai) {
        this.deTai = deTai;
    }

    public User getGiangVienHuongDan() {
        return giangVienHuongDan;
    }

    public void setGiangVienHuongDan(User giangVienHuongDan) {
        this.giangVienHuongDan = giangVienHuongDan;
    }

    public User getGiangVienPhanBien() {
        return giangVienPhanBien;
    }

    public void setGiangVienPhanBien(User giangVienPhanBien) {
        this.giangVienPhanBien = giangVienPhanBien;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}



