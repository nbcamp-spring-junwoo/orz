package com.junwoo.ott.global.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.junwoo.ott.global.exception.custom.ElasticException;
import com.junwoo.ott.global.search.dto.body.SearchDto;
import com.junwoo.ott.global.search.dto.response.SearchResponseDto;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchService {

  private final ElasticsearchClient esClient;

  private final String INDEX = "videos";
  private final String FIELD = "title";
  private final String AGG_MOVIE_NAME = "movie_name";
  private final int SIZE = 7;

  public SearchResponseDto search(final String input) {
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

  private SearchRequest autoSearch(final String input) {

    return new SearchRequest.Builder()
        .index(INDEX)
        .size(SIZE)
        .query(
            q -> q.matchPhrasePrefix(
                m -> m.field(FIELD).query(input)
            )
        )
        // 집계를 통해 중복된 내용을 걸러주기 위해서 사용했습니다. (group by 같은 느낌)
        .aggregations(AGG_MOVIE_NAME, a -> a.terms(
            v -> v.field("title.keyword")
        ))
        .build();
  }

}
