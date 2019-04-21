[TOC]
- - -
# 一 、工厂模式
工厂模式专门负责将大量有共同接口的类实例化。工厂模式可以动态决定将哪一个类实例化，不必事先知道每次要实例化哪一个类。
## 分类
1. 简单工厂（Simple Factory）模式，又称静态工厂方法模式（Static Factory Method Pattern）。
2. 工厂方法（Factory Method）模式，又称多态性工厂（Polymorphic Factory）模式或虚拟构造子（Virtual Constructor）模式；
3. 抽象工厂（Abstract Factory）模式，又称工具箱（Kit 或Toolkit）模式。
https://www.jianshu.com/p/6939416ecfa3
- - -
## 总结
1. sampleFactory  --> 抽象了Product , 固定写死Factory  新建时需要Product实现类路径，写死了类的生成模式
2. Factory --> 抽象了Product ,抽象了Factory 新建实例时需要知道相对应的Factory实现类，把类的加载延迟到其子类确定的时候
3. abstractFactory --> 靠一系列 抽象接口，不需要知道实现类是什么，用的时候加载，其实是吧Product更细化而已

- - -
## 二 、简单工厂 
### 2.1 介绍
> 简单工厂模式(Simple Factory Pattern)：又称为`静态工厂方法(Static Factory Method)模式`，它属于类创建型模式。在简单工厂模式中，可以根据自变量的不同返回不同类的实例。简单工厂模式专门定义一个类来负责创建其他类的实例，被创建的实例通常都具有共同的父类。
### 2.2 简单工厂角色
1. 工厂类（Creator）角色：担任这个角色的是工厂方法模式的核心，含有与应用紧密相关的商业逻辑。工厂类在客户端的直接调用下创建产品对象，它往往由一个具体Java 类实现。
2. 抽象产品（Product）角色：担任这个角色的类是工厂方法模式所创建的对象的父类，或它们共同拥有的接口。抽象产品角色可以用一个Java 接口或者Java 抽象类实现。
3. 具体产品（Concrete Product）角色：工厂方法模式所创建的任何对象都是这个角色的实例，具体产品角色由一个具体Java 类实现。
### 2.3 简单工厂模式的优缺点
**简单工厂模式的优点如下：**
1. 工厂类含有必要的判断逻辑，可以决定在什么时候创建哪一个产品类的实例，客户端可以免除直接创建产品对象的责任，而仅仅“消费”产品；简单工厂模式通过这种做法实现了对责任的分割，它提供了专门的工厂类用于创建对象。
2. 客户端无需知道所创建的具体产品类的类名，只需要知道具体产品类所对应的参数即可，对于一些复杂的类名，通过简单工厂模式可以减少使用者的记忆量。
3. 通过引入配置文件，可以在不修改任何客户端代码的情况下更换和增加新的具体产品类，在一定程度上提高了系统的灵活性。

**简单工厂模式的缺点如下：**
1. 由于工厂类集中了所有产品创建逻辑，一旦不能正常工作，整个系统都要受到影响。
2. 使用简单工厂模式将会增加系统中类的个数，在一定程序上增加了系统的复杂度和理解难度。
3. 系统扩展困难，一旦添加新产品就不得不修改工厂逻辑，在产品类型较多时，有可能造成工厂逻辑过于复杂，不利于系统的扩展和维护。
4. 简单工厂模式由于使用了静态工厂方法，造成工厂角色无法形成基于继承的等级结构。

### 2.4 简单工厂模式的适用环境
1. 工厂类负责创建的对象比较少：由于创建的对象较少，不会造成工厂方法中的业务逻辑太过复杂；
2. 客户端只知道传入工厂类的参数，对于如何创建对象不关心：客户端既不需要关心创建细节，甚至连类名都不需要记住，只需要知道类型所对应的参数。

### 2.5 DEMO
假设一家工厂，生产电视，汽车等等，我们先为所有产品定义一个共同的产品接口
```java
public interface Product {
}
```
接着我们让这个工厂的所有产品都必须实现此接口
```java
public class Tv implements Product {
    public Tv(){
        System.out.println("电视被制造了");
    }
}

public class Car implements Product {
    public Car(){
        System.out.println("汽车被制造了");
    }
}
```
下面有几种模式来实现这个工厂：
#### 2.5.1 直接判断传入的key
```java
public class ProductFactory {
    public static Product produce(String productName) throws Exception {
        switch (productName) {
            case "tv":
                return new Tv();
            case "car":
                return new Car();
            default:
                throw new Exception("没有该产品");
        }
    }
}
```
测试方法：
```java
try {
    ProductFactory.produce("car");
} catch (Exception e) {
    e.printStackTrace();
}
```
返回结果: 汽车被制造了
这样的实现有个问题，如果我们新增产品类的话，需要不断的在工厂类中新增case，这样需要修改的地方比较多，所以不建议使用这样的方法来实现工厂类。

#### 2.5.2 利用反射
```java
public class ProductFactory2 {
    public static Product produce(String className) throws Exception {
        try {
            Product product = (Product) Class.forName(className).newInstance();
            return product;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new Exception("没有该产品");
    }
}
```
测试方法：
```java
try {
    ProductFactory2.produce("com.zhaofeng.factory.simple.Tv");
} catch (Exception e) {
    e.printStackTrace();
}
```
返回结果：电视被制造了
这种的缺点在于，每次创建一个产品时，需要传入产品的全部类路径，也就是要记住一个产品的全部路径，比较麻烦。我们想到可以通过配置文件，来将类路径全部写在properties文件中，通过加载配置文件，这样如果以后新增的话，直接修改配置文件即可。

#### 2.5.3 反射加配置文件
新增配置文件product.properties
tv=com.zhaofeng.factory.simple.Tv
car=com.zhaofeng.factory.simple.Car

新增配置文件读取类，将读出来的内容存储到一个map中
```java
public class PropertyReader {
    public static Map<String, String> map = new HashMap<>();

    public Map<String, String> readPropertyFile(String fileName) {
        Properties pro = new Properties();
        InputStream in = getClass().getResourceAsStream(fileName);//
        /**
            * 因为android会找不到文件，所以只能存放在assets或者res的子目录里面的
            * android写法(参数：Context context)：
            * 1、fileName = "/assets/product.properties" //文件放在assets下
            * 2、in = context.getAssets().open(fileName);  //文件放在assets下
            * 3、in = context.getResources().openRawResource(R.raw.fileName);  //文件放在res/raw下
            * 4、in = new FileInputStream(android.os.Environment.getExternalStorageDirectory() + File.separator+fileName); 
            */
        try {
            pro.load(in);
            Iterator<String> iterator = pro.stringPropertyNames().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = pro.getProperty(key);
                map.put(key, value);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
```
全新的工厂类如下：
```java

public class ProductFactory3 {
    public static Product produce(String key) throws Exception {
        PropertyReader reader = new PropertyReader();
        Map<String, String> map = reader.readPropertyFile("product.properties");
        try {
            Product product = (Product) Class.forName(map.get(key)).newInstance();
            return product;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new Exception("没有该产品");
    }
}
```
测试类:
```java
try {
    ProductFactory3.produce("tv");
} catch (Exception e) {
    e.printStackTrace();
}

```
返回结果:电视被制造了
当然这个方法也有可以改进的地方，比如将map在程序启动时就加载，这样就不必要每次调用的时候，都去解析配置文件了，节省了一批开销。
- - -
## 三 、工厂方法
### 3.1 介绍
> 实现类的动态加载可以用代理
工厂方法模式定义一个用于创建对象的接口，让子类决定实例化哪一个类。Factory Method是一个类的实例化延迟到其子类。
在工厂方法模式中，核心的工厂类不再负责所有的产品的创建，而是将具体创建的工作交给子类去做。
这个核心类则摇身一变，成为了一个抽象工厂角色，仅负责给出具体工厂子类必须实现的接口，而不接触哪一个产品类应当被实例化这种细节。
  
### 3.2工厂方法模式角色
1. 抽象工厂（Creator）角色：担任这个角色的是工厂方法模式的核心，它是与应用程序无关的。任何在模式中创建对象的工厂类必须实现这个接口。在上面的系统中这个角色由Java 接口Creator 
扮演；在实际的系统中，这个角色也常常使用抽象Java 类实现。
2. 具体工厂（Concrete Creator）角色：担任这个角色的是实现了抽象工厂接口的具体Java 类。具体工厂角色含有与应用密切相关的逻辑，并且受到应用程序的调用以创建产品对象。在本系统中给出了两个这样的角色，也就是具体Java 
类ConcreteCreator1 和ConcreteCreator2。
3. 抽象产品（Product）角色：工厂方法模式所创建的对象的超类型，也就是产品对象的共同父类或共同拥有的接口。在本系统中，这个角色由Java 接口Product 扮演；在实际的系统中，这个角色也常常使用抽象Java 类实现。
4. 具体产品（Concrete Product）角色：这个角色实现了抽象产品角色所声明的接口。工厂方法模式所创建的每一个对象都是某个具体产品角色的实例。
### 3.3工厂方法模式的优缺点
***工厂方法模式的优点如下：***
1. 在工厂方法模式中，工厂方法用来创建客户所需要的产品，同时还向客户隐藏了哪种具体产品类将被实例化这一细节，用户只需要关心所需产品对应的工厂，无需关心创建细节，甚至无需知道具体产品类的类名。
2. 基于工厂角色和产品角色的多态性设计是工厂方法模式的关键。它能够使工厂可以自主确定创建何种产品对象，而如何创建这个对象的细节则完全封装在具体工厂内部。工厂方法模式之所以又被称为多态工厂模式，正是因为所有的具体工厂类都具有同一抽象父类。
3. 使用工厂方法模式的另一个优点是在系统中加入新产品时，无需修改抽象工厂和抽象产品提供的接口，无需修改客户端，也无需修改其他的具体工厂和具体产品，而只要添加一个具体工厂和具体产品就可以了，这样，系统的可扩展性也就变得非常好，完全符合“开闭原则”。
***工厂方法模式的缺点如下：***
1.在添加新产品时，需要编写新的具体产品类，而且还要提供与之对应的具体工厂类，系统中类的个数将成对增加，在一定程度上增加了系统的复杂度，有更多的类需要编译和运行，会给系统带来一些额外的开销。
2.由于考虑到系统的可扩展性，需要引入抽象层，在客户端代码中均使用抽象层进行定义，增加了系统的抽象性和理解难度，且在实现时可能需要用到DOM、反射等技术，增加了系统的实现难度。
### 3.4工厂方法模式的适用环境
在以下情况下可以使用工厂方法模式：
1. 一个类不知道它所需要的对象的类：在工厂方法模式中，客户端不需要知道具体产品类的类名，只需要知道所对应的工厂即可，具体的产品对象由具体工厂类创建；客户端需要知道创建具体产品的工厂类。
2. 一个类通过其子类来指定创建哪个对象：在工厂方法模式中，对于抽象工厂类只需要提供一个创建产品的接口，而由其子类来确定具体要创建的对象，利用面向对象的多态性和里氏代换原则，在程序运行时，子类对象将覆盖父类对象，从而使得系统更容易扩展。
3. 将创建对象的任务委托给多个工厂子类中的某一个，客户端在使用时可以无需关心是哪一个工厂子类创建产品子类，需要时再动态指定，可将具体工厂类的类名存储在配置文件或数据库中。
### 3.5举例
工厂方法为工厂类定义了接口，用多态来削弱了工厂类的职能，以下是工厂接口的定义：
```java
public interface Factory {
    public Product produce();
}
```

我们再来定义一个产品接口
```java
public interface Product{}
```

以下是实现了产品接口的产品类：
```java
public class Tv implements Product {
    public Tv() {
        System.out.println("电视被制造了");
    }
}

public class Car implements Product {
    public Car(){
        System.out.println("汽车被制造了");
    }
}

```
接下来，就是工厂方法的核心部分，也就是具体创建产品对象的具体工厂类，
```java

public class TvFactory implements Factory {
    @Override
    public Product produce() {
        return new Tv();
    }
}

public class CarFactory implements Factory {
    @Override
    public Product produce() {
        return new Car();
    }
}

```
- - -
## 四、抽象工厂模式
### 4.1抽象工厂模式的介绍
> 抽象工厂模式提供一个创建一系列或相互依赖的对象的接口，而无需指定它们具体的类。
### 4.2抽象工厂模式角色
抽象工厂模式涉及到的系统角色
1. 抽象工厂（AbstractFactory）角色：担任这个角色的是工厂方法模式的核心，它是与应用系统的商业逻辑无关的。通常使用Java 接口或者抽象Java 类实现，而所有的具体工厂类必须实现这个Java 
接口或继承这个抽象Java 类。
2. 具体工厂类（Conrete Factory）角色：这个角色直接在客户端的调用下创建产品的实例。这个角色含有选择合适的产品对象的逻辑，而这个逻辑是与应用系统的商业逻辑紧密相关的。通常使用具体Java 类实现这个角色。
3. 抽象产品（Abstract Product）角色：担任这个角色的类是工厂方法模式所创建的对象的父类，或它们共同拥有的接口。通常使用Java 接口或者抽象Java 类实现这一角色。
4. 具体产品（Concrete Product）角色：抽象工厂模式所创建的任何产品对象都是某一个具体产品类的实例。这是客户端最终需要的东西，其内部一定充满了应用系统的商业逻辑。通常使用具体Java 类实现这个角色。
### 4.3抽象工厂模式的优缺点
**优点：**
1. 隔离了具体类的生成，使得用户不需要知道什么被创建了。
2. 当一个产品族中的多个对象被设计成一起工作时，它能够保证客户端始终只使用同一个产品族中的对象。
**缺点：**
1. 添加新的产品对像时，难以扩展抽象工厂以便生产新种类的产品。
### 4.4抽象工厂模式的适用环境
1. 一个系统不应当依赖于产品类实例如何被创建、组合和表达的细节。这对于所有形态的工厂模式都是重要的；
2. 一个系统的产品有多于一个的产品族，而系统只消费其中某一族的产品；
3. 同属于同一个产品族的产品是在一起使用的，这一约束必须要在系统的设计中体现出来；
4. 系统提供一个产品类的库，所有的产品以同样的接口出现，从而使客户端不依赖于实现。
### 4.5举例
将汽车和电视定义两个接口，对他们进行分类
```java

public interface Car {
}

public interface Tv {
}
```

创建汽车和电视的具体产品
```java

public class Audi implements Car {
    public Audi(){
        System.out.println("奥迪车生产出来了");
    }
}

public class BMW implements Car {
    public BMW(){
        System.out.println("一辆宝马生产出来了");
    }
}

public class LeTv implements Tv {
    public LeTv() {
        System.out.println("乐视电视被生产出来了");
    }
}

public class Sony implements Tv {
    public Sony(){
        System.out.println("索尼电视机被生产出来了");
    }
}
```

接下来定义工厂行为接口
```java

public interface Factory {
    public Tv produceTv();

    public Car produceCar();
}
```

具体工厂类：
```java
public class FactoryA implements Factory {
    @Override
    public Tv produceTv() {
        return new LeTv();
    }

    @Override
    public Car produceCar() {
        return new BMW();
    }
}

public class FactoryB implements Factory {

    @Override
    public Tv produceTv() {
        return new Sony();
    }

    @Override
    public Car produceCar() {
        return new Audi();
    }
}
```

测试类:
```java

public class Test {
    public static void main(String[] args) {
        FactoryA factoryA = new FactoryA();
        factoryA.produceCar();

        FactoryB factoryB = new FactoryB();
        factoryB.produceTv();
    }
}
```



