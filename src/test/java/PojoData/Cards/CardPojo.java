package PojoData.Cards;

import lombok.Data;

@Data
public class CardPojo {
    private String id;
    private String name;
    private String desc;
    private String pos;
    private String due;
    private String start;
    private boolean dueComplete;
    private String urlSource;
    private String fileSource;

    public CardPojo(String name, String desc, String start) {
        this.name = name;
        this.desc = desc;
        this.start = start;
    }



}
