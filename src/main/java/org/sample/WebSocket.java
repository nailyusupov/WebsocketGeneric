/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.sample.data.Figure;
import org.sample.data.FigureDecoder;
import org.sample.data.FigureEncoder;

/**
 *
 * @author User
 */
@ServerEndpoint(value="/websocket", encoders = {FigureEncoder.class}, decoders = {FigureDecoder.class})
public class WebSocket {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void broadcastFigure(Figure figure, Session session) throws IOException, EncodeException {
        System.out.println("broadcastFigure: " + figure);
        for (Session peer : peers) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendObject(figure);
            }
        }
    }

    @OnOpen
    public void onOpen(Session peer) {
        System.out.println("Session opened on server WebSocket, sessionId: "+peer.getId());
        peers.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        System.out.println("Session closed on server WebSocket");
        peers.remove(peer);
    }
    
    @OnError
    public void onError(Session peer, Throwable t) {
        System.out.println("WebSocket server error: "+t.toString());
        peers.remove(peer);
    }
}
