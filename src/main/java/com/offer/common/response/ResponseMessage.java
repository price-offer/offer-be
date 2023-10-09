package com.offer.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {
    // common
    SUCCESS(2000, "Success"),

    // error
    INTERNAL_SERVER_ERROR(5000, "서버에서 내부적으로 에러가 발생했습니다."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(4001, "지원하지 않는 HTTP method 입니다."),
    INVALID_REQUEST_ARGUMENT_TYPE(4002, "Request 인자 값의 타입이 올바르지 않습니다."),
    MISSING_PARAMETER(4003, "Request Parameter가 비어있습니다."),

    // authentication
    LOGIN_FAIL(4004, "email 또는 비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(4005, "이미 존재하는 이메일입니다."),

    // article
    ARTICLE_NOT_FOUND(4006, "요청한 게시글을 찾을 수 없습니다."),
    PRODUCT_STATUS_NOT_FOUND(4007, "요청한 상품 상태(중고상품, 새상품)가 올바르지 않습니다."),
    TRADE_STATUS_NOT_FOUND(4008, "요청한 거래 상태가 존재하지 않습니다."),
    TRADE_METHOD_NOT_FOUND(4009, "요청한 거래방식이 존재하지 않습니다."),
    CATEGORY_NOT_FOUND(4010, "카테고리가 존재하지 않습니다."),
    NOT_SUPPORTING_PARAM_COMBINATION(4011, "지원하지 않는 parameter 조합입니다."),
    NOT_COMPLETED_TRADE(4012, "거래완료상태가 아닙니다."),
    ALREADY_SWITCH_TRADESTATUS(4013, "거래완료상태에서 거래상태를 변경할 수 없습니다."),

    // member
    MEMBER_NOT_FOUND(4014, "요청한 사용자를 찾을 수 없습니다."),
    MEMBER_ALREADY_EXIST(4015, "해당 사용자가 이미 존재합니다."),
    SCORE_NOT_FOUND(4016, "존재하지 않는 Score입니다."),

    // review
    ALREADY_REVIEWED(4017, "이미 리뷰를 남긴 사용자입니다."),
    INVALID_REVIEWEE(4018, "리뷰 대상이 올바르지 않습니다."),
    INVALID_ROLE(4019, "상대방의 역할이 올바르지 않습니다."),
    REVIEW_NOT_FOUND(4020, "거래후기가 존재하지 않습니다."),

    // s3
    FILE_CONVERTION_FAIL(4021, "이미지 파일을 전환하는데 실패했습니다."),
    INVALID_IMAGE_EXCEPTION(4022, "변환할 이미지가 존재하지 않습니다."),
    FILE_SIZE_LIMIT_EXCEEDED(4023, "파일 사이즈는 최대 5MB 이하여야 합니다."),

    // offer
    EXCEED_OFFER_COUNT(4024, "가격제안 횟수를 초과했습니다."),
    OFFER_NOT_FOUND(4025, "가격 제안이 존재하지 않습니다."),
    EXISTS_ALREADY_SELECTED_OFFER(4026, "이미 선택된 offer가 존재합니다."),
    NOT_SELECTED_OFFER(4027, "선택된 Offer가 없습니다."),
    ALREADY_EXIST_SAME_PRICE_OFFER(4028, "같은 가격으로 제안한 오퍼가 존재합니다."),

    // message
    MESSAGE_ROOM_NOT_FOUND(4029, "나가기한 대화방이거나 없는 대화방입니다. 가격 제안자에게 쪽지를 전송하려면 [게시글]>[가격제안]의 [편지 버튼]을 누르세요."),
    EXITED_MESSAGE_ROOM(4030, "나가기한 대화방이거나 없는 대화방입니다."),
    MESSAGE_PARTNER_EXITED_MESSAGE_ROOM(4031, "쪽지 상대가 대화방을 나갔습니다."),
    PERMISSION_DENIED(4032, "접근 권한이 없습니다."),

    // request invalid
    DTO_FIELD_INVALID(4033, "DTO 요청 값이 유효하지 않습니다."),
    PATH_FIELD_INVALID(4034, "HTTP Path 값이 유효하지 않습니다."),

    ;

    private final int code;
    private final String message;
}
