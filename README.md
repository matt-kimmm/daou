\1. 조직도 조회 

[GET] [http://{](http://{/)서버URL}/org/organizations

파라메터는 RequestPram

\- searchType과 searchKeyword를 모두 보낸 경우, deptCode와 deptOnly는 적용 안됨

\- searchType와 searchKeyword 중 빈 값이 있는 경우 키워드 조회 X

| 파라미터      | 타입    | 필수여부 | 설명                                                |
| ------------- | ------- | -------- | --------------------------------------------------- |
| deptCode      | String  | N        | 기준 부서코드. 미입력시 최상위 노드를 기준으로 조회 |
| deptOnly      | boolean | N        | 부서만 또는 부서원 포함 응답 구분                   |
| searchType    | String  | N        | 검색 대상* dept: 부서* member: 부서원               |
| searchKeyword | String  | N        | 검색어                                              |

 

(1) 전체 조회 : [GET] [http://{](http://{/)서버URL}/org/organizations 

(2) deptOnly 조회 : [GET] [http://{](http://{/)서버URL}/org/organizations?deptOnly=true 

(3) deptCode 조회 : [GET] [http://{](http://{/)서버URL}/org/organizations?deptCode=D110

(4) 부서명 키워드 검색 조회 : [GET] [http://{](http://{/)서버URL}/org/organizations?searchType=dept&searchKeyword=abc

(5) 부서원 키워드 검색 조회 : [GET] [http://{](http://{/)서버URL}/org/organizations?searchType=member&searchKeyword=abc

 

2. 부서 관리

\# root가 true인 department는 하나만 등록

(1) 부서 추가 : [POST] [http://{](http://{/)서버URL}/org/dept

\- request body :

{

​    "name":"ABC회사", // 회사명

​    "code" : "code01", // 코드

​    "root": true, // 최상단 여부

​    "upperDepartmentId" : 1,

}

(2) 부서 변경: [PUT} [http://{](http://{/)서버URL}/org/dept/{deptId} 

\- request body :

{

​    "name":"DDD회사", // 회사명

​    "code" : "code01", // 코드

​    "root": true, // 최상단 여부

​    "upperDepartmentId" : 2,

}

(3) 부서 삭제: [DELETE] [http://{](http://{/)서버URL}/org/dept/{deptId}

 

3. 부서원 관리

(1) 부서원 추가 : [POST] [http://{](http://{/)서버URL}/org/member

\- request body :

{

​    "name": "류현진",

​    "manager": true,

​    "departmentIds": [1]

}

(2) 부서원 변경 : [PUT] [http://{](http://{/)서버URL}/org/member/{memberId}

\- request body :

{

​    "name": "류현진",

​    "manager": false,

​    "departmentIds": [1, 2]

}

(3) 부서원 삭제: [DELETE] [http://{](http://{/)서버URL}/org/member/{memberId}

 

\+ Exception 정의 : src > java > com.daou.organizations > config 내