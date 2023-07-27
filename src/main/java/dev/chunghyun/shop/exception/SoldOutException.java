package dev.chunghyun.shop.exception;

public class SoldOutException extends RuntimeException {
    public SoldOutException() {
        super("SoldOutException 발생. 주문한 상품량 재고량보다 큽니다.");
    }
}
