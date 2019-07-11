package com.yc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.yc.Biz.MovieBiz;
import com.yc.bean.Actor;
import com.yc.bean.Movie;
import com.yc.bean.MovieActor;
import com.yc.bean.MovieImage;
import com.yc.bean.MovieType;
import com.yc.bean.Type;
import com.yc.dao.UserMapper;
import com.yc.service.ActorRepository;
import com.yc.service.MovieRepository;


@Controller
public class SearchController {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private ActorRepository actorRepository;
	List<String> bList=null;
	List<List<String>> aList=null;
	List<String> cList=null;
	List<List<String>> dList=null;
	@Resource
	private MovieBiz mb;
	
//	@Autowired
//	UserMapper userMapper;
		
	
	@RequestMapping("search2" )
	public String search(Model m,String kw,String type,String pageNum) {

		aList=new ArrayList<>();
		dList=new ArrayList<>();
		PageInfo<Movie> a=null;	
		PageInfo<Actor> b=null;
			 a=mb.findByMovieName(kw,Integer.parseInt(pageNum));		
			 b=mb.findByActorName(kw,Integer.parseInt(pageNum));
			 System.out.println(a.getPageSize());
			 System.out.println("---------------------------------"+a);
			 for(int i=0;i<a.getSize();i++){		
				 Integer id=a.getList().get(i).getMovieId();
				List<MovieType> aid=mb.getTypeId(id);		
				bList = new ArrayList<>();
				List<Type> li=new ArrayList<>();
				for(int j=0;j<aid.size();j++){			
					 li=mb.getTypeName(aid.get(j).getTypeId());
					 bList.add(li.get(0).getName());
				}
				aList.add(bList);				
			 }
			 
			 for(int i=0;i<b.getSize();i++){
					Integer id= b.getList().get(i).getActorId();
					List<MovieActor> aid=mb.getMovieId(id);		
					cList = new ArrayList<>();
					List<Movie> li=new ArrayList<>();
					for(int j=0;j<aid.size();j++){
						if(j<3){
							
						
						 li=mb.getMovieName(aid.get(j).getMovieId());
						 
						 cList.add("《"+li.get(0).getName()+"》");
						}else{
							break;
						}
					}
					dList.add(cList);				
				 }
			 
		m.addAttribute("actormovie", dList);
		m.addAttribute("type", type);
		m.addAttribute("index", 0);
		m.addAttribute("kw", kw);
		 m.addAttribute("movie",a);
			m.addAttribute("movieType",aList);
			m.addAttribute("actor",b);
		return "pages/Search2";
	}
	
	@RequestMapping("search" )
	public String search2(Model m,String kw,String type,String pageNum) {
		String preTag = "<font color='#dd4b39'>";//google的色值
        String postTag = "</font>";
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		NativeSearchQueryBuilder queryBuilder2 = new NativeSearchQueryBuilder();
		movieRepository.deleteAll();
		actorRepository.deleteAll();
		aList=new ArrayList<>();
		dList=new ArrayList<>();
		List<Movie> a=null;	
		List<Actor> b=null;
			 a=mb.findByMovieName2(kw);		
			 b=mb.findByActorName2(kw);
			
			 if(a.size()>0){
				 movieRepository.saveAll(a);
			 }
			 if(b.size()>0){
				 actorRepository.saveAll(b);
			 }
			
			 
			 /**
			     * 组合查询
			     * must(QueryBuilders) :   AND
			     * mustNot(QueryBuilders): NOT
			     * should:                  : OR
			     */
			 
			 BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.wildcardQuery("name",  
			            "*"+kw+"*")).should(QueryBuilders.wildcardQuery("foreignName",  
					            "*"+kw+"*"));
			 
			 queryBuilder.withQuery(boolQueryBuilder);
			 queryBuilder.withPageable(PageRequest.of(Integer.parseInt(pageNum)-1,2));
			//设置高亮
		        queryBuilder.withHighlightFields(new HighlightBuilder.Field("name").preTags("<font color='red'>").postTags("</font>"),
		        		new HighlightBuilder.Field("foreignName").preTags("<font color='red'>").postTags("</font>"));
			 
			//使用es查询
			 AggregatedPage<Movie>aa= elasticsearchTemplate.queryForPage(queryBuilder.build(), Movie.class, new SearchResultMapper() {

		            @Override
		            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {

		                //从response中获取结果
		                SearchHits hits = response.getHits();
		                int size1=(int) hits.getTotalHits();
		                m.addAttribute("anum",size1);
		                m.addAttribute("apages", (size1%2)==0 ? (size1/2):(size1/2)+1); 
		                //定义一个集合
		                List<Movie> itemList = new ArrayList<>();
		                
		                //获取命中文本结果
		                //循环命中集合
		                for (SearchHit hit : hits) {
		                    //获取结果数据
		                    Map<String, Object> result = hit.getSourceAsMap();
		                    //创建商品对象
		                    Movie item = new Movie();
		                    MovieImage m=new MovieImage();
		                    
		                    //获取id
		                    String name0 = (String)result.get("name");
		                    String foreignName0 = (String)result.get("foreignName");
		                    Integer id = (Integer)result.get("movieId");
		                    String status = (String)result.get("status");
		                    Map<String, Object> movieImage = (Map<String, Object>) result.get("movieImage");
		                    Long releaseTime = (Long) result.get("releaseTime");
		                    Date date = new Date(releaseTime); 
		                    m.setType((String)movieImage.get("type"));
		                    m.setImage((String)movieImage.get("image"));
		                    //判断id是否为空
		                    if(id!=null){
		                        //设置id
		                        item.setMovieId(id);
		                        item.setStatus(status);
		                        item.setMovieImage(m);
		                        item.setReleaseTime(date);
		                    }
		                    
		                    //获取title
		                    HighlightField name = hit.getHighlightFields().get("name");
		                    HighlightField foreignName = hit.getHighlightFields().get("foreignName");
		                    //判断title不为空
		                    if(name!=null){
		                        //获取高亮值
		                        String name2 = name.getFragments()[0].toString();
		                        System.out.println("高亮字段："+name2);
		                        item.setName(name2);
		                    }else{
		                    	 item.setName(name0);
		                    }
		                    if(foreignName!=null){
		                        //获取高亮值
		                        String title2 = foreignName.getFragments()[0].toString();
		                        System.out.println("高亮字段："+title2);
		                        item.setForeignName(title2);
		                    }else{
		                    	item.setForeignName(foreignName0);
		                    }

		                    //将查询结果放入集合
		                    itemList.add(item);
		                }

		                //返回
		                if(itemList!=null && itemList.size()>0){
		                    return new AggregatedPageImpl(itemList);
		                }

		                //否则返回null
		                return null;
		            }
		        });
			 
			 
			 BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery().should(QueryBuilders.wildcardQuery("aname",  
			            "*"+kw+"*")).should(QueryBuilders.wildcardQuery("secondName",  
					            "*"+kw+"*"));
			 
			 queryBuilder2.withQuery(boolQueryBuilder2);
			 queryBuilder2.withPageable(PageRequest.of(Integer.parseInt(pageNum)-1,2));
			//设置高亮
		        queryBuilder2.withHighlightFields(new HighlightBuilder.Field("aname").preTags("<font color='red'>").postTags("</font>"),
		        		new HighlightBuilder.Field("secondName").preTags("<font color='red'>").postTags("</font>"));
			 
			//使用es查询
			 AggregatedPage<Actor>bb= elasticsearchTemplate.queryForPage(queryBuilder2.build(), Actor.class, new SearchResultMapper() {

		            @Override
		            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {

		                //从response中获取结果
		                SearchHits hits = response.getHits();
		               int size2=(int) hits.getTotalHits();
		               m.addAttribute("bnum",size2);
		               m.addAttribute("bpages", (size2%2)==0 ? (size2/2):(size2/2)+1); 
		                //定义一个集合
		                List<Actor> itemList = new ArrayList<>();
		                
		                //获取命中文本结果
		                //循环命中集合
		                for (SearchHit hit : hits) {
		                    //获取结果数据
		                    Map<String, Object> result = hit.getSourceAsMap();
		                    //创建商品对象
		                    Actor item = new Actor();
		                    
		                    //获取id
		                    Integer id = (Integer)result.get("actorId");
		                    String pic = (String)result.get("pic");
		                    String aname0 = (String)result.get("aname");
		                    String secondName0 = (String)result.get("secondName");

		                    //判断id是否为空
		                    if(id!=null){
		                        //设置id
		                        item.setActorId(id);
		                        item.setPic(pic);
		                    }
		                    
		                    //获取title
		                    HighlightField title = hit.getHighlightFields().get("aname");
		                    HighlightField secondName = hit.getHighlightFields().get("secondName");
		                    //判断title不为空
		                    if(title!=null){
		                        //获取高亮值
		                        String title2 = title.getFragments()[0].toString();
		                        System.out.println("高亮字段："+title2);
		                        item.setAname(title2);
		                    }else{
		                    	 item.setAname(aname0);
		                    }
		                    if(secondName!=null){
		                        //获取高亮值
		                        String title2 = secondName.getFragments()[0].toString();
		                        System.out.println("高亮字段："+title2);
		                        item.setSecondName(title2);
		                    }else{
		                    	item.setSecondName(secondName0);
		                    }

		                    //将查询结果放入集合
		                    itemList.add(item);
		                }

		                //返回
		                if(itemList!=null && itemList.size()>0){
		                    return new AggregatedPageImpl(itemList);
		                }

		                //否则返回null
		                return null;
		            }
		        });
			 
			 //Page<Movie> aa =movieRepository.search(queryBuilder.build());
			/* Page<Actor> bb =actorRepository.search(queryBuilder.
					 withHighlightFields(new HighlightBuilder.Field("name").preTags(preTag).postTags(postTag),
		                        new HighlightBuilder.Field("secondName").preTags(preTag).postTags(postTag)).build());*/
			if(aa!=null){
				
			
			 for(int i=0;i<aa.getContent().size();i++){	
				 System.out.println(aa.getSize());
				 Integer id=aa.getContent().get(i).getMovieId();
				List<MovieType> aid=mb.getTypeId(id);		
				bList = new ArrayList<>();
				List<Type> li=new ArrayList<>();
				for(int j=0;j<aid.size();j++){			
					 li=mb.getTypeName(aid.get(j).getTypeId());
					 bList.add(li.get(0).getName());
				}
				aList.add(bList);				
			 }
			}
			if(bb!=null){
				
			
			 for(int i=0;i<bb.getContent().size();i++){
					Integer id= bb.getContent().get(i).getActorId();
					List<MovieActor> aid=mb.getMovieId(id);		
					cList = new ArrayList<>();
					List<Movie> li=new ArrayList<>();
					for(int j=0;j<aid.size();j++){
						if(j<3){
							
						
						 li=mb.getMovieName(aid.get(j).getMovieId());
						 
						 cList.add("《"+li.get(0).getName()+"》");
						}else{
							break;
						}
					}
					dList.add(cList);				
				 }
			}
			
			
			 m.addAttribute("pageNum",pageNum);
			 
			 
		m.addAttribute("actormovie", dList);
		m.addAttribute("type", type);
		m.addAttribute("index", 0);
		m.addAttribute("kw", kw);
		 m.addAttribute("movie",aa);
			m.addAttribute("movieType",aList);
			m.addAttribute("actor",bb);
		return "pages/Search";
	}
	
}
