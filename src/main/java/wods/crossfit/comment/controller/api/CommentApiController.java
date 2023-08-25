package wods.crossfit.comment.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wods.crossfit.comment.domain.dto.CommentDto.CommentRequest;
import wods.crossfit.global.common.CommonResponse;
import wods.crossfit.global.common.ErrorResponse;
import wods.crossfit.global.common.ResponseMessage;
import wods.crossfit.comment.service.CommentService;

@Tag(name = "댓글 API Controller")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/comment")
public class CommentApiController {

    private final CommentService commentService;

    /**
     * 댓글 저장
     */
    @Operation(summary = "post save comment api", responses = {
            @ApiResponse(responseCode = "201", description = "댓글 저장 성공"),
            @ApiResponse(responseCode = "400", description = "댓글 저장 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "댓글 저장 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<CommonResponse<CommentRequest>> saveComment(
            @Validated @RequestBody final CommentRequest dto, BindingResult bindingResult)
            throws ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(ResponseMessage.CREATED_COMMENT_FAIL);
        }

        commentService.saveComment(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.res(HttpStatus.CREATED, ResponseMessage.CREATED_COMMENT));
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{id}")
    @Operation(summary = "put update comment api", responses = {
            @ApiResponse(responseCode = "201", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "댓글 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "댓글 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CommonResponse<?>> updateComment(@PathVariable long id,
            @RequestBody final CommentRequest dto, BindingResult bindingResult)
            throws ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(ResponseMessage.UPDATE_COMMENT_FAIL);
        }

        commentService.updateComment(id, dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.res(HttpStatus.OK, ResponseMessage.UPDATE_COMMENT));
    }

    /**
     * 댓글 삭제
     */
    @Operation(summary = "delete delete comment api", responses = {
            @ApiResponse(responseCode = "201", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "댓글 삭제 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "댓글 저장 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.res(HttpStatus.OK, ResponseMessage.DELETE_COMMENT));
    }

}
