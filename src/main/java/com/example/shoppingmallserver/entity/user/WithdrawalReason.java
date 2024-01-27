package com.example.shoppingmallserver.entity.user;


/**
 * 사용자의 탈퇴 이유를 나타내는 열거형 클래스입니다.
 * 탈퇴 이유는 서비스 불만, 배송 불만, 환불 정책 불만, 미방문 횟수 증가, 상품 가격 불만, 개인정보 누출 불만, 신뢰성 불만, 퇴사 등으로 구분됩니다.
 */
public enum WithdrawalReason {
    SERVICE_COMPLAIN,
    DELIVERY_COMPLAIN,
    REFUND_POLICY_COMPLAIN,
    LESS_FREQUENT_VISITS,
    ITEM_PRICE_COMPLAIN,
    PRIVACY_LEAKAGE_COMPLAIN,
    RELIABILITY_COMPLAIN,
    COMPANY_LEAVE
}
