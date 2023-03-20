# Blog-search

__블로그 검색 API:__
```
curl --location --request GET 'http://localhost:8080/v1/search/blog?query=kakao&provider=kakao&sort=ACCURACY&page=0&size=15'
```

__인기 검색어 목록 API:__

```
curl --location --request GET 'http://localhost:8080/v1/popular-keywords'
```

> 새롭게 검색한 키워드에 대해서 인기 검색어 순위 반영은 1분마다 배치를 통해 갱신하고 있습니다. 

## Gradle Multi Module

- __api__: API 모듈
- __core__: 다른 서브 모듈들에서 공통으로 사용하는 코드를 관리
- __domain__: 도메인 모듈
- __infra__: 인프라스트럭쳐 모듈 
- __batch__: 배치 모듈

## 블로그 검색 및 인기 검색어 목록 처리

__블로그 검색:__
1. 사용자는 GET /v1/search/blog 를 통해 블로그를 검색합니다.
2. 사용자의 ip 와 keyword 를 포함한 로그를 남깁니다.
3. 페이지를 적용하여 응답 결과를 내줍니다.
4. 정렬은 accuracy or recency 로 입력하면 됩니다.

__인기 검색어:__
1. batch 모듈에서 (지정한 시간 마다) 로그 파일을 읽습니다.
2. 로그 파일(blog.log)을 읽어다가 파싱합니다.
3. 파싱된 결과를 PopularSearchKeyword 테이블에 저장합니다.

> 현재는 설정한 시간마다 파일을 읽지만, 이전에 쌓인 로그도 다시 읽기 때문에 실무에서는 이 부분에 대한 개선이 필요합니다.

__인기 검색어 목록:__
1. 사용자는 GET /v1/search/popular-keyword 를 통해 인기 검색어 목록을 조회할 수 있습니다.

> Message 같은 Event 로 처리하지 않은 이유는 읽기 조회 한 번당 메시지 발급 비용 or Insert 비용이 발생하기 때문에 처리하지 않았습니다.

## 대용량 트래픽 고려

- 특정 키워드에 대한 통계를 구해야하는 경우에는 키워드에 인덱스를 걸어줍니다.
- api 모듈과 batch 모듈은 bootJar = true 옵션으로 K8S 의 별도의 POD 으로 올라갑니다.
- batch 모듈에서는 master 로 데이터 삽입을 진행하고, 검색 트래픽이 많아서 읽기 처리량을 올릴려면 Replication 진행하면 됩니다.

> 현재 소스는, In-memory DB 제약 사항으로 batch 를 api 애플리케이션과 같이 묶어서 올리는 방향으로 과제 진행
