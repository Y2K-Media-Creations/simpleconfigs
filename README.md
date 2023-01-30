
# Simple Configs

Simple Config Utilites for projects

## Usage
```java
// load a config
SimpleConfig config = SimpleConfigs.loadConfig("test.yml");
```
```java
// get a config
SimpleConfig config = SimpleConfigs.getConfig("test.yml");
```
```java
// get a value
String value = config.get("key", String.class);
```
```java
// set a value
String key = "key";
String value = "value";

config.set(key, value);
```
```java
// save a config
config.save();
```
```java
// reload a config
config.reload();
```

## Constructing new config types
Config types are managed by "YamlConverter"'s which are registered to the "YamlConverterManager". The "YamlConverterManager" is a singleton and can be accessed by "YamlConverterManager.getInstance()". The "YamlConverterManager" is used to register new "YamlConverter"'s and to get "YamlConverter"'s for a specific type.

### Registering a new SimpleYamlConverter
A SimpleYamlConverter is a converter that has 4 parameters in a constructor, 2 Functions, 1 to convert, 1 to revert, 2 Classes's to specify the type of the object to convert and the type of the object to revert to.

In the example below we are going to create a object that needs to be converted to a string and back to the object. The object is a simple wrapper for a string and int.
```java
public class TestObject {
    
    String string;
    int integer;

    public TestObject(String string, int integer) {
        this.string = string;
        this.integer = integer;
    }

    public String toString() {
        return string + ":" + integer;
    }

    public static TestObject fromString(String string) {
        String[] split = string.split(":");
        return new TestObject(split[0], Integer.parseInt(split[1]));
    }
}
```
```java
// create the converter
public static final YamlConverter<TestObject, String> TEST_OBJECT_CONVERTER = new SimpleYamlConverter<>(
        TestObject::toString,
        TestObject::fromString,
        TestObject.class,
        String.class
);
```
```java
// register the converter
YamlConverterManager.getInstance().registerConverter(TEST_OBJECT_CONVERTER);
// or
SimpleConfigs.registerConverter(TEST_OBJECT_CONVERTER);
```

### Registering a new LikeYamlConverter
A LikeYamlConverter is a converter that doesn't need special formatting or functions to  be put into yaml files at works 1 to 1. Like a String or Integer.
Examples of LikeYamlConverter's are: String, Integer, Double, Float, Boolean, etc.
In the example below we are going to create a LikeYamlConverter for String.
```java
// create the converter
public static final YamlConverter<String, String> STRING_CONVERTER = new LikeYamlConverter<>(String.class);
```
```java
// register the converter
YamlConverterManager.getInstance().registerConverter(STRING_CONVERTER);
// or
SimpleConfigs.registerConverter(STRING_CONVERTER);
```

### Creating a custom YamlConverter
It may be that none of the YamlConverter templates avaliable like SimpleYamlConverter or LikeYamlConverter are not suited for your needs. In that case you can create your own
YamlConverter. This is done by implmenting the YamlConverter interface. The YamlConverter interface has 4 methods that need to be implemented. The methods are:
```java
O convert(I input);

I revert(O configValue);

TypeToken<I> getInputToken();

TypeToken<O> getOutputToken();
```

Once all the methods are implemented you can register the YamlConverter to the YamlConverterManager.
```java
// create the converter
public static final YamlConverter<TestObject, String> TEST_OBJECT_CONVERTER = new YamlConverter<TestObject, String>() {
    @Override
    public TestObject convert(String input) {
        String[] split = input.split(":");
        return new TestObject(split[0], Integer.parseInt(split[1]));
    }

    @Override
    public String revert(TestObject configValue) {
        return configValue.toString();
    }

    @Override
    public TypeToken<TestObject> getInputToken() {
        return TypeToken.of(TestObject.class);
    }

    @Override
    public TypeToken<String> getOutputToken() {
        return TypeToken.of(String.class);
    }
};
```
```java
// register the converter
YamlConverterManager.getInstance().registerConverter(TEST_OBJECT_CONVERTER);
// or
SimpleConfigs.registerConverter(TEST_OBJECT_CONVERTER);
```

## Depending on SimpleConfigs
### Maven
```xml
<repositories>
    <repository>
      <id>miles-repos-releases</id>
      <url>https://maven.miles.sh/snapshots</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>sh.miles</groupId>
        <artifactId>simpleconfigs</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

### Gradle
```groovy
repositories {
    maven {
        url "https://maven.miles.sh/snapshots"
    }
}   

dependencies {
    compile 'sh.miles:simpleconfigs:1.0-SNAPSHOT'
}
```

## Building SimpleConfigs
### Maven
First clone the repository
then go into the directory with the pom.xml file and run
```bash
mvn clean install
```
