뿌리기 API

개발환경
Spring, Mybatis, Mysql

aop 트랜잭션을 서비스단에 설정



1. 뿌리기 API
금액,인원 요청값으로 받고 x-room-id, x-user-id를 header로 받아서 진행
3자리 랜덤토큰을 발생
금액을 인원에 맞게 랜덤으로 분배
DB에는 summary와 detail에 알맞게 insert 하고 
응답메시지 반환


2. 받기 API
뿌리기 시 생성된 token을 요청값으로 받고 x-room-id, x-user-id를 header로 받아서 진행
첫번째 select로 token, x-room-id, 뿌린사람, 받은사람 값들을 사용
(뿌린사람이거나, 받았거나, 뿌린돈을 이미 다 받아갔거나, 10분이 넘은경우 음수값(-1)을 반환하도록 함 로직보다 DB조건으로 validation 진행)
가능한경우 select로 금액리스트를 추출
추출한 금액리스트를 리스트 사이즈와 랜덤함수로 랜덤하게 가져와서 
요청 사용자에게 할당하며 DB UPDATE 진행 후
응답으로 금액과 응답메시지 반환 


3. 조회 API 
뿌리기 시 생성된 token을 요청값으로 받고 x-room-id, x-user-id를 header로 받아서 진행
token, x-room-id, x-user-id 를 이용하여 검색 
(7일이 넘었거나, 뿌린사람이 아닌경우 조회가 불가능하도록 DB조건 설정 조건이 맞지않으면 FAIL응답 값나가도록 처리)
응답으로 응답메시지,뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료된 정보 (받은금액, 사용자 리스트)를 반환



- 테스트 

junit4 및 postman으로 진행

각 경우의수를 생각하여 뿌리기, 받기, 조회시 알맞게 동작하는지를 확인





- DB구조 

summary에는 뿌린사람, 금액, 방번호, 토큰, 뿌린시간, 사람수

detail에는 랜덤금액, 받았는지여부, 받은사람

으로 구성

간단하게 API 쿼리에 조건에 맞는 인덱스와 외래키 생성

CREATE TABLE `flex_summary` (
  `summaryno` int NOT NULL AUTO_INCREMENT,
  `token` char(3) COLLATE utf8_unicode_ci NOT NULL,
  `room` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `holder` int NOT NULL,
  `totalmoney` int NOT NULL,
  `personcnt` int NOT NULL,
  `regdt` datetime NOT NULL,
  PRIMARY KEY (`summaryno`),
  KEY `IX_FLEX_SUMMARY_ROOM_TOKEN_REGDT` (`room`,`token`,`regdt`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `flex_detail` (
  `detailno` int NOT NULL AUTO_INCREMENT,
  `summaryno` int NOT NULL,
  `available` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Y',
  `money` int NOT NULL DEFAULT '0',
  `pno` int DEFAULT NULL,
  PRIMARY KEY (`detailno`),
  KEY `IX_FLEX_DETAIL_SUMMARYNO` (`summaryno`,`available`),
  CONSTRAINT `FK_FLEX_SUMMARY_SUMMARYNO` FOREIGN KEY (`summaryno`) REFERENCES `flex_summary` (`summaryno`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
