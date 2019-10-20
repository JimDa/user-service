package com.example.user.mapper;

import domain.ThirdPartyServiceAccess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ThirdPartyServiceMapper {
    ThirdPartyServiceAccess queryByServiceName(@Param("serviceName") String serviceName);
}
