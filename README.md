# sul-ijoa_be
### 개발환경
**IDE:** IntelliJ IDEA 2023.2.3 (Ultimate Edition)  
**java:** 17  
**Spring Boot:** 3.2.0  
**Dependencies:** Spring Web, Spring Data JPA, Lombok, MySQL Driver, OpenCSV, QueryDSL JPA

### springboot 3.xx 이상 버전에서의 QueryDSL 설정

```
dependencies {
  implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
  annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
  annotationProcessor "jakarta.annotation:jakarta.annotation-api"
  annotationProcessor "jakarta.persistence:jakarta.persistence-api"
}
```

### RDS 구축
**DB엔진:** MySQL 8.0  
**인스턴스 유형:** db.t3.micro  
**보안관리:** IAM 인증 사용  
