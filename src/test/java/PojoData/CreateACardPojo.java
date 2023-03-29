package PojoData;

import lombok.Data;

@Data
public class CreateACardPojo {
    private String name;
    private badges badges;
    private String desc;
    private String pos;
    private String due;
    private String start;
    private boolean dueComplete;
    private String urlSource;
    private String fileSource;
    private String mimeType;

    @Data
    public class badges{
        private int comments;
        private int attachments;
    }

    public CreateACardPojo(String name, String desc, String start, String mimeType) {
        this.name = name;
        this.desc = desc;
        this.start = start;
        this.mimeType = mimeType;
    }
}
