package com.coooldoggy.semasearch.base

enum class SemaResultCode(val code: String, val message: String) {
    INFO_000(code = "INFO-000", message = "정상 처리되었습니다"),
    ERROR_300(code = "ERROR-300", message = "필수 값이 누락되어 있습니다."),
    INFO_100(code = "INFO-100", message = "인증키가 유효하지 않습니다."),
    ERROR_301(code = "ERROR-301", message = "파일타입 값이 누락 혹은 유효하지 않습니다."),
    ERROR_310(code = "ERROR-310", message = "해당하는 서비스를 찾을 수 없습니다."),
    ERROR_331(code = "ERROR-331", message = "요청시작위치 값을 확인하십시오."),
    ERROR_332(code = "ERROR-332", message = "요청종료위치 값을 확인하십시오."),
    ERROR_333(code = "ERROR-333", message = "요청위치 값의 타입이 유효하지 않습니다."),
    ERROR_334(code = "ERROR-334", message = "요청종료위치 보다 요청시작위치가 더 큽니다."),
    ERROR_335(code = "ERROR-335", message = "샘플데이터(샘플키:sample) 는 한번에 최대 5건을 넘을 수 없습니다."),
    ERROR_336(code = "ERROR-336", message = "데이터요청은 한번에 최대 1000건을 넘을 수 없습니다."),
    ERROR_500(code = "ERROR-500", message = "서버 오류입니다."),
    ERROR_600(code = "ERROR-600", message = "데이터베이스 연결 오류입니다."),
    ERROR_601(code = "ERROR-601", message = "SQL 문장 오류 입니다."),
    INFO_200(code = "INFO-200", message = "해당하는 데이터가 없습니다."),
    NONE(code = "", message = ""),
    ;

    companion object {
        fun from(search: String): SemaResultCode = values().find { it.code == search } ?: NONE
    }
}
