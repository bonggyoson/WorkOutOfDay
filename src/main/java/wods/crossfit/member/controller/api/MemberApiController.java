package wods.crossfit.member.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wods.crossfit.global.common.CommonResponse;
import wods.crossfit.global.common.ErrorResponse;
import wods.crossfit.global.common.ResponseMessage;
import wods.crossfit.member.domain.dto.MemberDto;
import wods.crossfit.member.domain.dto.MemberDto.MemberRequest;
import wods.crossfit.member.domain.dto.MemberDto.MemberResponse;
import wods.crossfit.member.service.MemberService;

@Tag(name = "회원 API Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    private final AuthenticationManager authenticationManager;

    /**
     * 회원 가입
     */
    @Operation(summary = "post save member api", responses = {
            @ApiResponse(responseCode = "201", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원 가입 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "회원 가입 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<CommonResponse<MemberResponse>> saveMember(
            @Validated @RequestBody final MemberRequest dto, BindingResult bindingResult)
            throws ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(ResponseMessage.CREATED_WORKOUT_FAIL);
        }

        memberService.saveMember(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.res(HttpStatus.CREATED, ResponseMessage.CREATED_MEMBER));
    }

    /**
     * 회원 정보 수정
     */
    @Operation(summary = "put update member api", responses = {
            @ApiResponse(responseCode = "200", description = "회원 수정 성공"),
            @ApiResponse(responseCode = "400", description = "회원 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "회원 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<MemberResponse>> updateMember(@PathVariable long id,
            @Validated @RequestBody final MemberRequest dto, BindingResult bindingResult)
            throws ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(ResponseMessage.UPDATE_MEMBER_FAIL);
        }

        memberService.updateMember(id, dto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(),
                        dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.res(HttpStatus.OK, ResponseMessage.UPDATE_MEMBER));
    }

    /**
     * 회원 정보 삭제
     */
    @Operation(summary = "delete delete member api", responses = {
            @ApiResponse(responseCode = "200", description = "회원 삭제 성공"),
            @ApiResponse(responseCode = "500", description = "회원 삭제 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<MemberResponse>> deleteMember(@PathVariable long id,
            HttpServletRequest request,
            HttpServletResponse response) {

        memberService.deleteMember(id);

        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.res(HttpStatus.OK, ResponseMessage.DELETE_MEMBER));
    }

}
