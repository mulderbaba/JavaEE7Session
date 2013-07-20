
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mertcaliskan
 */
@WebServlet(urlPatterns="/asyncServlet", asyncSupported=true)
public class AsyncServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        // start async
        AsyncContext ac = req.startAsync();
        // set up async listener
        ac.addListener(new AsyncListener() {
            public void onComplete(AsyncEvent event) throws IOException {
                event.getSuppliedResponse().getOutputStream().print("Complete");
            }
            public void onError(AsyncEvent event) {
                System.out.println(event.getThrowable());
            }
            public void onStartAsync(AsyncEvent event) {
            }
            public void onTimeout(AsyncEvent event) {
                System.out.println("my asyncListener.onTimeout");
            }
        });

        // set up ReadListener to read data for processing
        ServletInputStream input = req.getInputStream();
        ReadListener readListener = new ReadListenerImpl(input, res, ac);
        input.setReadListener(readListener);
    }
}


class ReadListenerImpl implements ReadListener {
    private ServletInputStream input = null;
    private HttpServletResponse res = null;
    private AsyncContext ac = null;
    // store the processed data to be sent back to client later
    private Queue queue = new LinkedBlockingQueue();

    ReadListenerImpl(ServletInputStream in, HttpServletResponse r,
            AsyncContext c) {
        input = in;
        res = r;
        ac = c;
    }

    public void onDataAvailable() throws IOException {
        StringBuilder sb = new StringBuilder();
        int len = -1;
        byte b[] = new byte[1024];
        // We need to check input#isReady before reading data.
        // The ReadListener will be invoked again when 
        // the input#isReady is changed from false to true
        while (input.isReady() && (len = input.read(b)) != -1) {
            String data = new String(b, 0, len);
            sb.append(data);
        }
        queue.add(sb.toString());
    }

    public void onAllDataRead() throws IOException {
        // now all data are read, set up a WriteListener to write
        ServletOutputStream output = res.getOutputStream();
        WriteListener writeListener = new WriteListenerImpl(output, queue, ac);
        output.setWriteListener(writeListener);
    }

    public void onError(final Throwable t) {
        ac.complete();
        t.printStackTrace();
    }
}


class WriteListenerImpl implements WriteListener {
    private ServletOutputStream output = null;
    private Queue queue = null;
    private AsyncContext ac = null;

    WriteListenerImpl(ServletOutputStream sos, Queue q, AsyncContext c) {
        output = sos;
        queue = q;
        ac = c;
    }

    public void onWritePossible() throws IOException {
        // write while there is data and is ready to write
        while (queue.peek() != null && output.isReady()) {
            String data = (String) queue.poll();
            System.out.print(data);
        }
        // complete the async process when there is no more data to write
        if (queue.peek() == null) {
            ac.complete();
        }
    }

    public void onError(final Throwable t) {
        ac.complete();
        t.printStackTrace();
    }
}