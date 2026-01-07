package com.qlda.service;

import com.qlda.model.Diem;
import com.qlda.model.PhanCong;
import com.qlda.repository.DiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DiemService {
    @Autowired
    private DiemRepository diemRepository;

    public Optional<Diem> getDiemByPhanCong(PhanCong phanCong) {
        return diemRepository.findByPhanCong(phanCong);
    }

    public Diem saveDiem(Diem diem) {
        return diemRepository.save(diem);
    }

    public Diem updateDiem(Diem diem) {
        return diemRepository.save(diem);
    }
}



