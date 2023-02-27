package com.example.jdbcex.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

public enum MapperUtil {
//ModelMapper의 설정을 변경하고 쉽게 사용할 수 있는 MapperUtil
    INSTANCE;

    private ModelMapper modelMapper;

    MapperUtil(){
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration() //ModelMapper의 설정 변경
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ModelMapper get(){
        return modelMapper;
    }
}
