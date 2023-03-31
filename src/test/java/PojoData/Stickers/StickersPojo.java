package PojoData.Stickers;

import lombok.Data;

@Data
public class StickersPojo {

    private String id;
    private String top;
    private String left;
    private String zIndex;
    private String rotate;
    private String image;

    public StickersPojo(String top, String left, String zIndex, String rotate, String image) {
        this.top = top;
        this.left = left;
        this.zIndex = zIndex;
        this.rotate = rotate;
        this.image = image;
    }

}
