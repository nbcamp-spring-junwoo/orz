package com.junwoo.ott.domain.coupon.repository.queryDsl;

import static com.junwoo.ott.domain.coupon.entity.QCouponIssuance.couponIssuance;

import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CouponIssuanceQueryDslImpl implements CouponIssuanceQueryDsl {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<CouponIssuance> getCouponIssuance(final Long userId, final Pageable pageable) {

    List<CouponIssuance> result = jpaQueryFactory.select(couponIssuance)
        .from(couponIssuance)
        .where(notUsedAndUserIdEq(userId))
        .orderBy(couponIssuance.issuedAt.desc())
        .fetch();

    JPAQuery<Long> count = jpaQueryFactory
        .select(couponIssuance.count())
        .from(couponIssuance);

    return PageableExecutionUtils.getPage(result, pageable, count::fetchOne);
  }

  @Override
  public List<CouponIssuance> getCouponIssuance(final LocalDate localDate) {

    return jpaQueryFactory.select(couponIssuance)
        .from(couponIssuance)
        .where(couponEndAtExpired(localDate))
        .fetch();
  }

  private BooleanExpression userIdEq(final Long userId) {
    return userId != null ? couponIssuance.user.userId.eq(userId) : null;
  }

  private BooleanExpression notUsedAndUserIdEq(final Long userId) {
    return couponIssuance.usedAt.isNull().and(userIdEq(userId));
  }

  private BooleanExpression couponEndAtExpired(final LocalDate localDate) {
    return couponIssuance.usedAt.isNull().and(couponIssuance.coupon.endAt.lt(localDate));
  }

}
