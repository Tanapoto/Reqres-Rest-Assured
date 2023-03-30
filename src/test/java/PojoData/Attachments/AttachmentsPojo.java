package PojoData.Attachments;

import lombok.Data;

@Data
public class AttachmentsPojo {

    private String name;

    private String fileName;

    private String url;

    public AttachmentsPojo(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
