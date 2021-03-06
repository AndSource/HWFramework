package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

@Deprecated
public class IdentityOutputStream extends OutputStream {
    private boolean closed = false;
    private final SessionOutputBuffer out;

    public IdentityOutputStream(SessionOutputBuffer out2) {
        if (out2 != null) {
            this.out = out2;
            return;
        }
        throw new IllegalArgumentException("Session output buffer may not be null");
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.out.flush();
        }
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void write(byte[] b, int off, int len) throws IOException {
        if (!this.closed) {
            this.out.write(b, off, len);
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void write(int b) throws IOException {
        if (!this.closed) {
            this.out.write(b);
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }
}
