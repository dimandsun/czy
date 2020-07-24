package english.model;

import english.enums.TenseEnum;
import english.enums.WordTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author chenzy
 * 
 * @since 2020-04-28
 */
public class Word {
    private Integer id;
    private String code;
    private String des;
    @JsonProperty("type")
    private WordTypeEnum wordTypeEnum;
    @JsonProperty("tense")
    private TenseEnum tenseEnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public WordTypeEnum getWordTypeEnum() {
        return wordTypeEnum;
    }

    public void setWordTypeEnum(WordTypeEnum wordTypeEnum) {
        this.wordTypeEnum = wordTypeEnum;
    }

    public TenseEnum getTenseEnum() {
        return tenseEnum;
    }

    public void setTenseEnum(TenseEnum tenseEnum) {
        this.tenseEnum = tenseEnum;
    }
}
