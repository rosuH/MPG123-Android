package me.rosuh.libmpg123;

public class MPG123 {
    static {
        System.loadLibrary("mpg123");
    }

    protected static native int init();

    protected static native long openFile(String filename);

    protected static native void delete(long handle);

    protected static native boolean skipFrame(long handle);

    protected static native int seek(long handle, float offsetInSeconds, int mode);

    protected static native float getPosition(long handle);

    protected static native int getNumChannels(long handle);

    protected static native int getRate(long handle);

    protected static native float getDuration(long handle);

    protected static native long openStream();

    protected static native void feed(long handle, byte[] buffer, int count);

    protected static native short[] readFrame(long handle);

    protected static native int getSeekFrameOffset(long handle, float position);

    protected static native long getTimeFrame(long handle, double sec);

    protected static native long seekFrame(long handle, float offset, int mode);

    private boolean _streamComplete = false;

    private long _handle = 0;

    public MPG123() {
        MPG123.init();
        _handle = openStream();
    }

    public MPG123(String filename) {
        MPG123.init();
        _handle = openFile(filename);
        _streamComplete = true;
    }

    public void close() {
        if (_handle != 0)
            MPG123.delete(_handle);
    }

    /**
     * 返回一帧解码的数据 buffer；如果返回空 buffer 表示解码完成；返回 null 表示错误
     * @return decode frame buffer if successfully; return a empty buffer if decode done , error would be a null
     *
     */
    public short[] readFrame() {
        return MPG123.readFrame(_handle);
    }

    public boolean skipFrame() {
        return MPG123.skipFrame(_handle);
    }
    
    public int seek(float offset) {
        return seek(offset, SeekMode.SEEK_SET);
    }

    /**
     * Seek with modes:
     * SEEK_SET: set position to (or near to) specified offset
     * SEEK_CUR: change position by offset from now
     * SEEK_END: set position to offset from end
     * https://www.mpg123.de/api/group__mpg123__seek.shtml
     * @author rosuh@qq.com
     * @date 2021/8/30
    */
    public int seek(float offset, SeekMode mode) {
        int nativeMode = 0;
        switch (mode) {
            case SEEK_CUR:
                nativeMode = 0;
                break;
            case SEEK_SET:
                nativeMode = 1;
                break;
            case SEEK_END:
                nativeMode = 2;
                break;
        }
        return MPG123.seek(_handle, offset, nativeMode);
    }

    public float getPosition() {
        return MPG123.getPosition(_handle);
    }

    public int getNumChannels() {
        return MPG123.getNumChannels(_handle);
    }

    public int getRate() {
        return MPG123.getRate(_handle);
    }

    public float getDuration() {
        return MPG123.getDuration(_handle);
    }

    public int getSeekFrameOffset(float position) {
        return MPG123.getSeekFrameOffset(_handle, position);
    }

    public void feed(byte[] buffer, int count) {
        MPG123.feed(_handle, buffer, count);
    }

    public void completeStream() {
        _streamComplete = true;
    }

    public boolean isStreamComplete() {
        return _streamComplete;
    }

    /**
     * Return a MPEG frame offset corresponding to an offset in seconds.
     * This assumes that the samples per frame do not change in the file/stream,
     * which is a good assumption for any sane file/stream only.
     * REF: https://www.mpg123.de/api/group__mpg123__seek.shtml
     * @author hi@rosuh.me
     * @since 0.1.2
    */
    public long getTimeFrame(double sec) {
        return MPG123.getTimeFrame(_handle, sec);
    }

    /**
     * Seek to a desired MPEG frame offset. Usage is modelled afer the standard lseek().
     * REF: https://www.mpg123.de/api/group__mpg123__seek.shtml
     * @author hi@rosuh.me
     * @since 0.1.2
    */
    public long seekFrame(float offset, SeekMode mode) {
        int nativeMode = 0;
        switch (mode) {
            case SEEK_CUR:
                nativeMode = 0;
                break;
            case SEEK_SET:
                nativeMode = 1;
                break;
            case SEEK_END:
                nativeMode = 2;
                break;
        }
        return MPG123.seekFrame(_handle, offset, nativeMode);
    }
}
