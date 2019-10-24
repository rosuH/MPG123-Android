## MPG123 Migration on Android

MPG123 is a lightweight and compact library for decoding and playing MP3 files.

This dependency library provides some common APIs that can be called by the Java layer:

```java
Int readFrame(short[] buffer)
Boolean skipFrame()
Int seek(float offset)
Float getPosition()
Int getNumChannels()
Int getRate()
Float getDuration()
Int getSeekFrameOffset(float position)
Void feed(byte[] buffer, int count)
Void completeStream()
Boolean isStreamComplete()
```

The most common use: call the `readFrame()` method to decode MP3 frame by frame to get PCM data.

The advantage of using MPG123 is that the API is simple and fast.

Most of the currently available decodings for the Android platform are `MediaExtractor` and FFmpeg that come with Android.

The former is more cumbersome to use, and the latter cannot decode and read frame by frame without custom API.

So if you want to quickly access the function of decoding MP3, then you are recommended to try MPG123.

Features supported by this dependency library:

- Common libmpg123 API implementation
- Support common architecture: armeabi, armeabi-v7a, arm64-v8a, x86, x86_64
    - 64-bit arm64-v8a is recommended, self-test loading time is reduced by 22.3% compared to armeabi-v7a
- Quick access to existing projects with Gradle

## access

The project's `build.gradle`.

```
Allprojects {
  Repositories {
  ...
  Maven { url 'https://jitpack.io' }
  }
}
```

Module dependency:

```
dependencies {
  Implementation 'com.github.rosuH:MPG123-Android:${latest_version}'
}
```

## Usage

Take a look at [Demo][https://github.com/rosuH/MPG123-Android/blob/master/app/src/main/java/me/rosuh/decoder/MainActivity.kt) I wrote.

## Other

[MPG123](https://www.mpg123.de/) is a free software licensed under LGPL 2.1. In principle, it can be used by commercial software and closed source software, but secondary development is not allowed. In general, the library does not modify and change the MPG123 source code, only the API is written so there should be no risk.

This library uses a more relaxed MIT protocol, so there is no need to worry more.

This library part of the API and compilation description file implementation reference to the following two open source libraries, I would like to thank you.

Reference:

- [android-mp3decoders](https://github.com/thasmin/android-mp3decoders)
- [SDL2_mixer](https://github.com/emscripten-ports/SDL2_mixer/)
