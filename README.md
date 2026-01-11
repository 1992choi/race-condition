# 강의
- Inflearn > 가장 쉬운 동시성 문제 - Race Condition

## 프로젝트 기본설정
### k6
- 설치
  - brew install k6
- 버전확인 (설치확인 목적)
  - k6 version
- 사용방법
  - k6 run k6_request.js (프로젝트 루트에서 실행)

## 개념정리
### 동시성 문제
- 동시성 문제란?
  - 여러 스레드 또는 프로세스가 동시에 같은 자원(데이터, 메모리 등)에 접근하면서 발생하는 문제
- 동시성 문제의 종류
  - Race Condition (경쟁 상태)
    - 둘 이상의 스레드가 동시에 같은 데이터에 접근했을 때, 어느 스레드가 먼저 처리하느냐에 따라 결과가 달라지는 상황
      - 예) 재고가 1개인 상품에 두 명의 고객이 동시에 구매를 시도했을 때, 두 명 모두 구매에 성공
    - Lost Update가 발생한다
  - Deadlock (교착 상태)
    - 서로 자원을 점유한 상태에서 상대방 자원이 사용 가능 상태가 되기를 계속 기다림
- 원인과 해결책
  - 근본적인 원인
    - 여러 스레드(또는 트랜잭션)가 공유 자원에 대해 동시에 접근하면서, 그 접근을 제어하지 않기 때문
  - 해결방법
    - 공유 자원에 대한 접근 순서를 강제하여 예측 가능한 결과를 만든다

### 공유변수에서의 동시성 문제
- 3.4. 실습에서는 왜 동시성 이슈가 발생할까?
  - 공유 변수를 증가시키는 연산은 원자적(atomic)이지 않기 때문
    - 실제는 아래와 같은 단계로 나뉘어서 수행된다.
      1. 변수 읽기
      2. 값 증가 ( +1 )
      3. 값을 변수에 저장
    - 두 개 이상의 스레드가 동시에 이 연산을 수행하면, 한 스레드의 결과가 덮어씌워지는 **Lost Update** 현상이 발생할 수 있다.
- 해결방법
  -  락(Lock)을 건다
    - 여러 스레드나 프로세스가 동시에 하나의 공유 자원에 접근하지 못하도록, 특정 시점에 하나만 접근 가능하게 제어
  - 락을 거는 방법
    - synchronized 사용
      - Java에서 스레드 동기화를 위해 사용하는 키워드
      - 하나의 스레드만 특정 코드 블록 또는 메서드에 접근할 수 있도록 제어할 수 있는 키워드이다.
      - `synchronized` 키워드를 사용하면 JVM이 락을 자동으로 관리하면서, 스레드 간의 동시성 문제(레이스 컨디션)를 자동으로 방지할 수 있음
    - ReentrantLock 사용
      - 한 스레드가 락을 잡은 상태에서 그 락에 다시 진입(enter)해도 허용하는 락
    - Atomic 클래스 사용
      - Java에서 제공하는 원자적(atomic) 연산을 제공하는 유틸리티 클래스
      - 내부적으로 CAS(Compare-And-Set) 연산을 이용해 락 없는 동기화(lock-free synchronization)를 지원
        - CAS (Compare-And-Swap)
          - CPU 수준에서 제공하는 원자적 연산
          - 특정 메모리 값이 기대값과 같을 경우, 새 값으로 원자적으로 교체
- synchronized / ReentrantLock / Atomic 비교 정리
  - synchronized
    - 원리 : JVM 내부 락
    - 장/단점 : 구현이 간단하다 / 스레드 중지로 인한 성능 저하 가능
  - ReentrantLock
    - 원리 : 수동 락 제어
    - 장/단점 : 세밀한 제어 / unlock 관리 필요
  - Atomic
    - 원리 : CAS 기반
    - 장/단점 : 빠름 (lock-free) / 단일 변수 한정
      - lock-free란?
        - 락을 걸지 않고도 공유 자원을 안전하게 조작하는 기술
        - 주로 CAS 기반 알고리즘으로 구현
        - lock은 임계 영역에 대한 배타적 접근을 강제하여 동시성을 제한하고, lock-free는 동시 접근 경쟁을 허용하되 블로킹 없이 진행을 보장한다

### @Transactional과 (synchronized 혹은 ReentrantLock) 동시사용
- synchronized 혹은 ReentrantLock만 사용했을 때는 경쟁이 발생하지 않는다.
  - 하지만 @Transactional을 붙이는 순간 경쟁이 발생하게 된다.
- 정리
  - @Transactional을 붙이면 save() 시점에 즉시 UPDATE가 실행되지 않고, 트랜잭션 커밋 시점에 flush 된다. 
  - 그 사이에 락은 이미 해제되고 DB에는 아직 반영되지 않은 상태가 된다. 
  - 다음 스레드는 이전 값을 다시 조회하게 되고, 그 결과 값 유실(Lost Update)이 발생한다. 
  - synchronized나 ReentrantLock은 자바 코드 실행만 보호하며 DB 커밋 시점까지는 보호하지 못한다. 
  - 공유 자원이 DB라면 JVM 락이 아니라 DB 레벨의 동시성 제어가 필요하다.

## 실습
### 3.4. 실습 환경 만들기
- 순서
  - VariableService.java > 3.4. 코드 주석 해제
  - k6 실행 > test_request.http로 카운트 조회
- 결과
  - 1000번 호출됐지만, 조회되는 값은 1000이 아님
- 정리
  - 동시성에 대한 고려를 하지 않으면, 경쟁 상태로 인하여 의도하지 않은 결과를 초래할 수 있다.

### 3.5. 공유 변수에 사용하는 synchronized, ReentrantLock
- 순서
  - VariableService.java > 3.5. 코드 주석 해제
  - k6 실행 > test_request.http로 카운트 조회
- 결과
  - 1000번 호출 -> 조회되는값은 1000
- 정리
  - synchronized 혹은 ReentrantLock을 사용하면, 정확히 1000이 증가한다.

### 3.6. Atomic 클래스
- 순서
  - k6 실행 > test_request.http로 카운트 조회
- 결과
  - 1000번 호출 -> 조회되는값은 1000
- 정리
  - Atomic 클래스를 사용하면, 정확히 1000이 증가한다.

### 4.8. 실습 환경 만들기
- 순서
  - product_entity 테이블에 데이터 밀어넣기 (id=1 / stock=0) 
  - k6 실행 > DB로 데이터 확인
- 결과
  - 1000번 호출 -> 조회되는 값은 1000이 아님
- 정리
  - 동시성에 대한 고려를 하지 않으면, 경쟁 상태로 인하여 의도하지 않은 결과를 초래할 수 있다.

### 4.9. 공유 DB에 사용하는 synchronized, ReentrantLock
- 순서
  - product_entity 테이블에 데이터 밀어넣기 (id=1 / stock=0)
  - k6 실행 > DB로 데이터 확인
- 결과
  - 1000번 호출 -> 조회되는값은 1000
- 정리
  - synchronized 혹은 ReentrantLock을 사용하면, 정확히 1000이 증가한다.

### 4.10. Transactional 애노테이션 이해하기
- 순서
  - product_entity 테이블에 데이터 밀어넣기 (id=1 / stock=0)
  - k6 실행 > DB로 데이터 확인
- 결과
  - 1000번 호출 -> 조회되는 값은 1000이 아님
- 정리
  - @Transactional 어노테이션과 synchronized 혹은 ReentrantLock을 사용하면, 다시 경쟁이 발생한다.