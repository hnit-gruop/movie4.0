package com.yc.bean;

public class Cinema {
    private Integer cinemaId;

    private String name;

    private String address;

    private String gps;

    private String img;

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps == null ? null : gps.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

	@Override
	public String toString() {
		return "Cinema [cinemaId=" + cinemaId + ", name=" + name + ", address=" + address + ", gps=" + gps + ", img="
				+ img + "]";
	}
    
    
}