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
  private final int SIZE = 7;

  public SearchResponseDto search(final String input) {
    SearchResponse<SearchDto> response;

    try {
      response = esClient.search(autoSearch(input), SearchDto.class);
    } catch (ElasticsearchException | IOException e) {
      throw new ElasticException("ElasticSearch에 문제가 발생했습니다.");
    }

    List<String> array = response.aggregations()
        .get("unique_name")
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
        .aggregations("unique_name", a -> a.terms(
            v -> v.field("title.keyword")
        ))
        .build();
  }

}
