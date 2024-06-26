package com.ssafy.idk.domain.client.service;

import com.ssafy.idk.domain.client.dto.request.CreateCiRequestDto;
import com.ssafy.idk.domain.client.dto.request.SignRequestDto;
import com.ssafy.idk.domain.client.dto.response.CreateCiResponseDto;
import com.ssafy.idk.domain.client.dto.response.GetCiResponseDto;
import com.ssafy.idk.domain.client.dto.response.SignResponseDto;
import com.ssafy.idk.domain.mydata.exception.MydataException;
import com.ssafy.idk.domain.mydata.util.MydataUtil;
import com.ssafy.idk.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientCaService {

    private final RestTemplate restTemplate;
    @Value("${spring.mydata.ca-url}")
    private String caUrl;


    // ci 생성 요청
    public String createCiRequest(String name, String birthDate, String phoneNumber) {
        String caServerUrl = caUrl.concat("/api/ca/member/ci_create");
        System.out.println("caServerUrl = " + caServerUrl);
        CreateCiRequestDto createCiRequestDto = CreateCiRequestDto.of(name, birthDate, phoneNumber);

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity에 헤더와 요청 본문 추가
        HttpEntity<CreateCiRequestDto> requestEntity = new HttpEntity<>(createCiRequestDto, headers);

        // 요청 후 응답
        ResponseEntity<CreateCiResponseDto> responseEntity = restTemplate.exchange(caServerUrl, HttpMethod.POST, requestEntity, CreateCiResponseDto.class);

        // HTTP 상태 코드가 실패인지 확인
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new MydataException(ErrorCode.MEMBER_SIGNUP_FAILED);
        }

        // connectionInformation 추출
        String connectionInformation = responseEntity.getBody().getData().getConnectionInformation();

        return connectionInformation;
    }

    // ci 조회 요청
    public String getCiRequset(String name, String birthDate, String phoneNumber) {
        String caServerUrl = caUrl.concat("/api/ca/member/ci");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(caServerUrl)
                .queryParam("name", name)
                .queryParam("birthDate", birthDate)
                .queryParam("phoneNumber", phoneNumber);

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // parameter 설정
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("birthDate", birthDate);
        params.put("phoneNumber", phoneNumber);

        ResponseEntity<GetCiResponseDto> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, GetCiResponseDto.class);

        // HTTP 상태 코드가 실패인지 확인
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new MydataException(ErrorCode.MEMBER_SIGNUP_FAILED);
        }

        // connectionInformation 추출
        String connectionInformation = responseEntity.getBody().getData().getConnectionInformation();

        return connectionInformation;
    }

    // 전자서명 요청
    public List<Map<String, String>> signRequest(String connectionInformation, List<Map<String, String>> consentList) {

        // 디버깅: consentList 확인
        System.out.println("Debug: consentList size: " + consentList.size());
        for (Map<String, String> consent : consentList) {
            System.out.println("Debug: Consent: " + consent);
        }

        String caServerUrl = caUrl.concat("/api/ca/sign_request");

        SignRequestDto signRequestDto = SignRequestDto.of(connectionInformation, consentList);

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);

        // HttpEntity에 헤더와 요청 본문 추가
        HttpEntity<SignRequestDto> requestEntity = new HttpEntity<>(signRequestDto, headers);

        // 요청 후 응답
        ResponseEntity<SignResponseDto> responseEntity = restTemplate.exchange(caServerUrl, HttpMethod.POST, requestEntity, SignResponseDto.class);

        // HTTP 상태 코드가 실패인지 확인
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new MydataException(ErrorCode.MEMBER_SIGNUP_FAILED);
        }

        // signedDataList 추출
        List<Map<String, String>> signedInfoList = responseEntity.getBody().getData().getSignedInfoList();

        System.out.println("signedInfoList = " + signedInfoList);

        return signedInfoList;

    }
}
