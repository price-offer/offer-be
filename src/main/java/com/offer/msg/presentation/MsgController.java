package com.offer.msg.presentation;

import com.offer.authentication.presentation.AuthenticationPrincipal;
import com.offer.authentication.presentation.LoginMember;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.msg.application.MsgRoomService;
import com.offer.msg.application.MsgService;
import com.offer.msg.application.request.MsgCreateRequest;
import com.offer.msg.application.request.MsgRoomCreateRequest;
import com.offer.msg.application.response.MsgInfoResponse;
import com.offer.msg.application.response.MsgRoomBriefResponse;
import com.offer.msg.application.response.MsgRoomInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MsgController {
    private final MsgService msgService;
    private final MsgRoomService msgRoomService;

    @Operation(summary = "쪽지 room 생성", security = {@SecurityRequirement(name = "jwt")})
    @PostMapping("/api/msgrooms")
    public ResponseEntity<ApiResponse<CommonCreationResponse>> createMsgRoom(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @RequestBody MsgRoomCreateRequest request) {

        CommonCreationResponse response = msgRoomService.createMsgRoom(request,
            loginMember.getId());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "쪽지 room 목록 조회", security = {@SecurityRequirement(name = "jwt")})
    @GetMapping("/api/msgrooms")
    public ResponseEntity<ApiResponse<List<MsgRoomInfoResponse>>> getMsgRooms(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @RequestParam(required = true) int page) {

        List<MsgRoomInfoResponse> response = msgRoomService.getMsgRooms(page, loginMember.getId());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "쪽지 room 단건 조회", security = {@SecurityRequirement(name = "jwt")})
    @GetMapping("/api/msgrooms/{roomId}")
    public ResponseEntity<ApiResponse<MsgRoomBriefResponse>> getMsgRooms(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long roomId) {

        MsgRoomBriefResponse response = msgRoomService.getMsgRoom(roomId, loginMember.getId());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "쪽지 room 삭제", security = {@SecurityRequirement(name = "jwt")})
    @DeleteMapping("/api/msgrooms/{roomId}")
    public ResponseEntity<ApiResponse<Map<String, Long>>> deleteMsgRooms(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long roomId) {

        Long deleteMsgRoomId = msgRoomService.deleteMsgRoom(roomId, loginMember.getId());
        Map<String, Long> result = new HashMap<>();
        result.put("msgRoomId", deleteMsgRoomId);
        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, result)
        );
    }

    @Operation(summary = "쪽지 보내기", security = {@SecurityRequirement(name = "jwt")})
    @PostMapping("/api/msgrooms/{msgRoomId}/msgs")
    public ResponseEntity<ApiResponse<CommonCreationResponse>> sendMsg(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long msgRoomId,
        @RequestBody MsgCreateRequest request) {

        CommonCreationResponse response = msgService.sendMsg(msgRoomId, request,
            loginMember.getId());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "특정 쪽지 room의 전체 쪽지 내용 조회", security = {@SecurityRequirement(name = "jwt")})
    @GetMapping("/api/msgrooms/{msgRoomId}/msgs")
    public ResponseEntity<ApiResponse<List<MsgInfoResponse>>> getAllMsgs(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long msgRoomId,
        @RequestParam(required = true) int page) {

        List<MsgInfoResponse> response = msgService.getMsgs(page, msgRoomId);

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }


}
