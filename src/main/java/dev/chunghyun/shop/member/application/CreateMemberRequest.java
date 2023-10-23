package dev.chunghyun.shop.member.application;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateMemberRequest {
    public final String name;
    public final String password;
}
