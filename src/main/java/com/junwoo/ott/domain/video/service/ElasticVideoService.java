package com.junwoo.ott.domain.video.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhrasePrefixQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import com.junwoo.ott.domain.video.dto.body.VideoSearchDto;
import com.junwoo.ott.domain.video.dto.request.VideoSearchRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoSearchResponseDto;
import com.junwoo.ott.global.exception.custom.ElasticException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ElasticVideoService {

  private final ElasticsearchClient esClient;

  private final String FIELD_TYPE = "title";
  private final String INDEX = "videos";
  private final String SORT_TYPE = "created_at";
  private final int SIZE = 10;

  public VideoSearchResponseDto getVideosElasticSearch(VideoSearchRequestDto dto) {
    SearchRequest searchRequest = createSearchRequest(dto.getTitle(), dto.getPage());

    SearchResponse<VideoSearchDto> response;

    try {
      response = esClient.search(searchRequest, VideoSearchDto.class);
    } catch (ElasticsearchException | IOException e) {
      throw new ElasticException("ElasticSearch에 문제가 발생했습니다.");
    }

    TotalHits hits = response.hits().total();
    List<VideoSearchDto> result = response.hits().hits().stream().map(Hit::source).toList();

    return new VideoSearchResponseDto(result, hits.value(), (hits.value() / SIZE) + 1);
  }

  private SearchRequest createSearchRequest(final String input, final int page) {

    MatchPhrasePrefixQuery matchQuery = QueryBuilders.matchPhrasePrefix()
        .field(FIELD_TYPE)
        .query(input)
        .build();

    return new SearchRequest.Builder()
        .index(INDEX)
        .from((page - 1) * SIZE)
        .size(SIZE)
        .query(matchQuery._toQuery())
        .sort(s -> s.field(
            f -> f.field(SORT_TYPE).order(SortOrder.Desc)))
        .build();
  }

}
