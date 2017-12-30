package com.eleven.http.gdo.chan;

import com.eleven.http.gdo.Context;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:55
 * @des <p class="note">File Note</p>
 */

public class GlobalChanNode extends ChanNode{

    private ChanNode[] mGlobalChanNodes;

    public GlobalChanNode(ChanNode ...chanNodes) {
        mGlobalChanNodes = chanNodes;
    }

    @Override
    public void run(Context ctx) {
        ChanNode[] globalChanNodes = mGlobalChanNodes;

        if (globalChanNodes == null) { return;}
        for (ChanNode item : globalChanNodes) {
            if (item == null) { continue;}

            item.beforeCall(isBeforeCall());
            item.exec(ctx);
        }
    }
}
