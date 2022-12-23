package br.com.lojavirtual.security;

import java.io.IOException;
import java.net.http.HttpRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.resource.HttpResource;

public class JWTApiAutenticacaoFilter extends GenericFilterBean {

	/* filtro onde todas as requisições serão capturadas para autenticar */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			/* estabelecer a atuenticação do usuário */

			Authentication authentication = new JWTTokenAutenticacaoService()
					.getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);

			/* coloca o processo de autenticação para o spring security */

			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Ocorreu um erro no sistema! avise o Administrador: \n" + e.getMessage());
		}

	}

}
