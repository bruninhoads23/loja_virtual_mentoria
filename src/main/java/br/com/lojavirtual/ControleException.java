package br.com.lojavirtual;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.lojavirtual.model.dto.ObjectErrorDTO;
import br.com.lojavirtual.service.ServiceSendEmail;

@RestControllerAdvice
@ControllerAdvice
public class ControleException extends ResponseEntityExceptionHandler {
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@ExceptionHandler(ExceptionMentoriaJava.class)
	public ResponseEntity<Object> handleExceptionCustom (ExceptionMentoriaJava ex){
		
		ObjectErrorDTO objectErrorDTO = new ObjectErrorDTO();
		objectErrorDTO.setError(ex.getMessage());
		objectErrorDTO.setCode(HttpStatus.OK.toString());
		
		return new ResponseEntity<Object>(objectErrorDTO, HttpStatus.OK);
		
	}
	
	
	/*captura exceções do projeto*/
	@ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ObjectErrorDTO objectErrorDTO = new ObjectErrorDTO();
			
		String msg = "";
		
		if(ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
			for(ObjectError objecterror : list) {
				msg += objecterror.getDefaultMessage() + "\n";
				
			}
			
		}else if(ex instanceof HttpMessageNotReadableException){
			
			msg = "não está sendo enviado mensagem para o corpo da requisição";
			
			
		}else {
			msg = ex.getMessage();
		}
		
		objectErrorDTO.setError(msg);
		objectErrorDTO.setCode(status.value() + " ==> " + status.getReasonPhrase()); 
		
		ex.printStackTrace();
		
		//envia email para suporte quando der erro na loja virtual
		try {
			serviceSendEmail.enviarEmailHtml("Erro na Loja Virtual", 
					ExceptionUtils.getStackTrace(ex), 
					"bruninhoads23@gmail.com");
		} catch (UnsupportedEncodingException | MessagingException e) {
	
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>(objectErrorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	@ExceptionHandler({DataIntegrityViolationException.class, 
		               ConstraintViolationException.class, 
		               java.sql.SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex){
	
	ObjectErrorDTO objetoErrorDTO = new ObjectErrorDTO();
	
	String msg = "";
	
	if (ex instanceof DataIntegrityViolationException) {
		msg = "Erro de integridade no banco: " +  ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
	}else
	if (ex instanceof ConstraintViolationException) {
		msg = "Erro de chave estrangeira: " + ((ConstraintViolationException) ex).getCause().getCause().getMessage();
	}else
	if (ex instanceof SQLException) {
		msg = "Erro de SQL do Banco: " + ((SQLException) ex).getCause().getCause().getMessage();
	}else {
		msg = ex.getMessage();
	}
	
	objetoErrorDTO.setError(msg);
	objetoErrorDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString()); 
	
	ex.printStackTrace();
	
	try {
		serviceSendEmail.enviarEmailHtml("Erro na Loja Virtual", 
				ExceptionUtils.getStackTrace(ex), 
				"bruninhoads23@gmail.com");
	} catch (UnsupportedEncodingException | MessagingException e) {

		e.printStackTrace();
	}
	
	return new ResponseEntity<Object>(objetoErrorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	
}

}
