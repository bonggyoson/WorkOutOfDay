package wods.crossfit.profile.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import wods.crossfit.global.common.CommonResponse;
import wods.crossfit.global.common.ResponseMessage;
import wods.crossfit.global.util.FileStore;
import wods.crossfit.profile.domain.dto.ProfileDto;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto;
import wods.crossfit.profileBenchmark.service.ProfileBenchmarkService;
import wods.crossfit.profile.service.ProfileService;

@Tag(name = "프로필 API Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Slf4j
public class ProfileApiController {

    private final ProfileService profileService;

    private final ProfileBenchmarkService profileBenchmarkService;

    private final FileStore fileStore;

    /**
     * 프로필 수정
     */
    @PostMapping("")
    public ResponseEntity<?> updateProfile(@ModelAttribute ProfileDto.ProfileRequest dto,
            HttpServletResponse response)
            throws IOException {

        profileService.updateProfile(dto);

        response.sendRedirect("http://localhost:8080/myPage/myProfile?id=" + dto.getMemberId());

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.res(HttpStatus.OK,
                ResponseMessage.PROFILE_UPDATE));
    }

    /**
     * 벤치마크 등록
     */
    @PostMapping("/benchmark")
    public ResponseEntity<?> saveBenchmark(@RequestBody ProfileBenchMarkDto.saveRequest dto) {

        profileBenchmarkService.save(dto);

        return ResponseEntity.ok().build();
    }

    /**
     * 벤치마크 기록 수정
     */
    @PutMapping("/benchmark/{id}")
    public ResponseEntity<?> updateBenchmark(@PathVariable long id,
            @RequestBody ProfileBenchMarkDto.recordRequest dto) {

        profileBenchmarkService.update(id, dto);

        return ResponseEntity.ok().build();
    }

    /**
     * 벤치마크 삭제
     */
    @DeleteMapping("/benchmark/{id}")
    public ResponseEntity<?> deleteBenchmark(@PathVariable long id) {

        profileBenchmarkService.delete(id);

        return ResponseEntity.ok().build();
    }

    /**
     * 이미지 조회
     */
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            MalformedURLException {

        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }


}
