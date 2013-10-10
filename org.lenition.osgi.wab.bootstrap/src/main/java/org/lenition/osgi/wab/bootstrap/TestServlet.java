package org.lenition.osgi.wab.bootstrap;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;


/**
 * A test servlet. See https://github.com/cgfrost/osgi-toolchain-gradle-bnd for a more complete listing of Servlet types and web.xml configurations.
 */
public class TestServlet extends HttpServlet {
	
    protected static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    /**
     * Default get method. Prints request parameters.
     * @param request client request
     * @param response server response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        print(request.getParameterMap(), out);
        out.close();
    }
    	
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("Starting org.lenition.osgi.wab.bootstrap.TestServlet");
    }

    protected static void print(Map map, PrintWriter out) {
        out.println("====Printing Map====");
        Iterator iterator = map.keySet().iterator();  
        while (iterator.hasNext()) {  
           String key = iterator.next().toString();  
           String value = map.get(key).toString();  
           System.out.println(key + " " + value);  
        }  
    }
    
}
