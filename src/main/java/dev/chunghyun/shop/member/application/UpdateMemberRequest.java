package dev.chunghyun.shop.member.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateMemberRequest{
    private String name;
    private String password;
}
