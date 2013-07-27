/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.whiteboardapp;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author nb
 */
public class FigureEncoder implements Encoder.Text<Figure> {

    @Override
    public String encode(Figure figure) throws EncodeException {
        return figure.getJson().toString();
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
    
}
