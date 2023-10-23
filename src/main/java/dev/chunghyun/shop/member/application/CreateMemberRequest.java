package dev.chunghyun.shop.member.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateMemberRequest {
    private final String name;
    private final String password;
}
