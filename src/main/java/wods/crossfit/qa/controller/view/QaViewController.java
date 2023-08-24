package wods.crossfit.qa.controller.view;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wods.crossfit.qa.domain.dto.QaDto;
import wods.crossfit.qa.service.QaService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QaViewController {

    private final QaService qaService;

    @GetMapping("/qa")
    public String getQas(Model model, @RequestParam(defaultValue = "크로스핏") String search)
            throws IOException {
        List<QaDto> newsList = qaService.getQas(search);

        model.addAttribute("news", newsList);
        model.addAttribute("search", search);

        return "qa/qa";
    }
}
