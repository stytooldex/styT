package dump_dex.Activity;

class Listitem {
    private int imageId;
    private String comtext;
    private String time;
    private int textcolor;

    Listitem(int imageId, String comtext, String time, int textcolor) {
        this.imageId = imageId;
        this.comtext = comtext;
        this.time = time;
        this.textcolor = textcolor;
    }

    public String getTime() {
        return time;
    }


    int getImageId() {
        return imageId;
    }


    String getComtext() {
        return comtext;
    }

    public int getTextcolor() {
        return textcolor;
    }
}
