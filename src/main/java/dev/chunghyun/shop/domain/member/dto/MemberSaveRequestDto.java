package dev.chunghyun.shop.domain.member.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberSaveRequestDto {
    public final String name;

    public final String password;
}
