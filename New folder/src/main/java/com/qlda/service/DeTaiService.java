package com.qlda.service;

import com.qlda.model.DeTai;
import com.qlda.model.TrangThaiDeTai;
import com.qlda.model.User;
import com.qlda.repository.DeTaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeTaiService {
    @Autowired
    private DeTaiRepository deTaiRepository;

    public List<DeTai> getAllDeTai() {
        return deTaiRepository.findAll();
    }

    public List<DeTai> getDeTaiByGiangVien(User giangVien) {
        return deTaiRepository.findByGiangVien(giangVien);
    }

    public List<DeTai> getDeTaiByTrangThai(TrangThaiDeTai trangThai) {
        return deTaiRepository.findByTrangThai(trangThai);
    }

    public DeTai getDeTaiById(Long id) {
        return deTaiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đề tài không tồn tại"));
    }

    public DeTai saveDeTai(DeTai deTai) {
        return deTaiRepository.save(deTai);
    }

    public DeTai updateDeTai(DeTai deTai) {
        return deTaiRepository.save(deTai);
    }

    public void deleteDeTai(Long id) {
        deTaiRepository.deleteById(id);
    }

    public void updateTrangThai(Long id, TrangThaiDeTai trangThai) {
        DeTai deTai = getDeTaiById(id);
        deTai.setTrangThai(trangThai);
        deTaiRepository.save(deTai);
    }
}



