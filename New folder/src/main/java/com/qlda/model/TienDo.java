package com.qlda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.LocalDateTime;

@Entity
@Table(name = "tien_do")
public class TienDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "phan_cong_id", nullable = false)
    private PhanCong phanCong;

    @NotNull(message = "Tuần không được để trống")
    @Min(value = 1, message = "Tuần phải từ 1 đến 10")
    @Max(value = 10, message = "Tuần phải từ 1 đến 10")
    @Column(nullable = false)
    private Integer tuan;

    @NotBlank(message = "Nội dung tiến độ không được để trống")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String noiDung;

    @Column(columnDefinition = "TEXT")
    private String nhanXet;

    private LocalDateTime createdAt = LocalDateTime.now();

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

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNhanXet() {
        return nhanXet;
    }

    public void setNhanXet(String nhanXet) {
        this.nhanXet = nhanXet;
    }

    public Integer getTuan() {
        return tuan;
    }

    public void setTuan(Integer tuan) {
        this.tuan = tuan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}



