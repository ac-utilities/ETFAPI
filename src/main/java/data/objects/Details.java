package data.objects;

public class Details {
    public String title;
    public String soldBy;
    public String recoil;
    public String ergonomics;
    public String accuracy;
    public int slotCount;

    public Details() {
        this.title = "title";
        this.soldBy = "soldBy";
        this.recoil = "recoil";
        this.ergonomics = "ergonomics";
        this.accuracy = "accuracy";
        this.slotCount = -1;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }

    public void setRecoil(String recoil) {
        this.recoil = recoil;
    }

    public void setErgonomics(String ergonomics) {
        this.ergonomics = ergonomics;
    }

    public void setSlotCount(int slotCount) {
        this.slotCount = slotCount;
    }

}
