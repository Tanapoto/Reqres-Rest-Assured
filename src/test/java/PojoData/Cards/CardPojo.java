package PojoData.Cards;

import lombok.Data;

@Data
public class CardPojo {
    private String id;
    private String name;
    private badges badges;
    private String desc;
    private String pos;
    private String due;
    private String start;
    private boolean dueComplete;
    private String urlSource;
    private String fileSource;

    private String limits;
    @Data
    public class badges{
        private int comments;
        private int attachments;
    }

    public CardPojo(String name, String desc, String start) {
        this.name = name;
        this.desc = desc;
        this.start = start;
    }



}
