package com.example.user.service;

import dto.AliSmsResponse;

public interface IAlismsService {
    AliSmsResponse sendMessage(String phoneNum);
}
