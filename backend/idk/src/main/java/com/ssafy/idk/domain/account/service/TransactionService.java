package com.ssafy.idk.domain.account.service;

import com.ssafy.idk.domain.account.entity.Account;
import com.ssafy.idk.domain.account.entity.Category;
import com.ssafy.idk.domain.account.entity.Transaction;
import com.ssafy.idk.domain.account.dto.response.TransactionResponseDto;
import com.ssafy.idk.domain.account.exception.AccountException;
import com.ssafy.idk.domain.account.repository.AccountRepository;
import com.ssafy.idk.domain.account.repository.TransactionRepository;
import com.ssafy.idk.domain.member.entity.Member;
import com.ssafy.idk.domain.member.service.AuthenticationService;
import com.ssafy.idk.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AuthenticationService authenticationService;

    public List<TransactionResponseDto> getTransaction() {
        Member member = authenticationService.getMemberByAuthentication();
        Account account = accountRepository.findByMember(member)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        List<Transaction> transactionList = transactionRepository.findAllByAccount(account);

        // 최신순 정렬
        Comparator<Transaction> comparator = Comparator.comparing(Transaction::getCreatedAt).reversed();
        Collections.sort(transactionList, comparator);

        List<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        for(Transaction transaction : transactionList) {
            Boolean isDeposit = false;
            if(transaction.getCategory() == Category.입금) isDeposit = true;
            transactionResponseDtoList.add(TransactionResponseDto.of(transaction.getTransactionId(), transaction.getContent(), transaction.getAmount(), transaction.getBalance(), isDeposit, transaction.getCreatedAt()));
        }

        return transactionResponseDtoList;
    }

    @Transactional
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }


}
