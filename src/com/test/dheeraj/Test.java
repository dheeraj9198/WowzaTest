package com.test.dheeraj;

import com.wowza.util.BufferUtils;
import com.wowza.util.MD5DigestUtils;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.http.IHTTPRequest;
import com.wowza.wms.http.IHTTPResponse;
import com.wowza.wms.httpstreamer.cupertinostreaming.httpstreamer.HTTPStreamerSessionCupertino;
import com.wowza.wms.module.ModuleBase;

/**
 * Created by dheeraj on 13/8/15.
 */

import com.wowza.wms.amf.*;
import com.wowza.wms.client.*;
import com.wowza.wms.request.*;
import com.wowza.wms.stream.*;
import com.wowza.wms.vhost.IVHost;
import org.apache.mina.common.IoSession;

public class Test extends ModuleBase {
    public void play(IClient client, RequestFunction function, AMFDataList params) {
        boolean doit = true;
        IMediaStream stream = getStream(client, function);
        String streamName = getParamString(params, PARAM1);

        // check to see if client should be allowed to play content
        doit = true;

        if (doit)
            invokePrevious(this, client, function, params);
        else
            sendStreamOnStatusError(stream, "NetStream.Play.Failed", "Access denied: " + streamName);
    }

    public void onHTTPCupertinoEncryptionKeyRequest(HTTPStreamerSessionCupertino httpCupertinoStreamingSession, IHTTPRequest req, IHTTPResponse resp)
    {

        boolean isGood = true;
        String ipAddress = httpCupertinoStreamingSession.getIpAddress();
        String queryStr = req.getQueryString();
        String referrer = httpCupertinoStreamingSession.getReferrer();
        String cookieStr = httpCupertinoStreamingSession.getCookieStr();
        String userAgent = httpCupertinoStreamingSession.getUserAgent();
        String sessionId = httpCupertinoStreamingSession.getSessionId();
        IApplicationInstance appInstance = httpCupertinoStreamingSession.getAppInstance();
        String streamName = httpCupertinoStreamingSession.getStreamName();

        getLogger().info("ModuleEncryptionHandlerCupertinoStreaming.onHTTPCupertinoEncryptionKeyRequest[" + appInstance.getContextStr() + "/" + httpCupertinoStreamingSession.getStreamName() + "]: accept:" + isGood);
        if (!isGood) {
            httpCupertinoStreamingSession.rejectSession();
        }
    }

    public void onHTTPCupertinoEncryptionKeyCreateLive(IApplicationInstance appInstance, String streamName, byte[] encKey)
    {

        String mySharedSecret = appInstance.getProperties().getPropertyStr("cupertinoEncryptionSharedSecret", "");

        String hashStr = mySharedSecret+":"+appInstance.getApplication().getName()+":"+appInstance.getName()+":"+streamName;

        byte[] tmpBytes = MD5DigestUtils.generateHashBytes(hashStr);
        if (tmpBytes != null)
            System.arraycopy(tmpBytes, 0, encKey, 0, encKey.length);

        getLogger().info("ModuleEncryptionHandlerCupertinoStreaming.onHTTPCupertinoEncryptionKeyCreateLive["+appInstance.getContextStr()+"/"+streamName+"]: *"+ BufferUtils.encodeHexString(encKey).substring(28));
    }

    public void onHTTPCupertinoEncryptionKeyCreateVOD(HTTPStreamerSessionCupertino httpCupertinoStreamingSession, byte[] encKey)
    {
        String ipAddress = httpCupertinoStreamingSession.getIpAddress();
        String queryStr = httpCupertinoStreamingSession.getQueryStr();
        String referrer = httpCupertinoStreamingSession.getReferrer();
        String cookieStr = httpCupertinoStreamingSession.getCookieStr();
        String userAgent = httpCupertinoStreamingSession.getUserAgent();

        IApplicationInstance appInstance = httpCupertinoStreamingSession.getAppInstance();
        String streamName = httpCupertinoStreamingSession.getStreamName();
        String sessionId = httpCupertinoStreamingSession.getSessionId();

        String mySharedSecret = appInstance.getProperties().getPropertyStr("cupertinoEncryptionSharedSecret", "");

        String hashStr = mySharedSecret+":"+(httpCupertinoStreamingSession.isHTTPOrigin() ? "" : sessionId+":")+appInstance.getApplication().getName()+":"+appInstance.getName()+":"+httpCupertinoStreamingSession.getStreamName();

        byte[] tmpBytes = MD5DigestUtils.generateHashBytes(hashStr);
        if (tmpBytes != null)
            System.arraycopy(tmpBytes, 0, encKey, 0, encKey.length);
        getLogger().info("ModuleEncryptionHandlerCupertinoStreaming.onHTTPCupertinoEncryptionKeyCreateVOD["+appInstance.getContextStr()+"/"+httpCupertinoStreamingSession.getStreamName()+"]: *"+BufferUtils.encodeHexString(encKey).substring(28));
    }

    public void onMediaFile(long l, org.apache.mina.common.IoSession ioSession, com.wowza.wms.vhost.IVHost ivHost, com.wowza.wms.http.IHTTPRequest ihttpRequest, com.wowza.wms.http.IHTTPResponse ihttpResponse)
    {
        long k = l;
        IoSession ioSession1 = ioSession;
        IVHost ivHost1 = ivHost;
        IHTTPRequest ihttpRequest1 = ihttpRequest;
        IHTTPResponse ihttpResponse1 = ihttpResponse;
        getLogger().info("");
    }

    public void onPlaylist(long l, org.apache.mina.common.IoSession ioSession, com.wowza.wms.vhost.IVHost ivHost, com.wowza.wms.http.IHTTPRequest ihttpRequest, com.wowza.wms.http.IHTTPResponse ihttpResponse)
    {
        getLogger().info("");
    }



}