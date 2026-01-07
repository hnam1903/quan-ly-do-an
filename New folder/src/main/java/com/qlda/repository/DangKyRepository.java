package com.qlda.repository;

import com.qlda.model.DangKy;
import com.qlda.model.DeTai;
import com.qlda.model.TrangThaiDangKy;
import com.qlda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DangKyRepository extends JpaRepository<DangKy, Long> {
    List<DangKy> findBySinhVien(User sinhVien);
    List<DangKy> findByDeTai(DeTai deTai);
    List<DangKy> findByDeTaiAndTrangThai(DeTai deTai, TrangThaiDangKy trangThai);
    Optional<DangKy> findBySinhVienAndDeTai(User sinhVien, DeTai deTai);
    boolean existsBySinhVienAndDeTai(User sinhVien, DeTai deTai);
}



