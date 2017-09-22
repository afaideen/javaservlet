/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.birdpeek.javaservlet.backend;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyServlet extends HttpServlet {
    private JSONObject jsonObject;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = null;
        resp.setContentType("text/plain");
        name = req.getParameter("name");

        if(name == null){
            myCustomFunction(req, jsonObject);
            name = jsonObject.get("name").toString();
            resp.getWriter().println("Hello " + name);
        }
        else {

            resp.setContentType("text/plain");
            if (name == null) {
                resp.getWriter().println("Please enter a name");
            }
            else
                resp.getWriter().println("Hello " + name);
        }
    }

    private boolean myCustomFunction(HttpServletRequest req, JSONObject json) throws IOException {
        StringBuffer jb = new StringBuffer();

        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }
        String jsonData = jb.toString().replace("\t", "");

        try {
            json =  new JSONObject(jsonData);//HTTP.toJSONObject(jsonData);

        } catch (JSONException e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        }
        if(json == null)
            return false;
        else {
            jsonObject = json;
            return true;
        }
    }
}
