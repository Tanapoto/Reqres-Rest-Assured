package PojoData.Attachments;

import lombok.Data;

@Data
public class AttachmentsPojo {
    private String id;

    private String name;

    private String file;

    private String url;

    public AttachmentsPojo(String name, String file) {
        this.name = name;
        this.file = file;
    }

}
