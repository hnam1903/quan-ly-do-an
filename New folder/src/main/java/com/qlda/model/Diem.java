package com.qlda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "diem")
public class Diem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "phan_cong_id", nullable = false)
    private PhanCong phanCong;

    @DecimalMin(value = "0.0", message = "Điểm phải >= 0")
    @DecimalMax(value = "10.0", message = "Điểm phải <= 10")
    @Column(precision = 3, scale = 1)
    private BigDecimal diemHuongDan;

    @DecimalMin(value = "0.0", message = "Điểm phải >= 0")
    @DecimalMax(value = "10.0", message = "Điểm phải <= 10")
    @Column(precision = 3, scale = 1)
    private BigDecimal diemPhanBien;

    @DecimalMin(value = "0.0", message = "Điểm phải >= 0")
    @DecimalMax(value = "10.0", message = "Điểm phải <= 10")
    @Column(precision = 3, scale = 1)
    private BigDecimal diemHoiDong;

    @Column(columnDefinition = "TEXT")
    private String nhanXet;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhanCong getPhanCong() {
        return phanCong;
    }

    public void setPhanCong(PhanCong phanCong) {
        this.phanCong = phanCong;
    }

    public BigDecimal getDiemHuongDan() {
        return diemHuongDan;
    }

    public void setDiemHuongDan(BigDecimal diemHuongDan) {
        this.diemHuongDan = diemHuongDan;
    }

    public BigDecimal getDiemPhanBien() {
        return diemPhanBien;
    }

    public void setDiemPhanBien(BigDecimal diemPhanBien) {
        this.diemPhanBien = diemPhanBien;
    }

    public BigDecimal getDiemHoiDong() {
        return diemHoiDong;
    }

    public void setDiemHoiDong(BigDecimal diemHoiDong) {
        this.diemHoiDong = diemHoiDong;
    }

    public String getNhanXet() {
        return nhanXet;
    }

    public void setNhanXet(String nhanXet) {
        this.nhanXet = nhanXet;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Tính điểm trung bình của 3 loại điểm: Hướng dẫn, Phản biện, Hội đồng
     * @return BigDecimal điểm trung bình hoặc null nếu chưa có đủ điểm
     */
    public BigDecimal tinhDiemTrungBinh() {
        int count = 0;
        BigDecimal sum = BigDecimal.ZERO;

        if (diemHuongDan != null) {
            sum = sum.add(diemHuongDan);
            count++;
        }
        if (diemPhanBien != null) {
            sum = sum.add(diemPhanBien);
            count++;
        }
        if (diemHoiDong != null) {
            sum = sum.add(diemHoiDong);
            count++;
        }

        if (count == 0) {
            return null;
        }

        // Tính trung bình với 1 chữ số thập phân
        BigDecimal average = sum.divide(BigDecimal.valueOf(count), 1, java.math.RoundingMode.HALF_UP);
        return average;
    }
}



