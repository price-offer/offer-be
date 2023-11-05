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
import com.offer.msg.application.response.MsgRoomInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MsgController {
    private final MsgService msgService;
    private final MsgRoomService msgRoomService;

    @Operation(summary = "쪽지 room 생성")
    @PostMapping("/api/msgrooms")
    public ResponseEntity<ApiResponse> createMsgRoom(@AuthenticationPrincipal LoginMember loginMember,
                                                     @RequestBody MsgRoomCreateRequest request) {
        CommonCreationResponse response = msgRoomService.createMsgRoom(request, loginMember.getId());

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "쪽지 room 목록 조회")
    @GetMapping("/api/msgrooms")
    public ResponseEntity<ApiResponse> getMsgRooms(@AuthenticationPrincipal LoginMember loginMember,
                                                   @RequestParam(required = true) int page) {
        List<MsgRoomInfoResponse> response = msgRoomService.getMsgRooms(page, loginMember.getId());

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "쪽지 보내기")
    @PostMapping("/api/msgrooms/{msgRoomId}/msgs")
    public ResponseEntity<ApiResponse> sendMsg(@AuthenticationPrincipal LoginMember loginMember,
                                               @PathVariable Long msgRoomId,
                                               @RequestBody MsgCreateRequest request) {
        CommonCreationResponse response = msgService.sendMsg(msgRoomId, request, loginMember.getId());

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "특정 쪽지 room의 전체 쪽지 내용 조회")
    @GetMapping("/api/msgrooms/{msgRoomId}/msgs")
    public ResponseEntity<ApiResponse> getAllMsgs(@AuthenticationPrincipal LoginMember loginMember,
                                                  @PathVariable Long msgRoomId,
                                                  @RequestParam(required = true) int page) {
        List<MsgInfoResponse> response = msgService.getMsgs(page, msgRoomId);

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    // TODO: msg room 삭제 API
}
