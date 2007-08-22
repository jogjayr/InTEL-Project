//
// $Id$
//
// BUI - a user interface library for the JME 3D engine
// Copyright (C) 2005-2006, Michael Bayne, All Rights Reserved
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package com.jmex.bui.tests.text;

import junit.framework.Test;
import junit.framework.TestCase;

import com.jmex.bui.text.Document;

/**
 * A unit test for {@link Document}.
 */
public class DocumentUTest extends TestCase
{
    public static Test suite ()
    {
        return new DocumentUTest("testEdit");
    }

    public static void main (String[] args)
    {
        try {
            DocumentUTest test = new DocumentUTest("testEdit");
            test.runTest();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    public DocumentUTest (String method)
    {
        super(method);
    }

    public void testEdit ()
    {
        Document doc = new Document();
        int lineidx = 0;
        doc.insert(0, "The quick brown fox jumped over the lazy dog.");
        assertTrue("Check line: " + OUTPUT[lineidx],
                   doc.getText().equals(OUTPUT[lineidx++]));
        doc.insert(0, "I heard that ");
        assertTrue("Check line " + OUTPUT[lineidx],
                   doc.getText().equals(OUTPUT[lineidx++]));
        doc.remove(0, "I heard that ".length());
        assertTrue("Check line " + OUTPUT[lineidx],
                   doc.getText().equals(OUTPUT[lineidx++]));

        doc.insert(0, "Some guy said, \"");
        doc.insert(doc.getLength(), "\"");
        assertTrue("Check line " + OUTPUT[lineidx],
                   doc.getText().equals(OUTPUT[lineidx++]));

        doc.replace(doc.getText().indexOf("lazy"), "lazy".length(), "spritely");
        assertTrue("Check line " + OUTPUT[lineidx],
                   doc.getText().equals(OUTPUT[lineidx++]));
    }

    protected static final String[] OUTPUT = {
        "The quick brown fox jumped over the lazy dog.",
        "I heard that The quick brown fox jumped over the lazy dog.",
        "The quick brown fox jumped over the lazy dog.",
        "Some guy said, \"The quick brown fox jumped over the lazy dog.\"",
        "Some guy said, \"The quick brown fox jumped over the spritely dog.\"",
    };
}
