package com.ishaangarg.bottlemessage;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ishaan on 30-Aug-15.
 */
public class Looker extends AccessibilityService {

    static final String TAG = "LOOKER SERVICE";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "hola!");

        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        }

        // Grab the parent of the view that fired the event.
        AccessibilityNodeInfo parent = getListItemNodeInfo(source);
        if (parent == null) {
            return;
        }
        traverse(parent);
    }

    public void traverse(AccessibilityNodeInfo parent) {
        if (parent != null) {
            int len = parent.getChildCount();
            for (int i = 0; i < len - 1; i++) {
                CharSequence text = parent.getChild(i).getText();
                if (text.toString().equalsIgnoreCase("yogendra saxena")) {
                    Log.d(TAG, "YOGI MIL GAYA***********************");
                    Toast.makeText(this, "Yogi Mil Gaya", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            for (int i = 0; i < len - 1; i++) {
                traverse(parent.getChild(i));
            }
        }
    }



    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private AccessibilityNodeInfo getListItemNodeInfo(AccessibilityNodeInfo source) {
        AccessibilityNodeInfo current = source;
        while (true) {
            AccessibilityNodeInfo parent = current.getParent();
            if (parent == null) {
                return null;

                //TODO MAYBE, we can check if parent is a textview & only then proceed further
            } else {
                Log.d(TAG, "parent: " + parent.getClassName());
                return current;
            }
            /*// NOTE: Recycle the infos.
            AccessibilityNodeInfo oldCurrent = current;
            current = parent;
            oldCurrent.recycle();*/
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO ONLY FOR DEBUGGING
        return START_NOT_STICKY; //Will not restart if stopped explicitly
    }
}
