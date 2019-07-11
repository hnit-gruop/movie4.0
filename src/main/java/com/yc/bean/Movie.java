package com.yc.bean;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "movie",type = "movie", shards = 1, replicas = 0)
public class Movie {
	@Id 
    private Integer movieId;
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;
	@Field(index=false,type = FieldType.Integer)
    private Integer duration;
	@Field(type = FieldType.Date)
    private Date releaseTime;
	@Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String foreignName;
	@Field(index=false,type = FieldType.Text)
    private String region;
	@Field(index=false,type = FieldType.Text)
    private String language;
	@Field(type = FieldType.Text)
    private String description;
	@Field(index=false,type = FieldType.Text)
    private String status;
	@Field(index=false,type = FieldType.Object)
    private MovieImage movieImage;
    
    public MovieImage getMovieImage() {
		return movieImage;
	}

	public void setMovieImage(MovieImage movieImage) {
		this.movieImage = movieImage;
	}
	
	
	/**
     * 非数据库字段
     */
    private String coverImage;
    private String bigImage;
    private List<MovieImage> smallImage;
    private List<Actor> listActor;
    private Actor director;
    private Double score;
    
    
    public Actor getDirector() {
		return director;
	}

	public void setDirector(Actor director) {
		this.director = director;
	}

	public List<Actor> getListActor() {
		return listActor;
	}

	public void setListActor(List<Actor> listActor) {
		this.listActor = listActor;
	}

	public String getBigImage() {
		return bigImage;
	}

	public void setBigImage(String bigImage) {
		this.bigImage = bigImage;
	}

	public List<MovieImage> getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(List<MovieImage> smallImage) {
		this.smallImage = smallImage;
	}

	private List<MovieType> type;
    
    //用于关联查询
    private Type tempType;
    private List<Type> listType;
    
    

    public Type getTempType() {
		return tempType;
	}

	public void setTempType(Type tempType) {
		this.tempType = tempType;
	}

	public List<Type> getListType() {
		return listType;
	}

	public void setListType(List<Type> listType) {
		this.listType = listType;
	}


	public List<MovieType> getType() {
		return type;
	}

	public void setType(List<MovieType> type) {
		this.type = type;
	}
    
    public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName == null ? null : foreignName.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", name=" + name + ", duration=" + duration + ", releaseTime="
				+ releaseTime + ", foreignName=" + foreignName + ", region=" + region + ", language=" + language
				+ ", description=" + description + ", status=" + status + ", movieImage=" + movieImage + ", coverImage="
				+ coverImage + ", score=" + score + ", type=" + type + "]";
	}
}