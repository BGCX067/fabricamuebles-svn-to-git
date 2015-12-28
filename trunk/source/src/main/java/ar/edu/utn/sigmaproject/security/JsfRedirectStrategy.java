package ar.edu.utn.sigmaproject.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.util.UrlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsfRedirectStrategy extends DefaultRedirectStrategy {
	private static final Logger logger = LoggerFactory.getLogger(JsfRedirectStrategy.class);

	private boolean contextRelative;

	private static final String PARTIAL_RESPONSE_PREFIX = "<partial-response><redirect url=\"";
	private static final String PARTIAL_RESPONSE_SUFFIX = "\"/></partial-response>";

	@Override
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

		if (isAjaxRequest(request)) {

			try {
				// in order for the url to be valid in the XML content
				if (url.contains("&"))
					url = url.replace("&", "&amp;");

				returnPartialResponseRedirect(request, response, url);
				return;

			} catch (Exception e) {
				logger.error("[sendRedirect: failed]", e);
			}
		}

		super.sendRedirect(request, response, url);
	}

	/**
	 * Copied from org.springframework.faces.webflow.JsfAjaxHandler.isAjaxRequestInternal
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("Faces-Request");
		String param = request.getParameter("javax.faces.partial.ajax");
		return ("partial/ajax".equals(header) || "true".equals(param)) ? true : false;
	}

	private void returnPartialResponseRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
		String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
		redirectUrl = response.encodeRedirectURL(redirectUrl);

		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String responseStr = PARTIAL_RESPONSE_PREFIX + redirectUrl + PARTIAL_RESPONSE_SUFFIX;

		PrintWriter out = response.getWriter();
		out.write(responseStr);
		out.flush();
		out.close();
	}

	/**
	 * Copied from parent (Spring Security 3.1.2), because it's "private"! Changed access modifier to "protected".
	 * @param contextPath
	 * @param url
	 * @return
	 */
	protected String calculateRedirectUrl(String contextPath, String url) {
		if (!UrlUtils.isAbsoluteUrl(url)) {
			if (contextRelative) {
				return url;
			} else {
				return contextPath + url;
			}
		}

		// Full URL, including http(s)://

		if (!contextRelative) {
			return url;
		}

		// Calculate the relative URL from the fully qualified URL, minus the scheme and base context.
		url = url.substring(url.indexOf("://") + 3); // strip off scheme
		url = url.substring(url.indexOf(contextPath) + contextPath.length());

		if (url.length() > 1 && url.charAt(0) == '/') {
			url = url.substring(1);
		}

		return url;
	}

	/**
	 * If <tt>true</tt>, causes any redirection URLs to be calculated minus the protocol
	 * and context path (defaults to <tt>false</tt>).
	 */
	public void setContextRelative(boolean useRelativeContext) {
		this.contextRelative = useRelativeContext;
	}

}