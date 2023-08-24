package wods.crossfit.qa.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class QaDto {

    private String image;
    private String subject;
    private String content;
    private String url;

}
