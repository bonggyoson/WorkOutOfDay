package wods.crossfit.heart.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wods.crossfit.global.common.CommonResponse;
import wods.crossfit.global.common.ErrorResponse;
import wods.crossfit.global.common.ResponseMessage;
import wods.crossfit.heart.domain.dto.HeartDto;
import wods.crossfit.heart.domain.dto.HeartDto.HeartRequest;
import wods.crossfit.heart.service.HeartService;
import wods.crossfit.member.domain.dto.MemberDto;

@Tag(name = "좋아요 API Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/heart")
@Slf4j
public class HeartApiController {

    private final HeartService heartService;

    /**
     * 좋아요
     */
    @Operation(summary = "post save heart api", responses = {
            @ApiResponse(responseCode = "201", description = "좋아요 성공"),
            @ApiResponse(responseCode = "400", description = "좋아요 실패", content = @Content(schema = @Schema(implementation = MemberDto.MemberResponse.class))),
            @ApiResponse(responseCode = "500", description = "좋아요 실패", content = @Content(schema = @Schema(implementation = MemberDto.MemberResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<CommonResponse<HeartRequest>> saveHeart(
            @Validated @RequestBody final HeartDto.HeartRequest dto,
            BindingResult bindingResult) throws ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(ResponseMessage.INCREASE_HEART_FAIL);
        }

        heartService.saveHeart(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.res(HttpStatus.CREATED, ResponseMessage.INCREASE_HEART));
    }

    /**
     * 좋아요 취소
     */
    @Operation(summary = "delete delete heart api", responses = {
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
            @ApiResponse(responseCode = "500", description = "좋아요 취소 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteHeart(@RequestBody @Valid final HeartDto.HeartRequest dto) {
        heartService.deleteHeart(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.res(HttpStatus.OK, ResponseMessage.DECREASE_HEART));
    }
}
