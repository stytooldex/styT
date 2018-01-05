package nico.styTool;


import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by luxin on 15-12-8.
 *  http://luxin.gitcafe.io
 */
public class PhontoFiles extends BmobObject implements Serializable{
    private static final long serialVersionUID=1L;

    private List<BmobFile> photos;
    private String photo;

    public List<BmobFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<BmobFile> photos) {
        this.photos = photos;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
