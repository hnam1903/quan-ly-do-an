package com.qlda.repository;

import com.qlda.model.DeTai;
import com.qlda.model.TrangThaiDeTai;
import com.qlda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeTaiRepository extends JpaRepository<DeTai, Long> {
    List<DeTai> findByGiangVien(User giangVien);
    List<DeTai> findByTrangThai(TrangThaiDeTai trangThai);
    List<DeTai> findByGiangVienAndTrangThai(User giangVien, TrangThaiDeTai trangThai);
}



