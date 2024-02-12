package likelion.site.domain.questionpost.dto.response.tag;

import likelion.site.domain.questionpost.domain.QuestionTagMap;
import lombok.Getter;

@Getter
public class QuestionTagMapResponseIdDto {

    Long mapId;

    public QuestionTagMapResponseIdDto(QuestionTagMap questionTagMap) {
        this.mapId = questionTagMap.getId();
    }
}
