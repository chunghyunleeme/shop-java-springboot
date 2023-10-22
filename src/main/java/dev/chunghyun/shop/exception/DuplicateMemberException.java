package dev.chunghyun.shop.exception;

public class DuplicateMemberException extends RuntimeException{
    public DuplicateMemberException() {
        super("DuplicateMemberException 발생. 이미 가입된 유저입니다.");
    }
}
