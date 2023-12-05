package com.myfreeproject.api.respons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다.",
 *     "validation": {
 *         "title": "값을 입력해 주세요."
 *     }
 * }
 */

@Setter
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;

}
