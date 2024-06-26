package com.ssafy.idk.domain.piggybank.controller;


import com.ssafy.idk.domain.piggybank.dto.request.PiggyBankCreateRequestDto;
import com.ssafy.idk.domain.piggybank.dto.request.PiggyBankTransactionRequestDto;
import com.ssafy.idk.domain.piggybank.service.PiggyBankService;
import com.ssafy.idk.global.result.ResultCode;
import com.ssafy.idk.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/piggy-bank")
@Slf4j
public class PiggyBankController {

    private final PiggyBankService piggyBankService;

    @Operation(summary = "저금통 가입")
    @PostMapping(value = "")
    public ResponseEntity<ResultResponse> createPiggyBank(@RequestBody PiggyBankCreateRequestDto requestDto) {

        return ResponseEntity.ok(ResultResponse.of(ResultCode.PIGGY_BANK_CREATE_SUCCESS, piggyBankService.createPiggyBank(requestDto)));
    }

    @Operation(summary = "저금통 조회")
    @GetMapping(value = "")
    public ResponseEntity<ResultResponse> getPiggyBank() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PIGGY_BANK_GET_SUCCESS, piggyBankService.getPiggyBank()));
    }

    @Operation(summary = "저금통 해지")
    @DeleteMapping(value = "/{piggyBankId}")
    public ResponseEntity<ResultResponse> deletePiggyBank(@PathVariable("piggyBankId") Long piggyBankId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PIGGY_BANK_DELETE_SUCCESS, piggyBankService.deletePiggyBank(piggyBankId)));
    }

    @Operation(summary = "저금통 상세 조회")
    @GetMapping(value = "/{piggyBankId}")
    public ResponseEntity<ResultResponse> getDetailPiggyBank(@PathVariable("piggyBankId") Long piggyBankId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PIGGY_BANK_GET_DETAIL_SUCCESS, piggyBankService.getDetailPiggyBank(piggyBankId)));
    }

    @Operation(summary = "저금통 입금")
    @PatchMapping(value = "/{piggyBankId}/deposit")
    public ResponseEntity<ResultResponse> depositPiggyBank(@RequestBody PiggyBankTransactionRequestDto requestDto, @PathVariable("piggyBankId") Long piggyBankId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PIGGY_BANK_DEPOSIT_SUCCESS, piggyBankService.deposit(requestDto, piggyBankId)));
    }

    @Operation(summary = "저금통 출금")
    @PatchMapping(value = "/{piggyBankId}/withdrawal")
    public ResponseEntity<ResultResponse> withdrawPiggyBank(@RequestBody PiggyBankTransactionRequestDto requestDto, @PathVariable("piggyBankId") Long piggyBankId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PIGGY_BANK_WITHDRAW_SUCCESS, piggyBankService.withdraw(requestDto, piggyBankId)));
    }
}
