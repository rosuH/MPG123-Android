## MPG123 在 Android 上的移植版

MPG123 是一个轻量并且小巧的，可用于解码和播放 MP3 文件的库。

本依赖库提供了部分常见的 API，可供 Java 层调用：

```java
int readFrame(short[] buffer)
boolean skipFrame()
int seek(float offset)
float getPosition()
int getNumChannels()
int getRate()
float getDuration()
int getSeekFrameOffset(float position)
void feed(byte[] buffer, int count)
void completeStream()
boolean isStreamComplete()
```

最常见的使用方法：调用`readFrame()`方法来逐帧解码 MP3 得到 PCM 数据。

使用 MPG123 的好处在于：API 简单并且速度快。

目前 Android 平台大部分可用的解码为 Android 自带的`MediaExtractor` 和 FFmpeg。

前者使用较为繁琐，后者不自定 API 的话无法做到逐帧解码和读取。

所以如果你想要快速接入解码 MP3 的功能，那推荐你试试 MPG123。

此依赖库支持的特性：

-   常见的 libmpg123 API 实现
-   支持常见的架构：armeabi、armeabi-v7a、arm64-v8a、x86、x86_64
    -   推荐使用 64 位的 arm64-v8a，自测加载时间相较 armeabi-v7a 降低 22.3%
-   利用 Gradle 快速接入已有项目

## 接入

项目的 `build.gradle`。

```
allprojects {
  repositories {
  ...
    maven { url 'https://jitpack.io' }
  }
}
```

模块依赖：

```
dependencies {
  implementation 'com.github.rosuH:MPG123-Android:${latest_version}'
}
```

## 使用

看看我写的 [Demo](https://github.com/rosuH/MPG123-Android/blob/master/app/src/main/java/me/rosuh/decoder/MainActivity.kt)。



## 其他

[MPG123](https://www.mpg123.de/) 是一个基于 LGPL 2.1 开源的自由软件，原则上可以被商用软件和闭源软件所使用，但不允许进行二次开发。一般来说，本库没有对 MPG123 源码进行修改和变动，仅是编写 API 所以应该没有风险。

本库使用更宽松的 MIT 协议，所以无需有更多担心。

本库部分 API 和编译描述文件实现参考下列两个开源库，在此致谢🙏。

参考：

-   [android-mp3decoders](https://github.com/thasmin/android-mp3decoders)
-   [SDL2_mixer](https://github.com/emscripten-ports/SDL2_mixer/)


