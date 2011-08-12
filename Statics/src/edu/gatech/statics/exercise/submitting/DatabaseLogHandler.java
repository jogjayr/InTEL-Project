/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.submitting;

import edu.gatech.statics.exercise.Exercise;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author lccstudent
 */
public class DatabaseLogHandler extends Handler {

    private PostLogger poster;

    /**
     * Constructor. Creates a postLogger with urlBase
     * @param urlBase
     */
    public DatabaseLogHandler(String urlBase) {
        poster = new PostLogger(urlBase);
    }

    /**
     * Records record using poster
     * @param record
     */
    @Override
    public void publish(LogRecord record) {
        Map<String, String> postMap = poster.getNewPostMap();

        String message = record.getMessage();
        if (record.getThrown() != null) {
            Throwable throwable = record.getThrown();

            StringBuilder sb = new StringBuilder();
            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                sb.append(stackTraceElement.toString() + "\n");
            }

            message += "\n" + throwable;
            message += "\n" + sb.toString();
        }

        postMap.put("problem_id", "" + Exercise.getExercise().getProblemID());
        postMap.put("user_id", "" + Exercise.getExercise().getState().getUserID());
        postMap.put("session_id", "" + Exercise.getExercise().getSessionID());
        postMap.put("java_class", record.getSourceClassName());
        postMap.put("java_method", record.getSourceMethodName());
        postMap.put("level", record.getLevel().toString());
        postMap.put("message", message);
        postMap.put("timestamp", "" + record.getMillis());

        poster.post(postMap);
    }

    /**
     * 
     */
    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}
