package com.easy.boke.service.impl;

import com.easy.boke.dao.DemoMapper;
import com.easy.boke.dto.DemoDTO;
import com.easy.boke.entity.DemoEntity;
import com.easy.boke.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/11/7 0007
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    public Integer demo(DemoDTO demoDTO) {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setName(demoDTO.getName());
        return demoMapper.insert(demoEntity);
    }
}
