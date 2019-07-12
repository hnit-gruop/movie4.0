package com.yc.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.yc.Biz.MovieBiz;
import com.yc.bean.Actor;
import com.yc.bean.Movie;
import com.yc.service.ActorRepository;
import com.yc.service.MovieRepository;
@Component
public class Initialization implements ApplicationRunner{
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private ActorRepository actorRepository;
	@Resource
	private MovieBiz mb;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		movieRepository.deleteAll();
		actorRepository.deleteAll();
		List<Movie> a=null;	
		List<Actor> b=null;
			 a=mb.findByMovieName2();		
			 b=mb.findByActorName2();			
				 movieRepository.saveAll(a);
				 actorRepository.saveAll(b);
		
	}

}
