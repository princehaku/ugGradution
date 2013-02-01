/*  Copyright 2010 princehaku
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Created on : Oct 9, 2011, 5:17:29 PM
 *  Author     : princehaku
 */
package net.techest.ug;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author princehaku
 */
public class ResponseHeaderFilter implements Filter {

    FilterConfig fc;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // set the provided HTTP response parameters
        for (Enumeration e = fc.getInitParameterNames(); e.hasMoreElements();) {
            String headerName = (String) e.nextElement();
            response.addHeader(headerName, fc.getInitParameter(headerName));
        }
        // pass the request/response on
        chain.doFilter(req, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.fc = filterConfig;
    }

    @Override
    public void destroy() {
        this.fc = null;
    }
}