import com.msfuncionario.ms_funcionario..exceptions.ListaVaziaException;

@Service
public class FuncionarioService {
    
    @Autowired
    private ModelMapper modelMapper;

    public List<FuncionarioResponseDto> listar() throws ListaVaziaException {
        Optional<List<Funcionario>> listaFuncionarioBD = funcionarioRepository.findByAtivo(true);

        if (!listaFuncionarioBD.isPresent() || listaFuncionarioBD.get().isEmpty()) {
            throw new ListaFuncionarioVaziaException("Lista de funcionarios ativos vazia!");
        }
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

    public FuncionarioResponseDto consultarEmail(String email) {
        Funcionario funcionario = funcionarioRepository.findByEmailAndAtivo(email, true)
            .orElseThrow(() -> new FuncionarioNaoExisteException("Funcionário ativo não existe!"));

        return modelMapper.map(funcionario, FuncionarioResponseDto.class);
    }

    public UsuarioRequestCadastrarDto cadastrar(FuncionarioRequestDto funcionarioRequestDto) throws FuncionarioJaExisteException {
        List<Funcionario> funcionariosExistentes = funcionarioRepository
            .findByCpfOrEmail(funcionarioRequestDto.getCpf(), funcionarioRequestDto.getEmail())
            .orElse(Collections.emptyList());

        boolean cpfExists = false;
        boolean emailExists = false;
        Long idFuncionario = 0L;

        for (Funcionario f : funcionariosExistentes) {
            if (f.isAtivo()) {
                if (f.getCpf().equals(funcionarioRequestDto.getCpf())) {
                    cpfExists = true;
                }
                if (f.getEmail().equals(funcionarioRequestDto.getEmail())) {
                    emailExists = true;
                }
            }
        }

        if (cpfExists && emailExists) {
            throw new FuncionarioJaExisteException("Outro funcionário ativo com CPF e email já existente!");
        } else if (cpfExists) {
            throw new FuncionarioJaExisteException("Outro funcionário ativo com CPF já existente!");
        } else if (emailExists) {
            throw new FuncionarioJaExisteException("Outro funcionário ativo com email já existente!");
        }

        if (funcionariosExistentes.size() == 1) {
            idFuncionario = funcionariosExistentes.get(0).getIdFuncionario();
        } else if (funcionariosExistentes.size() > 1) {
            throw new FuncionarioJaExisteException("Outros funcionários inativos (um com CPF e outro com email) já existentes!");
        }

        Funcionario funcionario = modelMapper.map(funcionarioRequestDto, Funcionario.class);
        funcionario.setIdFuncionario(idFuncionario);

        Funcionario funcionarioCriado = funcionarioRepository.save(funcionario);

        UsuarioRequestCadastrarDto usuarioDto = modelMapper.map(funcionarioCriado, UsuarioRequestCadastrarDto.class);
        usuarioDto.setSenha(funcionarioRequestDto.getSenha());

        return usuarioDto;
    }

    public UsuarioRequestAtualizarDto atualizar(FuncionarioRequestDto funcionarioRequestDto) 
        throws FuncionarioNaoExisteException, FuncionarioJaExisteException {

        Optional<Funcionario> funcionarioBD = funcionarioRepository.findByIdAndAtivo(funcionarioRequestDto.getIdFuncionario(), true);
        if (!funcionarioBD.isPresent()) {
            throw new FuncionarioNaoExisteException("Funcionário ativo não existe!");
        }

        Optional<List<Funcionario>> funcionarioExistente = funcionarioRepository.findByCpfOrEmail(
                funcionarioRequestDto.getCpf(), funcionarioRequestDto.getEmail());

        if (funcionarioExistente.isPresent()) {
            List<Funcionario> listaFuncionarioExistente = funcionarioExistente.get();
            boolean duplicado = listaFuncionarioExistente.stream()
                .anyMatch(funcionario -> !funcionario.getIdFuncionario().equals(funcionarioBD.get().getIdFuncionario()));

            if (duplicado) {
                boolean cpfDuplicado = listaFuncionarioExistente.stream()
                    .anyMatch(funcionario -> !funcionario.getIdFuncionario().equals(funcionarioBD.get().getIdFuncionario()) &&
                                            funcionario.getCpf().equals(funcionarioRequestDto.getCpf()));
                if (cpfDuplicado) {
                    throw new FuncionarioJaExisteException("Outro funcionário com CPF já existente!");
                } else {
                    throw new FuncionarioJaExisteException("Outro funcionário com e-mail já existente!");
                }
            }
        }

        Funcionario funcionario = modelMapper.map(funcionarioRequestDto, Funcionario.class);
        funcionario.setIdFuncionario(funcionarioRequestDto.getIdFuncionario());

        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionario);

        UsuarioRequestAtualizarDto usuarioDto = modelMapper.map(funcionarioAtualizado, UsuarioRequestAtualizarDto.class);
        usuarioDto.setId(funcionarioAtualizado.getIdFuncionario());
        usuarioDto.setOldEmail(funcionarioBD.get().getEmail());
        usuarioDto.setSenha(funcionarioRequestDto.getSenha());

        return usuarioDto;
    }

    public FuncionarioResponseDto inativar(String email) throws FuncionarioNaoExisteException {
        Funcionario funcionario = funcionarioRepository.findByEmailAndAtivo(email, true)
            .orElseThrow(() -> new FuncionarioNaoExisteException("Funcionário ativo não existe!"));

        funcionario.setAtivo(false);
        Funcionario funcionarioInativado = funcionarioRepository.save(funcionario);
        return modelMapper.map(funcionarioInativado, FuncionarioResponseDto.class);
    }

    public FuncionarioResponseDto ativar(String email) throws FuncionarioNaoExisteException {
        Funcionario funcionario = funcionarioRepository.findByEmailAndAtivo(email, false)
            .orElseThrow(() -> new FuncionarioNaoExisteException("Funcionário inativo não existe!"));

        funcionario.setAtivo(true);
        Funcionario funcionarioAtivado = funcionarioRepository.save(funcionario);
        return modelMapper.map(funcionarioAtivado, FuncionarioResponseDto.class);
    }

    public FuncionarioResponseDto remover(String email) throws FuncionarioNaoExisteException {
        Optional<Funcionario> funcionarioBD = funcionarioRepository.findByEmail(email);
        if (!funcionarioBD.isPresent()) {
            throw new FuncionarioNaoExisteException("Funcionario nao existe!");
        }

        Funcionario funcionario = funcionarioBD.get();
        funcionarioRepository.deleteById(funcionario.getIdFuncionario());
        FuncionarioResponseDto funcionarioRemovidoDto = modelMapper.map(funcionario, FuncionarioResponseDto.class);
        return funcionarioRemovidoDto;
    }

    public FuncionarioResponseDto reverter(Long idFuncionario) throws FuncionarioNaoExisteException {
        // Funcionario funcionarioCache = redisFuncionarioCache.getCache(idFuncionario);
        // if (funcionarioCache == null) {
        //     throw new FuncionarioNaoExisteException("Funcionario nao existe no cache!");
        // }

        // Funcionario funcionario = funcionarioRepository.save(funcionarioCache);
        // redisFuncionarioCache.removeCache(funcionarioCache.getIdFuncionario());
        return modelMapper.map(funcionario, FuncionarioResponseDto.class);
    }
}
