package com.example.climbingBear.domain.record.controller;

import com.example.climbingBear.domain.diary.dto.DiaryPostReqDto;
import com.example.climbingBear.domain.record.dto.RecordDetailReqDto;
import com.example.climbingBear.domain.record.dto.RecordPostReqDto;
import com.example.climbingBear.domain.record.service.RecordService;
import com.example.climbingBear.global.common.CommonResponse;
import com.example.climbingBear.global.jwt.JwtProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mntn/record")
@RequiredArgsConstructor
@Slf4j
public class RecordController {
    private final JwtProvider jwtProvider;
    private final RecordService recordService;

    @PostMapping
    @ApiOperation(value = "등산 기록 저장", notes = "year, month, day, mntnSeq, time, distance 입력, header에 token 입력")
    public ResponseEntity<CommonResponse> saveRecord(HttpServletRequest request, @RequestBody RecordPostReqDto dto) throws Exception {
        Long userSeq = jwtProvider.getUserSeqFromRequest(request);
        return new ResponseEntity<>(CommonResponse.getSuccessResponse(recordService.recordSave(dto, userSeq)), HttpStatus.OK);
    }

    @GetMapping("/list")
    @ApiOperation(value = "등산 기록 리스트", notes = "header에 token 입력")
    public ResponseEntity<CommonResponse> getRecordList(HttpServletRequest request) throws Exception {
        Long userSeq = jwtProvider.getUserSeqFromRequest(request);
        return new ResponseEntity<>(CommonResponse.getSuccessResponse(recordService.MyRecordList(userSeq)), HttpStatus.OK);
    }

    @GetMapping()
    @ApiOperation(value = "등산 기록 상세", notes = "diarySeq 입력, header에 token 입력")
    public ResponseEntity<CommonResponse> getRecordList(HttpServletRequest request, RecordDetailReqDto dto) throws Exception {
        Long userSeq = jwtProvider.getUserSeqFromRequest(request);
        return new ResponseEntity<>(CommonResponse.getSuccessResponse(recordService.recordDetail(dto, userSeq)), HttpStatus.OK);
    }
}