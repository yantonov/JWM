package org.jetbrains.jwm;

import java.util.function.*;
import org.jetbrains.annotations.*;

public class Log {
    @ApiStatus.Internal public static boolean _verbose = false;
    @ApiStatus.Internal public static Consumer<String> _listener;

    public static void log(String message) {
        assert _onUIThread();
        if (_listener != null)
            _listener.accept(message);
    }

    public static void verbose(String message) {
        assert _onUIThread();
        if (_listener != null && _verbose)
            _listener.accept(message);
    }

    public static void setLogger(@Nullable Consumer<String> listener) {
        assert _onUIThread();
        _listener = listener;
        _nSetListener(listener);
    }

    public static void setVerbose(boolean enabled) {
        assert _onUIThread();
        _verbose = enabled;
        _nSetVerbose(enabled);
    }
    
    @ApiStatus.Internal public static boolean _onUIThread() {
        return App._onUIThread();
    }

    @ApiStatus.Internal public static native void _nSetVerbose(boolean enabled);
    @ApiStatus.Internal public static native void _nSetListener(Consumer<String> listener);
}