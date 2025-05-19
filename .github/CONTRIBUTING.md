# 기여 가이드

안녕하세요! 이 프로젝트에 관심을 가져주셔서 감사합니다.

이 문서는 프로젝트 간단한 안내서입니다.

---

## ✨ 기여 가능한 항목

- 버그 제보 및 수정
- 기능 추가 또는 개선
- 문서 개선 (README, 주석 등)
- 테스트 코드 추가
- 코드 리팩토링

---

## 👉문제
문제가 발생하면 [.github/TROUBLESHOOTING.md](.github/TROUBLESHOOTING.md)를 참고해주세요.

## 📦 프로젝트 세팅

1. 리포지토리를 포크(Fork)하세요
2. 로컬에 클론(Clone)하세요

```bash
git clone https://github.com/yourname/your-repo.git
cd your-repo
```

3. 필요한 패키지를 설치하고 빌드하세요

```bash
./gradlew build
```
---

## 🌱 브랜치 전략
본 프로젝트는 <b>GitHub Flow</b> 전략을 채택하고 있습니다. 

새로운 기능/수정 작업은 <b>main 브랜치</b>에서 분기해서 작업해주세요:

``` bash
# 새로운 기능
git checkout -b feature/기능명
# 버그 수정
git checkout -b fix/버그명
# 문서 수정
git checkout -b docs/문서명
```
완료된 작업은 반드시 main 브랜치로 <b>PR을 생성하여 코드 리뷰를 받아야 하며</b>, 승인 후 병합됩니다.

## 📮 커밋 메시지 컨벤션
### 종류

feat: | 새로운 기능 추가

fix: | 버그 수정

docs: | 문서 수정

style: | 코드 포맷팅, 세미콜론 등 비기능 수정

refactor: | 코드 리팩토링

test: | 테스트 코드 추가/수정

chore: | 빌드 설정, 패키지 매니저 설정 등

### 예시

``` bash
feat: 게시글 작성 API 추가
fix: 로그인 오류 처리 로직 수정
docs: README에 실행 방법 추가
```

## ✅ Pull Request 보내기
1. main 브랜치 기준으로 작업 브랜치를 만들어 주세요 
2. 작성 후 main으로 Pull Request를 생성해주세요 
3. PR 설명에 변경 내용, 테스트 방법 등을 명확하게 작성해주세요 
4. 리뷰어가 확인 후 머지됩니다 🎉

## 🧼 코드 스타일
1. Java 17 기반 (Gradle 프로젝트)
2. IDE에서 자동 포맷팅 권장 (IntelliJ 사용 시 Google Java Style 적용 가능)
3. 가능하면 Lombok 사용 시 @Builder, @Getter, @Slf4j 등 일관성 유지 
4. Google Java Style 또는 네이버 핵데이 Java 코딩 컨벤션 권장 
5. IntelliJ 사용 시 XML 포맷 적용 가능 
6. 개행 문자: LF (\n)

## ❗ 추가 사항
RegexpMultiline 관련 경고 발생 시 naver-checkstyle-suppressions.xml에 아래 설정을 추가하세요.
``` xml
<suppressions>
  <suppress files=".*" checks="RegexpMultiline" />
</suppressions>
```

## 🧾 주석 규칙
### 필수 주석 대상
Service, Repository, Global: JavaDocs 필수

DTO: Request 시 필수 (Constraint 설명 포함)

### 예시
``` java
/**
 * 게시물 관련 Repository입니다.
 * @author 홍길동
 */
@Repository
public interface PostRepository { ... }

/**
 * 게시물 추가 로직입니다.
 * @param post
 * @return 저장된 게시물 ID
 */
public Long createPost(Post post) { ... }
```

## 🔁 응답 구조
공통 응답 구조 사용: <b>BaseResponseWrapper<T></b>

``` java
public class BaseResponseWrapper<T> {...}
```
``` java 
BaseResponseWrapper.ok(SuccessCode.SUCCESS, response);
```

## ⚙️ 클래스 및 메서드 작성 규칙
1. 메서드 네이밍은 동사 + 명사 방식 사용 (예: addNumber() ✅ / numberAdd() ❌)

2. CRUD 메서드는 다음 키워드 사용: create, find, modify, delete
``` java
public class PostCreateRequest(String title, String content) {...}
```

## 🔄 트랜잭션 처리
트랜잭션 어노테이션은 메서드 단위로 적용

복잡한 경우, 팀원과 사전 협의 필수

## 📬 문의
기여 중 문의사항이 있으면 이슈에 자유롭게 남겨주세요!

혹은 이메일: <b>jho951@naver.com</b>