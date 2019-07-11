package com.yc.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.github.pagehelper.PageInfo;
import com.yc.bean.Movie;

public interface MovieRepository extends ElasticsearchRepository<Movie,Long> {



}
