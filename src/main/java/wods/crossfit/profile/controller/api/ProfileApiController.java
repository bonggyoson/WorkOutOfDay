package wods.crossfit.profile.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wods.crossfit.global.common.CommonResponse;
import wods.crossfit.global.common.ErrorResponse;
import wods.crossfit.global.common.ResponseMessage;
import wods.crossfit.global.util.FileStore;
import wods.crossfit.profile.domain.dto.ProfileDto;
import wods.crossfit.profile.domain.dto.ProfileDto.ProfileResponse;
import wods.crossfit.profile.service.ProfileService;

@Tag(name = "프로필 API Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Slf4j
public class ProfileApiController {

    private final ProfileService profileService;

    private final FileStore fileStore;

    /**
     * 프로필 수정
     */
    @Operation(summary = "post update profile api", responses = {
            @ApiResponse(responseCode = "200", description = "프로필 수정 성공"),
            @ApiResponse(responseCode = "400", description = "프로필 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "프로필 수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<CommonResponse<ProfileResponse>> updateProfile(
            @ModelAttribute final ProfileDto.ProfileRequest dto,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        profileService.updateProfile(dto);

        String redirectUrl = request.getRequestURL().toString()
                .replace(request.getRequestURI(), "") + "/myPage/myProfile?id=" + dto.getMemberId();

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println("<script>alert('프로필이 수정되었습니다.'); window.location.replace('" + redirectUrl
                + "');</script>");
        writer.flush();

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.res(HttpStatus.OK,
                ResponseMessage.PROFILE_UPDATE));
    }

    /**
     * 이미지 조회
     */
    @Operation(summary = "get image api", responses = {
            @ApiResponse(responseCode = "200", description = "이미지 로딩 성공"),
            @ApiResponse(responseCode = "500", description = "이미지 로딩 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            MalformedURLException {

        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }


}
