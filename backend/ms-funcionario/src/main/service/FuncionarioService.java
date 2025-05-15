import com.msfuncionario.exceptions.ListaVaziaException;

@Service
public class FuncionarioService {
    
    @Autowired
    private ModelMapper modelMapper;

    public List<FuncionarioResponseDto> listar() throws ListaVaziaException {
        Optional<List<Funcionario>> listaFuncionarioBD = funcionarioRepository.findByAtivo(true);

        if (!listaFuncionarioBD.isPresent() || listaFuncionarioBD.get().isEmpty()) {
            throw new ListaFuncionarioVaziaException("Lista de funcionarios ativos vazia!");
        }
        // Converte cada Funcionario em FuncionarioResponseDto usando ModelMapper
        List<FuncionarioResponseDto> listaFuncionarioDto = listaFuncionarioBD.get()
            .stream()
            .map(funcionario -> modelMapper.map(funcionario, FuncionarioResponseDto.class))
            .collect(Collectors.toList());

        return listaFuncionarioDto;
    }

    public FuncionarioResponseDto buscarPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
            .orElseThrow(() -> new FuncionarioNaoEncontradoException("Não encontrado"));
        
        return modelMapper.map(funcionario, FuncionarioResponseDto.class);
    }
}
