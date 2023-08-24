package wods.crossfit.qa.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import wods.crossfit.qa.domain.dto.QaDto;

@Service
public class QaService {

    private static final String News_URL = "https://search.naver.com/search.naver?where=view&sm=tab_jum&query=";

    public List<QaDto> getQas(String search) throws IOException {
        List<QaDto> newsList = new ArrayList<>();
        Document document = Jsoup.connect(News_URL + search).get();

        Elements contents = document.select("div ul.lst_total._list_base li");

        for (Element content : contents) {
            QaDto news = QaDto.builder()
                    .image(content.select(
                                    "a span.thumb_fix img")
                            .attr("abs:src")) // 이미지
                    .subject(content.select("a.api_txt_lines").text())        // 제목
                    .content(content.select("div.total_dsc_wrap").text())        // 본문
                    .url(content.select("div.total_dsc_wrap a").attr("abs:href"))        // 링크
                    .build();
            newsList.add(news);
        }

        return newsList;
    }
}
