package com.qlda.service;

import com.qlda.model.PhanCong;
import com.qlda.model.TienDo;
import com.qlda.repository.TienDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TienDoService {
    @Autowired
    private TienDoRepository tienDoRepository;

    public List<TienDo> getTienDoByPhanCong(PhanCong phanCong) {
        return tienDoRepository.findByPhanCongOrderByCreatedAtDesc(phanCong);
    }

    public TienDo saveTienDo(TienDo tienDo) {
        return tienDoRepository.save(tienDo);
    }
}



