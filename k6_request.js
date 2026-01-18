import http from 'k6/http';

// 테스트 옵션 설정
export const options = {
    vus: 100,          // 동시에 실행할 가상 사용자 수 (동시 요청)
    iterations: 100,   // 전체 요청 횟수
};

// 테스트 시나리오
export default function () {
    // 3.4 ~ 3.5
    // http.post('http://localhost:8080/variable/stock');

    // 3.6
    // http.post('http://localhost:8080/variable/stock-atomic');

    // 4.8 ~ 4.10
    // http.post('http://localhost:8080/basic/stock');

    // 4.11
    // http.post('http://localhost:8080/basic/stock-tran');

    // 4.13
    // http.post('http://localhost:8080/basic/stock-isolation');

    // 4.14
    // http.post('http://localhost:8080/opti/stock');

    // 4.15
    // http.post('http://localhost:8080/pessi/stock');

    // 5.18
    // http.post('http://localhost:8080/basic/stock-distributed');
    // http.post('http://localhost:8081/basic/stock-distributed');

    // 5.19
    // 낙관적 락 사용예제
    // http.post('http://localhost:8080/opti/stock');
    // http.post('http://localhost:8081/opti/stock');
    // 비관적 락 사용예제
    // http.post('http://localhost:8080/pessi/stock');
    // http.post('http://localhost:8081/pessi/stock');

    // 5.21
    http.post('http://localhost:8080/basic/stock-redisson');
    http.post('http://localhost:8081/basic/stock-redisson');
}