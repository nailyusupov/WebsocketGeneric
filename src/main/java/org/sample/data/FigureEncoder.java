/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.data;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author User
 */
public class FigureEncoder implements Encoder.Text<Figure> {

    @Override
    public String encode(Figure t) throws EncodeException {
        return t.getJson().toString();
    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("init encoder");
    }

    @Override
    public void destroy() {
        System.out.println("destroy encoder");
    }

}
