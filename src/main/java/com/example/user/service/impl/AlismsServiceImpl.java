package com.example.user.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.example.user.mapper.ThirdPartyServiceMapper;
import com.example.user.service.IAlismsService;
import com.google.gson.Gson;
import domain.ThirdPartyServiceAccess;
import dto.AliSmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AlismsServiceImpl implements IAlismsService {
    @Autowired
    private Gson gson = new Gson();
    @Autowired
    private ThirdPartyServiceMapper thirdPartyServiceMapper;

    @Override
    public AliSmsResponse sendMessage(String phoneNum) {
        ThirdPartyServiceAccess access = thirdPartyServiceMapper.queryByServiceName("aliSms");
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", access.getAccessKey(), access.getAccessSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "ezblog短信验证");
        request.putQueryParameter("TemplateCode", "SMS_175572254");
        request.putQueryParameter("TemplateParam", String.format("{\"code\":\"%s\"}", getRandomCode()));
        String message = null;
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            message = response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return message == null ? null : gson.fromJson(message, AliSmsResponse.class);
    }

    private String getRandomCode() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
