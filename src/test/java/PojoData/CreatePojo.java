package PojoData;

import lombok.Data;

@Data
public class CreatePojo {
    private String name;
    private String job;

    public CreatePojo(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
