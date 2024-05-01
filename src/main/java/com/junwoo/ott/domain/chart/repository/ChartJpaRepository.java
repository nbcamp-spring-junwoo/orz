package com.junwoo.ott.domain.chart.repository;

import com.junwoo.ott.domain.chart.entity.Chart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartJpaRepository extends JpaRepository<Chart, Long>, ChartQueryRepository {

}
