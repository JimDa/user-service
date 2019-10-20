package com.example.user.endpoints;

import com.example.user.service.IAlismsService;
import dto.AliSmsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
@Api("阿里云短信服务")
public class SmsEndpoint {
    @Autowired
    private IAlismsService iAlismsService;

    @GetMapping("/get-verify-code")
    @ApiOperation("给手机号码发送短信验证码")
    public ResponseEntity<String> getVerifyCode(@RequestParam("phoneNum") String phoneNum) {
        final AliSmsResponse aliSmsResponse = iAlismsService.sendMessage(phoneNum);
        return "OK".equals(aliSmsResponse.getMessage()) ? ResponseEntity.ok("获取验证码成功！") : ResponseEntity.status(500).body("获取验证码失败！");
    }
}
