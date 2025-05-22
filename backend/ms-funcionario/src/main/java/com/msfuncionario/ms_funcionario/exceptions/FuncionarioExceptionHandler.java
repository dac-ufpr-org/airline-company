import com.msfuncionario.ms_funcionario..exceptions.specific.FuncionarioNaoEncontradoException;
import com.msfuncionario.ms_funcionario..exceptions.specific.FuncionarioJaExisteException;
import com.msfuncionario.ms_funcionario..exceptions.specific.ListaVaziaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FuncionarioExceptionHandler {

    @ExceptionHandler(FuncionarioNaoEncontradoException.class)
    public ResponseEntity<String> handleFuncionarioNaoEncontrado(FuncionarioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(FuncionarioJaExisteException.class)
    public ResponseEntity<String> handleFuncionarioJaExiste(FuncionarioJaExisteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ListaVaziaException.class)
    public ResponseEntity<String> handleListaVazia(ListaVaziaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum funcionário encontrado.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleErroGeral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Erro inesperado: " + ex.getMessage());
    }
}