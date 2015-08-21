/*
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.runtime.array;

public abstract class BasicArrayMirror implements ArrayMirror {

    @Override
    public Object copyArrayAndMirror() {
        return copyArrayAndMirror(getLength());
    }

    @Override
    public Object[] getBoxedCopy() {
        return getBoxedCopy(getLength());
    }

    @Override
    public Object[] getBoxedCopy(int newLength) {
        final Object[] boxed = new Object[newLength];
        copyTo(boxed, 0, 0, Math.min(getLength(), newLength));
        return boxed;
    }

}
