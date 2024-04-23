package com.junwoo.ott.global.aop;

import com.junwoo.ott.global.exception.custom.CustomLockException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j(topic = "LockAspect")
@Aspect
@AllArgsConstructor
@Component
public class LockAspect {

  private final RedissonClient redissonClient;

  @Around("@annotation(lockable)")
  public Object applyLock(final ProceedingJoinPoint joinPoint, final Lockable lockable) throws Throwable {
    RLock lock = redissonClient.getFairLock(lockable.value());

    try {
      boolean isLockSuccess = lock.tryLock(
          lockable.waitTime(),
          lockable.leaseTime(),
          lockable.timeUnit()
      );
      log.info("Lock 획득 Success");

      if (!isLockSuccess) {
        throw new CustomLockException("Lock 획득 Fail");
      }

      Object result = joinPoint.proceed();

      if (lock.isHeldByCurrentThread()) {
        log.info("Lock 해제 Success");
        lock.unlock();
      } else {
        log.info("메서드 실행할 때 너무 오래 걸려서 누가 내 lock을 가져감");
        log.info("Lock 해제 Fail");
      }
      return result;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new CustomLockException("Interrupt 발생");
    } catch (RuntimeException e) {

      if (lock.isHeldByCurrentThread()) { // 락이 현재 내 스레드 일 때만 해제를 한다.
        log.info("현재 내 스레드에서 예외가 발생해서 Lock을 해제");
        lock.unlock();
      } else {
        log.info("남의 스레드에서 Lock이 가지고 있는 상태임..");
        log.info("개빡침 그냥 안함");
      }

      throw e;
    }
  }

}
