cmake_minimum_required(VERSION 3.4.1)

MESSAGE(STATUS "Checking ABI " ${ANDROID_ABI})
MESSAGE(STATUS "Checking ABI " ${CMAKE_ANDROID_ARCH_ABI})
set(MPG123_SOURCE_DIR "src/main/jni/mpg123-1.28.2/")

if (${ANDROID_ABI} EQUAL "armeabi")
    set(SRC_LIST
            ${MPG123_SOURCE_DIR}/src/libmpg123/stringbuf.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy2utf8.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/ntom.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_8bit.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer1.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer2.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer3.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct36_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct64_neon64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_neon64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_neon64_s32.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_neon64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_neon64_s32.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct64_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_s32.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_real.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/dither.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/getcpuflags_arm.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/check_neon.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/feature.c)
    set(DECODER_CFLAGS "-DOPT_NEON -DREAL_IS_FLOAT")
elseif (${ANDROID_ABI} EQUAL "armeabi-v7a")
    set(SRC_LIST
            ${MPG123_SOURCE_DIR}/src/libmpg123/stringbuf.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy2utf8.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/ntom.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_8bit.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer1.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer2.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer3.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct36_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct64_neon64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_neon64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_neon64_s32.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_neon64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_neon64_s32.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct64_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_s32.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_real.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/dither.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/getcpuflags_arm.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/check_neon.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/feature.c)
    set(DECODER_CFLAGS "-DOPT_MULTI -DOPT_GENERIC -DOPT_GENERIC_DITHER -DOPT_NEON64 -DREAL_IS_FLOAT")
elseif (${ANDROID_ABI} EQUAL "arm64-v8a")
    set(SRC_LIST
            ${MPG123_SOURCE_DIR}/src/libmpg123/stringbuf.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy2utf8.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/ntom.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_8bit.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer1.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer2.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer3.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct36_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct64_neon64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_neon64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_neon64_s32.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_neon64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_neon64_s32.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct64_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_neon64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_s32.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_real.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/dither.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/getcpuflags_arm.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/check_neon.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/feature.c)
    set(DECODER_CFLAGS "-DOPT_MULTI -DOPT_GENERIC -DOPT_GENERIC_DITHER -DOPT_NEON64 -DREAL_IS_FLOAT")
elseif (${ANDROID_ABI} EQUAL "x86")
    set(SRC_LIST
            ${MPG123_SOURCE_DIR}/src/libmpg123/feature.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy2utf8.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer1.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer2.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer3.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/ntom.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/stringbuf.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_8bit.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_real.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_s32.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/dither.c)
    set(DECODER_CFLAGS "-DOPT_GENERIC -DREAL_IS_FLOAT")
elseif (${ANDROID_ABI} EQUAL "x86_64")
    set(SRC_LIST
            ${MPG123_SOURCE_DIR}/src/libmpg123/stringbuf.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy.h
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy2utf8.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy2utf8.h
            ${MPG123_SOURCE_DIR}/src/libmpg123/ntom.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth.h
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_8bit.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_8bit.h
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer1.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer2.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer3.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_s32.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_real.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct36_x86_64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct64_x86_64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_x86_64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_x86_64_s32.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_x86_64_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_x86_64_s32.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_x86_64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct64_x86_64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_x86_64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dither.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/dither.h
            ${MPG123_SOURCE_DIR}/src/libmpg123/getcpuflags_x86_64.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct36_avx.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct64_avx_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_avx_float.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_avx_s32.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/dct64_avx.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_stereo_avx.S
            ${MPG123_SOURCE_DIR}/src/libmpg123/feature.c)
    set(DECODER_CFLAGS "-DOPT_MULTI -DOPT_X86_64 -DOPT_GENERIC -DOPT_GENERIC_DITHER -DREAL_IS_FLOAT -DOPT_AVX")
elseif (${ANDROID_ABI} EQUAL "mips")
    set(SRC_LIST
            ${MPG123_SOURCE_DIR}/src/libmpg123/stringbuf.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy2utf8.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/ntom.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_8bit.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer1.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer2.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer3.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_s32.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_real.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/feature.c)
    set(DECODER_CFLAGS "-DOPT_GENERIC -DREAL_IS_FLOAT")
elseif (${ANDROID_ABI} EQUAL "mips64")
    set(SRC_LIST
            ${MPG123_SOURCE_DIR}/src/libmpg123/stringbuf.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/icy2utf8.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/ntom.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_8bit.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer1.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer2.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/layer3.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_s32.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/synth_real.c
            ${MPG123_SOURCE_DIR}/src/libmpg123/feature.c)
    set(DECODER_CFLAGS "-DOPT_GENERIC -DREAL_IS_FLOAT")
endif ()

add_definitions(${DECODER_CFLAGS})

add_library( # Specifies the name of the library.
        mpg123-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        ${MPG123_SOURCE_DIR}/src/libmpg123/parse.c
        ${MPG123_SOURCE_DIR}/src/libmpg123/frame.c
        ${MPG123_SOURCE_DIR}/src/libmpg123/format.c
        ${MPG123_SOURCE_DIR}/src/libmpg123/dct64.c
        ${MPG123_SOURCE_DIR}/src/libmpg123/equalizer.c
        ${MPG123_SOURCE_DIR}/src/libmpg123/id3.c
        ${MPG123_SOURCE_DIR}/src/libmpg123/optimize.c
        ${MPG123_SOURCE_DIR}/src/libmpg123/readers.c
        ${MPG123_SOURCE_DIR}/src/libmpg123/tabinit.c
        ${MPG123_SOURCE_DIR}/src/libmpg123/libmpg123.c
        ${MPG123_SOURCE_DIR}/src/libmpg123/index.c
        ${MPG123_SOURCE_DIR}/src/compat/compat_str.c
        ${MPG123_SOURCE_DIR}/src/compat/compat.c
        src/main/jni/me_rosuh_libmpg123_MPG123.c
        ${SRC_LIST}
        )

include_directories(
        src/main/jni/mpg123_android
        ${MPG123_SOURCE_DIR}/src
        ${MPG123_SOURCE_DIR}/src/compat
        ${MPG123_SOURCE_DIR}/src/libmpg123
)

find_library( # Defines the name of the path variable that stores the
        # location of the NDK library.
        log-lib

        # Specifies the name of the NDK library that
        # CMake needs to locate.
        log)

target_link_libraries( # Specifies the target library.
        mpg123-lib

        # Links the log library to the target library.
        ${log-lib})