package com.influence.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	
	
	USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),
	USERNAME_NOTFOUND(HttpStatus.NOT_FOUND, ""),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ""),
	EMAIL_DUPLICATED(HttpStatus.CONFLICT, ""),
	

    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다"),
	MEET_NOTFOUND(HttpStatus.NOT_FOUND, "존재하지 않는 모임 입니다."),
    MEETUSER_NOTFOUND(HttpStatus.NOT_FOUND, "해당 사용자는 이 모임에 참가하지 않았습니다."),
	INFLUENCER_NOTFOUND(HttpStatus.NOT_FOUND, "존재하지 않는 인플루언서 입니다."),
    
	
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "권한이 없습니다"),
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "유저명이 중복됩니다"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러가 발생하였습니다"),   

    
    /* 400 BAD_REQUEST : 잘못된 요청 */
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),

    /* 409 : CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다."),
    ;
	

    private HttpStatus status;
    private String message;
    
//	//Controller 단에서 처리할 에러코드
//	  BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "C001" , "잘못된 서버 요청입니다.")
//	, MISSING_REQUEST_PARAMETER_ERROR(HttpStatus.BAD_REQUEST,"C002","입력된 데이터 수가 잘못되었습니다.") 
//	, INVALID_PARAMS(HttpStatus.BAD_REQUEST, "C003", "필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다.")
//	, INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", "유효하지 않은 데이터 타입 입니다.")  
//	, UNAUTORIZED(HttpStatus.UNAUTHORIZED, "C005", "토큰 정보가 유효하지 않습니다.")
//	, FORBIDDEN_ERROR(HttpStatus.FORBIDDEN,"C006","권한이 없습니다.")
//	, NULL_POINT_ERROR(HttpStatus.NOT_FOUND,"C007","널값이 발생했습니다.")
//	, METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C008", "지원되지 않는 메서드입니다.")
//	, MISSING_REQUEST_VALUE_ERROR(HttpStatus.BAD_REQUEST,"C009","입력된 주소 값이 잘못되었습니다.")
//	, INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "C010" , "관리자에게 문의하여 주세요.")
//	
//	
//	//Service 단에서 처리할 에러코드
//	
//	//User
//	,USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND ,"U001" , "존재하지 않는 유저입니다.")
//	,USER_NULL_POINT_ERROR(HttpStatus.BAD_REQUEST,"U002" , "탈퇴한 유저 입니다.")
//	,USER_INVALID_ERROR(HttpStatus.BAD_REQUEST,"U003","권한이 없는 사용자 입니다.")
//	,USER_MISSING_VALUE_ERROR(HttpStatus.BAD_REQUEST,"U004","아이디 나 비밀번호가 일치하지 않습니다.")
//	
//	//board
//	,BOARD_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"B001", "존재하지 않는 게시글 유형 입니다.")
//	
//	//Post
//	,POST_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"P001", "존재하지 않는 피드 입니다.")
//	,POST_NULL_POINT_ERROR(HttpStatus.BAD_REQUEST,"P002" , "삭제된 게시글 입니다.") //게시글 삭제를 누를 경우 pk는 보존하고 해당 값에 담긴 나머지 컬럼을 null로 처리한다.
//	,POST_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "P003", "관리자에게 문의하여 주세요.")
//	,POST_FORBIDDEN_ERROR(HttpStatus.FORBIDDEN,"P004","권한이 없습니다.")
//	//LogState
//	,LOGSTATE_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"LS001", "존재하지 않는 로그 유형 입니다.")
//	
//	//Log
//	,LOG_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"L001","존재하지 않는 로그 입니다.")
//	,LOG_NULL_POINT_ERROR(HttpStatus.BAD_REQUEST,"L002","삭제된 로그 입니다.")
//	,LOG_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "L003", "관리자에게 문의하여 주세요.")
//	
//	//PostSource
//	,POSTSOURCE_BAD_REQUEST_ERROR(HttpStatus.NOT_FOUND,"PS001","존재하지 않는 출처 입니다." )
//	,POSTSOURCE_NULL_POINT_ERROR(HttpStatus.BAD_REQUEST,"PS002","존재하지 않는 출처 입니다." )
//	,POSTSOURCE_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PS003", "관리자에게 문의하여 주세요.")
//	
//	//File
//	,FILE_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"F001","존재하지 않는 파일 입니다." )
//	
//	
//	//HashTag
//	,HASHTAG_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, " HT001", "존재하는 해시태그가 없습니다.")
//
//	// Token
//	,REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A001", "존재하지 않는 리프레쉬 토큰입니다.")
//	,TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "A002", "유효하지 않은 토큰입니다.")
//	,TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A003", "만료된 토큰입니다.")
//
//
//	//Heart
//	,HEART_ALREADY_EXIST(HttpStatus.CONFLICT, "H001", "유저가 이미 하트를 눌렀습니다.")
//	,HEART_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "H002", " 존재하지 않는 하트입니다.")
//	//
//	//Reply
//	,REPLY_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"R001", "존재하지 않는 댓글입니다..")
//	,REPLY_NULL_POINT_ERROR(HttpStatus.BAD_REQUEST, "R002", "삭제된 댓글 입니다.")
//	,REPLY_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"R003", "관리자에게 문의하여 주세요.")
//	,REPLY_FORBIDDEN_ERROR(HttpStatus.FORBIDDEN,"R004","권한이 없습니다.")
//	,
//	
//	;

}