package wods.crossfit.profileBenchmark.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wods.crossfit.benchmark.domain.dto.BenchmarkDto.BenchmarkResponse;
import wods.crossfit.global.common.CommonResponse;
import wods.crossfit.global.common.ErrorResponse;
import wods.crossfit.global.common.ResponseMessage;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.ProfileBenchmarkRequest;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.ProfileBenchmarkRequestRecord;
import wods.crossfit.profileBenchmark.service.ProfileBenchmarkService;

@Tag(name = "프로필 벤치마크 API Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profileBenchmark")
@Slf4j
public class ProfileBenchmarkApiController {

    private final ProfileBenchmarkService profileBenchmarkService;

    /**
     * 프로필 벤치마크 등록
     */
    @Operation(summary = "post save profileBenchmark api", responses = {
            @ApiResponse(responseCode = "201", description = "프로필 벤치마크 등록 성공"),
            @ApiResponse(responseCode = "4xx", description = "프로필 벤치마크 등록 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "5xx", description = "프로필 벤치마크 등록 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<CommonResponse<BenchmarkResponse>> saveProfileBenchmark(
            @RequestBody final List<ProfileBenchmarkRequest> dto) {

        profileBenchmarkService.saveProfileBenchmark(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.res(HttpStatus.CREATED,
                ResponseMessage.CREATED_PROFILE_BENCHMARK));
    }

    /**
     * 프로필 벤치마크 기록 등록 및 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<BenchmarkResponse>> saveProfileBenchmarkRecord(
            @PathVariable long id,
            @RequestBody final ProfileBenchmarkRequestRecord dto) {

        profileBenchmarkService.saveProfileBenchmarkRecordRequest(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.res(HttpStatus.OK,
                ResponseMessage.CREATED_PROFILE_BENCHMARK_RECORD));
    }

    /**
     * 프로필 벤치마크 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfileBenchmark(@PathVariable long id) {

        profileBenchmarkService.deleteProfileBenchmark(id);

        return ResponseEntity.ok().build();
    }
}
