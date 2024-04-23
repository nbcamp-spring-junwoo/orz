package com.junwoo.ott.global.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import com.junwoo.ott.global.exception.custom.ElasticException;
import com.junwoo.ott.global.search.dto.body.SearchDto;
import com.junwoo.ott.global.search.dto.request.VideoSearchRequestDto;
import com.junwoo.ott.global.search.dto.response.SearchResponseDto;
import com.junwoo.ott.global.search.dto.response.VideoRandomResponseDto;
import com.junwoo.ott.global.search.dto.response.VideoResponseDto;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchService {

  private final ElasticsearchClient esClient;

  private final String INDEX = "videos";
  private final String FIELD = "title";
  private final String AGG_MOVIE_NAME = "movie_name";
  private final String POSTER_URL = "poster_url";
  private final int AUTO_SIZE = 7;
  private final int SIZE = 10;

  public SearchResponseDto titleSearch(final String input) {
    SearchResponse<SearchDto> response;

    try {
      response = esClient.search(autoSearch(input), SearchDto.class);
    } catch (ElasticsearchException | IOException e) {
      throw new ElasticException("ElasticSearch에 문제가 발생했습니다.");
    }

    // 집계된 필드 "unique_name"을 통해 버킷 안의 key값을 빼오려고 하는 메서드입니다.
    List<String> array = response.aggregations()
        .get(AGG_MOVIE_NAME)
        .sterms()
        .buckets()
        .array()
        .stream()
        .map(m -> m.key().stringValue())
        .toList();

    return new SearchResponseDto(array);
  }

  public Page<VideoResponseDto> getVideosElasticSearch(VideoSearchRequestDto dto) {
    SearchRequest searchRequest = createSearchRequest(dto.getInput(), dto.getPageable()
        .getPageNumber());

    SearchResponse<VideoResponseDto> response;

    try {
      response = esClient.search(searchRequest, VideoResponseDto.class);
    } catch (ElasticsearchException | IOException e) {
      throw new ElasticException("ElasticSearch에 문제가 발생했습니다.");
    }

    TotalHits hits = response.hits().total();
    List<VideoResponseDto> result = response.hits().hits().stream().map(Hit::source).toList();

    return PageableExecutionUtils.getPage(result, dto.getPageable(), hits::value);
  }

  public VideoRandomResponseDto getRandomVideosElasticSearch() {
    SearchRequest searchRequest = randomSearch();

    SearchResponse<VideoResponseDto> response;

    try {
      response = esClient.search(searchRequest, VideoResponseDto.class);
    } catch (ElasticsearchException | IOException e) {
      throw new ElasticException("ElasticSearch에 문제가 발생했습니다.");
    }

    List<VideoResponseDto> result = response.hits().hits().stream().map(Hit::source).toList();

    return new VideoRandomResponseDto(result);
  }

  private SearchRequest createSearchRequest(final String input, final Integer page) {

    return new SearchRequest.Builder()
        .index(INDEX)
        .from(page * SIZE)
        .size(SIZE)
        .query(
            q -> q.matchPhrasePrefix(
                m -> m.field(FIELD).query(input)
            )
        )
        .build();
  }


  private SearchRequest autoSearch(final String input) {

    return new SearchRequest.Builder()
        .index(INDEX)
        .size(0)
        .query(
            q -> q.matchPhrasePrefix(
                m -> m.field(FIELD).query(input)
            )
        )
        // 집계를 통해 중복된 내용을 걸러주기 위해서 사용했습니다. (group by 같은 느낌)
        .aggregations(AGG_MOVIE_NAME, a -> a.terms(
            v -> v.field("title.keyword").size(AUTO_SIZE)
        ))
        .build();
  }

  private SearchRequest randomSearch() {

    return new SearchRequest.Builder()
        .index(INDEX)
        .size(SIZE)
        .query(
            q -> q.bool(
                b -> b.mustNot(
                    mn -> mn.match(
                        m -> m.field(POSTER_URL).query("None")
                    )
                ).must(
                    m -> m.functionScore(
                        f -> f.functions(o -> o.randomScore(r -> r))
                            .query(y -> y.matchAll(v -> v))
                    )
                )
            )
        )
        .build();
  }

}
