package com.yc.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yc.bean.Actor;


public interface ActorRepository extends ElasticsearchRepository<Actor,Long>{

}
