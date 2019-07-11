package com.yc.bean;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@Document(indexName = "actor",type = "actor", shards = 1, replicas = 0)
public class Actor {
	@Id 
    private Integer actorId;
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String aname;
	@Field(index=false,type = FieldType.Integer)
    private Integer height;
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String secondName;
	@Field(index=false,type = FieldType.Text)
    private String position;
	@Field(index=false,type = FieldType.Text)
    private String country;
	@Field(index=false,type = FieldType.Text)
    private String sex;
	@Field(index=false,type = FieldType.Date)
    private Date birthday;
	@Field(index=false,type = FieldType.Text)
    private String nation;
	@Field(index=false,type = FieldType.Text)
    private String birthplace;
	@Field(index=false,type = FieldType.Text)
    private String constellation;
	@Field(index=false,type = FieldType.Text)
    private String description;
	@Field(index=false,type = FieldType.Text)
    private String pic;

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname == null ? null : aname.trim();
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName == null ? null : secondName.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace == null ? null : birthplace.trim();
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation == null ? null : constellation.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

	@Override
	public String toString() {
		return "Actor [actorId=" + actorId + ", aname=" + aname + ", height=" + height + ", secondName=" + secondName
				+ ", position=" + position + ", country=" + country + ", sex=" + sex + ", birthday=" + birthday
				+ ", nation=" + nation + ", birthplace=" + birthplace + ", constellation=" + constellation
				+ ", description=" + description + ", pic=" + pic + "]";
	}
    
}