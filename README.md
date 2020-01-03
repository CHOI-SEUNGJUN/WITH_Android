

# W!TH Android

### W!TH - 위드

안전하고 간편한 여행 동행 플랫폼 'W!TH'

***
![WITH 그림자](https://user-images.githubusercontent.com/50166893/71722574-a783cd80-2e6c-11ea-82a1-eb62218093d0.jpg)



## W!TH Project Repositories

* <a href="https://github.com/TEAM-WITH/WITH-Android">Android</a>

  Android Studio Client
  
* <a href="https://github.com/TEAM-WITH/WITH_iOS">iOS</a>

  XCODE - Swift

* <a href="https://github.com/TEAM-WITH/WITH_Server">Server</a>

   Node.js API Server


### Work Flow

********
![위드 work-flow](https://user-images.githubusercontent.com/50166893/71722841-cafb4800-2e6d-11ea-82e8-eaabaecbc864.PNG)

    

### 패키지 구조

패키지별 설명

**********

- manage : TOKEN 및 서버 통신 모듈 등을 관리하는 코드
- data : UI에서 화면을 그리는데 사용하는 데이터를 모아놓은 코드
    - local : 로컬에서 저장하는 데이터 클래스를 모아놓은 코드
    - remote : Retrofit을 이용해서 response 클래스와 request 클래스를 관리하는 코드
    - repository : Retrofit 통신을 위한 interface가 구현된 코드
- extension : 재사용성이 강한 반복되는 코드를 간결하게 만들어주는 kotlin extension 함수들을 모아놓은 코드
- ui : Activity, Fragment, Adapter, ViewHolder 등 화면을 그려주는 코드
    - 화면별로 패키지를 분류
    <br></br>
    <br></br>

### 사용 라이브러리

라이브러리

**********

- glide : 이미지 로딩을 위해 사용 (url 로드, uri 로드, placeholder 사용해 일관적인 image 처리)
- koin : 생성을 추상화 하기 위해서 사용 (repository, auth, logger 등을 singleton 객체로 쉽게 전체 프로젝트에서 접근)
- retrofit + gson : Json 데이터를 이용한 rest 서버 통신을 위해 사용 (Callback를 람다를 이용해 만드는 함수 설계로 일관적인 code 개발 구현)
- google material : google material design 적용을 위해 사용
- circle imageView : 원형 이미지를 구현하기 위해 사용
- firebase-database : Firebase realtime db를 이용하여 채팅 구현하기 위해 사용
- lottie : 애프터이펙트로 만든 애니메이션을 적용하기 위해 사용
    <br></br>
    <br></br>

### 핵심기능

핵심기능 구현 방법

**********

- 채팅
    * FireBase-Realtime DB를 이용하여 채팅 목록과 채팅 기능을 구현
    
    * 채팅 화면 구성을 7개의 ViewHolder를 이용
    
    * RecyclerView.Adapter에서 채팅 데이터를 각 item viewtype으로 반환하는 로직 구현
          =>  kotlin extension을 최대한 활용하여 보일러 코드를 줄임)
      
    * WITH DB와 파이어베이스를 연동하여 채팅 목록을 구성함.
      


- DI 라이브러리(koin)와 kotlin extension을 이용한 손쉬운 비동기 통신 처리
    * 반복되는 retrofit 구현체와 enqueue 처리 코드를 koin과 extension으로 종속 코드를 최대한 줄임
    
    * 재사용성을 매우 높게 만들어주고, 결합도를 낮춰 유연성과 확장성을 향상시킴
	
	* 코드를 매우 단순화시켜 기존 enqueue 코드를 단 두 줄로 가능하게함
		1)   모듈을 생성(koin DSL - manage 패키지)
		2)   Android Application Class를 생성 후 그곳에서 startKoin()으로 실행
		3)   사용하고자 하는 클래스에서 의존성 주입
		
		
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
    <br></br>
    <br></br>

### lambda

lambda 사용

***********
* 주요 사용처
- extension/CallExt : Retrofit Enqueue 코드
- extension/FireBaseListenerExt : FireBase Single Listener와 Value Event Listener 코드
* 람다 사용가능한 코드에는 대부분 적용.

    
### extension function

extension function의 사용
**********
- CallExt
- FireBaseListenerExt
- GlideExt
- ToastExt
- UtilExt
- ViewExt
    <br></br>
    - lambda와 extension 용례
<br></br>
    <img width="465" alt="스크린샷 2020-01-03 오후 11 51 08" src="https://user-images.githubusercontent.com/55845206/71729961-51228900-2e84-11ea-86a2-74887c5a8fbf.png">
    <br></br>

    
### ConstraintLayout 
<img width="1074" alt="스크린샷 2020-01-03 오후 11 49 54" src="https://user-images.githubusercontent.com/55845206/71729956-4d8f0200-2e84-11ea-9d69-3b809cbf3654.png">
**********
모든 뷰를 ConstraintLayout을 이용하여 XML 레이아웃 작성.
 - 채팅과 Custom Dialog은 ConstraintLayout의 match_parent 버그로 사용할 수 없는 이유로 다른 레이아웃으로 작성
 
 <br></br>

### Developer

개발자
***********

**최승준**(CHOI-SEUNGJUN)</br>
**조현아**(whgusdk98)</br>
**석영현**(yeonghyeonSeok)
