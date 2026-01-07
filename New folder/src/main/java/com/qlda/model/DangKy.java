package com.qlda.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dang_ky")
public class DangKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sinh_vien_id", nullable = false)
    private User sinhVien;

    @ManyToOne
    @JoinColumn(name = "de_tai_id", nullable = false)
    private DeTai deTai;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrangThaiDangKy trangThai = TrangThaiDangKy.DANG_CHO;

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

    public TrangThaiDangKy getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiDangKy trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}



