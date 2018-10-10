# Optional 가이드 정리 
#### [https://www.baeldung.com/java-optional]
#### 아래 문서는 상단 링크의 문서를 번역한 내용입니다. 
#### 오역 및 의역이 있을 수 있으니 자세한 내용은 해당 링크에서 확인 바랍니다.

## 1. 개요
이 문서는 자바8에서 새롭게 소개된 Optional class에 대해 설명합니다.
Optional class의 목적은 null 참조를 사용하는 대신 선택적 값을 나타내는 type 솔루션을 제공하는 것입니다.
Optional class에 대해 관심을 가져야하는 이유에 대해 더 자세히 이해하고자 하시면 오라클 공식 문서를 확인하십시오.
Optional class는 java.util.package의 한 부분입니다. 따라서 아래와 같은 import 선언이 필요합니다.
```
import java.util.Optional;
```

## 2. Optional 객체 생성하기
Optional 객체를 생성하기 위한 몇가지 방법이 있습니다. 

### Empty Optional 객체를 생성하기:
```
@Test
public void whenCreatesEmptyOptional_thenCorrect() {
    Optional<String> empty = Optional.empty();
    assertFalse(empty.isPresent());
}
```
isPresent API는 Optional 객체 안에 값이 있는지 확인하는데 사용됩니다. null이 아닌 값으로 Optional을 만든 경우에만 값이 나타냅니다. 다음 섹션에서 isPresent API에 대해 보실 수 있습니다.

### 정적 API를 통한 Optional 객체 생성하기:
```
@Test
public void givenNonNull_whenCreatesNonNullable_thenCorrect() {
    String name = "baeldung";
    Optional.of(name);
}
```

### Optional 값은 아래와 같이 처리할 수 있습니다:
```
@Test
public void givenNonNull_whenCreatesOptional_thenCorrect() {
    String name = "baeldung";
    Optional<String> opt = Optional.of(name);
    assertEquals("Optional[baeldung]", opt.toString());
}
```
반면에 of 메서드는 인자값으로 null을 허용하지 않습니다. 따라서 인자값이 null이될 경우 NullPointerException을 발생시킵니다.
```
@Test(expected = NullPointerException.class)
public void givenNull_whenThrowsErrorOnCreate_thenCorrect() {
    String name = null;
    Optional<String> opt = Optional.of(name);
}
```
만일 전달받은 인자값에 대해 null을 기대할 경우 ofNullable API를 사용할 수 있습니다.
```
@Test
public void givenNonNull_whenCreatesNullable_thenCorrect() {
    String name = "baeldung";
    Optional<String> opt = Optional.ofNullable(name);
    assertEquals("Optional[baeldung]", opt.toString());
}
```
아래와 같이 null 참조를 전달할 경우 예외를 throw하지 않고 Optional.empty API를 생성하여 빈 객체를 반환합니다.
```
@Test
public void givenNull_whenCreatesNullable_thenCorrect() {
    String name = null;
    Optional<String> opt = Optional.ofNullable(name);
    assertEquals("Optional.empty", opt.toString());
}
```

## 3. isPresent() 메서드를 통한 값 체크하기
사용자에 의해 생성되거나 접근자 메서드로 부터 반환된 Optional 객체를 가질 때 isPresent API를 사용하여 값의 존재 여부를 확인할 수 있습니다.
```
@Test
public void givenOptional_whenIsPresentWorks_thenCorrect() {
    Optional<String> opt = Optional.of("Baeldung");
    assertTrue(opt.isPresent());
 
    opt = Optional.ofNullable(null);
    assertFalse(opt.isPresent());
}
```
isPresent 메서드는 Optional 객체가 null이 아닌 값을 갖는 경우 true를 반환합니다.

## 4. ifPresent()를 통한 조건부 동작 수행
Optional class 이전에 값이 null이 아닌 경우를 체크하기 위해서는 아래와 같이 선언하여 사용하였습니다.
```
if(name != null){
    System.out.println(name.length);
}
```
상단의 코드는 name의 값이 null인지 아닌지를 확인한 후 코드를 실행하게 됩니다. 이는 코드가 길어지는 문제 뿐 아니라 다양한 버그를 야기 시킵니다.
이러한 접근법에 익숙해질 경우 *코드 작성 시에 null 체크하는 것을 잊어버릴 수 있습니다.* 이는 런타임 시에 NullPointerException을 발생 시킬 수 있습니다.
Optional은 좋은 프로그래밍 습관을 강제함으로서 null이 들어갈 수 있는 값을 다룰 수 있습니다. 그럼 Java8에서 상단에 제시되었던 코드가 어떻게 리팩토링 될 수 있는지 보겠습니다.

일반적인 함수형 프로그래밍에서는 실제로 존재하는 객체에 대해 동작을 수행 할 수 있습니다.
```
@Test
public void givenOptional_whenIfPresentWorks_thenCorrect() {
    Optional<String> opt = Optional.of("baeldung");
 
    opt.ifPresent(name -> System.out.println(name.length()));
}
```
상단의 예제를 통해 Optional class 사용 이전 코드가 5줄인 것에 반해 Optional class를 사용할 경우 단 2줄만으로 동일한 기능을 구현할 수 있습니다. 첫번 째 줄은 Optional 객체 내부에 객체를 감싸고 두번 째 줄은 유효성을 검사하고 코드를 실행하게 됩니다.
(ifPresent() 메서드 내부의 로직을 JAVA 8부터 추가된 lambda를 사용한 것입니다. 내용이 이해되지 않으실 경우 JAVA 8 lambda에 관련하여 학습하시길 바랍니다.)

## 5. 기본 값과 orElse
orElse API는 Optional 인스턴스 내에 래핑된 값을 검색하는 데 사용됩니다. 기본값으로 사용되는 하나의 매개 변수를 사용합니다. orElse를 사용하면 래핑 된 값이있는 경우 래핑된 *값이 반환되고 래핑된 값이 없으면 orElse에 지정된 인수가 반환됩니다.*
```
@Test
public void whenOrElseWorks_thenCorrect() {
    String nullName = null;
    String name = Optional.ofNullable(nullName).orElse("john");
    assertEquals("john", name);
}
```

## 6. 기본값과 orElseGet
orElseGet API는 orElse와 비슷합니다. 그러나 Optional 객체의 값이 없는 경우 값을 반환하는 대신 funcational interface의 결과를 반환합니다.
```
@Test
public void whenOrElseGetWorks_thenCorrect() {
    String nullName = null;
    String name = Optional.ofNullable(nullName).orElseGet(() -> "john");
    assertEquals("john", name);
}
```

## 7. orElse와 orElseGet의 차이 *(중요)*
Optional 또는 Java 8을 처음 접하는 많은 프로그래머에게 orElse와 orElseGet의 차이점은 분명하지 않습니다. 사실상이 두 API는 기능면에서 서로 겹치는 인상을줍니다.

*그러나 둘 사이에는 미묘하지만 매우 중요한 차이점이 있는데, 이를 잘 이해하지 못하고 사용할 경우 코드 성능에 크게 영향을 줄 수 있습니다.*

테스트 클래스에 getMyDefault라는 메서드를 생성합니다. 이 메서드는 인자값을 받지 않으며 정해진 "Getting Default Value"출력 후에 "Default Value"라는 값을 반환하게 됩니다.
```
public String getMyDefault() {
    System.out.println("Getting Default Value");
    return "Default Value";
}
```
*아래 두개의 테스트를 생성하여 두 API의 차이를 확인하고 side effect를 확인할 수 있습니다.*
```
@Test
public void whenOrElseGetAndOrElseOverlap_thenCorrect() {
    String text;
 
    System.out.println("Using orElseGet:");
    String defaultText = 
      Optional.ofNullable(text).orElseGet(this::getMyDefault);
    assertEquals("Default Value", defaultText);
 
    System.out.println("Using orElse:");
    defaultText = Optional.ofNullable(text).orElse(getMyDefault());
    assertEquals("Default Value", defaultText);
}
```
위의 예제에서, 우리는 Optional 객체 안에 null 텍스트를 감싸고 두 API를 사용하여 랩핑된 값을 얻으려고합니다. side effect는 아래와 같습니다.
```
Using orElseGet:
Getting default value...
Using orElse:
Getting default value...
```
각 케이스 별로 getMyDefault API가 호출됩니다. *래핑된 값이 없으면 orElse 및 orElseGet API가 모두 동일한 방식으로 작동합니다.*

이제 null이 아닌 값이 존재하는 경우의 테스트를 실행 해봅시다. 이번 테스트에서는 기본값은 생성되지 않아야합니다.
```
@Test
public void whenOrElseGetAndOrElseDiffer_thenCorrect() {
    String text = "Text present";               //앞선 예제와 달리 "Text present"값 설정
 
    System.out.println("Using orElseGet:");
    String defaultText 
      = Optional.ofNullable(text).orElseGet(this::getMyDefault);
    assertEquals("Text present", defaultText);
 
    System.out.println("Using orElse:");
    defaultText = Optional.ofNullable(text).orElse(getMyDefault());
    assertEquals("Text present", defaultText);
}
```
상단의 예제에서 우리는 더이상 null 값을 래핑하지 하지 않습니다. 이제 실행된 코드의 side effect를 확인해봅시다.
```
Using orElseGet:
Using orElse:
Getting default value...
```
주의해야 될 것은 래핑된 값을 찾기위해 orElseGet을 사용할 때는 포함된 값이 있기 때문에 getMyDefault API가 호출되지 않는다는 것입니다.

그러나 orElse를 사용하면 *래핑된 값의 존재 여부에 관계없이* 기본 객체가 만들어집니다. 따라서 이 경우에는 사용되지않는 하나의 중복 객체를 만들게됩니다.

이 간단한 예제에서 기본 객체를 만드는 데 별로 비용이 들지 않습니다. 이는 JVM이 이를 처리하는 방법을 알고 있기 때문입니다. 그러나 getMyDefault와 같은 메소드가 웹 서비스 호출을하거나 데이터베이스를 쿼리해야하는 경우 명백한 비용이 발생하게 됩니다.
#### ※ 즉 orElse는 객체 존재 여부에 관계없이 객체가 만들어지고 이를 수행하게 되지만 orElseGet은 null이 아닌 경우 수행되지 않습니다.

## 8. 예외와 orElseThrow
orElseThrow API는 orElse 및 orElseGet을 이후에 남은 값을 처리하는 새로운 접근법을 추가합니다. 래핑된 값이 없을 때 기본값을 반환하는 대신 예외가 발생합니다.
```
@Test(expected = IllegalArgumentException.class)
public void whenOrElseThrowWorks_thenCorrect() {
    String nullName = null;
    String name = Optional.ofNullable(nullName).orElseThrow(
      IllegalArgumentException::new);
}
```
Java 8의 메소드 참조는 여기에서 예외 생성자를 전달하는데 편리합니다.

## 9. 값의 반환과 get()
get API는 래핑된 값을 가져오는 마지막 방법입니다. (앞서 소개한 of, ofNullable 또한 Optional로 래핑된 값을 가져오는 방법입니다.)
```
@Test
public void givenOptional_whenGetsValue_thenCorrect() {
    Optional<String> opt = Optional.of("baeldung");
    String name = opt.get();
 
    assertEquals("baeldung", name);
}
```

그러나 앞서 소개된 접근 방식과 달리 get API는 래핑 된 객체가 *null이 아닌 경우에만 값을 반환 할 수 있으며 그렇지 않으면 예외를 throw 합니다.*
```
@Test(expected = NoSuchElementException.class)
public void givenOptionalWithNull_whenGetThrowsException_thenCorrect() {
    Optional<String> opt = Optional.ofNullable(null);
    String name = opt.get();
}
```
이상적으로 이러한 예기치 않은 예외를 회피할 수 있도록 도와야 하나 get API는 그러한 동작을 수행하지 못하며 이는 get API의 주된 결함입니다. 따라서 이 접근 방식은 이후 릴리즈에서는 더 이상 사용되지 않습니다.

*따라서 null 케이스에 대비할 수 있고 명시적으로 처리 할 수 있는 다른 접근 방식을 사용하는 것이 좋습니다.*

## 10. 조건부 반환과 filter()
필터 API는 래핑된 값에 대한 인라인 테스트를 실행하는데 사용됩니다. 인자값으로 조건을 입력하고 입력된 조건된 테스트를 통과화면 그대로 반환 됩니다.

그러나 조건자가 거짓을 반환하면 비어있는 선택사항이 반환됩니다.
```
@Test
public void whenOptionalFilterWorks_thenCorrect() {
    Integer year = 2016;
    Optional<Integer> yearOptional = Optional.of(year);
    boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
    assertTrue(is2016);
    boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
    assertFalse(is2017);
}

```
filter API는 일반적으로 미리 정의된 규칙에 따라 래핑된 값을 거부할 때 사용하게 됩니다. 예를들어 잘못된 이메일 형식이나 충분히 강력하지 않은 암호를 허용하지 않을게 할 때 사용할 수 있습니다.

다른 의미있는 예를 살펴 보겠습니다. 우리가 모뎀을 사고 싶다고 가정할 때 가격만 신경 쓰면됩니다. Google은 특정 사이트에서 모뎀 가격에 대한 푸시 알림을 받고 이를 개체에 저장합니다.
```
public class Modem {
    private Double price;
 
    public Modem(Double price) {
        this.price = price;
    }
    //standard getters and setters
}
```
그런 다음 모뎀 가격이 예산 범위 내에 있는지 확인해야합니다. 우리는 모뎀을 사기위한 예산으로 10 ~ 15 달러를 소비하려고합니다. Optional 없이 작성된 코드를 살펴 보겠습니다.
```
public boolean priceIsInRange1(Modem modem) {
    boolean isInRange = false;
 
    if (modem != null && modem.getPrice() != null
      && (modem.getPrice() >= 10
        && modem.getPrice() <= 15)) {
 
        isInRange = true;
    }
    return isInRange;
}
```
상단의 코드에서 특히 if 조건에서 작성해야하는 코드의 양이 많은 것은 확인 할 수 있습니다. if 조건에서 의미있는 부분은 예산의 범위를 검사하는 부분입니다. 
```
(mode.getPrice() >= 10 && modem.getPrice() <= 15)
```
나머지 앞에 선언된 조건은 오동작을 방어하기 위한 조건입니다. (객체와 가격의 null 체크)
```
@Test
public void whenFiltersWithoutOptional_thenCorrect() {
    assertTrue(priceIsInRange1(new Modem(10.0)));
    assertFalse(priceIsInRange1(new Modem(9.9)));
    assertFalse(priceIsInRange1(new Modem(null)));
    assertFalse(priceIsInRange1(new Modem(15.5)));
    assertFalse(priceIsInRange1(null));
}
```
상단의 코드는 null 검사를 잊는 등의 오류를 야기시킬 수 있습니다.

이제 Optional filter API를 사용하여 동일한 코드를 구성해보겠습니다.
```
public boolean priceIsInRange2(Modem modem2) {
     return Optional.ofNullable(modem2)
       .map(Modem::getPrice)
       .filter(p -> p >= 10)
       .filter(p -> p <= 15)
       .isPresent();
 }
```
map API는 단순히 값을 다른 값으로 변환하는데 사용됩니다. (위의 예시에서는 원래 값을 수정하지 않는다는 점에 유의하십시오.)
여기서는 Model 클래스에서 가격 객체를 얻습니다. 다음 섹션에서 map API를 자세히 살펴볼 것입니다.

우선, null 객체가 이 API에 전달되면 아무런 문제가 없을 것으로 예상됩니다.

두 번째로, filter API를 통한 가격 범위 검사입니다. Optional은 나머지를 처리합니다.
```
@Test
public void whenFiltersWithOptional_thenCorrect() {
    assertTrue(priceIsInRange2(new Modem(10.0)));
    assertFalse(priceIsInRange2(new Modem(9.9)));
    assertFalse(priceIsInRange2(new Modem(null)));
    assertFalse(priceIsInRange2(new Modem(15.5)));
    assertFalse(priceIsInRange2(null));
}
```
이전 API는 가격 범위를 확인하지만 고유의 취약성(null 검사)을 방어하기 위해 그 이상을 수행해야합니다. 따라서 불필요한 값을 거부하기 위해 filter API를 사용하여 불필요한 if 문을 대체 할 수 있습니다.

## 11. 값의 변환과 map()
이전 섹션에서는 filter를 기반으로 값을 거부하거나 수락하는 방법에 대해 살펴 보았습니다. map API를 사용하여 Optional 값을 변환하는데도 비슷한 구문을 사용할 수 있습니다.
```
@Test
public void givenOptional_whenMapWorks_thenCorrect() {
    List<String> companyNames = Arrays.asList(
      "paypal", "oracle", "", "microsoft", "", "apple");
    Optional<List<String>> listOptional = Optional.of(companyNames);
 
    int size = listOptional
      .map(List::size)
      .orElse(0);
    assertEquals(6, size);
}
```
이 예제에서는 Optional 객체 안에 문자열 목록을 래핑하고 map API를 사용하여 포함된 목록에 대한 작업을 수행합니다. 우리가 수행하는 동작은 목록의 크기를 검색하는 것입니다.

map API는 래핑된 계산 결과를 반환합니다.

*filter API의 경우 단순히 값을 확인하고 boolean값 을 반환*하는 반면에 *map API는 기존 값을 가져 와서 이 값을 사용하여 계산을 수행하고 Optional 객체에 래핑된 계산 결과를 반환*합니다.
```
@Test
public void givenOptional_whenMapWorks_thenCorrect2() {
    String name = "baeldung";
    Optional<String> nameOptional = Optional.of(name);
 
    int len = nameOptional
     .map(String::length())
     .orElse(0);
    assertEquals(8, len);
}
```
map과 filter를 연결하여 더 강력한 것을 할 수 있습니다.

사용자가 입력한 비밀번호의 유효성을 확인한다고 가정해 보겠습니다. map 변환을 사용하여 비밀번호를 지우고 필터를 사용하여 유효성을 검사 할 수 있습니다.
```
@Test
public void givenOptional_whenMapWorksWithFilter_thenCorrect() {
    String password = " password ";
    Optional<String> passOpt = Optional.of(password);
    boolean correctPassword = passOpt.filter(
      pass -> pass.equals("password")).isPresent();
    assertFalse(correctPassword);
 
    correctPassword = passOpt
      .map(String::trim)                            //trim을 사용하여 문자열의 공백을 제거
      .filter(pass -> pass.equals("password"))
      .isPresent();
    assertTrue(correctPassword);
}
```
위의 예시와 같이 입력된 비밀번호의 공백값을 처리하지 않으면 filter의 결과가 의도하지않게 실패할 수 있습니다. 따라서 잘못된 비밀번호를 filter 하기 전에 map을 사용하여 앞뒤의 공백을 처리해줄 수 있습니다.

## 12. 값의 변환과 flatMap()
map API와 마찬가지로 값 변환을 위한 대안으로 flatMap API도 있습니다. 차이점은 map이 래핑되지 않은 경우에만 값을 변환하는 반면 flatMap은 래핑된 값을 가져와서 변환하기 전에 래핑된 값을 사용한다는 것입니다.

이전에는 Optional 인스턴스에서 래핑 할 수 있도록 간단한 String 및 Integer 객체를 만들었습니다. 그러나 종종 복잡한 객체의 접근 자로부터 이러한 객체를받습니다.

차이점을 더 명확하게 파악하기 위해 이름, 나이 및 비밀번호와 같은 세부 정보가 포함된 Person 객체를 살펴 보겠습니다.
```
public class Person {
    private String name;
    private int age;
    private String password;
 
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
 
    public Optional<Integer> getAge() {
        return Optional.ofNullable(age);
    }
 
    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }
 
    // normal constructors and setters
}
```

String과 같이 Optional 객체에 래핑합니다. 또는 다른 API를 호출하여 반환할 수 있습니다.
```
Person person = new Person("john", 26);
Optional<Person> personOptional = Optional.of(person);
```

Person 객체를 래핑 할 때 중첩된 Optional 인스턴스가 포함됩니다. *(중첩된 Optional의 처리에 대해 확인하세요.)*
```
@Test
public void givenOptional_whenFlatMapWorks_thenCorrect2() {
    Person person = new Person("john", 26);
    Optional<Person> personOptional = Optional.of(person);
 
    Optional<Optional<String>> nameOptionalWrapper  
      = personOptional.map(Person::getName);                               
    Optional<String> nameOptional                                               
      = nameOptionalWrapper.orElseThrow(IllegalArgumentException::new);
    String name1 = nameOptional.orElse("");
    assertEquals("john", name1);
 
    String name = personOptional
      .flatMap(Person::getName)
      .orElse("");
    assertEquals("john", name);
}
```
위의 예시에서는 Assertion을 수행하기 위해 Person 객체의 name 속성을 검색하려고합니다.
세 번째 구문에서 map API를 사용하여 이 작업을 수행한 코드를 확인하시고, 하단부에서 flatMap API로 동일한 동작을 어떻게 처리하는지 확인하십시오.
Person::getName 메소드 참조는 이전 절에서 암호 정리에 대해 사용한 String::trim 호출과 유사합니다.

유일한 차이점은 getName()은 trim() 작업과 마찬가지로 String이 아닌 Optional을 반환한다는 것입니다. *map 변환 결과가 Optional 객체로 래핑된다는 사실과 결합하면 중첩된 Optional이 됩니다.*

map API를 사용하는 동안 변환된 값을 사용하기 전에 값을 검색하기 위해 추가 호출을 추가해야합니다. 이렇게하면 Optional 랩퍼가 제거됩니다. flatMap을 사용할 때는 이러한 중첩에 대해 암시적으로 동작을 수행하기 때문에 사용자가 중첩된 Optional객체에 대해 별도의 처리를 해줄 필요가 없습니다.
#### ※flatMap은 Object나 Array등 (위의 예시에서는 Optional)로 감싸져 있는 모든 원소를 단일 원소 스트림으로 반환합니다.

## 13. JDK9 Optional API
Java 9의 출시로 더 많은 새로운 메소드가 Optional API에 추가되었습니다.
* 대안을 만드는 공급자를 제공하는 or () 메소드
* Optional이 있는 경우 액션을 실행하도록 허용하는 ifPresentOrElse() 메소드 또는 그렇지 않은 경우 다른 액션
* Optional을 Stream로 변환하기위한 stream() 메소드

## 14. 결론
이 문서서는 Java 8 Optional 클래스의 중요한 기능 대부분을 다루었습니다.
우리는 명시적 null 검사 및 입력 유효성 검사 대신 Optional을 사용하는 이유에 대해서도 간략하게 살펴 보았습니다.
마지막으로, 우리는 orElse와 orElseGet의 미묘하지만 중요한 차이점을 다루었습니다. 
이 글의 모든 예제 소스 코드는 GitHub에서 볼 수 있습니다.
