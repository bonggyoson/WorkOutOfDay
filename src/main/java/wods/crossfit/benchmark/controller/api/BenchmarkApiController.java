package wods.crossfit.benchmark.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.saveProfileBenchmarkRecordRequest;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.saveProfileBenchmarkRequest;
import wods.crossfit.profileBenchmark.service.ProfileBenchmarkService;

@Tag(name = "벤치마크 API Controller")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/benchmark")
public class BenchmarkApiController {

    private final ProfileBenchmarkService profileBenchmarkService;

    /**
     * 벤치마크 등록
     */
    @Operation(summary = "post update profile api", responses = {
            @ApiResponse(responseCode = "200", description = "프로필 수정 성공"),
            @ApiResponse(responseCode = "400", description = "프로필 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "프로필 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<CommonResponse<BenchmarkResponse>> saveBenchmark(
            @RequestBody final saveProfileBenchmarkRequest dto) {

        profileBenchmarkService.save(dto);

        return ResponseEntity.ok().build();
    }

    /**
     * 벤치마크 기록 등록 및 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<BenchmarkResponse>> updateBenchmark(@PathVariable long id,
            @RequestBody final saveProfileBenchmarkRecordRequest dto) {

        profileBenchmarkService.update(id, dto);

        return ResponseEntity.ok().build();
    }

    /**
     * 벤치마크 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBenchmark(@PathVariable long id) {

        profileBenchmarkService.delete(id);

        return ResponseEntity.ok().build();
    }
}
