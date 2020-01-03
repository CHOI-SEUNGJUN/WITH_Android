# W!TH Android

### W!TH - 위드
안전하고 간편한 여행 동행 플랫폼 'W!TH'

***
![WITH 그림자](https://user-images.githubusercontent.com/50166893/71722574-a783cd80-2e6c-11ea-82a1-eb62218093d0.jpg){: width : 40%}


### Work Flow
********
![위드 work-flow](https://user-images.githubusercontent.com/50166893/71722841-cafb4800-2e6d-11ea-82e8-eaabaecbc864.PNG)


### 패키지 구조
패키지별 설명

**********

- manage : 로그인, token 및 서버 통신 모듈 등을 관리하는 코드
- data : ui에서 화면을 그리는데 사용하는 데이터를 모아놓은 코드
    - local : 
    - remote : retrofit을 이용해서 response 클래스와 request 클래스를 관리하는 코드
    - repository : retrofit 통신을 위한 interface가 구현된 코드
- extension : 확장함수
- ui : Activity, Fragment, Adapter, Holder 등 화면을 그려주는 코드
    - 기능별로 패키지 분류



### 사용 라이브러리
라이브러리

**********

- glide : 이미지 로딩을 위해 사용 (url 로드, uri 로드, placeholder 사용해 일관적인 image 처리)
- koin : 생성을 추상화 하기 위해서 사용 (repository, auth, logger 등을 singleton 객체로 쉽게 전체 프로젝트에서 접근)
- retrofit + gson : Json 데이터를 이용한 rest 서버 통신을 위해 사용 (Callback를 람다를 이용해 만드는 함수 설계로 일관적인 code 개발 구현)
- google material : google material design 적용을 위해 사용
- circle imageView : 원형 이미지를 구현하기 위해 사용
- firebase - Database : ???



### 핵심기능
핵심기능 구현 방법

**********

- 채팅
    * FireBase-Realtime DB를 이용하여 채팅 목록과 채팅 기능을 구현
    
    * 채팅 화면 구성을 7개의 ViewHolder를 이용
    
    * RecyclerView.Adapter에서 채팅 데이터를 각 item viewtype으로 반환하는 로직 구현
          =>  kotlin extension을 최대한 활용하여 보일러 코드를 줄임)
      
      


- DI 라이브러리(koin)와 kotlin extension을 이용한 손쉬운 비동기 통신 처리
    * 반복되는 retrofit 구현체와 enqueue 처리 코드를 koin과 extension으로 종속 코드를 최대한 줄임
    
    * 재사용성을 매우 높게 만들어주고, 결합도를 낮춰 유연성과 확장성을 향상시킴
	
	* 코드를 매우 단순화시켜 기존 enqueue 코드를 단 두 줄로 가능하게함
		1)   모듈을 생성(koin DSL - manage 패키지)
		2)   Android Application Class를 생성 후 그곳에서 startKoin()으로 실행
		3)  사용하고자 하는 클래스에서 의존성 주입
		
		


- DatePicker
	* 날짜 선택 컨트롤을 커스텀하여 다이얼로그 창에 구현
	
	* 다이얼로그에 구현되어 있는 datePicker에서 선택한 값을 koin을 이용하여 로드한 후 부모 화면에서 그 값을 받아 보여줌
	
	* 다이얼로그 창을 띄우기 전, 저장된 날짜 정보를 파싱하여 datePicker에서 띄우는 로직 구현
	
- Device 갤러리 접근

  - 안드로이드 Intent 중 ACTION_PICK을 사용하여 갤러리에 접근하여 이미지를 가져옴
  - onActivityResult()를 오버라이드하여 이미지를 표시하는 작업 수행

    - Glide 라이브러리를 사용하여 간편하게 배치
  - getRealPathFromURI() 함수에서 이미지의 절대 파일 경로를 가져옴
  - Multipart로 서버에 해당 이미지 전송




### lamda
lamda 사용

***********
-
-



### extension function
extension function의 사용

**********
- CallEx
- FireBaseListenerExt
- GlideExt
- ToastExt
- UtilExt
- ViewExt


### Developer

개발자
***********

**최승준**(CHOI-SEUNGJUN)
**조현아**(whgusdk98)
**석영현**(yeonghyeonSeok)



### W!TH의 다른 프로젝트

************

- TEAM W!TH 
<https://github.com/TEAM-WITH>