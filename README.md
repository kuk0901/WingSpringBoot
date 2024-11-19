# WING\_ - 가계부 웹 애플리케이션 프로젝트

WING\_은 사용자의 소비 습관을 분석하고 개선하는 것을 목표로 하는 웹 기반 가계부 애플리케이션 프로젝트입니다.

## 목차

1. [프로젝트 개요](#프로젝트-개요)
2. [특징](#특징)
3. [기술 스택](#기술-스택)
4. [팀 정보](#팀-정보)
5. [프로젝트 목표](#프로젝트-목표)
6. [개발 과정 및 도전 과제](#개발-과정-및-도전-과제)
7. [설치 및 실행 방법](#설치-및-실행-방법)
8. [프로젝트 구조](#프로젝트-구조)
9. [이미지 파일 설정 가이드](#이미지-파일-설정-가이드)
10. [데이터베이스 설정 가이드](#데이터베이스-설정-가이드)
11. [라이선스](#라이선스)

### ecipse-version 브랜치는 main 브랜치보다 업데이트가 늦게 일어날 수 있습니다.

## 프로젝트 개요

- **목적**: 웹 개발 기술 학습 및 실제 애플리케이션 개발 경험 획득
- **주요 기능**:
  - 가계부 관리 (카테고리 제공)
  - 소비 데이터 분석
  - 가상의 맞춤형 카드 추천 시스템
  - 카드 종류 조회

![메인 화면](https://raw.githubusercontent.com/kuk0901/WingSpringBoot/project-documents/img/sampleUI/signin.png)
![회원 가계부 화면](https://raw.githubusercontent.com/kuk0901/WingSpringBoot/project-documents/img/sampleUI/user_main.png)
![카드 추천 화면](https://raw.githubusercontent.com/kuk0901/WingSpringBoot/project-documents/img/sampleUI/user_mypage.png)
![관리자 대시보드 화면](https://raw.githubusercontent.com/kuk0901/WingSpringBoot/project-documents/img/sampleUI/admin_main.png)

- **개발 중점**: 실제 서비스와 유사한 환경의 웹 애플리케이션 구현

## 특징

- 사용자 중심의 UI/UX 설계 및 구현
- 데이터 분석 및 추천 시스템 로직 개발
- 관리자 페이지를 통한 서비스 관리 기능 구현

## 기술 스택

- Frontend: JSP, CSS, JavaScript
- Backend: Java, MyBatis, Maven
- Database: Oracle
- 개발 도구: IntelliJ IDEA, SQL Developer
- UI 디자인: Figma

## 팀 정보

- 팀명: We are ING
- 팀원

  - 국하현(팀장): https://github.com/kuk0901
    - 역할: 팀장, 깃허브/문서 관리자, 프론트엔드 통합
    - 담당 기능: 공통 UI/Utils, 인증 페이지, 카드 관리/판매 카드 현황(관리자), 카드 종류/신청(회원), 마이페이지 카드 추천/소비 백분율(회원)

  <br />

  - 김민재: https://github.com/kimminje1
    - 역할: 백엔드 통합
    - 담당 기능: 디렉터리 구조 설계, 회원/가계부 관리(관리자), 마이페이지/가계부(회원)

  <br />

  - 김진우: https://github.com/kimjinwoo97
    - 역할: DBA
    - 담당 기능: DB 설계 및 ERD 제작, 카테고리/결제수단/게시판/고객센터 관리(관리자), 게시판/고객센터(회원)

## 프로젝트 목표

이 프로젝트는 실제 서비스 출시보다는 팀원들의 웹 개발 기술 향상과 실제 프로젝트 경험 축적에 중점을 두고 있습니다. 주요 목표는 다음과 같습니다:

1. 실제 사용자 요구사항을 반영한 가계부 애플리케이션의 주요 기능 구현
2. 데이터베이스 설계, 백엔드 로직 개발, 프론트엔드 구현 등 전반적인 웹 개발 과정 경험
3. 팀 협업 능력 향상 및 프로젝트 관리 경험 축적
4. 최신 웹 개발 기술 및 프레임워크 활용 능력 강화
5. 사용자 중심의 UI/UX 설계 및 구현 능력 개발
6. 데이터 분석 및 시각화 기능 구현을 통한 실무 응용 능력 향상

이 프로젝트를 통해 팀원들은 실무와 유사한 환경에서의 협업과 문제 해결 능력을 강화하며, 다양한 기술을 적용하여 전반적인 기술 역량을 향상하고 향후 경력에 도움이 될 중요한 경험을 쌓았습니다.

## 개발 과정 및 도전 과제

### 1. 효율적인 협업 워크플로우 구축

#### 경험한 어려움

- 팀원 간 코드 충돌 최소화 및 효율적인 협업 방식 확립

#### 해결 방법

- Git 브랜치 전략 도입:
  - main 브랜치: 최종 배포용 코드 관리
  - project-documents 브랜치: 프로젝트 문서화 및 정리
  - project-base-XX 브랜치: 주요 개발 작업 (XX는 버전 번호 영문화)
  - project-base-XX-test 브랜치: 테스트 환경 구성 및 테스트 진행
- Pull Request(PR) 활용: 코드 리뷰 및 병합 과정의 체계화
- 지속적인 팀 커뮤니케이션: 코드 충돌 발생 시 즉각적인 소통으로 문제 해결

#### 학습 내용

- Git을 활용한 효과적인 버전 관리 및 팀 협업 프로세스 개선 방법

### 2. 데이터베이스 설계 최적화

#### 경험한 어려움

- 복잡한 비즈니스 로직을 반영한 효율적인 ERD 설계

#### 해결 방법

- 반복적인 ERD 수정 및 검토: 팀원 간 지속적인 피드백을 통한 ERD 개선
- 정규화 과정 적용: 데이터 중복 최소화 및 일관성 확보

#### 학습 내용

- 실제 서비스에 적합한 데이터베이스 모델링 기법 및 최적화 방법

### 3. 역할 분담 및 기능 구현

#### 경험한 어려움

- 팀원 개개인의 역량을 고려한 효과적인 업무 분담

#### 해결 방법

- 개인별 강점 분석: 각 팀원의 기술 스택과 경험을 고려한 역할 할당
- 단계별 기능 구현: 핵심 기능부터 순차적으로 개발하여 진행 상황 관리

#### 학습 내용

- 팀 프로젝트에서의 효과적인 업무 분배 및 진행 관리 방법

### 4. UI/UX 설계 및 구현

#### 경험한 어려움

- 사용자 친화적이면서도 기능적인 UI/UX 설계

#### 해결 방법

- Figma를 활용한 프로토타이핑: 실제 구현 전 디자인 검토 및 수정
- 데이터 유무에 따른 UI 분기: 다양한 상황을 고려한 유연한 UI 설계

#### 학습 내용

- 사용자 중심의 UI/UX 설계 프로세스 및 프로토타이핑 도구 활용 기술

## 설치 및 실행 방법

1. 프로젝트 클론:

> 에디터에 따라 `애플리케이션 실행` 부분을 참고해 사용해 주세요.

```shell
git clone https://github.com/kuk0901/WingSpringBoot.git
cd WingSpringBoot
```

2. 데이터베이스 설정:

- [데이터베이스 설정 가이드](#데이터베이스-설정-가이드) 섹션을 참조하여 Oracle 데이터베이스를 설정합니다.

3. 이미지 파일 설정:

- [이미지 파일 설정 가이드](#이미지-파일-설정-가이드) 섹션을 참조하여 필요한 이미지 파일을 설정합니다.

4. 프로젝트 빌드:

```shell
mvn clean install
```

5. 애플리케이션 실행:

- IntelliJ IDEA를 사용하는 경우:

  1. main 브랜치를 이용한 방법:
     ```shell
     git clone https://github.com/kuk0901/WingSpringBoot.git
     ```

  - IntelliJ IDEA에서 File > Open을 선택하고 클론한 프로젝트 폴더를 선택합니다.
  - WingApplication 클래스를 찾아 우클릭 후 'Run WingApplication'을 선택합니다.

  <br />

  2. ZIP 파일을 이용한 방법:

  - GitHub에서 프로젝트를 ZIP 파일로 다운로드합니다.
  - IntelliJ IDEA에서 File > New > Project from Existing Sources를 선택합니다.
  - 압축 해제한 프로젝트 폴더를 선택하고 'Open'을 클릭합니다.
  - Import project from external model에서 Maven을 선택하고 'Finish'를 클릭합니다.
  - WingApplication 클래스를 찾아 우클릭 후 'Run WingApplication'을 선택합니다.

<br />

- Eclipse를 사용하는 경우:

  1. eclipse-version 브랜치를 이용한 방법:
     ```shell
     git clone -b eclipse-version https://github.com/kuk0901/WingSpringBoot.git
     ```

  - Eclipse에서 File > Import > General > Existing Projects into Workspace 선택
  - 클론한 프로젝트 디렉터리를 선택하고 'Finish' 클릭
  - 프로젝트 우클릭 > Run As > Spring Boot App 선택

  <br />

  2. ZIP 파일을 이용한 방법:

  - GitHub에서 eclipse-version 브랜치의 프로젝트를 ZIP 파일로 다운로드합니다.
  - Eclipse에서 File > Import > General > Existing Projects into Workspace 선택
  - "Select archive file"을 선택하고 다운로드 받은 ZIP 파일을 지정합니다.
  - 프로젝트를 선택하고 'Finish' 클릭
  - 프로젝트 우클릭 > Run As > Spring Boot App 선택

- 또는 명령줄에서:
  ```shell
  ./mvnw spring-boot:run
  ```

6. 브라우저에서 `http://localhost:8888` 접속

## 프로젝트 구조

이 프로젝트의 디렉터리 구조는 다음과 같습니다. 각 디렉터리는 특정한 역할을 수행하며, 전체 애플리케이션의 기능을 지원합니다.

```
.
├── README.md
├── docs
│   ├── images
│   │   ├── WingCardDaily.png
│   │   ├── WingCardShopping.png
│   │   └── WingCardTraffic.png
│   └── sql
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   ├── resources
│   │   └── webapp
│   └── test
│       └── java
└── target
    ├── classes
    │   ├── application.yaml
    │   ├── com
    │   ├── logback.xml
    │   ├── mappers
    │   ├── mybatis-config.xml
    │   └── static
    ├── generated-sources
    │   └── annotations
    ├── generated-test-sources
    │   └── test-annotations
    └── test-classes
        └── com
```

### 주요 디렉토리

- **src/main**: 애플리케이션의 주요 코드와 리소스가 위치합니다.
- **src/test**: 테스트 코드가 포함되어 있으며, 품질 보증을 위한 테스트를 수행합니다.
- **target**: 빌드 결과물이 저장되는 디렉터리입니다.

이와 같이 간단하게 설명하면 포트폴리오의 가독성을 높이고, 독자가 프로젝트의 구조를 이해하는 데 도움이 될 것입니다. 필요에 따라 설명의 깊이를 조정하시면 됩니다!

## 이미지 파일 설정 가이드

WING\_ 애플리케이션은 카드 이미지를 저장하기 위해 특정 디렉터리를 사용합니다. 애플리케이션을 처음 실행하면 이 디렉터리가 자동으로 생성되지만, 초기 데이터 설정을 위해 미리 이미지를 준비해 두는 것이 좋습니다.

### 이미지 파일 위치

- Windows: `C:\wing_files\`
- macOS/Linux: `~/wing_files/` (사용자 홈 디렉터리 내)

### 설정 단계

1. 위의 경로에 `wing_files` 디렉터리를 수동으로 생성합니다.

2. 프로젝트 doc/images 폴더에 미리 준비된 카드 이미지 파일(.jpg, .jpeg, 또는 .png)을 이 디렉터리에 복사합니다.

3. 이미지 파일명을 데이터베이스의 카드 정보와 일치하도록 변경합니다.
   예: `WingCardTraffic.png`, `WingCardShopping.png`, `WingCardDaily.png`

4. 애플리케이션을 실행하면, 이 디렉터리에 있는 이미지들을 자동으로 인식하고 사용합니다.

주의: 이미지 파일은 .jpg, .jpeg, 또는 .png 형식이어야 하며, 다른 형식의 파일은 자동으로 .png로 처리됩니다.

이 설정을 통해 애플리케이션 실행 시 초기 카드 이미지를 즉시 사용할 수 있습니다.

## 데이터베이스 설정 가이드

WING\_ 애플리케이션은 Oracle 데이터베이스를 사용합니다. 다음 단계를 따라 데이터베이스를 설정하세요.

### 필요 도구

- SQL Developer (또는 다른 Oracle 데이터베이스 관리 도구)

### 설정 단계

1. SQL Developer를 실행합니다.

2. 새로운 데이터베이스 연결을 생성합니다:

- 사용자 이름: wing
- 비밀번호: 1234
- 호스트 및 포트: (your_host:your_port)
- SID 또는 서비스 이름: (your_sid_or_service_name)

3. 'docs/sql' 폴더에서 'WING\_ DB DOC.sql' 파일을 찾습니다.

4. SQL Developer에서 wing/1234 계정으로 접속합니다.

5. 찾은 SQL 파일을 열고 전체 내용을 실행합니다.

- 이 스크립트는 필요한 모든 테이블과 시퀀스를 생성합니다.

6. (선택사항) 초기 데이터 없이 시작하려면:

- SQL 파일에서 테이블 생성(CREATE TABLE)과 시퀀스 생성(CREATE SEQUENCE) 문만 실행하세요.
- INSERT 문은 실행하지 마세요.

주의:

- 이 과정은 새로운 데이터베이스 환경을 설정합니다. 기존 데이터가 있다면 백업을 먼저 진행하세요.
- 실제 운영 환경에서는 보안을 위해 더 복잡한 비밀번호를 사용해야 합니다.

이 설정을 완료하면 WING\_ 애플리케이션을 위한 데이터베이스 환경이 준비됩니다.

## 라이선스

이 프로젝트는 교육 및 학습 목적으로 제작되었으며, 상업적 이용을 금지합니다. 이 프로젝트는 개인의 웹 개발 기술 향상과 실무 경험 축적을 위해 진행되었으며, 다른 사용자들은 이 코드를 상업적 목적으로 사용할 수 없습니다.

### 사용 및 배포 제한

- 코드의 전체 또는 일부를 상업적 목적으로 사용할 수 없습니다.
- 프로젝트의 코드를 다른 목적으로 재사용하려면 원 저자의 동의가 필요합니다.
- 학습 및 비상업적 목적으로의 참조는 허용됩니다.

### 면책 조항

- 이 프로젝트는 "있는 그대로" 제공되며, 어떠한 명시적 또는 묵시적 보증도 하지 않습니다.
- 프로젝트 사용으로 인한 어떠한 손해에 대해서도 책임지지 않습니다.

© 2024 We are ING 팀 - 모든 권리 보유
