package com.ssafy.idk.domain.pocket.controller;

import com.ssafy.idk.domain.pocket.dto.request.PocketCreateAutoTransferRequestDto;
import com.ssafy.idk.domain.pocket.dto.request.PocketUpdateNameRequestDto;
import com.ssafy.idk.domain.pocket.service.PocketService;
import com.ssafy.idk.global.result.ResultCode;
import com.ssafy.idk.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pocket")
@Slf4j
public class PocketController {

    private final PocketService pocketService;

    @Operation(summary = "자동이체 돈 포켓 가입")
    @PostMapping(value = "/auto-transfer")
    public ResponseEntity<ResultResponse> createPocketAutoTransfer(@RequestBody PocketCreateAutoTransferRequestDto requestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POCKET_CREATE_BY_AUTO_TRANSFER_SUCCESS, pocketService.createByAutoTransfer(requestDto)));
    }

    @Operation(summary = "돈 포켓 상세 조회(입출금내역)")
    @GetMapping(value = "/{pocketId}")
    public ResponseEntity<ResultResponse> getPocketDetail(@PathVariable(name = "pocketId") Long pocketId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POCKET_GET_DETAIL_SUCCESS, pocketService.getPocketDetail(pocketId)));
    }

    @Operation(summary = "돈 포켓 입출금 내역 상세 조회")
    @GetMapping(value = "/{pocketId}/transaction/{transaction}")
    public ResponseEntity<ResultResponse> getPocketTransactionDetail(@PathVariable(name = "pocketId") Long pocketId, @PathVariable(name = "transactionId") Long transactionId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POCKET_GET_TRANSACTION_DETAIL_SUCCESS, pocketService.getPocketTransactionDetail(pocketId, transactionId)));
    }

    @Operation(summary = "돈 포켓 자동이체 해지")
    @DeleteMapping(value = "/auto-transfer/{pocketId}")
    public ResponseEntity<ResultResponse> deletePocketAutoTransfer(@PathVariable(name = "pocketId") Long pocketId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POCKET_AUTO_TRANSFER_DELETE_SUCCESS, pocketService.deletePocketAutoTransfer(pocketId)));
    }

    @Operation(summary = "돈 포켓 이름 수정")
    @PatchMapping(value = "/{pocketId}/name")
    public ResponseEntity<ResultResponse> updatePocketName(@RequestBody PocketUpdateNameRequestDto requestDto, @PathVariable(name = "pocketId") Long pocketId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POCKET_UPDATE_NAME_SUCCESS, pocketService.updatePocketName(requestDto, pocketId)));
    }
}
