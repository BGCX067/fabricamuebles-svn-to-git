package ar.edu.utn.sigmaproject.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedExceptionFilter implements Filter {

	protected final Log logger = LogFactory.getLog(CustomAccessDeniedExceptionFilter.class);

	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	private RedirectStrategy redirectStrategy = new JsfRedirectStrategy();
	private String destinationUrl;

	public CustomAccessDeniedExceptionFilter(String destinationUrl) {
		this.destinationUrl = destinationUrl;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);

			logger.debug("Chain processed normally");
		} catch (AccessDeniedException exception) {
			if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
				logger.debug("Access is denied (user is anonymous); redirecting to authentication entry point",
						exception);

				redirectStrategy.sendRedirect((HttpServletRequest) request, (HttpServletResponse) response,
						destinationUrl);
			} else {
				throw exception;
			}
		}
	}

	@Override
	public void destroy() {

	}
}
